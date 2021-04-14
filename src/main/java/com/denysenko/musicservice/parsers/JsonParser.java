package com.denysenko.musicservice.parsers;

import com.denysenko.musicservice.forms.Response;
import com.denysenko.musicservice.exceptions.RestServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;


@Component
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
            logger.error("No such album found");
            throw new RestServiceException(HttpStatus.BAD_GATEWAY, "No such album found", "1");
        }
    }

    public static Response parseAlbumInfo(Response response, String info) throws RestServiceException {
        logger.debug("Method \"parseAlbumInfo\" was called");
        JSONObject obj = new JSONObject(info);
        try {
            obj = obj.getJSONObject("album");
            response.setTitle(obj.getString("name"));
            logger.debug("Album title was received");
            response.setSinger(obj.getString("artist"));
            logger.debug("Singer was received");
            JSONArray imgObj = obj.getJSONArray("image");
            String img = imgObj.getJSONObject(2).getString("#text");
            response.setPoster(img);
            logger.debug("Link to poster was received");
            response.setTracks(parseTracks(obj.getJSONObject("tracks")));
            logger.debug("Info about album tracks was received");
            return response;
        } catch (JSONException e) {
            logger.error("Not found all album information");
            throw new RestServiceException(HttpStatus.BAD_GATEWAY, "Not found all album information", "2");
        }
    }

    public static Map<String, String> parseTracks(JSONObject jsonObject) throws JSONException {
        Map<String, String> tracks = new HashMap<>();

        JSONArray jsonArrayTracks = jsonObject.getJSONArray("track");
        for (int i = 0; i < jsonArrayTracks.length(); i++) {
            JSONObject object = jsonArrayTracks.getJSONObject(i);
            String name = object.getString("name");
            String duration = object.getString("duration");
            tracks.put(name, duration);
        }
        return tracks;
    }
}
