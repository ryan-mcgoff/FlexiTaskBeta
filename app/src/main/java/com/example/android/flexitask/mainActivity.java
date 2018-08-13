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
 * Created by Ryan Mcgoff (4086944), Jerry Kumar (3821971), Jaydin Mcmullan (9702973)
 * This class is the main class which acts as a container for all the fragments
 * It inflates an XML layout which contains a nav draw, it then programtically checks for when an item in this
 * nav draw is selected and switch the current fragment it is displaying with the new one.
 * The class also sets up an alarm anytime the user opens the App (this will later be done by {@link AppManager}
 */
public class mainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

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

        setAlarm();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        displayView(R.id.nav_tasks);

    }


        public void setAlarm(){


            //get notification time preference
            String alarmS = PreferenceManager.getDefaultSharedPreferences(this).getString("time", "08:00");

            String[] timeArray = alarmS.split(":");
            int hour = (Integer.parseInt(timeArray[0]));
            int min = (Integer.parseInt(timeArray[1]));

            Calendar todayC = Calendar.getInstance();

            Calendar timePickerC = (Calendar) todayC.clone();
            timePickerC.set(Calendar.HOUR_OF_DAY, hour);
            timePickerC.set(Calendar.MINUTE, min);

            // if the time is after or equal to the notification hour / min schedule alarm for the next day
            if (todayC.after(timePickerC)){
                timePickerC.add(Calendar.DAY_OF_YEAR, 1); //add day
                Log.e("alarm: ", "Alarm will schedule for tomorrow");
            }
            else {
                Log.e("alarm: ", "Alarm will schedule for today");
            }

            AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
            Intent intent = new Intent(this,AlertReceiver.class);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(this,1,intent,0);

            alarmManager.setExact(AlarmManager.RTC_WAKEUP,timePickerC.getTimeInMillis(),pendingIntent);

        }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        displayView(item.getItemId());

        return true;
    }


    /**Checks which fragment the user has cliked on in the in the navigation draw
     * and begins transitioning to that fragment inside MainActiivty's XML content Frame.
     * @param viewId is the ID of the fragment the user has clicked
    * */
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

        //closes drawer asfter it's made the switch
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.nav_draw);
        drawer.closeDrawer(GravityCompat.START);

    }

    /*Checks user colour preferences and changes UI*/
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
