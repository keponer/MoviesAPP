package com.example.arg_a.moviesapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.arg_a.moviesapp.Model.Movie;
import com.example.arg_a.moviesapp.Utilities.MoviesAPI;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private MovieAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ArrayList<Movie> arrayList = new ArrayList<>();

        recyclerView = findViewById(R.id.moviesRecyclerView);

        adapter = new MovieAdapter(getApplicationContext());

        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);


        MoviesAPI.getMovies(this, "https://api.themoviedb.org/3/movie/popular?api_key=ApiKEy", new MoviesAPI.VolleyCallback() {
            @Override
            public void onSuccess(ArrayList<Movie> arrayList) {
                Log.d("CALLBACK", String.valueOf(arrayList.size()));
                adapter.swapMovies(arrayList);
            }
        });




        Log.d("ONCREATE", String.valueOf(arrayList.size()));
    }
}
