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
import java.util.Random;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private TimePicker timePicker;

    private Button addEvent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d(TAG, "onCreate: Started");

        createNotifChannel();

        timePicker = findViewById(R.id.time);

        addEvent = findViewById(R.id.doneButton);

        addEvent.setOnClickListener(v -> {

            Calendar calendar = Calendar.getInstance();

            calendar.set(Calendar.HOUR_OF_DAY, timePicker.getHour());
            calendar.set(Calendar.MINUTE, timePicker.getMinute());
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MILLISECOND, 0);

            Intent intent = new Intent(MainActivity.this, RemainderBroadcast.class);

            Random r = new Random();

            int id = r.ints(0, 1000).findFirst().getAsInt();

            intent.putExtra("id", id);

            PendingIntent pendingIntent
                    = PendingIntent.getBroadcast(MainActivity.this, 0,
                    intent, 0);

            AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

            Log.d(TAG, "Time: " + calendar.getTimeInMillis());

            alarmManager.set(AlarmManager.RTC_WAKEUP,
                    calendar.getTimeInMillis(),
                    pendingIntent);

            Toast.makeText(v.getContext(), "Remainder Set", Toast.LENGTH_SHORT).show();

        });
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


    private long power(long a, long b) {
        if (b == 0)
            return 1;

        return a * power(a, b - 1);
    }
}