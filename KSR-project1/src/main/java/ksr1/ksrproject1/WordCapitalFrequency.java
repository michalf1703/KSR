package ksr1.ksrproject1;

import java.util.ArrayList;
import java.util.List;

public class WordCapitalFrequency {

    public double calculateWCapital(List<String> words) {
        int capitalWordsCount = countCapitalWords(words);
        int totalWordsCount = countTotalWords(words);

        return totalWordsCount != 0 ? (double) capitalWordsCount / totalWordsCount : 0;
    }

    private int countCapitalWords(List<String> words) {
        int count = 0;
        boolean isFirstWord = true;
        for (String word : words) {
            if (!isFirstWord && Character.isUpperCase(word.charAt(0))) {
                count++;
            }
            if (word.endsWith(".")) {
                isFirstWord = true;
            } else {
                isFirstWord = false;
            }
        }
        return count;
    }

    private int countTotalWords(List<String> words) {
        return words.size();
    }

    // Przykładowe użycie
    public static void main(String[] args) {
        List<String> words = new ArrayList<>();
        words.add("To");
        words.add("jest");
        words.add("przykładowy");
        words.add("tekst");
        words.add("z");
        words.add("kilku");
        words.add("zdań");
        words.add("Zaczynamy");
        words.add("Nowe");
        words.add("Zdanie");
        words.add("Kolejne");
        words.add("zdanie");
        words.add("Rozpoczynające");
        words.add("się");
        words.add("od");
        words.add("dużej");
        words.add("litery");

        WordCapitalFrequency wordCapitalFrequency = new WordCapitalFrequency();
        double wCapital = wordCapitalFrequency.calculateWCapital(words);
        System.out.println("Wartość cechy wCapital: " + wCapital);
    }
}
