package ksr1.ksrproject1;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class Article {
    private int id;
    private LocalDateTime date;
    private String title;
    private String place;
    private String body;

    public ArrayList<String> getTags() {
        return tags;
    }

    private ArrayList<String> tags;

    public Article(int id, LocalDateTime date, String title, String place, String body){
        this.id = id;
        this.date = date;
        this.title = title;
        this.place = place;
        this.body = body;
        this.tags = new ArrayList<>();
    }


    public void setTags(ArrayList<String> tags) {
        this.tags = tags;
    }

    public int getId() {
        return id;
    }

    public LocalDateTime getDate() {
        return date;
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

    public void setDate(LocalDateTime date) {
        this.date = date;
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
                ", date=" + date +
                ", title='" + title + '\'' +
                ", place='" + place + '\'' +
                ", body='" + body + '\'' +
                ')';
    }
}