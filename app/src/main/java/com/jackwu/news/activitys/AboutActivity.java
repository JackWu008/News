package com.jackwu.news.activitys;

import android.support.v4.app.Fragment;

import com.jackwu.news.fragment.AboutFragment;


public class AboutActivity extends BaseActivity {

    @Override
    protected Fragment getFragment() {
        return new AboutFragment();
    }
}
