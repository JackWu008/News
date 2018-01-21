package com.jackwu.news.fragment;

import android.os.Handler;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.jackwu.news.R;
import com.jackwu.news.utils.CacheUtils;
import com.jackwu.news.utils.ToastUtils;
import com.jackwu.news.dialog.CustomDialog;


public class SettingFragment extends BaseFragment {
    private FrameLayout frame;
    private Toolbar toolbar;
    private TextView tv_cache;

    @Override
    protected int setContentView() {
        return R.layout.fragment_setting;
    }

    @Override
    protected void find(View view) {
        frame = (FrameLayout) view.findViewById(R.id.fragment_setting_frame);
        toolbar = (Toolbar) view.findViewById(R.id.fragment_setting_toolbar);
        tv_cache = (TextView) view.findViewById(R.id.fragment_setting_tv_show);
    }

    @Override
    protected void init() {
        final CacheUtils glideCache = CacheUtils.getInstance();
        tv_cache.setText(glideCache.getWebViewImgCacheSize(getContext()));
        frame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!tv_cache.getText().toString().equals("0.0B")) {
                    final CustomDialog dialog = new CustomDialog(getContext(), R.style.Custom_dialog);
                    dialog.setTitle("清除中");
                    dialog.setCancelable(false);
                    dialog.show();
                    glideCache.clearWebViewImgCache(getContext());
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            dialog.dismiss();
                            ToastUtils.showShortToastSafe("清除成功");
                            tv_cache.setText("0.0B");
                        }
                    }, 2000);
                } else
                    ToastUtils.showShortToastSafe("已经清空，不需要再清除");
            }
        });
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });
    }
}
