package com.nils.becker.fhplaner.controller;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.widget.TextView;

import com.nils.becker.fhplaner.R;
import com.nils.becker.fhplaner.model.Course;

public class ClassDetailActivity extends Activity {

	final String debugPrefix = "Debug ->";
	private Course course;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.class_detail_activity);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        Intent intent = getIntent();
		this.course = (Course) intent.getSerializableExtra("selectedCourse");
		
		TextView courseName = (TextView) findViewById(R.id.courseNameView);
		TextView courseTime = (TextView) findViewById(R.id.courseTimeView);
		TextView courseRaum = (TextView) findViewById(R.id.courseRoomView);
        
		courseName.setText(this.course.getName());
		courseTime.setText("von: " + this.course.getStart() + " bis: " + this.course.getEnd());
		courseRaum.setText("Raum: " + Integer.toString(this.course.getRoom()));
		
		return true;
    }
}
