package com.zizhong.chatroom.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.gms.ads.AdView;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.zizhong.chatroom.R;
import com.zizhong.chatroom.adapter.MyViewPageAdapter;
import com.zizhong.chatroom.base.IBaseActivity;
import com.zizhong.chatroom.base.IBaseFragment;
import com.zizhong.chatroom.costomview.PopupWindowDialog;
import com.zizhong.chatroom.fragment.PhotoFragment;
import com.zizhong.chatroom.googleadmob.Bannerss;
import com.zizhong.chatroom.listener.IBaseDataChangeListener;

import java.util.ArrayList;
import java.util.List;

public class LocalActivity extends IBaseActivity {
    private TabLayout mTabLayout;
    private ViewPager2 mViewPage;
    private String[] tabTitles;//tab的标题
    private List<IBaseFragment> mDatas = new ArrayList<>();//ViewPage2的Fragment容器
    private ImageView iv_back;
    private TextView tv_edit;
    private PhotoFragment photoFragment;
    private PhotoFragment videoFragment;
    private int inputType = 1;
    private AdView adView;

    @Override
    public int getLayoutId() {
        return R.layout.activity_local;
    }

    protected void initView() {
        mTabLayout = findViewById(R.id.tab_layout);
        mViewPage = findViewById(R.id.view_page);
        iv_back = findViewById(R.id.iv_back);
        tv_edit = findViewById(R.id.tv_edit);
        adView = findViewByID(R.id.adView);
    }


    protected void initData() {
        tabTitles = new String[]{"Photos", "Videos"};
        photoFragment = new PhotoFragment();
        photoFragment.setInputType(1);
        videoFragment = new PhotoFragment();
        videoFragment.setInputType(2);
        mDatas.add(photoFragment);
        mDatas.add(videoFragment);
        photoFragment.setiBaseDataChangeListener(dataChangeListener);
        videoFragment.setiBaseDataChangeListener(dataChangeListener);
        MyViewPageAdapter mAdapter = new MyViewPageAdapter(this, mDatas);
        mViewPage.setAdapter(mAdapter);
        //TabLayout与ViewPage2联动关键代码
        new TabLayoutMediator(mTabLayout, mViewPage, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                tab.setText(tabTitles[position]);
            }
        }).attach();
        Bannerss bannerss = new Bannerss();
        bannerss.goole_banner(adView,this);
    }

    @Override
    protected void initListener() {
        iv_back.setOnClickListener(this);
        tv_edit.setOnClickListener(this);
        //ViewPage2选中改变监听
        mViewPage.registerOnPageChangeCallback(mOnPageChangeCallback);
        //TabLayout的选中改变监听
        mTabLayout.addOnTabSelectedListener(mOnTabSelectedListener);
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
            case R.id.tv_edit:
                if (checkedNum > 0) {
                    deleteConfirm(view);
                    return;
                }
                PhotoFragment fragment = inputType == 1 ? photoFragment : videoFragment;
                fragment.setControl(!photoFragment.isControl());
                if (fragment.isControl()) {
                    tv_edit.setText("Delete(0)");
                    tv_edit.setTextColor(getResources().getColor(android.R.color.holo_red_dark));
                } else {
                    checkedNum = 0;
                    tv_edit.setText("Edit");
                    tv_edit.setTextColor(getResources().getColor(android.R.color.white));
                }
                break;
        }
    }

    private ViewPager2.OnPageChangeCallback mOnPageChangeCallback = new ViewPager2.OnPageChangeCallback() {
        @Override
        public void onPageSelected(int position) {
            super.onPageSelected(position);
            inputType = position + 1;
        }
    };

    private TabLayout.OnTabSelectedListener mOnTabSelectedListener = new TabLayout.OnTabSelectedListener() {
        @Override
        public void onTabSelected(TabLayout.Tab tab) {
            PhotoFragment fragment = inputType == 1 ? photoFragment : videoFragment;
            fragment.setControl(false);
            checkedNum = 0;
            tv_edit.setText("Edit");
            tv_edit.setTextColor(getResources().getColor(android.R.color.white));
        }

        @Override
        public void onTabUnselected(TabLayout.Tab tab) {

        }

        @Override
        public void onTabReselected(TabLayout.Tab tab) {

        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 1:
//                if (resultCode == RESULT_OK) {
//                    videoFragment.setPhotoInfos(data);
//                }
                break;
            case 2:
                if (resultCode == RESULT_OK) {
                    PhotoFragment fragment = inputType == 1 ? photoFragment : videoFragment;
                    fragment.setPhotoInfos(data);
                }
                break;
            case 3:
                if (resultCode == RESULT_OK) {
                    PhotoFragment fragment = inputType == 1 ? photoFragment : videoFragment;
                    fragment.delPhotoInfo(data);
                }
                break;
        }
    }


    private int checkedNum = 0;
    private IBaseDataChangeListener dataChangeListener = new IBaseDataChangeListener() {
        @Override
        public void dataChange(int type, Object o) {
            if (type == 1) {
                checkedNum = (int) o;
                tv_edit.setText("Delete(" + checkedNum + ")");
            }
        }
    };


    private void deleteConfirm(View v) {
        View view = LayoutInflater.from(this).inflate(R.layout.view_delete_confirm, null);
        PopupWindowDialog popupWindowDialog = new PopupWindowDialog(view, this, new PopupWindowDialog.PopupWindowDialogListener() {
            @Override
            public void onIsDis(boolean isDis) {

            }
        });
        view.findViewById(R.id.txt_yes).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindowDialog.dismiss();
                PhotoFragment fragment = inputType == 1 ? photoFragment : videoFragment;
                fragment.confirmDelete();
                fragment.setControl(false);
                checkedNum = 0;
                tv_edit.setText("Edit");
                tv_edit.setTextColor(getResources().getColor(android.R.color.white));
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