package ksr1.ksrproject1;

public class Article {

    private String title;
    private String place;
    private String body;
    private String dateline;
    private String topic;
    private String exchange;
    private String people;


    public String getExchange() {
        return exchange;
    }

    public void setExchange(String exchange) {
        this.exchange = exchange;
    }

    public String getPeople() {
        return people;
    }

    public void setPeople(String people) {
        this.people = people;
    }

    public Article(String topic, String title, String exchange, String people, String dateline, String place, String body){
        this.topic = topic;
        this.title = title;
        this.exchange = exchange;
        this.people = people;
        this.dateline = dateline;
        this.place = place;
        this.body = body;
    }
    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }
    public String getDateline() {
        return dateline;
    }

    public void setDateline(String dateline) {
        this.dateline = dateline;
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
                "topic='" + topic + '\'' +
                "| title='" + title + '\'' +
                "| place='" + place + '\'' +
                "| dateline='" + dateline + '\'' +
                "| body='" + body + '\'' +
                ')';
    }
}