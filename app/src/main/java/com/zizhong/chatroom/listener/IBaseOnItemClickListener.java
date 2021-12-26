package com.zizhong.chatroom.listener;

import android.view.View;

public interface  IBaseOnItemClickListener<T> {

    public void onItemClick(View view, int position, int type, T t);

}
