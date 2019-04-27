package part1.lesson05.task01.domain;

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

    @Override
    public int hashCode() {
        return this.name.hashCode() + this.age.hashCode() + this.sex.hashCode();
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Person) || object.hashCode() != this.hashCode()) {
            return false;
        }

        Person person = (Person) object;
        return person.sex.equals(this.sex) && person.age.equals(this.age) && person.name.equals(this.name);
    }

    @Override
    public String toString() {
        return String.format("Sex: %s; age: %s; name: %s.", sex, age, name);
    }
}
