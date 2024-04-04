package ksr1.ksrproject1.DataOperations;

import java.io.*;
import java.util.*;

public class StoplistFilter {
    private Set<String> stoplist;

    public StoplistFilter(String stoplistFilePath) {
        this.stoplist = loadStoplist(stoplistFilePath);
    }

    private Set<String> loadStoplist(String stoplistFilePath) {
        Set<String> stoplist = new HashSet<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(stoplistFilePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                stoplist.add(line.trim().toLowerCase());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stoplist;
    }

    public String filter(String text) {
        StringBuilder filteredText = new StringBuilder();
        StringTokenizer tokenizer = new StringTokenizer(text);
        while (tokenizer.hasMoreTokens()) {
            String word = tokenizer.nextToken().toLowerCase();
            if (!stoplist.contains(word)) {
                filteredText.append(word).append(" ");
            }
        }
        return filteredText.toString().trim();
    }

    public void filterArticles(List<String> articles) {
        for (int i = 0; i < articles.size(); i++) {
            articles.set(i, filter(articles.get(i)));
        }
    }
    public int countFilteredWords(String text) {
        int count = 0;
        StringTokenizer tokenizer = new StringTokenizer(text);
        while (tokenizer.hasMoreTokens()) {
            String word = tokenizer.nextToken().toLowerCase();
            if (stoplist.contains(word)) {
                count++;
            }
        }
        return count;
    }

}
