package ksr1.ksrproject1.FeaturesEx;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MostCommonSurname {

    public String surnamePath = "src/main/resources/ dictionaries/people.txt";
    private List<String> surnameList;

    public MostCommonSurname() {
        this.surnameList = loadSurnameList();
    }

    public String calculateMostCommonSurname(ArrayList<String> words) {
        Map<String, Integer> surnameOccurrences = countSurnameOccurrences(words);
        return getMostCommonSurname(surnameOccurrences);
    }

    private Map<String, Integer> countSurnameOccurrences(ArrayList<String> words) {
        Map<String, Integer> surnameOccurrences = new HashMap<>();
        for (String word : words) {
            if (surnameList.contains(word)) {
                surnameOccurrences.put(word, surnameOccurrences.getOrDefault(word, 0) + 1);
            }
        }
        return surnameOccurrences;
    }

    private String getMostCommonSurname(Map<String, Integer> surnameOccurrences) {
        int maxOccurrences = 0;
        String mostCommonSurname = null;
        for (Map.Entry<String, Integer> entry : surnameOccurrences.entrySet()) {
            if (entry.getValue() > maxOccurrences) {
                maxOccurrences = entry.getValue();
                mostCommonSurname = entry.getKey();
            }
        }
        return mostCommonSurname;
    }

    private List<String> loadSurnameList() {
        List<String> continentList = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(surnamePath))) {
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
