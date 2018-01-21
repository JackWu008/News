package com.jackwu.news.json;

import com.jackwu.news.constants.ApiConstants;
import com.jackwu.news.models.News;

import net.lzzy.sqllib.JsonConverter;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;


public class NewJson {
    public static List<News> getNews(String json) throws IllegalAccessException, JSONException, InstantiationException {
        JsonConverter<News> converter = new JsonConverter<>(News.class);
        return converter.getArray(new JSONObject(json).getJSONObject(ApiConstants.JSON_NEWS_RESULT).toString(),ApiConstants.JSON_NEWS_DATA);
    }
}
