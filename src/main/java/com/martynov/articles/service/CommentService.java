package com.martynov.articles.service;

import com.martynov.articles.models.Comment;
import com.martynov.articles.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final PersonService personService;
    private final ArticleService articleService;

    @Transactional
    public void save(Comment comment,int id) {
        var article = articleService.findById(id);
        comment.setArticle(article);
        comment.setCreatedAt(new Date());
        comment.setPerson(personService.getCurrentPerson());
        commentRepository.save(comment);
    }
    @Transactional
    public void deleteById(int id) {
        commentRepository.deleteById(id);
    }

    @Transactional
    public Comment findById(int id) {
        return commentRepository.findById(id).orElse(null);
    }

    @Transactional
    public void edit(int id,Comment comment) {
        var commentFromDb = findById(id);
        if (commentFromDb==null) {
            return;
        }
        commentFromDb.setText(comment.getText());
    }
}
