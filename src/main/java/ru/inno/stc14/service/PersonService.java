package ru.inno.stc14.service;

import ru.inno.stc14.entity.Person;

import java.io.IOException;
import java.util.List;

public interface PersonService {

    List<Person> getList();

    boolean addPerson(String name, String birth) throws IOException;
}
