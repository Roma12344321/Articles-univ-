package com.martynov.articles.controllers;

import com.martynov.articles.models.Person;
import com.martynov.articles.service.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class IndexController {

    private final PersonService personService;

    @GetMapping("/")
    public String index(Model model) {
        try {
            Person person = personService.getCurrentPerson();
            model.addAttribute("person",person);
        } catch (Exception e) {
            model.addAttribute("person",null);
        }
        return "index";
    }
}
