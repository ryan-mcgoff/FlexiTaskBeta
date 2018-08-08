package com.example.android.flexitask;

import android.app.Notification;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;

/**
 * Created by rymcg on 8/08/2018.
 */

public class AlertReceiver extends BroadcastReceiver {

    private NotificationManagerCompat notificationManager;


    @Override
    public void onReceive(Context context, Intent intent) {

        notificationManager = NotificationManagerCompat.from(context);
        //called when alarm is fired, do this....
        //channel 1 ignored on lower API <26
        Notification notification = new NotificationCompat.Builder(context,AppManager.CHANNEL_1_ID)
                .setSmallIcon(R.drawable.oval_shape)
                .setContentTitle("title in main test")
                .setContentText("message in main test")
                .setPriority(NotificationCompat.PRIORITY_LOW)
                .setCategory(NotificationCompat.CATEGORY_REMINDER)
                .build();

        //overwrites old notification id 1
        notificationManager.notify(1,notification);
    }
}
