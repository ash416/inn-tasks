package part1.lesson01.task03.service.policies;

import part1.lesson01.task03.domain.Person;
import part1.lesson01.task03.service.ComparingPersonsPolicy;

/**
 * Class that compares two persons according to their names
 * @author Sheronova Anna
 */


public class NameComparingPersonPolicy implements ComparingPersonsPolicy {

    @Override
    public int compare(Person person1, Person person2) {
        return Integer.compare(0, person1.getName().compareTo(person2.getName()));
    }
}
