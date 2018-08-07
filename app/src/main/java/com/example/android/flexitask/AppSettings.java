package com.example.android.flexitask;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by rymcg on 1/08/2018.
 */

public class AppSettings {
    SharedPreferences mSharedPreferences;

    public  AppSettings(Context context){
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public String getPreference(){
        return mSharedPreferences.getString("storage","default");
    }
    public void setSharedPreferences(String value){
        mSharedPreferences
                .edit()
                .putString("storange",value)
                .apply();
    }
}
