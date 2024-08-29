package com.nutricon.smartcare.ads;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.Display;
import android.widget.FrameLayout;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.nutricon.smartcare.resources.Constants;

public class BannerManager {
    Context context;
    Activity activity;
    FrameLayout adViewContainer;

    Constants constants = new Constants();

    public BannerManager(Context context, Activity activity, FrameLayout frameLayout) {
        this.context = context;
        this.activity = activity;
        this.adViewContainer = frameLayout;
    }


    public void loadBanner() {
        AdView adView = new AdView(context);
        adView.setAdUnitId(constants.getBannerAdId());
        adViewContainer.removeAllViews();
        adViewContainer.addView(adView);

        AdSize adSize = getAdSize();
        adView.setAdSize(adSize);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);
    }

    private AdSize getAdSize() {
        Display display = activity.getWindowManager().getDefaultDisplay();
        DisplayMetrics outMetrics = new DisplayMetrics();
        display.getMetrics(outMetrics);

        float widthPixels = outMetrics.widthPixels;
        float density = outMetrics.density;
        int adWidth = (int) (widthPixels / density);

        return AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize(context, adWidth);
    }
}
