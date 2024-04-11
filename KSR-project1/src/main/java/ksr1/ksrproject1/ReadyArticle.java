package ksr1.ksrproject1;

import java.util.ArrayList;
import java.util.Collections;

import java.util.ArrayList;
public class ReadyArticle {
    private String title;
    private ArrayList<String> words;
    private String place;
    private double WCapital;

    public double getWCapital() {
        return WCapital;
    }

    public void setWCapital(double WCapital) {
        this.WCapital = WCapital;
    }

    public ReadyArticle(Article article) {
        this.words = new ArrayList<>();
        this.title = article.getTitle();
        this.place = article.getPlace();
        this.WCapital = 0;
        extractWords(article.getTopic());
        extractWords(article.getTitle());
        extractWords(article.getPeople());
        extractWords(article.getExchange());
        extractWords(article.getDateline());
        extractWords(article.getBody());
    }

    public String getPlace() {
        return place;
    }

    private void extractWords(String text) {
        String[] wordsArray = text.split("\\s+");
        boolean isWestDetected = false;
        boolean isNorthDetected = false;
        boolean isSouthDetected = false;

        for (int i = 0; i < wordsArray.length; i++) {
            String word = wordsArray[i];
            word = word.replaceAll("[^a-zA-Z]", "").toLowerCase();
            if (!word.isEmpty()) {

                if (word.equals("west")) {
                    isWestDetected = true;
                    continue;
                } else if (isWestDetected && word.equals("germany")) {
                    words.add("west-germany");
                    isWestDetected = false;
                    continue;
                }

                if (word.equals("north")) {
                    isNorthDetected = true;
                    continue;
                } else if (isNorthDetected && word.equals("america")) {
                    words.add("north-america");
                    isNorthDetected = false;
                    continue;
                }

                if (word.equals("south")) {
                    isSouthDetected = true;
                    continue;
                } else if (isSouthDetected && word.equals("america")) {
                    words.add("south-america");
                    isSouthDetected = false;
                    continue;
                }

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
            word = word.replaceAll("[^a-zA-Z0-9]", "").toLowerCase();
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
        return "" + words;
    }
}
