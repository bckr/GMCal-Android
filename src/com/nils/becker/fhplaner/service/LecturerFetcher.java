package com.nils.becker.fhplaner.service;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.nils.becker.fhplaner.model.Lecturer;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

/**
 * Created by nils on 8/18/13.
 */
public class LecturerFetcher implements Fetcher {

    private static final String REQUEST_URI = "http://nils-becker.com/fhg/lecturers";
    private final Context context;

    public LecturerFetcher(Context context) {
        this.context = context;
    }

    public void fetchLecturers() {
        new FetchLecturersTask(this).execute(REQUEST_URI);
    }

    @Override
    public void responseLoaded(JSONArray response) {

        ArrayList<Lecturer> lecturerList = new ArrayList<Lecturer>();
        for (int i = 0; i < response.length(); i++) {
            try {
                Lecturer currentLecturer = new Lecturer(response.getJSONObject(i));
                lecturerList.add(currentLecturer);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        persistLecturers(lecturerList);
    }

    private void persistLecturers(ArrayList<Lecturer> lecturers) {

        ScheduleDBA dbHelper = new ScheduleDBA(this.context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.beginTransaction();

        // delete all previous entries
        db.delete(ScheduleDBA.LECTURER_TABLE_NAME, null, null);

        ContentValues content = new ContentValues();
        for(Lecturer l : lecturers) {
            content.put(ScheduleDBA.KEY_LECTURER_SHORT, l.getDozentkuerzel());
            content.put(ScheduleDBA.KEY_FACULTY, l.getFb_nr());
            content.put(ScheduleDBA.KEY_FORENAME, l.getVorname());
            content.put(ScheduleDBA.KEY_SURNAME, l.getNachname());
            content.put(ScheduleDBA.KEY_FORM, l.getAnrede());
            content.put(ScheduleDBA.KEY_TITLE, l.getTitel());
            content.put(ScheduleDBA.KEY_FUNCTION, l.getFunktion());
            content.put(ScheduleDBA.KEY_PHONE, l.getTel_intern());
            content.put(ScheduleDBA.KEY_EMAIL, l.getEmail());
            content.put(ScheduleDBA.KEY_HOMEPAGE, l.getHomepage());
            db.insert(ScheduleDBA.LECTURER_TABLE_NAME, null, content);
        }

        db.setTransactionSuccessful();
        db.endTransaction();
    }

}
