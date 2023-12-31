package com.example.demo.controller;

import com.example.demo.entity.Article;
import com.example.demo.entity.User;
import com.example.demo.repository.ArticleRepository;
import com.example.demo.service.DBService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Controller
public class ArticleController {

    @Autowired
    private ArticleRepository articleRepository;

    DBService db = new DBService();
    @GetMapping("/articles")
    public String showArticlePage(Model model){
        List<Article> articleList = articleRepository.findAll();
        model.addAttribute("articleList", articleList);
        return "articles";
    }

    @GetMapping("/article/view/{id}")
    public String showArticleDetail(Model model, @PathVariable String id){
        ResultSet article = db.executeQuery("SELECT * FROM article WHERE id = " + id);
        try{
            if (article != null) {
                article.next();
                model.addAttribute("id", id);
                model.addAttribute("title", article.getString("title"));
                model.addAttribute("content", article.getString("content"));
                model.addAttribute("author", article.getString("author"));
            } else {
                model.addAttribute("errMsg", "Không tìm thấy bài viết này");
            }
        }catch (SQLException ignored){
            model.addAttribute("errMsg", "Không tìm thấy bài viết này");
        }
        return "detailArticle";
    }

    @PostMapping("/article/add")
    public String processAddArticle(Article article, RedirectAttributes ra){
        String query = "INSERT INTO article (title, content, author) VALUES ('" + article.getTitle() + "','" + article.getContent() + "','" + article.getAuthor()+"')";
        int result = db.executeUpdateQuery(query);
        if (result == 0){
            ra.addFlashAttribute("errMsg", "Có lỗi khi đăng ký");
            return "redirect:/error";
        }
        ra.addFlashAttribute("msg", "Thêm bài viết thành công!");
        return "redirect:/error";
    }


}
