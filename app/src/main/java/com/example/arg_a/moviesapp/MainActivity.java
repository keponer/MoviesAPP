package com.example.arg_a.moviesapp;

import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;

import com.example.arg_a.moviesapp.DB.MoviesContract;
import com.example.arg_a.moviesapp.Model.Movie;
import com.example.arg_a.moviesapp.Utilities.MoviesAPI;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements MovieAdapter.MovieAdapterOnClickHandler{

    @BindView(R.id.moviesRecyclerView) RecyclerView recyclerView;
    @BindView(R.id.bottom_navigation) BottomNavigationView navigation;

    private MovieAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        if(!isOnline()){

            noInternetConnectionDialog();

        }else {

            setRecyclerView();

            CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) navigation.getLayoutParams();
            layoutParams.setBehavior(new BottomNavigationBehavior());
        }
    }

    public void setRecyclerView(){
        navigation.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener);

        adapter = new MovieAdapter(getApplicationContext(), this);

        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 3);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        setMovies(MoviesAPI.FILTER_POPULAR);
    }

    /**
     * Call the MovieDetailsActivity and putExtra the clicked movie
     * @param movie Movie Clicked
     */
    @Override
    public void onClick(Movie movie) {
        Intent intent = new Intent(this, MovieDetailsActivity.class);

        intent.putExtra("movie", movie);

        startActivity(intent);
    }

    /**
     * Handle the Bottom Menu
     */
    private BottomNavigationView.OnNavigationItemSelectedListener onNavigationItemSelectedListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                    switch (item.getItemId()){
                        case R.id.most_popular:

                            if(!MoviesAPI.currentFilter.equals(MoviesAPI.FILTER_POPULAR)){

                                setMovies(MoviesAPI.FILTER_POPULAR);

                                MoviesAPI.currentFilter = MoviesAPI.FILTER_POPULAR;

                                return true;
                            }
                            break;

                        case R.id.top_rated:

                            if(!MoviesAPI.currentFilter.equals(MoviesAPI.FILTER_TOP_RATED)){

                                setMovies(MoviesAPI.FILTER_TOP_RATED);

                                MoviesAPI.currentFilter = MoviesAPI.FILTER_TOP_RATED;

                                return true;
                            }
                            break;

                        case R.id.favorite:

                            if(!MoviesAPI.currentFilter.equals(MoviesAPI.FILTER_FAVORITE)){

                                setMovies(MoviesAPI.FILTER_FAVORITE);

                                MoviesAPI.currentFilter = MoviesAPI.FILTER_FAVORITE;

                                return true;
                            }
                    }
                    return false;
                }
            };

    /**
     * Make the GET call and set new movies in the adapter depending ot the filter given
     * @param filter how to filter the movies
     */
    private void setMovies(String filter){

        if(!filter.equals(MoviesAPI.FILTER_FAVORITE)) {
            MoviesAPI.getMovies(getApplicationContext(), MoviesAPI.BASE_URL + filter + MoviesAPI.API_KEY, new MoviesAPI.VolleyCallback() {
                @Override
                public void onSuccess(ArrayList<Movie> arrayList) {
                    adapter.swapMovies(arrayList);
                }

                @Override
                public void onError() {
                    noInternetConnectionDialog();
                }
            });
        }
        else{

            ContentResolver contentResolver = getApplicationContext().getContentResolver();

            Cursor cursor;

            cursor = contentResolver.query(MoviesContract.MoviesTable.CONTENT_URI, null, null, null, null, null);

            ArrayList<Movie> arrayList = new ArrayList<>();

            while(cursor.moveToNext()){

                arrayList.add(new Movie(cursor));

            }

            adapter.swapMovies(arrayList);
        }
    }

    /**
     * Return if the device have internet connection
     * @return boolean
     */
    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    /**
     * Sow a dialog Saying is not internet connection and finishing the app when the button is pressed
     */
    public void noInternetConnectionDialog(){

        AlertDialog.Builder builder;

        builder = new AlertDialog.Builder(this);

        builder.setTitle("Error!")
                .setMessage("You have no internet connexion")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                })
                .show();
    }
}
