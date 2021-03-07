package com.example.vp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class send_email extends AppCompatActivity {

    Button sendemail;
    EditText emailtext;
    String mailaddress,Sname,filename,body,date,subject;
    private static final int STORAGE_CODE = 1000;
    private static final String TAG = "send_email";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_email);

        Intent intent= getIntent();
        filename = intent.getStringExtra("message");
        Sname = intent.getStringExtra("name");

        sendemail = (Button) findViewById(R.id.send_btn_email);
        emailtext = (EditText) findViewById(R.id.emailText);

        date = new SimpleDateFormat("dd-MM-yyyy",
                Locale.getDefault()).format(System.currentTimeMillis());
        subject = "Prescription dated :"+date;
        body = "Respected Sir/Madam,\nPlease find the prescription attached.\nYour visit was on date: "+date+"."
                +"\n\nThank You";

        sendemail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mailaddress = emailtext.getText().toString();

                if (TextUtils.isEmpty(mailaddress))
                {
                    toastMessage("Please fill all fields");
                    return;
                }

                String spath = getExternalFilesDir("/Voiceprescriptions/").getPath();
                Log.d(TAG, "onClick: location found" + spath);
                File filelocation = new File(spath,filename);
                Log.d(TAG,"Made the file object");
                Uri path = Uri.fromFile(filelocation);
                Log.d(TAG,"Made the Uri object");
                String[] email = new String[1];
                email[0] = mailaddress;
                Intent intent = new Intent(Intent.ACTION_VIEW);
                Log.d(TAG,"Made the intent");
                intent.setData(Uri.parse("mailto:"));
                Log.d(TAG,"Made the intent 2");
                intent.putExtra(Intent.EXTRA_EMAIL,email);
                Log.d(TAG,"Made the intent 3");
                intent.putExtra(Intent.EXTRA_STREAM,path);
                Log.d(TAG,"Made the intent 4");
                intent.putExtra(Intent.EXTRA_SUBJECT,subject);
                Log.d(TAG,"Made the intent 5");
                intent.putExtra(Intent.EXTRA_TEXT,body);
                Log.d(TAG,"Made the intent 6");
                startActivity(intent);
                toastMessage("Redirecting, one moment please");
                finish();
            }
        });

    }
    private void toastMessage(String msg)
    {
        Toast.makeText(send_email.this,msg,Toast.LENGTH_SHORT).show();
    }
}