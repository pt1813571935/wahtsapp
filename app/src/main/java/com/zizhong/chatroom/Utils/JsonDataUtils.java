package com.zizhong.chatroom.Utils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class JsonDataUtils {

    public static <T> T getJsonData(String content) {
        Gson gson = new Gson();
        Type type = new TypeToken<T>() {
        }.getType();
        return gson.fromJson(content, type);
    }

    public static <T> String getToJson(List<T> list) {
        Gson gson = new Gson();
        return gson.toJson(list);
    }

    public static String replaceBlank(String str) {
        String dest = "";
        if (str != null) {
            Pattern p = Pattern.compile("\\s*|\t|\r|\n");
            Matcher m = p.matcher(str);
            dest = m.replaceAll("");
        }
        return dest;
    }
}
