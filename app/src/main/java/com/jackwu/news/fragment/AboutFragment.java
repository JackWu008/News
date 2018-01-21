package com.jackwu.news.fragment;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.jackwu.news.R;


public class AboutFragment extends BaseFragment {

    private Toolbar toolbar;
    private TextView tv;

    @Override
    protected int setContentView() {
        return R.layout.fragment_about;
    }

    @Override
    protected void find(View view) {
        toolbar = (Toolbar) view.findViewById(R.id.fragment_about_toolbar);
        tv = (TextView) view.findViewById(R.id.fragment_about_tv);
    }

    @Override
    protected void init() {


        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });

        tv.setText(getAppVersionName(getContext()));

    }

    public String getAppVersionName(Context context) {
        String versionName;
        try {
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
            versionName = pi.versionName;
            if (versionName == null || versionName.length() <= 0) {
                return "";
            }
        } catch (Exception e) {
            return "";
        }
        return versionName;
    }
}
