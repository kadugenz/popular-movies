package com.spartatechnology.popularmovies.dao;

import net.simonvt.schematic.annotation.DataType;
import net.simonvt.schematic.annotation.NotNull;
import net.simonvt.schematic.annotation.PrimaryKey;

/**
 * Created by kadu on 6/8/16.
 */
public interface MovieColumns {
    @DataType(DataType.Type.INTEGER) @PrimaryKey
    public static final String _ID = "_id";

    @DataType(DataType.Type.TEXT) @NotNull
    public static final String POSTER_ART = "poster_art";
}
