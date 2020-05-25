package com.example.arnold.remindme;

public class RemindmeItem {

    Long id;
    String title;
    String body;



    public RemindmeItem(Long id, String title, String body) {
        // TODO Auto-generated constructor stub
        this.id = id;
        this.body = body;
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }



}
