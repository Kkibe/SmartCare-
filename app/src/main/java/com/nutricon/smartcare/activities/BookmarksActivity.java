package com.nutricon.smartcare.activities;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.nutricon.smartcare.R;
import com.nutricon.smartcare.adapters.PostsAdapter;
import com.nutricon.smartcare.ads.BannerManager;
import com.nutricon.smartcare.data.Post;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BookmarksActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private PostsAdapter postsAdapter;
    private List<Post> postList;
    private FirebaseFirestore db;
    LinearLayoutManager linearLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookmarks);

        FrameLayout adViewContainer = findViewById(R.id.adViewContainer);
        BannerManager bannerManager = new BannerManager(this, BookmarksActivity.this, adViewContainer);
        bannerManager.loadBanner();


        recyclerView = findViewById(R.id.recyclerView);
        postList = new ArrayList<>();
        postsAdapter = new PostsAdapter(BookmarksActivity.this, this, postList);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        db = FirebaseFirestore.getInstance();

        setAnimation();
    }

    @SuppressLint("NotifyDataSetChanged")
    private void readFirebase() {
        db.collection("blogs")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            /*if(postList.contains(document)){
                                Post post = new Post(
                                        document.getId(),
                                        "smartcare",
                                        document.getString("title"),
                                        document.getString("description"),
                                        document.getString("date"),
                                        document.getString("image"));
                                postList.add(post);
                            }*/
                        }
                        recyclerView.setAdapter(postsAdapter);
                        postsAdapter.notifyDataSetChanged();
                    } else {
                        Toast.makeText(this, task.getException().toString(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void setAnimation() {
        int resId = 0;

        Random rand = new Random();
        int n = rand.nextInt(4);
        ArrayList arrayList = new ArrayList<>();
        arrayList.add(R.anim.layout_animation_fall_down);
        arrayList.add(R.anim.layout_animation_slide_up);
        arrayList.add(R.anim.layout_animation_rotate_in);
        arrayList.add(R.anim.layout_animation_scale_in);
        resId = R.anim.layout_animation_fall_down;


        if (resId != 0) {
            // Set animation for RecyclerView
            LayoutAnimationController animation = AnimationUtils.loadLayoutAnimation(this, resId);
            recyclerView.setLayoutAnimation(animation);
            readFirebase();
        }

    }

}
