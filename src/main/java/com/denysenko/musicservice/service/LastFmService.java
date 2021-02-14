package com.denysenko.musicservice.service;

import com.denysenko.musicservice.Request;
import com.denysenko.musicservice.Response;
import com.denysenko.musicservice.parsers.JsonParser;
import com.denysenko.musicservice.parsers.XmlParser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;


@Service
public class LastFmService implements MusicService {
    private Request request;
    private Response response;
    @Value("${API.key}")
    private String apiKey;
    private String url = "http://ws.audioscrobbler.com";
    private JsonParser jsonParser;
    private XmlParser xmlParser;

    public LastFmService(Request request, Response response, JsonParser jsonParser, XmlParser xmlParser) {
        this.request = request;
        this.response = response;
        this.jsonParser = jsonParser;
        this.xmlParser = xmlParser;
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
    public Response getInfo() {
        String urlSearchTrack = new String();
        urlSearchTrack = url + "/2.0/?method=track.getInfo&track=" + request.getTrack() + "&artist=" + request.getSinger() + "&api_key=" + apiKey;
        final RestTemplate restTemplate = new RestTemplate();
        if(request.getFormat().equals("json")){
            urlSearchTrack += "&format=json";
            final String stringPosts = restTemplate.getForObject(urlSearchTrack, String.class);
            String albumName = JsonParser.getAlbumName(stringPosts);
            String urlGetAlbumInfo = new String();
            urlGetAlbumInfo += url + "/2.0/?method=album.getinfo&api_key=" + apiKey + "&artist=" +
                    request.getSinger() + "&album=" + albumName + "&format=json";
            final String secondStringPosts = restTemplate.getForObject(urlGetAlbumInfo, String.class);
            JsonParser.ParseAlbumInfo(response, secondStringPosts);
        }
        else{
            final String stringPosts = restTemplate.getForObject(urlSearchTrack, String.class);
            System.out.println(stringPosts);
            String albumName = XmlParser.getAlbumName(stringPosts);
            String urlGetAlbumInfo = new String();
            urlGetAlbumInfo += url + "/2.0/?method=album.getinfo&api_key=" + apiKey + "&artist=" +
                    request.getSinger() + "&album=" + albumName;
            final String secondStringPosts = restTemplate.getForObject(urlGetAlbumInfo, String.class);
            XmlParser.ParseAlbumInfo(response, secondStringPosts);
        }
        return response;
    }


}
