package com.spartatechnology.popularmovies.fragment;

import android.support.v4.app.Fragment;

import com.spartatechnology.moviedbapi.MovieDbApi;
import com.spartatechnology.popularmovies.PopularMoviesApplication;

/**
 * Created by kadu on 5/11/16.
 */
public abstract class AbstractMovieFragment extends Fragment {

    /**
     * Gets the movie db api
     *
     * @return
     */
    public MovieDbApi getMovieDbApi() {
       return ((PopularMoviesApplication)getActivity().getApplication()).getMovieDbApi();
    }
}
