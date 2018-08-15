package com.lucreziagrassi.androidapp.main;

import android.arch.persistence.room.Database;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.preference.PreferenceScreen;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lucreziagrassi.androidapp.R;
import com.lucreziagrassi.androidapp.db.DatabaseManager;
import com.lucreziagrassi.androidapp.db.User;

import java.util.Map;


public class SettingsFragment extends PreferenceFragment implements SharedPreferences.OnSharedPreferenceChangeListener {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle(R.string.settings_fragment_name);

        // Set actual values
        User user = DatabaseManager.getDatabase().getUserDao().getUser();

        SharedPreferences prefs = getPreferenceManager().getSharedPreferences();
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("university", user.getUniversity());
        editor.putString("corso", user.getCourse());
        editor.putString("avg_type", user.getAvg_type_String());
        editor.putString("cfu", "" + user.getCFU());
        editor.putString("badge_number", "" + user.getBadge_number());

        editor.apply();

        // Load the preferences from an XML resource
        addPreferencesFromResource(R.xml.preferences);

        findPreference("university").setSummary(user.getUniversity() + "");
        findPreference("corso").setSummary(user.getCourse() + "");
        findPreference("cfu").setSummary(user.getCFU() + "");
        findPreference("badge_number").setSummary(user.getBadge_number() + "");
        findPreference("avg_type").setSummary(user.getAvg_type_String());

        getPreferenceManager().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        view.setBackgroundColor(getResources().getColor(android.R.color.white));
        return view;
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
        User user = DatabaseManager.getDatabase().getUserDao().getUser();

        user.setUniversity(sharedPreferences.getString("university", user.getUniversity()));
        user.setCourse(sharedPreferences.getString("corso", user.getCourse()));
        user.setCFU(Integer.parseInt(sharedPreferences.getString("cfu", "" + user.getCFU())));
        user.setBadge_number(sharedPreferences.getString("badge_number", "" + user.getBadge_number()));
        user.setAvg_type_String(sharedPreferences.getString("avg_type", user.getAvg_type_String()));

        DatabaseManager.getDatabase().getUserDao().setUser(user);

        findPreference("university").setSummary(user.getUniversity() + "");
        findPreference("corso").setSummary(user.getCourse() + "");
        findPreference("cfu").setSummary(user.getCFU() + "");
        findPreference("badge_number").setSummary(user.getBadge_number() + "");
        findPreference("avg_type").setSummary(user.getAvg_type_String());
    }
}