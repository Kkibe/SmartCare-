package com.nutricon.smartcare.resources;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.widget.ImageView;
import android.widget.TextView;

import com.nutricon.smartcare.R;

import java.util.Set;

public class BookmarkManager {
    public void addBookmark (String id, TextView textView, Activity activity) {

        SharedPreferences sharedPref = activity.getPreferences(Context.MODE_PRIVATE);
        Set<String> set = sharedPref.getStringSet(activity.getString(R.string.INFO_BOOKMARKS_PREFERENCE), null);

        /*if (set.contains(id)) {
            imageView.setImageDrawable(activity.getDrawable(R.drawable.baseline_bookmarks_24));
        } else {
            imageView.setImageDrawable(activity.getDrawable(R.drawable.baseline_bookmark_border_24));
        }
         */

        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putStringSet(activity.getString(R.string.INFO_BOOKMARKS_PREFERENCE), set);
        editor.apply();
    }
}
