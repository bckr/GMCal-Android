package com.nils.becker.fhplaner.controller;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import org.json.JSONArray;
import org.json.JSONException;

import android.app.ActionBar;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.ContentObserver;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.nils.becker.fhplaner.R;
import com.nils.becker.fhplaner.service.CourseAdapter;
import com.nils.becker.fhplaner.service.CourseCursorAdapter;
import com.nils.becker.fhplaner.service.FetchScheduleTask;
import com.nils.becker.fhplaner.service.ScheduleFetcher;
import com.nils.becker.fhplaner.service.ScheduleOpenHelper;
import com.nils.becker.fhplaner.model.Course;
import com.nils.becker.fhplaner.settings.SettingsActivity;

public class ScheduleListViewActivity extends Activity implements ActionBar.OnNavigationListener, SharedPreferences.OnSharedPreferenceChangeListener {

	private ArrayList<Course> list = new ArrayList<Course>();
	private CourseCursorAdapter adapter;
	private ListView listView;
    private int mFeatureId;
    private MenuItem mItem;
    private ScheduleOpenHelper dbHelper;

    @Override
	public boolean onCreateOptionsMenu(Menu menu) {
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.main, menu);
	    return true;
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_list);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        sharedPreferences.registerOnSharedPreferenceChangeListener(this);
        // sharedPreferences.edit().clear().commit();

        if (isFirstStartup()) {
            Intent switchToSettingsActivity = new Intent(ScheduleListViewActivity.this, SettingsActivity.class);
            startActivity(switchToSettingsActivity);
        }

        setupActionBar();
        setupListView();
    }

    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
        mFeatureId = featureId;
        mItem = item;

        switch (item.getItemId()){
    /*        case R.id.action_refresh:
                System.out.println("refresh");
                break;*/
            case R.id.action_settings:
                System.out.println("settings");
                Intent showNextActivityIntent = new Intent(ScheduleListViewActivity.this, SettingsActivity.class);
                startActivity(showNextActivityIntent);
                break;
        }

        return super.onMenuItemSelected(featureId, item);
    }

    @Override
    public boolean onNavigationItemSelected(int i, long l) {

        Log.d("debug", "switched view" + i);

        switch (i) {
            case 0:
                int dayOfWeek = Calendar.getInstance().get(Calendar.DAY_OF_WEEK) - 2;
                this.adapter.changeCursor(this.dbHelper.getCoursesForDayOfWeek(dayOfWeek));
                break;
            case 1:
                this.adapter.changeCursor(this.dbHelper.getAllCourses());
                break;
        }

        return false;
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
        if (s.equals("dbupdate")) {
            Log.d("debug", "reload list view");
            this.adapter.changeCursor(this.dbHelper.getAllCourses());
        }
    }

    private void setupListView() {
        this.listView = (ListView) findViewById(R.id.listview);
        this.dbHelper = new ScheduleOpenHelper(this);
        Cursor cursor = this.dbHelper.getAllCourses();

        this.adapter = new CourseCursorAdapter(this, cursor, true);
        listView.setAdapter(this.adapter);

        listView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                Intent showNextActivityIntent = new Intent(ScheduleListViewActivity.this, CourseDetailActivity.class);
                Course selectedCourse = (Course) adapter.getItem(arg2);
                showNextActivityIntent.putExtra("selectedCourse", selectedCourse);
                startActivity(showNextActivityIntent);
            }

        });
    }

    private void setupActionBar() {
        final ActionBar actionBar = getActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);

        SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE dd. MMM");
        Calendar cal = Calendar.getInstance();

        final String today = dateFormat.format(cal.getTime());
        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);

        dateFormat = new SimpleDateFormat("EEE. dd");
        final String startOfWeek = dateFormat.format(cal.getTime());
        cal.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY);
        final String endOfWeek= dateFormat.format(cal.getTime());

        final String[] dropdownValues = {today, startOfWeek + " - " + endOfWeek};

        ArrayAdapter<String> menuListAdapter = new ArrayAdapter<String>(actionBar.getThemedContext(),
                android.R.layout.simple_spinner_item, android.R.id.text1,
                dropdownValues);

        menuListAdapter .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        actionBar.setListNavigationCallbacks(menuListAdapter , this);
    }

    private Boolean isFirstStartup() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        return sharedPreferences.getString("ressourceURL", "").equals("");
    }


}
