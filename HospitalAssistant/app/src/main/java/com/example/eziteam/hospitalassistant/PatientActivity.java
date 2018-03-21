package com.example.eziteam.hospitalassistant;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

public class PatientActivity extends AppCompatActivity {
    EditText firstName, lastName, pesel, phone, blood, postal, city, homeNumber;

    //Example data
    String[] dataArray = {"Krzysztof","Ibisz","87051811234","789456123","0Rh+","55-020","Las Palmas","1/74"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient);

        //Attribution values to the fields in XML
        firstName = findViewById(R.id.firstnameText);
        lastName = findViewById(R.id.lastnameText);
        pesel = findViewById(R.id.peselText);
        phone = findViewById(R.id.phoneText);
        blood = findViewById(R.id.bloodTypeText);
        postal = findViewById(R.id.postalAddressText);
        city = findViewById(R.id.cityText);
        homeNumber = findViewById(R.id.homeNumberText);

        initData(dataArray);
    }

    //TODO rozwazyc stworzenie klasy do pobrania danych z database

    // Processing data from database
    void initData(String[] initData)
    {
        firstName.setText(initData[0]);
        lastName.setText(initData[1]);
        pesel.setText(initData[2]);
        phone.setText(initData[3]);
        blood.setText(initData[4]);
        postal.setText(initData[5]);
        city.setText(initData[6]);
        homeNumber.setText(initData[7]);
    }
}
