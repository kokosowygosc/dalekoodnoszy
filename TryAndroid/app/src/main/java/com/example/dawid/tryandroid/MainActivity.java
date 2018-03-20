package com.example.dawid.tryandroid;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       ImageButton LoginButton = (ImageButton) findViewById(R.id.LoginButton);

        LoginButton.setOnClickListener((v) -> {
            Intent SignIn = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(SignIn);
        });


    }
}
