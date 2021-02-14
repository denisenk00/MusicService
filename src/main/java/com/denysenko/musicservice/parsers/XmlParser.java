package com.denysenko.musicservice.parsers;

import com.denysenko.musicservice.Response;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import org.json.XML;
@Component
public class XmlParser {
    public static String getAlbumName(String info){
        JSONObject obj = XML.toJSONObject(info);
        String albumName = obj.getJSONObject("lfm").getJSONObject("track").getJSONObject("album").get("title").toString();
        return albumName;
    }
    public static Response ParseAlbumInfo(Response response, String info){
        JSONObject obj = XML.toJSONObject(info);
        System.out.println(obj.toString());
        obj = obj.getJSONObject("lfm").getJSONObject("album");
        response.setNameOfAlbum(obj.get("name").toString());
        response.setSinger(obj.get("artist").toString());
        JSONArray imgObj = obj.getJSONArray("image");
        String img = imgObj.getJSONObject(2).getString("content");
        response.setPoster(img);
        response.setTable(parseTracks(obj.getJSONObject("tracks")));
        return response;
    }
    public static Map<String, String> parseTracks(JSONObject jsonObject){
        Map<String, String> tracks = new HashMap<>();
        JSONArray jsonArrayTracks = jsonObject.getJSONArray("track");
        for(int i = 0; i < jsonArrayTracks.length(); i++){
            JSONObject object = jsonArrayTracks.getJSONObject(i);
            String name = object.get("name").toString();
            String duration = Integer.toString(object.getInt("duration"));
            tracks.put(name, duration);
        }
        return tracks;
    }
}
