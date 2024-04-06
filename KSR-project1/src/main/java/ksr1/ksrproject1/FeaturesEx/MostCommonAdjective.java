package ksr1.ksrproject1.FeaturesEx;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MostCommonAdjective {
    public String adjectivePath = "src/main/resources/ dictionaries/adjective.txt";
    private List<String> adjectiveList;

    public MostCommonAdjective() {
        this.adjectiveList = loadAdjectiveList();
    }

    public String calculateMostCommonAdjective(ArrayList<String> words) {
        Map<String, Integer> adjectivesOccurrences = countAdjectiveOccurrences(words);
        return getMostCommonAdjective(adjectivesOccurrences);
    }

    private Map<String, Integer> countAdjectiveOccurrences(ArrayList<String> words) {
        Map<String, Integer> adjectiveOccurrences = new HashMap<>();
        for (String word : words) {
            if (adjectiveList.contains(word)) {
                adjectiveOccurrences.put(word, adjectiveOccurrences.getOrDefault(word, 0) + 1);
            }
        }
        return adjectiveOccurrences;
    }

    private String getMostCommonAdjective(Map<String, Integer> countryOccurrences) {
        int maxOccurrences = 0;
        String mostCommonAdjective = null;
        for (Map.Entry<String, Integer> entry : countryOccurrences.entrySet()) {
            if (entry.getValue() > maxOccurrences) {
                maxOccurrences = entry.getValue();
                mostCommonAdjective = entry.getKey();
            }
        }
        return mostCommonAdjective;
    }

    private List<String> loadAdjectiveList() {
        List<String> adjectiveList = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(adjectivePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                adjectiveList.add(line.trim());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return adjectiveList;
    }
}
