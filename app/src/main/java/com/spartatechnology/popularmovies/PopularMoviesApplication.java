package com.spartatechnology.popularmovies;

import android.app.Application;

import com.spartatechnology.moviedbapi.MovieDbApi;

/**
 * Created by kadu on 5/10/16.
 */
public class PopularMoviesApplication extends Application {
    private MovieDbApi movieDbApi;

    @Override
    public void onCreate() {
        super.onCreate();
        movieDbApi = new MovieDbApi(BuildConfig.MOVIE_DB_API_KEY);
    }

    public MovieDbApi getMovieDbApi() {
        return movieDbApi;
    }
}
