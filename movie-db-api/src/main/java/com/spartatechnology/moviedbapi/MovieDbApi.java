package com.spartatechnology.moviedbapi;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.spartatechnology.moviedbapi.entity.Movie;
import com.spartatechnology.moviedbapi.entity.MovieDetail;
import com.spartatechnology.moviedbapi.entity.MovieList;
import com.spartatechnology.moviedbapi.exception.MovieDbException;
import com.spartatechnology.moviedbapi.service.MovieDbService;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by kadu on 5/10/16.
 */
public class MovieDbApi {
    private static final String MOVIE_DB_API_BASE_URL = "https://api.themoviedb.org/3/";

    private MovieDbService movieDbService;

    /**
     * Initializes the api using the given api key and ENGLISH as default language
     * @param movieDbApiKey
     */
    public MovieDbApi(String movieDbApiKey) {
        this(movieDbApiKey, Locale.ENGLISH);
    }

    /**
     * Initializes the api using the given api key and language
     * @param movieDbApiKey
     * @param language
     */
    public MovieDbApi(final String movieDbApiKey, final Locale language) {
        final HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);

        final OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .addInterceptor(new Interceptor() {

                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        final Request request = chain.request();
                        final HttpUrl url = request.url().newBuilder()
                                .addQueryParameter("api_key", movieDbApiKey)
                                .addQueryParameter("language", language.toString())
                                .build();
                        return chain.proceed(request.newBuilder().url(url).build());
                    }

                }).build();

        final Gson gson = new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create();

        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(MOVIE_DB_API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(client)
                .build();

        movieDbService = retrofit.create(MovieDbService.class);
    }

    /**
     * Retrieves the given movie detail
     *
     * @param movieId
     * @return
     * @throws MovieDbException
     */
    public MovieDetail retrieveMovieDetail(Long movieId) throws MovieDbException {
        try {

            final Call<MovieDetail> call = movieDbService.retrieveMovieDetail(movieId);

            final retrofit2.Response<MovieDetail> response = call.execute();
            if (response.isSuccessful()) {
                return response.body();
            }

            throw new MovieDbException("Unable to complete the request successfully. HTTP code returned was [" + response.code() + "]");
        } catch (MovieDbException e) {
            throw e;
        } catch (Exception e) {
            throw new MovieDbException("Unexpected error while calling the movie db api", e);
        }
    }

    /**
     * Retrieves a list of most popular movies
     *
     * @return
     * @throws MovieDbException
     */
    public List<Movie> retrievePopularMovies() throws MovieDbException {
        try {

            final Call<MovieList> call = movieDbService.retrieveMostPopularMovies();

            final retrofit2.Response<MovieList> response = call.execute();
            if (response.isSuccessful()) {
                return response.body().getResults();
            }

            throw new MovieDbException("Unable to complete the request successfully. HTTP code returned was [" + response.code() + "]");
        } catch (MovieDbException e) {
            throw e;
        } catch (Exception e) {
            throw new MovieDbException("Unexpected error while calling the movie db api", e);
        }
    }

    /**
     * Retrieves a list of top rated movies
     *
     * @return
     * @throws MovieDbException
     */
    public List<Movie> retrieveTopRatedMovies() throws MovieDbException {
        try {

            final Call<MovieList> call = movieDbService.retrieveTopRatedMovies();

            final retrofit2.Response<MovieList> response = call.execute();
            if (response.isSuccessful()) {
                return response.body().getResults();
            }

            throw new MovieDbException("Unable to complete the request successfully. HTTP code returned was [" + response.code() + "]");
        } catch (MovieDbException e) {
            throw e;
        } catch (Exception e) {
            throw new MovieDbException("Unexpected error while calling the movie db api", e);
        }
    }
}
