package part1.lesson12.task02.creator;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * The class creates .java file with class called className
 */

public class JavaClassCreator {

    public static void create(String className) {
        try (BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(className + ".java"))) {
            outputStream.write(getBody(className).getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String getBody(String className) {
        return "public class " + className + " {\n" +
                "   public void doWork() {\n" +
                "   }\n" +
                "}";
    }
}
