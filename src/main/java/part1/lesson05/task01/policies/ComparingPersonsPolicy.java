package part1.lesson05.task01.policies;

import part1.lesson05.task01.domain.Person;

/**
 * Interface for comparing two persons
 * @author Sheronova Anna
 */
public interface ComparingPersonsPolicy {
    /**
     * method for comparing persons
     * @param person1
     * @param person2
     * @return -1, 0, 1 if person1 less than person2, the same as person2 or the more respectively
     */
    int compare(Person person1, Person person2);
}
