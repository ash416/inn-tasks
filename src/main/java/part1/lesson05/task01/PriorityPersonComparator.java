package part1.lesson05.task01;

import part1.lesson05.task01.domain.Person;
import part1.lesson05.task01.policies.ComparingPersonsPolicy;

import java.util.ArrayList;
import java.util.List;

/**
 * The class sorts the array according to policies in order of priority
 * @author Sheronova Anna
 */
public class PriorityPersonComparator {

    private List<ComparingPersonsPolicy> policies = new ArrayList<>();

    public PriorityPersonComparator withPolicy(ComparingPersonsPolicy policy) {
        policies.add(policy);
        return this;
    }

    public int compare(Person person1, Person person2) throws SimilarPersonsException{
        int comparingResult;
        for (ComparingPersonsPolicy policy: policies) {
            comparingResult = policy.compare(person1, person2);
            if (comparingResult != 0) {
                return comparingResult;
            }
        }
        throw new SimilarPersonsException(String.format("Пол, возраст и имена людей совпадают. Sex: %s; age: %s, name: %s.",
                person1.getSex(), person1.getAge(), person1.getName()));
    }
}
