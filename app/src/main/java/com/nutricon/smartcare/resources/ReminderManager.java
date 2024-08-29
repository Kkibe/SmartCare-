package com.nutricon.smartcare.resources;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

public class ReminderManager {
    private static final String PREFS_NAME = "ReminderPrefs";
    private static final String KEY_ALARMS = "alarms";
    static Context context;
    static  Activity activity;

    public ReminderManager(Context context, Activity activity) {
        this.context = context;
        this.activity = activity;
    }

    static AlarmManager alarmManager = (AlarmManager) activity.getSystemService(Context.ALARM_SERVICE);
    static Intent intent = new Intent(context, AlarmReceiver.class);

    public void setAlarm(int requestCode, Calendar calendar) {
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, requestCode, intent, 0);
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
        saveAlarm(context, requestCode, calendar.getTimeInMillis());
    }

    public void cancelAlarm(int requestCode) {
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, requestCode, intent, 0);
        alarmManager.cancel(pendingIntent);
        removeAlarm(context, requestCode);
    }


    //If you want the reminders to repeat daily, you can modify the setAlarm method to use setRepeating instead of setExact.
    public static void setRepeatingAlarm(int requestCode, Calendar calendar) {
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, requestCode, intent, 0);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                AlarmManager.INTERVAL_DAY, pendingIntent);
    }


    private static void saveAlarm(Context context, int requestCode, long timeInMillis) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        Set<String> alarms = prefs.getStringSet(KEY_ALARMS, new HashSet<>());
        alarms.add(requestCode + ":" + timeInMillis);
        prefs.edit().putStringSet(KEY_ALARMS, alarms).apply();
    }

    private static void removeAlarm(Context context, int requestCode) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        Set<String> alarms = prefs.getStringSet(KEY_ALARMS, new HashSet<>());
        Set<String> updatedAlarms = new HashSet<>();
        for (String alarm : alarms) {
            if (!alarm.startsWith(requestCode + ":")) {
                updatedAlarms.add(alarm);
            }
        }
        prefs.edit().putStringSet(KEY_ALARMS, updatedAlarms).apply();
    }

    public static Set<String> getAlarms(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        return prefs.getStringSet(KEY_ALARMS, new HashSet<>());
    }
}
