package com.martynov.articles.controllers;

import com.martynov.articles.models.Comment;
import com.martynov.articles.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/comment")
public class CommentController {
    private final CommentService commentService;

    @PostMapping("/{id}")
    public String create(@ModelAttribute("comment") Comment comment, @PathVariable("id") int id) {
        commentService.save(comment, id);
        return "redirect:/articles/" + comment.getArticle().getId();
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        commentService.deleteById(id);
        return "redirect:/articles";
    }

    @GetMapping("/edit/{id}")
    public String editPage(@PathVariable int id, Model model) {
        model.addAttribute("comment", commentService.findById(id));
        return "comment/edit";
    }

    @PostMapping("/edit/{id}")
    public String edit(@PathVariable int id, @ModelAttribute Comment comment) {
        commentService.edit(id, comment);
        var articleId = commentService.findById(id).getArticle().getId();
        return "redirect:/articles/" + articleId;
    }
}
