package com.example.sqliplayground.controller;

import com.example.sqliplayground.entity.User;
import com.example.sqliplayground.service.DBService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.util.UUID;

@Controller
public class RegisterController {
    @Autowired
    private JavaMailSender javaMailSender;

    DBService db = new DBService();

    @GetMapping("/register")
    public String viewRegisterPage(Model model, HttpServletRequest request) {
        model.addAttribute("user", new User());
        db.saveLog(request);
        return "register";
    }

    @PostMapping("/processRegister")
    public String processRegister(User user, RedirectAttributes ra, HttpServletRequest request) {
        db.saveLog(request);
        user.setPassword(encryptMD5(user.getPassword()));
        String token = UUID.randomUUID().toString();
        String query = "INSERT INTO users (token ,email, name, password, username, is_active) VALUES ('" + token + "', '" + user.getEmail() + "','" + user.getName() + "','"+user.getPassword() +"','" +user.getUsername() + "',false)";
        int result = db.executeUpdateQuery(query);

        if (result == 0){
            ra.addFlashAttribute("errMsg", "Có lỗi khi đăng ký");
            return "redirect:/register";
        }

        sendActivationEmail(user, token, request);

        ra.addFlashAttribute("msg", "Đăng ký thành công, chúng tôi đã gửi một mã kích hoạt tài khoản đến email của bạn. Vui lòng kích hoạt để có thể đăng nhập!");
        return "redirect:/login";
    }

    @GetMapping("/active/{token}")
    public String validateRegister(@PathVariable("token") String token, Model model){
        String query = "SELECT * FROM users WHERE token = '" + token + "';";
        ResultSet resultSet = db.executeQuery(query);
        System.out.println("[Debug]: " + query);
        try {
            resultSet.next();
            query = "UPDATE users SET is_active = true, token = NULL WHERE id = " + resultSet.getString("id");
            db.executeUpdateQuery(query);
            model.addAttribute("sucMsg", "Kích hoạt thành công tài khoản.");
        } catch (Exception e){
            model.addAttribute("errMsg", "Đã có lỗi! " + e);
        }
        return "activate";
    }
    public String encryptMD5(String text) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(text.getBytes());
            BigInteger no = new BigInteger(1, messageDigest);
            StringBuilder hashText = new StringBuilder(no.toString(16));
            while (hashText.length() < 32) {
                hashText.insert(0, "0");
            }
            return hashText.toString();
        }catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    private void sendActivationEmail(User user, String token, HttpServletRequest request) {
        String confirmationUrl = "Đường dẫn để kích hoạt tài khoản: http://" + request.getServerName() + ":" + request.getServerPort() +
                request.getContextPath() + "/active/" + token;
        SimpleMailMessage message = new SimpleMailMessage();

        message.setTo(user.getEmail());
        message.setSubject("Kích hoạt tài khoản");
        message.setText(confirmationUrl);
        javaMailSender.send(message);
    }
}
