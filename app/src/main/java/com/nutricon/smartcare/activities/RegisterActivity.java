package com.nutricon.smartcare.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.nutricon.smartcare.R;
import com.nutricon.smartcare.resources.AuthManager;

public class RegisterActivity extends AppCompatActivity {
    private AuthManager authManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        EditText textUsername = findViewById(R.id.textUsername);
        EditText textEmail = findViewById(R.id.textEmail);
        EditText textPassword = findViewById(R.id.textPassword);
        TextView btnRegister = findViewById(R.id.btnRegister);
        TextView btnSignIn = findViewById(R.id.btnSignIn);

        authManager = new AuthManager(this, RegisterActivity.this);

        btnRegister.setOnClickListener(v -> authManager.registerNewUser(textUsername.getText().toString(), textUsername, textEmail.getText().toString(), textEmail, textPassword.getText().toString(), textPassword));

        btnSignIn.setOnClickListener(v -> {
            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        });
    }
}