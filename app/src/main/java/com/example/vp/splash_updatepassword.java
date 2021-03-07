package com.example.vp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import static android.os.SystemClock.sleep;

public class splash_updatepassword extends AppCompatActivity {

    ImageView text1, text2;
    Animation anim1,anim2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_updatepassword);

        text1= (ImageView) findViewById(R.id.adminmsgupdate);
        text2= (ImageView) findViewById(R.id.emailupdate);

        anim1= AnimationUtils.loadAnimation(this, R.anim.splash_anim);
        anim2= AnimationUtils.loadAnimation(this, R.anim.splash_text);

        text1.startAnimation(anim1);
        text2.startAnimation(anim2);

        Thread thread= new Thread(new Runnable() {
            @Override
            public void run() {

                sleep(3500);
                Intent intent= new Intent(splash_updatepassword.this, login_doc.class);
                startActivity(intent);
                finish();
            }
        });

        thread.start();

    }
}