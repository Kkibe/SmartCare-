package com.nutricon.smartcare.resources;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.nutricon.smartcare.R;
import com.nutricon.smartcare.activities.FeedbackActivity;
import com.nutricon.smartcare.activities.ReminderActivity;
import com.nutricon.smartcare.ads.InterstitialManager;
import com.nutricon.smartcare.trackers.BmiActivity;
import com.nutricon.smartcare.trackers.BmrActivity;
import com.nutricon.smartcare.trackers.GlucoseActivity;

public class BottomSheetDialogManager {
    InterstitialManager interstitialManager = new InterstitialManager();
    public void openDialog (Context context, Activity activity) {
        interstitialManager.loadInterstitial(activity);
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(context, R.style.BottomSheetDialogTheme);
        View bottomSheetView = LayoutInflater.from(context).inflate(R.layout.bottom_sheet_dialog, activity.findViewById(R.id.bottomSheet));
        bottomSheetDialog.setContentView(bottomSheetView);
        bottomSheetDialog.show();

        bottomSheetView.findViewById(R.id.btnGlucose).setOnClickListener(v -> {
            Intent intent = new Intent(context, GlucoseActivity.class);
            activity.startActivity(intent);
            bottomSheetDialog.hide();
            interstitialManager.showInterstitial(activity);
        });
        bottomSheetView.findViewById(R.id.btnBmi).setOnClickListener(v -> {
            Intent intent = new Intent(context, BmiActivity.class);
            activity.startActivity(intent);
           bottomSheetDialog.hide();
           interstitialManager.showInterstitial(activity);
        });
        bottomSheetView.findViewById(R.id.btnBmr).setOnClickListener(v -> {
            Intent intent = new Intent(context, BmrActivity.class);
            activity.startActivity(intent);
            bottomSheetDialog.hide();
            interstitialManager.showInterstitial(activity);
        });
        bottomSheetView.findViewById(R.id.btnReminder).setOnClickListener(v -> {
            Intent intent = new Intent(context, ReminderActivity.class);
            activity.startActivity(intent);
            bottomSheetDialog.hide();
            interstitialManager.showInterstitial(activity);
        });
        bottomSheetView.findViewById(R.id.btnFeedback).setOnClickListener(v -> {
            Intent intent = new Intent(context, FeedbackActivity.class);
            activity.startActivity(intent);
            bottomSheetDialog.hide();
        });
    }
}
