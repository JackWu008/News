package com.jackwu.news.utils;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;

import com.jackwu.news.models.Share;

import java.util.ArrayList;
import java.util.List;



public class ShareUtils {
    /**
     *获取可以分享的应用的集合
     * */
    public static List<Share> getShares(Context context) {
        List<Share> shares = new ArrayList<>();
        PackageManager packageManager = context.getPackageManager();
        List<ResolveInfo> resolveInfos = getShareApps(context);
        if (resolveInfos.size() > 0) {
            for (ResolveInfo r : resolveInfos) {
                Share share = new Share();
                share.setAppIcon(r.loadIcon(packageManager));
                share.setAppName(r.loadLabel(packageManager).toString());
                share.setLaunchClassName(r.activityInfo.name);
                share.setPkgName(r.activityInfo.packageName);
                shares.add(share);
            }
            return shares;
        } else return null;
    }
    /**
     *获取可以分享的应用的信息的集合
     * */
    private static List<ResolveInfo> getShareApps(Context context) {
        List<ResolveInfo> apps;
        Intent intent = new Intent(Intent.ACTION_SEND, null);
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        intent.setType("text/plain");
        PackageManager pManager = context.getPackageManager();
        apps = pManager.queryIntentActivities(intent,
                PackageManager.COMPONENT_ENABLED_STATE_DEFAULT);
        return apps;
    }

}
