package part1.lesson06.task02;

import java.util.Random;
/**
 * The class generates random words
 */

import static part1.lesson06.task02.RandomSymbolsGenerator.getRandomLetter;

public class WordGenerator {
    private static Random random = new Random();
    static final int MIN_LETTERS_IN_WORD = 1;
    static final int MAX_LETTERS_IN_WORD = 15;


    static String generateFirstWordFromArray(String[] words, int maxSize) {
        String word;
        do {
            word = words[random.nextInt(words.length)];
        } while (word.length() > maxSize || maxSize - word.length() > 0 && maxSize - word.length() < MIN_LETTERS_IN_WORD + 1);
        String firstLetter = String.valueOf(word.charAt(0));
        return firstLetter.toUpperCase() + word.substring(1).toLowerCase();
    }

    static String generateWord(char firstLetter, int leftSentenceSize) {
        int size = Math.min(random.nextInt(MAX_LETTERS_IN_WORD - MIN_LETTERS_IN_WORD - 1) + MIN_LETTERS_IN_WORD, leftSentenceSize);
        StringBuilder word = new StringBuilder();
        word.append(firstLetter);
        for (int i = 1; i < size; i++) {
            word.append(getRandomLetter());
        }
        return word.toString();
    }
}
