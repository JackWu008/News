package com.jackwu.news.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jackwu.news.models.News;

import java.util.List;


public abstract class NewsRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<News> newses;
    private boolean isFooterView = true;
    private View footView;
    private final int NOFOOT = 1;
    private final int YESFOOT = 2;
    private int layout;
    private OnRecyclerViewItemClickListener listener;//item点击监听
    private OnRecyclerViewItemLongClickListener longListener;

    public NewsRecyclerAdapter( List<News> newses, int layout) {
        this.layout = layout;
        isFooterView = false;
        this.newses = newses;
    }

    public NewsRecyclerAdapter(List<News> newses, int layout, View footView) {
        this.layout = layout;
        isFooterView = true;
        this.newses = newses;
        this.footView = footView;
    }

    @Override
    public int getItemViewType(int position) {
        if (isFooterView) {
            if (position == (newses.size())) {
                return YESFOOT;
            } else {
                return NOFOOT;
            }
        } else {
            return NOFOOT;
        }
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder = null;
        switch (viewType) {
            case YESFOOT:
                holder = new ViewHolderFoot(footView);
                break;

            case NOFOOT:
                View view = LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);
                holder = new RecyclerViewHolder(view);
                break;
        }

        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof RecyclerViewHolder) {
            RecyclerViewHolder holder1 = (RecyclerViewHolder) holder;
            populate(holder1, newses.get(position));
            if (listener != null) {
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        listener.onItemClick(v, position);
                    }
                });
            }
            if (longListener != null) {
                holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View view) {
                        longListener.onItemLongClick(view, position);
                        return true;
                    }
                });
            }
        }
    }

    public abstract void populate(RecyclerViewHolder holder, News news);
    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.listener = listener;
    }

    public void setOnItemLongListener(OnRecyclerViewItemLongClickListener listener) {
        this.longListener = listener;
    }
    @Override
    public int getItemCount() {
        return isFooterView ? newses.size() + 1 : newses.size();
    }

    public class ViewHolderFoot extends RecyclerView.ViewHolder {

        public ViewHolderFoot(View itemView) {
            super(itemView);
        }
    }

}
