package com.jackwu.news.activitys;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.jackwu.news.fragment.SettingFragment;
import com.jackwu.news.utils.AppUtils;

public class SettingActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((AppUtils) getApplication()).addActivity(this);
    }

    @Override
    protected Fragment getFragment() {
        return new SettingFragment();
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
