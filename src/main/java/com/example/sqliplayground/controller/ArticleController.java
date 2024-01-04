package com.example.sqliplayground.controller;

import com.example.sqliplayground.entity.Article;
import com.example.sqliplayground.repository.ArticleRepository;
import com.example.sqliplayground.service.DBService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
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
        return "article/articles";
    }

    @Transactional
    @GetMapping("/search")
    public String showSearchPage(Model model, @RequestParam("key") String key ){
        List<Article> articleList = articleRepository.searchArticle(key);
        model.addAttribute("articleList", articleList);
        return "article/articles";
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
        return "article/detail";
    }

    @PostMapping("/article/add")
    public String processAddArticle(Article article, RedirectAttributes ra){
        System.out.println(article.toString());
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
