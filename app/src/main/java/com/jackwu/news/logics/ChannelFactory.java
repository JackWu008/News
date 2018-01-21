package com.jackwu.news.logics;

import com.jackwu.news.constants.DbConstants;
import com.jackwu.news.models.Channel;
import com.jackwu.news.utils.AppUtils;

import net.lzzy.sqllib.SqlRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


public class ChannelFactory {
    private static ChannelFactory instance;
    private SqlRepository<Channel> repository;

    private ChannelFactory() {
        repository = new SqlRepository<>(AppUtils.getContext(), Channel.class, DbConstants.dbPackage);
        List<Channel> channels = repository.get();
        if (channels.size() == 0) {
            String[][] list = new String[][]{{"头条", "top", "0"}, {"社会", "shehui", "1"},
                    {"国内", "guonei", "2"}, {"国际", "guoji", "3"}, {"娱乐", "yule", "4"}, {"体育", "tiyu", "5"},
                    {"军事", "junshi", "6"}, {"科技", "keji", "7"}, {"财经", "caijing", "8"}, {"时尚", "shishang", "9"}};
            for (String[] strings : list) {
                Channel channel = new Channel();
                channel.setName(strings[0]);
                channel.setType(strings[1]);
                channel.setPosition(Integer.parseInt(strings[2]));
                repository.insert(channel);
            }
        }
    }

    public static ChannelFactory getInstance() {
        if (instance == null) {
            synchronized (ChannelFactory.class) {
                if (instance == null)
                    instance = new ChannelFactory();
            }
        }
        return instance;
    }

    public List<Channel> getChannels() {
        return repository.get();
    }

    public void update(Channel channel) {
        repository.update(channel);
    }

    public void sort(List<Channel> channels) {
        Collections.sort(channels, new Comparator<Channel>() {
            @Override
            public int compare(Channel lhs, Channel rhs) {
                int isShowL = lhs.isShow() ? 1 : 0;
                int isShowR = rhs.isShow() ? 1 : 0;
                int l = lhs.getPosition();
                int r = rhs.getPosition();
                if (isShowL > isShowR)
                    return -1;
                if (isShowR > isShowL)
                    return 1;
                if (isShowR == isShowL) {
                    if (l > r)
                        return 1;
                    if (r > l)
                        return -1;
                }
                return 0;
            }
        });
    }

    public int getShowCount() {
        List<Channel> channels = repository.get();
        int count = 0;
        for (Channel channel : channels) {
            if (channel.isShow())
                count++;
        }
        return count;
    }

    public List<Channel> getShowChannel() {
        try {
            return repository.getByKeyword("1", new String[]{DbConstants.CHANNEL_IS_SHOW}, true);
        } catch (IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
}
