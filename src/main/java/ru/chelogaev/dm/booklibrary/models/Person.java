package ru.chelogaev.dm.booklibrary.models;

import org.springframework.stereotype.Component;

import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;


@Component
public class Person {
    private int id;

    //@NotEmpty(message = "ФИО не заполнено")
    @Size(min = 3, max = 100, message = "The full name field should contain from 3 to 100 characters")
    private String fio;


    @Pattern(regexp="^(0|[1-9][0-9]*)$", message = "Enter an integer")
    //@NotEmpty(message = "Год рождения не заполнен")
    private String birthYear;

    //@NotEmpty(message = "Email не заполнен")
    @Email(message = "Email is not valid")
    private String email;

   /* public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    private List<Book> books;*/

    public Person() {

    }


    public Person(String fio, String birthYear, String email) {
        this.fio = fio;
        //checkBirthYear(birthYear);
        this.birthYear = birthYear;
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFio() {
        return fio;
    }

    public void setFio(String fio) {
        this.fio = fio;
    }

    public String getBirthYear() {
        return birthYear;
    }

    private void checkBirthYear(String birthYear){
        if (birthYear!=null && !birthYear.isEmpty()) {
            int year = 0;
            try {
                year = Integer.parseInt(birthYear);
            } catch (IllegalArgumentException  ex) {
                throw new NumberFormatException("The numbers must be entered in the year of birth input field!");
            }
        }
    }

    public void setBirthYear(String birthYear) {
        //checkBirthYear(birthYear);
        this.birthYear = birthYear;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
