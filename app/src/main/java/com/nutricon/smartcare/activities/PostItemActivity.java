package com.nutricon.smartcare.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.nutricon.smartcare.R;
import com.nutricon.smartcare.ads.BannerManager;
import com.nutricon.smartcare.data.Info;
import com.nutricon.smartcare.resources.ShareManager;
import com.squareup.picasso.BuildConfig;
import com.squareup.picasso.Picasso;

public class PostItemActivity extends AppCompatActivity {
    TextView btnComment, textReadingTime, textDate, btnLike, btnBookmark, btnShare, textTitle, textDescription, tag1, tag2, tag3;
    ImageView imageThumbnail;
    FirebaseFirestore db;
    String id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_item);

        db = FirebaseFirestore.getInstance();

        Intent intent = getIntent();
        id = intent.getStringExtra("id");

        FrameLayout adViewContainer = findViewById(R.id.adViewContainer);
        BannerManager bannerManager = new BannerManager(PostItemActivity.this, PostItemActivity.this, adViewContainer);
        bannerManager.loadBanner();

        btnComment = findViewById(R.id.btnComment);
        textReadingTime = findViewById(R.id.textReadingTime);
        textDate = findViewById(R.id.textDate);
        btnLike = findViewById(R.id.btnLike);
        btnBookmark = findViewById(R.id.btnBookmark);
        btnShare = findViewById(R.id.btnShare);

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
        readFirebase();

        btnComment.setOnClickListener(v -> {
            Dialog dialog = new Dialog(PostItemActivity.this);
            dialog.setContentView(R.layout.write_comment_dialog);
            dialog.setCancelable(true);
            dialog.getWindow().setGravity(Gravity.CENTER);

            EditText textComment = dialog.findViewById(R.id.btnComment);
            TextView btnSend = dialog.findViewById(R.id.btnSend);


            btnSend.setOnClickListener(v1 -> {
                if(textComment.getText().equals("")){
                    Toast.makeText(PostItemActivity.this, "Comment cannot be empty", Toast.LENGTH_SHORT).show();
                    return;
                }
                dialog.dismiss();
                //add comment
                //dialog.closeOptionsMenu();
            });

            dialog.show();
        });
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private void readFirebase () {
        db.collection("blogs").document(id).get().addOnCompleteListener(task -> {
            if(task.isSuccessful()) {
                DocumentSnapshot doc = task.getResult();
                Info info = new Info(doc.getString("title"), doc.getString("image"), doc.getString("description"), doc.getId());
                textTitle.setText(info.getTitle());
                textDescription.setText(info.getDescription());
                //btnBookmark.setImageDrawable(getDrawable(R.drawable.baseline_bookmarks_24));
                Picasso.get().load(info.getImage()).placeholder(R.mipmap.ic_launcher).error(R.mipmap.ic_launcher).into(imageThumbnail);

            } else {
                Toast.makeText(PostItemActivity.this, "While trying to load data!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}