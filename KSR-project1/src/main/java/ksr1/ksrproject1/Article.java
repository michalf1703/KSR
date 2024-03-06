package ksr1.ksrproject1;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class Article {
    private int id;
    private String title;
    private String place;
    private String body;


    public Article(int id, String title, String place, String body){
        this.id = id;
        this.title = title;
        this.place = place;
        this.body = body;
    }


    public int getId() {
        return id;
    }


    public String getTitle() {
        return title;
    }

    public String getPlace() {
        return place;
    }

    public String getBody() {
        return body;
    }

    public void setId(int id) {
        this.id = id;
    }


    public void setTitle(String title) {
        this.title = title;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public void setBody(String body) {
        this.body = body;
    }

    @Override
    public String toString() {
        return "Article(" +
                "id='" + id + '\'' +
                "| title='" + title + '\'' +
                "| place='" + place + '\'' +
                "| body='" + body + '\'' +
                ')';
    }
}