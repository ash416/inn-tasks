package part1.lesson01.task03.domain;

import java.util.Random;

/**
 * Class for persons list generation
 * @author Sheronova Anna
 */

public class PersonsListGenerator {

    private static Random random = new Random();

    private static String[] womanNames = new String[]
            {"Надежда", "Наджия", "Надия", "Назгуль", "Наоми", "Наталья", "Невена", "Нелли"};
    private static String[] manNames = new String[]
            {"Бадр", "Барни", "Басир", "Бен", "Берт", "Богдан", "Борис", "Булат"};

    /**
     * method for persons list generation
     * @param personsCount - the number of required elements of a person class
     * @return randomly generated list of persons
     */
    public static Person[] generate(Integer personsCount) {
        Person[] persons = new Person[personsCount];
        for (int i = 0; i < personsCount; i++) {
            persons[i] = createRandomPerson();
        }
        return persons;
    }

    private static Person createRandomPerson() {
        Person.Sex sex = getRandomSex();
        return new Person(getRandomNameFor(sex), sex, getRandomAge());
    }

    private static String getRandomNameFor(Person.Sex sex) {
        if (sex == Person.Sex.MAN) {
            return manNames[random.nextInt(manNames.length)];
        } else {
            return womanNames[random.nextInt(womanNames.length)];
        }
    }

    private static Person.Sex getRandomSex() {
        return random.nextInt() % 2 == 0 ? Person.Sex.MAN : Person.Sex.WOMAN;
    }

    private static Integer getRandomAge() {
        return random.nextInt(100);
    }
}
