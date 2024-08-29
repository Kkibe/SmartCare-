package com.nutricon.smartcare;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.nutricon.smartcare.fragments.AccountFragment;
import com.nutricon.smartcare.fragments.HomeFragment;
import com.nutricon.smartcare.fragments.InfoFragment;
import com.nutricon.smartcare.fragments.TrackerFragment;
import com.nutricon.smartcare.resources.BottomSheetDialogManager;
import com.onesignal.OneSignal;

public class MainActivity extends AppCompatActivity {
    private SharedPreferences prefs ;
    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //EdgeToEdge.enable(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // OneSignal Initialization
        OneSignal.initWithContext(this);
        OneSignal.setAppId(getString(R.string.Onesignal_Id));
        prefs = getSharedPreferences("PREFERENCES", MODE_PRIVATE);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        getSupportFragmentManager().beginTransaction().replace(R.id.container,  returnActiveFragment()).commit();
        bottomNavigationView.setOnItemSelectedListener(item -> {
            Fragment fragment = null;
            String activeFragment = "home";
            switch (item.getItemId()) {
                case R.id.home:
                    fragment = new HomeFragment();
                    activeFragment = "home";
                    break;
                case R.id.trackers:
                    fragment = new TrackerFragment();
                    activeFragment = "tracker";
                    break;
                case R.id.info:
                    fragment = new InfoFragment();
                    activeFragment = "info";
                    break;
                case R.id.profile:
                    fragment = new AccountFragment();
                    activeFragment = "profile";
                    break;

            }
            item.setChecked(true);
            assert fragment != null;
            getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();
            prefs.edit().putString("active_fragment", activeFragment).apply();
            return false;
        });

        FloatingActionButton fabAdd = findViewById(R.id.fabAdd);

        fabAdd.setOnClickListener( v -> {
            BottomSheetDialogManager dialogManager = new BottomSheetDialogManager();
            dialogManager.openDialog(this, MainActivity.this);
        });
    }

    private Fragment returnActiveFragment () {
        String activeFragment = prefs.getString("active_fragment", "home");
        switch (activeFragment) {
            case "tracker":
                return new TrackerFragment();
            case "info":
                return new InfoFragment();
            case "profile":
                return new AccountFragment();
        }
        return new HomeFragment();
    }
}