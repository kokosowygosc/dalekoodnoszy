package com.example.eziteam.hospitalassistant;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ExpandableListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TemperatureActivity extends AppCompatActivity {

    private ExpandableListView listView;
    private ExpandableListAdapter listAdapter;
    private List<String> listDataHeader;
    private HashMap<String,List<String>> listHash;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temperature);


        String pesel;
        pesel="";
        SharedPreferences prefs = getSharedPreferences("sharedData", MODE_PRIVATE);
        String restoredText = prefs.getString("pesel", null);
        if (restoredText != null) {
            pesel = prefs.getString("pesel", "");
        }

        listView = (ExpandableListView)findViewById(R.id.temperatureExpandable);
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
        firstDay.add("8:00 36.6");
        firstDay.add("12:00 36.7");
        firstDay.add("16:00 36.6");
        firstDay.add("20:00 36.8");

        List<String> secondDay = new ArrayList<>();
        secondDay.add("8:00 36.6");
        secondDay.add("12:00 36.7");
        secondDay.add("16:00 36.6");
        secondDay.add("20:00 36.8");

        List<String> thirdDay = new ArrayList<>();
        thirdDay.add("8:00 36.6");
        thirdDay.add("12:00 36.7");
        thirdDay.add("16:00 36.6");
        thirdDay.add("20:00 36.8");

        List<String> fourthDay = new ArrayList<>();
        fourthDay.add("8:00 36.6");
        fourthDay.add("12:00 36.7");
        fourthDay.add("16:00 36.6");
        fourthDay.add("20:00 36.8");

        List<String> fifthDay = new ArrayList<>();
        fifthDay.add("8:00 36.6");
        fifthDay.add("12:00 36.7");
        fifthDay.add("16:00 36.6");
        fifthDay.add("20:00 36.8");

        List<String> sixthDay = new ArrayList<>();
        sixthDay.add("8:00 36.6");
        sixthDay.add("12:00 36.7");
        sixthDay.add("16:00 36.6");
        sixthDay.add("20:00 36.8");

        List<String> seventhDay = new ArrayList<>();
        seventhDay.add("8:00 36.6");
        seventhDay.add("12:00 36.7");
        seventhDay.add("16:00 36.6");
        seventhDay.add("20:00 36.8");

        listHash.put(listDataHeader.get(0),firstDay);
        listHash.put(listDataHeader.get(1),secondDay);
        listHash.put(listDataHeader.get(2),thirdDay);
        listHash.put(listDataHeader.get(3),fourthDay);
        listHash.put(listDataHeader.get(4),fifthDay);
        listHash.put(listDataHeader.get(5),sixthDay);
        listHash.put(listDataHeader.get(6),seventhDay);
    }
}
