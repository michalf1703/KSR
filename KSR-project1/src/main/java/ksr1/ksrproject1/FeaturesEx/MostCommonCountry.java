package ksr1.ksrproject1.FeaturesEx;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MostCommonCountry {
    public String countryPath = "src/main/resources/ dictionaries/country.txt";
    private List<String> countryList;

    public MostCommonCountry() {
        this.countryList = loadCountryList();
    }

    public String calculateMostCommonCountry(ArrayList<String> words) {
        Map<String, Integer> currencyOccurrences = countCountryOccurrences(words);
        return getMostCommonCountry(currencyOccurrences);
    }

    private Map<String, Integer> countCountryOccurrences(ArrayList<String> words) {
        Map<String, Integer> countryOccurrences = new HashMap<>();
        for (String word : words) {
            if (countryList.contains(word)) {
                countryOccurrences.put(word, countryOccurrences.getOrDefault(word, 0) + 1);
            }
        }
        return countryOccurrences;
    }

    private String getMostCommonCountry(Map<String, Integer> countryOccurrences) {
        int maxOccurrences = 0;
        String mostCommonCountry = null;
        for (Map.Entry<String, Integer> entry : countryOccurrences.entrySet()) {
            if (entry.getValue() > maxOccurrences) {
                maxOccurrences = entry.getValue();
                mostCommonCountry = entry.getKey();
            }
        }
        return mostCommonCountry;
    }

    private List<String> loadCountryList() {
        List<String> countryList = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(countryPath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                countryList.add(line.trim());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return countryList;
    }
}
