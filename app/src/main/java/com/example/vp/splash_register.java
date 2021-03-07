package com.example.vp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import static android.os.SystemClock.sleep;

public class splash_register extends AppCompatActivity {

    ImageView tick, text;
    Animation anim1,anim2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_register);

        tick =(ImageView) findViewById(R.id.confirm);
        text = (ImageView) findViewById(R.id.adminmsg);

        anim1= AnimationUtils.loadAnimation(this, R.anim.splash_anim);
        anim2= AnimationUtils.loadAnimation(this, R.anim.splash_text);

        tick.startAnimation(anim1);
        text.startAnimation(anim2);

        Thread thread= new Thread(new Runnable() {
            @Override
            public void run() {

                sleep(5000);
                Intent intent= new Intent(splash_register.this, login_doc.class);
                startActivity(intent);
                finish();
            }
        });

        thread.start();

    }
}