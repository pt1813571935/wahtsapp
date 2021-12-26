package com.zizhong.chatroom.costomview;

import android.content.Context;
import android.view.View;

import androidx.annotation.IdRes;


public abstract class IBaseContentView extends View implements View.OnClickListener {

    protected Context mContext;

    protected View mView;

    public IBaseContentView(Context context) {
        super(context);
        this.mContext = context;
        mView = inflate(context, getLayoutId(), null);
        initView();
        initData();
        initListener();
    }

    private long time;

    @Override
    public void onClick(View view) {
        try {
            long currentTime = System.currentTimeMillis();
            if (currentTime - time <= 500) {
                time = currentTime;
                return;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public final <T extends View> T findViewByID(@IdRes int id) {
        return (T) mView.findViewById(id);
    }

    public abstract int getLayoutId();

    protected abstract void initView();

    protected abstract void initData();

    protected abstract void initListener();

    public View getView() {
        return mView;
    }
}
