package part1.lesson01.task03.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import part1.lesson01.task03.domain.Person;
import part1.lesson01.task03.service.exceptions.SimilarPersonsException;
import part1.lesson01.task03.service.strategies.QuickPersonsSortingStrategy;

/**
 * Class demonstrates persons array sort
 * @author Sheronova Anna
 */

public class PersonsSortingDemonstrator {

    private static final Log LOGGER = LogFactory.getLog(PersonsSortingDemonstrator.class);
    private PersonsSortingStrategy strategy = new QuickPersonsSortingStrategy(new PriorityPersonComparator());

    public void printSort(Person[] persons) {
        LOGGER.info("Получен массив объектов Person для сортировки");
        long startTime = System.currentTimeMillis();
        Person[] sortingPersons = new Person[0];
        try {
            sortingPersons = strategy.sort(persons);
        } catch (SimilarPersonsException e) {
            LOGGER.error(e.getMessage());
        }
        long endTime = System.currentTimeMillis();
        LOGGER.info(String.format("Массив отсортирован за %s мс. Результат сортировки: ", endTime - startTime));
        for (Person person: sortingPersons) {
            System.out.println(person);
        }
    }

    public PersonsSortingDemonstrator withStrategy(PersonsSortingStrategy strategy) {
        this.strategy = strategy;
        return this;
    }

}
