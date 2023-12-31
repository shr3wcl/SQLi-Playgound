package com.example.sqliplayground.controller;

import com.example.sqliplayground.entity.User;
import com.example.sqliplayground.service.DBService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

@Controller
public class LoginController {
    DBService db = new DBService();
    @GetMapping("/login")
    public String viewLoginPage(Model model, HttpServletRequest request){
        db.saveLog(request);
        model.addAttribute("user", new User());
        return "login";
    }

    @PostMapping("/processLogin")
    public String processLogin(User user, RedirectAttributes ra, HttpSession httpSession, HttpServletRequest request) {
        db.saveLog(request);
        user.setPassword(encryptMD5(user.getPassword()));


        try{
            Statement statement = db.connection.createStatement();
            String query = "SELECT * FROM users WHERE username = '" + user.getUsername() + "' AND password = '" + user.getPassword() + "' AND is_active = 1";
            ResultSet checkUser = statement.executeQuery(query);
            if (checkUser != null) {
                checkUser.next();
                httpSession.setAttribute("username", checkUser.getString("username"));
                httpSession.setAttribute("id", checkUser.getString("id"));
                httpSession.setAttribute("email", checkUser.getString("email"));
                httpSession.setAttribute("name", checkUser.getString("name"));

                return "redirect:/main";
            } else {
                ra.addFlashAttribute("msg", "Sai tên đăng nhập hoặc mật khẩu");
                return "redirect:/login";
            }
        }catch (SQLException e){
            ra.addFlashAttribute("msg", "Sai tên đăng nhập hoặc mật khẩu\n[Debug]: " + e);
            return "redirect:/login";
        }
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
}
