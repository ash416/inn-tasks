package part1.lesson12.task02;

import part1.lesson12.task02.creator.JavaClassCreator;
import part1.lesson12.task02.creator.SomeClassLoader;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * The class demonstrates outOfMemoryError in Metaspace. To get this error it is needed to add
 * option -XX:MaxMetaspaceSize=16M
 */

public class MetaspaceErrorDemonstrator {
    private static final int LOOP_COUNT = 100000;
    private static final String file = "test";

    public static void main(String[] args) {
        List<Object> objects = new ArrayList<>();
        for (int i = 0; i < LOOP_COUNT; i++) {
            try {
                objects.add(createClass(file + i));
                if (i % 10 == 0) {
                    objects.remove(0);
                }
            } catch (ClassNotFoundException | IOException | IllegalAccessException | InstantiationException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * the method creates new class using custom loader and deletes files that were used for class creating
     * @param className
     * @return
     * @throws ClassNotFoundException
     * @throws IOException
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    private static Object createClass(String className) throws ClassNotFoundException, IOException, IllegalAccessException, InstantiationException {
        JavaClassCreator.create(className);
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        compiler.run(null, null, null, className + ".java");
        ClassLoader classLoader = new SomeClassLoader(className);
        Class newClass = classLoader.loadClass(className);
        Files.delete(Paths.get(className + ".java"));
        Files.delete(Paths.get(className + ".class"));
        return newClass.newInstance();
    }
}
