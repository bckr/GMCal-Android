package com.nils.becker.fhplaner;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class ScheduleListView extends Activity implements ScheduleFetcher, SharedPreferences.OnSharedPreferenceChangeListener{

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
        System.out.println("--> onCreate");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_schedule_list);

        ActionBar actionBar = getActionBar();
        actionBar.setDisplayShowHomeEnabled(true);

		new FetchScheduleTask(this).execute("http://nils-becker.com/fhg/schedule/lecturer/FW");

		this.listView = (ListView) findViewById(R.id.listview);

		adapter = new CourseAdapter(this, R.layout.class_item_row);
		listView.setAdapter(adapter);
		
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				Intent showNextActivityIntent = new Intent(ScheduleListView.this, ClassDetailActivity.class);
				Course selectedCourse = adapter.getItem(arg2);
				showNextActivityIntent.putExtra("selectedCourse", selectedCourse);
				startActivity(showNextActivityIntent);
			}
			
		});

        PreferenceManager.getDefaultSharedPreferences(this).registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
        mFeatureId = featureId;
        mItem = item;

        switch (item.getItemId()){
            case R.id.action_refresh:
                System.out.println("refresh");
                break;
            case R.id.action_settings:
                System.out.println("settings");
                Intent showNextActivityIntent = new Intent(ScheduleListView.this, SettingsActivity.class);
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
}
