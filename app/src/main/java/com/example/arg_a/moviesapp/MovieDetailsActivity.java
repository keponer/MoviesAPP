package com.example.arg_a.moviesapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.arg_a.moviesapp.Model.Movie;
import com.squareup.picasso.Picasso;

public class MovieDetailsActivity extends AppCompatActivity {

    private Movie movie;

    private ImageView moviePoster;
    private TextView movieRelease;
    private TextView movieUserRating;
    private TextView movieSynopsis;
    private TextView movieTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        Bundle bundle = getIntent().getExtras();
        movie = bundle.getParcelable("movie");

        moviePoster = findViewById(R.id.movieImageDetail);
        movieRelease = findViewById(R.id.movie_release);
        movieUserRating = findViewById(R.id.movie_user_rating);
        movieSynopsis = findViewById(R.id.movie_synopsis);
        movieTitle = findViewById(R.id.movie_title);

        String image = "http://image.tmdb.org/t/p/w185/" + movie.getPosterImage();

        Log.d("DETAILS", image);

        Picasso.with(this)
                .load(image)
                .into(moviePoster);

        movieRelease.setText(movie.getReleaseDate());
        movieUserRating.setText(movie.getRating() + "/10");
        movieSynopsis.setText(movie.getSynopsis() + "\n");
        movieTitle.setText(movie.getOriginalTitle());

        setTitle(movie.getOriginalTitle());

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
