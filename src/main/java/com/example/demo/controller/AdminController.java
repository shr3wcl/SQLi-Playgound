package com.example.demo.controller;

import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.DBService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Controller
public class AdminController {
    @Autowired
    private UserRepository userRepo;
    DBService db = new DBService();

    @GetMapping("/users")
    public String showListUserPage(Model model, HttpSession httpSession, HttpServletRequest request){
        db.saveLog(request);
        if(!Objects.equals(httpSession.getAttribute("username"), "admin")){
            return "error";
        }
        List<User> listUser = userRepo.findAll();
        model.addAttribute("listUser", listUser);
        model.addAttribute("user", new User());
        return "users";
    }

    @GetMapping("/users/edit/{id}")
    public String showEditPage(@PathVariable("id") Long id, Model model, HttpServletRequest request){
        db.saveLog(request);
        try{
            Optional<User> user = userRepo.findById(id);
            if (user.isPresent()) {
                model.addAttribute("user", user.get());
                return "edit_user";
            }else{
                model.addAttribute("error", "Không tìm thấy người dùng");
                return "redirect:/users";

            }

        } catch (Error error){
            model.addAttribute("error", error);
            return "error";
        }
    }

    @PostMapping("/users/edit")
    public String processEdit(User user, Model model, HttpServletRequest request){
        db.saveLog(request);
        User existingUser = userRepo.findById(user.getId()).orElse(null);
        if (existingUser != null) {
            existingUser.setUsername(user.getUsername());
            existingUser.setEmail(user.getEmail());
            existingUser.setName(user.getName());
            userRepo.save(existingUser);
            return "redirect:/users";
        } else {
            model.addAttribute("error", "Có lỗi khi thay đổi");
            return "redirect:/users/edit/"+user.getId();
        }
    }

    @PostMapping("/users/delete")
    public String processDelete(User user, RedirectAttributes ra, HttpServletRequest request){
        db.saveLog(request);
        String query = "DELETE FROM users WHERE username = '" + user.getUsername() + "'";
        int result = db.executeUpdateQuery(query);
        if(result == 0){
            ra.addFlashAttribute("errMsg", "Xóa không thành công");
        }
        else{
            ra.addFlashAttribute("sucMsg", "Đã xóa người dùng");
        }
        return "redirect:/users";
    }
}