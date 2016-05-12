package com.spartatechnology.moviedbapi.service;

import com.spartatechnology.moviedbapi.entity.MovieDetail;
import com.spartatechnology.moviedbapi.entity.MovieList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by kadu on 5/10/16.
 */
public interface MovieDbService {

    @GET("movie/{id}")
    Call<MovieDetail> retrieveMovieDetail(@Path("id") Long movieId);

    @GET("movie/popular")
    Call<MovieList> retrieveMostPopularMovies();

    @GET("movie/top_rated")
    Call<MovieList> retrieveTopRatedMovies();
}
