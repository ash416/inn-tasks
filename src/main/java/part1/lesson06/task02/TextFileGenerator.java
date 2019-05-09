package part1.lesson06.task02;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import static part1.lesson06.task02.SentenceGenerator.*;

/**
 * The class generates files
 */

public class TextFileGenerator {
    private static final int MAX_SENTENCES_IN_PARAGRAPH = 20;
    private static final String PATH_NAME = "src/main/resources/";
    private static final int NUM_FILES = 5;
    private static final int FILE_SIZE = 1000;
    private static final int PROBABILITY = 2;


    public static void main(String[] args) {
        TextFileGenerator generator = new TextFileGenerator();
        generator.getFiles(PATH_NAME, NUM_FILES, FILE_SIZE, new String[] {"Q", "Qwerty", "WWE", "xz"}, PROBABILITY);
    }

    /**
     * Method creates file with random words
     * @param path to files
     * @param n - count of files to create
     * @param size - size of each file
     * @param words - there are words from this array with some probability
     * @param probability - probability that word from words array will be in the next sentence
     */

    void getFiles(String path, int n, int size, String[] words, int probability) {
        for (int i = 0; i < n; i++) {
            getFile(size, words, probability, path, i);
        }
    }

    private static void getFile(int size, String[] words, int probability, String path, int fileNum) {
        int leftSize = size;
        int countUsedWordsFromArray = 0;
        int countSentences = 0;
        String file = path + "file" + fileNum + ".txt";
        try (BufferedOutputStream os = new BufferedOutputStream(new FileOutputStream(file))) {
            while (leftSize > 0) {
                int sentenceSize = generateSentenceSize(leftSize, countSentences, MAX_SENTENCES_IN_PARAGRAPH);
                if (needToAddSpecialWord(countUsedWordsFromArray, countSentences, probability)) {
                    countUsedWordsFromArray++;
                    os.write(getSentenceWithOneOfWords(sentenceSize, words).getBytes());
                } else {
                    os.write(getSentenceWithRandomFirstWord(sentenceSize).getBytes());
                }
                countSentences++;
                leftSize -= sentenceSize;
                if (countSentences % MAX_SENTENCES_IN_PARAGRAPH == 0) {
                    leftSize--;
                    os.write('\r');
                }
                os.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static boolean needToAddSpecialWord(int countUsedWordsFromArray, int countSentences, int probability) {
        return ((double)countUsedWordsFromArray / (double)(countSentences + 1)) < (1 / (double)probability);
    }


}
