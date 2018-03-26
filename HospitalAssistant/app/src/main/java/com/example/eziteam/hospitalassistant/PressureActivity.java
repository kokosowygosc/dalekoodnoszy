package com.example.eziteam.hospitalassistant;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ExpandableListView;

import java.util.*;


public class PressureActivity extends AppCompatActivity {

    private ExpandableListView listView;
    private ExpandableListAdapter listAdapter;
    private List<String> listDataHeader;
    private HashMap<String, List<String>> listHash;

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

        listView = findViewById(R.id.pressureExpandable);
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

        for(int i = 0; i <= data.length; i++)
            for(int j = 0; j < data[0].length; j++)
            {
                if(i == 0 && j != 0)
                    firstDay.add(data[i][j]);
                if(i == 1 && j != 0)
                    secondDay.add(data[i][j]);
                if(i == 2 && j != 0)
                    thirdDay.add(data[i][j]);
                if(i == 3 && j != 0)
                    fourthDay.add(data[i][j]);
                if(i == 4 && j != 0)
                    fifthDay.add(data[i][j]);
                if(i == 5 && j != 0)
                    sixthDay.add(data[i][j]);
                if(i == 6 && j != 0)
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
    }
//}

