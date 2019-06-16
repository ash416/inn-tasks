package ru.inno.stc14.service;

import ru.inno.stc14.dao.PersonDAO;
import ru.inno.stc14.dao.jdbc.PersonDAOImpl;
import ru.inno.stc14.entity.Person;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;
import java.util.logging.Logger;

public class PersonServiceImpl implements PersonService {
    private Logger logger = Logger.getLogger(PersonServiceImpl.class.getName());
    private final PersonDAO personDAO;

    public PersonServiceImpl(Connection con) {
        personDAO = new PersonDAOImpl(con);
    }

    @Override
    public List<Person> getList() {
        return personDAO.getList();
    }

    @Override
    public boolean addPerson(String name, String birth) throws IOException {
        if ("Feofan".equals(name)) {
            throw new IOException("Feofan is not available!");
        }
        Person person = new Person();
        person.setName(name);
        person.setBirthDate(birth);
        return personDAO.addPerson(person);
    }
}
