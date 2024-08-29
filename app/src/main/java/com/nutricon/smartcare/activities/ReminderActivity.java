package com.nutricon.smartcare.activities;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.nutricon.smartcare.R;
import com.nutricon.smartcare.ads.BannerManager;
import com.nutricon.smartcare.data.Reminder;
import com.nutricon.smartcare.resources.AlarmReceiver;
import com.nutricon.smartcare.resources.ReminderManager;

import java.util.ArrayList;
import java.util.Calendar;

public class ReminderActivity extends AppCompatActivity {
    FloatingActionButton mCreateRem;
    RecyclerView recyclerview;
    ArrayList<Reminder> dataholder = new ArrayList<Reminder>();                                               //Array list to add reminders and display in recyclerview
    //MyAdapter adapter;


    private TextView tvReminderTime;
    private Calendar calendar;
    private ReminderManager reminderManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder);

        FrameLayout adViewContainer = findViewById(R.id.adViewContainer);
        BannerManager bannerManager = new BannerManager(ReminderActivity.this, ReminderActivity.this, adViewContainer);
        bannerManager.loadBanner();

        reminderManager = new ReminderManager(ReminderActivity.this, ReminderActivity.this);
        FloatingActionButton floatingActionButton = findViewById(R.id.btnReminder);
        floatingActionButton.setOnClickListener(v -> showTimePickerDialog());

        /*
        // Example: Setting two alarms
        Calendar calendar1 = Calendar.getInstance();
        calendar1.set(Calendar.HOUR_OF_DAY, 9);  // Set the desired time for the first reminder
        calendar1.set(Calendar.MINUTE, 0);

        Calendar calendar2 = Calendar.getInstance();
        calendar2.set(Calendar.HOUR_OF_DAY, 21);  // Set the desired time for the second reminder
        calendar2.set(Calendar.MINUTE, 0);

        AlarmReceiver.setAlarm(this, 1001, calendar1);  // Unique requestCode for each alarm
        AlarmReceiver.setAlarm(this, 1002, calendar2);

         */

        //Create a user interface where users can input their desired times for medication reminders. You can use TimePickerDialog for this purpose.

        /*recyclerview = findViewById(R.id.recyclerView);

        recyclerview.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        mCreateRem = findViewById(R.id.create_reminder);
        //Floating action button to change activity
        mCreateRem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ReminderActivity.this, ReminderActivity.class);
                startActivity(intent);
                //Starts the new activity to add Reminders
            }
        });

        Cursor cursor = new dbManager(getApplicationContext()).readallreminders();                  //Cursor To Load data From the database
        while (cursor.moveToNext()) {
            Reminder model = new Reminder(cursor.getString(1), cursor.getString(2), cursor.getString(3), true);
            dataholder.add(model);
        }

        adapter = new myAdapter(dataholder);
        recyclerview.setAdapter(adapter);                                                          //Binds the adapter with recyclerview
*/
    }

    private void showTimePickerDialog() {
        calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(
                this,
                (view, hourOfDay, minute1) -> {
                    calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                    calendar.set(Calendar.MINUTE, minute1);
                    setReminder(calendar);
                },
                hour,
                minute,
                true
        );

        timePickerDialog.show();
    }
    private void setReminder(Calendar calendar) {
        // Use a unique requestCode for each reminder
        int requestCode = (int) System.currentTimeMillis();

        reminderManager.setAlarm(requestCode, calendar);
        // Update the UI with the selected time
        //String reminderTime = String.format("%02d:%02d", calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE));
        //tvReminderTime.setText("Reminder set for: " + reminderTime);
        Toast.makeText(this, "success", Toast.LENGTH_SHORT).show();
    }
}