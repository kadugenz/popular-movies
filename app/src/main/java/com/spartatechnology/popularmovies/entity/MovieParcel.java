package com.spartatechnology.popularmovies.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.spartatechnology.moviedbapi.entity.Movie;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kadu on 6/8/16.
 */
public class MovieParcel implements Parcelable {
    private Long id;
    private String posterPath;

    public MovieParcel(Movie movie) {
        setId(movie.getId());
        setPosterPath(movie.getPosterPath());
    }

    public MovieParcel(Parcel source) {
        setId(source.readLong());
        setPosterPath(source.readString());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(getId());
        dest.writeString(getPosterPath());
    }

    public static final Parcelable.Creator<MovieParcel> CREATOR = new Parcelable.Creator<MovieParcel>() {
        public MovieParcel createFromParcel(Parcel in) {
            return new MovieParcel(in);
        }

        public MovieParcel[] newArray(int size) {
            return new MovieParcel[size];
        }
    };

    /**
     * @return
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return
     */
    public String getPosterPath() {
        return posterPath;
    }

    /**
     * @param posterPath
     */
    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    /**
     * @param list
     * @return
     */
    public static ArrayList<Movie> toMovieList(ArrayList<MovieParcel> list) {
        final ArrayList<Movie> movies = new ArrayList<>();
        for (MovieParcel movieParcel : list) {
            Movie movie = new Movie();
            movie.setId(movieParcel.getId());
            movie.setPosterPath(movieParcel.getPosterPath());
            movies.add(movie);
        }
        return movies;
    }

    /**
     * @param list
     * @return
     */
    public static ArrayList<MovieParcel> fromMovieList(List<Movie> list) {
        final ArrayList<MovieParcel> movies = new ArrayList<>();
        for (Movie movie : list) {
            movies.add(new MovieParcel(movie));
        }
        return movies;
    }
}
