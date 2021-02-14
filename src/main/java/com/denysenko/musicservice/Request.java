package com.denysenko.musicservice;

import org.springframework.stereotype.Component;

@Component
public class Request {
    private String track;
    private String singer;
    private String format;

    public Request(){}
    public Request(String track, String singer, String format) {
        this.track = track;
        this.singer = singer;
        this.format = format;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getTrack() {
        return track;
    }

    public void setTrack(String track) {
        this.track = track;
    }

    public String getSinger() {
        return singer;
    }

    public void setSinger(String singer) {
        this.singer = singer;
    }
}
