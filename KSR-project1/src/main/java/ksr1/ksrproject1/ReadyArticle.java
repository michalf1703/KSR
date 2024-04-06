package ksr1.ksrproject1;

import java.util.ArrayList;
import java.util.Collections;

import java.util.ArrayList;

public class ReadyArticle {
    private String title;
    private ArrayList<String> words;

    public ReadyArticle(Article article) {
        this.words = new ArrayList<>();
        this.title = article.getTitle();
        extractWords(article.getTopic());
        extractWords(article.getTitle());
        //extractWords(article.getPlace());
        extractWords(article.getDateline());
        extractWords(article.getBody());
    }

    private void extractWords(String text) {
        String[] wordsArray = text.split("\\s+");
        for (String word : wordsArray) {
            // Usuń znaki inne niż litery i cyfry, a następnie zamień na małe litery
            word = word.replaceAll("[^a-zA-Z0-9]", "").toLowerCase();
            if (!word.isEmpty()) {
                words.add(word);
            }
        }
    }


    public ArrayList<String> getTitle() {
        return extractWordsFromTitle(this.title);
    }

    private ArrayList<String> extractWordsFromTitle(String title) {
        ArrayList<String> titleWords = new ArrayList<>();
        String[] wordsArray = title.split("\\s+");
        for (String word : wordsArray) {
            word = word.replaceAll("[^a-zA-Z0-9]", "");
            if (!word.isEmpty()) {
                titleWords.add(word);
            }
        }
        return titleWords;
    }


    public ArrayList<String> getWords() {
        return words;
    }

    public void setWords(ArrayList<String> words) {
        this.words = words;
    }

    @Override
    public String toString() {
        return "ReadyArticle" + words;
    }
}
