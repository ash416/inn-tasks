package part1.lesson05.task01.policies;

import part1.lesson05.task01.domain.Person;
import part1.lesson05.task01.domain.Sex;

/**
 * Class that compares two persons according to their sex
 * @author Sheronova Anna
 */


public class SexComparingPersonsPolicy implements ComparingPersonsPolicy {

    @Override
    public int compare(Person person1, Person person2) {
        if (person1.getSex() == person2.getSex()) {
            return 0;
        } else {
            return person1.getSex() == Sex.MAN ? 1 : -1;
        }
    }
}
