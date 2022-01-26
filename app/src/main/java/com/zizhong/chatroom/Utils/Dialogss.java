package com.zizhong.chatroom.Utils;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.zizhong.chatroom.R;
import com.zizhong.chatroom.activitys.AgreementActivity;
import com.zizhong.chatroom.activitys.PolicyActivity;

import org.greenrobot.eventbus.EventBus;

public class Dialogss {
    private TextView agreement;
    private TextView cancel;
    private TextView consent;
    private TextView policy;
    private Dialog yinse_dialog;


    public void onDialog(Context context, View inflate) {

        yinse_dialog = new Dialog(context, R.style.dialog_bottom_full);
        yinse_dialog.setCanceledOnTouchOutside(false); //手指触碰到外界取消
        yinse_dialog.setCancelable(false);             //可取消 为true(屏幕返回键监听)
        Window window = yinse_dialog.getWindow();      // 得到dialog的窗体
        window.setGravity(Gravity.CENTER);
        window.setWindowAnimations(R.style.share_animation);
        window.getDecorView().setPadding(150, 0, 150, 0);

        View view = View.inflate(context, R.layout.policy_dialog, null); //获取布局视图
        agreement = view.findViewById(R.id.agreement);
        cancel = view.findViewById(R.id.cancel);
        consent = view.findViewById(R.id.consent);
        policy = view.findViewById(R.id.policy);
        window.setContentView(view);
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);//设置横向全屏

        DialogListener(context,inflate);

        yinse_dialog.show();
    }

    private void DialogListener(Context context, View inflate) {
        agreement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //创建意图，跳转
                Intent intent = new Intent(context, AgreementActivity.class);
                context.startActivity(intent);
            }
        });
        policy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, PolicyActivity.class);
                context.startActivity(intent);
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                yinse_dialog.dismiss();

            }
        });
        consent.setOnClickListener(new View.OnClickListener() {

            private String ok = "123";

            @Override
            public void onClick(View v) {
                yinse_dialog.dismiss();
                  //Utils.getPermission(context);
                SharedPreferencesUtil.getSharedPreferences(context).putString("OK", ok);
                EventBus.getDefault().post(new EventBusssss.getTure());
            }
        });
    }
}
