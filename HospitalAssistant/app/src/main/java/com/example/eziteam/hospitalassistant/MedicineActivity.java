package com.example.eziteam.hospitalassistant;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ExpandableListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MedicineActivity extends AppCompatActivity {

    private ExpandableListView listView;
    private ExpandableListAdapter listAdapter;
    private List<String> listDataHeader;
    private HashMap<String,List<String>> listHash;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicine);

        listView = (ExpandableListView)findViewById(R.id.medicineExpandable);
        initData();
        listAdapter = new ExpandableListAdapter(this,listDataHeader,listHash);
        listView.setAdapter(listAdapter);

        String pesel;
        pesel="";
        SharedPreferences prefs = getSharedPreferences("sharedData", MODE_PRIVATE);
        String restoredText = prefs.getString("pesel", null);
        if (restoredText != null) {
            pesel = prefs.getString("pesel", "");
        }

    }

    private void initData() {
        listDataHeader = new ArrayList<>();
        listHash = new HashMap<>();
        listDataHeader.add("Morning");
        listDataHeader.add("Midday");
        listDataHeader.add("Evening");

        List<String> morning = new ArrayList<>();
        morning.add("Vitamin C: 2 caps");
        morning.add("Prozac: 1 cap");

        List<String> midday = new ArrayList<>();
        midday.add("Vitamin C: 2 caps");
        midday.add("Prozac: 1 cap");

        List<String> evening = new ArrayList<>();
        evening.add("Vitamin C: 2 caps");
        evening.add("Prozac: 1 cap");


        listHash.put(listDataHeader.get(0),morning);
        listHash.put(listDataHeader.get(1),midday);
        listHash.put(listDataHeader.get(2),evening);
    }


}
