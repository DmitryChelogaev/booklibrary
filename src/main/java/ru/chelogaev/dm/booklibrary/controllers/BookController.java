package ru.chelogaev.dm.booklibrary.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.chelogaev.dm.booklibrary.dao.BookDAO;
import ru.chelogaev.dm.booklibrary.dao.PersonDAO;
import ru.chelogaev.dm.booklibrary.models.Book;
import ru.chelogaev.dm.booklibrary.models.Person;
import ru.chelogaev.dm.booklibrary.util.BookValidator;

import javax.validation.Valid;
import java.util.Optional;

@Controller
@RequestMapping("/books")
public class BookController {
    private final BookDAO bookDAO;
    private final PersonDAO personDAO;
    private final BookValidator bookValidator;

    @Autowired
    public BookController(BookDAO bookDAO, PersonDAO personDAO, BookValidator bookValidator) {
        this.bookDAO = bookDAO;
        this.personDAO = personDAO;
        this.bookValidator = bookValidator;
    }

    @GetMapping()
    public String books(Model model) {
        model.addAttribute("books", bookDAO.getAll());
        return "books/all";
    }

    @GetMapping("/{id}")
    public String book(@PathVariable("id") int id, Model model, @ModelAttribute("person") Person person) {
        model.addAttribute("book", bookDAO.getById(id));
        Optional<Person> owner = bookDAO.getOwner(id);
        if (owner.isPresent()) {
            model.addAttribute("owner", owner.get());
        } else {
            model.addAttribute("people", personDAO.getAll());
        }
        return "books/book";
    }


    @GetMapping("/{id}/edit")
    public String editBook(Model model, @PathVariable("id") int id)
    {
        model.addAttribute("book", bookDAO.getById(id));
        return "books/edit";
    }

    @GetMapping("/new")
    public String newBook(@ModelAttribute("book") Book book) {
        return "books/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("book") @Valid Book book, BindingResult result) {
        if (!result.hasErrors()) {
            bookValidator.validate(book, result); //Checking unique pairs of name+author, year
        }
        if (result.hasErrors()) {
            return "books/new";
        }
        bookDAO.save(book);
        return "redirect:/books";
    }

    @PutMapping("/{id}")
    public String update(@ModelAttribute("book") @Valid Book book, BindingResult result, @PathVariable("id") int id) {
        if (!result.hasErrors()) {
            bookValidator.validate(book, result); //Checking unique pairs of name+author, year
        }
        if (result.hasErrors()) {
            return "books/edit";
        }
        bookDAO.update(id, book);
        return "redirect:/books";
    }

    @PatchMapping("/{id}/assign")
    public String assign(@ModelAttribute("person") Person person, @PathVariable("id") int id_book) {
        bookDAO.assignToPerson(id_book, person.getId());
        return "redirect:/books/"+id_book;
    }

    @PatchMapping("/{id}/release")
    String releaseFromPerson(@PathVariable("id") int id_book) {
        bookDAO.releaseFromPerson(id_book);
        return "redirect:/books/"+id_book;
    }


    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        bookDAO.delete(id);
        return "redirect:/books";
    }


}
