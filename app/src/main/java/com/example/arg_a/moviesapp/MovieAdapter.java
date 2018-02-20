package com.example.arg_a.moviesapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.arg_a.moviesapp.Model.Movie;
import com.example.arg_a.moviesapp.Utilities.MoviesAPI;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by arg-a on 19/02/2018.
 */

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieAdapterViewHolder>{

    private final Context context;

    private ArrayList<Movie> moviesList;

    private int page;

    private final MovieAdapterOnClickHandler clickHandler;

    public interface MovieAdapterOnClickHandler{
        void onClick(Movie movie);
    }

    public MovieAdapter(Context context, MovieAdapterOnClickHandler clickHandler){
        this.context = context;
        this.clickHandler = clickHandler;
        page = 2;
    }

    @Override
    public MovieAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        int layoutID = R.layout.movie_card;

        View view = LayoutInflater.from(context).inflate(layoutID, parent, false);

        return new MovieAdapter.MovieAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MovieAdapterViewHolder holder, int position) {

        if(getItemCount()>0){

            String image = MoviesAPI.IMG_URL_BASE +
                    MoviesAPI.IMG_SIZE_PHONE +
                    moviesList.get(position).getPosterImage();

            Log.d("ONBIND", image);

            Picasso.with(context)
                    .load(image)
                    .into(holder.movieImage);
        }

        if(getItemCount()-1 == position){

            MoviesAPI.getMovies(context,
                    MoviesAPI.BASE_URL +
                            MoviesAPI.FILTER_POPULAR +
                            MoviesAPI.API_KEY +
                            MoviesAPI.PAGE_BASE +
                            page,
                    moviesList,
                    new MoviesAPI.VolleyCallback() {
                @Override
                public void onSuccess(ArrayList<Movie> arrayList) {
                    swapMovies(arrayList);
                    page++;
                }
            });

        }
    }

    @Override
    public int getItemCount() {

        if(moviesList == null) return 0;
        return moviesList.size();
    }

    public void swapMovies(ArrayList<Movie> arrayList){
        moviesList = arrayList;
        notifyDataSetChanged();
    }

    class MovieAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        final ImageView movieImage;
        final View movieCardLayout;

        public MovieAdapterViewHolder(View itemView){
            super(itemView);

            movieImage = itemView.findViewById(R.id.movieImage);
            movieCardLayout = itemView.findViewById(R.id.movieCardLayout);
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {

            int position = getAdapterPosition();

            Log.d("CLICK", "ASD");
            clickHandler.onClick(moviesList.get(position));

        }
    }
}
