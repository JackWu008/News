package com.jackwu.news.api;


import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ApiService {

    public static String get(String address) throws IOException {//使用HttpURLConnection下载data
        URL url = new URL(address);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        try {
            InputStream inputStream = connection.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder buffer = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
                buffer.append("\n");
            }
            reader.close();
            inputStream.close();
            return buffer.toString();
        } finally {
            connection.disconnect();
        }
    }

    public static void post(JSONObject json, String address) throws IOException {//上传
        URL url = new URL(address);
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        urlConnection.setDoOutput(true);
        urlConnection.setRequestMethod("POST");
        urlConnection.setChunkedStreamingMode(0);
        urlConnection.setRequestProperty("Content-Type", "application/json");
        byte[] data = json.toString().getBytes("UTF-8");
        urlConnection.setRequestProperty("Content-Length", String.valueOf(data.length));
        try (OutputStream stream = urlConnection.getOutputStream()) {
            stream.write(data);
            stream.flush();
        } finally {
            urlConnection.disconnect();
        }
    }


    public static String okGet(String address) throws IOException {//使用okHttp下载data
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(address).build();
        Response response = client.newCall(request).execute();
        if (response.isSuccessful())
            return response.body().string();
        else
            throw new IOException("Exception code " + response.code());
    }


    public static void okPost(JSONObject json, String address) throws IOException {//上传
        OkHttpClient client = new OkHttpClient();
        MediaType type = MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(type, json.toString());
        Request request = new Request.Builder().url(address).post(body).build();
        Response response = client.newCall(request).execute();
        if (!response.isSuccessful())
            throw new IOException("Exception code " + response.code());


    }

    public static String okGetWithHeaders(String url, String args, HashMap<String, Object> headers) throws IOException {
        url = url.concat("?").concat(args);
        OkHttpClient client = new OkHttpClient();
        Request.Builder builder = (new Request.Builder()).url(url);
        if (headers != null && headers.size() > 0) {
            Iterator request = headers.entrySet().iterator();

            label34:
            while (true) {
                while (true) {
                    if (!request.hasNext()) {
                        break label34;
                    }

                    Object response = request.next();
                    Map.Entry entry = (Map.Entry) response;
                    String key = entry.getKey().toString();
                    Object val = entry.getValue();
                    if (val instanceof String) {
                        builder = builder.header(key, val.toString());
                    } else {
                        String v;
                        if (val instanceof List) {
                            for (Iterator var10 = ((List) val).iterator(); var10.hasNext(); builder = builder.addHeader(key, v)) {
                                v = (String) var10.next();
                            }
                        }
                    }
                }
            }
        }

        Request request1 = builder.build();
        Response response1 = client.newCall(request1).execute();
        if (response1.isSuccessful()) {
            return response1.body().string();
        } else {
            throw new IOException("错误码：" + response1.code());
        }
    }

    public static String decode(String unicodeStr) {
        if (unicodeStr == null) {
            return null;
        } else {
            StringBuilder retBuf = new StringBuilder();
            int maxLoop = unicodeStr.length();

            for (int i = 0; i < maxLoop; ++i) {
                if (unicodeStr.charAt(i) != 92) {
                    retBuf.append(unicodeStr.charAt(i));
                } else if (i >= maxLoop - 5 || unicodeStr.charAt(i + 1) != 117 && unicodeStr.charAt(i + 1) != 85) {
                    retBuf.append(unicodeStr.charAt(i));
                } else {
                    try {
                        retBuf.append((char) Integer.parseInt(unicodeStr.substring(i + 2, i + 6), 16));
                        i += 5;
                    } catch (NumberFormatException var5) {
                        retBuf.append(unicodeStr.charAt(i));
                    }
                }
            }

            return retBuf.toString();
        }
    }
}


