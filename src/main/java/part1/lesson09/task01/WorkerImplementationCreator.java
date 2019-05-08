package part1.lesson09.task01;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Class creates .java file that is an implementation of Worker and has body of doWork method from parameters
 */

class WorkerImplementationCreator {

    static void create(String body, String file) {
        try (BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(file))) {
            outputStream.write(getBody(body).getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String getBody(String body) {
        return "public class SomeClass implements part1.lesson09.task01.Worker {\n" +
                "   @Override\n" +
                "   public void doWork() {\n" +
                "       " + body + "\n" +
                "   }\n" +
                "}";
    }
}
