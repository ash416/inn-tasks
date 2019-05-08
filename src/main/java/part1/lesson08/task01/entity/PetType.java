package part1.lesson08.task01.entity;

import java.io.Serializable;

/**
 * The class is for demonstration serialization. It is reference type for class Pet.
 */

public class PetType implements Serializable {
    private String type;

    private short typeId;

    public PetType() {}

    public PetType(String type, short typeId) {
        this.type = type;
        this.typeId = typeId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public short getTypeId() {
        return typeId;
    }

    public void setTypeId(short typeId) {
        this.typeId = typeId;
    }

    @Override
    public String toString() {
        return "type: " + type + ", typeId: " + typeId;
    }


}
