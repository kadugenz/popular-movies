package com.spartatechnology.moviedbapi.entity;

import java.util.List;

/**
 * Created by kadu on 5/10/16.
 */
public class Movies {
    private List<Movie> results;

    public List<Movie> getResults() {
        return results;
    }

    public void setResults(List<Movie> results) {
        this.results = results;
    }
}
