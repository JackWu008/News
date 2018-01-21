package com.jackwu.news.activitys;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.jackwu.news.fragment.WebFragment;
import com.jackwu.news.utils.AppUtils;



public class WebActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((AppUtils) getApplication()).addActivity(this);
    }

    @Override
    protected Fragment getFragment() {
        return new WebFragment();
    }


    @Override
    public void onBackPressed() {
        finish();
    }


}
