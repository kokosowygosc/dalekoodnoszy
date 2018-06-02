package com.example.eziteam.hospitalassistant;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class GeneratorActivity extends AppCompatActivity {

    private ImageView image;

    // Można wczytać liste mailów z bazy szpitala.
    private String[] emails = {"goganna14@gmail.com"};
    private Button button_send;
    private Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generator);

        image = findViewById(R.id.image);
        button_send = findViewById(R.id.button_send);

        Bundle recvData = getIntent().getExtras();
        String QRCode = recvData.getString("QR");

        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        try {
            BitMatrix bitMatrix = multiFormatWriter.encode(QRCode, BarcodeFormat.QR_CODE, 200,200);
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            bitmap = barcodeEncoder.createBitmap(bitMatrix);
            image.setImageBitmap(bitmap);

            button_send.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        File root = Environment.getExternalStorageDirectory();
                        if (root.canWrite()){
                            File pic = new File(root, "QR_Code.png");
                            FileOutputStream out = new FileOutputStream(pic);
                            bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
                            out.flush();
                            out.close();

                            Intent SendEmail = new Intent(Intent.ACTION_SEND);
                            SendEmail.putExtra(Intent.EXTRA_EMAIL, emails);
                            SendEmail.setType("image/*");
                            SendEmail.setType("message/rfc822");
                            SendEmail.putExtra(Intent.EXTRA_SUBJECT, "QR Code");
                            SendEmail.putExtra(Intent.EXTRA_TEXT, "Generated QR Code from App:");
                            SendEmail.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(pic));
                            startActivity(Intent.createChooser(SendEmail, "Send mail..."));
                        }
                    } catch (IOException e) {
                        Log.e("Error", "Could not write file " + e.getMessage());
                    }
                }
            });
        } catch (WriterException e) {
            e.printStackTrace();
        }
    }
}
