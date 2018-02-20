package com.example.arg_a.moviesapp.Model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by arg-a on 18/02/2018.
 */

public class Movie implements Parcelable{

    private String originalTitle;
    private String posterImage;
    private String synopsis;
    private String rating;
    private String releaseDate;

    public Movie (){}

    public Movie (String originalTitle, String posterImage, String synopsis,
                    String rating, String releaseDate){

        this.originalTitle  = originalTitle;
        this.posterImage    = posterImage;
        this.synopsis       = synopsis;
        this.rating         = rating;
        this.releaseDate    = releaseDate;
    }

    public Movie(Parcel parcel){

        this.originalTitle  = parcel.readString();
        this.posterImage    = parcel.readString();
        this.synopsis       = parcel.readString();
        this.rating         = parcel.readString();
        this.releaseDate    = parcel.readString();

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
