package com.nils.becker.fhplaner.service;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.HashMap;

public class ScheduleOpenHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 2;
    private static final String DATABASE_NAME = "ScheduleDatabase";
    private static final String DICTIONARY_TABLE_CREATE = "CREATE TABLE Course ( _id INTEGER PRIMARY KEY AUTOINCREMENT, abbreviation TEXT NOT NULL, name TEXT, type TEXT, lecturer_short TEXT, day INTEGER, start INTEGER, end INTEGER, room INTEGER);";

    public ScheduleOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(DICTIONARY_TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i2) {
        // don't need migration right now
    }

    public Cursor getAllCourses() {
        String selectQuery = "SELECT * FROM Course";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        return cursor;
    }

    public Cursor getCoursesForDayOfWeek(int dayOfWeek) {
        String selectQuery = "SELECT * FROM Course WHERE day =" + dayOfWeek;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        return cursor;
    }
}
