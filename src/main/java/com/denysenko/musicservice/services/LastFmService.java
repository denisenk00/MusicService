package com.denysenko.musicservice.services;

import com.denysenko.musicservice.Album;
import com.denysenko.musicservice.exceptions.RestServiceException;
import com.denysenko.musicservice.parsers.JsonParser;
import org.apache.http.client.utils.URIBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;


@Service
public class LastFmService implements MusicService {
    @Value("${API.key}")
    private String apiKey;
    private static Logger logger = LogManager.getLogger(LastFmService.class);

    @Override
    public Album getAlbum(String track, String singer) throws RestServiceException {
        logger.debug("Method \"getInfo\" was called");
        URIBuilder uriBuilder = new URIBuilder();
        uriBuilder.setScheme("http").setHost("ws.audioscrobbler.com")
                .setPath("/2.0/").addParameter("method", "track.getInfo")
                .addParameter("track", track)
                .addParameter("artist", singer).addParameter("api_key", apiKey)
                .addParameter("format", "json");
        URI uriSearchTrack = null;
        try {
            uriSearchTrack = uriBuilder.build();
        } catch (URISyntaxException e) {
            logger.error("Address for request to last.fm api was not created", e);
        }

        final String stringPosts = readJsonFromUrl(uriSearchTrack);
        String albumName = JsonParser.getAlbumTitle(stringPosts);

        uriBuilder.clearParameters();
        uriBuilder.setPath("/2.0/").addParameter("method", "album.getinfo").addParameter("api_key", apiKey)
                .addParameter("artist", singer).addParameter("album", albumName)
                .addParameter("format", "json");
        URI uriGetAlbumInfo = null;
        try {
            uriGetAlbumInfo = uriBuilder.build();
        } catch (URISyntaxException e) {
            logger.error("Address for request to last.fm api was not created", e);
        }
        final String secondStringPosts = readJsonFromUrl(uriGetAlbumInfo);
        return JsonParser.parseAlbum(secondStringPosts);
    }


    public String readJsonFromUrl(URI uri) {
        logger.debug("Method \"readJsonFromUrl\" was called");
        StringBuilder stringBuilder = new StringBuilder();

        try (BufferedReader in = new BufferedReader(new InputStreamReader(uri.toURL().openStream()))) {
            logger.debug("Address for request to last.fm api was successfully created");
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                stringBuilder.append(inputLine);
            }
            logger.debug("Information from url was successfully read");
        } catch (IOException e) {
            logger.error("Information from url was not read", e);
        }
        return stringBuilder.toString();
    }

}
