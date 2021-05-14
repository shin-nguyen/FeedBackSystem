package com.gaf.project.utils;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.lang.reflect.Type;

import java.time.LocalDate;

public class MyJsonDateSerializer
//        implements JsonSerializer<LocalDate>, JsonDeserializer<LocalDate>
{
//    @Override
//    public LocalDate deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
//        JsonObject jsonObject = (JsonObject)json;
//        String dateString = jsonObject.get("datetime").getAsString();
//        return DateTimeUtil.getDateTimeFromString(dateString);
//    }
//    @Override
//    public JsonElement serialize(LocalDate src, Type typeOfSrc, JsonSerializationContext context) {
////        final JsonObject jsonObject = new JsonObject();
////        jsonObject.addProperty("datetime", DateTimeUtil.getDateTimeString(src));
////        return jsonObject;
//    }
}