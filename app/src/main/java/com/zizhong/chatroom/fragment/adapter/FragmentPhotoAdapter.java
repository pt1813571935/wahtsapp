package com.zizhong.chatroom.fragment.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.zizhong.chatroom.R;
import com.zizhong.chatroom.entity.PhotoInfoEntity;
import com.zizhong.chatroom.listener.IBaseOnItemClickListener;

import java.util.ArrayList;

/**
 * FlymeCat保佑，永无BUG
 * Created by DJL on 2021/12/22.
 */

public class FragmentPhotoAdapter extends RecyclerView.Adapter {

    private Context mContext;
    private LayoutInflater layoutInflater;
    private ArrayList<PhotoInfoEntity> imgList = new ArrayList<>();
    private IBaseOnItemClickListener itemClickListener;
    private boolean isControl=false;
    private int inputType=1;

    public FragmentPhotoAdapter(Context context) {
        this.mContext = context;
        layoutInflater = LayoutInflater.from(mContext);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.item_fragment_photo, parent, false);
        return new FragmentPhotoAdapterHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        FragmentPhotoAdapterHolder photoAdapterHolder = (FragmentPhotoAdapterHolder) holder;
        photoAdapterHolder.setInfo(imgList.get(position),isControl,inputType);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isControl){
                    itemClickListener.onItemClick(view, position, -1, imgList.get(position));
                }else{
                    itemClickListener.onItemClick(view, position, 1, imgList.get(position));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return imgList.size();
    }

    public void setImgList(ArrayList<PhotoInfoEntity> imgList) {
        this.imgList = imgList;
    }

    public void setItemClickListener(IBaseOnItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public void setControl(boolean control) {
        isControl = control;
    }

    public void setInputType(int inputType) {
        this.inputType = inputType;
    }
}
