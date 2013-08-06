package com.nils.becker.fhplaner.service;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import com.nils.becker.fhplaner.R;
import com.nils.becker.fhplaner.model.Course;

/**
 * Created by nils on 8/5/13.
 */
public class CourseCursorAdapter extends CursorAdapter {

    public CourseCursorAdapter(Context context, Cursor c, boolean autoRequery) {
        super(context, c, autoRequery);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.class_item_row, viewGroup, false);
        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        Course currentCourse = new Course(cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getInt(5), cursor.getInt(6), cursor.getInt(7), cursor.getInt(8));
        TextView courseNameView = (TextView) view.findViewById(R.id.courseItemName);
        TextView courseRoomView = (TextView) view.findViewById(R.id.courseItemRoom);
        TextView courseTimeView = (TextView) view.findViewById(R.id.courseItemTime);
        View courseTypeIndicatorView = view.findViewById(R.id.indicatorView);

        courseNameView.setText(currentCourse.getName());
        courseRoomView.setText(currentCourse.getRoomFormatted());
        courseTimeView.setText(currentCourse.timeSpan());
        courseTypeIndicatorView.setBackgroundColor(Course.colorForCourseType(currentCourse.getType()));
    }
}
