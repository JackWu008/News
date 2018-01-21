package com.jackwu.news.utils;


import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class AppUtils extends Application {
    private List<Activity> activities = new ArrayList<>();
    private static AppUtils context;

    public AppUtils() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        ToastUtils.init(false);
    }

    public static Context getContext() {
        return context;
    }

    public void addActivity(Activity activity) {
        activities.add(activity);
    }

    public void allFinishActivity() {
        for (Activity a : activities)
            if (a != null)
                a.finish();
        System.exit(0);
    }


    public static boolean isConnectivity() {
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = manager.getActiveNetworkInfo();
        //return info != null && info.isAvailable();
        return info != null && info.isConnected();
    }


    private static void testConnectState(String ip, String port) throws IOException {
        URL url = new URL("http://".concat(ip).concat(":").concat(port));
        HttpURLConnection urlConnect = (HttpURLConnection) url.openConnection();
        urlConnect.setConnectTimeout(5000);
        urlConnect.getContent();
    }


}
