package com.example.vp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import static android.os.SystemClock.sleep;

public class splash_welcome extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_welcome);

        Thread thread= new Thread(new Runnable() {
            @Override
            public void run() {

                sleep(1000);
                Intent intent= new Intent(splash_welcome.this, login_doc.class);
                startActivity(intent);
                finish();
            }
        });

        thread.start();
    }
}