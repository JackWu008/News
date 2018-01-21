package com.jackwu.news.models;

import com.jackwu.news.constants.ApiConstants;

import net.lzzy.sqllib.IJsonable;

import org.json.JSONException;
import org.json.JSONObject;



public class News implements IJsonable {
    private String title;
    private String date;
    private String imgUrl;
    private String url;
    private String source;


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    @Override
    public JSONObject toJson() throws JSONException {
        return null;
    }

    @Override
    public void fromJson(JSONObject json) throws JSONException {
        title = json.getString(ApiConstants.JSON_NEWS_TITLE);
        imgUrl = json.getString(ApiConstants.JSON_IMG);
        url = json.getString(ApiConstants.JSON_NEWS_URL);
        source = json.getString(ApiConstants.JSON_NEWS_SOURCE);
        date = json.getString(ApiConstants.JSON_NEWS_DATE);
    }

}
