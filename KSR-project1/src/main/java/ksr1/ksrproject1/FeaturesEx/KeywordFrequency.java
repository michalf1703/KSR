package ksr1.ksrproject1.FeaturesEx;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class KeywordFrequency {

    public String keyWordsPath = "src/main/resources/ dictionaries/keyWords.txt";
    private ArrayList<String> keyWordsList;

    public KeywordFrequency() {
        this.keyWordsList = loadKeyWordsList();
    }

    public double calculateFKey(ArrayList<String> words) {

        // Obliczenie liczby słów kluczowych w tekście
        int nKey = countKeywords(words);

        // Obliczenie liczby wszystkich słów w tekście po usunięciu słów ze stoplisty
        int nTotal = countTotalWords(words);

        // Obliczenie wartości cechy fkey
        return nTotal != 0 ? (double) nKey / nTotal : 0;
    }

    // Metoda zliczająca słowa kluczowe w tekście
    public int countKeywords(ArrayList<String> words) {
        return (int) words.stream()
                .filter(keyWordsList::contains)
                .count();
    }

    // Metoda obliczająca liczbę wszystkich słów w tekście po usunięciu słów ze stoplisty
    private int countTotalWords(ArrayList<String> words) {
        return words.size();
    }

    // Metoda wczytująca listę słów kluczowych z pliku
    private ArrayList<String> loadKeyWordsList() {
        ArrayList<String> keyList = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(keyWordsPath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                keyList.add(line.trim());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return keyList;
    }
}

