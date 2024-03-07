package ksr1.ksrproject1.DataOperations;

import ksr1.ksrproject1.Article;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class DataExtarctor {
    private ArrayList<Article> articles;
    private int numberOfArticles;

    public DataExtarctor() {
        articles = new ArrayList<>();
        numberOfArticles = 0;
    }

    public void incrementArticlesCount() {
        numberOfArticles++;
    }

    public int getArticlesCount() {
        return numberOfArticles;
    }

    public void readFromFile() {
        String title = "";
        String content = "";
        String place = "";
        Article currentArticle = new Article("", "", "");
        boolean flag;
        try {
            String bufferLine;
            FileReader fileReader = new FileReader("src/main/resources/documents/reut2-000.sgm");
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            while ((bufferLine = bufferedReader.readLine()) != null) {
                if (bufferLine.contains("<REUTERS ")) {
                    currentArticle = new Article("", "", "");
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
                    currentArticle.setTitle(title);
                }
                if (bufferLine.contains("<PLACES>")) {
                    int znacznikPoc = bufferLine.indexOf("<PLACES>");
                    int znacznikKon = bufferLine.indexOf("</PLACES>");
                    place = bufferLine.substring(znacznikPoc + 8, znacznikKon);
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
                    currentArticle.setBody(content);
                }

                if (bufferLine.contains("</REUTERS>")) {
                    if (currentArticle.getBody() != null && currentArticle.getPlace() != "") {
                        articles.add(currentArticle);
                        incrementArticlesCount();
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
        displayArticles();
    }

    private boolean isValidPlace(String place) {
        return place.equals("<D>west-germany</D>") ||
                place.equals("<D>japan</D>") ||
                place.equals("<D>usa</D>") ||
                place.equals("<D>france</D>") ||
                place.equals("<D>uk</D>") ||
                place.equals("<D>canada</D>");
    }

    public void displayArticles() {
        for (Article article : articles) {
            System.out.println(article.toString());
        }
    }
}
