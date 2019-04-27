package part1.lesson05.task01;

import part1.lesson05.task01.domain.Person;
import part1.lesson05.task01.domain.PersonsListGenerator;
import part1.lesson05.task01.domain.Pet;
import part1.lesson05.task01.exceptions.DuplicateException;
import part1.lesson05.task01.exceptions.NoSuchPetException;

public class PetApplication {
    public static void main(String[] args) {
        PetsCollection petsCollection = new PetsCollection();
        PetComparator comparator = new PetComparator();
        Person[] persons = PersonsListGenerator.generate(5);
        try {
            petsCollection.addPet(new Pet("Борис", persons[0], 10.0));
            petsCollection.addPet(new Pet("Борис", persons[1], 12.0));
            petsCollection.addPet(new Pet("Василий", persons[2], 9.0));
            petsCollection.addPet(new Pet("Аркадий", persons[3], 10.0));
            petsCollection.addPet(new Pet("Тишка", persons[4], 11.0));
        } catch (DuplicateException e) {
            e.printStackTrace();
        }
        petsCollection.printSortPetsList(comparator);
        Pet pet = petsCollection.findPetByName("Василий").get(0);
        String uuid = pet.getUuid();
        try {
            System.out.println("Поменяем свойства Василия");
            petsCollection.changePetInfo(new Pet("Святозар", persons[1], 20.0), uuid);
            petsCollection.printSortPetsList(comparator);
            System.out.println("Поменяем имя Святозар на Арсений");
            petsCollection.changePetInfo("Арсений", uuid);
            petsCollection.printSortPetsList(comparator);
            System.out.println("Поменяем вес Арсения");
            petsCollection.changePetInfo(13.0, uuid);
            petsCollection.printSortPetsList(comparator);
            System.out.println("Поменяем владельца Арсения");
            petsCollection.changePetInfo(persons[3], uuid);
            petsCollection.printSortPetsList(comparator);
        } catch (NoSuchPetException e) {
            e.printStackTrace();
        }
    }
}
