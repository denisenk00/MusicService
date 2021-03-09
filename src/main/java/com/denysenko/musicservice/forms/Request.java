package com.denysenko.musicservice.forms;

import org.springframework.stereotype.Component;

@Component
public class Request {
    private String track;
    private String singer;

    public Request(){}
    public Request(String track, String singer) {
        this.track = track;
        this.singer = singer;
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
