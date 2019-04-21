package part1.lesson01.task03;

import part1.lesson01.task03.domain.Person;
import part1.lesson01.task03.domain.PersonsListGenerator;
import part1.lesson01.task03.service.PriorityPersonComparator;
import part1.lesson01.task03.service.PersonsSortingDemonstrator;
import part1.lesson01.task03.service.policies.AgeComparingPersonsPolicy;
import part1.lesson01.task03.service.policies.NameComparingPersonPolicy;
import part1.lesson01.task03.service.policies.SexComparingPersonsPollicy;
import part1.lesson01.task03.service.strategies.BubblePersonsSortingStrategy;
import part1.lesson01.task03.service.strategies.QuickPersonsSortingStrategy;

public class SortingPersonApplication {
    public static void main(String[] args) {
        PriorityPersonComparator personComparator = new PriorityPersonComparator()
                .withPolicy(new SexComparingPersonsPollicy())
                .withPolicy(new AgeComparingPersonsPolicy())
                .withPolicy(new NameComparingPersonPolicy());

        Person[] persons = PersonsListGenerator.generate(100);

        PersonsSortingDemonstrator demonstrator = new PersonsSortingDemonstrator();

        demonstrator.withStrategy(new QuickPersonsSortingStrategy(personComparator)).printSort(persons);
        demonstrator.withStrategy(new BubblePersonsSortingStrategy(personComparator)).printSort(persons);
    }
}
