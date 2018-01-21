package com.jackwu.news.dialog;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jackwu.news.R;
import com.jackwu.news.adapter.OnRecyclerViewItemClickListener;
import com.jackwu.news.adapter.RecyclerViewHolder;
import com.jackwu.news.adapter.SimpleRecyclerAdapter;
import com.jackwu.news.models.Share;
import com.jackwu.news.utils.ShareUtils;

import java.util.List;

import static android.support.design.widget.BottomSheetBehavior.STATE_COLLAPSED;


public class CustomBottomDialog extends BottomSheetDialog {
    private String shareContent;
    private Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int screenHeight = getScreenHeight();
        int statusBarHeight = getStatusBarHeight(getContext());
        int dialogHeight = screenHeight - statusBarHeight;
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, dialogHeight == 0 ? ViewGroup.LayoutParams.MATCH_PARENT : dialogHeight);
    }

    private int getScreenHeight() {
        DisplayMetrics displaymetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        return displaymetrics.heightPixels;
    }

    private static int getStatusBarHeight(Context context) {
        int statusBarHeight = 0;
        Resources res = context.getResources();
        int resourceId = res.getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            statusBarHeight = res.getDimensionPixelSize(resourceId);
        }
        return statusBarHeight;
    }

    public CustomBottomDialog(Activity activity) {
        super(activity);
        this.activity = activity;
        View view = LayoutInflater.from(activity).inflate(R.layout.bottom_dialog, null);
        setContentView(view);
        setBehaviorCallback();
        init(view);
    }

    private void init(View view) {
        RecyclerView rlv = (RecyclerView) view.findViewById(R.id.bottom_dialog_rlv);
        rlv.setHasFixedSize(true);
        rlv.setLayoutManager(new GridLayoutManager(activity, 3));
        final List<Share> shares = ShareUtils.getShares(activity);
        SimpleRecyclerAdapter<Share> adapter = new SimpleRecyclerAdapter<Share>(shares, R.layout.item_share) {
            @Override
            public void populate(RecyclerViewHolder holder, Share share) {
                holder.setTextView(R.id.item_share_tv, share.getAppName())
                        .setImageView(R.id.item_share_iv, share.getAppIcon());
            }

            @Override
            public boolean deletePersist(Share share) {
                return false;
            }

            @Override
            public boolean addPersist(Share share) {
                return false;
            }
        };
        rlv.setLayoutManager(new GridLayoutManager(activity, 3));
        rlv.setAdapter(adapter);
        adapter.setOnItemClickListener(new OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if (shares != null) {
                    Share share = shares.get(position);
                    Intent shareIntent = new Intent(Intent.ACTION_SEND);
                    shareIntent.setComponent(new ComponentName(share.getPkgName(), share.getLaunchClassName()));
                    shareIntent.setType("text/plain");
                    shareIntent.putExtra(Intent.EXTRA_TEXT, getShareContent());
                    activity.startActivity(shareIntent);
                    cancel();
                }
            }
        });
        rlv.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
                super.onDrawOver(c, parent, state);
            }

            @Override
            public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
                super.onDraw(c, parent, state);
            }

            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                outRect.set(20, 20, 20, 20);
            }
        });
    }

    public String getShareContent() {
        return shareContent;
    }

    public void setShareContent(String shareContent) {
        this.shareContent = shareContent;
    }

    @Override
    public void show() {
        super.show();
    }

    private void setBehaviorCallback() {
        View view = this.getDelegate().findViewById(android.support.design.R.id.design_bottom_sheet);
        final BottomSheetBehavior bottomSheetBehavior = BottomSheetBehavior.from(view);
         bottomSheetBehavior.setPeekHeight(850);
        bottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                //newState 有四个状态 ：
                //展开 BottomSheetBehavior.STATE_EXPANDED
                //收起 BottomSheetBehavior.STATE_COLLAPSED
                //拖动 BottomSheetBehavior.STATE_DRAGGING
                //下滑 BottomSheetBehavior.STATE_SETTLING
                if (newState == BottomSheetBehavior.STATE_HIDDEN) {
                    dismiss();
                    bottomSheetBehavior.setState(STATE_COLLAPSED);
                    cancel();
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
                //这里是拖拽中的回调，slideOffset为0-1 完全收起为0 完全展开为1


            }
        });
    }


}
