package com.zizhong.chatroom.base;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.IdRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.trello.rxlifecycle.components.support.RxFragmentActivity;

/**
 * FlymeCat保佑，永无BUG
 * Created by DJL on 2019/8/28.
 */
public abstract class IBaseActivity extends RxFragmentActivity implements View.OnClickListener {

    private long time;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        if (savedInstanceState != null) {
//            IBaseAppUtils.restartActivitySelf(this);
            return;
        }
        super.onCreate(savedInstanceState);
        int layoutID = getLayoutId();
        if (layoutID != 0) {
            setContentView(layoutID);
        }
        ViewGroup contentFrameLayout = (ViewGroup) findViewById(Window.ID_ANDROID_CONTENT);
        View parentView = contentFrameLayout.getChildAt(0);
        parentView.setFitsSystemWindows(true);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        //透明状态栏 _顶部
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        //Activity全屏显示，但是状态栏不会被覆盖掉，而是正常显示，只是Activity顶端布局会被覆盖住
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
//        }
        getIntentWord();
        initView();
        initData();
        initListener();
        initOther(savedInstanceState);
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

    private int fragmnetContentId = 0;


    /**
     * 布局中Fragment的ID
     *
     * @param contentId
     */
    public void setFragmentContentId(int contentId) {
        this.fragmnetContentId = contentId;

    }

    /**
     * 添加fragment
     *
     * @param fragment
     */
    protected void addFragment(IBaseFragment fragment) {
        if (fragment != null && fragmnetContentId != 0) {
            getSupportFragmentManager().beginTransaction()
                    .replace(fragmnetContentId, fragment, fragment.getClass().getSimpleName())
                    .addToBackStack(fragment.getClass().getSimpleName())
                    .commitAllowingStateLoss();
        }
    }

    protected void addOneFragment(int contentId, IBaseFragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(contentId, fragment).commitAllowingStateLoss();
    }


    /**
     * 用于切换不需要进行数据更新的Fragment
     */
    private Fragment cutFragment = new Fragment();

    protected void cutFragment(IBaseFragment fragment) {
        if (fragment.getClass().getSimpleName().equals(cutFragment.getClass().getSimpleName())) {
            return;
        }
        FragmentTransaction mFragmentTransaction = getSupportFragmentManager().beginTransaction();
        if (!fragment.isAdded()) {
            mFragmentTransaction.hide(cutFragment);
            mFragmentTransaction.add(fragmnetContentId, fragment, fragment.getClass().getSimpleName()).commitAllowingStateLoss();
        } else {
            mFragmentTransaction.show(fragment).hide(cutFragment).commitAllowingStateLoss();
        }
        cutFragment = fragment;
    }

    /**
     * 移除fragment
     */
    protected void removeFragment() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
            getSupportFragmentManager().popBackStack();
        } else {
            finish();
        }
    }

    public abstract int getLayoutId();

    protected abstract void initView();

    protected abstract void initData();

    protected abstract void initListener();

    protected abstract void initOther(Bundle savedInstanceState);

    protected void getIntentWord() {

    }

    public final <T extends View> T findViewByID(@IdRes int id) {
        return (T) findViewById(id);
    }


    public View getRootView() {
        return ((ViewGroup) findViewById(android.R.id.content)).getChildAt(0);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
    }

    protected boolean isActivityInanimation() {
        return this == null || isFinishing()
                || isActivityClose();
    }

    private Boolean isActivityClose() {
        if (Build.VERSION.SDK_INT < 17)
            return false;
        if (this != null) {
            return isDestroyed();
        }
        return false;
    }
}
