package com.example.eziteam.hospitalassistant;

import android.app.ProgressDialog;
import android.content.Intent;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class PatientActivity extends AppCompatActivity {
    private static final String TAG = PatientActivity.class.getSimpleName();
    private SQLiteHandler db;
    private EditText patient_name;
    private EditText patient_surname;
    private EditText patient_pesel;
    private EditText patient_phone;
    private EditText patient_blood;
    private EditText patient_postal;
    private EditText patient_city;
    private EditText patient_home_number;
    private ProgressDialog pDialog;
    private Button button_download;
    private Button button_actualize;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient);

        patient_name = (EditText)findViewById(R.id.firstnameText);
        patient_surname = (EditText)findViewById(R.id.lastnameText);
        patient_pesel = (EditText)findViewById(R.id.peselText);
        patient_phone = (EditText)findViewById(R.id.phoneText);
        patient_blood = (EditText)findViewById(R.id.bloodTypeText);
        patient_postal = (EditText)findViewById(R.id.postalAddressText);
        patient_city = (EditText)findViewById(R.id.cityText);
        patient_home_number = (EditText)findViewById(R.id.homeNumberText);
        button_download = (Button) findViewById(R.id.button_download);
        button_actualize = (Button) findViewById(R.id.button_actualize);

        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);
        db = new SQLiteHandler(getApplicationContext());

        button_download.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                db.deletepatient();
                String pesel = "85052659868"; //95081065242, 94060156866
                Get_patient(pesel);
            }
        });

        button_actualize.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                db.deletepatient();
                Actualize(patient_name.getText().toString(), patient_surname.getText().toString(), patient_pesel.getText().toString(), patient_phone.getText().toString(),patient_blood.getText().toString(),patient_postal.getText().toString(),patient_city.getText().toString(),patient_home_number.getText().toString());
            }
        });

        HashMap<String, String> patient = db.getPatientDetails();

        String p_name = patient.get("p_name");
        String p_surname = patient.get("p_surname");
        String p_pesel = patient.get("p_pesel");
        String p_phone = patient.get("p_phone");
        String p_blood = patient.get("p_blood");
        String p_postal = patient.get("p_postal");
        String p_city = patient.get("p_city");
        String p_home_number = patient.get("p_home_number");

        patient_name.setText(p_name);
        patient_surname.setText(p_surname);
        patient_pesel.setText(p_pesel);
        patient_phone.setText(p_phone);
        patient_blood.setText(p_blood);
        patient_postal.setText(p_postal);
        patient_city.setText(p_city);
        patient_home_number.setText(p_home_number);
    }

    private void Get_patient(final String pesel) {
        // Tag used to cancel the request
        String tag_string_req = "req_login";

        pDialog.setMessage("Pobieranie danych..." );
        showDialog();

        StringRequest strReq = new StringRequest( Request.Method.POST,
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

                        // Inserting row in users table
                        db.addPatient(p_name,p_surname,p_pesel,p_phone,p_blood,p_postal,p_city,p_home_number);

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

    private void Actualize(final String name, final String surname, final String pesel, final String phone, final String blood, final String postal, final String city, final String home_number) {
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
                        db.addPatient(name,surname,pesel,phone,blood,postal,city,home_number);
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
                return params;
            }

        };

        // Adding request to request queue
        SQLController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }
}
