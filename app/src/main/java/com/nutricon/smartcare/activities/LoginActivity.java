package com.nutricon.smartcare.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.common.SignInButton;
import com.nutricon.smartcare.R;
import com.nutricon.smartcare.resources.AuthManager;

public class LoginActivity extends AppCompatActivity {
    private AuthManager authManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        authManager = new AuthManager(this, LoginActivity.this);

        EditText textEmail = findViewById(R.id.textEmail);
        EditText textPassword = findViewById(R.id.textPassword);
        TextView btnLogin = findViewById(R.id.btnLogin);
        TextView btnStarted = findViewById(R.id.btnStarted);
        TextView btnReset = findViewById(R.id.btnReset);

        btnLogin.setOnClickListener(v -> authManager.signInUser(textEmail.getText().toString(), textEmail, textPassword.getText().toString(), textPassword));

        btnStarted.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
            finish();
        });

        btnReset.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, ResetPasswordActivity.class);
            startActivity(intent);
        });

    }
}