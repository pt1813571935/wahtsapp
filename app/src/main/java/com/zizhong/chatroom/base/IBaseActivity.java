package com.zizhong.chatroom.base;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.IdRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.trello.rxlifecycle.components.support.RxFragmentActivity;
import com.umeng.analytics.MobclickAgent;
import com.zizhong.chatroom.Utils.SharedPreferencesUtil;
import com.zizhong.chatroom.googleadmob.Table;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

/**
 * FlymeCat保佑，永无BUG
 * Created by DJL on 2019/8/28.
 */
public abstract class IBaseActivity extends RxFragmentActivity implements View.OnClickListener {

    private long time;
    private boolean times= false;
    private Table table;

    public static boolean isActive; //全局变量

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

        String ok = SharedPreferencesUtil.getSharedPreferences(this).getString("OK", "");
        table = new Table();
        if (ok.equals("123")) {

            times =true;
        }
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


    int screen=0;
    int stopss=0;
    boolean qiantai=false;
    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
        if (!isActive) {
            //app 从后台唤醒，进入前台
            isActive = true;
            Log.e("ACTIVITY", "程序从后台唤醒");
            screen = SharedPreferencesUtil.getSharedPreferences(this).getInt("screen", screen);
            screen++;
            SharedPreferencesUtil.getSharedPreferences(this).putInt("screen",screen);

            String ok = SharedPreferencesUtil.getSharedPreferences(this).getString("OK", "");
            if (screen%2==0&&qiantai==true&&ok.equals("123")){
                table.gooles_show(this);
                SharedPreferencesUtil.getSharedPreferences(this).remove("screen");
                qiantai=false;
            }
            qiantai=true;

        }

    }

    @Override
    protected void onPause() {
        super.onPause();

        MobclickAgent.onPause(this);
        if (!isActive) {
            //app 从后台唤醒，进入前台
            isActive = true;
            Log.e("ACTIVITY", "程序从后台唤醒");
            table.gooles_show(this);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (table.mInterstitialAd != null){
            table.mInterstitialAd = null;
        }
        EventBus.getDefault().unregister(this);
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

    @Override
    protected void onStop() {
        if (!isAppOnForeground()) {
            //app 进入后台
            isActive = false;//记录当前已经进入后台
            Log.i("ACTIVITY", "程序进入后台");
            stopss = SharedPreferencesUtil.getSharedPreferences(this).getInt("stopss", stopss);
            stopss++;
            String ok = SharedPreferencesUtil.getSharedPreferences(this).getString("OK", "");
            if (ok.equals("123")&& times==true) {
                if (stopss%2==0){
                    table.gooles_add(this);

                    SharedPreferencesUtil.getSharedPreferences(this).remove("stopss");
                }


            }
        }
        super.onStop();
    }

    /**
     * APP是否处于前台唤醒状态
     *
     * @return
     */
    public boolean isAppOnForeground() {
        ActivityManager activityManager = (ActivityManager) getApplicationContext().getSystemService(Context.ACTIVITY_SERVICE);
        String packageName = getApplicationContext().getPackageName();
        List<ActivityManager.RunningAppProcessInfo> appProcesses = activityManager
                .getRunningAppProcesses();
        if (appProcesses == null)
            return false;

        for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
            // The name of the process that this object is associated with.
            if (appProcess.processName.equals(packageName)
                    && appProcess.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                return true;
            }
        }

        return false;
    }
}
