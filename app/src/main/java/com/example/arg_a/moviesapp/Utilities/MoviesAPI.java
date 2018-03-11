package com.example.arg_a.moviesapp.Utilities;

import android.content.ContentValues;
import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.arg_a.moviesapp.BuildConfig;
import com.example.arg_a.moviesapp.DB.MoviesContract;
import com.example.arg_a.moviesapp.Model.Movie;
import com.example.arg_a.moviesapp.Model.MovieReview;
import com.example.arg_a.moviesapp.Model.MovieVideo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.android.volley.Response.ErrorListener;

/**
 * Created by arg-a on 18/02/2018.
 */

public class MoviesAPI {

    //Constants for the movie Parser
    private static final String MOVIE_RESULTS           = "results";
    private static final String MOVIE_ORIGINAL_TITLE    = "title";
    private static final String MOVIE_POSTER            = "poster_path";
    private static final String MOVIE_SYNOPSIS          = "overview";
    private static final String MOVIE_USER_RATING       = "vote_average";
    private static final String MOVIE_RELEASE_DATE      = "release_date";
    private static final String MOVIE_ID                = "id";

    private static final String MOVIE_VIDEO_NAME    = "name";
    private static final String MOVIE_VIDEO_SITE    = "site";
    private static final String MOVIE_VIDEO_TYPE    = "type";
    private static final String MOVIE_VIDEO_KEY     = "key";

    private static final String MOVIE_REVIEW_AUTHOR     = "author";
    private static final String MOVIE_REVIEW_CONTENT    = "content";

    //Constants to create the url for GET petitions
    public static final String BASE_URL             = "https://api.themoviedb.org/3/movie/";
    public static final String FILTER_POPULAR       = "popular";
    public static final String FILTER_TOP_RATED     = "top_rated";
    public static final String FILTER_FAVORITE      = "favorite";
    public static final String API_KEY              = "?api_key=" + BuildConfig.API_KEY;
    public static final String PAGE_BASE            = "&page=";
    public static final String MOVIE_VIDEO          = "/videos";
    public static final String MOVIE_REVIEW         = "/reviews";

    //Constants to get the images and the size
    public static final String IMG_URL_BASE     = "http://image.tmdb.org/t/p/";
    public static final String IMG_SIZE_PHONE   = "w185/";

    public static final String YOUTUBE_URL = "https://youtu.be/";
    //Which is the current filter
    public static String currentFilter = FILTER_POPULAR;



    /**
     * VolleyCallback Interface
     */
    public interface VolleyCallback{
        void onSuccess(ArrayList<Movie> arrayList);
        void onError();
    }


    /**
     * Does a GET request and in a callback return an arrayList of movies
     * @param context Context
     * @param url URL where to do the GET Request
     * @param callback Callback
     */
    public static synchronized void getMovies (Context context, String url, final VolleyCallback callback){

        final ArrayList<Movie> movies = new ArrayList<>();

        RequestQueue queue = Volley.newRequestQueue(context);

        // GET request
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {

                try {
                    //Get te results of the response
                    JSONArray movieList = response.getJSONArray(MOVIE_RESULTS);

                    //Parse and add movies to the arrayList
                    for(int i = 0;i<movieList.length();i++){

                        movies.add(parseJSONtoMovie(movieList.getJSONObject(i)));

                    }

                    //Callback
                    callback.onSuccess(movies);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                callback.onError();

            }
        });

