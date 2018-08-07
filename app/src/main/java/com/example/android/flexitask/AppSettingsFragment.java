package com.example.android.flexitask;

import android.os.Bundle;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;


import com.example.android.flexitask.R;

/**
 * Created by rymcg on 6/08/2018.
 */

public class AppSettingsFragment extends PreferenceFragmentCompat {


    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        addPreferencesFromResource(R.xml.preferences);
    }
}
