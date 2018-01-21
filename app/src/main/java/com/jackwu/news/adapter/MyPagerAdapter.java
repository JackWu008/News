package com.jackwu.news.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.ViewGroup;

import com.jackwu.news.models.Channel;

import java.util.List;



public class MyPagerAdapter extends FragmentStatePagerAdapter {
    private List<Fragment> fragments;
    private List<Channel> channels;


    public MyPagerAdapter(FragmentManager fm, List<Fragment> fragments, List<Channel> channels) {
        super(fm);
        this.fragments = fragments;
        this.channels = channels;
    }



    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        Channel channel = channels.get(position);
        return channel.isShow() ? channel.getName() : null;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {//不销毁

    }



}
