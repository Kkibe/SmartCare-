package com.nutricon.smartcare.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.nutricon.smartcare.R;
import com.nutricon.smartcare.activities.BookmarksActivity;
import com.nutricon.smartcare.activities.EditProfileActivity;
import com.nutricon.smartcare.activities.LoginActivity;
import com.nutricon.smartcare.activities.NotificationsActivity;
import com.nutricon.smartcare.activities.SettingsActivity;
import com.nutricon.smartcare.ads.BannerManager;
import com.nutricon.smartcare.ads.InterstitialManager;
import com.nutricon.smartcare.resources.AuthManager;
import com.squareup.picasso.Picasso;

public class AccountFragment extends Fragment {

    AuthManager authManager;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_account, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        FrameLayout adViewContainer = view.findViewById(R.id.adViewContainer);
        BannerManager bannerManager = new BannerManager(requireContext(), requireActivity(), adViewContainer);
        bannerManager.loadBanner();

        authManager = new AuthManager(getContext(), getActivity());

        InterstitialManager interstitialManager = new InterstitialManager();
        interstitialManager.loadInterstitial(requireActivity());

        TextView btnSettings = view.findViewById(R.id.btnSettings);
        TextView btnBookmarks = view.findViewById(R.id.btnBookmarks);
        TextView btnNotifications = view.findViewById(R.id.btnNotifications);
        TextView btnEdit = view.findViewById(R.id.btnEdit);
        TextView btnLogout = view.findViewById(R.id.btnLogout);


        btnSettings.setOnClickListener(v -> {
            Intent intent = new Intent(requireContext(), SettingsActivity.class);
            startActivity(intent);
            interstitialManager.showInterstitial(requireActivity());
        });

        btnBookmarks.setOnClickListener(v -> {
            Intent intent = new Intent(requireContext(), BookmarksActivity.class);
            startActivity(intent);
            interstitialManager.showInterstitial(requireActivity());
        });

        btnNotifications.setOnClickListener(v -> {
            Intent intent = new Intent(requireContext(), NotificationsActivity.class);
            startActivity(intent);
            interstitialManager.showInterstitial(requireActivity());
        });


        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        LinearLayout userData = view.findViewById(R.id.userData);
        LinearLayout noUserData = view.findViewById(R.id.noUserData);

        btnEdit.setOnClickListener(v -> {
            Intent intent  = new Intent(requireContext(), EditProfileActivity.class);
            if (currentUser == null) {
                intent = new Intent(requireContext(), LoginActivity.class);
            }
            startActivity(intent);
        });

        TextView btnLogin = view.findViewById(R.id.btnLogin);


        btnLogin.setOnClickListener(v -> {
            Intent intent = new Intent(requireContext(), LoginActivity.class);
            startActivity(intent);
            interstitialManager.showInterstitial(requireActivity());
        });

        if (currentUser == null) {
            userData.setVisibility(View.GONE);
            noUserData.setVisibility(View.VISIBLE);
            btnLogout.setVisibility(View.GONE);
            return;
        }
        TextView email = view.findViewById(R.id.textEmail);
        TextView displayName = view.findViewById(R.id.textUsername);
        TextView phone = view.findViewById(R.id.textPhone);
        ImageView profileImage = view.findViewById(R.id.imageProfile);
        email.setText(currentUser.getEmail());
        displayName.setText(currentUser.getDisplayName());
        phone.setText(currentUser.getPhoneNumber());
        btnLogout.setOnClickListener(v -> authManager.logOut());
        //Picasso.get().load(currentUser.getPhotoUrl()).placeholder(R.drawable.baseline_account_circle_24).error(R.drawable.baseline_account_circle_24).into(profileImage);
    }
}