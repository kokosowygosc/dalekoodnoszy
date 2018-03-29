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
    private static final String TABLE_DOCTOR = "doctor";

    private static final String PATIENT_NAME = "p_name";
    private static final String PATIENT_SURNAME = "p_surname";
    private static final String PESEL = "p_pesel";
    private static final String PHONE_NUMBER = "p_phone";
    private static final String BLOOD = "p_blood";
    private static final String POSTAL = "p_postal";
    private static final String CITY = "p_city";
    private static final String HOME = "p_home_number";
    private static final String STREET = "p_street";
    private static final String KEY_ID = "id";
    private static final String TEMPERATURE = "p_temperature";
    private static final String PRESSURE = "p_pressure";
    private static final String TIME = "p_time";
    private static final String TIME_MEDICINE = "p_time_medicine";
    private static final String TEXT_MEDICINE = "p_text_medicine";

    private static final String DOCTOR_NAME = "d_name";
    private static final String DOCTOR_SURNAME = "d_surname";
    private static final String DOCTOR_SPEC = "d_spec";
    private static final String DOCTOR_EXTRA = "d_extr";
    private static final String DOCTOR_UNIQ = "d_uniq_id";

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
                + STREET + " TEXT,"
                + HOME + " TEXT,"
                + TIME + " DATETIME,"
                + TEMPERATURE + " TEXT,"
                + PRESSURE + " TEXT,"
                + TIME_MEDICINE + " DATETIME,"
                + TEXT_MEDICINE + " TEXT" + ");";
        db.execSQL(CREATE_PATIENT_TABLE);

        String CREATE_DOCTOR_DATABASE = "CREATE TABLE "
                + TABLE_DOCTOR + " (" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + DOCTOR_NAME + " TEXT,"
                + DOCTOR_SURNAME + " TEXT,"
                + DOCTOR_UNIQ + " TEXT,"
                + DOCTOR_SPEC + " TEXT,"
                + DOCTOR_EXTRA + " TEXT" + ");";
        db.execSQL(CREATE_DOCTOR_DATABASE);

        Log.d(TAG, "Database tables created");
    }
    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PATIENT);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DOCTOR);
        // Create tables again
        onCreate(db);
    }

    /**
     * Storing user details in database
     * */
    public void addPatient(String p_name, String p_surname, String p_pesel, String p_phone, String p_blood, String p_postal, String p_city, String p_home_number, String p_street, String p_time, String p_temperature, String p_pressure, String p_time_medicine, String p_text_medicine) {
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
        values.put(STREET, p_street);               // Patient street name
        values.put(TIME, p_time);                   // Patient time
        values.put(TEMPERATURE, p_temperature);     // Patient temperature
        values.put(PRESSURE, p_pressure);           // Patient pressure
        values.put(TIME_MEDICINE, p_time_medicine); // Patient time medicine
        values.put(TEXT_MEDICINE, p_text_medicine); // Patient text medicine

        long id = db.insert(TABLE_PATIENT, null ,values);
        db.close();
    }

    public void add_doctor(String uniq_id, String d_name, String d_surname, String d_spec, String d_extr) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ID, 1);
        values.put(DOCTOR_NAME, d_name);           // Patient name
        values.put(DOCTOR_SURNAME, d_surname);     // Patient surname
        values.put(DOCTOR_UNIQ, uniq_id);          // Patient pesel
        values.put(DOCTOR_SPEC, d_spec);          // Patient phone
        values.put(DOCTOR_EXTRA, d_extr);          // Patient phone

        long id = db.insert(TABLE_DOCTOR, null ,values);
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
            patient.put("p_street", cursor.getString(9));
            patient.put("p_time", cursor.getString(10));
            patient.put("p_temperature", cursor.getString(11));
            patient.put("p_pressure", cursor.getString(12));
            patient.put("p_time_medicine", cursor.getString(13));
            patient.put("p_text_medicine", cursor.getString(14));
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

    public void deletedoctor() {
        SQLiteDatabase db = this.getWritableDatabase();
        // Delete All Rows
        db.delete(TABLE_DOCTOR, null, null);
        db.close();

        Log.d(TAG, "Deleted all user info from sqlite");
    }
}
