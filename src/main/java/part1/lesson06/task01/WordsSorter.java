package part1.lesson06.task01;

import java.io.*;
import java.util.*;

/**
 * The class extracts words from one file, sorts them and puts to result file
 */

class WordsSorter {

    private static final String INPUT_FILE = System.getProperty("input_file","input.txt");
    private static final String RESULT_FILE = System.getProperty("result_file", "result.txt");

    public static void main(String[] args) {
        transformText(INPUT_FILE, RESULT_FILE);
    }

    static void transformText(String inputFile, String outputFile) {
        List<String> words = readFileToArray(inputFile);
        Collections.sort(words);
        writeWordsToFile(words, outputFile);
    }

    static private List<String> readFileToArray(String path) {
        Set<String> words = new HashSet<>();
        try (BufferedInputStream inputStream = new BufferedInputStream(new FileInputStream(path))) {
            while (inputStream.available() > 0) {
                StringBuilder word = new StringBuilder();
                int symbol = inputStream.read();
                while (symbolIsLetter((char)symbol)) {
                    word.append((char)symbol);
                    symbol = inputStream.read();
                }
                if (word.length() > 0) {
                    words.add(word.toString().toLowerCase());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ArrayList<>(words);
    }

    private static boolean symbolIsLetter(char symbol) {
        return symbol >= 'a' && symbol <= 'z'
                || symbol >= 'A' && symbol <= 'Z'
                || symbol >= 'а' && symbol <= 'я'
                || symbol >= 'А' && symbol <= 'Я';
    }

    private static void writeWordsToFile(List<String> words, String path) {
        try (BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(path))) {
            for (String word: words) {
                byte[] bytes = (word + " ").getBytes();
                outputStream.write(bytes, 0, bytes.length);
                outputStream.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
