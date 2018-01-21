package com.jackwu.news.models;

import android.database.Cursor;

import com.jackwu.news.constants.DbConstants;

import net.lzzy.sqllib.DataUtils;
import net.lzzy.sqllib.ISqlitable;

import java.util.HashMap;
import java.util.UUID;


public class Channel implements  ISqlitable {
    private UUID id;
    private String name;
    private String type;
    private boolean isShow;
    private int position;

    public Channel() {
        id = UUID.randomUUID();
        isShow=true;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isShow() {
        return isShow;
    }

    public void setShow(boolean show) {
        isShow = show;
    }

    @Override
    public String getTableName() {
        return DbConstants.CHANNEL_TABLE_NAME;
    }

    @Override
    public String getPKName() {
        return DbConstants.CHANNEL_ID;
    }

    @Override
    public UUID getId() {
        return id;
    }

    @Override
    public HashMap<String, Object> getInsertCols() {
        HashMap<String, Object> map = new HashMap<>();
        map.put(DbConstants.CHANNEL_ID,id.toString());
        map.put(DbConstants.CHANNEL_TYPE, type);
        map.put(DbConstants.CHANNEL_POSITION, position);
        map.put(DbConstants.CHANNEL_IS_SHOW, isShow ? 1 : 0);
        map.put(DbConstants.CHANNEL_NAME, name);
        return map;
    }

    @Override
    public HashMap<String, Object> getUpdateCols() {
        HashMap<String, Object> map = new HashMap<>();
        map.put(DbConstants.CHANNEL_TYPE, type);
        map.put(DbConstants.CHANNEL_POSITION, position);
        map.put(DbConstants.CHANNEL_IS_SHOW, isShow ? 1 : 0);
        map.put(DbConstants.CHANNEL_NAME, name);
        return map;
    }

    @Override
    public HashMap<String, String> createTableCols() {
        HashMap<String, String> map = new HashMap<>();
        map.put(DbConstants.CHANNEL_ID,DataUtils.TYPE_STRING);
        map.put(DbConstants.CHANNEL_TYPE, DataUtils.TYPE_STRING);
        map.put(DbConstants.CHANNEL_IS_SHOW, DataUtils.TYPE_INT);
        map.put(DbConstants.CHANNEL_POSITION, DataUtils.TYPE_INT);
        map.put(DbConstants.CHANNEL_NAME, DataUtils.TYPE_STRING);
        return map;
    }

    @Override
    public boolean needUpdate() {
        return false;
    }

    @Override
    public void fromCursor(Cursor cursor) {
        id=UUID.fromString(cursor.getString(cursor.getColumnIndex(DbConstants.CHANNEL_ID)));
        name = cursor.getString(cursor.getColumnIndex(DbConstants.CHANNEL_NAME));
        type = cursor.getString(cursor.getColumnIndex(DbConstants.CHANNEL_TYPE));
        isShow = cursor.getInt(cursor.getColumnIndex(DbConstants.CHANNEL_IS_SHOW)) != 0;
        position = cursor.getInt(cursor.getColumnIndex(DbConstants.CHANNEL_POSITION));
    }

}
