package ksr1.ksrproject1.FeaturesEx;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class MostCommonCurrency {

    public String currencyPath = "src/main/resources/ dictionaries/currency.txt";
    private List<String> currencyList;

    public MostCommonCurrency() {
        this.currencyList = loadCurrencyList();
    }

    public String calculateMostCommonCurrency(ArrayList<String> words) {
        Map<String, Integer> currencyOccurrences = countCurrencyOccurrences(words);
        return getMostCommonCurrency(currencyOccurrences);
    }

    private Map<String, Integer> countCurrencyOccurrences(ArrayList<String> words) {
        Map<String, Integer> currencyOccurrences = new HashMap<>();
        for (String word : words) {
            if (currencyList.contains(word)) {
                currencyOccurrences.put(word, currencyOccurrences.getOrDefault(word, 0) + 1);
            }
        }
        return currencyOccurrences;
    }

    private String getMostCommonCurrency(Map<String, Integer> currencyOccurrences) {
        int maxOccurrences = 0;
        String mostCommonCurrency = null;
        for (Map.Entry<String, Integer> entry : currencyOccurrences.entrySet()) {
            if (entry.getValue() > maxOccurrences) {
                maxOccurrences = entry.getValue();
                mostCommonCurrency = entry.getKey();
            }
        }
        return mostCommonCurrency;
    }

    private List<String> loadCurrencyList() {
        List<String> currencyList = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(currencyPath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                currencyList.add(line.trim());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return currencyList;
    }
}

