package com.nils.becker.fhplaner.model;

import android.database.Cursor;

import java.io.Serializable;

import org.json.JSONException;
import org.json.JSONObject;

@SuppressWarnings("serial")
public class Course implements Serializable, RowItem {

	private String abbreviation;
	private String name;
	private String type;
    private String lecturer_short;
	private int day;
	private int start;
	private int end;
	private int room;

	public Course(String abbr, String name, String type, String lecturer_short, int day, int start, int end, int room) {
		this.abbreviation = abbr;
		this.name = name;
		this.type = type;
		this.day = day;
		this.start = start;
		this.end = end;
		this.room = room;
        this.lecturer_short = lecturer_short;
	}

	public Course(JSONObject jsonObject) {
		try {
			this.abbreviation = jsonObject.getString("abbreviation");
			this.name = jsonObject.getString("name");
			this.type = jsonObject.getString("type");
            this.lecturer_short = jsonObject.getString("lecturer_short");
			this.day = (int) jsonObject.getDouble("day");
			this.start = (int) jsonObject.getDouble("start");
			this.end = (int) jsonObject.getDouble("end");
			this.room = (int) jsonObject.getDouble("room");
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

    public Course(Cursor cursor) {
        this.abbreviation = cursor.getString(cursor.getColumnIndex("abbreviation"));
        this.name = cursor.getString(cursor.getColumnIndex("name"));
        this.type = cursor.getString(cursor.getColumnIndex("type"));
        this.lecturer_short = cursor.getString(cursor.getColumnIndex("lecturer_short"));;
        this.day = cursor.getInt(cursor.getColumnIndex("day"));
        this.start = cursor.getInt(cursor.getColumnIndex("start"));
        this.end = cursor.getInt(cursor.getColumnIndex("end"));
        this.room = cursor.getInt(cursor.getColumnIndex("room"));
    }

    @Override
	public String toString() {
		return this.name + " from: " + this.start + " to: " + this.end + " in: " + this.room;
	}
	
	public String getAbbreviation() {
		return abbreviation;
	}

	public void setAbbreviation(String abbreviation) {
		this.abbreviation = abbreviation;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getDay() {
		return day;
	}

	public void setDay(int day) {
		this.day = day;
	}

	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public int getEnd() {
		return end;
	}

	public void setEnd(int end) {
		this.end = end;
	}

	public int getRoom() {
		return room;
	}

	public void setRoom(int room) {
		this.room = room;
	}

    public String getLecturer_short() {
        return this.lecturer_short;
    }

    public void setLecturer_short(String lecturer_short) {
        this.lecturer_short = lecturer_short;
    }
}
