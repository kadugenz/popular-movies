package com.spartatechnology.popularmovies.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.spartatechnology.moviedbapi.entity.MovieDetail;
import com.spartatechnology.popularmovies.R;
import com.spartatechnology.popularmovies.async.LoadMovieDetailAsync;
import com.spartatechnology.popularmovies.constant.PopularMoviesConstants;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MovieDetailActivityFragment extends AbstractMovieFragment implements LoadMovieDetailAsync.Listener {
    private static final String LOG_TAG = MovieDetailActivityFragment.class.getSimpleName();

    @BindView(R.id.movie_detail_image) ImageView mMovieDetailImage;
    @BindView(R.id.movie_detail_title) TextView mMovieDetailTitle;
    @BindView(R.id.movie_detail_release_year) TextView mMovieDetailReleaseYear;
    @BindView(R.id.movie_detail_runtime) TextView mMovieDetailRuntime;
    @BindView(R.id.movie_detail_rating) TextView mMovieDetailRating;
    @BindView(R.id.movie_detail_description) TextView mMovieDetailDescription;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View root = inflater.inflate(R.layout.fragment_movie_detail, container, false);

        ButterKnife.bind(this, root);

        final Intent intent = getActivity().getIntent();
        final Bundle movieBundle = intent.getBundleExtra(PopularMoviesConstants.EXTRA_MOVIE_INFO);

        final String imageUri = PopularMoviesConstants.PICASSO_URL + movieBundle.getString(PopularMoviesConstants.EXTRA_MOVIE_INFO_IMAGE);
        Picasso.with(getContext()).load(imageUri).placeholder(R.drawable.movie_placeholder).into(mMovieDetailImage);

        new LoadMovieDetailAsync(getMovieDbApi(), this).execute(movieBundle.getLong(PopularMoviesConstants.EXTRA_MOVIE_INFO_ID));
        return root;
    }

    @Override
    public void onMovieLoaded(MovieDetail movieDetail) {
        mMovieDetailTitle.setText(movieDetail.getOriginalTitle());
        mMovieDetailReleaseYear.setText(PopularMoviesConstants.RELEASE_YEAR.format(movieDetail.getReleaseDate()));
        mMovieDetailRuntime.setText(String.format("%s: %dmin", getString(R.string.duration_label), movieDetail.getRuntime()));
        mMovieDetailRating.setText(String.format("%s: %s/10", getString(R.string.rating_label),movieDetail.getVoteAverage()));
        mMovieDetailDescription.setText(movieDetail.getOverview());
    }

    @Override
    public void onMovieError(Throwable cause) {
        Log.e(LOG_TAG, "Error loading movie", cause);
    }
}
