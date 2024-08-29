package com.nutricon.smartcare.activities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.nutricon.smartcare.MainActivity;
import com.nutricon.smartcare.R;
import com.nutricon.smartcare.ads.BannerManager;
import com.nutricon.smartcare.data.Info;
import com.nutricon.smartcare.resources.BookmarkManager;
import com.nutricon.smartcare.resources.ShareManager;
import com.squareup.picasso.BuildConfig;
import com.squareup.picasso.Picasso;

public class SingleInfoActivity extends AppCompatActivity {

    TextView textReadingTime, textDate, btnLike, btnBookmark, btnShare, textTitle, textDescription, tag1, tag2, tag3, btnMore;
    ImageView imageThumbnail;
    FirebaseFirestore db;
    String id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_info);

        db = FirebaseFirestore.getInstance();

        Intent intent = getIntent();
        id = intent.getStringExtra("id");

        FrameLayout adViewContainer = findViewById(R.id.adViewContainer);
        BannerManager bannerManager = new BannerManager(this, SingleInfoActivity.this, adViewContainer);
        bannerManager.loadBanner();


        textReadingTime = findViewById(R.id.textReadingTime);
        textDate = findViewById(R.id.textDate);
        btnLike = findViewById(R.id.btnLike);
        btnBookmark = findViewById(R.id.btnBookmark);
        btnShare = findViewById(R.id.btnShare);
        btnMore = findViewById(R.id.btnMore);

        textTitle = findViewById(R.id.textTitle);
        textDescription = findViewById(R.id.textDescription);
        tag1 = findViewById(R.id.tag1);
        tag2 = findViewById(R.id.tag2);
        tag3= findViewById(R.id.tag3);
        imageThumbnail = findViewById(R.id.imageThumbnail);

        btnBookmark.setOnClickListener(v -> {
            //BookmarkManager bookmarkManager = new BookmarkManager();
            //bookmarkManager.addBookmark("abcdefzELlgoXI7IhdYGdANnm2", btnBookmark, SingleInfoActivity.this);
        });


        btnShare.setOnClickListener(v -> {
            ShareManager shareManager = new ShareManager(this);
            shareManager.shareText(textTitle.getText().toString() +  "\n\n" + "https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID +"\n\n");
        });

        btnMore.setOnClickListener(v -> {
            SharedPreferences prefs = getSharedPreferences("PREFERENCES", MODE_PRIVATE);
            prefs.edit().putString("active_fragment", "info").commit();
            startActivity(new Intent(SingleInfoActivity.this, MainActivity.class));
        });
        readFirebase();
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private void readFirebase () {
        db.collection("info").document(id).get().addOnCompleteListener(task -> {
            if(task.isSuccessful()) {
                DocumentSnapshot doc = task.getResult();
                Info info = new Info(doc.getString("title"), doc.getString("image"), doc.getString("description"), doc.getId());
                textTitle.setText(info.getTitle());
                textDescription.setText(info.getDescription());
                //btnBookmark.setImageDrawable(getDrawable(R.drawable.baseline_bookmarks_24));
                Picasso.get().load(info.getImage()).placeholder(R.mipmap.ic_launcher).error(R.mipmap.ic_launcher).into(imageThumbnail);

            } else {
                Toast.makeText(SingleInfoActivity.this, "While trying to load data!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}