package ksr1.ksrproject1.FeaturesEx;

import ksr1.ksrproject1.DataOperations.DictionaryLoader;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MostCommonExchange {
    DictionaryLoader dictionaryLoader = new DictionaryLoader();

    public String exchangePath = "src/main/resources/ dictionaries/exchanges.txt";
    private List<String> exchangeList;

    public MostCommonExchange() {
        this.exchangeList = dictionaryLoader.loadWordsList(exchangePath);
    }

    public String calculateMostCommonExchange(ArrayList<String> words) {
        Map<String, Integer> exchangeOccurrences = dictionaryLoader.countOccurrences(words,exchangeList);
        return dictionaryLoader.getMostCommon(exchangeOccurrences);
    }
}
