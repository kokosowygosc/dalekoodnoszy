package com.example.eziteam.hospitalassistant;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

public class PatientActivity extends AppCompatActivity {
    EditText firstname;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient);
        firstname = findViewById(R.id.firstnameText);
        initData();
    }

    void initData() {
        firstname.setText("Krzysztof");
    }
}
