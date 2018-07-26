package com.example.android.flexitask;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;

/**
 * Created by rymcg on 21/07/2018.
 */

public class mainTest extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.maintest);



        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //TimelineFragmentsContainer t = new TimelineFragmentsContainer();
        //getSupportFragmentManager().beginTransaction().add(R.id.content_frame,t).commit();

        displayView(R.id.nav_tasks);

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        displayView(item.getItemId());
        Log.v(String.valueOf("te"),"MainTest" );
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
                fragment = new TaskHistoryFragment();
                title = "History";
                break;

        }

        if (fragment != null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.content_frame,fragment).commit();
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.nav_draw);
        drawer.closeDrawer(GravityCompat.START);


    }

    }
