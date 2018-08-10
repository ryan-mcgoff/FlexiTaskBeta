package com.example.android.flexitask;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.preference.PreferenceManager;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.util.Calendar;

/**
 * Created by rymcg on 21/07/2018.
 */

public class mainTest extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private NotificationManagerCompat notificationManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.maintest);

        Log.e("main: ","MAIN YO");




        notificationManager = NotificationManagerCompat.from(this);

        //testNotification();
        //startAlarm();


        //startAlarm();

        //startssAlarm();

        setAlarm();




        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //TimelineFragmentsContainer t = new TimelineFragmentsContainer();
        //getSupportFragmentManager().beginTransaction().add(R.id.content_frame,t).commit();

        displayView(R.id.nav_tasks);

        //alarm manager


    }

    private void startssAlarm(){
        long alarmTime = Calendar.getInstance().getTimeInMillis();
        alarmTime += 60000;
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this,AlertReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this,1,intent,PendingIntent.FLAG_UPDATE_CURRENT);



        // every day at hour specified by user in settings
        Calendar calendar = Calendar.getInstance();


        calendar.setTimeInMillis(System.currentTimeMillis());
        ///get number

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


        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                AlarmManager.INTERVAL_DAY, pendingIntent);




    }


        public void setAlarm(){

            Log.e("setAlarm: ", "alarm d");

            long c = Calendar.getInstance().getTimeInMillis();
            c+= 15000;

            AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
            Intent intent = new Intent(this,AlertReceiver.class);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(this,1,intent,0);

            alarmManager.setExact(AlarmManager.RTC_WAKEUP,c,pendingIntent);

        }

    private void cancelsAlarm(){

        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this,AlertReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this,1,intent,0);

        alarmManager.cancel(pendingIntent);

    }





    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        displayView(item.getItemId());

        return true;
    }

    public void displayView(int viewId) {
        Fragment fragment = null;
        String title = "Tasks";

        Log.v(String.valueOf(viewId),"" );
        switch (viewId) {
            case R.id.nav_tasks:
                fragment = new TimelineFragmentsContainer();
                title  = "Tasks";

                break;
            case R.id.nav_history:
                fragment = new TaskHistoryFragment();
                title = "History";
                break;
            case R.id.nav_settings:
                fragment = new AppSettingsFragment();
                title = "History";
                break;

        }

        if (fragment != null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.content_frame,fragment).commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.nav_draw);
        drawer.closeDrawer(GravityCompat.START);

    }
    public void testNotification(){

        //channel 1 ignored on lower API <26
        Notification notification = new NotificationCompat.Builder(this,AppManager.CHANNEL_1_ID)
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
