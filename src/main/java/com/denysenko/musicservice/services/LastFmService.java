package com.denysenko.musicservice.services;

import com.denysenko.musicservice.forms.Request;
import com.denysenko.musicservice.forms.Response;
import com.denysenko.musicservice.exceptions.RestServiceException;
import com.denysenko.musicservice.parsers.JsonParser;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;


@Service
public class LastFmService implements MusicService {
    private Request request;
    private Response response;
    @Value("${API.key}")
    private String apiKey;
    private String url = "http://ws.audioscrobbler.com";
    private static Logger logger = LogManager.getLogger(LastFmService.class);

    public LastFmService(Request request, Response response) {
        this.request = request;
        this.response = response;
    }

    public Response getResponse() {
        return response;
    }

    public void setResponse(Response response) {
        this.response = response;
    }

    public Request getRequest() {
        return request;
    }

    public void setRequest(Request request) {
        this.request = request;
    }

    @Override
    public Response getInfoFromService() throws RestServiceException{
        logger.debug("Method \"getInfo\" was called");
        String urlSearchTrack = new String();
        urlSearchTrack = url + "/2.0/?method=track.getInfo&track=" + request.getTrack() + "&artist=" + request.getSinger() +
                "&api_key=" + apiKey + "&format=json";
        urlSearchTrack = urlSearchTrack.replaceAll(" ", "%20");

        final String stringPosts = readJsonFromUrl(urlSearchTrack);
        String albumName = JsonParser.getAlbumTitle(stringPosts);
        String urlGetAlbumInfo = new String();
        urlGetAlbumInfo += url + "/2.0/?method=album.getinfo&api_key=" + apiKey + "&artist=" +
                request.getSinger() + "&album=" + albumName + "&format=json";
        urlGetAlbumInfo = urlGetAlbumInfo.replaceAll(" ", "%20");
        final String secondStringPosts = readJsonFromUrl(urlGetAlbumInfo);
        JsonParser.parseAlbumInfo(response, secondStringPosts);
        return response;
    }

    public static URL createURl(String urlSt){
        logger.debug("Method \"createURL\" was called");
        try{
            return new URL(urlSt);
        }
        catch (MalformedURLException e) {
            logger.error("Address for request to last.fm api was not created");
            return null;
        }
    }
    public static String readJsonFromUrl(String urlSt) {
        logger.debug("Method \"readJsonFromUrl\" was called");
        StringBuilder stringBuilder = new StringBuilder();

        try(BufferedReader in = new BufferedReader(new InputStreamReader(createURl(urlSt).openStream()))) {
            logger.debug("Address for request to last.fm api was successfully created");
            String inputLine;
            while((inputLine = in.readLine()) != null){
                stringBuilder.append(inputLine);
            }
            logger.debug("Information from url was successfully read");
        }
        catch (IOException e){
            logger.error("Information from url was not read");
        }
        return stringBuilder.toString();
    }

}
