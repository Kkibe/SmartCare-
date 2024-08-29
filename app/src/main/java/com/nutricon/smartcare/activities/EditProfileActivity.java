package com.nutricon.smartcare.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.nutricon.smartcare.R;
import com.nutricon.smartcare.ads.BannerManager;

public class EditProfileActivity extends AppCompatActivity {
    EditText textUsername, textEmail;
    TextView btnChangePassword, btnUpdate;
    FirebaseUser currentUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        FrameLayout adViewContainer = findViewById(R.id.adViewContainer);
        BannerManager bannerManager = new BannerManager(EditProfileActivity.this, EditProfileActivity.this, adViewContainer);
        bannerManager.loadBanner();

        textUsername = findViewById(R.id.textUsername);
        textEmail = findViewById(R.id.textEmail);
        currentUser = FirebaseAuth.getInstance().getCurrentUser();


        btnChangePassword = findViewById(R.id.btnChangePassword);
        btnUpdate = findViewById(R.id.btnUpdate);

        if(currentUser == null){
            Toast.makeText(this, "No User Found", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(EditProfileActivity.this, LoginActivity.class);
            startActivity(intent);
            return;
        }

        textUsername.setText(currentUser.getDisplayName());
        textEmail.setText(currentUser.getEmail());
        textEmail.setEnabled(false);

        btnUpdate.setOnClickListener(v -> handleUpdate());
        btnChangePassword.setOnClickListener(v -> {
            Intent intent = new Intent(EditProfileActivity.this, ResetPasswordActivity.class);
            startActivity(intent);
        });
    }

    private void handleUpdate() {
                String username = textUsername.getText().toString();
        if(currentUser == null){
            return;
        } else if(!currentUser.getDisplayName().equals(username)){
            currentUser.updateProfile(
                    new UserProfileChangeRequest.Builder().setDisplayName(username).build()).addOnCompleteListener(task -> textUsername.setText(currentUser.getDisplayName())).addOnFailureListener(e -> Toast.makeText(EditProfileActivity.this, "Failed to update your profile", Toast.LENGTH_SHORT).show());
        }
    }
}