package com.jackwu.news.fragment;

import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.jackwu.news.R;
import com.jackwu.news.adapter.NewsRecyclerAdapter;
import com.jackwu.news.adapter.OnRecyclerViewItemClickListener;
import com.jackwu.news.adapter.RecyclerViewHolder;
import com.jackwu.news.api.Api;
import com.jackwu.news.constants.ApiConstants;
import com.jackwu.news.dialog.CustomBottomDialog;
import com.jackwu.news.json.NewJson;
import com.jackwu.news.models.News;
import com.jackwu.news.utils.AppUtils;
import com.jackwu.news.utils.ToastUtils;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import rx.subjects.PublishSubject;

import static com.jackwu.news.utils.AppUtils.isConnectivity;


public class NewsFragment extends BaseFragment {
    private String type;
    private RecyclerView rlv;
    private SwipeRefreshLayout srl;
    private List<News> newses;
    private NewsJumpListener listener;
    private CustomBottomDialog customBottomDialog;
    private Subscription sub;
    private TextView tv;
    private ProgressBar pb;
    private LinearLayoutManager layoutManager;
    private NewsRecyclerAdapter adapter;
    private List<News> newsList;

    public static NewsFragment getInstance(String type) {
        NewsFragment fragment = new NewsFragment();
        Bundle args = new Bundle();
        args.putString(ApiConstants.JSON_NEWS_TYPE, type);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        newses = new ArrayList<>();
        Bundle args = getArguments();
        type = args.getString(ApiConstants.JSON_NEWS_TYPE);
    }


    @Override
    protected int setContentView() {
        return R.layout.fragment_news;
    }

    protected void find(View view) {
        rlv = (RecyclerView) view.findViewById(R.id.fragment_news_rlv);
        srl = (SwipeRefreshLayout) view.findViewById(R.id.fragment_news_srl);
    }


    protected void init() {
        srl.post(new Runnable() {
            @Override
            public void run() {
                srl.setRefreshing(true);
            }
        });
        if (newses.size() == 0) {
            if (AppUtils.isConnectivity())
                dowNewsRx(type);
            else {
                srl.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        ToastUtils.showShortToastSafe("网络异常，请检查网络！");
                        srl.setRefreshing(false);
                    }
                }, 1000);
            }
        }
        rlv.setHasFixedSize(true);
        View footView = LayoutInflater.from(getActivity()).inflate(R.layout.item_footview, null);
        footView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        tv = (TextView) footView.findViewById(R.id.item_footview_tv);
        pb = (ProgressBar) footView.findViewById(R.id.item_footview_pb);

        srl.setColorSchemeColors(getResources().getIntArray(R.array.refresh_colors));
        adapter = new NewsRecyclerAdapter(newses, R.layout.item_news, footView) {
            @Override
            public void populate(RecyclerViewHolder holder, final News news) {
                holder.setTextView(R.id.news_item_tv_title, news.getTitle())
                        .setTextView(R.id.news_item_tv_time, news.getDate())
                        .setImageView(R.id.news_item_tv_img, news.getUrl())
                        .setImageView(R.id.news_item_tv_img, news.getImgUrl())
                        .setImageView(R.id.news_item_tv_img_share, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                customBottomDialog = new CustomBottomDialog(getActivity());
                                customBottomDialog.setShareContent(news.getTitle() + "\n" + news.getUrl());
                                customBottomDialog.show();
                            }
                        });

            }
        };


        rlv.setAdapter(adapter);
        rlv.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                outRect.set(10, 10, 10, 0);
            }
        });
        layoutManager = new LinearLayoutManager(getContext());
        rlv.setLayoutManager(layoutManager);
        srl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (isConnectivity()) {
                    tv.setVisibility(View.GONE);
                    dowNewsRx(type);
                } else {
                    ToastUtils.showShortToastSafe("网络异常，请检查网络！");
                    srl.setRefreshing(false);
                }
            }
        });

        adapter.setOnItemClickListener(new OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                News news = newses.get(position);
                listener.recyclerItemClick(news.getUrl(), news.getSource(), news.getTitle());
            }
        });

        rlv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            private boolean isScroll = false;

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE && isScroll) {
                    int lastVisibleItem = layoutManager.findLastCompletelyVisibleItemPosition();
                    int totalItemCount = layoutManager.getItemCount();

                    if (lastVisibleItem == (totalItemCount - 1)) {
                        LoadMore();
                        isScroll = false;
                    }
                }

            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                isScroll = dy > 0;
            }
        });
    }


    private void dowNewsRx(final String type) {
        PublishSubject<String> publishSubject = PublishSubject.create();
        sub = publishSubject.doOnSubscribe(new Action0() {
            @Override
            public void call() {
                srl.setRefreshing(true);
            }
        })
                .observeOn(Schedulers.io())
                .map(new Func1<String, String>() {
                    @Override
                    public String call(String s) {
                        try {
                            return Api.getJson(s);
                        } catch (IOException e) {
                            e.printStackTrace();
                            return null;
                        }
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        srl.setRefreshing(false);
                    }

                    @Override
                    public void onNext(String s) {
                        if (s != null) {
                            try {
                                newsList = NewJson.getNews(s);
                                newses.clear();
                                if (newsList.size() > 7) {
                                    for (int i = 0; i < 7; i++) {
                                        newses.add(newsList.get(i));
                                        newsList.remove(i);
                                        ToastUtils.showShortToastSafe("刷新成功");
                                    }
                                } else
                                    newses.addAll(newsList);
                                adapter.notifyDataSetChanged();

       } catch (IllegalAccessException | java.lang.InstantiationException | JSONException e) {
                                e.printStackTrace();
                                ToastUtils.showShortToastSafe(e.getMessage());
                            }
                        } else {
                            ToastUtils.showShortToastSafe("获取失败！");
                        }
                        srl.setRefreshing(false);
                    }
                });
        publishSubject.onNext(type);
    }



    private void LoadMore() {
        if (newsList.size() != 0) {
            pb.setVisibility(View.VISIBLE);
        }
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (newsList.size() == 0) {
                    tv.setVisibility(View.VISIBLE);
                    pb.setVisibility(View.GONE);
                }
                if (newsList.size() > 7) {
                    for (int i = 0; i < newsList.size(); i++) {
                        newses.add(newsList.get(i));
                        newsList.remove(i);
                    }
                } else if (newsList.size() < 7) {
                    newses.addAll(newsList);
                    newsList.clear();
                }
                adapter.notifyDataSetChanged();
            }
        }, 1500);

    }



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener = (NewsJumpListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "必须实现NewsJumpListener");
        }
    }


    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        listener = null;
        if (sub != null && !sub.isUnsubscribed())
            sub.unsubscribe();
    }
}
