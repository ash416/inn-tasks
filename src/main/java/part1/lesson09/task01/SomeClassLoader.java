package part1.lesson09.task01;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * The class is custom class loader for special loading of file named "someClassName"
 */

public class SomeClassLoader extends ClassLoader {
    private String someClassName;

    SomeClassLoader(String someClassName) {
        this.someClassName = someClassName;
    }

    @Override
    public Class<?> loadClass(String name) throws ClassNotFoundException {
        if (someClassName.equals(name)) {
            return findClass(name);
        }
        return super.loadClass(name);
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        try {
            byte[] bytes = Files.readAllBytes(Paths.get(someClassName + ".class"));
            return defineClass(name, bytes, 0, bytes.length);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return super.findClass(name);
    }
}
