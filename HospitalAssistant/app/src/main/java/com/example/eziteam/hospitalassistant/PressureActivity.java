package com.example.eziteam.hospitalassistant;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ExpandableListView;
import android.widget.Toast;
import android.content.Intent;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.eziteam.Connect.ConnectConfig;
import com.example.eziteam.Connect.SQLController;
import com.example.eziteam.Connect.SQLiteHandler;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Method;
import java.util.*;


public class PressureActivity extends AppCompatActivity {
    private static final String TAG = com.example.eziteam.hospitalassistant.PressureActivity.class.getSimpleName();
    private SQLiteHandler db;
    private ExpandableListView listView;
    private ExpandableListAdapter listAdapter;
    private List<String> listDataHeader;
    private HashMap<String, List<String>> listHash;
    private ProgressDialog pDialog;

    String data[][] =
            {
                    {"Monday", "8:00 140/90", "12:00 140/90", "16:00 140/90", "20:00 140/90"},
                    {"Tuesday", "8:00 140/90", "12:00 140/90", "16:00 140/90", "20:00 140/90"},
                    {"Wednesday", "8:00 140/90", "12:00 140/90", "16:00 140/90", "20:00 140/90"},
                    {"Thursday", "8:00 140/90", "12:00 140/90", "16:00 140/90", "20:00 140/90"},
                    {"Friday", "8:00 140/90", "12:00 140/90", "16:00 140/90", "20:00 140/90"},
                    {"Saturday", "8:00 140/90", "12:00 140/90", "16:00 140/90", "20:00 140/90"},
                    {"Sunday", "8:00 140/90", "12:00 140/90", "16:00 140/90", "20:00 140/90"},
            };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pressure);

        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        String pesel;
        pesel = "";
        SharedPreferences prefs = getSharedPreferences("sharedData", MODE_PRIVATE);
        String restoredText = prefs.getString("pesel", null);
        if (restoredText != null) {
            pesel = prefs.getString("pesel", "");
        }

        db = new SQLiteHandler(getApplicationContext());
        db.deletepatient();
        Get_stats(pesel);

        listView = (ExpandableListView) findViewById(R.id.pressureExpandable);
        initData();
        listAdapter = new ExpandableListAdapter(this, listDataHeader, listHash);
        listView.setAdapter(listAdapter);
    }


    private void initData() {
        listDataHeader = new ArrayList<>();
        listHash = new HashMap<>();
        List<String> firstDay = new ArrayList<>();
        List<String> secondDay = new ArrayList<>();
        List<String> thirdDay = new ArrayList<>();
        List<String> fourthDay = new ArrayList<>();
        List<String> fifthDay = new ArrayList<>();
        List<String> sixthDay = new ArrayList<>();
        List<String> seventhDay = new ArrayList<>();


        //TODO Rozdanie danych na listy każdego dnia.

        for (int i = 0; i < data.length; i++) {
            listDataHeader.add(data[i][0]);
        }

        for (int i = 0; i <= data.length; i++)
            for (int j = 0; j < data[0].length; j++) {
                if (i == 0 && j != 0)
                    firstDay.add(data[i][j]);
                if (i == 1 && j != 0)
                    secondDay.add(data[i][j]);
                if (i == 2 && j != 0)
                    thirdDay.add(data[i][j]);
                if (i == 3 && j != 0)
                    fourthDay.add(data[i][j]);
                if (i == 4 && j != 0)
                    fifthDay.add(data[i][j]);
                if (i == 5 && j != 0)
                    sixthDay.add(data[i][j]);
                if (i == 6 && j != 0)
                    seventhDay.add(data[i][j]);
            }

        listHash.put(listDataHeader.get(0), firstDay);
        listHash.put(listDataHeader.get(1), secondDay);
        listHash.put(listDataHeader.get(2), thirdDay);
        listHash.put(listDataHeader.get(3), fourthDay);
        listHash.put(listDataHeader.get(4), fifthDay);
        listHash.put(listDataHeader.get(5), sixthDay);
        listHash.put(listDataHeader.get(6), seventhDay);
    }


    public void Get_stats(final String pesel) {
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
                        String p_name = ""; String p_surname = "";  String p_pesel ="";
                        String p_phone = ""; String p_blood = ""; String p_postal = "";
                        String p_city = ""; String p_home_number = ""; String p_street = "";
                        String p_time_medicine =""; String p_text_medicine ="";

                        JSONObject stats = jObj.getJSONObject("stats");
                        String p_time = stats.getString("time");
                        String p_temperature = stats.getString("temperature");
                        String p_pressure = stats.getString("pressure");

                        // Inserting row in users table
                        db.addPatient(p_name, p_surname, p_pesel, p_phone, p_blood, p_postal, p_city, p_home_number, p_street, p_time, p_temperature, p_pressure, p_time_medicine, p_text_medicine);

                        // TUTAJ MOŻNA WYKORZYSTAC POWYŻSZE POLA I USTAWIC JE NA WIDOKU

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
}
//}