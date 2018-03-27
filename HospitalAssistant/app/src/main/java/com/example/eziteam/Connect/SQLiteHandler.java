package com.example.eziteam.Connect;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.android.volley.RequestQueue;

import java.util.HashMap;

/**
 * Created by Mateusz Kurłaaaaaaa on 24.03.2018.
 */

public class SQLiteHandler extends SQLiteOpenHelper {
    private static final String TAG = SQLiteHandler.class.getSimpleName();
    private RequestQueue mRequestQueue;
    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "android_api";

    // Login table name
    private static final String TABLE_PATIENT = "patient";

    private static final String PATIENT_NAME = "p_name";
    private static final String PATIENT_SURNAME = "p_surname";
    private static final String PESEL = "p_pesel";
    private static final String PHONE_NUMBER = "p_phone";
    private static final String BLOOD = "p_blood";
    private static final String POSTAL = "p_postal";
    private static final String CITY = "p_city";
    private static final String HOME = "p_home_number";
    private static final String KEY_ID = "id";

    public SQLiteHandler(Context context) {	super(context, DATABASE_NAME, null, DATABASE_VERSION);	}

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db){
        String CREATE_PATIENT_TABLE = "CREATE TABLE "
                + TABLE_PATIENT + " (" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + PATIENT_NAME + " TEXT,"
                + PATIENT_SURNAME + " TEXT,"
                + PESEL + " INTEGER,"
                + PHONE_NUMBER + " TEXT,"
                + BLOOD + " TEST,"
                + POSTAL + " TEXT,"
                + CITY + " TEXT,"
                + HOME + " TEXT" + ");";
        db.execSQL(CREATE_PATIENT_TABLE);

        Log.d(TAG, "Database tables created");
    }
    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PATIENT);

        // Create tables again
        onCreate(db);
    }

    /**
     * Storing user details in database
     * */
    public void addPatient(String p_name, String p_surname, String p_pesel, String p_phone, String p_blood, String p_postal, String p_city, String p_home_number) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ID, 1);
        values.put(PATIENT_NAME, p_name);           // Patient name
        values.put(PATIENT_SURNAME, p_surname);     // Patient surname
        values.put(PESEL, p_pesel);                 // Patient pesel
        values.put(PHONE_NUMBER, p_phone);          // Patient phone
        values.put(BLOOD, p_blood);                 // Patient blood
        values.put(POSTAL, p_postal);               // Patient postal
        values.put(CITY, p_city);                   // Patient city
        values.put(HOME, p_home_number);            // Patient home number

        long id = db.insert(TABLE_PATIENT, null ,values);
        db.close();
    }

    /**
     * Getting user data from database
     * */
    public HashMap<String, String> getPatientDetails() {
        HashMap<String, String> patient = new HashMap<String, String>();
        String selectQuery = "SELECT * FROM " + TABLE_PATIENT;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // Move to first row
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            patient.put("p_name", cursor.getString(1));
            patient.put("p_surname", cursor.getString(2));
            patient.put("p_pesel", cursor.getString(3));
            patient.put("p_phone", cursor.getString(4));
            patient.put("p_blood", cursor.getString(5));
            patient.put("p_postal", cursor.getString(6));
            patient.put("p_city", cursor.getString(7));
            patient.put("p_home_number", cursor.getString(8));
        }
        cursor.close();
        db.close();
        // return user
        Log.d(TAG, "Fetching user from Sqlite: " + patient.toString());

        return patient;
    }

    /**
     * Re crate database Delete all tables and create them again
     * */
    public void deletepatient() {
        SQLiteDatabase db = this.getWritableDatabase();
        // Delete All Rows
        db.delete(TABLE_PATIENT, null, null);
        db.close();

        Log.d(TAG, "Deleted all user info from sqlite");
    }
}
