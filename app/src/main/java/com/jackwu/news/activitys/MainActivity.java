package com.jackwu.news.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.jackwu.news.R;
import com.jackwu.news.adapter.MyPagerAdapter;
import com.jackwu.news.constants.ApiConstants;
import com.jackwu.news.fragment.NewsFragment;
import com.jackwu.news.fragment.NewsJumpListener;
import com.jackwu.news.logics.ChannelFactory;
import com.jackwu.news.models.Channel;
import com.jackwu.news.utils.AppUtils;
import com.jackwu.news.utils.SharedPrefUtils;
import com.jackwu.news.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements NavigationView.
        OnNavigationItemSelectedListener, NewsJumpListener {
    private List<Fragment> fragments;
    private List<Channel> channels;
    private DrawerLayout drawer;
    private ViewPager pager;
    private NavigationView nv;
    private TabLayout tab;
    private long time;

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ((AppUtils) getApplication()).addActivity(this);
        loadingTheme();
        fragments = new ArrayList<>();
        ChannelFactory factory = ChannelFactory.getInstance();
        channels = factory.getShowChannel();
        factory.sort(channels);
        find();
        init();
    }

    private void find() {
        tab = (TabLayout) findViewById(R.id.activity_main_tab);
        drawer = (DrawerLayout) findViewById(R.id.activity_main_drawer);
        pager = (ViewPager) findViewById(R.id.activity_main_pager);
        nv = (NavigationView) findViewById(R.id.activity_main_nv);
        toolbar = (Toolbar) findViewById(R.id.activity_main_toolbar);

    }

    private void init() {
        tab.setupWithViewPager(pager);
        if (channels.size() < 5)
            tab.setTabMode(TabLayout.MODE_FIXED);
        else
            tab.setTabMode(TabLayout.MODE_SCROLLABLE);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawer.openDrawer(GravityCompat.START);
            }
        });
        toolbar.inflateMenu(R.menu.menu_main);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.menu_main_channel) {
                    startActivity(new Intent(MainActivity.this, ChannelActivity.class));
                    finish();
                }
                return true;
            }
        });
        //设置Toolbar和DrawerLayout实现动画和联动
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.open, R.string.close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        nv.setNavigationItemSelectedListener(this);
        for (int i = 0; i < channels.size(); i++) {
            fragments.add(NewsFragment.getInstance(channels.get(i).getType()));
        }
        MyPagerAdapter adapter = new MyPagerAdapter(getSupportFragmentManager(), fragments, channels);
        pager.setAdapter(adapter);


    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        drawer.closeDrawer(GravityCompat.START);
        switch (item.getItemId()) {
            case R.id.menu_navigation_theme:
                int theme = SharedPrefUtils.getTheme();
                if (theme == 0)
                    SharedPrefUtils.setTheme(1);
                else
                    SharedPrefUtils.setTheme(0);
                getWindow().setWindowAnimations(R.style.WindowAnimationFadeInOut);
                recreate();
                break;
            case R.id.menu_navigation_about:
                startActivity(new Intent(this, AboutActivity.class));
                break;
            case R.id.menu_navigation_setting:
                startActivity(new Intent(this, SettingActivity.class));
                break;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(nv)) {
            drawer.closeDrawers();
            return;
        }
        int pos = tab.getSelectedTabPosition();
        if (pos == 0) {
            if (System.currentTimeMillis() - time > 2000) {
                time = System.currentTimeMillis();
                ToastUtils.showShortToastSafe("再按一次退出程序");
            } else {
                ((AppUtils) getApplication()).allFinishActivity();
                System.exit(0);
            }
        } else
            tab.getTabAt(0).select();
    }

    private void loadingTheme() {
        int theme = SharedPrefUtils.getTheme();
        if (theme == 1) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
    }


    @Override
    public void recyclerItemClick(String url, String source, String title) {
        Intent intent = new Intent(this, WebActivity.class);
        intent.putExtra(ApiConstants.JSON_NEWS_URL, url);
        intent.putExtra(ApiConstants.JSON_NEWS_SOURCE, source);
        intent.putExtra(ApiConstants.JSON_NEWS_TITLE, title);
        startActivity(intent);
    }



}
