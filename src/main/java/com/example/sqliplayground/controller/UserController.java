package com.example.sqliplayground.controller;
import com.example.sqliplayground.repository.UserRepository;
import com.example.sqliplayground.service.DBService;
import com.example.sqliplayground.service.FileStorageService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.*;
import java.util.Objects;

@Controller
public class UserController {
    DBService db = new DBService();
    private final String UPLOAD_DIR = "src/main/webapp/resources/upload/";
    private final String AVATAR_DIR = UPLOAD_DIR + "/";
    @Autowired
    private UserRepository userRepo;
    @Autowired
    FileStorageService storageService;
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
                model.addAttribute("avatar", user.getString("avatar"));
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
        return "error404";
    }


    @GetMapping("/logout")
    public String processLogout(HttpSession httpSession, HttpServletRequest request){
        db.saveLog(request);
        httpSession.removeAttribute("username");
        httpSession.removeAttribute("id");
        return "redirect:/";
    }

    @GetMapping("/upload/avatar")
    public String showUploadAvatarForm(){
        return "user/upload";
    }

    @PostMapping("/upload/avatar")
    public String handleFileUpload(@RequestParam("file") MultipartFile file, Model model, @RequestParam("username") String username) {
        if (file.isEmpty()) {
            model.addAttribute("msg", "File không được để rỗng");

            return "user/upload";
        }

        try {
            File uploadDir = new File("./src/main/webapp/resources/upload");
            File avatarDir = new File("./src/main/webapp/resources/upload");

            if (!uploadDir.exists()) {
                uploadDir.mkdirs();
            }
            if (!avatarDir.exists()) {
                avatarDir.mkdirs();
            }

            File uploadedFile = new File(uploadDir.getAbsolutePath() + File.separator + file.getOriginalFilename());
            file.transferTo(uploadedFile);
//            try {
//                Thread.sleep(20000); // 5000 milliseconds tương đương với 5 giây
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            if (!FilenameUtils.getExtension(file.getOriginalFilename()).equalsIgnoreCase("png")) {
            System.out.println(FilenameUtils.getExtension(uploadedFile.getName()));
            if (!FilenameUtils.getExtension(uploadedFile.getName()).equalsIgnoreCase("png")) {
                model.addAttribute("msg", "Upload không thành công, file upload phải là file PNG");
                uploadedFile.delete();
                return "user/upload";
            }

            File newFile = new File(avatarDir.getAbsolutePath() + File.separator + file.getOriginalFilename());
            uploadedFile.renameTo(newFile);

            String query = "UPDATE users SET avatar = '" + newFile.getName() + "' WHERE username = '" + username + "'";
            db.executeUpdateQuery(query);

            model.addAttribute("msg", "Upload avatar thành công");
            return "user/upload";
        } catch (IOException e) {
            System.out.println(e);
            model.addAttribute("msg", "Upload không thành công");
            return "user/upload";
        }
    }

    @GetMapping("/getFile/{file}")
    public String getFile(@PathVariable("file") String filename, Model model) {
        model.addAttribute("filename", filename);
        return "user/getFile";
    }

    @GetMapping("/file")
    public String getImageContent(@RequestParam("file") String filename, @RequestParam(value = "image", defaultValue = "false") String image,Model model) {
        try {
            Path filePath = Paths.get("src/main/webapp/resources/upload/").resolve(filename);
            byte[] imageBytes = Files.readAllBytes(filePath);
            if(Objects.equals(filename.split("\\.")[1], "png")){
                model.addAttribute("imageType", image);
                model.addAttribute("rawData", imageBytes);
            }
            model.addAttribute("filename", filename);
            return "user/file";
        } catch (IOException e) {
            return "user/file";
        }
    }
}