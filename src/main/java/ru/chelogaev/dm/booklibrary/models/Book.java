package ru.chelogaev.dm.booklibrary.models;

import org.springframework.stereotype.Component;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Component
public class Book {
    private int id;

    @Size(min = 1, max = 100, message = "The \"Name\" field must contain from 1 to 100 characters")
    private String name;

    @Size(min = 1, max = 100, message = "The \"Author\" field must contain from 1 to 100 characters")
    private String author;

    @Pattern(regexp="^(0|[1-9][0-9]*)$", message = "Enter an integer")
    private String year;

    public Book() {
    }

    public Book(int id, String name, String author, String year) {
        this.id = id;
        this.name = name;
        this.author = author;
        this.year = year;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }


}
