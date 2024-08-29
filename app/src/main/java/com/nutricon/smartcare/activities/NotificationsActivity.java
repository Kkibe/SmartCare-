package com.nutricon.smartcare.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.nutricon.smartcare.R;
import com.nutricon.smartcare.adapters.NotificationsAdapter;
import com.nutricon.smartcare.ads.BannerManager;
import com.nutricon.smartcare.data.Notification;

import java.util.ArrayList;
import java.util.List;
public class NotificationsActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private NotificationsAdapter notificationsAdapter;
    private List<Notification> notificationList;
    private FirebaseFirestore db;
    LinearLayoutManager linearLayoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);

        FrameLayout adViewContainer = findViewById(R.id.adViewContainer);
        BannerManager bannerManager = new BannerManager(NotificationsActivity.this, NotificationsActivity.this, adViewContainer);
        bannerManager.loadBanner();

        db = FirebaseFirestore.getInstance();
        recyclerView = findViewById(R.id.recyclerView);
        notificationList = new ArrayList<>();
        notificationsAdapter = new NotificationsAdapter(NotificationsActivity.this, notificationList);
        linearLayoutManager = new LinearLayoutManager(NotificationsActivity.this);
        recyclerView.setLayoutManager(linearLayoutManager);

        readFirebase();
    }


    @SuppressLint("NotifyDataSetChanged")
    private void readFirebase () {
        db.collection("notifications")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Notification notification = new Notification(
                                    document.getString("image"),
                                    document.getString("title"),
                                    document.getString("description"),
                                    document.getBoolean("isRead"),
                                    document.getString("date"));
                            notificationList.add(notification);
                        }
                        recyclerView.setAdapter(notificationsAdapter);
                        notificationsAdapter.notifyDataSetChanged();
                    } else {
                        Toast.makeText(NotificationsActivity.this, task.getException().toString(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}