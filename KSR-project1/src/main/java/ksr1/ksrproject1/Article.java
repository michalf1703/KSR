package ksr1.ksrproject1;

public class Article {

    private String title;
    private String place;
    private String body;


    public Article(String title, String place, String body){
        this.title = title;
        this.place = place;
        this.body = body;
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
                "title='" + title + '\'' +
                "| place='" + place + '\'' +
                "| body='" + body + '\'' +
                ')';
    }
}