package part1.lesson12.task01;

import java.io.*;
import java.util.*;

/**
 * The class demonstrates OutOfMemoryError in heap space
 */

public class HeapSpaceErrorDemonstrator {

    private static final int LOOP_COUNT = 100000000;
    private static final String file = "content.txt";

    public static void main(String[] args) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)))) {
            StringBuilder builder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                builder.append(line);
            }
            List<String> list = new ArrayList<>();
            for (int i = 0; i < LOOP_COUNT; i++) {
                list.add(builder.toString());
                if (i % 10 == 0) {
                    list.remove(0);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
