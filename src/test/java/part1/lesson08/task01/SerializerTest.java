package part1.lesson08.task01;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import part1.lesson08.task01.entity.Pet;
import part1.lesson08.task01.entity.PetType;
import part1.lesson08.task01.way01.Serializer;

class SerializerTest {

    private Pet testPet = new Pet("Vova", 12, new PetType("cat", (short)2));
    private String way1File = "file1.txt";
    private String way2File = "file2.txt";


    @Test
    void checkWay1SerializationIsCorrect() {
        Serializer.serialize(testPet, way1File);
        Pet desPet = (Pet)Serializer.deSerialize(way1File);
        Assertions.assertThat(testPet).usingRecursiveComparison().isEqualTo(desPet);
    }

    @Test
    void checkWay2SerializationIsCorrect() {
        part1.lesson08.task01.way02.Serializer.serialize(testPet, way2File);
        Pet desPet = (Pet) part1.lesson08.task01.way02.Serializer.deserialize(way2File);
        Assertions.assertThat(testPet).usingRecursiveComparison().isEqualTo(desPet);
    }

}
