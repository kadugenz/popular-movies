package com.spartatechnology.popularmovies.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.spartatechnology.moviedbapi.entity.Review;
import com.spartatechnology.popularmovies.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kadu on 5/10/16.
 */
public class ReviewsAdapter extends RecyclerView.Adapter<ReviewsAdapter.ViewHolder> {

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView contentView;

        public ViewHolder(View itemView) {
            super(itemView);
            contentView = (TextView) itemView.findViewById(R.id.review_content);
        }

    }

    private List<Review> mReviewList;

    public ReviewsAdapter() {
        this(new ArrayList<Review>());
    }

    public ReviewsAdapter(List<Review> reviewList) {
        this.mReviewList = reviewList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.review_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Review review = mReviewList.get(position);

        holder.contentView.setText(review.getAuthor() + ": " + review.getContent());
    }

    @Override
    public int getItemCount() {
        return mReviewList.size();
    }
}
