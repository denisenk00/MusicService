package com.denysenko.musicservice.parsers;

import com.denysenko.musicservice.Album;
import com.denysenko.musicservice.Track;
import com.denysenko.musicservice.exceptions.RestServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;

import java.util.LinkedList;
import java.util.List;


public class JsonParser {
    private static Logger logger = LogManager.getLogger(JsonParser.class);

    public static String getAlbumTitle(String info) throws RestServiceException {
        logger.debug("Method \"getAlbumTitle\" was called");
        JSONObject obj = new JSONObject(info);
        logger.debug("JSONObject was created");
        try {
            String albumName = obj.getJSONObject("track").getJSONObject("album").getString("title");
            logger.debug("Album title was found");
            return albumName;
        } catch (JSONException e) {
            logger.error("No such album found", e);
            throw new RestServiceException(HttpStatus.BAD_GATEWAY, "No such album found", "1");
        }
    }

    public static Album parseAlbum(String info) throws RestServiceException {
        logger.debug("Method \"parseAlbumInfo\" was called");
        Album album = new Album();
        JSONObject obj = new JSONObject(info);
        try {
            obj = obj.getJSONObject("album");
            album.setTitle(obj.getString("name"));
            logger.debug("Album title was received");
            album.setSinger(obj.getString("artist"));
            logger.debug("Singer was received");
            JSONArray imgObj = obj.getJSONArray("image");
            String img = imgObj.getJSONObject(2).getString("#text");
            album.setPoster(img);
            logger.debug("Link to poster was received");
            album.setTracks(parseTracks(obj.getJSONObject("tracks")));
            logger.debug("Info about album tracks was received");
            return album;
        } catch (JSONException e) {
            logger.error("Not found all album information", e);
            throw new RestServiceException(HttpStatus.BAD_GATEWAY, "Not found all album information", "2");
        }
    }

    public static List<Track> parseTracks(JSONObject jsonObject) throws JSONException {
        List<Track> tracks = new LinkedList<>();
        JSONArray jsonArrayTracks = jsonObject.getJSONArray("track");
        for (int i = 0; i < jsonArrayTracks.length(); i++) {
            JSONObject object = jsonArrayTracks.getJSONObject(i);
            String title = object.getString("name");
            String duration = object.getString("duration");
            tracks.add(new Track(title, duration));
        }
        return tracks;
    }
}
