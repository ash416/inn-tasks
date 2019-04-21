package part1.lesson01.task03.service.strategies;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import part1.lesson01.task03.domain.Person;
import part1.lesson01.task03.service.PriorityPersonComparator;
import part1.lesson01.task03.service.PersonsSortingStrategy;
import part1.lesson01.task03.service.exceptions.SimilarPersonsException;

import java.util.Arrays;

/**
 * Class that executes bubble sort
 * @author Sheronova Anna
 */

public class BubblePersonsSortingStrategy implements PersonsSortingStrategy {
    private PriorityPersonComparator comparator;
    private static final Log LOGGER = LogFactory.getLog(BubblePersonsSortingStrategy.class);

    public BubblePersonsSortingStrategy(PriorityPersonComparator comparator) {
        this.comparator = comparator;
    }

    @Override
    public Person[] sort(final Person[] persons) {
        Person[] personsForSorting = Arrays.copyOf(persons, persons.length);
        LOGGER.info("Запуск пузырьковой сортировки.");
        for (int i = personsForSorting.length - 1; i >= 1; i--) {
            for (int j = 0; j < i; j++) {
                try {
                    if (comparator.compare(personsForSorting[j], personsForSorting[j + 1]) < 0) {
                        swap(personsForSorting, j, j + 1);
                    }
                } catch (SimilarPersonsException e) {
                    LOGGER.error(e.getMessage());
                    swap(personsForSorting, j, j + 1);
                }
            }
        }
        return personsForSorting;
    }

    private void swap(Person[] persons, int i, int j) {
        Person tempPerson = persons[j];
        persons[j] = persons[i];
        persons[i] = tempPerson;
    }
}
