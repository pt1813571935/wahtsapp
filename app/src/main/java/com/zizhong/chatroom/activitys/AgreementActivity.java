package com.zizhong.chatroom.activitys;

import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Bundle;
import android.util.Log;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;


import com.umeng.analytics.MobclickAgent;
import com.zizhong.chatroom.R;
import com.zizhong.chatroom.base.IBaseActivity;


public class AgreementActivity extends IBaseActivity {
    private WebView webView;
    private boolean isWebViewloadError=false;//记录webView是否已经加载出错



    @Override
    public int getLayoutId() {
        return R.layout.activity_agreement;
    }

    public void initData() {
        webView.loadUrl("file:android_asset/user.html");
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });


    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initOther(Bundle savedInstanceState) {

    }

    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }
    public void initView() {
        webView = (WebView) findViewById(R.id.weview);
    }

}
