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

    public void readFromFile() { // Usunięcie parametru tag
        ArrayList<String> tags;
        String title = "";
        String content = "";
        String place = "";
        int id = 0;
        Article currentArticle = new Article(id, "", "", "");

        boolean flag;
        try {
            String bufferLine;
            FileReader fileReader = new FileReader("src/main/resources/documents/reut2-000.sgm");
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            while ((bufferLine = bufferedReader.readLine()) != null) {
                if (bufferLine.contains("<REUTERS ")) {
                    currentArticle = new Article(id, "", "", "");
                    tags = new ArrayList<>();
                    do {
                        flag = true;
                        id += 1;
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
                                tags.add(bufferLine.substring(znacznikKon + 1, znacznikPoc));
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
                    currentArticle.setPlace(place);
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
                    if (currentArticle.getBody() != null) {
                        articles.add(currentArticle);
                        incrementArticlesCount();
                        System.out.println(currentArticle.toString());
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

}
