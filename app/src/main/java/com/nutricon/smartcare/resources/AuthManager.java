package com.nutricon.smartcare.resources;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.nutricon.smartcare.MainActivity;

public class AuthManager {
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    FirebaseUser currentUser = firebaseAuth.getCurrentUser();
    Context context;
    Activity activity;

    public AuthManager(Context context, Activity activity) {
        this.context = context;
        this.activity = activity;
    }

    ManageNotifications manageNotifications = new ManageNotifications(context);
    public Object getUser () {
       return currentUser = firebaseAuth.getCurrentUser();
    }

    public void signInAnonymously() {
        /*firebaseAuth.signInAnonymously().addOnCompleteListener(task -> Toast.makeText(context, "Welcome, make sure to save your bookmarks and health data when you leave.", Toast.LENGTH_LONG).show()).addOnFailureListener(e -> {
            Toast.makeText(context, "Welcome, this app is better with registered users.", Toast.LENGTH_LONG).show();
            activity.startActivity(new Intent(context, MainActivity.class));
        });*/
        activity.startActivity(new Intent(context, MainActivity.class));
    }


    public void registerNewUser(String username, EditText textUsername, String email, EditText textEmail, String password, EditText textPassword) {
        if (!validateUsername(username, textUsername) | !validateEmail(email, textEmail) | !validatePassword(password, textPassword)) {
            return;
        }
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        //add user to users database;
                        Toast.makeText(context, "Registration successful!", Toast.LENGTH_LONG).show();
                        //manageNotifications.init("Success!", "You are now connected!");
                        //manageNotifications.push();
                        //progressBar.setVisibility(View.GONE);
                        Intent intent = new Intent(activity, MainActivity.class);
                        activity.startActivity(intent);
                    } else {
                        Toast.makeText(context, "Registration failed!! Check your internet connectivity and try again", Toast.LENGTH_LONG).show();
                        //progressBar.setVisibility(View.GONE);
                    }
                }).addOnFailureListener(e -> Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show());
    }

    public void signInUser(String email, EditText textEmail, String password, EditText textPassword) {
        if (!validateEmail(email, textEmail) | !validatePassword(password, textPassword)) {
            return;
        }
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(context, "You are logged in successfully!", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(activity, MainActivity.class);
                        activity.startActivity(intent);
                    } else {
                        Toast.makeText(context, "Sign in failed!! Check your internet connectivity and try again", Toast.LENGTH_LONG).show();
                        //progressBar.setVisibility(View.GONE);
                    }
                });
    }


    public void resetPassword(String email, EditText editText) {
        if (!validateEmail(email, editText)) return;
        firebaseAuth.sendPasswordResetEmail(email).addOnCompleteListener(task -> Toast.makeText(context, "Check " + email + " for verification code.", Toast.LENGTH_SHORT).show()).addOnFailureListener(e -> Toast.makeText(context, "Failed to send verification code. Check your network settings and try again!", Toast.LENGTH_SHORT).show());
    }

    public void logOut() {
        firebaseAuth.signOut();
        firebaseAuth.addAuthStateListener(auth -> {
            //to do next here
            activity.finish();
            activity.startActivity(new Intent(context, MainActivity.class));
        });
    }

    private boolean checkWhiteSpace(String line) {
        for (int i = 0; i < line.length(); i++) {
            if (line.charAt(i) == ' ') {
                return true;
            }
        }
        return false;
    }

    private boolean validateUsername(String username, EditText editText) {
        ;
        if (username.isEmpty()) {
            editText.setError("Field can not be empty");
            return false;
        } else if (username.length() > 20) {
            editText.setError("Username is too large!");
            return false;
        } else if (checkWhiteSpace(username)) {
            editText.setError("No White spaces are allowed!");
            return false;
        } else {
            editText.setError(null);
            return true;
        }
    }

    private boolean validateEmail(String email, EditText editText) {
        String checkEmail = "[a-zA-Z0-9._-]+@[a-z]+.+[a-z]+";
        if (email.isEmpty()) {
            editText.setError("Field can not be empty");
            return false;
        } else if (!email.matches(checkEmail)) {
            editText.setError("Invalid Email!");
            return false;
        } else {
            editText.setError(null);
            return true;
        }
    }

    private boolean validatePassword(String password, EditText editText) {
        if (password.isEmpty()) {
            editText.setError("Field can not be empty");
            return false;
        } else if (password.length() < 6) {
            editText.setError("Password should contain 6 characters!");
            return false;
        } else {
            editText.setError(null);
            return true;
        }
    }
}
