package ksr1.ksrproject1.FeaturesEx;

import ksr1.ksrproject1.DataOperations.DictionaryLoader;

import java.util.ArrayList;
import java.util.List;

public class KeywordFrequency {

    private List<String> keyWordsList;
    DictionaryLoader dictionaryLoader = new DictionaryLoader();

    public KeywordFrequency() {
        this.keyWordsList = dictionaryLoader.loadWordsList("src/main/resources/ dictionaries/keyWords.txt");
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


}

