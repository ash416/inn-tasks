package part1.lesson06.task01;

import java.io.*;
import java.util.*;

/**
 * The class extracts words from one file, sorts them and puts to result file
 */

class WordsSorter {
    public static void main(String[] args) {
        WordsSorter sorter = new WordsSorter();
        sorter.transformText("input.txt", "result.txt");
    }

    void transformText(String inputFile, String outputFile) {
        List<String> words = readFileToArray(inputFile);
        Collections.sort(words);
        writeWordsToFile(words, outputFile);
    }

    private List<String> readFileToArray(String path) {
        Set<String> words = new HashSet<>();
        try (BufferedInputStream inputStream = new BufferedInputStream(new FileInputStream(path))) {
            while (inputStream.available() > 0) {
                StringBuilder word = new StringBuilder();
                int letter = inputStream.read();
                while ((letter > 64 && letter < 91 || letter > 96 && letter < 123 || letter > 159 && letter < 223) && inputStream.available() > 0) {
                    word.append((char)letter);
                    letter = inputStream.read();
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

    private void writeWordsToFile(List<String> words, String path) {
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
