package com.example.sqliplayground.controller;

import com.example.sqliplayground.entity.User;
import com.example.sqliplayground.repository.UserRepository;
import com.example.sqliplayground.service.DBService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.*;
import java.util.List;

@Controller
public class UserController {
    DBService db = new DBService();

    @Autowired
    private UserRepository userRepo;

    @GetMapping("")
    public String viewHomePage(HttpServletRequest request) {
        db.saveLog(request);
        return "index";
    }

    @GetMapping("/main")
    public String showMainPage(HttpSession httpSession, HttpServletRequest request) {
        db.saveLog(request);
        String username = (String) httpSession.getAttribute("username");
        if(username != null){
            return "user/main";
        }
        return "redirect:/";
    }

    @GetMapping("/user")
    public String showUsersPage(@RequestParam(name = "id", required = false) String id, Model model, HttpServletRequest request) {
        db.saveLog(request);
        ResultSet user = db.executeQuery("SELECT * FROM users WHERE id = " + id);
        try{
            if (user != null) {
                user.next();
                model.addAttribute("id", id);
                model.addAttribute("userName", user.getString("username"));
                model.addAttribute("userEmail", user.getString("email"));
                model.addAttribute("name", user.getString("name"));
            } else {
                model.addAttribute("errMsg", "Không tìm thấy người dùng này");
            }
        }catch (SQLException ignored){
            model.addAttribute("errMsg", "Không tìm thấy người dùng này");
        }
        return "user/user";
    }

    @GetMapping("/error")
    public String showErrorPage(Model model, HttpServletRequest request){
        db.saveLog(request);
        model.addAttribute("error");
        return "error";
    }


    @GetMapping("/logout")
    public String processLogout(HttpSession httpSession, HttpServletRequest request){
        db.saveLog(request);
        httpSession.removeAttribute("username");
        httpSession.removeAttribute("id");
        return "redirect:/";
    }
}