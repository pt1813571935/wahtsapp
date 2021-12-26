package com.zizhong.chatroom;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.zizhong.chatroom.base.IBaseActivity;

public class SettingActivity extends IBaseActivity {
    private ImageView iv_back;
    private TextView version_tv;

    @Override
    public int getLayoutId() {
        return R.layout.activity_setting;
    }

    @Override
    protected void initView() {
        iv_back = findViewById(R.id.iv_back);
        version_tv = findViewById(R.id.version_tv);
    }

    @Override
    protected void initData() {
        version_tv.setText("V" + getVersionName());
    }

    @Override
    protected void initListener() {
        iv_back.setOnClickListener(this);
    }

    @Override
    protected void initOther(Bundle savedInstanceState) {

    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
        }
    }

    private String getVersionName() {
        // 获取packagemanager的实例
        PackageManager packageManager = getPackageManager();
        // getPackageName()是你当前类的包名，0代表是获取版本信息
        PackageInfo packInfo = null;
        try {
            packInfo = packageManager.getPackageInfo(getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        String version = packInfo.versionName;
        return version;
    }
}
