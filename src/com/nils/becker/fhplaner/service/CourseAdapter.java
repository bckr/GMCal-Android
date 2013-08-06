package com.nils.becker.fhplaner.service;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.nils.becker.fhplaner.R;
import com.nils.becker.fhplaner.model.Course;

public class CourseAdapter extends ArrayAdapter<Course> {

	private LayoutInflater inflater;
	private int layoutRessourceId;
	private ArrayList<Course> courseList = new ArrayList<Course>();
	
	
	public CourseAdapter(Context context, int textViewResourceId) {
		super(context, textViewResourceId);
		this.inflater = ((Activity)context).getLayoutInflater();
		this.layoutRessourceId = textViewResourceId;
		this.courseList = new ArrayList<Course>();
	}
	
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = this.inflater.inflate(this.layoutRessourceId, parent, false);
		}
		Course currentCourse = (Course) this.getItem(position);
		TextView courseNameView = (TextView) convertView.findViewById(R.id.courseItemName);
		TextView courseRoomView = (TextView) convertView.findViewById(R.id.courseItemRoom);
		TextView courseTimeView = (TextView) convertView.findViewById(R.id.courseItemTime);
		View courseTypeIndicatorView = convertView.findViewById(R.id.indicatorView);
		
		courseNameView.setText(currentCourse.getName());
		courseRoomView.setText(currentCourse.getRoomFormatted());
		courseTimeView.setText(currentCourse.timeSpan());
		courseTypeIndicatorView.setBackgroundColor(Course.colorForCourseType(currentCourse.getType()));
		
		return convertView;
	}

	public void setCourseList(ArrayList<Course> courseList) {
		this.courseList = courseList;
		notifyDataSetChanged();
	}
	
	public ArrayList<Course> getCourseList() {
		return courseList;
	}
	
	@Override
	public Course getItem(int position) {
		return this.courseList.get(position);
	}

	@Override
	public int getCount() {
		return this.courseList.size();
	}
}
