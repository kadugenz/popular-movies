package com.spartatechnology.popularmovies.async;

import android.os.AsyncTask;

import com.spartatechnology.moviedbapi.MovieDbApi;
import com.spartatechnology.moviedbapi.entity.Movie;
import com.spartatechnology.moviedbapi.exception.MovieDbException;
import com.spartatechnology.popularmovies.constant.MoviesSortType;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kadu on 5/10/16.
 */
public class LoadMoviesAsync extends AsyncTask<MoviesSortType, Void, List<Movie>> {

    private MovieDbApi movieDbApi;
    private Listener listener;

    /**
     * Creates a async task with the api and the listener
     *
     * @param movieDbApi
     * @param listener
     */
    public LoadMoviesAsync(MovieDbApi movieDbApi, Listener listener) {
        this.movieDbApi = movieDbApi;
        this.listener = listener;
    }

    @Override
    protected List<Movie> doInBackground(MoviesSortType... sortTypes) {
        try {
            if (sortTypes[0] == MoviesSortType.MOST_POPULAR) {
                return movieDbApi.retrievePopularMovies();
            } else if (sortTypes[0] == MoviesSortType.TOP_RATED) {
                return movieDbApi.retrieveTopRatedMovies();
            }
        } catch (MovieDbException e) {
            listener.onMovieListError(e);
        }

        // If we have an error, return empty
        return new ArrayList<>();
    }

    @Override
    protected void onPostExecute(List<Movie> movies) {
        listener.onMovieListLoaded(movies);
    }

    public interface Listener {
        void onMovieListLoaded(List<Movie> movies);
        void onMovieListError(Throwable cause);
    }
}
