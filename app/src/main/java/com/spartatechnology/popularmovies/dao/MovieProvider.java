package com.spartatechnology.popularmovies.dao;

import android.net.Uri;

import net.simonvt.schematic.annotation.ContentProvider;
import net.simonvt.schematic.annotation.ContentUri;
import net.simonvt.schematic.annotation.InexactContentUri;
import net.simonvt.schematic.annotation.TableEndpoint;

/**
 * Created by kadu on 6/8/16.
 */
@ContentProvider(authority = MovieProvider.AUTHORITY, database = MovieDatabase.class)
public final class MovieProvider {
    static final String AUTHORITY = "com.spartatechnology.popularmovies.app";
    static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);

    /**
     * @param paths
     * @return
     */
    private static Uri buildUri(String... paths) {
        Uri.Builder builder = BASE_CONTENT_URI.buildUpon();
        for (String path : paths) {
            builder.appendPath(path);
        }
        return builder.build();
    }

    @TableEndpoint(table = MovieDatabase.MOVIES)
    public static class Movies {

        @ContentUri(
                path = MovieDatabase.MOVIES,
                type = "vnd.android.cursor.dir/list")
        public static final Uri CONTENT_URI = buildUri(MovieDatabase.MOVIES);

        @InexactContentUri(
                path = MovieDatabase.MOVIES + "/#",
                name = "MOVIE_ID",
                type = "vnd.android.cursor.item/list",
                whereColumn = MovieColumns._ID,
                pathSegment = 1)
        public static Uri withId(long id) {
            return buildUri(MovieDatabase.MOVIES, String.valueOf(id));
        }

    }
}
