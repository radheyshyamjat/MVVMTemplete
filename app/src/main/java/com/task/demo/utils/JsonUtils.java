package com.task.demo.utils;

import com.google.gson.Gson;

public class JsonUtils {

    /**
     * Convert POJO to JSON String
     *
     * @param object POJO
     * @return JSON String
     */
    public static String convertPojoToString(Object object) {
        Gson gson = new Gson();
        return gson.toJson(object);
    }

    /**
     * Convert JSON String to POJO
     *
     * @param json JSON String
     * @param c POJO
     * @return POJO
     */
    public static Object convertStringToPojo(String json, Class c) {
        Gson gson = new Gson();
        //noinspection unchecked
        return gson.fromJson(json, c);
    }

}
