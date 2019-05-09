package part1.lesson06.task02;

import java.util.Random;

/**
 * The class generates random symbols
 */

public class RandomSymbolsGenerator {
    private static Random random = new Random();

    static boolean getRandomBoolean() {
        return Math.random() < 0.5;
    }

    static char getRandomCapitalLetter() {
        return (char)(random.nextInt(26) + 'A');
    }

    static char getRandomLetter() {
        return (char)(random.nextInt(26) + 'a');
    }

    static char getRandomSentenceEnding() {
        switch (random.nextInt(3)) {
            case (0):
                return '!';
            case (1) :
                return '?';
            case (2):
            default:
                return '.';
        }
    }
}
