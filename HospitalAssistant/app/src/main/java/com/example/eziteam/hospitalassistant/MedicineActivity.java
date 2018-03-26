package com.example.eziteam.hospitalassistant;

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
    }


}
