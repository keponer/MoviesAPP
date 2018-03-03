package com.example.arg_a.moviesapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.arg_a.moviesapp.Model.Movie;
import com.example.arg_a.moviesapp.Utilities.MoviesAPI;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MovieDetailsActivity extends AppCompatActivity {

    private Movie movie;

    @BindView(R.id.movieImageDetail) ImageView moviePoster;
    @BindView(R.id.movie_release) TextView movieRelease;
    @BindView(R.id.movie_user_rating) TextView movieUserRating;
    @BindView(R.id.movie_synopsis) TextView movieSynopsis;
    @BindView(R.id.movie_title) TextView movieTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        Bundle bundle = getIntent().getExtras();
        movie = bundle.getParcelable("movie");

        ButterKnife.bind(this);

        String image = MoviesAPI.IMG_URL_BASE + MoviesAPI.IMG_SIZE_PHONE + movie.getPosterImage();

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
