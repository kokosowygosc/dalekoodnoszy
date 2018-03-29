    package com.example.eziteam.hospitalassistant;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.eziteam.Connect.ConnectConfig;
import com.example.eziteam.Connect.SQLController;
import com.example.eziteam.Connect.SQLiteHandler;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

    public class HomeActivity extends AppCompatActivity {
    SharedPreferences.Editor editor;
    Boolean testMode=false;
    private SQLiteHandler db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        editor = getSharedPreferences("sharedData", MODE_PRIVATE).edit();
        editor.putString("pesel", "");
        editor.putString("id","");
        editor.putBoolean("testMode",false);
        editor.apply();

        db = new SQLiteHandler(getApplicationContext());
        //db.deletedoctor();
        String unique_id = "999999999"; //999999998 999999997 999999996
        Get_doc(unique_id);

        SharedPreferences prefs = getSharedPreferences("sharedData", MODE_PRIVATE);
        Boolean restoredText = prefs.getBoolean("testMode", false);
        if (restoredText) {
            testMode = prefs.getBoolean("testMode", false);
        }

        Switch testModeSwitch = findViewById(R.id.switchTestEnv);
        if(testMode){
            testModeSwitch.setChecked(true);
        } else{
            testModeSwitch.setChecked(false);
        }
        testModeSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                editor.putBoolean("testMode", isChecked);
                editor.apply();
                if(isChecked){
                    //Toast.makeText(getApplicationContext(), "1", Toast.LENGTH_SHORT).show();

                    editor = getSharedPreferences("sharedData", MODE_PRIVATE).edit();
                    editor.putString("id", "999999999");
                    editor.apply();

                    Intent target = new Intent(getApplicationContext(), MenuActivity.class);
                    startActivity(target);
                } else {
                    //Toast.makeText(getApplicationContext(), "0", Toast.LENGTH_SHORT).show();
                }
            }
        });

        final Activity activity = this;
        ImageButton qrScanButton = (ImageButton) findViewById(R.id.qrScanButton);

        qrScanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentIntegrator intentIntegrator = new IntentIntegrator(activity);
                intentIntegrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
                intentIntegrator.setPrompt("Scan");
                intentIntegrator.setCameraId(0);
                intentIntegrator.setBeepEnabled(false);
                intentIntegrator.setBarcodeImageEnabled(false);
                intentIntegrator.initiateScan();

            }
        });

        BottomNavigationView bottomNavigationView = (BottomNavigationView)findViewById(R.id.navigation);
        bottomNavigationView.setSelectedItemId(R.id.navigation_home);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_home:
                        Intent intentHome = new Intent(getApplicationContext(), HomeActivity.class);
                        startActivity(intentHome);
                        break;
                    case R.id.navigation_menu:
                        Intent intentMenu = new Intent(getApplicationContext(), MenuActivity.class);
                        startActivity(intentMenu);
                        break;
                    case R.id.navigation_exit:
                        break;
                }
                return true;
            }
        });

    }

        @Override
        protected void onActivityResult(int requestCode, int resultCode, Intent data) {

            IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
            if(result != null) {
                if(result.getContents() == null) {
                    Toast.makeText(this, "You cancelled scanning.", Toast.LENGTH_SHORT).show();
                } else {
                    if(new String("999999999").equals(result.getContents().toString())) {
                        //condition hardcoded for tests
                        SharedPreferences.Editor editor3 = getSharedPreferences("sharedData", MODE_PRIVATE).edit();
                        editor3.putString("id", result.getContents());
                        editor3.apply();

                        Intent target = new Intent(this, MenuActivity.class);
                        startActivity(target);
                    } else {
                        Toast.makeText(this, "You have not access for app!", Toast.LENGTH_LONG).show();
                    }

                }
            } else {
                super.onActivityResult(requestCode, resultCode, data);
            }

        }

        public void Get_doc(final String unique_id) {
            // Tag used to cancel the request
            String tag_string_req = "req_login";

            StringRequest strReq = new StringRequest(Request.Method.POST,
                    ConnectConfig.URL_REGISTER, new Response.Listener<String>() {

                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject jObj = new JSONObject(response);
                        boolean error = jObj.getBoolean("error");

                        // Check for error node in json
                        if (!error) {
                            JSONObject doctor = jObj.getJSONObject("doctor");
                            String unique_id = doctor.getString("unique_id");
                            String name = doctor.getString("d_name");
                            String surname = doctor.getString("d_surname");
                            String spec = doctor.getString("d_spec");
                            String extr = doctor.getString("d_extr");
                            // Inserting row in users table
                            db.add_doctor(unique_id,name,surname,spec,extr);

                            // TUTAJ MOŻNA WYKORZYSTAC POWYŻSZE POLA I COS TAM POROBIC

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
                    Toast.makeText(getApplicationContext(),
                            error.getMessage(), Toast.LENGTH_LONG).show();
                }
            }) {

                @Override
                protected Map<String, String> getParams() {
                    // Posting parameters to login url
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("unique_id", unique_id);
                    return params;
                }
            };
            // Adding request to request queue
            SQLController.getInstance().addToRequestQueue(strReq, tag_string_req);
        }

    }
