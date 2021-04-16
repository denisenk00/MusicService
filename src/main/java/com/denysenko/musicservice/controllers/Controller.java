package com.denysenko.musicservice.controllers;

import com.denysenko.musicservice.ExceptionResponse;
import com.denysenko.musicservice.FileWriter;
import com.denysenko.musicservice.Album;
import com.denysenko.musicservice.exceptions.RestServiceException;
import com.denysenko.musicservice.services.MusicService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
public class Controller {
    private MusicService musicService;
    private FileWriter fileWriter;
    private static Logger logger = LogManager.getLogger(Controller.class);

    public Controller(MusicService musicService, FileWriter fileWriter) {
        this.musicService = musicService;
        this.fileWriter = fileWriter;
    }

    @GetMapping(value = "/albumInfo", produces = {"application/json", "application/xml"})
    @Cacheable("albumInfo")
    public ResponseEntity searchAlbumInfo(@RequestParam(name = "track") String track,
                                          @RequestParam(name = "singer") String singer,
                                          @RequestParam(name = "file", required = false) boolean file,
                                          @RequestParam(name = "format", required = false) String format) {

        logger.info("Method \"searchAlbumInfo\" was called with parameters: track = " + track + ", singer = " + singer +
                ", file = " + file + ", format = " + format);
        HttpHeaders headers = new HttpHeaders();
        if (format == null || format.equals("json")) {
            format = "json";
            headers.setContentType(MediaType.APPLICATION_JSON);
            logger.debug("Response format \"json\" has been set");
        } else if (format.equals("xml")) {
            headers.setContentType(MediaType.APPLICATION_XML);
            logger.debug("Response format \"xml\" has been set");
        } else {
            logger.error("Invalid format for response: " + format);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ExceptionResponse(new RestServiceException(HttpStatus.BAD_REQUEST,
                    "Invalid format - This service doesn't exist in that format", "3")));
        }
        try {
            Album album = musicService.getAlbum(track, singer);
            logger.debug("Response object was received");
            if (file) {
                byte[] document = fileWriter.writeDocument(album);
                headers.add("Content-Disposition", "attachment;filename=InfoAboutAlbum.docx");
                logger.info("Information was returned as a file");
                return ResponseEntity.ok().headers(headers).body(document);
            } else {
                logger.info("Information was returned in " + format + " format");
                return ResponseEntity.ok().headers(headers).body(album);
            }
        } catch (RestServiceException e) {
            logger.debug("Information about error was returned in " + format + " format", e);
            return ResponseEntity.status(e.getHttpStatus()).headers(headers).body(new ExceptionResponse(e));
        }
    }

}
