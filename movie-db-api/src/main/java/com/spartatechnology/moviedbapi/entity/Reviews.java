package com.spartatechnology.moviedbapi.entity;

import java.util.List;

/**
 * Created by kadu on 6/8/16.
 */
public class Reviews {
    private List<Review> results;

    public List<Review> getResults() {
        return results;
    }

    public void setResults(List<Review> results) {
        this.results = results;
    }
}
