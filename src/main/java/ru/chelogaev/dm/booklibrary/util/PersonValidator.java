package ru.chelogaev.dm.booklibrary.util;


import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.chelogaev.dm.booklibrary.dao.PersonDAO;
import ru.chelogaev.dm.booklibrary.models.Person;

@Component
public class PersonValidator implements Validator {

    private PersonDAO personDAO;

    public PersonValidator(PersonDAO personDAO) {
        this.personDAO = personDAO;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Person.class.equals(clazz);
    }

    @Override
    public void validate(Object obj, Errors errors) {
        Person person = (Person) obj;
        String age = person.getBirthYear();
        if (age != null && !age.isEmpty()) {
            int year = 0;
            try {
                year = Integer.parseInt(age);
            } catch (IllegalArgumentException ex) {
                //throw new NumberFormatException("Год рождения не число!");
                errors.rejectValue("birthYear", "", "The year of birth should be a number!");
            } finally {
                if (year < 1900) {
                    errors.rejectValue("birthYear", "", "The year of birth should not be earlier than 1900!");
                }
            }
        }
        if (personDAO.getByEmail(person.getEmail(), person.getId()).isPresent()) {
            errors.rejectValue("email", "", "The e-mail must be unique!");
        }

        if (personDAO.getByFio(person.getFio(),  person.getId()).isPresent()) {
            errors.rejectValue("fio", "", "Full name must be unique!");
        }
    }
}
