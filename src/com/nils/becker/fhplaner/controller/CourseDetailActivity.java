package com.nils.becker.fhplaner.controller;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.nils.becker.fhplaner.R;
import com.nils.becker.fhplaner.model.Course;
import com.nils.becker.fhplaner.service.CourseService;

public class CourseDetailActivity extends Activity {

    private Course selectedCourse;
	private ListView listView;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.course_detail_layout);
        this.setupHeaderView();
        this.setupActionBar();
        this.setupListView();
    }

    private void setupListView() {
        this.listView = (ListView) findViewById(R.id.course_detail_list_view);
        this.adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
        this.adapter.addAll(new String[]{"this", "is", "just", "a", "test", "for", "some", "items", "in", "this", "listview"});
        this.listView.setAdapter(this.adapter);
        this.adapter.setNotifyOnChange(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        // getMenuInflater().inflate(R.menu.main, menu);
		return true;
    }

    private void setupHeaderView() {
        Intent intent = getIntent();
        this.selectedCourse = (Course) intent.getSerializableExtra("selectedCourse");
        View indicatorView = findViewById(R.id.detailHeaderView);
        TextView courseName = (TextView) findViewById(R.id.courseNameView);
//        TextView courseTime = (TextView) findViewById(R.id.courseTimeView);
        TextView courseRaum = (TextView) findViewById(R.id.courseRoomView);

        indicatorView.setBackgroundColor(CourseService.colorForCourseType(selectedCourse.getType()));
        courseName.setText(this.selectedCourse.getName());
        courseRaum.setText("Raum: " + CourseService.getFormattedRoom(selectedCourse.getRoom()));
//        courseTime.setText("Zeit: " + CourseService.getFormattedTimeSpan(selectedCourse.getStart(), selectedCourse.getEnd()) + " Uhr");
    }

    private void setupActionBar() {
        final ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
