package part1.lesson04.task03;

import part1.lesson04.task03.exceptions.NotValidTypeException;

import java.util.HashSet;
import java.util.Set;
/**
 * The class allows to store a set of objects, add new object to set, delete object from set
 * @author Sheronova Anna
 */

public class ObjectBox {
    private Set<Object> objects = new HashSet<>();

    public void addObject(Object object) throws NotValidTypeException {
        objects.add(object);
    }


    public void deleteObject(Object object) throws NotValidTypeException {
        objects.remove(object);
    }

    public String dump() {
        StringBuilder builder = new StringBuilder();
        objects.forEach(item -> builder.append(item).append("\n"));
        return builder.toString();
    }

}
