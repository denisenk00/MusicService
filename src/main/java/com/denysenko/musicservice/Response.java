package com.denysenko.musicservice;

import org.springframework.stereotype.Component;

import java.awt.*;
import java.util.Map;

@Component
public class Response {
    private String poster;
    private String nameOfAlbum;
    private String genre;
    private String singer;
    private Map<String, String> table;

    public Response(){}
    public Response(String poster, String nameOfAlbum, String genre, String singer, Map<String, String> table) {
        this.poster = poster;
        this.nameOfAlbum = nameOfAlbum;
        this.genre = genre;
        this.singer = singer;
        this.table = table;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getNameOfAlbum() {
        return nameOfAlbum;
    }

    public void setNameOfAlbum(String nameOfAlbum) {
        this.nameOfAlbum = nameOfAlbum;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getSinger() {
        return singer;
    }

    public void setSinger(String singer) {
        this.singer = singer;
    }

    public Map<String, String> getTable() {
        return table;
    }

    public void setTable(Map<String, String> table) {
        this.table = table;
    }
}
