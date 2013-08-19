package com.nils.becker.fhplaner.controller;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.nils.becker.fhplaner.R;
import com.nils.becker.fhplaner.model.Course;
import com.nils.becker.fhplaner.service.CourseService;

public class CourseDetailActivity extends Activity {

	final String debugPrefix = "Debug ->";
	private Course selectedCourse;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.course_detail_layout);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        // getMenuInflater().inflate(R.menu.main, menu);
        setupView();
		setupActionBar();
		return true;
    }

    private void setupView() {
        Intent intent = getIntent();
        this.selectedCourse = (Course) intent.getSerializableExtra("selectedCourse");

        View indicatorView = findViewById(R.id.courseTypeIndicatorView);
        TextView courseName = (TextView) findViewById(R.id.courseNameView);
        TextView courseTime = (TextView) findViewById(R.id.courseTimeView);
        TextView courseRaum = (TextView) findViewById(R.id.courseRoomView);

        indicatorView.setBackgroundColor(CourseService.colorForCourseType(selectedCourse.getType()));
        courseName.setText(this.selectedCourse.getName());
        courseRaum.setText("Raum: " + CourseService.getFormattedRoom(selectedCourse.getRoom()));
        courseTime.setText("Zeit: " + CourseService.getFormattedTimeSpan(selectedCourse.getStart(), selectedCourse.getEnd()) + " Uhr");
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
