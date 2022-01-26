package com.zizhong.chatroom.activitys;

import android.os.Bundle;
import android.webkit.WebView;

import com.zizhong.chatroom.R;
import com.zizhong.chatroom.base.IBaseActivity;

public class WebViewActivity extends IBaseActivity {

    //      private String path = "http://api.whatsapp.com";
//      private String path = "http://www.google.cn";
    private String path = "http://web.whatsapp.com/";

    private WebView webView;

    @Override
    public int getLayoutId() {
        return R.layout.activity_web_view;
    }

    @Override
    protected void initView() {
        webView = findViewByID(R.id.webView);
    }

    @Override
    protected void initData() {
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setSupportZoom(true);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setAppCacheEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setUserAgentString("User-Agent:Android");
        webView.loadUrl(path);
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initOther(Bundle savedInstanceState) {

    }
}