package com.jackwu.news.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.jackwu.news.R;
import com.jackwu.news.adapter.ItemTouchHelperCallback;
import com.jackwu.news.adapter.RecyclerViewHolder;
import com.jackwu.news.adapter.SimpleRecyclerAdapter;
import com.jackwu.news.logics.ChannelFactory;
import com.jackwu.news.models.Channel;
import com.jackwu.news.utils.AppUtils;
import com.jackwu.news.utils.ToastUtils;

import java.util.List;


public class ChannelActivity extends AppCompatActivity {
    private List<Channel> channels;
    private ChannelFactory factory;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_channel);
        ((AppUtils) getApplication()).addActivity(this);
        factory = ChannelFactory.getInstance();
        channels = factory.getChannels();
        factory.sort(channels);
        Toolbar toolbar = (Toolbar) findViewById(R.id.activity_channel_toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (int i = 0; i < channels.size(); i++) {
                    channels.get(i).setPosition(i);
                    factory.update(channels.get(i));
                }
                startActivity(new Intent(ChannelActivity.this, MainActivity.class));
                overridePendingTransition(0, 0);
                finish();
            }
        });
        RecyclerView rlv = (RecyclerView) findViewById(R.id.activity_channel_rlv);
        rlv.setLayoutManager(new LinearLayoutManager(this));
        rlv.setHasFixedSize(true);
        SimpleRecyclerAdapter<Channel> adapter = new SimpleRecyclerAdapter<Channel>(channels, R.layout.item_channel) {
            @Override
            public void populate(RecyclerViewHolder holder, final Channel channel) {
                final Switch swi = holder.getView(R.id.item_channel_switch);
                holder.setTextView(R.id.item_channel_tv, channel.getName())
                        .setSwitch(R.id.item_channel_switch, channel.isShow(), new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                if (channel.isShow() && factory.getShowCount() == 1) {
                                    swi.setChecked(true);
                                    ToastUtils.showShortToastSafe("至少要有一个频道");
                                } else {
                                    channel.setShow(isChecked);
                                    factory.update(channel);
                                    if (isChecked)
                                        ToastUtils.showShortToastSafe(channel.getName() + "频道已显示");
                                    else
                                        ToastUtils.showShortToastSafe(channel.getName() + "频道已隐藏");
                                }
                            }
                        });

            }

            @Override
            public boolean deletePersist(Channel channel) {
                return false;
            }

            @Override
            public boolean addPersist(Channel channel) {
                return false;
            }
        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelperCallback(adapter));
        itemTouchHelper.attachToRecyclerView(rlv);
        rlv.setAdapter(adapter);
        rlv.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
    }


    @Override
    public void onBackPressed() {
        for (int i = 0; i < channels.size(); i++) {
            channels.get(i).setPosition(i);
            factory.update(channels.get(i));
        }
        startActivity(new Intent(ChannelActivity.this, MainActivity.class));
        finish();
    }


}
