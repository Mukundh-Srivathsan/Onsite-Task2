package com.delta.onsite2;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class EarlyBroadcastReciever extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent notificationIntent = new Intent(context, MainActivity.class);

        notificationIntent.putExtra("disable", true);

        PendingIntent dismissIntent = PendingIntent.getActivity(context, 0, notificationIntent, 0);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "Channel_1")
                .setSmallIcon(R.drawable.timer_icon)
                .setContentTitle("Remainder")
                .setContentText("It is time to do it")
                .addAction(R.drawable.timer_icon, "Cancel timer", dismissIntent)
                .setPriority(NotificationCompat.PRIORITY_HIGH);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);

        notificationManager.notify(200, builder.build());
    }
}
