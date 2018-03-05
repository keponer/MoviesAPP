package com.example.arg_a.moviesapp.Model;

/**
 * Created by arg-a on 04/03/2018.
 */

public class MovieReview {

    private String author;
    private String content;

    public MovieReview(String author, String content){
        this.author  = author;
        this.content = content;
    }

    public MovieReview(){}

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
}
