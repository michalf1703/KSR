package ksr1.ksrproject1.FeaturesEx;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MostCommonExchange {

    public String exchangePath = "src/main/resources/ dictionaries/exchanges.txt";
    private List<String> exchangeList;

    public MostCommonExchange() {
        this.exchangeList = loadExchangeList();
    }

    public String calculateMostCommonExchange(ArrayList<String> words) {
        Map<String, Integer> exchangeOccurrences = countExchangeOccurrences(words);
        return getMostCommonExchange(exchangeOccurrences);
    }

    private Map<String, Integer> countExchangeOccurrences(ArrayList<String> words) {
        Map<String, Integer> exchangeOccurrences = new HashMap<>();
        for (String word : words) {
            if (exchangeList.contains(word)) {
                exchangeOccurrences.put(word, exchangeOccurrences.getOrDefault(word, 0) + 1);
            }
        }
        return exchangeOccurrences;
    }

    private String getMostCommonExchange(Map<String, Integer> exchangeOccurrences) {
        int maxOccurrences = 0;
        String mostCommonExchange = null;
        for (Map.Entry<String, Integer> entry : exchangeOccurrences.entrySet()) {
            if (entry.getValue() > maxOccurrences) {
                maxOccurrences = entry.getValue();
                mostCommonExchange = entry.getKey();
            }
        }
        return mostCommonExchange;
    }

    private List<String> loadExchangeList() {
        List<String> continentList = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(exchangePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                continentList.add(line.trim());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return continentList;
    }
}
