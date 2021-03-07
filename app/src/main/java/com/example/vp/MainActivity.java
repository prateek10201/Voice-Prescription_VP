package com.example.vp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;


public class MainActivity extends AppCompatActivity {

    EditText doc_name;
    Button logout, newpres, aboutdoc, patienthis, oldprescription;
    AlertDialog.Builder builder;
    String dname,dage,dsex,dspec,dhospital,demail,dnumber;
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        doc_name= (EditText) findViewById(R.id.docname);
        aboutdoc= (Button) findViewById(R.id.aboutme);
        newpres =(Button) findViewById(R.id.new_pres);
        logout = (Button) findViewById(R.id.signout);
        oldprescription= (Button) findViewById(R.id.old_pres);
        patienthis= (Button) findViewById(R.id.patient_history);
        builder = new AlertDialog.Builder(this);


        doctordetails();


        doc_name.setText("Dr. "+dname);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                builder.setMessage("Do you want to logout?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                SharedPreferences preferences= getSharedPreferences("checkbox", MODE_PRIVATE);
                                SharedPreferences.Editor editor= preferences.edit();
                                editor.putString("remember", "false");
                                editor.apply();

                                Intent intent = new Intent(MainActivity.this,login_doc.class);
                                startActivity(intent);
                                finish();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });

                AlertDialog alertDialog= builder.create();
                alertDialog.setTitle("Logout");
                alertDialog.show();
            }
        });

        newpres.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, newprescritpion_one.class);
                startActivity(intent);
            }
        });

        aboutdoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(MainActivity.this, about_me.class);

                intent.putExtra("keydname", dname);
                intent.putExtra("keydage", dage);
                intent.putExtra("keydgender", dsex);
                intent.putExtra("keydhos", dhospital);
                intent.putExtra("keydspcl", dspec);
                intent.putExtra("keydmob", dnumber);
                intent.putExtra("keydemail", demail);

                startActivity(intent);
            }
        });

        patienthis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(MainActivity.this, patient_history.class);
                startActivity(intent);
            }
        });

        oldprescription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(MainActivity.this,old_prescriptions.class);
                startActivity(intent);
            }
        });

        String path = Environment.getExternalStorageDirectory() + "/voiceprescription";
        Log.d(TAG,"Got the directory address");
        File dir = new File(path);
        Log.d(TAG,"created file object 1");

        if (dir.exists() && dir.isDirectory())
        {
            Log.d(TAG, "onCreate: Directory already exists!");
        }
        else {
            Log.d(TAG, "onCreate: Directory doesnt exist");

                try {
                    String newPath = Environment.getExternalStorageDirectory() + "/voiceprescription";
                    Log.d(TAG,"Got the new directory address");
                    File newdir = new File(newPath);
                    Log.d(TAG,"created new file object");
                    newdir.mkdirs();
                    Log.d(TAG,"Created the new directory");
                }catch (Exception e)
                {
                    toastMessage(""+e.getMessage());
                }
        }
    }

    private void doctordetails(){

        Intent intent = getIntent();
        dname = intent.getStringExtra("dbname");
        dage = intent.getStringExtra("dbage");
        dhospital = intent.getStringExtra("dbhospital");
        demail = intent.getStringExtra("dbemail");
        dsex = intent.getStringExtra("dbgender");
        dnumber = intent.getStringExtra("dbmob");
        dspec = intent.getStringExtra("dbspcl");
    }

    private void toastMessage(String msg)
    {
        Toast.makeText(MainActivity.this,msg,Toast.LENGTH_SHORT).show();
    }
}