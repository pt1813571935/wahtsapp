package com.zizhong.chatroom.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.giftedcat.picture.lib.selector.MultiImageSelector;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.listener.OnResultCallbackListener;
import com.zizhong.chatroom.PhotoDetailActivity;
import com.zizhong.chatroom.R;
import com.zizhong.chatroom.Utils.GlideEngine;
import com.zizhong.chatroom.Utils.JsonDataUtils;
import com.zizhong.chatroom.Utils.log.LogUtils;
import com.zizhong.chatroom.base.IBaseFragment;
import com.zizhong.chatroom.entity.PhotoInfoEntity;
import com.zizhong.chatroom.fragment.adapter.FragmentPhotoAdapter;
import com.zizhong.chatroom.listener.IBaseDataChangeListener;
import com.zizhong.chatroom.listener.IBaseOnItemClickListener;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class VideoFragment extends IBaseFragment {
    private ImageView iv_add;
    private ArrayList<PhotoInfoEntity> mSelectList = new ArrayList<>();
    private RecyclerView recyclerView;
    private FragmentPhotoAdapter photoAdapter;
    private LinearLayout add_photo_ll1;
    private boolean isControl = false;
    private IBaseDataChangeListener iBaseDataChangeListener;
    private int inputType=1;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_video;
    }

    @Override
    protected void initView() {
        getData();
        add_photo_ll1 = findViewByID(R.id.add_photo_ll1);
        recyclerView = findViewByID(R.id.rv_phtots);
        iv_add = findViewByID(R.id.iv_add);
        iv_add.setOnClickListener(this);
        photoAdapter = new FragmentPhotoAdapter(mContext);
        photoAdapter.setImgList(mSelectList);
        photoAdapter.setItemClickListener(iBaseOnItemClickListener);
        recyclerView.setLayoutManager(new GridLayoutManager(mContext, 3));
        recyclerView.setAdapter(photoAdapter);
        defaultView();
    }

    private void getData() {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences("videos", 0);
        String content = sharedPreferences.getString("videoList", "").trim();
        LogUtils.e("视频信息", content + "===");

        Gson gson = new Gson();
        Type type = new TypeToken<List<PhotoInfoEntity>>() {
        }.getType();
        List<PhotoInfoEntity> list = gson.fromJson(content, type);
        if (list != null && list.size() > 0) {
            mSelectList.addAll(list);
        }
    }

    @SuppressLint("ApplySharedPref")
    private void setData() {
        String json = JsonDataUtils.getToJson(mSelectList);
        SharedPreferences sharedPreferences = mContext.getSharedPreferences("videos", 0);
        sharedPreferences.edit().putString("videoList", json).commit();
    }


    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.iv_add:
                getImageCallback();
                PictureSelector.create(this)
                        .openGallery(PictureMimeType.ofVideo())
                        .imageEngine(GlideEngine.createGlideEngine())
                        .forResult(PictureConfig.CHOOSE_REQUEST);

                break;
        }
    }

    private void getImageCallback() {
        PictureSelector.create(this)
                .openGallery(PictureMimeType.ofVideo())
                .imageEngine(GlideEngine.createGlideEngine())
                .forResult(new OnResultCallbackListener<LocalMedia>() {
                    @Override
                    public void onResult(List<LocalMedia> result) {
                        for (int i = 0; i < result.size(); i++) {
                            boolean isComprise = false;
                            for (int j = 0; j < mSelectList.size(); j++) {
                                if (result.get(i).getCompressPath().equals(mSelectList.get(j).getImgUrl())) {
                                    isShowRepetitionHint = true;
                                    isComprise = true;
                                }
                            }
                            if (!isComprise && !"".equals(result.get(i).getPath())) {
                                mSelectList.add(new PhotoInfoEntity(result.get(i).getCompressPath(), false));
                            }
                        }
                        if (isShowRepetitionHint) {
                            Toast.makeText(mContext, "已为您自动删除重复资源", Toast.LENGTH_LONG).show();
                        }
                        defaultView();
                    }

                    @Override
                    public void onCancel() {
                        // onCancel Callback
                    }
                });
    }


    private void defaultView() {
        if (mSelectList.size() > 0) {
            photoAdapter.notifyDataSetChanged();
            recyclerView.setVisibility(View.VISIBLE);
            add_photo_ll1.setVisibility(View.GONE);
            setData();
        } else {
            add_photo_ll1.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        }
    }

    private boolean isShowRepetitionHint = false;

    public void delPhotoInfo(Intent data) {
        String delUrl = data.getStringExtra("photoUrl");
        for (int i = 0; i < mSelectList.size(); i++) {
            if (mSelectList.get(i).getImgUrl().equals(delUrl)) {
                mSelectList.remove(i);
                break;
            }
        }
        photoAdapter.notifyDataSetChanged();
        setData();
        defaultView();
    }

    private IBaseOnItemClickListener iBaseOnItemClickListener = new IBaseOnItemClickListener() {
        @Override
        public void onItemClick(View view, int position, int type, Object o) {
            PhotoInfoEntity photoInfoEntity = (PhotoInfoEntity) o;
            switch (type) {
                case -1:
                    mSelectList.get(position).setChecked(!photoInfoEntity.isChecked());
                    photoAdapter.notifyItemChanged(position);
                    if (isControl) {
                        int checkedNum = 0;
                        for (int i = 0; i < mSelectList.size(); i++) {
                            if (mSelectList.get(i).isChecked()) {
                                checkedNum++;
                            }
                        }
                        iBaseDataChangeListener.dataChange(1, checkedNum);
                    }
                    break;
                case 1:
                    Intent photoDetail = new Intent(mContext, PhotoDetailActivity.class);
                    photoDetail.putExtra("photoUrl", photoInfoEntity.getImgUrl());
                    mContext.startActivityForResult(photoDetail, 3);
                    break;
                case 2:
                    break;
            }
        }
    };

    public void confirmDelete() {
        for (int i = mSelectList.size() - 1; i >= 0; i--) {
            if (mSelectList.get(i).isChecked()) {
                mSelectList.remove(i);
            }
        }
        setData();
        defaultView();
    }

    public void setControl(boolean control) {
        isControl = control;
        if (!control) {
            iv_add.setVisibility(View.VISIBLE);
            for (int i = 0; i < mSelectList.size(); i++) {
                mSelectList.get(i).setChecked(false);
            }
        } else {
            iv_add.setVisibility(View.GONE);
        }
        photoAdapter.setControl(isControl);
        photoAdapter.notifyDataSetChanged();
    }

    public boolean isControl() {
        return isControl;
    }

    public void setiBaseDataChangeListener(IBaseDataChangeListener iBaseDataChangeListener) {
        this.iBaseDataChangeListener = iBaseDataChangeListener;
    }

}
