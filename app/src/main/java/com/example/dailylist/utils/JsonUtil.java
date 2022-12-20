package com.example.dailylist.utils;


import com.google.gson.*;

import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @Author: 夜雨
 * @ClassName: JsonUtil
 * @Description: TODO
 * @Date: 2022/12/18 19:24
 * @Version: 1.0
 */
public class JsonUtil {

    public static String toJson(Object object) {
        if (object == null) {
            return "";
        }
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDateTime.class, new JsonSerializer<LocalDateTime>() {
                    @Override
                    public JsonElement serialize(LocalDateTime localDateTime, Type type, JsonSerializationContext jsonSerializationContext) {
                        return new JsonPrimitive(localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
                    }
                })
                .serializeNulls()
                .create();
        return gson.toJson(object);
    }

    public static String toJson(Object object, Type typeOfSrc) {
        if (object == null) {
            return "";
        }
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDateTime.class, new JsonSerializer<LocalDateTime>() {
                    @Override
                    public JsonElement serialize(LocalDateTime localDateTime, Type type, JsonSerializationContext jsonSerializationContext) {
                        return new JsonPrimitive(localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
                    }
                })
                .serializeNulls()
                .create();
        return gson.toJson(object, typeOfSrc);
    }

    public static <T> T jsonToObj(String json, Class<T> classOfT) {
        if (json == null || json.equals("")) {
            return null;
        }
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDateTime.class, new JsonDeserializer() {
                    @Override
                    public LocalDateTime deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
                        return LocalDateTime.parse(json.getAsJsonPrimitive().getAsString(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                    }
                })
                .create();
        return gson.fromJson(json, classOfT);
    }

    public static <T> T jsonToObj(String json, Type typeOfT) {
        if (json == null || json.equals("")) {
            return null;
        }
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDateTime.class, new JsonDeserializer() {
                    @Override
                    public LocalDateTime deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
                        return LocalDateTime.parse(json.getAsJsonPrimitive().getAsString(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                    }
                })
                .create();
        return gson.fromJson(json, typeOfT);
    }
}


