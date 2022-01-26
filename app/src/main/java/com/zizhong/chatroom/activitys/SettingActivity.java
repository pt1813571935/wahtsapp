package com.zizhong.chatroom.activitys;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.ads.AdView;
import com.zizhong.chatroom.R;
import com.zizhong.chatroom.base.IBaseActivity;
import com.zizhong.chatroom.googleadmob.Bannerss;

public class SettingActivity extends IBaseActivity {
    private ImageView iv_back;
    private TextView version_tv;
    private TextView txt_settings_userAgreement;
    private TextView txt_settings_privacyPolicy;
    private AdView adView;

    @Override
    public int getLayoutId() {
        return R.layout.activity_setting;
    }

    @Override
    protected void initView() {
        iv_back = findViewById(R.id.iv_back);
        version_tv = findViewById(R.id.version_tv);
        txt_settings_privacyPolicy = findViewByID(R.id.txt_settings_privacyPolicy);
        txt_settings_userAgreement = findViewByID(R.id.txt_settings_userAgreement);
        adView = findViewByID(R.id.adView);
    }

    @Override
    protected void initData() {
        version_tv.setText("V" + getVersionName());
        Bannerss bannerss = new Bannerss();
        bannerss.goole_banner(adView,this);
    }

    @Override
    protected void initListener() {
        iv_back.setOnClickListener(this);
        txt_settings_privacyPolicy.setOnClickListener(this);
        txt_settings_userAgreement.setOnClickListener(this);
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
            case R.id.txt_settings_privacyPolicy:
                //隐私政策
                Intent intent = new Intent(this, PolicyActivity.class);
                startActivity(intent);
                break;
            case R.id.txt_settings_userAgreement:
                //用户协议
                Intent intent1 = new Intent(this, AgreementActivity.class);
                startActivity(intent1);
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
