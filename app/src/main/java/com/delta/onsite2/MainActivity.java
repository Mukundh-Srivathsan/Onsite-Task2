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
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private EditText eventName;

    private NumberPicker hours;
    private NumberPicker minutes;

    private Button addEvent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d(TAG, "onCreate: Started");

        createNotifChannel();

        eventName = findViewById(R.id.eventName);

        hours = findViewById(R.id.hourPicker);
        minutes = findViewById(R.id.minutePicker);

        hours.setMinValue(0);
        hours.setMaxValue(23);

        minutes.setMinValue(0);
        minutes.setMaxValue(59);

        addEvent = findViewById(R.id.doneButton);

        addEvent.setOnClickListener(v -> {

            String event = eventName.getText().toString();

            if (event.length() > 0) {

                Intent intent = new Intent(MainActivity.this, RemainderBroadcast.class);

                intent.putExtra("event", event);

                PendingIntent pendingIntent
                        = PendingIntent.getBroadcast(MainActivity.this, 0,
                        intent, 0);

                AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

                int hour = hours.getValue();

                int min = minutes.getValue();

                long time = (hour*36*power(10,5))+(min*6*power(10, 4));

                alarmManager.set(AlarmManager.RTC_WAKEUP,
                        time,
                        pendingIntent);

                Toast.makeText(v.getContext(), "Remainder Set", Toast.LENGTH_SHORT).show();

                eventName.setText("");
            }
            else
                Toast.makeText(v.getContext(), "Enter Valid Event Name", Toast.LENGTH_SHORT).show();
        });
    }

    private void createNotifChannel()
    {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
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