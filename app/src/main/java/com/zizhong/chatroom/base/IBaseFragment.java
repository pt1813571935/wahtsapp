package com.zizhong.chatroom.base;


import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.IdRes;

import com.trello.rxlifecycle.components.support.RxFragment;

/**
 * FlymeCat保佑，永无BUG
 * Created by DJL on 2019/8/28.
 */

public abstract class IBaseFragment extends RxFragment implements View.OnClickListener {

    /**
     * Activity引用
     */
    protected IBaseActivity mContext;

    /***
     * 根VIEW
     */
    protected View rootView;


    public View getRootView() {
        return rootView;
    }

    private long time;

    @Override
    public final View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mContext = (IBaseActivity) getActivity();
        int layoutID = getLayoutId();
        if (layoutID != 0) {
            rootView = inflater.inflate(layoutID, container, false);
        } else {
            rootView = getLayoutView(mContext);
        }
        initView();
        return rootView;
    }


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

    /**
     * 设置contentview 使用new出来的对象
     *
     * @return
     */
    protected View getLayoutView(Context mContext) {
        return null;
    }

    /***
     * 设置contentView ID
     *
     * @return
     */

    public abstract int getLayoutId();

    /***
     * 初始化View让子类去实现
     */
    protected abstract void initView();

    public final <T extends View> T findViewByID(@IdRes int id) {
        return (T) getRootView().findViewById(id);
    }

}
