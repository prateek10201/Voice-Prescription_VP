package com.example.vp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class send_sms extends AppCompatActivity {

    private static final int MY_PERMISSIONS_REQUEST_SEND_SMS = 1;
    private static final String TAG = "send_sms";

    Button send;
    EditText phone;
    String number,message_send;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_sms);

        Intent intent = getIntent();
        message_send = intent.getStringExtra("message");

        send = (Button) findViewById(R.id.send_btn_sms);
        phone = (EditText) findViewById(R.id.phoneNumber);


        send.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("NewApi")
            @Override
            public void onClick(View v) {

                number = phone.getText().toString();

                if (TextUtils.isEmpty(number)){
                    toastMessage("Please fill all fields");
                    return;
                }

                checkSmsPermission();
                finish();
            }
        });
    }

    private void toastMessage(String msg)
    {
        Toast.makeText(send_sms.this,msg,Toast.LENGTH_SHORT).show();
    }



    @SuppressLint("NewApi")
    public void checkSmsPermission(){
        if(checkSelfPermission(Manifest.permission.SEND_SMS) ==
                PackageManager.PERMISSION_DENIED){

            String[] permissions = {Manifest.permission.SEND_SMS};
            requestPermissions(permissions, MY_PERMISSIONS_REQUEST_SEND_SMS);


        }
        else {
            Log.d(TAG,"First else");
            sendSms();
        }
    }

    public void sendSms()
    {
        try {
            number = phone.getText().toString();
            SmsManager smsManager = SmsManager.getDefault();
            ArrayList<String> parts = smsManager.divideMessage(message_send);
            smsManager.sendMultipartTextMessage(number, null, parts, null, null);
            Toast.makeText(getApplicationContext(), "SMS sent.",
                    Toast.LENGTH_LONG).show();
        }catch (Exception e)
        {
            Toast.makeText(this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }

}