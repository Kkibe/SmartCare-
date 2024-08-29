package com.nutricon.smartcare.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.nutricon.smartcare.R;
import com.nutricon.smartcare.adapters.InfoAdapter;
import com.nutricon.smartcare.ads.BannerManager;
import com.nutricon.smartcare.data.Info;

import java.util.ArrayList;
import java.util.List;

public class InfoFragment extends Fragment {
    private InfoAdapter infoAdapter;
    private List<Info> infoList;
    private FirebaseFirestore db;
    private RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_info, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        FrameLayout adViewContainer = view.findViewById(R.id.adViewContainer);
        BannerManager bannerManager = new BannerManager(requireContext(), requireActivity(), adViewContainer);
        bannerManager.loadBanner();

        db = FirebaseFirestore.getInstance();
        recyclerView = view.findViewById(R.id.recyclerView);

        infoList = new ArrayList<>();
        infoAdapter = new InfoAdapter(requireActivity(), requireContext(), infoList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        readFirebase();

    }
    private void readFirebase () {
        db.collection("info")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Info info = new Info(
                                    document.getString("title"),
                                    document.getString("image"),
                                    document.getString("description"),
                                    document.getId());
                            infoList.add(info);
                        }
                        recyclerView.setAdapter(infoAdapter);
                        infoAdapter.notifyDataSetChanged();
                    }
                });
    }

}