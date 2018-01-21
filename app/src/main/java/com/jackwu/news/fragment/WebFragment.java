package com.jackwu.news.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.jackwu.news.R;
import com.jackwu.news.constants.ApiConstants;
import com.jackwu.news.dialog.CustomBottomDialog;
import com.jackwu.news.utils.ADFilterUtils;
import com.jackwu.news.utils.ClipboardUtils;
import com.jackwu.news.utils.ToastUtils;


public class WebFragment extends BaseFragment {
    private WebView webView;
    private Toolbar toolbar;
    private String title;
    private String source;
    private String url;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getActivity().getIntent() != null) {
            title = getActivity().getIntent().getStringExtra(ApiConstants.JSON_NEWS_TITLE);
            source = getActivity().getIntent().getStringExtra(ApiConstants.JSON_NEWS_SOURCE);
            url = getActivity().getIntent().getStringExtra(ApiConstants.JSON_NEWS_URL);
        }
    }

    @Override
    protected int setContentView() {
        return R.layout.fragment_web;
    }

    @Override
    protected void find(View view) {
        webView = (WebView) view.findViewById(R.id.fragment_web);

        toolbar = (Toolbar) view.findViewById(R.id.fragment_web_toolbar);
    }

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void init() {
        Log.i("webfragment ", "init: " + url);
        toolbar.setTitle(source);
//        //声明WebSettings子类
//        WebSettings webSettings = webView.getSettings();
//
//////如果访问的页面中要与Javascript交互，则webview必须设置支持Javascript
////        webSettings.setJavaScriptEnabled(true);
//
//
////设置自适应屏幕，两者合用
//        webSettings.setUseWideViewPort(true); //将图片调整到适合webview的大小
//        webSettings.setLoadWithOverviewMode(true); // 缩放至屏幕的大小
//
////缩放操作
//        webSettings.setSupportZoom(true); //支持缩放，默认为true。是下面那个的前提。
//        webSettings.setBuiltInZoomControls(true); //设置内置的缩放控件。若为false，则该WebView不可缩放
//        webSettings.setDisplayZoomControls(false); //隐藏原生的缩放控件
//
////其他细节操作
//        if (AppUtils.isConnectivity()) {
//            webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);//根据cache-control决定是否从网络上取数据。
//        } else {
//            webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);//没网，则从本地获取，即离线加载
//        }
//
//        webSettings.setDomStorageEnabled(true); // 开启 DOM storage API 功能
//        webSettings.setDatabaseEnabled(true);   //开启 database storage API 功能
//        webSettings.setAppCacheEnabled(true);//开启 Application Caches 功能
//        webSettings.setAllowFileAccess(true); //设置可以访问文件
//        webSettings.setJavaScriptCanOpenWindowsAutomatically(true); //支持通过JS打开新窗口
//        webSettings.setLoadsImagesAutomatically(true); //支持自动加载图片
//        webSettings.setDefaultTextEncodingName("utf-8");//设置编码格式
//
//
////步骤3. 复写shouldOverrideUrlLoading()方法，使得打开网页时不调用系统浏览器， 而是在本WebView中显示
//        webView.setWebViewClient(new WebViewClient() {
//            @Override
//            public boolean shouldOverrideUrlLoading(WebView view, String url) {
//                view.loadUrl(url);
//                return true;
//            }
//        });

        WebSettings webSettings = webView.getSettings();
        //  设置自适应屏幕，两者合用
        webSettings.setUseWideViewPort(true); //将图片调整到适合webview的大小
        webSettings.setLoadWithOverviewMode(true); // 缩放至屏幕的大小

//缩放操作
        webSettings.setSupportZoom(true); //支持缩放，默认为true。是下面那个的前提。
        webSettings.setBuiltInZoomControls(true); //设置内置的缩放控件。若为false，则该WebView不可缩放
        webSettings.setDisplayZoomControls(false); //隐藏原生的缩放控件
        webSettings.setJavaScriptCanOpenWindowsAutomatically(false); //支持通过JS打开新窗口
        webSettings.setLoadsImagesAutomatically(true); //支持自动加载图片
        webSettings.setDefaultTextEncodingName("utf-8");//设置编码格式

        webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);//根据cache-control决定是否从网络上取数据。

        // 复写shouldOverrideUrlLoading()方法，使得打开网页时不调用系统浏览器， 而是在本WebView中显示
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });

        webSettings.setJavaScriptEnabled(true);
        webSettings.setAppCacheEnabled(true);
        webView.setWebViewClient(new NoAdWebViewClient(getActivity()));
        webView.loadUrl(url);
        toolbar.inflateMenu(R.menu.menu_web);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menu_web_share:
                        CustomBottomDialog dialog = new CustomBottomDialog(getActivity());
                        dialog.setShareContent(title + "\n" + url);
                        dialog.show();
                        break;
                    case R.id.menu_web_copy:
                        ClipboardUtils.copyText(url);
                        ToastUtils.showShortToastSafe("已复制");
                        break;
                    case R.id.menu_web_browser:
                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        intent.setData(Uri.parse(url));
                        startActivity(intent);
                        break;
                }
                return true;
            }
        });
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });


        webView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK && webView.canGoBack()) {
                    webView.goBack();
                    return true;
                }
                return false;
            }
        });


    }

    private class MyWebViewClient extends WebViewClient {


        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            startActivity(intent);
            return true;
        }
    }

    private class NoAdWebViewClient extends WebViewClient {

        private Context context;

        public NoAdWebViewClient(Context context) {
            this.context = context;
        }

        @Override
        public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
            url = url.toLowerCase();
            if (!ADFilterUtils.hasAd(context, url)) {
                return super.shouldInterceptRequest(view, url);//正常加载
            } else {
                return new WebResourceResponse(null, null, null);//含有广告资源屏蔽请求
            }

        }


    }
}
