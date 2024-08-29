package com.nutricon.smartcare.activities;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.nutricon.smartcare.R;
import com.nutricon.smartcare.ads.BannerManager;
import com.nutricon.smartcare.resources.RateManager;
import com.nutricon.smartcare.resources.ShareManager;
import com.nutricon.smartcare.resources.UrlManager;

public class AboutUsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);

        TextView textVersionName = findViewById(R.id.textVersionName);

        ImageButton twitter = findViewById(R.id.btnTwitter);
        ImageButton telegram = findViewById(R.id.btnTelegram);
        ImageButton whatsapp = findViewById(R.id.btnWhatsapp);

        MaterialButton moreBtn = findViewById(R.id.btnMore);
        MaterialButton shareBtn = findViewById(R.id.btnShare);
        MaterialButton rateBtn = findViewById(R.id.btnRate);
        MaterialButton termsBtn = findViewById(R.id.btnTerms);
        MaterialButton contactBtn = findViewById(R.id.btnContact);

        UrlManager urlManager = new UrlManager(this);


        twitter.setOnClickListener(v -> urlManager.openTwitter());
        telegram.setOnClickListener(v -> urlManager.openTelegram());
        whatsapp.setOnClickListener(v -> urlManager.openWhatsapp());
        moreBtn.setOnClickListener(v -> urlManager.moreApps());
        shareBtn.setOnClickListener(v -> {
            ShareManager shareManager = new ShareManager(this);
            shareManager.shareApp();
        });
        rateBtn.setOnClickListener(v -> {
            RateManager rateManager = new RateManager(this);
            rateManager.rate();
        });
        termsBtn.setOnClickListener(v -> urlManager.openTerms());
        contactBtn.setOnClickListener(v -> startActivity(new Intent(AboutUsActivity.this, FeedbackActivity.class)));

        FrameLayout adViewContainer = findViewById(R.id.adViewContainer);
        BannerManager bannerManager = new BannerManager(this, AboutUsActivity.this, adViewContainer);
        bannerManager.loadBanner();

        // Get the package manager instance
        PackageManager packageManager = getPackageManager();

        try {
            // Get the package information
            PackageInfo packageInfo = packageManager.getPackageInfo(getPackageName(), 0);
            // Retrieve the version information
            String versionName = packageInfo.versionName;
            textVersionName.setText("v" + versionName);

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

    }
}