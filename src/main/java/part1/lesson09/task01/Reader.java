package part1.lesson09.task01;

import java.util.Scanner;

/**
 * The class reads the body of the Worker class from console
 */

class Reader {

    /**
     * Reading from console. Reading continues until a blank line is entered
     * @return string from console
     */
    static String read() {
        Scanner scanner = new Scanner(System.in);
        StringBuilder method = new StringBuilder();
        while (scanner.hasNextLine()) {
            String current = scanner.nextLine();
            if (current.equals("")) {
                break;
            }
            method.append(current).append("\n");
        }
        return method.toString();
    }

}
