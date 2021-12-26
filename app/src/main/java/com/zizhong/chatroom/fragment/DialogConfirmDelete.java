package com.zizhong.chatroom.fragment;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.zizhong.chatroom.R;
import com.zizhong.chatroom.costomview.IBaseContentView;

/**
 * FlymeCat保佑，永无BUG
 * Created by DJL on 2021/12/24.
 */

public class DialogConfirmDelete extends IBaseContentView {

    private TextView txt_cancel, txt_yes;

    public DialogConfirmDelete(Context context) {
        super(context);
    }

    @Override
    public int getLayoutId() {
        return R.layout.view_delete_confirm;
    }

    @Override
    protected void initView() {
        txt_cancel = findViewByID(R.id.txt_cancel);
        txt_yes = findViewByID(R.id.txt_yes);
    }

    @Override
    protected void initData() {
        txt_cancel.setOnClickListener(this);
        txt_yes.setOnClickListener(this);
    }

    @Override
    protected void initListener() {

    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.txt_cancel:
                break;
            case R.id.txt_yes:
                break;
        }
    }
}
