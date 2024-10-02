package com.martynov.articles.repository;

import com.martynov.articles.models.Article;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticleRepository extends JpaRepository<Article,Integer> {
    @Query("SELECT a FROM Article a left join fetch a.person")
    List<Article> findArticlesWithLimitAndOffset(Pageable pageable);
}
