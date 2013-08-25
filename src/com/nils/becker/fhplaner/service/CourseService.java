package com.nils.becker.fhplaner.service;

import android.graphics.Color;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class CourseService implements Serializable {

    private static final Map<String, String> keyForCourse;
    static {
        Map<String, String> map = new HashMap<String, String>();
        map.put("Wirtschaftsinformatik", "WI");
        map.put("Medieninformatik", "MI");
        map.put("Allgemeine Informatik", "AI");
        map.put("Technische Informatik", "TI");
        keyForCourse = Collections.unmodifiableMap(map);
    }

    private static final Map<String, Integer> courseTypeColors;
    static {
        Map<String, Integer> map = new HashMap<String, Integer>();
        map.put("V", Color.parseColor("#33B5E5"));
        map.put("P", Color.parseColor("#FF4444"));
        map.put("S", Color.parseColor("#FFBB33"));
        map.put("UE", Color.parseColor("#99CC00"));
        map.put("T", Color.parseColor("#AA66CC"));
        courseTypeColors = Collections.unmodifiableMap(map);
    }

    public static final int DEFAULT_COLOR = Color.WHITE;

    public static int colorForCourseType(String type) {
        return courseTypeColors.get(type) == null ? DEFAULT_COLOR : courseTypeColors.get(type);
    }

    public static String keyForCourseName(String name) {
        return keyForCourse.get(name);
    }

    public static String getFormattedRoom(int room) {
        String roomNumber = Integer.toString(room);
        if(roomNumber.length() > 3) {
            roomNumber = roomNumber.substring(0, 1) + "." + roomNumber.substring(1);
        } else {
            roomNumber = "0." + roomNumber;
        }
        return roomNumber;
    }

    public static String getFormattedTimeSpan(int start, int end) {
        if (start >= 3) {
            return Integer.toString(start + 7) + ":00 - " + Integer.toString(end + 7) + ":00";
        } else if (start > 1 && start < 3) {
            return "0" + Integer.toString(start + 7) + ":00 - " + Integer.toString(end + 7) + ":00";
        } else {
            return "08:30 - " + Integer.toString(end + 7) + ":00";
        }
    }

}