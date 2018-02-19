package com.example.arg_a.moviesapp.Model;

/**
 * Created by arg-a on 18/02/2018.
 */

public class Movie {

    private String originalTitle;
    private String posterImage;
    private String synopsis;
    private String rating;
    private String releaseDate;

    public Movie (String originalTitle, String posterImage, String synopsis,
                    String rating, String releaseDate){

        this.originalTitle  = originalTitle;
        this.posterImage    = posterImage;
        this.synopsis       = synopsis;
        this.rating         = rating;
        this.releaseDate    = releaseDate;
    }

    public Movie (){}

    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public String getPosterImage() {
        return posterImage;
    }

    public void setPosterImage(String posterImage) {
        this.posterImage = posterImage;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }
}
