package com.spartatechnology.moviedbapi.exception;

/**
 * Created by kadu on 5/10/16.
 */
public class MovieDbException extends RuntimeException {

    public MovieDbException(String message) {
        super(message);
    }

    public MovieDbException(String message, Throwable cause) {
        super(message, cause);
    }
}
