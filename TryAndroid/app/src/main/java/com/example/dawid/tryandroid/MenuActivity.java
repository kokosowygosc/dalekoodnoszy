package com.example.dawid.tryandroid;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MenuActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        String correctLogIn = getIntent().getStringExtra("login");
        Toast.makeText(this, correctLogIn, Toast.LENGTH_SHORT).show();
        //TODO przypisac o przycisku zaloguj sie z loginactivity

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_skan_QR) {
            Intent QRScan = new Intent(MenuActivity.this, QRActivity.class);
            startActivity(QRScan);
        } else if (id == R.id.nav_my_patient) {
            Intent PatientList = new Intent(MenuActivity.this, PatientListActivity.class);
            startActivity(PatientList);
        } else if (id == R.id.nav_new_patient) {
            Intent NewPatient = new Intent(MenuActivity.this, FormularActivity.class);
            startActivity(NewPatient);
        } else if (id == R.id.nav_edit_patient) {
            Intent EditPatient = new Intent(MenuActivity.this, EditPatientActivity.class);
            startActivity(EditPatient);
        } else if (id == R.id.nav_manage) {
            Intent Settings = new Intent(MenuActivity.this, SettingActivity.class);
            startActivity(Settings);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
