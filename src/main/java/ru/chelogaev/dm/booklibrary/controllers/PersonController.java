package ru.chelogaev.dm.booklibrary.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.chelogaev.dm.booklibrary.dao.PersonDAO;
import ru.chelogaev.dm.booklibrary.models.Person;
import ru.chelogaev.dm.booklibrary.util.PersonValidator;

import javax.validation.Valid;


@Controller
@RequestMapping("/people")
public class PersonController {

    private final PersonDAO personDAO;
    private PersonValidator personValidator;

    @Autowired
    public PersonController(PersonDAO personDAO, PersonValidator personValidator) {
        this.personDAO = personDAO;
        this.personValidator = personValidator;
    }

    @GetMapping()
    public String index(Model model) {
        model.addAttribute("people", personDAO.getAll());
        return "people/all";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model) {
        model.addAttribute("person", personDAO.getById(id));
        model.addAttribute("books", personDAO.getBooksByPersonId(id));
        return "people/person";
    }

    @GetMapping("/new")
    public String newPerson(@ModelAttribute("person") Person person) {
        return "people/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("person") @Valid Person person,
                         BindingResult bindingResult) {
        if (!bindingResult.hasErrors())
        personValidator.validate(person, bindingResult);//Age verification, unique full name, email

        if (bindingResult.hasErrors())
            return "people/new";

        personDAO.save(person);
        return "redirect:/people";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("person", personDAO.getById(id));
        return "people/edit";
    }

    @PutMapping("/{id}")
    public String update(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult,
                         @PathVariable("id") int id) {
        if (!bindingResult.hasErrors())
            personValidator.validate(person, bindingResult);//Age verification, unique full name, email

        if (bindingResult.hasErrors())
            return "people/edit";

        personDAO.update(id, person);
        return "redirect:/people";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        personDAO.delete(id);
        return "redirect:/people";
    }
}
