package com.example.arg_a.moviesapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import com.example.arg_a.moviesapp.Model.Movie;
import com.squareup.picasso.Picasso;

public class MovieDetailsActivity extends AppCompatActivity {

    private Movie movie;

    private ImageView moviePoster;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        Bundle bundle = getIntent().getExtras();
        movie = bundle.getParcelable("movie");

        moviePoster = findViewById(R.id.movieImageDetail);

        String image = "http://image.tmdb.org/t/p/w185/" + movie.getPosterImage();

        Log.d("DETAILS", image);

        Picasso.with(this)
                .load(image)
                .into(moviePoster);

    }
}
