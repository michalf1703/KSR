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
        int nKey = countKeywords(words);
        int nTotal = countTotalWords(words);
        return nTotal != 0 ? (double) nKey / nTotal : 0;
    }

    public int countKeywords(ArrayList<String> words) {
        return (int) words.stream()
                .filter(keyWordsList::contains)
                .count();
    }

    private int countTotalWords(ArrayList<String> words) {
        return words.size();
    }

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

