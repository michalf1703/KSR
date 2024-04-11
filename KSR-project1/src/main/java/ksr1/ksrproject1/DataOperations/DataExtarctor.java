package ksr1.ksrproject1.DataOperations;

import ksr1.ksrproject1.Article;
import ksr1.ksrproject1.DataInstance;
import ksr1.ksrproject1.FeaturesEx.*;
import ksr1.ksrproject1.KNN;
import ksr1.ksrproject1.Metrics.IMetric;
import ksr1.ksrproject1.ReadyArticle;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class DataExtarctor {
    private ArrayList<ReadyArticle> readyArticles;
    DictionaryLoader dictionaryLoader = new DictionaryLoader();
    ArrayList<String> stopList = dictionaryLoader.loadStopList("src/main/resources/ dictionaries/stop_words.txt");
    private WCapitalFeature wCapitalFeature = new WCapitalFeature();

    private int numberOfArticles;

    public DataExtarctor() {
        this.readyArticles = new ArrayList<>();
        this.numberOfArticles = 0;
    }

    public void incrementArticlesCount() {
        numberOfArticles++;
    }

    public int getArticlesCount() {
        return numberOfArticles;
    }

    public ArrayList<ReadyArticle> readFromFile() {
        String title = "";
        String content = "";
        String place = "";
        String dateline = "";
        String topic = "";
        String exchange = "";
        //String people = "";
        Article currentArticle = new Article("", "","","", "", "", "");
        boolean flag;
        for (int i = 1; i <= 2; i++) {
            String numerPliku = String.format("%03d", i);
            String nazwaPliku = "src/main/resources/documents/reut2-" + numerPliku + ".sgm";
            try {
                String bufferLine;
                FileReader fileReader = new FileReader(nazwaPliku);
                BufferedReader bufferedReader = new BufferedReader(fileReader);
                while ((bufferLine = bufferedReader.readLine()) != null) {
                    if (bufferLine.contains("<REUTERS ")) {
                        currentArticle = new Article("", "", "","","", "", "");
                        do {
                            flag = true;
                            int znacznikPoc = bufferLine.indexOf("<");
                            int znacznikKon = bufferLine.indexOf(">");
                            if (znacznikKon > znacznikPoc) {
                                if (znacznikKon + 1 >= bufferLine.length()) {
                                    flag = false;
                                } else {
                                    bufferLine = bufferLine.substring(znacznikKon);
                                }
                            }
                            if (flag) {
                                znacznikPoc = bufferLine.indexOf("<");
                                znacznikKon = bufferLine.indexOf(">");
                                if (znacznikKon + 1 == znacznikPoc) {
                                    bufferLine = bufferLine.substring(znacznikPoc);
                                } else {
                                    bufferLine = bufferLine.substring(znacznikPoc);
                                }
                            }
                        } while (flag);
                    }
                    if (bufferLine.contains("<TOPICS>")) {
                        int znacznikPoc = bufferLine.indexOf("<TOPICS>");
                        int znacznikKon = bufferLine.indexOf("</TOPICS>");
                        topic = bufferLine.substring(znacznikPoc + 8, znacznikKon);
                        topic = topic.replaceAll("</?D>", "");
                        currentArticle.setTopic(topic);
                    }

                    if (bufferLine.contains("<TITLE>")) {
                        int znacznikPoc = bufferLine.indexOf("<TITLE>");
                        int znacznikKon = bufferLine.indexOf("</TITLE>");
                        if (znacznikKon != -1) {
                            title = bufferLine.substring(znacznikPoc + 7, znacznikKon);
                        } else {
                            StringBuilder titleBuilder = new StringBuilder();
                            titleBuilder.append(bufferLine.substring(znacznikPoc + 7));
                            while ((bufferLine = bufferedReader.readLine()) != null) {
                                znacznikKon = bufferLine.indexOf("</TITLE>");
                                if (znacznikKon != -1) {
                                    titleBuilder.append(bufferLine, 0, znacznikKon);
                                    title = titleBuilder.toString();
                                    break;
                                } else {
                                    titleBuilder.append(bufferLine);
                                }
                            }
                        }
                        title = title.replaceAll("&lt;", "<");
                        currentArticle.setTitle(title);
                    }
                    if (bufferLine.contains("<EXCHANGES>")) {
                        int znacznikPoc = bufferLine.indexOf("<EXCHANGES>");
                        int znacznikKon = bufferLine.indexOf("</EXCHANGES>");
                        String exchanges = bufferLine.substring(znacznikPoc + 11, znacznikKon);
                        exchanges = exchanges.replaceAll("</?D>", "");
                        currentArticle.setExchange(exchanges);
                    }
                    if (bufferLine.contains("<PEOPLE>")) {
                        int znacznikPoc = bufferLine.indexOf("<PEOPLE>");
                        int znacznikKon = bufferLine.indexOf("</PEOPLE>");
                        String people = bufferLine.substring(znacznikPoc + 8, znacznikKon);
                        people = people.replaceAll("</?D>", "");
                        currentArticle.setPeople(people);
                    }


                    if (bufferLine.contains("<DATELINE>")) {
                        int znacznikkon = bufferLine.indexOf("<DATELINE>");
                        int znacznikpoc;
                        bufferLine = bufferLine.substring(znacznikkon + 10);
                        dateline = "";
                        while (!bufferLine.contains("</DATELINE>")) {
                            dateline = dateline + bufferLine + " ";
                            bufferLine = bufferedReader.readLine();
                        }
                        znacznikpoc = bufferLine.indexOf("<");
                        dateline = dateline + bufferLine.substring(0, znacznikpoc);
                        currentArticle.setDateline(dateline);
                    }

                    if (bufferLine.contains("<PLACES>")) {
                        int znacznikPoc = bufferLine.indexOf("<PLACES>");
                        int znacznikKon = bufferLine.indexOf("</PLACES>");
                        place = bufferLine.substring(znacznikPoc + 8, znacznikKon);
                        place = place.replaceAll("</?D>", "");
                        if (isValidPlace(place)) {
                            currentArticle.setPlace(place);
                        }
                    }

                    if (bufferLine.contains("<BODY>")) {
                        int znacznikkon = bufferLine.indexOf("<BODY>");
                        int znacznikpoc;
                        bufferLine = bufferLine.substring(znacznikkon + 6);
                        content = "";
                        while (!bufferLine.contains("</BODY>")) {
                            content = content + bufferLine + " ";
                            bufferLine = bufferedReader.readLine();
                        }
                        znacznikpoc = bufferLine.indexOf("<");
                        content = content + bufferLine.substring(0, znacznikpoc);
                        content = content.replaceAll("&lt;", "<");
                        content = content.replaceAll("Reuter &#3;", "");
                        content = content.replaceAll("REUTER &#3;", "");
                        currentArticle.setBody(content);
                    }

                    if (bufferLine.contains("<TEXT TYPE=\"UNPROC\">")) {
                        StringBuilder bodyBuilder = new StringBuilder();
                        boolean isReadingBody = true;
                        while (isReadingBody && (bufferLine = bufferedReader.readLine()) != null) {
                            if (bufferLine.contains("</TEXT>")) {
                                int znacznikKon = bufferLine.indexOf("</TEXT>");
                                bodyBuilder.append(bufferLine, 0, znacznikKon);
                                isReadingBody = false;
                            } else {
                                bodyBuilder.append(bufferLine).append("\n");
                            }
                        }
                        content = bodyBuilder.toString();
                        content = content.replaceAll("&lt;", "<");
                        content = content.replaceAll("Reuter &#3;", "");
                        content = content.replaceAll("REUTER &#3;", "");
                        content = content.trim();
                        currentArticle.setBody(content);
                    }
                    if (bufferLine.contains("<TEXT TYPE=\"BRIEF\">")) {
                        StringBuilder bodyBuilder = new StringBuilder();
                        boolean isReadingBody = true;
                        while (isReadingBody && (bufferLine = bufferedReader.readLine()) != null) {
                            if (bufferLine.contains("</TEXT>")) {
                                int znacznikKon = bufferLine.indexOf("</TEXT>");
                                bodyBuilder.append(bufferLine, 0, znacznikKon);
                                isReadingBody = false;
                            } else {
                                bodyBuilder.append(bufferLine).append("\n");
                            }
                        }
                        content = bodyBuilder.toString();
                        content = content.replaceAll("&lt;", "<");
                        content = content.trim();
                        currentArticle.setBody(content);
                    }


                    if (bufferLine.contains("</REUTERS>")) {
                        if (currentArticle.getBody() != null && currentArticle.getPlace() != "") {
                            ReadyArticle readyArticle = new ReadyArticle(currentArticle);
                            readyArticles.add(readyArticle);
                            incrementArticlesCount();
                            ArrayList<String> bodyList = currentArticle.extractWordsFromBody();
                            ArrayList<String> words = dictionaryLoader.removeWordsContainedInStopList(bodyList, stopList);
                            String body = currentArticle.concatenateWords(words);
                            double wynik = wCapitalFeature.calculateWCapital(body);
                            readyArticle.setWCapital(wynik);
                        } else {
                            currentArticle = null;
                        }
                    }
                }

                bufferedReader.close();
            } catch (FileNotFoundException e) {
                System.out.println("Nie można otworzyć pliku");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return readyArticles;
    }

    private boolean isValidPlace(String place) {
        return place.equals("west-germany") ||
                place.equals("japan") ||
                place.equals("usa") ||
                place.equals("france") ||
                place.equals("uk") ||
                place.equals("canada");
    }

}