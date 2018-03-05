package com.example.arg_a.moviesapp.Model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by arg-a on 18/02/2018.
 */

public class Movie implements Parcelable{

    private String originalTitle;
    private String posterImage;
    private String synopsis;
    private String rating;
    private String releaseDate;
    private int id;

    private ArrayList<MovieVideo> movieVideos;
    private ArrayList<MovieReview> movieReviews;

    public Movie (){}

    public Movie (String originalTitle, String posterImage, String synopsis,
                    String rating, String releaseDate, int id){

        this.originalTitle  = originalTitle;
        this.posterImage    = posterImage;
        this.synopsis       = synopsis;
        this.rating         = rating;
        this.releaseDate    = releaseDate;
        this.id             = id;

        this.movieVideos = new ArrayList<>();
        this.movieReviews = new ArrayList<>();
    }

    public Movie(Parcel parcel){

        this.originalTitle  = parcel.readString();
        this.posterImage    = parcel.readString();
        this.synopsis       = parcel.readString();
        this.rating         = parcel.readString();
        this.releaseDate    = parcel.readString();
        this.id             = parcel.readInt();

        this.movieVideos = new ArrayList<>();
        this.movieReviews = new ArrayList<>();
    }


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

    public ArrayList<MovieVideo> getMovieVideos() {
        return movieVideos;
    }

    public void setMovieVideos(ArrayList<MovieVideo> movieVideos) {
        this.movieVideos = movieVideos;
    }

    public ArrayList<MovieReview> getMovieReviews() {
        return movieReviews;
    }

    public void setMovieReviews(ArrayList<MovieReview> movieReviews) {
        this.movieReviews = movieReviews;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {

        parcel.writeString(originalTitle);
        parcel.writeString(posterImage);
        parcel.writeString(synopsis);
        parcel.writeString(rating);
        parcel.writeString(releaseDate);
        parcel.writeInt(id);

    }

    public static final Parcelable.Creator<Movie> CREATOR = new Parcelable.Creator<Movie>(){


        @Override
        public Movie createFromParcel(Parcel parcel) {
            return new Movie(parcel);
        }

        @Override
        public Movie[] newArray(int i) {
            return new Movie[i];
        }
    };
}
