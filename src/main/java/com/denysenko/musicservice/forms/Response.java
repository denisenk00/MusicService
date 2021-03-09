package com.denysenko.musicservice.forms;

import org.springframework.stereotype.Component;
import java.io.Serializable;
import java.util.Map;


@Component
public class Response implements Serializable {
    private String poster;
    private String title;
    private String singer;
    private Map<String, String> tracks;

    public Response(){}

    public Response(String poster, String title, String singer, Map<String, String> tracks) {
        this.poster = poster;
        this.title = title;
        this.singer = singer;
        this.tracks = tracks;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSinger() {
        return singer;
    }

    public void setSinger(String singer) {
        this.singer = singer;
    }

    public Map<String, String> getTracks() {
        return tracks;
    }

    public void setTracks(Map<String, String> tracks) {
        this.tracks = tracks;
    }
}
