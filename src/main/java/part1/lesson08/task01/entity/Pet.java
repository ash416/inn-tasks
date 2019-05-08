package part1.lesson08.task01.entity;

import java.io.Serializable;

/**
 * The class is for demonstration serialization. It contains primitive type, String type and reference type.
 */

public class Pet implements Serializable {
    private String name;
    private int age;
    private PetType type;

    public Pet() {}

    public Pet(String name, int age, PetType type) {
        this.age = age;
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public PetType getType() {
        return type;
    }

    public void setType(PetType type) {
        this.type = type;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return name + ", " + age + ", " + type;
    }
}
