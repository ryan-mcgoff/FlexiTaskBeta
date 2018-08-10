package com.example.android.flexitask;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.preference.PreferenceManager;
import android.util.Log;

import java.util.Calendar;

/**
 * Created by rymcg on 9/08/2018.
 **/
public class DailyNotificationReminder extends AppCompatActivity {

    public void startAlarm(){

        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this,AlertReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this,1,intent,0);


        // every day at hour specified by user in settings
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());

        //get number
        String alarmS = PreferenceManager.getDefaultSharedPreferences(this).getString("some_time", "08:00");

        String[] timeArray = alarmS.split(":");
        int hour = (Integer.parseInt(timeArray[0]));
        int min = (Integer.parseInt(timeArray[1]));

        // if it's after or equal 9 am schedule for next day
        if (Calendar.getInstance().get(Calendar.HOUR_OF_DAY) >= hour) {
            Log.e("Hour Now : ", String.valueOf(Calendar.getInstance().get(Calendar.HOUR_OF_DAY)+
                    " Hour Set"+ String.valueOf(hour)));
            calendar.add(Calendar.DAY_OF_YEAR, 1); //add day
        }
        else{
            Log.e("alarm: ", "Alarm will schedule for today!");
        }

        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, min);

        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                AlarmManager.INTERVAL_DAY, pendingIntent);
    }

    private void cancelAlarm(){

        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this,AlertReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this,1,intent,0);

        alarmManager.cancel(pendingIntent);

    }
}
