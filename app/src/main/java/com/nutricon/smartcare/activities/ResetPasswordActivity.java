package com.nutricon.smartcare.activities;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.nutricon.smartcare.R;
import com.nutricon.smartcare.ads.BannerManager;
import com.nutricon.smartcare.resources.AuthManager;

public class ResetPasswordActivity extends AppCompatActivity {
    private AuthManager authManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        FrameLayout adViewContainer = findViewById(R.id.adViewContainer);
        BannerManager bannerManager = new BannerManager(ResetPasswordActivity.this, ResetPasswordActivity.this, adViewContainer);
        bannerManager.loadBanner();

        authManager = new AuthManager(this, ResetPasswordActivity.this);
        EditText textEmail = findViewById(R.id.textEmail);
        TextView btnReset = findViewById(R.id.btnReset);

        btnReset.setOnClickListener(v -> {
            authManager.resetPassword(textEmail.getText().toString(), textEmail);
        });
    }
}