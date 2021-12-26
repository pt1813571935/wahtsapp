package com.zizhong.chatroom.fragment.adapter;

import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.giftedcat.picture.lib.photoview.GlideImageLoader;
import com.zizhong.chatroom.R;
import com.zizhong.chatroom.entity.PhotoInfoEntity;

/**
 * FlymeCat保佑，永无BUG
 * Created by DJL on 2021/12/22.
 */

public class FragmentPhotoAdapterHolder extends RecyclerView.ViewHolder {

    private ImageView imageView, img_checked,img_video_play;

    public FragmentPhotoAdapterHolder(@NonNull View itemView) {
        super(itemView);
        imageView = itemView.findViewById(R.id.img_photo_ico);
        img_checked = itemView.findViewById(R.id.img_checked);
        img_video_play=itemView.findViewById(R.id.img_video_play);
    }

    public void setInfo(PhotoInfoEntity entity,boolean isControl,int inputType) {
        Glide.with(itemView).load(entity.getImgUrl()).into(imageView);
        if (isControl){
            img_checked.setVisibility(View.VISIBLE);
            img_checked.setSelected(entity.isChecked());
        }else{
            img_checked.setVisibility(View.GONE);
        }
        if (inputType==1){
            img_video_play.setVisibility(View.GONE);
        }else{
            img_video_play.setVisibility(View.VISIBLE);
        }
    }
}
