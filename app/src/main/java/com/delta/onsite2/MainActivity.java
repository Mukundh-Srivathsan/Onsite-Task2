package com.delta.onsite2;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private TimePicker timePicker;

    private Button addEvent;

    private Calendar calendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d(TAG, "onCreate: Started");

        Intent intent = getIntent();

        boolean disable = intent.getBooleanExtra("disable", false);

        createNotifChannel();

        timePicker = findViewById(R.id.time);

        addEvent = findViewById(R.id.doneButton);

        if(disable) {

            Log.d(TAG, "In Disable intent");

            Intent Nintent = new Intent(getApplicationContext() , RemainderBroadcast.class);

            PendingIntent pendingIntent
                    = PendingIntent.getBroadcast(getApplicationContext(), 1,
                    Nintent, PendingIntent.FLAG_UPDATE_CURRENT);

            AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

            alarmManager.cancel(pendingIntent);

            Toast.makeText(this, "Remainder Canceled", Toast.LENGTH_SHORT).show();

            finish();
        }else {

            addEvent.setOnClickListener(v -> {

                calendar = Calendar.getInstance();

                calendar.set(Calendar.HOUR_OF_DAY, timePicker.getHour());
                calendar.set(Calendar.MINUTE, timePicker.getMinute());
                calendar.set(Calendar.SECOND, 0);
                calendar.set(Calendar.MILLISECOND, 0);

                earlyRem();

                rem();


                Toast.makeText(v.getContext(), "Remainder Set", Toast.LENGTH_SHORT).show();

            });
        }
    }

    private void createNotifChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel1 = new NotificationChannel(
                    "Channel_1",
                    "Remainder",
                    NotificationManager.IMPORTANCE_HIGH
            );

            channel1.setDescription("This is the Remainder channel");

            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel1);
        }
    }

    private void earlyRem()
    {

        Intent intent = new Intent(MainActivity.this, EarlyBroadcastReciever.class);

        PendingIntent pendingIntent
                = PendingIntent.getBroadcast(MainActivity.this, 1,
                intent, PendingIntent.FLAG_UPDATE_CURRENT);

        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        long time = calendar.getTimeInMillis() - 120000;

        Log.d(TAG, "Time in early rem: " + time);

        alarmManager.setExact(AlarmManager.RTC_WAKEUP,
                time,
                pendingIntent);
    }

    private void rem()
    {
        Intent intent = new Intent(getApplicationContext(), RemainderBroadcast.class);

        PendingIntent pendingIntent
                = PendingIntent.getBroadcast(getApplicationContext(), 1,
                intent, PendingIntent.FLAG_UPDATE_CURRENT);

        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        Log.d(TAG, "Time in rem: " + calendar.getTimeInMillis());

        alarmManager.setExact(AlarmManager.RTC_WAKEUP,
                calendar.getTimeInMillis(),
                pendingIntent);
    }
}