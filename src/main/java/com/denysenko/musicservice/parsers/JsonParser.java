package com.denysenko.musicservice.parsers;

import com.denysenko.musicservice.Response;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Component;
import java.util.HashMap;
import java.util.Map;

@Component
public class JsonParser {
    public static String getAlbumName(String info){
        JSONObject obj = new JSONObject(info);
        String albumName = obj.getJSONObject("track").getJSONObject("album").getString("title");
        return albumName;
    }
    public static Response ParseAlbumInfo(Response response, String info) {
        JSONObject obj = new JSONObject(info);
        obj = obj.getJSONObject("album");
        response.setNameOfAlbum(obj.getString("name"));
        response.setSinger(obj.getString("artist"));
        JSONArray imgObj = obj.getJSONArray("image");
        String img = imgObj.getJSONObject(2).getString("#text");
        response.setPoster(img);
        response.setTable(parseTracks(obj.getJSONObject("tracks")));
        return response;
    }
    public static Map<String, String> parseTracks(JSONObject jsonObject){
        Map<String, String> tracks = new HashMap<>();
        JSONArray jsonArrayTracks = jsonObject.getJSONArray("track");
        for(int i = 0; i < jsonArrayTracks.length(); i++){
            JSONObject object = jsonArrayTracks.getJSONObject(i);
            String name = object.getString("name");
            String duration = object.getString("duration");
            tracks.put(name, duration);
        }
        return tracks;
    }

}
