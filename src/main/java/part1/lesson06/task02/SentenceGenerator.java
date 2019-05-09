package part1.lesson06.task02;

import java.util.Random;

import static part1.lesson06.task02.RandomSymbolsGenerator.*;
import static part1.lesson06.task02.WordGenerator.*;

/**
 * The class generates sentences
 */

public class SentenceGenerator {

    private static Random random = new Random();
    private static final int MIN_WORDS_IN_SENTENCE = 1;
    private static final int MAX_WORDS_IN_SENTENCE = 15;
    private static final int MIN_SYMBOLS_IN_SENTENCES
            = MIN_WORDS_IN_SENTENCE * MIN_LETTERS_IN_WORD //num of letters in words
            + MIN_WORDS_IN_SENTENCE - 1                       //num of spaces
            + 2;                                                  //(.|!|?)+" "
    private static final int MAX_SYMBOLS_IN_SENTENCES
            = MAX_WORDS_IN_SENTENCE * MAX_LETTERS_IN_WORD //num of letters in words
            + (MAX_WORDS_IN_SENTENCE - 1) * 2                 //num of spaces and commas
            + 2;

    static String getSentenceWithRandomFirstWord(int size) {
        int sentenceSize = size - 2;
        return getSentence(size, generateWord(getRandomCapitalLetter(), sentenceSize));
    }

    static String getSentenceWithOneOfWords(int size, String[] words) {
        int sentenceSize = size - 2;
        return getSentence(sentenceSize, generateFirstWordFromArray(words, sentenceSize));
    }
    static int generateSentenceSize(int leftSize, int countSentences, int maxSentencesInParagraph) {
        int sentenceSize = randomValidSize(leftSize);
        int diff = leftSize - sentenceSize;
        if (thereAreExtraUselessCharactersLeft(diff)) {
            if (countSentences % maxSentencesInParagraph == 0) {
                if (sentenceSize - MIN_SYMBOLS_IN_SENTENCES > MIN_SYMBOLS_IN_SENTENCES + 1 - diff) {
                    sentenceSize -= MIN_SYMBOLS_IN_SENTENCES + 1 - diff;
                } else {
                    sentenceSize += diff;
                }
            } else {
                if (diff < 3) {
                    if (sentenceSize - MIN_SYMBOLS_IN_SENTENCES > MIN_SYMBOLS_IN_SENTENCES - diff) {
                        sentenceSize -= MIN_SYMBOLS_IN_SENTENCES;
                    } else {
                        sentenceSize += diff;
                    }
                }
            }
        }
        return sentenceSize;
    }

    private static boolean thereAreExtraUselessCharactersLeft(int diff) {
        return diff < 4 && diff > 0;
    }

    private static int randomValidSize(int leftSize) {
        return random.nextInt(Math.min(MAX_SYMBOLS_IN_SENTENCES - MIN_SYMBOLS_IN_SENTENCES, leftSize))
                + MIN_SYMBOLS_IN_SENTENCES;
    }

    private static String getSentence(int sentenceSize, String firstWord) {
        StringBuilder sentence = new StringBuilder();
        String currentWord = firstWord;
        sentence.append(currentWord);
        sentenceSize -= currentWord.length();
        addSymbolsIfNecessary(sentence, sentenceSize);
        while (sentenceSize > 0) {
            currentWord = generateWord(getRandomLetter(), sentenceSize);
            sentenceSize -= currentWord.length();
            sentence.append(currentWord);
            addSymbolsIfNecessary(sentence, sentenceSize);
        }
        sentence.append(getRandomSentenceEnding()).append(' ');
        return sentence.toString();
    }


    private static void addSymbolsIfNecessary(StringBuilder sentence, int leftSize) {
        if (leftSize > 3) {
            if (getRandomBoolean()) {
                sentence.append(',');
            }
            sentence.append(' ');
            return;
        }
        if (leftSize == 2) {
           sentence.append(' ');
            return;
        }
        if (leftSize == 1) {
            sentence.append(getRandomLetter());
        }
    }
}
