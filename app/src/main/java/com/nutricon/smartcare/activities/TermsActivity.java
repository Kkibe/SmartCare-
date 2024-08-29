package com.nutricon.smartcare.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.os.Handler;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;

import com.nutricon.smartcare.R;
import com.nutricon.smartcare.ads.BannerManager;

public class TermsActivity extends AppCompatActivity {
    WebView browser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms);

        FrameLayout adViewContainer = findViewById(R.id.adViewContainer);
        BannerManager bannerManager = new BannerManager(TermsActivity.this, TermsActivity.this, adViewContainer);
        bannerManager.loadBanner();

        browser = findViewById(R.id.webview);

        browser.setWebViewClient(new Browser());
        loadUrl("https://nutricondiets.blogspot.com/2024/05/smartcare-health-care-application.html");
    }

    public void loadUrl (String url) {
        browser.loadUrl(url);
    }

    private class Browser extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }
}