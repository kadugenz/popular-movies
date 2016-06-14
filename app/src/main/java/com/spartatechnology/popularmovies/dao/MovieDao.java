package com.spartatechnology.popularmovies.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import com.spartatechnology.moviedbapi.entity.Movie;

/**
 * Created by kadu on 6/8/16.
 */
public class MovieDao {
    private static final String LOG_TAG = MovieDao.class.getSimpleName();

    private Context mContext;

    public MovieDao(Context context) {
        this.mContext = context;
    }

    /**
     * @param movie
     * @return
     */
    public boolean isFavorite(Movie movie) {
        final Cursor cursor = mContext.getContentResolver().query(
                MovieProvider.Movies.CONTENT_URI,
                null,   // projection
                MovieColumns._ID + " = ?", // selection
                new String[] { Long.toString(movie.getId()) },   // selectionArgs
                null    // sort order
        );
        final int numRows = cursor.getCount();
        cursor.close();
        return numRows != 0;
    }

    /**
     * @param movie
     * @return
     */
    public boolean toggleFavorite(Movie movie) {
        final boolean favorite = isFavorite(movie);

        if (favorite) {
            final int result = mContext.getContentResolver().delete(MovieProvider.Movies.withId(movie.getId()), null, null);
            Log.d(LOG_TAG, "Removal result: " + result);
        } else {
            final ContentValues cv = new ContentValues();
            cv.put(MovieColumns._ID, movie.getId());
            cv.put(MovieColumns.POSTER_ART, movie.getPosterPath());

            final Uri uri = mContext.getContentResolver().insert(MovieProvider.Movies.CONTENT_URI, cv);
            Log.d(LOG_TAG, "Inserting favorite url: " + uri);
        }

        return !favorite;
    }

}
