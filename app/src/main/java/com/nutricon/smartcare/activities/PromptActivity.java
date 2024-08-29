package com.nutricon.smartcare.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.nutricon.smartcare.MainActivity;
import com.nutricon.smartcare.R;
import com.nutricon.smartcare.ads.BannerManager;
import com.nutricon.smartcare.ads.InterstitialManager;
import com.nutricon.smartcare.resources.AuthManager;

public class PromptActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prompt);

        FrameLayout adViewContainer = findViewById(R.id.adViewContainer);
        BannerManager bannerManager = new BannerManager(PromptActivity.this, PromptActivity.this, adViewContainer);
        bannerManager.loadBanner();

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

        if(firebaseAuth.getCurrentUser() != null) {
            Intent intent = new Intent(PromptActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
        Button btnLogin = findViewById(R.id.btnLogin);
        Button btnRegister = findViewById(R.id.btnRegister);
        Button btnContinue = findViewById(R.id.btnContinue);

        InterstitialManager interstitialManager = new InterstitialManager();
        interstitialManager.loadInterstitial(this);

        btnLogin.setOnClickListener(v -> startActivity(new Intent(PromptActivity.this, LoginActivity.class)));
        btnRegister.setOnClickListener(v -> startActivity(new Intent(PromptActivity.this, RegisterActivity.class)));
        btnContinue.setOnClickListener(v -> {
            AuthManager authManager = new AuthManager(PromptActivity.this, PromptActivity.this);
            authManager.signInAnonymously();
            interstitialManager.showInterstitial(this);
        });
    }
}