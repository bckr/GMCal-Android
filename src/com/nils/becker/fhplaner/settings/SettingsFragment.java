package com.nils.becker.fhplaner.settings;

import android.content.ContentValues;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.preference.PreferenceFragment;

import com.nils.becker.fhplaner.model.Course;
import com.nils.becker.fhplaner.R;
import com.nils.becker.fhplaner.service.ScheduleDBA;
import com.nils.becker.fhplaner.service.CourseService;
import com.nils.becker.fhplaner.service.FetchCoursesTask;
import com.nils.becker.fhplaner.service.Fetcher;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

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
            /* TODO Deploy API (https://github.com/bckr/GMCal-API) to production server, as this backend does not work anymore */
            String newRessourceUrl = "http://nils-becker.com/fhg/schedule/student/20/" + CourseService.keyForCourseName(branch) + "/" + semester.substring(0, 1) + "/";
            sharedPreferences.edit().putString("ressourceURL", newRessourceUrl).commit();

            new FetchCoursesTask(new Fetcher() {
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
            }).execute(newRessourceUrl);
        }
    }

    private void persistSchedule(ArrayList<Course> courseList) {

        ScheduleDBA dbHelper = new ScheduleDBA(this.getActivity());
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.beginTransaction();

        // delete all previous entries
        db.delete(ScheduleDBA.COURSE_TABLE_NAME, null, null);

        ContentValues content = new ContentValues();
        for(Course c : courseList) {
            content.put(ScheduleDBA.KEY_ABBREVIATION, c.getAbbreviation());
            content.put(ScheduleDBA.KEY_NAME, c.getName());
            content.put(ScheduleDBA.KEY_TYPE, c.getType());
            content.put(ScheduleDBA.KEY_LECTURER_SHORT, c.getLecturer().getDozentkuerzel());
            content.put(ScheduleDBA.KEY_DAY, c.getDay());
            content.put(ScheduleDBA.KEY_START, c.getStart());
            content.put(ScheduleDBA.KEY_END, c.getEnd());
            content.put(ScheduleDBA.KEY_ROOM, c.getRoom());
            db.insert(ScheduleDBA.COURSE_TABLE_NAME, null, content);
        }

        db.setTransactionSuccessful();
        db.endTransaction();
        SharedPreferences sharedPreferences = getPreferenceScreen().getSharedPreferences();

        /* FIXME: This feels very wrong! */
        sharedPreferences.edit().putBoolean("dbupdate", !sharedPreferences.getBoolean("dbupdate", false)).commit();
    }
}
