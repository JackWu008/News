package com.jackwu.news.utils;

import android.content.Context;
import android.content.SharedPreferences;


public class SharedPrefUtils {
    public static final String SP_USER_SETTINGS = "sp_user_settings";
    public static final String SP_USER_SETTINGS_THEME = "sp_user_settings_theme";




    public static int getTheme() {
        SharedPreferences sp = AppUtils.getContext().getSharedPreferences(SP_USER_SETTINGS, Context.MODE_PRIVATE);
        if (sp.getInt(SP_USER_SETTINGS_THEME, 5) == 5)
            sp.edit().putInt(SP_USER_SETTINGS_THEME, 0).apply();
        return sp.getInt(SP_USER_SETTINGS_THEME, 0);
    }

    public static void setTheme(int theme) {
        SharedPreferences sp = AppUtils.getContext().getSharedPreferences(SP_USER_SETTINGS, Context.MODE_PRIVATE);
        sp.edit().putInt(SP_USER_SETTINGS_THEME, theme).apply();
    }
}
