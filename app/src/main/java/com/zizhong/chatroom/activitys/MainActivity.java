package com.zizhong.chatroom.activitys;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;


import com.google.android.gms.ads.AdView;
import com.umeng.analytics.AnalyticsConfig;
import com.umeng.analytics.MobclickAgent;
import com.umeng.commonsdk.UMConfigure;
import com.zizhong.chatroom.BuildConfig;
import com.zizhong.chatroom.R;
import com.zizhong.chatroom.Utils.Dialogss;
import com.zizhong.chatroom.Utils.EventBusssss;
import com.zizhong.chatroom.Utils.SharedPreferencesUtil;
import com.zizhong.chatroom.Utils.log.LogUtils;
import com.zizhong.chatroom.base.IBaseActivity;
import com.zizhong.chatroom.googleadmob.Bannerss;
import com.zizhong.chatroom.googleadmob.TableHome;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;


public class MainActivity extends IBaseActivity {

    private FrameLayout fl_chat_room,frame_settings;
    private FrameLayout fl_emoji;
    private FrameLayout fl;
    private FrameLayout fl_rate;
    private View inflate;
    private AdView adView;
    private TableHome tableHome;


    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }




    @Override
    protected void initView() {
        fl_chat_room = findViewById(R.id.fl_chat_room);
        fl_emoji = findViewById(R.id.fl_emoji);
        fl = findViewById(R.id.fl);
        frame_settings=findViewByID(R.id.frame_settings);
        fl_rate=findViewByID(R.id.fl_rate);
        adView = findViewByID(R.id.adView);

    }

    @Override
    protected void initData() {
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        String  ok = SharedPreferencesUtil.getSharedPreferences(this).getString("OK", "");
        Bannerss bannerss = new Bannerss();
        tableHome = new TableHome();
        if (!ok.equals("123")){
            new Dialogss().onDialog(this,inflate);
        }else {
            initYouMeng();
            bannerss.goole_banner(adView,this);
            this.tableHome.gooles_add(this);
        }


    }




    @Override
    protected void initListener() {
        fl_chat_room.setOnClickListener(this);
        fl_emoji.setOnClickListener(this);
        fl.setOnClickListener(this);
        frame_settings.setOnClickListener(this);
        fl_rate.setOnClickListener(this);
    }

    @Override
    protected void initOther(Bundle savedInstanceState) {

    }
    private void initYouMeng() {
        //设置log开关，默认为false
        UMConfigure.setLogEnabled(true);
        String channel2 = AnalyticsConfig.getChannel(this);
        LogUtils.e("渠道名字",channel2);
        UMConfigure.init(this,"61c95fa4e0f9bb492bad280a"
                ,channel2,UMConfigure.DEVICE_TYPE_PHONE,null);
        // 选用AUTO页面采集模式
        MobclickAgent.setPageCollectionMode(MobclickAgent.PageMode.AUTO);

    }
    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()){
            case R.id.fl:
                Intent intent1 = new Intent(MainActivity.this,LocalActivity.class);
                startActivity(intent1);
                break;
            case R.id.fl_chat_room:
                Intent intent2 = new Intent(MainActivity.this,WebViewActivity.class);
                startActivity(intent2);
                break;
            case R.id.fl_emoji:
                Intent intent3 = new Intent(MainActivity.this,EmojiActivity.class);
                startActivity(intent3);
                break;

            case R.id.frame_settings:
                Intent intent4 = new Intent(MainActivity.this,SettingActivity.class);
                startActivity(intent4);
                break;
            case R.id.fl_rate:
                goRate();
                break;
        }

    }

    private void goRate() {
        String market = "market://details?id=" + BuildConfig.APPLICATION_ID;
        Uri uri = Uri.parse(market);
        Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
        goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                Intent.FLAG_ACTIVITY_NEW_DOCUMENT |
                Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
        try {
            startActivity(goToMarket);
        } catch (ActivityNotFoundException e) {
            String url = "http://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID;
            startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse(url)));
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (tableHome.mInterstitialAd !=null){
            tableHome.gooles_show(this);
        }
    }

    //3.0以后这个方法自己随便写，不过要加@Subscribe这个注解
    @Subscribe(threadMode = ThreadMode.POSTING, sticky = true)//true 就是允许接收粘性事件
    public void onEventMain(EventBusssss.getTure event) {
        initData();
        tableHome.gooles_add(this);
    }
}