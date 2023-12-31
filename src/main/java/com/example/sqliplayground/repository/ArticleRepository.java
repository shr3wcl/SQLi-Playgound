package com.example.sqliplayground.repository;

import com.example.sqliplayground.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {
//    @Query("SELECT ")
}
