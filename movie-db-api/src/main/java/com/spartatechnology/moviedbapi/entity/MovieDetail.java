package com.spartatechnology.moviedbapi.entity;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by kadu on 5/10/16.
 */
public class MovieDetail extends Movie {
    private String overview;
    private Date releaseDate;
    private String originalTitle;
    private BigDecimal voteAverage;
    private Long runtime;

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public BigDecimal getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(BigDecimal voteAverage) {
        this.voteAverage = voteAverage;
    }

    public Long getRuntime() {
        return runtime;
    }

    public void setRuntime(Long runtime) {
        this.runtime = runtime;
    }
}
