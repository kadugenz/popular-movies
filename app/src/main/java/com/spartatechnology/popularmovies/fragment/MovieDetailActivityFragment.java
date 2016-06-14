package com.spartatechnology.popularmovies.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.spartatechnology.moviedbapi.entity.Movie;
import com.spartatechnology.moviedbapi.entity.MovieDetail;
import com.spartatechnology.moviedbapi.entity.Video;
import com.spartatechnology.popularmovies.R;
import com.spartatechnology.popularmovies.adapter.ReviewsAdapter;
import com.spartatechnology.popularmovies.adapter.TrailersAdapter;
import com.spartatechnology.popularmovies.async.LoadMovieDetailAsync;
import com.spartatechnology.popularmovies.constant.PopularMoviesConstants;
import com.spartatechnology.popularmovies.dao.MovieDao;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MovieDetailActivityFragment extends AbstractMovieFragment implements LoadMovieDetailAsync.Listener, TrailersAdapter.TrailerSelectedListener {
    private static final String LOG_TAG = MovieDetailActivityFragment.class.getSimpleName();

    @BindView(R.id.movie_detail_image) ImageView mMovieDetailImage;
    @BindView(R.id.movie_detail_title) TextView mMovieDetailTitle;
    @BindView(R.id.movie_detail_release_year) TextView mMovieDetailReleaseYear;
    @BindView(R.id.movie_detail_runtime) TextView mMovieDetailRuntime;
    @BindView(R.id.movie_detail_rating) TextView mMovieDetailRating;
    @BindView(R.id.movie_detail_description) TextView mMovieDetailDescription;
    @BindView(R.id.movie_detail_favorite_button) FloatingActionButton mMovieFavoriteButton;
    @BindView(R.id.movie_detail_trailers) RecyclerView mMovieDetailTrailers;
    @BindView(R.id.movie_detail_reviews) RecyclerView mMovieDetailReviews;

    private MovieDao movieDao;
    private Movie basicMovieInfo;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View root = inflater.inflate(R.layout.fragment_movie_detail, container, false);
        ButterKnife.bind(this, root);

        movieDao = new MovieDao(getContext());

        if (getArguments() != null) {
            basicMovieInfo = new Movie();
            basicMovieInfo.setId(getArguments().getLong(PopularMoviesConstants.EXTRA_MOVIE_INFO_ID));
            basicMovieInfo.setPosterPath(getArguments().getString(PopularMoviesConstants.EXTRA_MOVIE_INFO_IMAGE));

            final String imageUri = PopularMoviesConstants.PICASSO_URL + basicMovieInfo.getPosterPath();
            Picasso.with(getContext()).load(imageUri).placeholder(R.drawable.movie_placeholder).into(mMovieDetailImage);

            setFavoriteIcon(movieDao.isFavorite(basicMovieInfo));

        }

        mMovieDetailTrailers.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        mMovieDetailTrailers.setAdapter(new TrailersAdapter(this));

        mMovieDetailReviews.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        mMovieDetailReviews.setAdapter(new ReviewsAdapter());

        mMovieFavoriteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final boolean favorite = movieDao.toggleFavorite(basicMovieInfo);
                setFavoriteIcon(favorite);
            }
        });

        return root;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (getArguments() != null) {
            basicMovieInfo = new Movie();
            basicMovieInfo.setId(getArguments().getLong(PopularMoviesConstants.EXTRA_MOVIE_INFO_ID));
            basicMovieInfo.setPosterPath(getArguments().getString(PopularMoviesConstants.EXTRA_MOVIE_INFO_IMAGE));

            final String imageUri = PopularMoviesConstants.PICASSO_URL + basicMovieInfo.getPosterPath();
            Picasso.with(getContext()).load(imageUri).placeholder(R.drawable.movie_placeholder).into(mMovieDetailImage);

            setFavoriteIcon(movieDao.isFavorite(basicMovieInfo));

            new LoadMovieDetailAsync(getMovieDbApi(), this).execute(basicMovieInfo.getId());
        }
    }

    /**
     * @param favorite
     */
    private void setFavoriteIcon(boolean favorite) {
        mMovieFavoriteButton.setImageResource(favorite ? R.drawable.ic_favorite: R.drawable.ic_favorite_outline);
    }

    @Override
    public void onMovieLoaded(MovieDetail movieDetail) {
        mMovieDetailTitle.setText(movieDetail.getOriginalTitle());
        mMovieDetailReleaseYear.setText(PopularMoviesConstants.RELEASE_YEAR.format(movieDetail.getReleaseDate()));
        mMovieDetailRuntime.setText(String.format("%s: %dmin", getString(R.string.duration_label), movieDetail.getRuntime()));
        mMovieDetailRating.setText(String.format("%s: %s/10", getString(R.string.rating_label),movieDetail.getVoteAverage()));
        mMovieDetailDescription.setText(movieDetail.getOverview());

        mMovieDetailTrailers.setAdapter(new TrailersAdapter(this, movieDetail.getTrailers().getYoutube()));
        mMovieDetailReviews.setAdapter(new ReviewsAdapter(movieDetail.getReviews().getResults()));
    }

    @Override
    public void onMovieError(Throwable cause) {
        Log.e(LOG_TAG, "Error loading movie", cause);
    }

    @Override
    public void onTrailerSelected(Video video) {
        Log.d(LOG_TAG, "Video Selected: " + video.getName());

        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.youtube.com/watch?v=" + video.getSource())));
    }
}
