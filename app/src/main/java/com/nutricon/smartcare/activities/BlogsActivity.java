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
import com.nutricon.smartcare.adapters.PostsAdapter;
import com.nutricon.smartcare.ads.BannerManager;
import com.nutricon.smartcare.data.Post;

import java.util.ArrayList;
import java.util.List;

public class BlogsActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private PostsAdapter postsAdapter;
    private List<Post> postList;
    private FirebaseFirestore db;
    LinearLayoutManager linearLayoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blogs);

        FrameLayout adViewContainer = findViewById(R.id.adViewContainer);
        BannerManager bannerManager = new BannerManager(BlogsActivity.this, BlogsActivity.this, adViewContainer);
        bannerManager.loadBanner();


        recyclerView = findViewById(R.id.recyclerView);
        postList = new ArrayList<>();
        postsAdapter = new PostsAdapter(BlogsActivity.this, BlogsActivity.this, postList);
        linearLayoutManager = new LinearLayoutManager(BlogsActivity.this);
        recyclerView.setLayoutManager(linearLayoutManager);
        db = FirebaseFirestore.getInstance();
        readFirebase();
    }

    @SuppressLint("NotifyDataSetChanged")
    private void readFirebase () {
        db.collection("blogs")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Post post = new Post(
                                    document.getId(),
                                    "Smart Care",
                                    document.getString("title"),
                                    document.getString("description"),
                                    document.getString("date"),
                                    document.getString("image"));
                            postList.add(post);
                        }
                        recyclerView.setAdapter(postsAdapter);
                        postsAdapter.notifyDataSetChanged();
                    } else {
                        Toast.makeText(this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}