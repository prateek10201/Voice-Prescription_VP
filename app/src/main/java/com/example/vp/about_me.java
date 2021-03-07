package com.example.vp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class about_me extends AppCompatActivity {

    private static final String TAG = "about_me";

    Button  edit_btn;
    EditText docname, docage, docspec, dochos, docemail, docnum,docsex;
    String dname,dage,dsex,dspec,dhospital,demail,dnumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_me);

        edit_btn= (Button) findViewById(R.id.edit_btn);
        docname= (EditText) findViewById(R.id.name_doc);
        docage= (EditText) findViewById(R.id.age_doc);
        docsex= (EditText) findViewById(R.id.gender_doc);
        docspec= (EditText) findViewById(R.id.speciality_doc);
        dochos= (EditText) findViewById(R.id.hospital_doc);
        docemail= (EditText) findViewById(R.id.email_doc);
        docnum= (EditText) findViewById(R.id.number_doc);


        showdocData();


        edit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(about_me.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

    private void showdocData(){

        Intent intent= getIntent();
        dname= intent.getStringExtra("keydname");
        dage= intent.getStringExtra("keydage");
        dsex= intent.getStringExtra("keydgender");
        dspec= intent.getStringExtra("keydspcl");
        dhospital= intent.getStringExtra("keydhos");
        dnumber= intent.getStringExtra("keydmob");
        demail= intent.getStringExtra("keydemail");

        docname.setText(dname);
        docage.setText(dage);
        docspec.setText(dspec);
        dochos.setText(dhospital);
        docemail.setText(demail);
        docnum.setText(dnumber);
        docsex.setText(dsex);
    }

    private void toastMessage(String msg)
    {
        Toast.makeText(about_me.this,msg,Toast.LENGTH_SHORT).show();
    }

}