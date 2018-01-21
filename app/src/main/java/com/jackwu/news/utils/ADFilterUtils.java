package com.jackwu.news.utils;


import android.content.Context;
import android.content.res.Resources;

import com.jackwu.news.R;

public class ADFilterUtils {
    public static boolean hasAd(Context context, String url) {
        Resources res = context.getResources();
        String[] adUrls = res.getStringArray(R.array.adBlockUrl);
        for (String adUrl : adUrls) {
            if (url.contains(adUrl)) {
                return true;
            }
        }
        return false;
    }
}
