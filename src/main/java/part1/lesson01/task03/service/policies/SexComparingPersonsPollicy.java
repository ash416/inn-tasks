package part1.lesson01.task03.service.policies;

import part1.lesson01.task03.domain.Person;
import part1.lesson01.task03.service.ComparingPersonsPolicy;

/**
 * Class that compares two persons according to their sex
 * @author Sheronova Anna
 */


public class SexComparingPersonsPollicy implements ComparingPersonsPolicy {

    @Override
    public int compare(Person person1, Person person2) {
        if (person1.getSex() == person2.getSex()) {
            return 0;
        } else {
            return person1.getSex() == Person.Sex.MAN ? 1 : -1;
        }
    }
}
