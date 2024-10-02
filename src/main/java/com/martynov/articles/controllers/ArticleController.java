package com.martynov.articles.controllers;

import com.martynov.articles.models.Article;
import com.martynov.articles.service.ArticleService;
import com.martynov.articles.service.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
@RequiredArgsConstructor
@RequestMapping("/articles")
public class ArticleController {

    private static final int ARTICLES_PER_PAGE = 25;

    private final ArticleService articleService;
    private final PersonService personService;

    @GetMapping("")
    public String index(Model model, @RequestParam(value = "page", required = false, defaultValue = "0") int page) {
        var articles = articleService.findAll(page, ARTICLES_PER_PAGE);
        model.addAttribute("page", page);
        model.addAttribute("articles", articles);
        return "articles/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable int id, Model model) {
        var article = articleService.findById(id);
        model.addAttribute("article", article);
        model.addAttribute("person", personService.getCurrentPerson());
        return "articles/article";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable int id) {
        var article = articleService.findById(id);
        if (article.getPerson().getUsername().equals(personService.getCurrentPerson().getUsername()))
            articleService.deleteById(id);
        return "redirect:/articles";
    }

    @GetMapping("/edit/{id}")
    public String editPage(@PathVariable int id, Model model) {
        var article = articleService.findById(id);
        if (article.getPerson().getUsername().equals(personService.getCurrentPerson().getUsername())) {
            model.addAttribute("article", article);
            return "articles/edit";
        }
        return "redirect:/articles";
    }

    @PostMapping("/edit/{id}")
    public String edit(@PathVariable int id, @ModelAttribute("article") Article article) {
        articleService.update(id,article);
        return "redirect:/articles/" + id;
    }

    @GetMapping("/new")
    public String createPage(@ModelAttribute("article") Article article) {
        return "articles/new";
    }

    @PostMapping("/new")
    public String create(@ModelAttribute Article article) {
        articleService.save(article);
        return "redirect:/articles";
    }

}
