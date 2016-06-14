package com.spartatechnology.popularmovies.dao;

import net.simonvt.schematic.annotation.Database;
import net.simonvt.schematic.annotation.Table;

/**
 * Created by kadu on 6/8/16.
 */
@Database(version = MovieDatabase.VERSION)
public final class MovieDatabase {
    public static final int VERSION = 1;

    @Table(MovieColumns.class) public static final String MOVIES = "movies";
}
