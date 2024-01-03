package com.example.sqliplayground.repository;

import com.example.sqliplayground.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {
    @Query("SELECT u FROM Article u WHERE u.title LIKE %?1% or u.content LIKE %?1% or u.author LIKE %?1%")
    List<Article> findArticleByKeySearch(String key);

    @Procedure(name = "searchArticle")
    List<Article> searchArticle(@Param("p_Author") String p_Author);
}