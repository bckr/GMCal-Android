package com.nils.becker.fhplaner.model;

import java.io.Serializable;
import java.util.HashMap;

import org.json.JSONException;
import org.json.JSONObject;

import android.graphics.Color;

@SuppressWarnings("serial")
public class Course implements Serializable {

	private String abbreviation;
	private String name;
	private String type;
    private String lecturer_short;
	private int day;
	private int start;
	private int end;
	private int room;

	public Course(String abbr, String name, String type, int day, int start, int end, int room, String lecturer_short) {
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

	public String getRoomFormatted() {
		String roomNumber = Integer.toString(this.room);
		if(roomNumber.length() > 3) {
			roomNumber = roomNumber.substring(0, 1) + "." + roomNumber.substring(1);
		} else {
			roomNumber = "0." + roomNumber;
		}
		return roomNumber;
	}
	
	public String timeSpan() {
		if (this.start > 1) {
			return Integer.toString(start + 7) + ":00 - " + Integer.toString(end + 7) + ":00";
		} else {
			return "08:30 - " + Integer.toString(end + 7) + ":00";
		}
	}
	
	public static int colorForCourseType(String type) {
		int color = Color.WHITE;
		if (type.equals("V")) color = Color.parseColor("#33B5E5");
		if (type.equals("P")) color = Color.parseColor("#FF4444");
		if (type.equals("S")) color = Color.parseColor("#FFBB33");
		if (type.equals("UE")) color = Color.parseColor("#99CC00");
		if (type.equals("T")) color = Color.parseColor("#AA66CC");
		return color;
	}

    public static String keyForCourseName(String name) {
        HashMap<String, String> keyForCourse = new HashMap<String, String>();
        keyForCourse.put("Wirtschaftsinformatik", "WI");
        keyForCourse.put("Medieninformatik", "MI");
        keyForCourse.put("Angewandte Informatik", "AI");
        keyForCourse.put("Technische Informatik", "TI");
        return keyForCourse.get(name);
    }
}
