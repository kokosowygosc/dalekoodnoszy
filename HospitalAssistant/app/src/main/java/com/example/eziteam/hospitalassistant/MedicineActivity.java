package com.example.eziteam.hospitalassistant;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ExpandableListView;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MedicineActivity extends AppCompatActivity {
    private static final String TAG = PressureActivity.class.getSimpleName();
    private SQLiteHandler db;
    private ExpandableListView listView;
    private ExpandableListAdapter listAdapter;
    private List<String> listDataHeader;
    private HashMap<String,List<String>> listHash;
    private ProgressDialog pDialog;

    String data[][] =
            {
                    {"Morning", "Vitamin C: 2 caps","Prozac: 1 cap"},
                    {"Midday", "Vitamin C: 2 caps", "Prozac: 1 cap"},
                    {"Evening", "Vitamin C: 2 caps", "Prozac: 1 cap"}
            };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicine);
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        String pesel;
        pesel="";
        SharedPreferences prefs = getSharedPreferences("sharedData", MODE_PRIVATE);
        String restoredText = prefs.getString("pesel", null);
        if (restoredText != null) {
            pesel = prefs.getString("pesel", "");
        }


        db = new SQLiteHandler(getApplicationContext());
        db.deletepatient();
        Get_medicine(pesel);

        listView = findViewById(R.id.medicineExpandable);
        initData();
        listAdapter = new ExpandableListAdapter(this,listDataHeader,listHash);
        listView.setAdapter(listAdapter);
    }

    private void initData() {
        listDataHeader = new ArrayList<>();
        listHash = new HashMap<>();
        List<String> morning = new ArrayList<>();
        List<String> midday = new ArrayList<>();
        List<String> evening = new ArrayList<>();

        for(int i = 0; i < data.length; i++)
        {
            listDataHeader.add(data[i][0]);
        }

        for(int i = 0; i <= data.length; i++)
            for(int j = 0; j < data[0].length; j++) {
                if (i == 0 && j != 0)
                    morning.add(data[i][j]);
                if (i == 1 && j != 0)
                    midday.add(data[i][j]);
                if (i == 2 && j != 0)
                    evening.add(data[i][j]);
            }

        listHash.put(listDataHeader.get(0),morning);
        listHash.put(listDataHeader.get(1),midday);
        listHash.put(listDataHeader.get(2),evening);

       /* HashMap<String, String> patient = db.getPatientDetails();

        String p_pesel = patient.get("p_pesel");
        String p_time_medicine = patient.get("p_time_medicine"); // poszczegolne informacje danego pacjete umieszczone w malych tablicach
        String p_text_medicine = patient.get("p_text_medicine");*/
    }

    public void Get_medicine(final String pesel) {
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
                        String p_time = ""; String p_temperature = ""; String p_pressure = "";

                        JSONObject medicine = jObj.getJSONObject("medicine");
                        String p_time_medicine = medicine.getString("p_time_medicine");
                        String p_text_medicine = medicine.getString("p_text_medicine");

                        // Inserting row in users table
                        db.addPatient(p_name, p_surname, p_pesel, p_phone, p_blood, p_postal, p_city, p_home_number, p_street, p_time, p_temperature, p_pressure, p_time_medicine, p_text_medicine);
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
