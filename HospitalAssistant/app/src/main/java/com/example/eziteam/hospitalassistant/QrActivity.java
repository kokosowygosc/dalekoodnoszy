package com.example.eziteam.hospitalassistant;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.zxing.Result;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class QrActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr);
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

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result != null) {
            if(result.getContents() == null) {
                Toast.makeText(this, "You cancelled scanning.", Toast.LENGTH_SHORT).show();
            } else {
                //Toast.makeText(this, result.getContents(), Toast.LENGTH_LONG).show();

                //Assigned activity to target
                Intent target = new Intent(this, PatientActivity.class);

                //Assigned QRScanner result to target
                Bundle passingData = new Bundle();
                passingData.putString("pesel", result.getContents());
                target.putExtras(passingData);

                //Opens target
                startActivity(target);

            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }

    }
}
