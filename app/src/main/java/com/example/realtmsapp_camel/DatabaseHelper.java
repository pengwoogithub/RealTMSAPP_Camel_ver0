package com.example.realtmsapp_camel;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.List;
import java.util.ArrayList;


import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {
    //initialization of database
    private Context context;
    private static final String DATABASE_NAME = "Time.db";
    private static final int DATABASE_VERSION = 1;

    //database table and columns names
    private static final String TABLE_TMS = "Process_Time";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_PROCESS = "Process";
    private static final String COLUMN_PERSON = "Person";
    private static final String COLUMN_MODEL = "Model";
    private static final String COLUMN_TRIAL_SET = "Trial_Set";
    private static final String COLUMN_TRIAL1 = "Trial1";
    private static final String COLUMN_TRIAL2 = "Trial2";
    private static final String COLUMN_TRIAL3 = "Trial3";
    private static final String COLUMN_TRIAL4 = "Trial4";
    private static final String COLUMN_TRIAL5 = "Trial5";
    private static final String COLUMN_TRIAL6 = "Trial6";
    private static final String COLUMN_TRIAL7 = "Trial7";
    private static final String COLUMN_TRIAL8 = "Trial8";
    private static final String COLUMN_TRIAL9 = "Trial9";
    private static final String COLUMN_TRIAL10 = "Trial10";


    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_TMS +
                " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_PROCESS + " TEXT, " +
                COLUMN_PERSON + " TEXT, " +
                COLUMN_TRIAL_SET + " INTEGER, " +
                COLUMN_MODEL + " TEXT, " +
                COLUMN_TRIAL1 + " TEXT, " +
                COLUMN_TRIAL2 + " TEXT, " +
                COLUMN_TRIAL3 + " TEXT, " +
                COLUMN_TRIAL4 + " TEXT, " +
                COLUMN_TRIAL5 + " TEXT, " +
                COLUMN_TRIAL6 + " TEXT, " +
                COLUMN_TRIAL7 + " TEXT, " +
                COLUMN_TRIAL8 + " TEXT, " +
                COLUMN_TRIAL9 + " TEXT, " +
                COLUMN_TRIAL10 + " TEXT);";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TMS);
        onCreate(db);
    }

    public boolean insertTrial(String process, String person, String model, String trialValue, int trialNumber, int trialSetCount) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_PROCESS, process);
        values.put(COLUMN_PERSON, person);
        values.put(COLUMN_MODEL, model);
        values.put(COLUMN_TRIAL_SET, trialSetCount);
        values.put("trial" + trialNumber, trialValue);

        long resultID;
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_TMS + " WHERE " + COLUMN_PROCESS + "=? AND " + COLUMN_PERSON + "=? AND " + COLUMN_MODEL + "=? AND " + COLUMN_TRIAL_SET + "=?",
                new String[]{process, person, model, String.valueOf(trialSetCount)});
        if (cursor.moveToFirst()) {
            resultID = db.update(TABLE_TMS, values, COLUMN_PROCESS + "=? AND " + COLUMN_PERSON + "=? AND " + COLUMN_MODEL + "=? AND " + COLUMN_TRIAL_SET + "=?",
                    new String[]{process, person, model, String.valueOf(trialSetCount)});
        } else {
            resultID = db.insert(TABLE_TMS, null, values);
        }
        cursor.close();

        return resultID != -1;
    }

    public List<Trial> getAllTrials() {
        List<Trial> trialsList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_TMS, null);

        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") String Process = cursor.getString(cursor.getColumnIndex(COLUMN_PROCESS));
                @SuppressLint("Range") String Person = cursor.getString(cursor.getColumnIndex(COLUMN_PERSON));
                @SuppressLint("Range") String Model = cursor.getString(cursor.getColumnIndex(COLUMN_MODEL));
                @SuppressLint("Range") int trialSet = cursor.getInt(cursor.getColumnIndex(COLUMN_TRIAL_SET));

                String[] trialValues = new String[10];
                for (int i = 0; i < 10; i++) {
                    int colIndex = cursor.getColumnIndex("Trial" + (i + 1));
                    if (colIndex != -1) {
                        trialValues[i] = cursor.getString(colIndex);
                    } else {
                        trialValues[i] = null; // or handle appropriately
                    }
                }
                trialsList.add(new Trial(Process, Person, Model, trialSet, trialValues));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return trialsList;
    }

    public boolean clearAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            db.execSQL("DELETE FROM " + TABLE_TMS);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}



