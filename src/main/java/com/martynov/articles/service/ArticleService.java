package com.martynov.articles.service;

import com.martynov.articles.models.Article;
import com.martynov.articles.repository.ArticleRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ArticleService {

    private final ArticleRepository articleRepository;
    private final PersonService personService;

    @Transactional
    public List<Article> findAll(int page, int limit) {
        return articleRepository.findArticlesWithLimitAndOffset(PageRequest.of(page, limit));
    }

    @Transactional
    public Article findById(int id) {
        var article = articleRepository.findById(id).orElse(null);
        if (article!=null) {
            Hibernate.initialize(article.getComments());
        }
        return article;
    }

    @Transactional
    public void deleteById(int id) {
        articleRepository.deleteById(id);
    }

    @Transactional
    public void update(int id, Article article) {
        var articleFromDb = articleRepository.findById(id).orElse(null);
        if (articleFromDb == null)
            return;
        if (articleFromDb.getPerson().getId() != personService.getCurrentPerson().getId())
            return;
        articleFromDb.setName(article.getName());
        articleFromDb.setText(article.getText());
    }

    @Transactional
    public void save(Article article) {
        if (article==null) {
            return;
        }
        article.setPerson(personService.getCurrentPerson());
        article.setCreatedAt(new Date());
        articleRepository.save(article);
    }
}
