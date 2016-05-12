package com.spartatechnology.moviedbapi.entity;

/**
 * Created by kadu on 5/10/16.
 */
public class Movie {
    private Long id;
    private String posterPath;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }
}
