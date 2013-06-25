package com.nils.becker.fhplaner;

import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.PreferenceFragment;

import java.util.Set;
import java.util.TreeSet;

public class SettingsFragment extends PreferenceFragment implements SharedPreferences.OnSharedPreferenceChangeListener {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Load the preferences from an XML resource
        addPreferencesFromResource(R.xml.preferences);

        getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
        if (s.equals("pref_semester") || s.equals("pref_branch")) {
            String semester = sharedPreferences.getString("pref_semester", "1. Semester");
            String branch = sharedPreferences.getString("pref_branch", "Wirtschaftsinformatik");
            String newRessourceUrl = "http://nils-becker.com/fhg/schedule/student/20/" + Course.keyForCourseName(branch) + "/" + semester.substring(0, 1) + "/";
            sharedPreferences.edit().putString("ressourceURL", newRessourceUrl).commit();
        }
    }
}
