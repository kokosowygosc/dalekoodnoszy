package com.example.eziteam.hospitalassistant;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

public class MenuActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        BottomNavigationView bottomNavigationView = (BottomNavigationView)findViewById(R.id.navigation);
        bottomNavigationView.setSelectedItemId(R.id.navigation_menu);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_home:
                        Toast.makeText(MenuActivity.this, "HOME", Toast.LENGTH_SHORT).show();
                        Intent intentHome = new Intent(getApplicationContext(), HomeActivity.class);
                        startActivity(intentHome);
                        break;
                    case R.id.navigation_menu:
                        Toast.makeText(MenuActivity.this, "MENU", Toast.LENGTH_SHORT).show();
                        Intent intentMenu = new Intent(getApplicationContext(), MenuActivity.class);
                        startActivity(intentMenu);
                        break;
                    case R.id.navigation_exit:
                        Toast.makeText(MenuActivity.this, "EXIT", Toast.LENGTH_SHORT).show();
                        break;

                }
                return true;
            }
        });

        ImageButton patientButton = (ImageButton) findViewById(R.id.patientButton);
        ImageButton medicineButton = (ImageButton) findViewById(R.id.medicineButton);
        ImageButton temperatureButton = (ImageButton) findViewById(R.id.temperatureButton);
        ImageButton pressureButton = (ImageButton) findViewById(R.id.pressureButton);
        ImageButton settingsButton = (ImageButton) findViewById(R.id.settingsButton);
        ImageButton qrButton = (ImageButton) findViewById(R.id.qrButton);

        patientButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent patientIntent = new Intent(getApplicationContext(),PatientActivity.class);
                startActivity(patientIntent);
            }
        });

        medicineButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent medicineIntent = new Intent(getApplicationContext(),MedicineActivity.class);
                startActivity(medicineIntent);
            }
        });

        temperatureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent temperatureIntent = new Intent(getApplicationContext(),TemperatureActivity.class);
                startActivity(temperatureIntent);
            }
        });

        pressureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pressureIntent = new Intent(getApplicationContext(),PressureActivity.class);
                startActivity(pressureIntent);
            }
        });

        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent settingsIntent = new Intent(getApplicationContext(),SettingsActivity.class);
                startActivity(settingsIntent);
            }
        });

        qrButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent qrIntent = new Intent(getApplicationContext(),QrActivity.class);
                startActivity(qrIntent);
            }
        });


    }
}
