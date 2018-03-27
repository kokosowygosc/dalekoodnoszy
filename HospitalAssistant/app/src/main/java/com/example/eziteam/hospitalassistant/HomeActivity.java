    package com.example.eziteam.hospitalassistant;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.Toast;

    public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        BottomNavigationView bottomNavigationView = (BottomNavigationView)findViewById(R.id.navigation);
        bottomNavigationView.setSelectedItemId(R.id.navigation_home);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_home:
                        Toast.makeText(HomeActivity.this, "HOME", Toast.LENGTH_SHORT).show();
                        Intent intentHome = new Intent(getApplicationContext(), HomeActivity.class);
                        startActivity(intentHome);
                        break;
                    case R.id.navigation_menu:
                        Toast.makeText(HomeActivity.this, "MENU", Toast.LENGTH_SHORT).show();
                        Intent intentMenu = new Intent(getApplicationContext(), MenuActivity.class);
                        startActivity(intentMenu);
                        break;
                    case R.id.navigation_exit:
                        Toast.makeText(HomeActivity.this, "EXIT", Toast.LENGTH_SHORT).show();
                        break;

                }
                return true;
            }
        });

    }
}
