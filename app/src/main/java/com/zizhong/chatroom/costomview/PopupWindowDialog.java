package com.zizhong.chatroom.costomview;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;

import androidx.appcompat.widget.LinearLayoutCompat;

import com.zizhong.chatroom.R;


public class PopupWindowDialog {

    private PopupWindow screenPop;
    private Context mContext;
    private PopupWindowDialogListener dialogListener;
    private float alphaNum = 0.6f;

    /**
     * 筛选窗口
     * view=布局UI
     * linear是相对UI控件
     *
     * @param view
     */
    public PopupWindowDialog(View view, Context context, PopupWindowDialogListener listener) {
        this.mContext = context;
        this.dialogListener = listener;
        WindowManager.LayoutParams params = ((Activity) context).getWindow().getAttributes();
        params.alpha = alphaNum;
        ((Activity) context).getWindow().setAttributes(params);
        screenPop = new PopupWindow(context);
        screenPop.setAnimationStyle(R.style.custom_popu_anim);
//        screenPop.setWidth(AppUtils.getDisplayWidth(AppContext.getInstance().getApplicationContext()));
        screenPop.setWidth(LinearLayoutCompat.LayoutParams.WRAP_CONTENT);
        screenPop.setHeight(LinearLayoutCompat.LayoutParams.WRAP_CONTENT);
        screenPop.setTouchable(true);
        screenPop.setFocusable(true);
        screenPop.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        screenPop.setOutsideTouchable(true);
        screenPop.setContentView(view);
//        screenPop.showAtLocation(view, Gravity.BOTTOM, 0, 0);
        screenPop.setOnDismissListener(new PopupWindow.OnDismissListener() {

            @Override
            public void onDismiss() {
                WindowManager.LayoutParams params = ((Activity) mContext).getWindow().getAttributes();
                params.alpha = 1f;
                ((Activity) mContext).getWindow().setAttributes(params);
                if (dialogListener != null) {
                    dialogListener.onIsDis(true);
                }
            }
        });
    }

    public void showDialog(View view) {
        screenPop.showAtLocation(view, Gravity.BOTTOM, 0, 0);
        WindowManager.LayoutParams params = ((Activity) mContext).getWindow().getAttributes();
        params.alpha = alphaNum;
        ((Activity) mContext).getWindow().setAttributes(params);
    }

    public void showCenterDialog(View view) {
        screenPop.showAtLocation(view, Gravity.CENTER, 0, 0);
        WindowManager.LayoutParams params = ((Activity) mContext).getWindow().getAttributes();
        params.alpha = alphaNum;
        ((Activity) mContext).getWindow().setAttributes(params);
    }

    public boolean isShowing() {
        if (screenPop != null) {
            return screenPop.isShowing();
        }
        return false;
    }

    public void setAlphaNum(float alphaNum) {
        this.alphaNum = alphaNum;
    }

    public void setTouchable(boolean touchable) {
        screenPop.setFocusable(touchable);
        screenPop.setOutsideTouchable(touchable);
    }

    public void setDialogListener(PopupWindowDialogListener dialogListener) {
        this.dialogListener = dialogListener;
    }

    public void dismiss() {
        screenPop.dismiss();
    }

    public static interface PopupWindowDialogListener {
        public void onIsDis(boolean isDis);
    }
}
