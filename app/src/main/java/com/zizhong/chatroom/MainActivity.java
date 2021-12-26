package com.zizhong.chatroom;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;

import com.zizhong.chatroom.base.IBaseActivity;

public class MainActivity extends IBaseActivity {

    private FrameLayout fl_chat_room,frame_settings;
    private FrameLayout fl_emoji;
    private FrameLayout fl;

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
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {
        fl_chat_room.setOnClickListener(this);
        fl_emoji.setOnClickListener(this);
        fl.setOnClickListener(this);
        frame_settings.setOnClickListener(this);
    }

    @Override
    protected void initOther(Bundle savedInstanceState) {

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
        }

    }
}