package com.denysenko.musicservice.controllers;

import com.denysenko.musicservice.exceptions.RemoteServiceException;
import com.denysenko.musicservice.model.ExceptionResponse;
import com.denysenko.musicservice.services.FileWriter;
import com.denysenko.musicservice.model.Album;
import com.denysenko.musicservice.services.MusicService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@RestController
@RequestMapping(value = "/albums")
public class AlbumController {

    private MusicService musicService;
    private FileWriter fileWriter;
    private static Logger logger = LogManager.getLogger(AlbumController.class);

    public AlbumController(MusicService musicService, FileWriter fileWriter) {
        this.musicService = musicService;
        this.fileWriter = fileWriter;
    }

    @GetMapping(produces = {"application/json", "application/xml"})
    public ResponseEntity<Album> findAlbum(@RequestParam(name = "track") String track,
                                           @RequestParam(name = "singer") String singer,
                                           @RequestHeader(value = "Accept") String acceptHeader) {

        MediaType requestedContentType = MediaType.valueOf(acceptHeader);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(requestedContentType);

        logger.info("Method \"searchAlbumInfo\" was called with parameters: track = " + track + ", singer = "
                + singer + ", format = " + requestedContentType);

        Optional<Album> optionalAlbum = musicService.findAlbum(track, singer);

        if (optionalAlbum.isPresent()) {
            return ResponseEntity.ok(optionalAlbum.get());
        } else return ResponseEntity.noContent().build();
    }

    @GetMapping(value = "/file", produces = "application/vnd.openxmlformats-officedocument.wordprocessingml.document")
    public ResponseEntity findAlbumAsDocument(@RequestParam(name = "track") String track,
                                              @RequestParam(name = "singer") String singer) {

        Optional<Album> optionalAlbum = musicService.findAlbum(track, singer);
        logger.debug("Response object was received");

        if (optionalAlbum.isEmpty()) return ResponseEntity.noContent().build();

        byte[] document = fileWriter.writeDocument(optionalAlbum.get());
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Content-Disposition", "attachment;filename=InfoAboutAlbum.docx");
        logger.info("Information was returned as a file");

        return ResponseEntity.ok().headers(httpHeaders).body(document);
    }

    @ExceptionHandler(value = RemoteServiceException.class)
    public ResponseEntity<ExceptionResponse> remoteServiceExceptionHandler(RemoteServiceException e){
        var exceptionResponse = new ExceptionResponse(e);
        return ResponseEntity.status(e.getHttpStatus()).body(exceptionResponse);
    }

}
