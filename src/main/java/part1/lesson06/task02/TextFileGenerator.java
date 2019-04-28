package part1.lesson06.task02;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Random;

/**
 * The class generates files
 */

public class TextFileGenerator {

    private Random random = new Random();
    private static final int MIN_NUM_WORDS_IN_SENTENCE = 1;
    private static final int MAX_NUM_WORDS_IN_SENTENCE = 15;
    private static final int MIN_NUM_LETTERS_IN_WORD = 1;
    private static final int MAX_NUM_LETTERS_IN_WORD = 15;
    private static final int MAX_NUM_SENTENCES_IN_PARAGRAPH = 20;
    private static final int MIN_NUM_SYMBOLS_IN_SENTENCES
            = MIN_NUM_WORDS_IN_SENTENCE * MIN_NUM_LETTERS_IN_WORD //num of letters in words
            + MIN_NUM_WORDS_IN_SENTENCE - 1                       //num of spaces
            + 2;                                                  //(.|!|?)+" "
    private static final int MAX_NUM_SYMBOLS_IN_SENTENCES
            = MAX_NUM_WORDS_IN_SENTENCE * MAX_NUM_LETTERS_IN_WORD //num of letters in words
            + (MAX_NUM_WORDS_IN_SENTENCE - 1) * 2                 //num of spaces and commas
            + 2;                                                  //(.|!|?)+" "



    public static void main(String[] args) {
        TextFileGenerator generator = new TextFileGenerator();
        generator.getFiles("src/main/resources/", 5, 1000, new String[] {"Q", "Qwerty", "WWE", "xz"}, 2);
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

    private void getFile(int size, String[] words, int probability, String path, int fileNum) {
        int leftSize = size;
        int countUsedWordsFromArray = 0;
        int countSentences = 0;
        String file = path + "file" + fileNum + ".txt";
        try (BufferedOutputStream os = new BufferedOutputStream(new FileOutputStream(file))) {
            while (leftSize > 0) {
                int sentenceSize = getSentenceSize(leftSize, countSentences);
                if (((double)countUsedWordsFromArray / (double)(countSentences + 1)) < (1 / (double)probability)) {
                    countUsedWordsFromArray++;
                    addSentenceWithOneOfWords(sentenceSize, os, words);
                } else {
                    addSentenceWithRandomFirstWord(sentenceSize, os);
                }
                countSentences++;
                leftSize -= sentenceSize;
                if (countSentences % MAX_NUM_SENTENCES_IN_PARAGRAPH == 0) {
                    leftSize--;
                    os.write('\r');
                }
                os.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private int getSentenceSize(int leftSize, int countSentences) {
        int sentenceSize = random.nextInt(Math.min(MAX_NUM_SYMBOLS_IN_SENTENCES - MIN_NUM_SYMBOLS_IN_SENTENCES, leftSize)) + MIN_NUM_SYMBOLS_IN_SENTENCES;
        int diff = leftSize - sentenceSize;
        if (diff < 4 && diff > 0) {
            if (countSentences % MAX_NUM_SENTENCES_IN_PARAGRAPH == 0) {
                if (sentenceSize - MIN_NUM_SYMBOLS_IN_SENTENCES > MIN_NUM_SYMBOLS_IN_SENTENCES + 1 - diff) {
                    sentenceSize -= MIN_NUM_SYMBOLS_IN_SENTENCES + 1 - diff;
                } else {
                    sentenceSize += diff;
                }
            } else {
                if (diff < 3) {
                    if (sentenceSize - MIN_NUM_SYMBOLS_IN_SENTENCES > MIN_NUM_SYMBOLS_IN_SENTENCES - diff) {
                        sentenceSize -= MIN_NUM_SYMBOLS_IN_SENTENCES;
                    } else {
                        sentenceSize += diff;
                    }
                }
            }
        }
        return sentenceSize;
    }

    private void addSentenceWithOneOfWords(int size, BufferedOutputStream os, String[] words) throws IOException {
        int sentenceSize = size - 2;
        String word;
        do {
            word = words[random.nextInt(words.length)];
        } while (word.length() > sentenceSize || sentenceSize - word.length() > 0 && sentenceSize - word.length() < MIN_NUM_LETTERS_IN_WORD + 1);
        String firstLetter = String.valueOf(word.charAt(0));
        addSentence(sentenceSize, os, firstLetter.toUpperCase() + word.substring(1).toLowerCase());
    }

    private void addSentenceWithRandomFirstWord(int size, BufferedOutputStream os) throws IOException {
        int sentenceSize = size - 2;
        addSentence(size, os, getFirstWordInSentence(sentenceSize));
    }


    private void addSentence(int sentenceSize, BufferedOutputStream os, String firstWord) throws IOException {
        String currentWord = firstWord;
        os.write(currentWord.getBytes());
        sentenceSize -= currentWord.length();
        addSymbolsIfNecessary(os, sentenceSize);
        while (sentenceSize > 0) {
            currentWord = getUsualWordInSentence(sentenceSize);
            sentenceSize -= currentWord.length();
            os.write(currentWord.getBytes());
            addSymbolsIfNecessary(os, sentenceSize);
        }
        os.write(getRandomEnding());
        os.write(' ');
    }

    private char getRandomEnding() {
        switch (random.nextInt(3)) {
            case (0) :
                return '!';
            case (1) :
                return '?';
            case (2) :
                return '.';
            default:
                return '.';
        }
    }

    private void addSymbolsIfNecessary(BufferedOutputStream os, int leftSize) throws IOException {
        if (leftSize > 3) {
            if (getRandomBoolean()) {
                os.write(',');
            }
            os.write(' ');
            return;
        }
        if (leftSize == 2) {
           os.write(' ');
           return;
        }
        if (leftSize == 1) {
            os.write(getRandomLetter());
        }
    }

    private String getFirstWordInSentence(int leftSentenceSize) {
        return getWord(getRandomCapitalLetter(), leftSentenceSize);
    }

    private String getUsualWordInSentence(int leftSentenceSize) {
        return getWord(getRandomLetter(), leftSentenceSize);
    }


    private String getWord(char firstLetter, int leftSentenceSize) {
        int size = Math.min(random.nextInt(MAX_NUM_LETTERS_IN_WORD - MIN_NUM_LETTERS_IN_WORD - 1) + MIN_NUM_LETTERS_IN_WORD, leftSentenceSize);
        StringBuilder word = new StringBuilder();
        word.append(firstLetter);
        for (int i = 1; i < size; i++) {
            word.append(getRandomLetter());
        }
        return word.toString();
    }

    private boolean getRandomBoolean() {
        return Math.random() < 0.5;
    }

    private char getRandomCapitalLetter() {
        return (char)(random.nextInt(26) + 'A');
    }

    private char getRandomLetter() {
        return (char)(random.nextInt(26) + 'a');
    }

}
