package com.example.pavan.moviesapp.NetworkActivity;

/**
 * Created by pavan on 4/2/2016.
 */
public class MovieReviewsResponse {

    private long id;
    private String author;
    private String content;
    private String url;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
