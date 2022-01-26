package com.zizhong.chatroom.googleadmob;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.zizhong.chatroom.api.Variable;
import com.zizhong.chatroom.Utils.EventBusssss;

import org.greenrobot.eventbus.EventBus;

public class Tables {
    public InterstitialAd mInterstitialAd;
    private String TAG="保存插屏广告加载";
    public void gooles_add(Context context){
        AdRequest adRequest = new AdRequest.Builder().build();
        InterstitialAd.load(context, Variable.Goole_table, adRequest,
                new InterstitialAdLoadCallback() {
                    @Override
                    public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                        // The mInterstitialAd reference will be null until
                        // an ad is loaded.
                        mInterstitialAd = interstitialAd;
                        Log.i(TAG, "onAdLoaded");
                    }

                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        // Handle the error
                        Log.i(TAG, loadAdError.getMessage());
                        mInterstitialAd = null;
                    }
                });
    }


    public void gooles_show(Context context){
        if (mInterstitialAd!=null){
            mInterstitialAd.show((Activity) context);
            mInterstitialAd.setFullScreenContentCallback(new FullScreenContentCallback(){
                @Override
                public void onAdDismissedFullScreenContent() {
                    EventBus.getDefault().post(new EventBusssss.getClose("通知关闭"));
                    // Called when fullscreen content is dismissed.
                    Log.d(TAG, "广告被驳回");
                }

                @Override
                public void onAdFailedToShowFullScreenContent(AdError adError) {
                    // Called when fullscreen content failed to show.
                    Log.d(TAG, "广告显示失败"+adError);
                }

                @Override
                public void onAdShowedFullScreenContent() {
                    // Called when fullscreen content is shown.
                    // Make sure to set your reference to null so you don't
                    // show it a second time.
                    mInterstitialAd = null;
                    Log.d(TAG, "这则广告正在播出");
                }
            });
        }else {
            Log.d(TAG, "The interstitial ad wasn't ready yet.");
        }

    }
}
