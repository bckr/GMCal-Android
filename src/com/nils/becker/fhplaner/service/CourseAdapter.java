package com.nils.becker.fhplaner.service;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.nils.becker.fhplaner.R;
import com.nils.becker.fhplaner.model.Course;
import com.nils.becker.fhplaner.model.RowItem;
import com.nils.becker.fhplaner.model.RowSection;

public class CourseAdapter extends ArrayAdapter<RowItem> {

	private LayoutInflater inflater;
	private int rowLayoutRessourceId;
	private int sectionLayoutRessourceId;
    private Cursor cursor;
    private ArrayList<RowItem> dataSource;
    private int sectionCount = 0;
    private static String[] daysOfWeek = {"Sonntag", "Montag", "Dienstag", "Mittwoch", "Donnerstag", "Freitag", "Samstag"};


    public CourseAdapter(Context context, int textViewResourceId, int sectionLayoutRessourceId, Cursor cursor) {
		super(context, textViewResourceId);
		this.inflater = ((Activity)context).getLayoutInflater();
		this.rowLayoutRessourceId = textViewResourceId;
        this.sectionLayoutRessourceId = sectionLayoutRessourceId;
        this.cursor = cursor;
        this.setupDataSource();
	}

    private void setupDataSource() {
        this.dataSource = new ArrayList<RowItem>();
        int lastDay = -1;

        while (cursor.moveToNext()) {
            if (lastDay != cursor.getInt(5)) {
                lastDay = cursor.getInt(5);
                this.sectionCount += 1;
                this.dataSource.add(new RowSection(this.daysOfWeek[cursor.getInt(cursor.getColumnIndex(ScheduleDBA.KEY_DAY))]));
            }
            this.dataSource.add(new Course(cursor));
        }

        if (this.sectionCount == 1) {
            this.dataSource.remove(0);
        }

        this.sectionCount = 0;
    }

    @Override
    public boolean isEnabled(int position) {
        return this.dataSource.get(position) instanceof Course;
    }

    public void changeCursor(Cursor cursor) {
        this.cursor.close();
        this.cursor = cursor;
        this.setupDataSource();
        this.notifyDataSetChanged();
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        return this.dataSource.get(position) instanceof Course ? 0 : 1;
    }

    @Override
	public View getView(int position, View convertView, ViewGroup parent) {

        if (this.dataSource.get(position) instanceof Course) {
            TextView courseNameView;
            TextView courseRoomView;
            TextView courseTimeView;
            TextView courseLecturerView;
            TextView courseTypeTextView;
            View courseTypeIndicatorView;
            View courseTimelineView;

            if (convertView == null) {
                convertView = this.inflater.inflate(this.rowLayoutRessourceId, parent, false);

                courseNameView = (TextView) convertView.findViewById(R.id.courseNameTextView);
                courseRoomView = (TextView) convertView.findViewById(R.id.courseRoomTextView);
                courseTimeView = (TextView) convertView.findViewById(R.id.courseTimespanTextView);
                courseLecturerView = (TextView) convertView.findViewById(R.id.courseLecturerTextView);
                courseTypeTextView = (TextView) convertView.findViewById(R.id.courseTypeTextView);
                courseTypeIndicatorView = convertView.findViewById(R.id.courseTypeIndicatorView);
                courseTimelineView= convertView.findViewById(R.id.courseTimelineView);

                // Tag views for reuse
                convertView.setTag(R.id.courseNameTextView, courseNameView);
                convertView.setTag(R.id.courseRoomTextView, courseRoomView);
                convertView.setTag(R.id.courseTimespanTextView, courseTimeView);
                convertView.setTag(R.id.courseLecturerTextView, courseLecturerView);
                convertView.setTag(R.id.courseTypeTextView, courseTypeTextView);
                convertView.setTag(R.id.courseTypeIndicatorView, courseTypeIndicatorView);
                convertView.setTag(R.id.courseTimelineView, courseTimelineView);
            } else {
                courseNameView = (TextView) convertView.getTag(R.id.courseNameTextView);
                courseRoomView = (TextView) convertView.getTag(R.id.courseRoomTextView);
                courseTimeView = (TextView) convertView.getTag(R.id.courseTimespanTextView);
                courseLecturerView = (TextView) convertView.getTag(R.id.courseLecturerTextView);
                courseTypeTextView = (TextView) convertView.getTag(R.id.courseTypeTextView);
                courseTypeIndicatorView = (View) convertView.getTag(R.id.courseTypeIndicatorView);
                courseTimelineView = (View) convertView.getTag(R.id.courseTimelineView);
            }

            Course currentCourse = (Course) this.getItem(position);

            courseNameView.setText(currentCourse.getName());
            courseRoomView.setText(CourseService.getFormattedRoom(currentCourse.getRoom()));
            courseTimeView.setText(CourseService.getFormattedTimeSpan(currentCourse.getStart(), currentCourse.getEnd()));
            // TODO set real lecturer name
            courseLecturerView.setText("Victor");
            courseTypeTextView.setText(currentCourse.getType().substring(0, 1));
            courseTypeIndicatorView.setBackgroundColor(CourseService.colorForCourseType(currentCourse.getType()));
            courseTimelineView.setBackgroundColor(Color.parseColor("#CCCCCC"));
        } else {
            TextView headerTextView;
            if (convertView == null) {
                convertView = this.inflater.inflate(this.sectionLayoutRessourceId, parent, false);
                headerTextView = (TextView) convertView.findViewById(R.id.list_header_title);
                convertView.setTag(R.id.list_header_title, headerTextView);
            } else {
                headerTextView = (TextView) convertView.getTag(R.id.list_header_title);
            }

            RowSection currentSection = (RowSection) this.getItem(position);
            headerTextView.setText(currentSection.getTitle());
        }

		return convertView;
	}

	@Override
	public RowItem getItem(int position) {
		return this.dataSource.get(position);
	}

    @Override
	public int getCount() {
		return this.dataSource.size();
	}
}
