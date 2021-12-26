package com.zizhong.chatroom.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.zizhong.chatroom.base.IBaseFragment;

import java.util.ArrayList;
import java.util.List;

public class MyViewPageAdapter extends FragmentStateAdapter {
    List<IBaseFragment> mDatas = new ArrayList<>();

    public MyViewPageAdapter(@NonNull FragmentActivity fragmentActivity, List<IBaseFragment> mDatas) {
        super(fragmentActivity);
        this.mDatas = mDatas;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return mDatas.get(position);
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }
}