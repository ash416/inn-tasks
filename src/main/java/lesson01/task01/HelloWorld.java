package part1.lesson01.task01;

import part1.lesson01.task01.exceptions.NotHelloWorldException;

/**
 * The class demonstrates several exceptions which lead to an error of the program
 * @author Sheronova Anna
 */

public class HelloWorld {

    public static void main(String[] args) {
        HelloWorld helloWorld = new HelloWorld();
        helloWorld.demonstrateNullPointer(null);
        helloWorld.demonstrateIndexOutOfBounds(new String[2]);
        helloWorld.demonstrateMyException("Not Hello world");
    }

    private void demonstrateNullPointer(String[] strings) {
        if (strings.length > 0) {
            System.out.println("Hello world");
        }
    }

    private void demonstrateIndexOutOfBounds(String[] strings) {
        for (int i = 0; i <= strings.length; i++) {
            System.out.println(strings[i]);
        }
    }

    private void  demonstrateMyException(String string) {
        if (!string.equals("Hello world")) {
            throw new NotHelloWorldException(String.format("String %s is not equal Hello world", string));
        } else {
            System.out.println(string);
        }
    }


}
