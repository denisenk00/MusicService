package com.denysenko.musicservice;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import java.io.Serializable;
import java.util.List;

public class Album implements Serializable {
    private String poster;
    private String title;
    private String singer;

    @JacksonXmlElementWrapper(localName = "tracks")
    @JacksonXmlProperty(localName = "track")
    private List<Track> tracks;

    public Album() {
    }

    public Album(String poster, String title, String singer, List<Track> tracks) {
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

    public List<Track> getTracks() {
        return tracks;
    }

    public void setTracks(List<Track> tracks) {
        this.tracks = tracks;
    }
}
