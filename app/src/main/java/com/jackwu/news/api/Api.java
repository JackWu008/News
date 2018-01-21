package com.jackwu.news.api;

import com.jackwu.news.constants.ApiConstants;

import java.io.IOException;
import java.util.HashMap;


public class Api {

    private static String reuest(String url, String args) throws IOException {
        HashMap<String, Object> headers = new HashMap<>();
        return ApiService.okGetWithHeaders(url, args, headers);
    }

    private static String urlArgs(HashMap<String, String> paras) {
        String result = "";
        for (String key : paras.keySet()) {
            result = result.concat(key).concat(paras.get(key)).concat("&");
        }

        if (result.endsWith("&"))
            result = result.substring(0, result.length() - 1);
        return result;
    }

    public static String getJson(String type) throws IOException {
        HashMap<String,String> paras=new HashMap<>();
        paras.put(ApiConstants.JSON_NEWS_TYPE,type);
        paras.put(ApiConstants.JSON_NEWS_KEY,ApiConstants.API_URL_API_KEY);
        String args = urlArgs(paras);
        return ApiService.decode(reuest(ApiConstants.API_URL_NEWS, args));
    }




}
