package part1.lesson01.task02;

import java.util.Random;

/**
 * Class generates N random integer numbers from -100 to 100
 * @author Sheronova Anna
 */

class NumbersGenerator {
    static Integer[] generate(int N) {
        Random random = new Random();
        Integer[] numbers = new Integer[N];
        for (int i = 0; i < numbers.length; i++) {
            numbers[i] = random.nextInt() % 100;
        }
        return numbers;
    }
}
