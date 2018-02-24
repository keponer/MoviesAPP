package com.example.arg_a.moviesapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;

import com.example.arg_a.moviesapp.Model.Movie;
import com.example.arg_a.moviesapp.Utilities.MoviesAPI;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements MovieAdapter.MovieAdapterOnClickHandler{

    private RecyclerView recyclerView;
    private MovieAdapter adapter;

    private BottomNavigationView navigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ArrayList<Movie> arrayList = new ArrayList<>();

        recyclerView = findViewById(R.id.moviesRecyclerView);
        navigation = findViewById(R.id.bottom_navigation);

        navigation.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener);

        adapter = new MovieAdapter(getApplicationContext(), this);

        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 3);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);


        MoviesAPI.getMovies(this, MoviesAPI.BASE_URL + MoviesAPI.FILTER_POPULAR + MoviesAPI.API_KEY, new MoviesAPI.VolleyCallback() {
            @Override
            public void onSuccess(ArrayList<Movie> arrayList) {
                Log.d("CALLBACK", String.valueOf(arrayList.size()));
                adapter.swapMovies(arrayList);
            }
        });

        Log.d("ONCREATE", String.valueOf(arrayList.size()));
    }

    @Override
    public void onClick(Movie movie) {

        Log.d("MOVIE", movie.getOriginalTitle());

        Intent intent = new Intent(this, MovieDetailsActivity.class);

        intent.putExtra("movie", movie);

        startActivity(intent);

    }

    private BottomNavigationView.OnNavigationItemSelectedListener onNavigationItemSelectedListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                    switch (item.getItemId()){
                        case R.id.most_popular:

                            if(MoviesAPI.currentFilter != MoviesAPI.FILTER_POPULAR){
                                MoviesAPI.getMovies(getApplicationContext(), MoviesAPI.BASE_URL + MoviesAPI.FILTER_POPULAR + MoviesAPI.API_KEY, new MoviesAPI.VolleyCallback() {
                                    @Override
                                    public void onSuccess(ArrayList<Movie> arrayList) {
                                        Log.d("CALLBACK", String.valueOf(arrayList.size()));
                                        adapter.swapMovies(arrayList);
                                        MoviesAPI.currentFilter = MoviesAPI.FILTER_POPULAR;
                                    }
                                });

                                return true;
                            }
                            break;

                        case R.id.top_rated:

                            if(MoviesAPI.currentFilter != MoviesAPI.FILTER_TOP_RATED){

                                MoviesAPI.getMovies(getApplicationContext(), MoviesAPI.BASE_URL + MoviesAPI.FILTER_TOP_RATED + MoviesAPI.API_KEY, new MoviesAPI.VolleyCallback() {
                                    @Override
                                    public void onSuccess(ArrayList<Movie> arrayList) {
                                        Log.d("CALLBACK", String.valueOf(arrayList.size()));
                                        adapter.swapMovies(arrayList);
                                        MoviesAPI.currentFilter = MoviesAPI.FILTER_TOP_RATED;
                                    }
                                });

                                return true;
                            }
                            break;

                    }
                    Log.d("NAVIGATION", String.valueOf(item.getItemId()));
                    return false;
                }
            };

}
