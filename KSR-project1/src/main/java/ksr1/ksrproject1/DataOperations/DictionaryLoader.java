package ksr1.ksrproject1.DataOperations;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class DictionaryLoader {
    public List<String> loadWordsList(String filePath) {
        List<String> keyList = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                keyList.add(line.trim());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return keyList;
    }

    public ArrayList<String> removeWordsContainedInStopList(ArrayList<String> words, ArrayList<String> stopList) {
        return words.stream()
                .filter(word -> !stopList.contains(word))
                .filter(word -> !word.matches("\\d+([.,]\\d+)?"))
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public ArrayList<String> loadStopList(String filePath) {
        ArrayList<String> stopList = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String word = line.trim();
                stopList.add(word);
                stopList.add(Character.toUpperCase(word.charAt(0)) + word.substring(1).toLowerCase());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stopList;
    }
    public Map<String, Integer> countOccurrences(ArrayList<String> words,List<String> list) {
        Map<String, Integer> occurrences = new HashMap<>();
        for (String word : words) {
            if (list.contains(word)) {
                occurrences.put(word, occurrences.getOrDefault(word, 0) + 1);
            }
        }
        return occurrences;
    }

    public String getMostCommon(Map<String, Integer> occurrences) {
        int maxOccurrences = 0;
        String mostCommon = null;
        for (Map.Entry<String, Integer> entry : occurrences.entrySet()) {
            if (entry.getValue() > maxOccurrences) {
                maxOccurrences = entry.getValue();
                mostCommon = entry.getKey();
            }
        }
        return mostCommon;
    }

}
