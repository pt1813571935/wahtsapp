package com.zizhong.chatroom.activitys;

import android.content.Intent;
import android.opengl.Visibility;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

import com.bumptech.glide.Glide;
import com.google.android.gms.ads.AdView;
import com.zizhong.chatroom.R;
import com.zizhong.chatroom.Utils.SharedPreferencesUtil;
import com.zizhong.chatroom.base.IBaseActivity;
import com.zizhong.chatroom.costomview.PopupWindowDialog;
import com.zizhong.chatroom.googleadmob.Bannerss;
import com.zizhong.chatroom.googleadmob.Table;

/**
 * FlymeCat保佑，永无BUG
 * Created by DJL on 2021/12/22.
 */

public class PhotoDetailActivity extends IBaseActivity {

    private TextView txt_photo_name, tv_del;
    private ImageView iv_back, img_photo;
    private String photoUrl;
    private VideoView video_play;
    private int inputType;
    private AdView adView;
    private Table table;
    @Override
    protected void getIntentWord() {
        super.getIntentWord();
        photoUrl = getIntent().getStringExtra("photoUrl");
        inputType = getIntent().getIntExtra("inputType", 1);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_photo_detail;
    }

    @Override
    protected void initView() {
        txt_photo_name = findViewByID(R.id.txt_photo_name);
        tv_del = findViewByID(R.id.tv_del);
        img_photo = findViewByID(R.id.img_photo);
        iv_back = findViewByID(R.id.iv_back);
        video_play = findViewByID(R.id.video_play);
        adView = findViewByID(R.id.adView);
    }

    @Override
    protected void initData() {
        if (!"".equals(photoUrl) && photoUrl != null) {
            txt_photo_name.setText(photoUrl.substring(photoUrl.lastIndexOf('/') + 1));
            if (inputType == 1) {
                Glide.with(this).load(photoUrl).into(img_photo);
            } else {
                video_play.setVisibility(View.VISIBLE);
                img_photo.setVisibility(View.GONE);
                video_play.setVideoPath(photoUrl);
                video_play.start();
            }
        } else {
            txt_photo_name.setText("图片详情");
        }
        String  ok = SharedPreferencesUtil.getSharedPreferences(this).getString("OK", "");
        Bannerss bannerss = new Bannerss();
        table = new Table();
        if (ok.equals("123")){
            bannerss.goole_banner(adView,this);
            table.gooles_add(this);

        }
    }

    @Override
    protected void initListener() {
        tv_del.setOnClickListener(this);
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
            case R.id.tv_del:
                deleteConfirm(view);

                break;

        }
    }

    private void deleteConfirm(View v) {
        View view = LayoutInflater.from(this).inflate(R.layout.photos_delete_confirm, null);
        PopupWindowDialog popupWindowDialog = new PopupWindowDialog(view, this, new PopupWindowDialog.PopupWindowDialogListener() {
            @Override
            public void onIsDis(boolean isDis) {
                table.gooles_show(PhotoDetailActivity.this);
            }
        });
        view.findViewById(R.id.txt_yes).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindowDialog.dismiss();
                Intent intent = new Intent();
                intent.putExtra("photoUrl", photoUrl);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        view.findViewById(R.id.txt_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindowDialog.dismiss();
            }
        });
        popupWindowDialog.showCenterDialog(v);
    }
}
