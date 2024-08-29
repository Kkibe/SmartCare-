package com.nutricon.smartcare.trackers;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import com.nutricon.smartcare.R;
import com.nutricon.smartcare.ads.BannerManager;
import com.nutricon.smartcare.ads.InterstitialManager;

public class BmiActivity extends AppCompatActivity {
    EditText edKg, edFeet, edIns;
    TextView cardBtn;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bmi);
        edFeet = findViewById(R.id.edFeet);
        edKg = findViewById(R.id.edKg);
        cardBtn = findViewById(R.id.cardBtn);
        textView = findViewById(R.id.textView);
        edIns = findViewById(R.id.edIns);


        InterstitialManager interstitialManager = new InterstitialManager();
        interstitialManager.loadInterstitial(BmiActivity.this);
        cardBtn.setOnClickListener(view -> {
            handleCalculate();
            interstitialManager.showInterstitial(BmiActivity.this);
        });

        FrameLayout adViewContainer = findViewById(R.id.adViewContainer);
        BannerManager bannerManager = new BannerManager(this, BmiActivity.this, adViewContainer);
        bannerManager.loadBanner();
    }

    private void handleCalculate() {
        String kg = edKg.getText().toString();
        String feet = edFeet.getText().toString();
        String inc = edIns.getText().toString();

        if (kg.length() > 0 && feet.length() > 0 && inc.length() > 0) {
            float weight = Float.parseFloat(kg);
            float efeet = Float.parseFloat(feet);
            float eInc = Float.parseFloat(inc);
            float height = (float) (efeet * 0.3048 + eInc * 0.0254);
            float bmiIndex = weight / (height * height);

            if (bmiIndex > 24) {
                textView.setText("Overweight : " + bmiIndex);
            } else if (bmiIndex > 18) {
                textView.setText("Normal weight : " + bmiIndex);
            } else {
                textView.setText("Underweight : " + bmiIndex);
            }
        } else {
            textView.setText("Please Input All Box");
        }
    }

}