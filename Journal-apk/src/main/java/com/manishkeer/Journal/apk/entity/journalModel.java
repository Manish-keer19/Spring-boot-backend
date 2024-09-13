package com.manishkeer.Journal.apk.entity;

public class journalModel {

    private String id;
    // genrate the the id title and content
    private String title;
    private String content;

    // create getter and setter

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}
