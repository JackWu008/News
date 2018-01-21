package com.jackwu.news.adapter;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

/**
 * Created by lzzy_gxy on 2016/11/24.
 * 实现侧滑删除和移动item的回调
 */

public class ItemTouchHelperCallback extends ItemTouchHelper.Callback {
    private OnItemMoveSwipedListener adapter;
    public ItemTouchHelperCallback(OnItemMoveSwipedListener listener){
        adapter=listener;
    }

    @Override//定义拖拽和侧滑方式
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        //如果是ListView样式的RecyclerView
        if (recyclerView.getLayoutManager() instanceof LinearLayoutManager){
            //设置拖拽方向为上下
            final int dragFlags = ItemTouchHelper.UP| ItemTouchHelper.DOWN;
            //设置侧滑方向为从左到右和从右到左都可以
          //  final int swipeFlags = ItemTouchHelper.START| ItemTouchHelper.END;
            final int swipeFlags = 0;
            //将方向参数设置进去
            return makeMovementFlags(dragFlags,swipeFlags);
        }else{//如果是GridView样式的RecyclerView
            //设置拖拽方向为上下左右
            final int dragFlags = ItemTouchHelper.UP| ItemTouchHelper.DOWN|
                    ItemTouchHelper.LEFT| ItemTouchHelper.RIGHT;
            //不支持侧滑
            final int swipeFlags = 0;
            return makeMovementFlags(dragFlags,swipeFlags);
        }
    }

    @Override//定义拖拽的业务逻辑
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        adapter.moveItem(viewHolder.getAdapterPosition(),target.getAdapterPosition());
        return true;
    }




    @Override//定义侧滑的业务逻辑
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        adapter.removeItem(viewHolder.getAdapterPosition());
    }
}
