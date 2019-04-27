package part1.lesson04.task02;

import java.util.HashSet;
import java.util.Set;
/**
 * The class allows to store a set of objects, add new object to set, delete object from set
 * @author Sheronova Anna
 */

public class ObjectBox {
    private Set<Object> objects = new HashSet<>();

    public void addObject(Object object) {
        objects.add(object);
    }

    public void deleteObject(Object object) {
        objects.remove(object);
    }

    public String dump() {
        StringBuilder builder = new StringBuilder();
        objects.forEach(item -> builder.append(item).append(" "));
        return builder.toString();
    }

}
