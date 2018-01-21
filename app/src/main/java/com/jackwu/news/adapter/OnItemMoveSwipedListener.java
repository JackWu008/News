package com.jackwu.news.adapter;

/**
 * Created by lzzy_gxy on 2016/11/24.
 * 手指移动或侧滑删除某Item时的接口方法
 */
public interface OnItemMoveSwipedListener {
    void moveItem(int from, int to);
    void removeItem(int pos);
}
