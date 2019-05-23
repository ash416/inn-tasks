package part1.lesson01.task03.service.strategies;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import part1.lesson01.task03.domain.Person;
import part1.lesson01.task03.service.PriorityPersonComparator;
import part1.lesson01.task03.service.PersonsSortingStrategy;
import part1.lesson01.task03.service.exceptions.SimilarPersonsException;

import java.util.Arrays;

/**
 * Class that executes quick sort
 * @author Sheronova Anna
 */

public class QuickPersonsSortingStrategy implements PersonsSortingStrategy {

    private PriorityPersonComparator comparator;
    private static Log LOGGER = LogFactory.getLog(QuickPersonsSortingStrategy.class);

    public QuickPersonsSortingStrategy(PriorityPersonComparator comparator) {
        this.comparator = comparator;
    }

    @Override
    public Person[] sort(final Person[] persons) {
        Person[] personsForSorting = Arrays.copyOf(persons, persons.length);
        LOGGER.info("Запуск быстрой сортировки.");
        doSortRecursively(personsForSorting, 0, personsForSorting.length - 1);
        return personsForSorting;
    }

    private void doSortRecursively(Person[] personsForSorting, int start, int end) {
        if (start >= end) {
            return;
        }
        int i = start;
        int j = end;
        int current = i - (i - j) / 2;
        while (i < j) {
            while (i < current) {
                try {
                    if (comparator.compare(personsForSorting[i], personsForSorting[current]) == -1) {
                        break;
                    }
                } catch (SimilarPersonsException e) {
                    LOGGER.error(e.getMessage());
                }
                i++;
            }
            while (j > current) {
                try {
                    if (comparator.compare(personsForSorting[current], personsForSorting[j]) == -1) {
                        break;
                    }
                } catch (SimilarPersonsException e) {
                    LOGGER.error(e.getMessage());
                }
                j--;
            }
            if (i < j) {
                Person tempPerson = personsForSorting[i];
                personsForSorting[i] = personsForSorting[j];
                personsForSorting[j] = tempPerson;
                if (i == current) {
                    current = j;
                } else {
                    if (j == current) {
                        current = i;
                    }
                }
            }
            doSortRecursively(personsForSorting, start, current);
            doSortRecursively(personsForSorting, current + 1, end);
        }
    }
}