        queue.add(jsonObjectRequest);
    }

    /**
     * Does a GET request and add the response to the ArrayList given
     * In the callback return the new ArrayList
     * @param context Context
     * @param url URL where to do the GET
     * @param arrayList ArrayList where the movies have to been added
     * @param callback Callback
     */
    public static synchronized void getMovies (Context context, String url, ArrayList<Movie> arrayList, final VolleyCallback callback){

        final ArrayList<Movie> movies = arrayList;

        RequestQueue queue = Volley.newRequestQueue(context);

        //GET request
        JsonObjectRequest jsonArrayRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {

                try {
                    //Get the results of the response
                    JSONArray movieList = response.getJSONArray(MOVIE_RESULTS);

                    //Parse and add movies to the ArrayList
                    for(int i = 0;i<movieList.length();i++){

                        movies.add(parseJSONtoMovie(movieList.getJSONObject(i)));

                    }

                    //Callback
                    callback.onSuccess(movies);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                callback.onError();

            }
        });

        queue.add(jsonArrayRequest);
    }

    /**
     * Parse JSONObject to a movie
     * @param jsonMovie JSONObject to parse
     * @return Movie from JSON
     * @throws JSONException
     */
    private static Movie parseJSONtoMovie (JSONObject jsonMovie) throws JSONException {

        Movie movie;

        String originalTitle    = jsonMovie.getString(MOVIE_ORIGINAL_TITLE);
        String poster           = jsonMovie.getString(MOVIE_POSTER);
        String synopsis         = jsonMovie.getString(MOVIE_SYNOPSIS);
        String rating           = jsonMovie.getString(MOVIE_USER_RATING);
        String releaseDate      = jsonMovie.getString(MOVIE_RELEASE_DATE);
        int id                  = jsonMovie.getInt(MOVIE_ID);

        movie = new Movie(originalTitle, poster, synopsis, rating, releaseDate, id);

        return movie;
    }

    /**
     * Does a GET request to get videos and add the response to the Movie given
     * In the callback return the new Movie
     * @param context Context
     * @param url URL where to do the GET
     * @param movie Movie where to add the videos
     * @param callback Callback
     */
    public static synchronized void getMovieVideos(Context context, String url, final Movie movie, final VolleyCallback callback){

        final ArrayList<MovieVideo> movieVideos = new ArrayList<>();
        final ArrayList<Movie> movies = new ArrayList<>();

        RequestQueue queue = Volley.newRequestQueue(context);

        // GET request
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {

                try {
                    //Get te results of the response
                    JSONArray movieVideoList = response.getJSONArray(MOVIE_RESULTS);

                    //Parse and add movies to the arrayList
                    for(int i = 0;i<movieVideoList.length();i++){

                        movieVideos.add(parseJSONtoMovieVideo(movieVideoList.getJSONObject(i)));

                    }

                    movie.setMovieVideos(movieVideos);

                    movies.add(movie);
                    //Callback
                    callback.onSuccess(movies);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                callback.onError();

            }
        });

        queue.add(jsonObjectRequest);

    }

    /**
     * Parse a JSONObject to a MovieVideo
     * @param jsonVideoMovie object to parse
     * @return MovieVideo
     * @throws JSONException
     */
    private static MovieVideo parseJSONtoMovieVideo (JSONObject jsonVideoMovie) throws JSONException {

        MovieVideo movieVideo;

        String name     = jsonVideoMovie.getString(MOVIE_VIDEO_NAME);
        String site     = jsonVideoMovie.getString(MOVIE_VIDEO_SITE);
        String type     = jsonVideoMovie.getString(MOVIE_VIDEO_TYPE);
        String key      = jsonVideoMovie.getString(MOVIE_VIDEO_KEY);

        movieVideo = new MovieVideo(name, type, site, key);

        return movieVideo;
    }

    /**
     * Does a GET request to get reviews and add the response to the Movie given
     * In the callback return the new Movie
     * @param context Context
     * @param url URL to do the GET request
     * @param movie Movie where to add the Reviews
     * @param callback Callback
     */
    public static synchronized void getMovieReviews(Context context, String url, final Movie movie, final VolleyCallback callback){

        final ArrayList<MovieReview> movieReviews = new ArrayList<>();
        final ArrayList<Movie> movies = new ArrayList<>();

        RequestQueue queue = Volley.newRequestQueue(context);

        // GET request
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {

                try {
                    //Get te results of the response
                    JSONArray movieReviewList = response.getJSONArray(MOVIE_RESULTS);

                    //Parse and add movies to the arrayList
                    for(int i = 0;i<movieReviewList.length();i++){

                        movieReviews.add(parseJSONtoMovieReview(movieReviewList.getJSONObject(i)));

                    }

                    movie.setMovieReviews(movieReviews);

                    movies.add(movie);
                    //Callback
                    callback.onSuccess(movies);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                callback.onError();

            }
        });

        queue.add(jsonObjectRequest);

    }

    /**
     * Parse a JSONObject to a MovieReview
     * @param jsonMovieReview object to parse
     * @return MovieReview
     * @throws JSONException
     */
    private static MovieReview parseJSONtoMovieReview (JSONObject jsonMovieReview) throws JSONException {

        MovieReview movieReview;

        String author   = jsonMovieReview.getString(MOVIE_REVIEW_AUTHOR);
        String content  = jsonMovieReview.getString(MOVIE_REVIEW_CONTENT);

        movieReview = new MovieReview(author, content);

        return movieReview;
    }

    /**
     * Parse a Movie Object into a ContentValues Object
     * @param movie Movie to parse
     * @return ContentValues
     */
    public static ContentValues parseMovieToContentValues (Movie movie) {
        ContentValues contentValues = new ContentValues();

        contentValues.put(MoviesContract.MoviesTable.COLUMN_ORIGINAL_TITLE, movie.getOriginalTitle());
        contentValues.put(MoviesContract.MoviesTable.COLUMN_POSTER_IMAGE, movie.getPosterImage());
        contentValues.put(MoviesContract.MoviesTable.COLUMN_RATING, movie.getRating());
        contentValues.put(MoviesContract.MoviesTable.COLUMN_RELEASE_DATE, movie.getReleaseDate());
        contentValues.put(MoviesContract.MoviesTable.COLUMN_SYNOPSIS, movie.getSynopsis());
        contentValues.put(MoviesContract.MoviesTable._ID, movie.getId());

        return contentValues;
    }

}
