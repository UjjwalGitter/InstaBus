package com.ujjwalsingh.busticket;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;
import java.util.Random;

import github.nisrulz.screenshott.ScreenShott;

public class Invoice extends AppCompatActivity {
    int view = R.layout.activity_invoice;
    ImageView screenShort;
    private static final int REQUEST_WRITE_STORAGE = 112;
boolean g=true;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(view);
        final LinearLayout parent = findViewById(R.id.parent);
        screenShort = findViewById(R.id.screenShot);

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            boolean hasPermission = (ContextCompat.checkSelfPermission(getBaseContext(),
                    Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED);
            if (!hasPermission) {
                ActivityCompat.requestPermissions(Invoice.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                Manifest.permission.ACCESS_NETWORK_STATE, Manifest.permission.RECORD_AUDIO, Manifest.permission.MODIFY_AUDIO_SETTINGS, Manifest.permission.INTERNET
                        },
                        REQUEST_WRITE_STORAGE);
                g=false;
            } else {

            }
        }

        if (!g){
            textView = findViewById(R.id.text);
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    textView.setText("It is screen shot text");

                    screenShot(parent);
                }
            });
        }

    }

    public void screenShot(View view) {
        Bitmap bitmap = Bitmap.createBitmap(view.getWidth(),
                view.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        view.draw(canvas);
        screenShort.setImageBitmap(bitmap);
        textView.setText("click");
        saveImageToExternalStorage(bitmap);

    }

    private void saveImageToExternalStorage(Bitmap finalBitmap) {
        String path = Environment.getExternalStorageDirectory().getAbsolutePath();
        OutputStream fOutputStream = null;
        File file = new File(path, "screen.jpg");
        if (!file.exists()) {
            file.mkdirs();
        }

        try {
            fOutputStream = new FileOutputStream(file);

            finalBitmap.compress(Bitmap.CompressFormat.JPEG, 100, fOutputStream);

            fOutputStream.flush();
            fOutputStream.close();

            MediaStore.Images.Media.insertImage(getContentResolver(), file.getAbsolutePath(), file.getName(), file.getName());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Toast.makeText(this, "Save Failed", Toast.LENGTH_SHORT).show();
            return;
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Save Failed", Toast.LENGTH_SHORT).show();
            return;
        }
    }

}