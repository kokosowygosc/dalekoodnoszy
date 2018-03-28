    package com.example.eziteam.hospitalassistant;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

    public class HomeActivity extends AppCompatActivity {
    SharedPreferences.Editor editor;
    Boolean testMode=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        editor = getSharedPreferences("sharedData", MODE_PRIVATE).edit();
        editor.putString("pesel", "");
        editor.putString("id","");
        editor.putBoolean("testMode",false);
        editor.apply();

        SharedPreferences prefs = getSharedPreferences("sharedData", MODE_PRIVATE);
        Boolean restoredText = prefs.getBoolean("testMode", false);
        if (restoredText) {
            testMode = prefs.getBoolean("testMode", false);
        }

        Switch testModeSwitch = findViewById(R.id.switchTestEnv);
        if(testMode){
            testModeSwitch.setChecked(true);
        } else{
            testModeSwitch.setChecked(false);
        }
        testModeSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                editor.putBoolean("testMode", isChecked);
                editor.apply();
                if(isChecked){
                    //Toast.makeText(getApplicationContext(), "1", Toast.LENGTH_SHORT).show();

                    editor = getSharedPreferences("sharedData", MODE_PRIVATE).edit();
                    editor.putString("id", "999999999");
                    editor.apply();

                    Intent target = new Intent(getApplicationContext(), MenuActivity.class);
                    startActivity(target);
                } else {
                    //Toast.makeText(getApplicationContext(), "0", Toast.LENGTH_SHORT).show();
                }
            }
        });

        final Activity activity = this;
        ImageButton qrScanButton = (ImageButton) findViewById(R.id.qrScanButton);

        qrScanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentIntegrator intentIntegrator = new IntentIntegrator(activity);
                intentIntegrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
                intentIntegrator.setPrompt("Scan");
                intentIntegrator.setCameraId(0);
                intentIntegrator.setBeepEnabled(false);
                intentIntegrator.setBarcodeImageEnabled(false);
                intentIntegrator.initiateScan();

            }
        });

        BottomNavigationView bottomNavigationView = (BottomNavigationView)findViewById(R.id.navigation);
        bottomNavigationView.setSelectedItemId(R.id.navigation_home);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_home:
                        Intent intentHome = new Intent(getApplicationContext(), HomeActivity.class);
                        startActivity(intentHome);
                        break;
                    case R.id.navigation_menu:
                        Intent intentMenu = new Intent(getApplicationContext(), MenuActivity.class);
                        startActivity(intentMenu);
                        break;
                    case R.id.navigation_exit:
                        break;
                }
                return true;
            }
        });

    }

        @Override
        protected void onActivityResult(int requestCode, int resultCode, Intent data) {

            IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
            if(result != null) {
                if(result.getContents() == null) {
                    Toast.makeText(this, "You cancelled scanning.", Toast.LENGTH_SHORT).show();
                } else {
                    if(new String("999999999").equals(result.getContents().toString())) {
                        //condition hardcoded for tests
                        SharedPreferences.Editor editor3 = getSharedPreferences("sharedData", MODE_PRIVATE).edit();
                        editor3.putString("id", result.getContents());
                        editor3.apply();

                        Intent target = new Intent(this, MenuActivity.class);
                        startActivity(target);
                    } else {
                        Toast.makeText(this, "You have not access for app!", Toast.LENGTH_LONG).show();
                    }

                }
            } else {
                super.onActivityResult(requestCode, resultCode, data);
            }

        }
}
