package com.spartatechnology.moviedbapi.entity;

import java.util.List;

/**
 * Created by kadu on 6/8/16.
 */
public class Trailers {
    private List<Video> youtube;

    public List<Video> getYoutube() {
        return youtube;
    }

    public void setYoutube(List<Video> youtube) {
        this.youtube = youtube;
    }
}
