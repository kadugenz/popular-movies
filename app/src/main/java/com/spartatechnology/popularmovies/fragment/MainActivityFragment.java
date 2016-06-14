package com.spartatechnology.popularmovies.fragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.spartatechnology.moviedbapi.entity.Movie;
import com.spartatechnology.popularmovies.R;
import com.spartatechnology.popularmovies.adapter.MoviesAdapter;
import com.spartatechnology.popularmovies.async.LoadMoviesAsync;
import com.spartatechnology.popularmovies.constant.MoviesSortType;
import com.spartatechnology.popularmovies.constant.PopularMoviesConstants;
import com.spartatechnology.popularmovies.entity.MovieParcel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends AbstractMovieFragment implements LoadMoviesAsync.Listener, MoviesAdapter.OnMovieSelectedListener {
    private static final String LOG_TAG = MainActivityFragment.class.getSimpleName();
    public static final String TWO_PANE = "TWO_PANE";

    @BindView(R.id.popular_movies_grid_view) RecyclerView mRecyclerView;

    private MoviesAdapter moviePosterAdapter;
    private MoviesSortType mCurrentSortType;
    private ArrayList<MovieParcel> currentMovies;
    private boolean mTwoPane;
    private int mNumColumns = 2;

    public MainActivityFragment() {
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View root = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.bind(this, root);
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (getArguments() != null) {
            mTwoPane = getArguments().getBoolean(TWO_PANE);
        }

        moviePosterAdapter = new MoviesAdapter(getActivity(), this);

        mRecyclerView.setAdapter(moviePosterAdapter);
        mNumColumns = isTablet() || mTwoPane ? 3 : 2;
        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), mNumColumns));

        mCurrentSortType = getSortType();

        if (savedInstanceState != null) {
            currentMovies = savedInstanceState.getParcelableArrayList(PopularMoviesConstants.PARCEL_MOVIE_LIST);
            if (currentMovies != null) {
                moviePosterAdapter.setMovieList(MovieParcel.toMovieList(currentMovies));
            }
        } else if (currentMovies == null) {
            new LoadMoviesAsync(getContext(), getMovieDbApi(), this).execute(mCurrentSortType);
        }

        setTitleBarText(mCurrentSortType);
    }

    /**
     * Sets the title bar according to the sort type received
     * @param sortType
     */
    private void setTitleBarText(MoviesSortType sortType) {
        final StringBuilder sb = new StringBuilder(getString(R.string.title_bar_text));

        if (sortType == MoviesSortType.MOST_POPULAR) {
            sb.append(" - ").append(getString(R.string.pref_sort_popular_label));
        } else if (sortType == MoviesSortType.TOP_RATED) {
            sb.append(" - ").append(getString(R.string.pref_sort_top_rated_label));
        } else if (sortType == MoviesSortType.FAVORITES) {
            sb.append(" - ").append(getString(R.string.pref_sort_favorites_label));
        }

        getActivity().setTitle(sb.toString());
    }

    /**
     * Gets the current setting for sort
     *
     * @return
     */
    private MoviesSortType getSortType() {
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getActivity());
        final String sort = sharedPref.getString(getString(R.string.pref_sort_key), getString(R.string.pref_sort_default));

        return MoviesSortType.valueOf(sort);
    }

    public boolean isTablet() {
        DisplayMetrics metrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int widthPixels = metrics.widthPixels;
        int heightPixels = metrics.heightPixels;
        float scaleFactor = metrics.density;
        float widthDp = widthPixels / scaleFactor;
        float heightDp = heightPixels / scaleFactor;
        float smallestWidth = Math.min(widthDp, heightDp);
        return  smallestWidth >= 600;
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(LOG_TAG, "onResume");

        if (mCurrentSortType != getSortType()) {
            mCurrentSortType = getSortType();
            setTitleBarText(mCurrentSortType);

            moviePosterAdapter.clear();
            new LoadMoviesAsync(getContext(), getMovieDbApi(), this).execute(mCurrentSortType);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(PopularMoviesConstants.PARCEL_MOVIE_LIST, currentMovies);
    }


    @Override
    public void onMovieListLoaded(List<Movie> movies) {
        moviePosterAdapter.setMovieList(movies);

        currentMovies = MovieParcel.fromMovieList(movies);
    }

    @Override
    public void onMovieListError(Throwable cause) {
        Log.e(LOG_TAG, "Error loading movies", cause);
    }

    @Override
    public void onMovieListSelected(Movie movie) {
        Log.d(LOG_TAG, "Selected movie id: " + movie.getId());

        ((MoviesAdapter.OnMovieSelectedListener) getActivity()).onMovieListSelected(movie);
//        final Bundle movieBundle = new Bundle();
//        movieBundle.putLong(PopularMoviesConstants.EXTRA_MOVIE_INFO_ID, movie.getId());
//        movieBundle.putString(PopularMoviesConstants.EXTRA_MOVIE_INFO_IMAGE, movie.getPosterPath());
//
//        final Intent intent = new Intent(getActivity(), MovieDetailActivity.class).putExtra(PopularMoviesConstants.EXTRA_MOVIE_INFO, movieBundle);
//        startActivity(intent);
    }
}
