package com.nutricon.smartcare.ads;

import android.app.Activity;
import android.os.Handler;

import androidx.annotation.NonNull;

import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.nutricon.smartcare.resources.Constants;

public class InterstitialManager {
    Constants constants = new Constants();
    private InterstitialAd interstitialAd = null;
    public void   loadInterstitial(Activity activity){
        AdRequest adRequest = new AdRequest.Builder().build();

        InterstitialAd.load(activity, constants.getInterstitialAdId(), adRequest, new InterstitialAdLoadCallback() {
            @Override
            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                super.onAdFailedToLoad(loadAdError);
                interstitialAd = null;
                new Handler().postDelayed(() ->{
                        loadInterstitial(activity);
                }, 3000);
            }

            @Override
            public void onAdLoaded(@NonNull InterstitialAd mInterstitialAd) {
                super.onAdLoaded(interstitialAd);
                interstitialAd = mInterstitialAd;
            }
        });
    }

    public void showInterstitial(Activity activity){
        if(interstitialAd !=null) {
            interstitialAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                @Override
                public void onAdDismissedFullScreenContent() {
                    super.onAdDismissedFullScreenContent();
                    loadInterstitial(activity);
                }

                @Override
                public void onAdFailedToShowFullScreenContent(@NonNull AdError adError) {
                    super.onAdFailedToShowFullScreenContent(adError);
                    loadInterstitial(activity);
                }
            });
            interstitialAd.show(activity);
        }else{
            loadInterstitial(activity);
        }
    }
}
