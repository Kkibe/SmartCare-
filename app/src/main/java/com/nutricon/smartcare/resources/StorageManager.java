package com.nutricon.smartcare.resources;

import android.app.Activity;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

public class StorageManager {

    //add & remove notification
    //add & remove bookmark
    //clear all fun
    //add & remove reminder
    //add bmi
    //add bmr
    //add blood sugar record
    //heart rate

    Context context;
    Activity activity;

    public StorageManager(Context context, Activity activity) {
        this.context = context;
        this.activity = activity;
    }

    FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();

    AuthManager authManager = new AuthManager(context, activity);

    public void addNotification () {
        if(authManager.getUser() != null){
            //add notification
        }
    }

}
