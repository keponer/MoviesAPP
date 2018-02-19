package com.example.arg_a.moviesapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.arg_a.moviesapp.Model.Movie;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by arg-a on 19/02/2018.
 */

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieAdapterViewHolder>{

    private final Context context;

    private ArrayList<Movie> moviesList;

    private int heightSize;

    public MovieAdapter(Context context){this.context = context;}

    @Override
    public MovieAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        int layoutID = R.layout.movie_card;

        View view = LayoutInflater.from(context).inflate(layoutID, parent, false);

        return new MovieAdapter.MovieAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MovieAdapterViewHolder holder, int position) {

        if(getItemCount()>0){

            ViewGroup.LayoutParams params = holder.movieCardLayout.getLayoutParams();

            if(holder.movieCardLayout.getWidth()>0){
                heightSize = (int) (holder.movieCardLayout.getWidth()*1.5);
            }

            params.height = heightSize;

            holder.movieCardLayout.setLayoutParams(params);

            Log.d("width", String.valueOf(holder.movieCardLayout.getWidth()));

            String image = "http://image.tmdb.org/t/p/w185/" + moviesList.get(position).getPosterImage();

            Log.d("ONBIND", image);

            Picasso.with(context)
                    .load(image)
                    .into(holder.movieImage);
        }
    }

    @Override
    public int getItemCount() {
        return moviesList.size();
    }

    public void swapMovies(ArrayList<Movie> arrayList){
        moviesList = arrayList;
        notifyDataSetChanged();
    }

    class MovieAdapterViewHolder extends RecyclerView.ViewHolder{

        final ImageView movieImage;
        final View movieCardLayout;

        public MovieAdapterViewHolder(View itemView){
            super(itemView);

            movieImage = itemView.findViewById(R.id.movieImage);
            movieCardLayout = itemView.findViewById(R.id.movieCardLayout);

        }

    }
}
