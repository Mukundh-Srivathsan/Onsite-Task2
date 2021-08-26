package com.delta.onsite2;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;


public class RemainderBroadcast extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context,
                "Channel_1")
                .setSmallIcon(R.drawable.timer_icon)
                .setContentTitle("Remainder")
                .setContentText("It is time to do it")
                .setPriority(NotificationCompat.PRIORITY_HIGH);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);

        notificationManager.notify(100, builder.build());

    }

}
