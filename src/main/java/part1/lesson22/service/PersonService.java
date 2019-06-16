package part1.lesson22.service;

import part1.lesson22.entity.Person;

import java.io.IOException;
import java.util.List;

public interface PersonService {

    List<Person> getList();

    boolean addPerson(String name, String birth) throws IOException;
}
