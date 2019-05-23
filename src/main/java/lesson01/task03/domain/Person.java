package part1.lesson01.task03.domain;

/**
 * Person class with properties <b>age</b>, <b>sex</b>, <b>name</b>
 * @author Sheronova Anna
 */
public class Person {

    private Integer age;
    private Sex sex;
    private String name;

    public Person(String name, Sex sex, Integer age) {
        this.name = name;
        this.age = age;
        this.sex = sex;
    }

    public Integer getAge() {
        return age;
    }

    public Sex getSex() {
        return sex;
    }

    public String getName() {
        return name;
    }

    /**
     * Sex class
     */
    public enum Sex {
        MAN,
        WOMAN
    }
    @Override
    public String toString() {
        return String.format("Sex: %s; age: %s; name: %s.", sex, age, name);
    }
}
