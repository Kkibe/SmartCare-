package com.nutricon.smartcare.resources;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.nutricon.smartcare.R;
import com.nutricon.smartcare.activities.TermsActivity;
public class UrlManager {
    Context context;
    public UrlManager(Context myContext) {
        context = myContext;
    }

    public void openTwitter() {
        Uri uri = Uri.parse(context.getString(R.string.twitter_profile));
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        context.startActivity(intent);
    }

    public void openTelegram() {
        Uri uri = Uri.parse(context.getString(R.string.telegram_channel));
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        context.startActivity(intent);
    }

    public void openWhatsapp() {
        Uri uri = Uri.parse(context.getString(R.string.whatsapp_channel));
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        context.startActivity(intent);
    }

    public void moreApps() {
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(context.getString(R.string.more_apps_url)));
            intent.setPackage(context.getPackageName());
            context.startActivity(intent);
        } catch (ActivityNotFoundException e) {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(context.getString(R.string.more_apps_url)));
            context.startActivity(intent);
        }
    }

    public void openTerms () {
        Intent intent = new Intent(context, TermsActivity.class);
        context.startActivity(intent);
    }

}
