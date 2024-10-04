package com.martynov.articles.service;

import com.martynov.articles.models.Comment;
import com.martynov.articles.repository.CommentRepository;
import com.martynov.articles.repository.ORM;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Timestamp;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final PersonService personService;
    private final ORM orm;

    @Transactional
    public void save(Comment comment, int id) {
        String[] columns = {"person_id", "article_id", "text", "created_at"};
        Object[] values = {
                personService.getCurrentPerson().getId(),
                id,
                comment.getText(),
                new Date()
        };
        orm.create("comment", columns, values);
    }
    @Transactional
    public void deleteById(int id) {
        String tableName = "comment";
        String idColumn = "id";
        orm.delete(tableName, idColumn, id);
    }

    @Transactional
    public Comment findById(int id) {
        String tableName = "comment";
        String idColumn = "id";
        return orm.findById(tableName, idColumn, id, Comment.class);
    }


    @Transactional
    public void edit(int id, Comment comment) {
        var commentFromDb = findById(id);
        if (commentFromDb == null) {
            return;
        }
        commentFromDb.setText(comment.getText());
        String[] columns = {"text"};
        Object[] values = {commentFromDb.getText()};
        orm.update("comment", columns, values, "id", id);
    }

}
