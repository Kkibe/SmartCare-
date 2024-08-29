package com.nutricon.smartcare.fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.nutricon.smartcare.R;
import com.nutricon.smartcare.activities.BlogsActivity;
import com.nutricon.smartcare.adapters.PostsAdapter;
import com.nutricon.smartcare.ads.BannerManager;
import com.nutricon.smartcare.ads.InterstitialManager;
import com.nutricon.smartcare.data.Post;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    private RecyclerView recyclerView;
    private PostsAdapter postsAdapter;
    private List<Post> postList;
    private FirebaseFirestore db;
    LinearLayoutManager linearLayoutManager;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        FrameLayout adViewContainer = view.findViewById(R.id.adViewContainer);
        BannerManager bannerManager = new BannerManager(requireContext(), requireActivity(), adViewContainer);
        bannerManager.loadBanner();


        recyclerView = view.findViewById(R.id.recyclerView);
        postList = new ArrayList<>();
        postsAdapter = new PostsAdapter(requireActivity(), requireContext(), postList);
        linearLayoutManager = new LinearLayoutManager(requireContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        db = FirebaseFirestore.getInstance();
        readFirebase();

        InterstitialManager interstitialManager = new InterstitialManager();
        interstitialManager.loadInterstitial(requireActivity());


        CardView cardView = view.findViewById(R.id.cardView);

        cardView.setOnClickListener(v -> {
            Intent intent = new Intent(requireContext(), BlogsActivity.class);
            startActivity(intent);
            //interstitialManager.showInterstitial(requireActivity());
        });
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
                        Toast.makeText(requireContext(), task.getException().toString(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

}