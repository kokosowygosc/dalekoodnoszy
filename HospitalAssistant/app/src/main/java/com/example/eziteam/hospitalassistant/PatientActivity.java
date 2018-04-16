package com.example.eziteam.hospitalassistant;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.eziteam.Connect.ConnectConfig;
import com.example.eziteam.Connect.SQLController;
import com.example.eziteam.Connect.SQLiteHandler;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class PatientActivity extends AppCompatActivity {
    private static final String TAG = PatientActivity.class.getSimpleName();
    private String passingPesel;
    private SQLiteHandler db;
    private EditText patient_name;
    private EditText patient_surname;
    private EditText patient_pesel;
    private EditText patient_phone;
    private EditText patient_blood;
    private EditText patient_postal;
    private EditText patient_city;
    private EditText patient_home_number;
    private EditText patient_street;
    private ProgressDialog pDialog;
    private Button button_actualize, button_generate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient);

        String pesel ="";
        SharedPreferences prefs = getSharedPreferences("sharedData", MODE_PRIVATE);
        String restoredText = prefs.getString("pesel", null);
        if (restoredText != null) {
            pesel = prefs.getString("pesel", "");
        }

        patient_name = (EditText)findViewById(R.id.firstnameText);
        patient_surname = (EditText)findViewById(R.id.lastnameText);
        patient_pesel = (EditText)findViewById(R.id.peselText);
        patient_phone = (EditText)findViewById(R.id.phoneText);
        patient_blood = (EditText)findViewById(R.id.bloodTypeText);
        patient_postal = (EditText)findViewById(R.id.postalAddressText);
        patient_street = (EditText)findViewById(R.id.streetText);
        patient_city = (EditText)findViewById(R.id.cityText);
        patient_home_number = (EditText)findViewById(R.id.homeNumberText);
        button_actualize = (Button) findViewById(R.id.button_actualize);
        button_generate = (Button) findViewById(R.id.button_generate);

        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);
        db = new SQLiteHandler(getApplicationContext());
        db.deletepatient();
        Get_patient(pesel);

        button_actualize.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                db.deletepatient();
                Actualize(patient_name.getText().toString(), patient_surname.getText().toString(), patient_pesel.getText().toString(), patient_phone.getText().toString(),patient_blood.getText().toString(),patient_postal.getText().toString(),patient_city.getText().toString(),patient_home_number.getText().toString(), patient_street.getText().toString());
            }
        });
        button_generate.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                passingPesel = patient_pesel.getText().toString().trim();
                Intent target = new Intent(getApplicationContext(), GeneratorActivity.class);
                Bundle passingData = new Bundle();
                passingData.putString("QR",passingPesel);
                target.putExtras(passingData);
                startActivity(target);
            }
        });
    }

    public void Get_patient(final String pesel) {
        // Tag used to cancel the request
        String tag_string_req = "req_login";

        pDialog.setMessage("Pobieranie danych...");
        showDialog();


        StringRequest strReq = new StringRequest(Request.Method.POST,
                ConnectConfig.URL_LOGIN, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Taking response: " + response.toString());
                hideDialog();

                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");

                    // Check for error node in json
                    if (!error) {
                        String uid = jObj.getString("uid");
                        JSONObject patient = jObj.getJSONObject("patient");
                        String p_name = patient.getString("p_name");
                        String p_surname = patient.getString("p_surname");
                        String p_pesel = patient.getString("p_pesel");
                        String p_phone = patient.getString("p_phone");
                        String p_blood = patient.getString("p_blood");
                        String p_postal = patient.getString("p_postal");
                        String p_city = patient.getString("p_city");
                        String p_home_number = patient.getString("p_home_number");
                        String p_street = patient.getString("p_street");


                        String p_time = ""; String p_temperature = "";
                        String p_pressure = ""; String p_time_medicine ="";
                        String p_text_medicine = "";

                        // Inserting row in users table
                        db.addPatient(p_name, p_surname, p_pesel, p_phone, p_blood, p_postal, p_city, p_home_number, p_street, p_time, p_temperature, p_pressure, p_time_medicine, p_text_medicine);

                        patient_name.setText(p_name);
                        patient_surname.setText(p_surname);
                        patient_pesel.setText(p_pesel);
                        patient_phone.setText(p_phone);
                        patient_blood.setText(p_blood);
                        patient_postal.setText(p_postal);
                        patient_city.setText(p_city);
                        patient_home_number.setText(p_home_number);
                        patient_street.setText(p_street);

                    } else {
                        // Error in login. Get the error message
                        String errorMsg = jObj.getString("error_msg");
                        Toast.makeText(getApplicationContext(),
                                errorMsg, Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Json error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Login Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();
                hideDialog();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();
                params.put("pesel", pesel);
                return params;
            }

        };

        // Adding request to request queue
        SQLController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }

    private void Actualize(final String name, final String surname, final String pesel, final String phone, final String blood, final String postal, final String city, final String home_number, final String street) {
// Tag used to cancel the request
        String tag_string_req = "req_login";

        pDialog.setMessage("Aktualizowanie..." );
        showDialog();

        StringRequest strReq = new StringRequest( Request.Method.POST,
                ConnectConfig.URL_ACT, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Act response: " + response.toString());
                hideDialog();

                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");

                    // Check for error node in json
                    if (!error) {
                        final String p_time=""; final String p_temperature=""; final String p_pressure="";
                        final String p_time_medicine=""; final String p_text_medicine="";
                        db.addPatient(name,surname,pesel,phone,blood,postal,city,home_number,street,p_time,p_temperature,p_pressure,p_time_medicine,p_text_medicine);
                        // Launch main activity
                        Intent intent = new Intent(PatientActivity.this,PatientActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        // Error in login. Get the error message
                        String errorMsg = jObj.getString("error_msg");
                        Toast.makeText(getApplicationContext(),
                                errorMsg, Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Json error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Login Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();
                hideDialog();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();
                params.put("name", name);
                params.put("surname", surname);
                params.put("pesel", pesel);
                params.put("phone", phone);
                params.put("blood", blood);
                params.put("postal", postal);
                params.put("city", city);
                params.put("home_number", home_number);
                params.put("street", street);
                return params;
            }

        };

        // Adding request to request queue
        SQLController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }
}
