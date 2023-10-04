package ru.chelogaev.dm.booklibrary.mapper;

import org.springframework.jdbc.core.RowMapper;
import ru.chelogaev.dm.booklibrary.models.Person;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PersonMapper implements RowMapper<Person> {
    @Override
    public Person mapRow(ResultSet rs, int rowNum) throws SQLException {

        Person person = new Person();
        person.setId(rs.getInt("id"));
        person.setFio(rs.getString("fio"));
        //person.setBirthYear(rs.getInt("birth_year"));
        person.setEmail(rs.getString("email"));
        return null;
    }
}
