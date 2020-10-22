package com.mbcq.baselibrary.gson;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONObject;

public class GsonUtils {
    /**
     * 解决gson生成json 有多余的namevaluepairs键
     *
     * @param jsonString
     * @return
     */
    public static String toPrettyFormat(String jsonString) {
        JsonParser parser = new JsonParser();
        JsonObject json = parser.parse(jsonString).getAsJsonObject();
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(json);
    }

    /**
     * 解决gson生成json 有多余的namevaluepairs键
     *
     * @param jsonString
     * @return
     */
    public static String toPrettyFormat(JSONObject jsonString) {
        JsonParser parser = new JsonParser();
        JsonObject json = parser.parse(jsonString.toString()).getAsJsonObject();
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(json);
    }
}
