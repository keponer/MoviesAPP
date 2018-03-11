package com.example.arg_a.moviesapp;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.arg_a.moviesapp.DB.MoviesContract;
import com.example.arg_a.moviesapp.Model.Movie;
import com.example.arg_a.moviesapp.Model.MovieVideo;
import com.example.arg_a.moviesapp.Utilities.MoviesAPI;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MovieDetailsActivity extends AppCompatActivity implements TrailerAdapter.TrailerAdapterOnClickHandler{

    private Movie movie;

    private TrailerAdapter trailerAdapter;

    private ReviewAdapter reviewAdapter;

    private ContentResolver contentResolver;

    private boolean add;

    @BindView(R.id.movieImageDetail) ImageView moviePoster;
    @BindView(R.id.movie_release) TextView movieRelease;
    @BindView(R.id.movie_user_rating) TextView movieUserRating;
    @BindView(R.id.movie_synopsis) TextView movieSynopsis;
    @BindView(R.id.movie_title) TextView movieTitle;
    @BindView(R.id.movie_video_recyclerView) RecyclerView videoRecyclerView;
    @BindView(R.id.movie_review_recyclerView) RecyclerView reviewRecyclerView;
    @BindView(R.id.progressBar) ProgressBar progressBar;
    @BindView(R.id.synopsis_divider) View divider;
    @BindView(R.id.add_favorites)
    Button addFavorites;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        Bundle bundle = getIntent().getExtras();
        movie = bundle.getParcelable("movie");

        contentResolver = getApplicationContext().getContentResolver();

        ButterKnife.bind(this);

        textAddOrDelete();

        showProgressBar();

        setMovieVideosRecyclerView();

        setUI();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    /**
     * Set all the elements in the UI less the recyclerView
     */
    private void setUI(){

        String image = MoviesAPI.IMG_URL_BASE + MoviesAPI.IMG_SIZE_PHONE + movie.getPosterImage();

        Picasso.with(this)
                .load(image)
                .into(moviePoster);

        movieRelease.setText(movie.getReleaseDate());
        movieUserRating.setText(movie.getRating() + "/10");
        movieSynopsis.setText(movie.getSynopsis() + "\n");
        movieTitle.setText(movie.getOriginalTitle());

        setTitle(movie.getOriginalTitle());

    }

    /**
     * Set the MovieVideos RecyclerView, also do the call to setMovieVideos to get all the videos data
     */
    private void setMovieVideosRecyclerView(){
        trailerAdapter = new TrailerAdapter(getApplicationContext(), this);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        videoRecyclerView.setLayoutManager(layoutManager);
        videoRecyclerView.setItemAnimator(new DefaultItemAnimator());
        videoRecyclerView.setAdapter(trailerAdapter);

        setMovieVideos();
    }

    /**
     * Set the MovieReview RecyclerView, also do the call to the setMovieReviews to get all the reviews data
     */
    private void setMovieReviewRecyclerView(){

        reviewAdapter = new ReviewAdapter(getApplicationContext());

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        reviewRecyclerView.setLayoutManager(layoutManager);
        reviewRecyclerView.setItemAnimator(new DefaultItemAnimator());
        reviewRecyclerView.setAdapter(reviewAdapter);

        setMovieReviews();

    }

    /**
     * Handle the menu items (topBar)
     * @param item menuItem
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Do a Volley call to get all the data for MovieVideo, also if the call is succeed
     * call the setMovieReviewRecyclerView()
     */
    private void setMovieVideos(){

        MoviesAPI.getMovieVideos(getApplicationContext(),
                MoviesAPI.BASE_URL
                + movie.getId()
                + MoviesAPI.MOVIE_VIDEO
                + MoviesAPI.API_KEY,
                movie,
                new MoviesAPI.VolleyCallback() {
            @Override
            public void onSuccess(ArrayList<Movie> arrayList) {

                trailerAdapter.swapMovies(arrayList.get(0).getMovieVideos());

                setMovieReviewRecyclerView();

            }

            @Override
            public void onError() {

                Toast.makeText(getApplicationContext(),
                        "Has been a problem getting the videos",
                        Toast.LENGTH_LONG)
                        .show();

            }
        });
    }

    /**
     * Do a Volley call to get all the data for MovieReview, also if the call is succeed
     * call hideProgressBar() to show all the elements
     */
    private void setMovieReviews(){

        MoviesAPI.getMovieReviews(getApplicationContext(),
                MoviesAPI.BASE_URL
                        + movie.getId()
                        + MoviesAPI.MOVIE_REVIEW
                        + MoviesAPI.API_KEY,
                movie,
                new MoviesAPI.VolleyCallback() {
                    @Override
                    public void onSuccess(ArrayList<Movie> arrayList) {
                        reviewAdapter.swapReviews(arrayList.get(0).getMovieReviews());

                        hideProgressBar();
                    }

                    @Override
                    public void onError() {

                        Toast.makeText(getApplicationContext(),
                                "Has been a problem getting the reviews",
                                Toast.LENGTH_LONG)
                                .show();

                    }
                });
    }

    /**
     * Handle when a video button is pressed
     * @param video
     */
    @Override
    public void onClick(MovieVideo video) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(MoviesAPI.YOUTUBE_URL + video.getKey()));

        Intent chooser = Intent.createChooser(intent, "Open with...");

        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(chooser);
        }
    }

    /**
     * onClickListerner. Add or delete favorites when the button is pressed
     * @param view
     */
    public void addFavorites(View view){

        if(add){
            contentResolver.insert(MoviesContract.MoviesTable.CONTENT_URI, MoviesAPI.parseMovieToContentValues(movie));
            addFavorites.setText(getApplicationContext().getResources().getText(R.string.movie_delete_favorites));
            add = false;

            Toast.makeText(getApplicationContext(),
                    String.valueOf(getApplicationContext().getResources().getText(R.string.movie_added_favorites)),
                    Toast.LENGTH_LONG)
                    .show();

        }
        else{
            contentResolver.delete(MoviesContract.MoviesTable.buildMovieIDUri(movie.getId()), null, null);
            addFavorites.setText(getApplicationContext().getResources().getText(R.string.movie_add_favorites));
            add = true;

            Toast.makeText(getApplicationContext(),
                    String.valueOf(getApplicationContext().getResources().getText(R.string.movie_deleted_favorites)),
                    Toast.LENGTH_LONG)
                    .show();
        }
    }

    /**
     * When the view is created set the text button
     */
    private void textAddOrDelete(){

        Cursor cursor = contentResolver.query(MoviesContract.MoviesTable.buildMovieIDUri(movie.getId()),
                null,
                null,
                null,
                null);

        if(cursor.getCount()==0){
            addFavorites.setText(getApplicationContext().getResources().getText(R.string.movie_add_favorites));
            add = true;
        }
        else{
            addFavorites.setText(getApplicationContext().getResources().getText(R.string.movie_delete_favorites));
            add = false;
        }

    }
    /**
     * Hide all the elements in the view and show the progressBar
     */
    private void showProgressBar(){

        moviePoster.setVisibility(View.GONE);
        movieRelease.setVisibility(View.GONE);
        movieUserRating.setVisibility(View.GONE);
        movieSynopsis.setVisibility(View.GONE);
        movieTitle.setVisibility(View.GONE);
        videoRecyclerView.setVisibility(View.GONE);
        reviewRecyclerView.setVisibility(View.GONE);
        divider.setVisibility(View.GONE);
        addFavorites.setVisibility(View.GONE);

        progressBar.setVisibility(View.VISIBLE);

    }

    /**
     * Show all the elements in the view and hide the progressBar
     */
    private void hideProgressBar(){

        moviePoster.setVisibility(View.VISIBLE);
        movieRelease.setVisibility(View.VISIBLE);
        movieUserRating.setVisibility(View.VISIBLE);
        movieSynopsis.setVisibility(View.VISIBLE);
        movieTitle.setVisibility(View.VISIBLE);
        videoRecyclerView.setVisibility(View.VISIBLE);
        reviewRecyclerView.setVisibility(View.VISIBLE);
        divider.setVisibility(View.VISIBLE);
        addFavorites.setVisibility(View.VISIBLE);

        progressBar.setVisibility(View.INVISIBLE);

    }
}
