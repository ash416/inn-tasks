package part1.lesson05.task01;

import part1.lesson05.task01.domain.Person;

/**
 * Class executes persons array sort
 * @author Sheronova Anna
 */
public interface PersonsSortingStrategy {
    Person[] sort(Person[] persons) throws SimilarPersonsException;
}
