package com.nils.becker.fhplaner.settings;

import android.content.ContentValues;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.preference.PreferenceFragment;

import com.nils.becker.fhplaner.model.Course;
import com.nils.becker.fhplaner.R;
import com.nils.becker.fhplaner.service.FetchScheduleTask;
import com.nils.becker.fhplaner.service.ScheduleDBA;
import com.nils.becker.fhplaner.service.ScheduleFetcher;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

public class SettingsFragment extends PreferenceFragment implements SharedPreferences.OnSharedPreferenceChangeListener, ScheduleFetcher{

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
            new FetchScheduleTask(this).execute(newRessourceUrl);
        }
    }

    @Override
    public void responseLoaded(JSONArray response) {

        ArrayList<Course> courseList = new ArrayList<Course>();
        for (int i = 0; i < response.length(); i++) {
            try {
                Course currentCourse = new Course(response.getJSONObject(i));
                courseList.add(currentCourse);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        persistSchedule(courseList);
    }

    private void persistSchedule(ArrayList<Course> courseList) {

        ScheduleDBA dbHelper = new ScheduleDBA(this.getActivity());
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.beginTransaction();

        // delete all previous entries
        db.delete("course", null, null);

        ContentValues content = new ContentValues();
        for(Course c : courseList) {
            content.put("abbreviation", c.getAbbreviation());
            content.put("name", c.getName());
            content.put("type", c.getType());
            content.put("lecturer_short", c.getLecturer_short());
            content.put("day", c.getDay());
            content.put("start", c.getStart());
            content.put("end", c.getEnd());
            content.put("room", c.getRoom());
            db.insert("Course", null, content);
        }

        db.setTransactionSuccessful();
        db.endTransaction();
        SharedPreferences sharedPreferences = getPreferenceScreen().getSharedPreferences();
        sharedPreferences.edit().putBoolean("dbupdate", !sharedPreferences.getBoolean("dbupdate", false)).commit();
    }
}
