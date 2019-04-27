package part1.lesson05.task01;

import part1.lesson05.task01.policies.AgeComparingPersonsPolicy;
import part1.lesson05.task01.policies.SexComparingPersonsPolicy;
import part1.lesson05.task01.domain.Pet;

import java.util.Comparator;

/**
 * The class compares two pets. Comparison criteria are owner, pet's name and weigh
 */

public class PetComparator implements Comparator {
    private PriorityPersonComparator personComparator = new PriorityPersonComparator()
            .withPolicy(new SexComparingPersonsPolicy())
            .withPolicy(new AgeComparingPersonsPolicy());

    @Override
    public int compare(Object o1, Object o2) {
        Pet pet1 = (Pet)o1;
        Pet pet2 = (Pet)o2;
        int personComparingResult = 0;
        try {
            personComparingResult= personComparator.compare(pet1.getOwner(), pet2.getOwner());
            return personComparingResult;
        } catch (SimilarPersonsException e) {
            int nameComparingResult = Integer.compare(0, pet1.getName().compareTo(pet2.getName()));
            return nameComparingResult == 0 ? 0 : Double.compare(pet1.getWeight(), pet2.getWeight());
        }
    }
}
