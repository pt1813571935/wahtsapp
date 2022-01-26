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

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.listener.OnResultCallbackListener;
import com.zizhong.chatroom.Utils.SharedPreferencesUtil;
import com.zizhong.chatroom.activitys.PhotoDetailActivity;
import com.zizhong.chatroom.R;
import com.zizhong.chatroom.Utils.GlideEngine;
import com.zizhong.chatroom.Utils.JsonDataUtils;
import com.zizhong.chatroom.Utils.log.LogUtils;
import com.zizhong.chatroom.base.IBaseFragment;
import com.zizhong.chatroom.entity.PhotoInfoEntity;
import com.zizhong.chatroom.fragment.adapter.FragmentPhotoAdapter;
import com.zizhong.chatroom.googleadmob.Table;
import com.zizhong.chatroom.listener.IBaseDataChangeListener;
import com.zizhong.chatroom.listener.IBaseOnItemClickListener;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class PhotoFragment extends IBaseFragment {

    private ImageView iv_add;
    private ArrayList<PhotoInfoEntity> mSelectList = new ArrayList<>();
    private RecyclerView recyclerView;
    private FragmentPhotoAdapter photoAdapter;
    private LinearLayout add_photo_ll1;
    private boolean isControl = false;
    private IBaseDataChangeListener iBaseDataChangeListener;
    //1图片；2视频
    private int inputType = 1;
    private Table table;

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
        photoAdapter.setInputType(inputType);
        photoAdapter.setImgList(mSelectList);
        photoAdapter.setItemClickListener(iBaseOnItemClickListener);
        recyclerView.setLayoutManager(new GridLayoutManager(mContext, 3));
        recyclerView.setAdapter(photoAdapter);
        defaultView();
    }

    private void getData() {
        String dbName, tbName;
        if (inputType == 1) {
            dbName = "photos";
            tbName = "phtotList";
        } else {
            dbName = "videos";
            tbName = "videoList";
        }
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(dbName, 0);
        String content = sharedPreferences.getString(tbName, "").trim();
        LogUtils.e("图片信息", content + "===");

        Gson gson = new Gson();
        Type type = new TypeToken<List<PhotoInfoEntity>>() {
        }.getType();
        List<PhotoInfoEntity> list = gson.fromJson(content, type);
        if (list != null && list.size() > 0) {
            mSelectList.addAll(list);
        }

        String  ok = SharedPreferencesUtil.getSharedPreferences(getContext()).getString("OK", "");
        table = new Table();
        if (ok.equals("123")){
            table.gooles_add(getContext());

        }
    }

    @SuppressLint("ApplySharedPref")
    private void setData() {
        String dbName, tbName;
        if (inputType == 1) {
            dbName = "photos";
            tbName = "phtotList";
        } else {
            dbName = "videos";
            tbName = "videoList";
        }
        String json = JsonDataUtils.getToJson(mSelectList);
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(dbName, 0);
        sharedPreferences.edit().putString(tbName, json).commit();
    }


    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.iv_add:
//                MultiImageSelector selector = MultiImageSelector.create(getContext());
//                selector.showCamera(true);
//                selector.count(9);
//                selector.multi();
////                selector.origin(mSelectList);
//                selector.start(getActivity(), 2);
                getImageCallback();
                PictureSelector.create(this)
                        .openGallery(inputType == 1 ? PictureMimeType.ofImage() : PictureMimeType.ofVideo())
                        .imageEngine(GlideEngine.createGlideEngine())
                        .forResult(PictureConfig.CHOOSE_REQUEST);
                table.gooles_show(getContext());
                break;
        }
    }

    private void getImageCallback() {
        PictureSelector.create(this)
                .openGallery(inputType == 1 ? PictureMimeType.ofImage() : PictureMimeType.ofVideo())
                .imageEngine(GlideEngine.createGlideEngine())
                .forResult(new OnResultCallbackListener<LocalMedia>() {
                    @Override
                    public void onResult(List<LocalMedia> result) {
                        LogUtils.e("选中图片数量2", result.size() + "====");
                        //查询已经保存的数据内是否已经包含
                        for (int i = 0; i < result.size(); i++) {
                            LogUtils.e("选中的图片信息", result.get(i).getPath());
                            boolean isComprise = false;
                            for (int j = 0; j < mSelectList.size(); j++) {
                                if (result.get(i).getPath().equals(mSelectList.get(j).getImgUrl())) {
                                    isShowRepetitionHint = true;
                                    isComprise = true;
                                }
                            }
                            if (!isComprise && !"".equals(result.get(i).getPath())) {
                                mSelectList.add(new PhotoInfoEntity(result.get(i).getPath(), false));
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

    public void setPhotoInfos(Intent data) {
//        List<String> select = data.getStringArrayListExtra(MultiImageSelector.EXTRA_RESULT);
//        //查询已经保存的数据内是否已经包含
//        for (int i = 0; i < select.size(); i++) {
//            LogUtils.e("选中的图片信息", select.get(i));
//            boolean isComprise = false;
//            for (int j = 0; j < mSelectList.size(); j++) {
//                if (select.get(i).equals(mSelectList.get(j).getImgUrl())) {
//                    isShowRepetitionHint = true;
//                    isComprise = true;
//                }
//            }
//            if (!isComprise && !"".equals(select.get(i))) {
//                mSelectList.add(new PhotoInfoEntity(select.get(i), false));
//            }
//        }
//        if (isShowRepetitionHint) {
//            Toast.makeText(mContext, "已为您自动删除重复资源", Toast.LENGTH_LONG).show();
//        }
//        defaultView();
    }

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
                    photoDetail.putExtra("inputType",inputType);
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

    public void setInputType(int inputType) {
        this.inputType = inputType;
    }
}
