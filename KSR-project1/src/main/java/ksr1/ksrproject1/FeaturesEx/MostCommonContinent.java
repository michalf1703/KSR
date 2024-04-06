package ksr1.ksrproject1.FeaturesEx;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MostCommonContinent {
    public String countryPath = "src/main/resources/ dictionaries/continents.txt";
    private List<String> continentList;

    public MostCommonContinent() {
        this.continentList = loadContinentList();
    }

    public String calculateMostCommonContinent(ArrayList<String> words) {
        Map<String, Integer> continentOccurrences = countContinentOccurrences(words);
        return getMostCommonContinent(continentOccurrences);
    }

    private Map<String, Integer> countContinentOccurrences(ArrayList<String> words) {
        Map<String, Integer> continentOccurrences = new HashMap<>();
        for (String word : words) {
            if (continentList.contains(word)) {
                continentOccurrences.put(word, continentOccurrences.getOrDefault(word, 0) + 1);
            }
        }
        return continentOccurrences;
    }

    private String getMostCommonContinent(Map<String, Integer> countryOccurrences) {
        int maxOccurrences = 0;
        String mostCommonContinent = null;
        for (Map.Entry<String, Integer> entry : countryOccurrences.entrySet()) {
            if (entry.getValue() > maxOccurrences) {
                maxOccurrences = entry.getValue();
                mostCommonContinent = entry.getKey();
            }
        }
        return mostCommonContinent;
    }

    private List<String> loadContinentList() {
        List<String> continentList = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(countryPath))) {
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
