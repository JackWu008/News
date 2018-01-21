package com.jackwu.news.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Collections;
import java.util.List;

/**
 * Created by lzzy_gxy on 2016/11/24.
 * 没有选择模式的recyclerview适配器
 */

public abstract class SimpleRecyclerAdapter<T> extends RecyclerView.Adapter<RecyclerViewHolder>
        implements OnItemMoveSwipedListener {//接口为移动item及侧滑删除的方法定义
    private int layout;
    private List<T> items;
    private OnRecyclerViewItemClickListener listener;//item点击监听
    private OnRecyclerViewItemLongClickListener longListener;

    public SimpleRecyclerAdapter(List<T> items, int layout) {
        this.items = items;
        this.layout = layout;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecyclerViewHolder holder, final int position) {
        populate(holder, items.get(position));
        if (listener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(v, holder.getAdapterPosition());
                }
            });
        }
        if (longListener != null) {
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    longListener.onItemLongClick(view, holder.getAdapterPosition());
                    return true;
                }
            });
        }
    }

    public abstract void populate(RecyclerViewHolder holder, T t);//抽象方法，适配数据及view

    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.listener = listener;
    }

    public void setOnItemLongListener(OnRecyclerViewItemLongClickListener listener) {
        this.longListener = listener;
    }

    public T getItem(int position) {
        return items.get(position);
    }

    @Override
    public int getItemCount() {
        return items == null ? 0 : items.size();
    }

    public abstract boolean deletePersist(T t);//抽象方法，实现删除的业务逻辑（持久化）

    public void add(int position, T t) {
        if (addPersist(t)) {
            items.add(position, t);
            notifyItemInserted(position);
        }
    }

    public abstract boolean addPersist(T t);//抽象方法，实现insert（持久化）

    @Override//OnItemMoveSwipedListener接口方法 拖拽
    public void moveItem(int from, int to) {
        Collections.swap(items, from, to);
        notifyItemMoved(from, to);
    }

    @Override//OnItemMoveSwipedListener接口方法 侧滑
    public void removeItem(int pos) {
//        T t = items.get(pos);
//        if (deletePersist(t)) {
//            items.remove(pos);
//            notifyItemRemoved(pos);
//        }
    }




}
