package com.denysenko.musicservice.controllers;

import com.denysenko.musicservice.Request;
import com.denysenko.musicservice.Response;
import com.denysenko.musicservice.service.MusicService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
public class Controller {
    private Response response;
    private Request request;
    private MusicService musicService;

    public Controller(Response response, Request request, MusicService musicService) {
        this.response = response;
        this.request = request;
        this.musicService = musicService;
    }

    @GetMapping ("/search")
    public ResponseEntity search(@RequestParam(name = "track", required = false) String track,
                                 @RequestParam(name = "singer", required = false) String singer,
                                 @RequestParam(name = "format") String format){
        request.setTrack(track);
        request.setSinger(singer);
        request.setFormat(format);
        response = musicService.getInfo();
        return ResponseEntity.ok().body(response);
    }
}
