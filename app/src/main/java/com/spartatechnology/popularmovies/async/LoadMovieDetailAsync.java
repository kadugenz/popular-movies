package com.spartatechnology.popularmovies.async;

import android.os.AsyncTask;

import com.spartatechnology.moviedbapi.MovieDbApi;
import com.spartatechnology.moviedbapi.entity.MovieDetail;
import com.spartatechnology.moviedbapi.exception.MovieDbException;

/**
 * Created by kadu on 5/10/16.
 */
public class LoadMovieDetailAsync extends AsyncTask<Long, Void, MovieDetail> {

    private MovieDbApi movieDbApi;
    private Listener listener;

    /**
     * Creates a async task with the api and the listener
     *
     * @param movieDbApi
     * @param listener
     */
    public LoadMovieDetailAsync(MovieDbApi movieDbApi, Listener listener) {
        this.movieDbApi = movieDbApi;
        this.listener = listener;
    }

    @Override
    protected MovieDetail doInBackground(Long... ids) {
        try {
            return movieDbApi.retrieveMovieDetail(ids[0]);
        } catch (MovieDbException e) {
            listener.onMovieError(e);
        }

        // If we have an error, return null
        return null;
    }

    @Override
    protected void onPostExecute(MovieDetail movieDetail) {
        listener.onMovieLoaded(movieDetail);
    }

    public interface Listener {
        void onMovieLoaded(MovieDetail movieDetail);
        void onMovieError(Throwable cause);
    }
}
