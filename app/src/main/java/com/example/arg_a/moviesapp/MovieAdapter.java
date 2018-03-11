package com.example.arg_a.moviesapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.arg_a.moviesapp.Model.Movie;
import com.example.arg_a.moviesapp.Utilities.MoviesAPI;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by arg-a on 19/02/2018.
 */

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieAdapterViewHolder>{

    private final Context context;

    private ArrayList<Movie> moviesList;

    //The current page in the moviesdb
    private int page;

    private final MovieAdapterOnClickHandler clickHandler;

    public interface MovieAdapterOnClickHandler{
        void onClick(Movie movie);
    }

    /**
     * Constructor, set the context, the clickHandler and initialize the page to 2 (1 is always charged the first time)
     * @param context Context
     * @param clickHandler clickHandler
     */
    public MovieAdapter(Context context, MovieAdapterOnClickHandler clickHandler){
        this.context = context;
        this.clickHandler = clickHandler;
        page = 2;
    }

    /**
     * Set the layout to the views
     * @param parent
     * @param viewType
     * @return
     */
    @Override
    public MovieAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        int layoutID = R.layout.movie_card;

        View view = LayoutInflater.from(context).inflate(layoutID, parent, false);

        return new MovieAdapter.MovieAdapterViewHolder(view);
    }

    /**
     * Set the image for each movie and add if is the last position call a GET to get more movies
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(MovieAdapterViewHolder holder, int position) {

        if(getItemCount()>0){

            String image = MoviesAPI.IMG_URL_BASE +
                    MoviesAPI.IMG_SIZE_PHONE +
                    moviesList.get(position).getPosterImage();


            Picasso.with(context)
                    .load(image)
                    .error(R.drawable.no_poster)
                    .into(holder.movieImage);
        }

        if(!MoviesAPI.currentFilter.equals(MoviesAPI.FILTER_FAVORITE)){

            //If we are in the last position do a GET to get more movies
            if(getItemCount()-1 == position) {

                MoviesAPI.getMovies(context,
                        MoviesAPI.BASE_URL +
                                MoviesAPI.currentFilter +
                                MoviesAPI.API_KEY +
                                MoviesAPI.PAGE_BASE +
                                page,
                        moviesList,
                        new MoviesAPI.VolleyCallback() {
                            // Put new ArrayList and add +1 to page variable
                            @Override
                            public void onSuccess(ArrayList<Movie> arrayList) {
                                swapMovies(arrayList);
                                page++;
                            }

                            @Override
                            public void onError() {

                                Toast.makeText(context, context.getResources().getString(R.string.no_internet_connexion), Toast.LENGTH_LONG).show();

                            }
                        });
            }

        }
    }

    /**
     * Get the number of items
     * @return number of items
     */
    @Override
    public int getItemCount() {
        if(moviesList == null) return 0;
        return moviesList.size();
    }

    /**
     * Set a new ArrayList to the Adapter
     * @param arrayList
     */
    public void swapMovies(ArrayList<Movie> arrayList){
        moviesList = arrayList;
        notifyDataSetChanged();
    }


    class MovieAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        @BindView(R.id.movieImage)      ImageView movieImage;
        @BindView(R.id.movieCardLayout) View movieCardLayout;


        /**
         * Bind Views and set ClickListener
         * @param itemView
         */
        public MovieAdapterViewHolder(View itemView){
            super(itemView);

            ButterKnife.bind(this, itemView);

            itemView.setOnClickListener(this);

        }

        /**
         * Call onClick
         * @param view
         */
        @Override
        public void onClick(View view) {

            int position = getAdapterPosition();

            clickHandler.onClick(moviesList.get(position));

        }
    }
}
