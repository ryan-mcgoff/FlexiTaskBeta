package com.example.android.flexitask;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by rymcg on 10/08/2018.
 */

public class NotificationHelper extends ContextWrapper {
    public static final String CHANNELID = "channel1ID";
    public static final String CHANNELNAME = "channel1";

    private NotificationManager mNotificationManager;


    public NotificationHelper(Context base) {
        super(base);
        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.O) {
            createChannels();
        }
    }

    @TargetApi(Build.VERSION_CODES.O)
    public void createChannels(){
        NotificationChannel notificationChannel = new NotificationChannel(CHANNELID,CHANNELNAME,
                NotificationManager.IMPORTANCE_DEFAULT);
        notificationChannel.enableLights(true);
        notificationChannel.enableVibration(true);
        notificationChannel.setLightColor(R.color.colorPrimary);
        notificationChannel.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);

        getNotificationManager().createNotificationChannel(notificationChannel);
    }

    public NotificationManager getNotificationManager(){

        if (mNotificationManager==null){
            mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        }

        return mNotificationManager;
    }

    public NotificationCompat.Builder getChannel(String title, ArrayList <String> message){



        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(getApplicationContext(),CHANNELID)
                .setContentTitle(title)
                .setContentText("contentText")
                .setSmallIcon(R.drawable.ic_launcher_background);
        NotificationCompat.InboxStyle inboxStyle =
                new NotificationCompat.InboxStyle();


// Sets a title for the Inbox in expanded layout
        inboxStyle.setBigContentTitle("Schedule:");

        if(message.size()==0){
            Log.e("alarm: ", "Nothing");
            inboxStyle.addLine("No upcoming events!");
        }
        else {

            for (int i = 0; i < message.size(); i++) {
                if (message.get(i) != null) {
                    inboxStyle.addLine(message.get(i));
                }
            }
        }
// Moves the expanded layout object into the notification object.
        mBuilder.setStyle(inboxStyle);

        return mBuilder;
        //set Icon Later

    }
}
