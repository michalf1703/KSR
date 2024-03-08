package ksr1.ksrproject1;

import java.util.ArrayList;
import java.util.Collections;

public class ReadyArticle {
    private ArrayList<String> words;

    public ReadyArticle(Article article) {
        this.words = new ArrayList<>();
        extractWords(article.getTopic());
        extractWords(article.getTitle());
        extractWords(article.getPlace());
        extractWords(article.getDateline());
        extractWords(article.getBody());
    }

    private void extractWords(String text) {
        String[] wordsArray = text.split("\\s+");
        Collections.addAll(words, wordsArray);
    }

    public ArrayList<String> getWords() {
        return words;
    }

    public void setWords(ArrayList<String> words) {
        this.words = words;
    }

    @Override
    public String toString() {
        return "ReadyArticle" +
                words;
    }
}
