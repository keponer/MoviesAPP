package com.example.arg_a.moviesapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.arg_a.moviesapp.Model.MovieReview;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by arg-a on 04/03/2018.
 */

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewAdapterViewHolder> {

    private final Context context;

    private ArrayList<MovieReview> movieReviewList;

    public ReviewAdapter (Context context){
        this.context = context;
    }

    @Override
    public ReviewAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        int layoutID = R.layout.movie_review;

        View view = LayoutInflater.from(context).inflate(layoutID, parent, false);

        return new ReviewAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ReviewAdapter.ReviewAdapterViewHolder holder, int position) {

        holder.autorReview.setText(movieReviewList.get(position).getAuthor() + ":");
        holder.movieReview.setText(movieReviewList.get(position).getContent() + "\n");

    }

    public void swapReviews(ArrayList<MovieReview> movieReviews){
        this.movieReviewList = movieReviews;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if(movieReviewList == null) return 0;
        return movieReviewList.size();
    }

    public class ReviewAdapterViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.movie_review)
        TextView movieReview;
        @BindView(R.id.movie_review_author) TextView autorReview;

        public ReviewAdapterViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);

        }
    }
}
