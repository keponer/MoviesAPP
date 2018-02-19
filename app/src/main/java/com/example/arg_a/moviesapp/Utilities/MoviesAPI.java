package com.example.arg_a.moviesapp.Utilities;

import android.content.Context;

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

    private static final String MOVIE_RESULTS           = "results";
    private static final String MOVIE_ORIGINAL_TITLE    = "title";
    private static final String MOVIE_POSTER            = "poster_path";
    private static final String MOVIE_SYNOPSIS          = "overview";
    private static final String MOVIE_USER_RATING       = "vote_average";
    private static final String MOVIE_RELEASE_DATE      = "release_date";

    public interface VolleyCallback{
        void onSuccess(ArrayList<Movie> arrayList);
    }


    public static synchronized void getMovies (Context context, String url, final VolleyCallback callback){

        final ArrayList<Movie> movies = new ArrayList<>();

        RequestQueue queue = Volley.newRequestQueue(context);

        JsonObjectRequest jsonArrayRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    JSONArray movieList = response.getJSONArray(MOVIE_RESULTS);

                    for(int i = 0;i<movieList.length();i++){

                        movies.add(parseJSONtoMovie(movieList.getJSONObject(i)));

                    }

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

    public static Movie parseJSONtoMovie (JSONObject jsonMovie) throws JSONException {

        Movie movie;

        String originalTitle    = jsonMovie.getString(MOVIE_ORIGINAL_TITLE);
        String poster           = jsonMovie.getString(MOVIE_POSTER);
        String synopsis         = jsonMovie.getString(MOVIE_SYNOPSIS);
        String rating           = jsonMovie.getString(MOVIE_USER_RATING);
        String releaseDate      = jsonMovie.getString(MOVIE_RELEASE_DATE);

        //Log.d("PARSEJONTOMOVIE", originalTitle);

        movie = new Movie(originalTitle, poster, synopsis, rating, releaseDate);

        return movie;
    }
}
