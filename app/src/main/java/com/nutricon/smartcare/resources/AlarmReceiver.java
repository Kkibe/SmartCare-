package com.nutricon.smartcare.resources;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import java.util.Calendar;

public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        // You can show a notification or trigger any other action here
        Toast.makeText(context, "Time to take your medication!", Toast.LENGTH_LONG).show();
    }
}
