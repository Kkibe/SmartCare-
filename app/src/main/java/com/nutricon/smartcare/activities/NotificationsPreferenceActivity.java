package com.nutricon.smartcare.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.FrameLayout;

import com.nutricon.smartcare.R;
import com.nutricon.smartcare.ads.BannerManager;

public class NotificationsPreferenceActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications_preference);

        FrameLayout adViewContainer = findViewById(R.id.adViewContainer);
        BannerManager bannerManager = new BannerManager(NotificationsPreferenceActivity.this, NotificationsPreferenceActivity.this, adViewContainer);
        bannerManager.loadBanner();
    }
}