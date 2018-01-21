package com.jackwu.news.constants;


import com.jackwu.news.R;
import com.jackwu.news.utils.AppUtils;

import net.lzzy.sqllib.ClassUtils;
import net.lzzy.sqllib.DbPackager;

import java.util.List;


public final class DbConstants {

    private DbConstants() {
    }

    private static final String DB_NAME = "news.db";
    /**
     * 数据库版本
     */

    private static final int DB_VERSION = 1;


    /**
     * table news
     **/
    public static final String NEWS_TABLE_NAME = "news";
    public static final String NEWS_ID = "id";
    public static final String NEWS_TITLE = "title";
    public static final String NEWS_DATE = "date";
    public static final String NEWS_URL = "url";
    public static final String NEWS_SOURCE = "source";
    public static final String NEWS_IMG_URL = "imgUrl";

    /**
     * table channel
     **/
    public static final String CHANNEL_TABLE_NAME = "channel";
    public static final String CHANNEL_NAME = "name";
    public static final String CHANNEL_ID = "id";
    public static final String CHANNEL_TYPE = "type";
    public static final String CHANNEL_IS_SHOW = "isShow";
    public static final String CHANNEL_POSITION = "position";
    public static DbPackager dbPackage;

    static {
        List<String> tables = ClassUtils.getModels(AppUtils.getContext(), R.raw.tables);
        dbPackage = DbPackager.getInstance(DB_NAME, DB_VERSION, tables);
    }
}
