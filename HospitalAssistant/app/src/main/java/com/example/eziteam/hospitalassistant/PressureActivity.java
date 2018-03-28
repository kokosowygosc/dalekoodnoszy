package com.example.eziteam.hospitalassistant;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ExpandableListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PressureActivity extends AppCompatActivity {

    private ExpandableListView listView;
    private ExpandableListAdapter listAdapter;
    private List<String> listDataHeader;
    private HashMap<String,List<String>> listHash;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pressure);

        String pesel;
        pesel="";
        SharedPreferences prefs = getSharedPreferences("sharedData", MODE_PRIVATE);
        String restoredText = prefs.getString("pesel", null);
        if (restoredText != null) {
            pesel = prefs.getString("pesel", "");
        }

        listView = (ExpandableListView)findViewById(R.id.pressureExpandable);
        initData();
        listAdapter = new ExpandableListAdapter(this,listDataHeader,listHash);
        listView.setAdapter(listAdapter);
    }

    private void initData() {
        listDataHeader = new ArrayList<>();
        listHash = new HashMap<>();
        listDataHeader.add("12.03.18");
        listDataHeader.add("13.03.18");
        listDataHeader.add("14.03.18");
        listDataHeader.add("15.03.18");
        listDataHeader.add("16.03.18");
        listDataHeader.add("17.03.18");
        listDataHeader.add("18.03.18");

        List<String> firstDay = new ArrayList<>();
        firstDay.add("8:00 140/90");
        firstDay.add("12:00 140/90");
        firstDay.add("16:00 140/90");
        firstDay.add("20:00 140/90");

        List<String> secondDay = new ArrayList<>();
        secondDay.add("8:00 140/90");
        secondDay.add("12:00 140/90");
        secondDay.add("16:00 140/90");
        secondDay.add("20:00 140/90");

        List<String> thirdDay = new ArrayList<>();
        thirdDay.add("8:00 140/90");
        thirdDay.add("12:00 140/90");
        thirdDay.add("16:00 140/90");
        thirdDay.add("20:00 140/90");

        List<String> fourthDay = new ArrayList<>();
        fourthDay.add("8:00 140/90");
        fourthDay.add("12:00 140/90");
        fourthDay.add("16:00 140/90");
        fourthDay.add("20:00 140/90");

        List<String> fifthDay = new ArrayList<>();
        fifthDay.add("8:00 140/90");
        fifthDay.add("12:00 140/90");
        fifthDay.add("16:00 140/90");
        fifthDay.add("20:00 140/90");

        List<String> sixthDay = new ArrayList<>();
        sixthDay.add("8:00 140/90");
        sixthDay.add("12:00 140/90");
        sixthDay.add("16:00 140/90");
        sixthDay.add("20:00 140/90");

        List<String> seventhDay = new ArrayList<>();
        seventhDay.add("8:00 140/90");
        seventhDay.add("12:00 140/90");
        seventhDay.add("16:00 140/90");
        seventhDay.add("20:00 140/90");

        listHash.put(listDataHeader.get(0),firstDay);
        listHash.put(listDataHeader.get(1),secondDay);
        listHash.put(listDataHeader.get(2),thirdDay);
        listHash.put(listDataHeader.get(3),fourthDay);
        listHash.put(listDataHeader.get(4),fifthDay);
        listHash.put(listDataHeader.get(5),sixthDay);
        listHash.put(listDataHeader.get(6),seventhDay);
    }
}
