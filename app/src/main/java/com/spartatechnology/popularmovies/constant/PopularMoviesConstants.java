package com.spartatechnology.popularmovies.constant;

import java.text.SimpleDateFormat;

/**
 * Created by kadu on 5/11/16.
 */
public abstract class PopularMoviesConstants {
    public static final String EXTRA_MOVIE_INFO_ID = "EXTRA_MOVIE_INFO_ID";
    public static final String EXTRA_MOVIE_INFO_IMAGE = "EXTRA_MOVIE_INFO_IMAGE";

    public static final String PARCEL_MOVIE_LIST = "PARCEL_MOVIE_LIST";

    public static final String PICASSO_URL = "http://image.tmdb.org/t/p/w342/";

    public static final SimpleDateFormat RELEASE_YEAR = new SimpleDateFormat("yyyy");
}
