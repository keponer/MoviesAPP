package com.example.arg_a.moviesapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.arg_a.moviesapp.Model.MovieVideo;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by arg-a on 03/03/2018.
 */

public class TrailerAdapter extends RecyclerView.Adapter<TrailerAdapter.TrailerAdapterViewHolder>{

    private final Context context;

    private ArrayList<MovieVideo> trailerList;

    private final TrailerAdapterOnClickHandler clickHandler;

    public interface TrailerAdapterOnClickHandler{
        void onClick(MovieVideo video);
    }

    public TrailerAdapter(Context context, TrailerAdapterOnClickHandler clickHandler){
        this.context = context;
        this.clickHandler = clickHandler;
    }

    @Override
    public TrailerAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        int layoutID = R.layout.movie_video;

        View view = LayoutInflater.from(context).inflate(layoutID, parent, false);

        return new TrailerAdapter.TrailerAdapterViewHolder(view);
    }

    /**
     * Set a new ArrayList to the Adapter
     * @param arrayList
     */
    public void swapMovies(ArrayList<MovieVideo> arrayList){
        trailerList = arrayList;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(TrailerAdapter.TrailerAdapterViewHolder holder, int position) {

        holder.trailerButton.setText(trailerList.get(position).getName());


    }

    @Override
    public int getItemCount() {
        if(trailerList == null) return 0;
        return trailerList.size();
    }

    public class TrailerAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        @BindView(R.id.trailer_button)
        Button trailerButton;

        public TrailerAdapterViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);

            trailerButton.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();

            clickHandler.onClick(trailerList.get(position));
        }
    }
}
