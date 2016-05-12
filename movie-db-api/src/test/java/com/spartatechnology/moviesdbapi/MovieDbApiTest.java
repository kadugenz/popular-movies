package com.spartatechnology.moviesdbapi;

import com.spartatechnology.moviedbapi.MovieDbApi;
import com.spartatechnology.moviedbapi.entity.Movie;
import com.spartatechnology.moviedbapi.entity.MovieDetail;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

/**
 * Created by kadu on 5/10/16.
 */
public class MovieDbApiTest {
    private static final String API_KEY = "3247a8993ebe4347d1f0622edc5cc8c6";

    private static MovieDbApi movieDbApi;

    @BeforeClass
    public static void init() {
        movieDbApi = new MovieDbApi(API_KEY);
    }

    @Test
    public void testRetrieveMovieDetail() throws IOException {
        final MovieDetail movie = movieDbApi.retrieveMovieDetail(550L);

        MatcherAssert.assertThat(movie, Matchers.notNullValue());
        MatcherAssert.assertThat(movie.getId(), Matchers.notNullValue());
        MatcherAssert.assertThat(movie.getPosterPath(), Matchers.notNullValue());
        MatcherAssert.assertThat(movie.getOriginalTitle(), Matchers.notNullValue());
        MatcherAssert.assertThat(movie.getOverview(), Matchers.notNullValue());
        MatcherAssert.assertThat(movie.getReleaseDate(), Matchers.notNullValue());
        MatcherAssert.assertThat(movie.getVoteAverage(), Matchers.notNullValue());
    }

    @Test
    public void testRetrievePopularMovies() throws IOException {
        final List<Movie> movies = movieDbApi.retrievePopularMovies();

        MatcherAssert.assertThat(movies, Matchers.notNullValue());
        MatcherAssert.assertThat(movies.isEmpty(), Matchers.is(false));
    }

    @Test
    public void testRetrieveTopRatedMovies() throws IOException {
        final List<Movie> movies = movieDbApi.retrieveTopRatedMovies();

        MatcherAssert.assertThat(movies, Matchers.notNullValue());
        MatcherAssert.assertThat(movies.isEmpty(), Matchers.is(false));
    }

}
