package com.lucreziagrassi.androidapp.main;

import android.os.Bundle;
import android.preference.PreferenceFragment;

import com.lucreziagrassi.androidapp.R;


public class SettingsFragment extends PreferenceFragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Load the preferences from an XML resource
        addPreferencesFromResource(R.xml.preferences);
    }
}