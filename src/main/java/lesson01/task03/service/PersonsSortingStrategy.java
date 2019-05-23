package part1.lesson01.task03.service;

import part1.lesson01.task03.domain.Person;
import part1.lesson01.task03.service.exceptions.SimilarPersonsException;

/**
 * Class executes persons array sort
 * @author Sheronova Anna
 */
public interface PersonsSortingStrategy {
    Person[] sort(Person[] persons) throws SimilarPersonsException;
}
