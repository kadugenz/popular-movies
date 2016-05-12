package com.spartatechnology.popularmovies.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.spartatechnology.moviedbapi.entity.Movie;
import com.spartatechnology.popularmovies.R;
import com.spartatechnology.popularmovies.activity.MovieDetailActivity;
import com.spartatechnology.popularmovies.adapter.MoviePosterAdapter;
import com.spartatechnology.popularmovies.async.LoadMoviesAsync;
import com.spartatechnology.popularmovies.constant.MoviesSortType;
import com.spartatechnology.popularmovies.constant.PopularMoviesConstants;

import java.util.ArrayList;
import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends AbstractMovieFragment implements LoadMoviesAsync.Listener {
    private static final String LOG_TAG = MainActivityFragment.class.getSimpleName();

    private MoviePosterAdapter moviePosterAdapter;

    public MainActivityFragment() {
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        moviePosterAdapter = new MoviePosterAdapter(getActivity(), new ArrayList<Movie>());

        final View root = inflater.inflate(R.layout.fragment_main, container, false);

        GridView gridview = (GridView) root.findViewById(R.id.popular_movies_grid_view);
        gridview.setAdapter(moviePosterAdapter);

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                final Movie selected = moviePosterAdapter.getItem(position);
                Log.d(LOG_TAG, "Selected movie id: " + selected.getId());

                final Bundle movieBundle = new Bundle();
                movieBundle.putLong(PopularMoviesConstants.EXTRA_MOVIE_INFO_ID, selected.getId());
                movieBundle.putString(PopularMoviesConstants.EXTRA_MOVIE_INFO_IMAGE, selected.getPosterPath());

                final Intent intent = new Intent(getActivity(), MovieDetailActivity.class).putExtra(PopularMoviesConstants.EXTRA_MOVIE_INFO, movieBundle);
                startActivity(intent);
            }
        });

        return root;
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

    @Override
    public void onStart() {
        super.onStart();
        Log.d(LOG_TAG, "Started");
        final MoviesSortType sort = getSortType();
        setTitleBarText(sort);

        final LoadMoviesAsync loadMoviesAsync = new LoadMoviesAsync(getMovieDbApi(), this);
        loadMoviesAsync.execute(sort);
    }

    @Override
    public void onMovieListLoaded(List<Movie> movies) {
        moviePosterAdapter.clear();
        moviePosterAdapter.addAll(movies);
    }

    @Override
    public void onMovieListError(Throwable cause) {
        Log.e(LOG_TAG, "Error loading movies", cause);
    }
}
