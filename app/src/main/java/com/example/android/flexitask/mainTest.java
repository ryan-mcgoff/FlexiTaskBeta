package com.example.android.flexitask;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
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
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toolbar;

import java.util.Calendar;

/**
 * Created by rymcg on 21/07/2018.
 */

public class mainTest extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private NotificationManagerCompat notificationManager;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private android.support.v7.widget.Toolbar toolbar;


    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        actionBarDrawerToggle.syncState();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.maintest);

        Log.e("main: ","MAIN YO");

        notificationManager = NotificationManagerCompat.from(this);

        toolbar = findViewById(R.id.toolbar);
        colorSwitch();
        setSupportActionBar(toolbar);

        drawerLayout = (DrawerLayout) findViewById(R.id.nav_draw);

        actionBarDrawerToggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,
                R.string.navigation_drawer_open,R.string.navigation_drawer_close);

        drawerLayout.addDrawerListener(actionBarDrawerToggle);








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


        public void setAlarm(){

            Log.e("setAlarm: ", "alarm d");


            //get number
            String alarmS = PreferenceManager.getDefaultSharedPreferences(this).getString("some_time", "08:00");

            String[] timeArray = alarmS.split(":");
            int hour = (Integer.parseInt(timeArray[0]));
            int min = (Integer.parseInt(timeArray[1]));


            Calendar todayC = Calendar.getInstance();

            Calendar timePickerC = (Calendar) todayC.clone();
            timePickerC.set(Calendar.HOUR_OF_DAY, hour);
            timePickerC.set(Calendar.MINUTE, min);

            // if it's after or equal to the notification hour / min schedule for next day
            if (todayC.after(timePickerC)){
                timePickerC.add(Calendar.DAY_OF_YEAR, 1); //add day
                Log.e("alarm: ", "Alarm will schedule for tomorrow!");
            }
            else {
                Log.e("alarm: ", "Alarm will schedule for today!");
            }

            AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
            Intent intent = new Intent(this,AlertReceiver.class);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(this,1,intent,0);

            alarmManager.setExact(AlarmManager.RTC_WAKEUP,timePickerC.getTimeInMillis(),pendingIntent);

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

    private void colorSwitch() {
        String colourSetting = PreferenceManager.getDefaultSharedPreferences(getApplicationContext())
                .getString("color_preference_key", "OCOLOUR");

        switch (colourSetting) {
            case ("DCOLOUR"):
                toolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimaryD));

                break;

            case ("PCOLOUR"):

                toolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimaryP));

                break;

            case ("TCOLOUR"):
                toolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimaryT));

                break;
            default:
                toolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        }
    }



    }
