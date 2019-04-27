package part1.lesson05.task01;

import part1.lesson05.task01.domain.Person;
import part1.lesson05.task01.domain.Pet;
import part1.lesson05.task01.exceptions.DuplicateException;
import part1.lesson05.task01.exceptions.NoSuchPetException;

import java.util.*;
import java.util.stream.Collectors;

/**
 * The class contains pets collection and allows to find pet by name, to change pet's info by uuid
 */

class PetsCollection {
    private Map<String, Pet> petsByIds = new HashMap<>();
    private Map<String, List<String>> petsIdsByName = new HashMap<>();

    void addPet(Pet pet) throws DuplicateException {
        final String name = pet.getName();
        final String uuid = pet.getUuid();
        if (petsIdsByName.containsKey(name)) {
            List<String> petsWithTheSameNameIds = petsIdsByName.get(name);
            if (petsWithTheSameNameIds.contains(uuid)) {
                throw new DuplicateException("Данный питомец уже существует в картотеке");
            }
            petsWithTheSameNameIds.add(uuid);
        } else {
            petsIdsByName.put(name, new ArrayList<>(Arrays.asList(uuid)));
        }
        petsByIds.put(uuid, pet);
    }

    List<Pet> findPetByName(String name) {
        List<String> uuids = petsIdsByName.get(name);
        return uuids == null ? null : uuids.stream().map(uuid -> petsByIds.get(uuid)).collect(Collectors.toList());
    }

    void changePetInfo(Pet newPet, String uuid) throws NoSuchPetException {
        checkNotExist(uuid);
        Pet pet = petsByIds.get(uuid);
        updateName(pet, newPet.getName());
        pet.setOwner(newPet.getOwner());
        pet.setWeight(newPet.getWeight());
    }

    void changePetInfo(Person newOwner, String uuid) throws NoSuchPetException{
        checkNotExist(uuid);
        Pet pet = petsByIds.get(uuid);
        pet.setOwner(newOwner);
    }

    void changePetInfo(String name, String uuid) throws NoSuchPetException {
        checkNotExist(uuid);
        Pet pet = petsByIds.get(uuid);
        updateName(pet, name);

    }

    void changePetInfo(Double weight, String uuid) throws NoSuchPetException {
        checkNotExist(uuid);
        Pet pet = petsByIds.get(uuid);
        pet.setWeight(weight);
    }

    void printSortPetsList(Comparator comparator) {
        List<Pet> pets = new ArrayList<>(petsByIds.values());
        pets.sort(comparator);
        for(Pet pet:pets) {
            System.out.println(pet);
        }
    }

    private void checkNotExist(String uuid) throws NoSuchPetException {
        if (!petsByIds.containsKey(uuid)) {
            throw new NoSuchPetException("Питомец с запрашиваемым идентификатором не найден");
        }
    }

    private void updateName(Pet pet, String newName) {
        String uuid = pet.getUuid();
        petsIdsByName.get(pet.getName()).remove(uuid);
        if (petsIdsByName.containsKey(newName)) {
            petsIdsByName.get(newName).add(uuid);
        } else {
            petsIdsByName.put(newName, new ArrayList<>(Arrays.asList(uuid)));
        }
        pet.setName(newName);
    }
}
