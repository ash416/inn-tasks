package part1.lesson05.task01.domain;

import java.util.UUID;

/**
 * The class contains info about pet: uuid, name, weight and owner weigh
 */

public class Pet {
    private String uuid = UUID.randomUUID().toString();
    private String name;
    private Person owner;
    private Double weight;

    public Pet(String name, Person owner, Double weight) {
        this.name = name;
        this.owner = owner;
        this.weight = weight;
    }

    public Double getWeight() {
        return weight;
    }

    public String getName() {
        return name;
    }

    public Person getOwner() {
        return owner;
    }

    public String getUuid() {
        return uuid;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public void setOwner(Person owner) {
        this.owner = owner;
    }

    @Override
    public int hashCode() {
        return this.uuid.hashCode();
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Pet) || this.hashCode() != object.hashCode()) {
            return false;
        }
        Pet pet = (Pet) object;
        return this.name.equals(pet.name) && this.weight.equals(pet.weight)
                && this.uuid.equals(pet.uuid) && this.owner.equals(pet.owner);
    }

    @Override
    public String toString() {
        return String.format("Pet { id: %s; name: %s; weight: %s }; Owner { %s }", uuid, name, weight, owner);
    }
}
