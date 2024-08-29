package com.nutricon.smartcare.activities;

import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.SwitchCompat;

import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.play.core.appupdate.AppUpdateInfo;
import com.google.android.play.core.appupdate.AppUpdateManager;
import com.google.android.play.core.appupdate.AppUpdateManagerFactory;
import com.google.android.play.core.install.InstallStateUpdatedListener;
import com.google.android.play.core.install.model.AppUpdateType;
import com.google.android.play.core.install.model.InstallStatus;
import com.google.android.play.core.install.model.UpdateAvailability;
import com.nutricon.smartcare.R;
import com.nutricon.smartcare.ads.InterstitialManager;
import com.nutricon.smartcare.resources.AuthManager;
import com.nutricon.smartcare.resources.RateManager;
import com.nutricon.smartcare.resources.ShareManager;

public class SettingsActivity extends AppCompatActivity {
    AuthManager authManager;
    SwitchCompat switchMode;
    boolean nightMode;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    AppUpdateManager appUpdateManager;
    private InstallStateUpdatedListener installStateUpdatedListener;
    private static final int MY_REQUEST_CODE = 1234; // Use any unique integer value
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        ImageView btnClose = findViewById(R.id.btnClose);
        TextView btnShare = findViewById(R.id.btnShare);
        TextView btnRate = findViewById(R.id.btnRate);
        switchMode = findViewById(R.id.switchMode);
        TextView btnReminder = findViewById(R.id.btnReminder);
        TextView btnNotifications = findViewById(R.id.btnNotifications);
        TextView btnTerms = findViewById(R.id.btnTerms);
        TextView btnAbout = findViewById(R.id.btnAbout);
        TextView btnFeedback = findViewById(R.id.btnFeedback);
        TextView btnDelete = findViewById(R.id.btnDelete);

        authManager = new AuthManager(this, SettingsActivity.this);
        appUpdateManager = AppUpdateManagerFactory.create(SettingsActivity.this);

        InterstitialManager interstitialManager = new InterstitialManager();
        interstitialManager.loadInterstitial(SettingsActivity.this);

        // Monitor the update state
        appUpdateManager.registerListener(state -> {
            if (state.installStatus() == InstallStatus.DOWNLOADED) {
                // Update downloaded, prompt the user to install
                popupSnackbarForCompleteUpdate();
            }
        });


        // Define the listener
        installStateUpdatedListener = state -> {
            if (state.installStatus() == InstallStatus.DOWNLOADED) {
                // Update downloaded, prompt the user to install
                popupSnackbarForCompleteUpdate();
            }
        };

        // Register the listener
        appUpdateManager.registerListener(installStateUpdatedListener);


        btnClose.setOnClickListener(v -> {
            finish();
            interstitialManager.showInterstitial(SettingsActivity.this);
        });
        btnReminder.setOnClickListener(v -> {
            Intent intent = new Intent(this, ReminderActivity.class);
            startActivity(intent);
        });

        btnNotifications.setOnClickListener(v -> {
            Intent intent = new Intent(this, NotificationsPreferenceActivity.class);
            startActivity(intent);
        });

        btnShare.setOnClickListener(v -> {
            ShareManager shareManager = new ShareManager(this);
            shareManager.shareApp();
        });

        btnRate.setOnClickListener(v -> {
            RateManager rateManager = new RateManager(this);
            rateManager.rate();
        });

        btnTerms.setOnClickListener(v -> {
            Intent intent = new Intent(this, TermsActivity.class);
            startActivity(intent);
        });

        btnAbout.setOnClickListener(v -> {
            Intent intent = new Intent(this, AboutUsActivity.class);
            startActivity(intent);
        });

        btnFeedback.setOnClickListener(v -> {
            Intent intent = new Intent(this, FeedbackActivity.class);
            startActivity(intent);
        });

        btnDelete.setOnClickListener(v -> {
            authManager.logOut();
        });

        sharedPreferences = getSharedPreferences("MODE", Context.MODE_PRIVATE);
        nightMode = sharedPreferences.getBoolean("nightMode", false);

        changeSwitch();

        switchMode.setOnClickListener(v -> {
            if(nightMode) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                editor = sharedPreferences.edit();
                editor.putBoolean("nightMode", false);
                switchMode.setChecked(false);
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                editor = sharedPreferences.edit();
                editor.putBoolean("nightMode", true);
                switchMode.setChecked(true);
            }
            editor.commit();
        });

        // Check for updates
        checkForAppUpdate();
    }

    private void changeSwitch() {
        if(nightMode) {
            switchMode.setChecked(true);
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == MY_REQUEST_CODE) {
            if (resultCode != RESULT_OK) {
                // If the update is cancelled or fails, handle accordingly
                // You may want to retry or notify the user
            }
        }
    }


    //to avoid memory leaks
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (appUpdateManager != null && installStateUpdatedListener != null) {
            appUpdateManager.unregisterListener(installStateUpdatedListener);
        }
    }


    private void checkForAppUpdate() {
        Task<AppUpdateInfo> appUpdateInfoTask = appUpdateManager.getAppUpdateInfo();

        appUpdateInfoTask.addOnSuccessListener(appUpdateInfo -> {
            if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE
                    && appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.FLEXIBLE)) {
                // Request the update
                try {
                    appUpdateManager.startUpdateFlowForResult(
                            appUpdateInfo,
                            AppUpdateType.FLEXIBLE,
                            this,
                            MY_REQUEST_CODE);
                } catch (IntentSender.SendIntentException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void popupSnackbarForCompleteUpdate() {
        Snackbar snackbar =
                Snackbar.make(findViewById(android.R.id.content), "An update has been downloaded.",
                        Snackbar.LENGTH_INDEFINITE);
        snackbar.setAction("INSTALL", view -> appUpdateManager.completeUpdate());
        snackbar.show();
    }
}