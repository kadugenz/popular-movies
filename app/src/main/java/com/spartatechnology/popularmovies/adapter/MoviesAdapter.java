package com.spartatechnology.popularmovies.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.spartatechnology.moviedbapi.entity.Movie;
import com.spartatechnology.popularmovies.R;
import com.spartatechnology.popularmovies.constant.PopularMoviesConstants;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kadu on 5/10/16.
 */
public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.ViewHolder> {

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener  {
        private Movie movie;
        private ImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            imageView = (ImageView) itemView.findViewById(R.id.movie_item_image_view);
        }

        @Override
        public void onClick(View view) {
            movieSelected(this.movie);
        }
    }

    public interface OnMovieSelectedListener {
        void onMovieListSelected(Movie movie);
    }

    private List<Movie> mMovieList;
    private Context mContext;
    private OnMovieSelectedListener mOnMovieSelectedListener;

    public MoviesAdapter(Context context, OnMovieSelectedListener onMovieSelectedListener) {
        this.mContext = context;
        this.mOnMovieSelectedListener = onMovieSelectedListener;
        this.mMovieList = new ArrayList<>();
    }

    public void movieSelected(Movie movie) {
        mOnMovieSelectedListener.onMovieListSelected(movie);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_item, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Movie movie = mMovieList.get(position);

        final String imageUri = PopularMoviesConstants.PICASSO_URL + movie.getPosterPath();
        Picasso.with(mContext).load(imageUri).placeholder(R.drawable.movie_placeholder).into(holder.imageView);
        holder.movie = movie;
    }

    @Override
    public int getItemCount() {
        return mMovieList.size();
    }

    public void setMovieList(List<Movie> movies) {
        this.mMovieList = movies;
        notifyDataSetChanged();
    }

    public void clear() {
        this.mMovieList.clear();
        notifyDataSetChanged();
    }
}
