package com.nils.becker.fhplaner.controller;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import org.json.JSONArray;
import org.json.JSONException;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.format.DateFormat;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.nils.becker.fhplaner.R;
import com.nils.becker.fhplaner.helper.CourseAdapter;
import com.nils.becker.fhplaner.helper.FetchScheduleTask;
import com.nils.becker.fhplaner.helper.ScheduleFetcher;
import com.nils.becker.fhplaner.model.Course;
import com.nils.becker.fhplaner.settings.SettingsActivity;

public class ScheduleListViewActivity extends Activity implements ScheduleFetcher, SharedPreferences.OnSharedPreferenceChangeListener, ActionBar.OnNavigationListener {

	private ArrayList<Course> list = new ArrayList<Course>();
	private CourseAdapter adapter;
	private ListView listView;
    private int mFeatureId;
    private MenuItem mItem;

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
        // sharedPreferences.edit().clear().commit();

        if (isFirstStartup()) {
            Intent switchToSettingsActivity = new Intent(ScheduleListViewActivity.this, SettingsActivity.class);
            startActivity(switchToSettingsActivity);
        }

        setupActionBar();

		new FetchScheduleTask(this).execute("http://nils-becker.com/fhg/schedule/lecturer/FW");

		this.listView = (ListView) findViewById(R.id.listview);

		adapter = new CourseAdapter(this, R.layout.class_item_row);
		listView.setAdapter(adapter);
		
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				Intent showNextActivityIntent = new Intent(ScheduleListViewActivity.this, ClassDetailActivity.class);
				Course selectedCourse = adapter.getItem(arg2);
				showNextActivityIntent.putExtra("selectedCourse", selectedCourse);
				startActivity(showNextActivityIntent);
			}
			
		});

        PreferenceManager.getDefaultSharedPreferences(this).registerOnSharedPreferenceChangeListener(this);
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
	public void responseLoaded(JSONArray response) {
        this.list = new ArrayList<Course>();
		for (int i = 0; i < response.length(); i++) {
			try {
				Course currentCourse = new Course(response.getJSONObject(i));
				this.list.add(currentCourse);
			} catch (JSONException e) {
				e.printStackTrace();
			}

		}
		this.adapter.setCourseList(this.list);
	}

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
        if (s.equals("ressourceURL")) {
            new FetchScheduleTask(this).execute(sharedPreferences.getString("ressourceURL", ""));
        }
    }

    @Override
    public boolean onNavigationItemSelected(int i, long l) {
        return false;
    }
}
