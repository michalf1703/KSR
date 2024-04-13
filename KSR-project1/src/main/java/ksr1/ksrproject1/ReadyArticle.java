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
        text = text.replace("U.S.", "USA");
        text = text.replace("Britain", "uk");
        text = text.replace("britain", "uk");
        text = text.replace("Germany's", "germany");
        text = text.replace("$", "dollar");
        text = text.replace("dlr", "dollar");
        text = text.replace("dlrs", "dollar");
        text = text.replace("pound's", "stg");


        String[] wordsArray = text.split("\\s+");
        boolean isWestDetected = false;
        boolean isNorthDetected = false;
        boolean isSouthDetected = false;

        for (int i = 0; i < wordsArray.length; i++) {
            String word = wordsArray[i];
            word = word.replaceAll("[^a-zA-Z]", "").toLowerCase();
            if (!word.isEmpty()) {

                if (word.equals("west")) {
                    if (i < wordsArray.length - 1 && !wordsArray[i + 1].replaceAll("[^a-zA-Z]", "").toLowerCase().equals("germany")) {
                        words.add("west");
                    }
                    isWestDetected = true;
                    continue;
                } else if (isWestDetected && word.equals("germany")) {
                    words.add("west-germany");
                    isWestDetected = false;
                    continue;
                }

                if (word.equals("west")) {
                    if (i < wordsArray.length - 1 && !wordsArray[i + 1].replaceAll("[^a-zA-Z]", "").toLowerCase().equals("german")) {
                        words.add("west");
                    }
                    isWestDetected = true;
                    continue;
                } else if (isWestDetected && word.equals("german")) {
                    words.add("west-german");
                    isWestDetected = false;
                    continue;
                }

                if (word.equals("united")) {
                    if (i < wordsArray.length - 1 && !wordsArray[i + 1].replaceAll("[^a-zA-Z]", "").toLowerCase().equals("states")) {
                        words.add("united");
                    }
                    isWestDetected = true;
                    continue;
                } else if (isWestDetected && word.equals("states")) {
                    words.add("usa");
                    isWestDetected = false;
                    continue;
                }
                if (word.equals("canadian")) {
                    if (i < wordsArray.length - 1) {
                        String nextWord = wordsArray[i + 1].replaceAll("[^a-zA-Z]", "").toLowerCase();
                        if (!nextWord.equals("dollar") && !nextWord.equals("dlrs") && !nextWord.equals("dlr") && !nextWord.equals("dollars")) {
                            words.add("canadian");
                        }
                    }
                    isWestDetected = true;
                    continue;
                } else if (isWestDetected && (word.equals("dollar") || word.equals("dlrs") || word.equals("dlr") || word.equals("dollars"))) {
                    words.add("canadian-dollar");
                    isWestDetected = false;
                    continue;
                }

                if (word.equals("north")) {
                    if (i < wordsArray.length - 1 && !wordsArray[i + 1].replaceAll("[^a-zA-Z]", "").toLowerCase().equals("america")) {
                        words.add("north");
                    }
                    isNorthDetected = true;
                    continue;
                } else if (isNorthDetected && word.equals("america")) {
                    words.add("north-america");
                    isNorthDetected = false;
                    continue;
                }

                if (word.equals("south")) {
                    if (i < wordsArray.length - 1 && !wordsArray[i + 1].replaceAll("[^a-zA-Z]", "").toLowerCase().equals("america")) {
                        words.add("south");
                    }
                    isSouthDetected = true;
                    continue;
                } else if (isSouthDetected && word.equals("america")) {
                    words.add("south-america");
                    isSouthDetected = false;
                    continue;
                }
                if (word.equals("sterling")) {
                    words.add("stg");
                    continue;
                }
                if (word.equals("pound") ) {
                    words.add("stg");
                    continue;
                }
                if (word.equals("dmk") ) {
                    words.add("mark");
                    continue;
                }
                if (word.equals("marks") ) {
                    words.add("mark");
                    continue;
                }
                if (word.equals("ffr") ) {
                    words.add("franc");
                    continue;
                }
                if (word.equals("deutsche") ) {
                    words.add("west-german");
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
