package ksr1.ksrproject1.DataOperations;

import ksr1.ksrproject1.Article;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class DataExtarctor {
    private ArrayList<Article> articles;
    private int numberOfArticles;

    public DataExtarctor(){
        articles = new ArrayList<>();
        numberOfArticles = 0;
    }

    public void incrementArtykulyCount() {
        numberOfArticles++;
    }

    public int getArticles() {
        return numberOfArticles;
    }

    public void readfromFile(String tag){
        tag = "<"+tag+">";
        ArrayList<String> tags;
        String title = "";
        String content = "";
        Article currentArticle = new Article(0, LocalDateTime.now(), "", "", "");

        boolean flag;
        try {
            String bufferLine;

            FileReader fileReader = new FileReader("src/main/resources/documents/reut2-021.sgm");
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            while ((bufferLine = bufferedReader.readLine()) != null){
                if(bufferLine.contains(tag)){
                    currentArticle = new Article(0, LocalDateTime.now(), "", "", "");
                    tags = new ArrayList<>();
                    do {
                        flag = true;
                        int znacznikPoc = bufferLine.indexOf("<");
                        int znacznikKon = bufferLine.indexOf(">");
                        if (znacznikKon > znacznikPoc) {
                            if(znacznikKon + 1 >= bufferLine.length()) {
                                flag = false;
                            }else {
                                bufferLine = bufferLine.substring(znacznikKon);
                            }
                        }
                        if(flag) {
                            znacznikPoc = bufferLine.indexOf("<");
                            znacznikKon = bufferLine.indexOf(">");
                            if (znacznikKon + 1 == znacznikPoc) {
                                bufferLine = bufferLine.substring(znacznikPoc);
                            } else {
                                tags.add(bufferLine.substring(znacznikKon + 1, znacznikPoc));
                                bufferLine = bufferLine.substring(znacznikPoc);
                            }
                        }
                    }while (flag);
                    currentArticle.setTags(tags);
                }
                if(bufferLine.contains("<TITLE>")){
                    int znacznikkon = bufferLine.indexOf(">");
                    int znacznikpoc;
                    bufferLine = bufferLine.substring(znacznikkon+1);
                    znacznikkon = bufferLine.indexOf(">");
                    if(znacznikkon + 1 != bufferLine.length()){
                        bufferLine = bufferLine.substring(znacznikkon+1);
                    }
                    if(bufferLine.contains("</TITLE>")){
                        znacznikpoc = bufferLine.indexOf("<");
                        title = bufferLine.substring(0,znacznikpoc);
                    }else {
                        while(!bufferLine.contains("</TITLE>")){
                            title = title + bufferLine + " ";
                            bufferLine = bufferedReader.readLine();
                        }
                        znacznikpoc = bufferLine.indexOf("<");
                        title = title + bufferLine.substring(0,znacznikpoc);
                    }
                    currentArticle.setTitle(title);
                }
                if(bufferLine.contains("<BODY>")){
                    int znacznikkon = bufferLine.indexOf("<BODY>");
                    int znacznikpoc;
                    bufferLine = bufferLine.substring(znacznikkon+6);
                    content = "";
                    while (!bufferLine.contains("</BODY>")){
                        content = content + bufferLine + " ";
                        bufferLine = bufferedReader.readLine();
                    }
                    znacznikpoc = bufferLine.indexOf("<");
                    content = content + bufferLine.substring(0,znacznikpoc);
                    currentArticle.setBody(content);
                }
                if(bufferLine.contains("</REUTERS>")){
                    if(currentArticle.getBody() != null){
                        articles.add(currentArticle);
                        incrementArtykulyCount();
                    }
                }
            }
            bufferedReader.close();
        }catch (FileNotFoundException e){
            System.out.println("Nie można otworzyć pliku");
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    private ArrayList<String> GetWordsFromSentence(String tekst){
        String[] pom;
        ArrayList<String> Rez = new ArrayList<>();
        pom = tekst.split(" ");
        Collections.addAll(Rez,pom);
        Rez.removeAll(Arrays.asList("", null));
        return Rez;
    }
}
