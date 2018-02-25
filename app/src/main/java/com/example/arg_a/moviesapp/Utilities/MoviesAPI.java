package com.example.arg_a.moviesapp.Utilities;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.arg_a.moviesapp.Model.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.android.volley.Response.*;

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

    //Constants to create the url for GET petitions
    public static final String BASE_URL             = "https://api.themoviedb.org/3/movie/";
    public static final String FILTER_POPULAR       = "popular";
    public static final String FILTER_TOP_RATED     = "top_rated";
    public static final String API_KEY              = "APIKEY";
    public static final String PAGE_BASE            = "&page=";

    //Constants to get the images and the size
    public static final String IMG_URL_BASE     = "http://image.tmdb.org/t/p/";
    public static final String IMG_SIZE_PHONE   = "w185/";

    //Which is the current filter
    public static String currentFilter = FILTER_POPULAR;


    /**
     * VolleyCallback Interface
     */
    public interface VolleyCallback{
        void onSuccess(ArrayList<Movie> arrayList);
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
        JsonObjectRequest jsonArrayRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Listener<JSONObject>() {

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

            }
        });

        queue.add(jsonArrayRequest);
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


        movie = new Movie(originalTitle, poster, synopsis, rating, releaseDate);



        return movie;
    }
}
