package com.nils.becker.fhplaner.service;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class ScheduleDBA extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 2;
    private static final String DATABASE_NAME = "ScheduleDatabase";

    public static final String KEY_ABBREVIATION = "abbreviation";
    public static final String KEY_NAME = "name";
    public static final String KEY_TYPE = "type";
    public static final String KEY_LECTURER_SHORT = "lecturer_short";
    public static final String KEY_DAY = "day";
    public static final String KEY_START = "start";
    public static final String KEY_END = "end";
    public static final String KEY_ROOM = "room";

    public static final String KEY_FACULTY = "faculty";
    public static final String KEY_FORENAME = "forename";
    public static final String KEY_SURNAME = "surname";
    public static final String KEY_FORM = "form";
    public static final String KEY_TITLE = "title";
    public static final String KEY_FUNCTION = "function";
    public static final String KEY_PHONE = "phone";
    public static final String KEY_EMAIL = "email";
    public static final String KEY_HOMEPAGE = "homepage";
    public static final String KEY_ROOM_LECTURER = "room_lecturer";

    public static final String COURSE_TABLE_NAME = "Course";
    public static final String LECTURER_TABLE_NAME = "Lecturer";

    private static final String CREATE_COURSE_TABLE_STRING = "CREATE TABLE " + COURSE_TABLE_NAME + " ( _id INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_ABBREVIATION + " TEXT NOT NULL, " + KEY_NAME + " TEXT, " + KEY_TYPE + " TEXT, " + KEY_LECTURER_SHORT + " TEXT, " + KEY_DAY + " INTEGER, " + KEY_START + " INTEGER, " + KEY_END + " INTEGER, " + KEY_ROOM + " INTEGER);";
    public static final String CREATE_LECTURER_TABLE_STRING = "CREATE TABLE " + LECTURER_TABLE_NAME + " (_id INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_LECTURER_SHORT + " TEXT, " + KEY_FACULTY + " INTEGER, " + KEY_FORENAME + " TEXT, " + KEY_SURNAME + " TEXT, " + KEY_FORM + " TEXT, " + KEY_TITLE + " TEXT, " + KEY_FUNCTION + " TEXT, " + KEY_PHONE + " TEXT, " + KEY_EMAIL + " TEXT, " + KEY_HOMEPAGE + " TEXT, " + KEY_ROOM_LECTURER + " INTEGER );";


    public ScheduleDBA(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_LECTURER_TABLE_STRING);
        sqLiteDatabase.execSQL(CREATE_COURSE_TABLE_STRING);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i2) {
        // don't need migration right now
    }

    public Cursor getAllCourses() {
//        String selectQuery = "SELECT * FROM " + COURSE_TABLE_NAME;
        String selectQuery = "SELECT * FROM " + COURSE_TABLE_NAME + " JOIN "+ LECTURER_TABLE_NAME + " USING (" + KEY_LECTURER_SHORT + ")";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        return cursor;
    }

    // TODO Make use of SQLiteQueryBuilder
    public Cursor getAllCoursesWithFilter(String[] typesToFilter) {
        if (typesToFilter.length == 0){
            Log.d("debug", "no filter - get all courses");
            return getAllCourses();
        }

//        String selectQuery = "SELECT * FROM " + COURSE_TABLE_NAME + " WHERE " + KEY_TYPE + " NOT IN (";
        String selectQuery = "SELECT * FROM " + COURSE_TABLE_NAME + " JOIN "+ LECTURER_TABLE_NAME + " USING (" + KEY_LECTURER_SHORT + ") WHERE " + KEY_TYPE + " NOT IN (";
        String predicate = "";

        for (String type : typesToFilter) {
            predicate += "'" + type + "',";
        }

        predicate = predicate.substring(0, predicate.length() -1 );

        selectQuery += predicate + ")";

        Log.d("debug", "query: " + selectQuery + " " + predicate);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        return cursor;
    }

    public Cursor getCoursesForDayOfWeek(int dayOfWeek) {
        String selectQuery = "SELECT * FROM " + COURSE_TABLE_NAME + " JOIN "+ LECTURER_TABLE_NAME + " USING (" + KEY_LECTURER_SHORT + ") WHERE " + KEY_DAY + " = ?";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, new String[] {Integer.toString(dayOfWeek)});
        return cursor;
    }

    // TODO Make use of SQLiteQueryBuilder
    public Cursor getCoursesForDayOfWeekWithFilter(int dayOfWeek, String[] typesToFilter) {
        if (typesToFilter.length == 0){
            Log.d("debug", "no filter - get all courses");
            return getCoursesForDayOfWeek(dayOfWeek);
        }

        String selectQuery = "SELECT * FROM " + COURSE_TABLE_NAME + " JOIN "+ LECTURER_TABLE_NAME + " USING (" + KEY_LECTURER_SHORT + ") WHERE " + KEY_DAY + " =" + dayOfWeek + " AND " + KEY_TYPE + " NOT IN (";
        String predicate = "";

        for (String type : typesToFilter) {
            predicate += "'" + type + "',";
        }

        predicate = predicate.substring(0, predicate.length() -1 );

        selectQuery += predicate + ")";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        return cursor;
    }

    public Cursor getAllLecturer() {
        return getReadableDatabase().rawQuery("SELECT * FROM " + LECTURER_TABLE_NAME , null);
    }
}
