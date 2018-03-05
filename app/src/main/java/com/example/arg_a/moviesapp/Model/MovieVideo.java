package com.example.arg_a.moviesapp.Model;

/**
 * Created by arg-a on 04/03/2018.
 */

public class MovieVideo {

    private String name;
    private String type;
    private String site;
    private String key;

    public MovieVideo(String name, String type, String site, String key){
        this.name   = name;
        this.type   = type;
        this.site   = site;
        this.key    = key;
    }

    public MovieVideo(){}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
