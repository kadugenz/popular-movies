package com.spartatechnology.popularmovies.async;

import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;

import com.spartatechnology.moviedbapi.MovieDbApi;
import com.spartatechnology.moviedbapi.entity.Movie;
import com.spartatechnology.moviedbapi.exception.MovieDbException;
import com.spartatechnology.popularmovies.constant.MoviesSortType;
import com.spartatechnology.popularmovies.dao.MovieColumns;
import com.spartatechnology.popularmovies.dao.MovieProvider;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kadu on 5/10/16.
 */
public class LoadMoviesAsync extends AsyncTask<MoviesSortType, Void, List<Movie>> {

    private Context mContext;
    private MovieDbApi movieDbApi;
    private Listener listener;

    /**
     * Creates a async task with the api and the listener
     *
     * @param context
     * @param movieDbApi
     * @param listener
     */
    public LoadMoviesAsync(Context context, MovieDbApi movieDbApi, Listener listener) {
        this.mContext = context;
        this.movieDbApi = movieDbApi;
        this.listener = listener;
    }

    @Override
    protected List<Movie> doInBackground(MoviesSortType... sortTypes) {
        try {
            switch (sortTypes[0]) {
                case MOST_POPULAR:
                    return movieDbApi.retrievePopularMovies();
                case TOP_RATED:
                    return movieDbApi.retrieveTopRatedMovies();
                case FAVORITES:
                    return retrieveFavorites();
            }
        } catch (MovieDbException e) {
            listener.onMovieListError(e);
        }

        // If we have an error, return empty
        return new ArrayList<>();
    }

    /**
     * @return
     */
    private List<Movie> retrieveFavorites() {
        final List<Movie> movies = new ArrayList<>();

        final Cursor cursor = mContext.getContentResolver().query(MovieProvider.Movies.CONTENT_URI, null, null, null, null);
        try {
            if (cursor.moveToFirst()) {
                do {
                    Movie movie = new Movie();
                    movie.setId(cursor.getLong(cursor.getColumnIndex(MovieColumns._ID)));
                    movie.setPosterPath(cursor.getString(cursor.getColumnIndex(MovieColumns.POSTER_ART)));
                    movies.add(movie);
                }  while (cursor.moveToNext());
            }
        } finally {
            cursor.close();
        }

        return movies;
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
