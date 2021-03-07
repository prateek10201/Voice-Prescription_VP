package com.example.vp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.annotation.Documented;
import java.util.StringTokenizer;

public class preview_page extends AppCompatActivity {

    Button confirm;
    EditText namep, agep, sexp, sympp, diagp, presp, advicep, countp;
    String patname, patage, patsex, patsymp, patdiag, patprescription, patadvice, patcount, Name;

    private static final String TAG = "preview_page";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview_page);

        confirm = (Button) findViewById(R.id.confirm_btn);
        countp = (EditText)findViewById(R.id.count_p);
        namep =(EditText) findViewById(R.id.name_p);
        agep =(EditText) findViewById(R.id.age_p);
        sexp = (EditText) findViewById(R.id.gender_p);
        sympp = (EditText) findViewById(R.id.symptom_p);
        diagp =(EditText) findViewById(R.id.diagnosis_p);
        presp =(EditText) findViewById(R.id.prescription_p);
        advicep = (EditText) findViewById(R.id.advice_p);


        patname = getIntent().getStringExtra("namekey");
        patage = getIntent().getStringExtra("agekey");
        patsex = getIntent().getStringExtra("sexkey");
        patsymp = getIntent().getStringExtra("symptomkey");
        patdiag = getIntent().getStringExtra("diagnosiskey");
        patprescription = getIntent().getStringExtra("keyprescription");
        patadvice = getIntent().getStringExtra("keyadvice");
        patcount = getIntent().getStringExtra("patientcountkey");

        countp.setText(patcount);
        namep.setText(patname);
        agep.setText(patage);
        sexp.setText(patsex);
        sympp.setText(patsymp);
        diagp.setText(patdiag);
        presp.setText(patprescription);
        advicep.setText(patadvice);

        BufferedReader input = null;
        File file = null;
        try{
            file = new File(getFilesDir(),"AboutMe.txt");
            input = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
            String line;
            line = input.readLine();
            StringTokenizer str = new StringTokenizer(line,":");
            Name = str.nextToken();

        }catch(Exception e)
        {
            toastMessage(""+ e.getMessage());
        }


        File dir = new File(getExternalFilesDir(null)+"/Voiceprescriptions");
        if (dir.exists() && dir.isDirectory())
        {
            Log.d(TAG, "onCreate: Direvtory Exists");
        }
        else
        {
            Log.d(TAG, "onCreate: Directory doenst exist");

            try{
                if(dir.mkdir()){
                    Log.d(TAG,"Directory created");
                }
                else{
                    Log.d(TAG,"Directory not created");
                }
            }catch (Exception e)
            {
                toastMessage(""+ e.getMessage());
            }
        }

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name,age,sex,symptom,diagnosis,prescription,advice,count;

                name= namep.getText().toString();
                age= agep.getText().toString();
                sex= sexp.getText().toString();
                symptom= sympp.getText().toString();
                diagnosis= diagp.getText().toString();
                prescription= presp.getText().toString();
                advice= advicep.getText().toString();
                count= countp.getText().toString();



                Intent intent = new Intent(preview_page.this, send_page.class);
                intent.putExtra("keypatientname",name);
                intent.putExtra("keypatientage",age);
                intent.putExtra("keypatientsex",sex);
                intent.putExtra("keypatientsymptom",symptom);
                intent.putExtra("keypatientdiagnosis",diagnosis);
                intent.putExtra("keypatientprescription",prescription);
                intent.putExtra("keypatientadvice",advice);
                intent.putExtra("keypatientcount",count);
                startActivity(intent);
                finish();
            }
        });
    }

    private void toastMessage(String msg)
    {
        Toast.makeText(preview_page.this,msg,Toast.LENGTH_SHORT).show();
    }
}