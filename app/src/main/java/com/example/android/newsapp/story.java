package com.example.android.newsapp;

public class story {
    private String title;
    private String section;
    private String webUrl;
    private String date;
    private String author;

    public story(String title, String section, String webUrl, String date, String author) {
        this.title = title;
        this.section = section;
        this.webUrl = webUrl;
        this.date = date;
        this.author = author;
    }

    public String getSection() {
        return section;
    }

    public String getWebUrl() {
        return webUrl;
    }

    public String getAuthor() {
        return author;
    }

    public String getTitle() {
        return title;
    }

    public String getDate() {
        return date;
    }


}




