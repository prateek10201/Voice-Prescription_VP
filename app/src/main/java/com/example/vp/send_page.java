package com.example.vp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerHelper;


import java.io.FileOutputStream;

import java.io.StringReader;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.StringTokenizer;

public class send_page extends AppCompatActivity {

    private static final int STORAGE_CODE = 1000;

    Button sms, email, trash, done;
    String Sname, Sage, Ssex, Ssymptoms, Sdiagnosis, Sprescription, Sadvice, Sprescriptno, Name;
    String SmsMessage1, PdfMessage;
    String filename;

    private static final String TAG = "send_page";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_page);


        Name= "Dr. Prateek";

        sms = (Button) findViewById(R.id.sendSMS);
        email = (Button) findViewById(R.id.sendEmail);
        trash = (Button) findViewById(R.id.delete_btn);
        done = (Button) findViewById(R.id.btn_done);


        Sname= getIntent().getStringExtra("keypatientname");
        Sage= getIntent().getStringExtra("keypatientage");
        Ssex= getIntent().getStringExtra("keypatientsex");
        Ssymptoms= getIntent().getStringExtra("keypatientsymptom");
        Sdiagnosis= getIntent().getStringExtra("keypatientdiagnosis");
        Sprescription= getIntent().getStringExtra("keypatientprescription");
        Sadvice= getIntent().getStringExtra("keypatientadvice");
        Sprescriptno= getIntent().getStringExtra("keypatientcount");



        SmsMessage1 = Name+"\n\n" + "Name:   " + Sname + "\nAge:  " + Sage + "\nSex:  " + Ssex + "\nSymptoms:  " + Ssymptoms
                + "\n\nDiagnosis:  " + Sdiagnosis + "\n\nPrescription:  " + Sprescription + "\n\nAdvice:  " + Sadvice + "\n\n\n"+Name;

        PdfMessage = "<h1>"+Name+"</h1> <h2> </h2> <b>Prescription No. :</b> " + "<p>" + Sprescriptno + "</p> <br>" +
                "<b>Patient Name :</b> <p>" + Sname + "</p> <b>Age :</b>   <p>" + Sage + "</p>    <b>Sex :</b><p>" + Ssex +
                "</p> <b>Symptoms : </b> <p> " + Ssymptoms + "</p> <b>Diagnosis :</b> <p>" + Sdiagnosis +
                "</p> <b>Prescription :</b> <p> " + Sprescription + "</p><b>Advice :</b><p>" + Sadvice
                + "</p><h2></h2><h5>"+Name+"</h5>";

        String date = new SimpleDateFormat("dd-MM-yyyy--HH:mm",
                Locale.getDefault()).format(System.currentTimeMillis());
        filename = Sname + date + ".pdf";
        checkpdfpermission();

        String daten = new SimpleDateFormat("dd-MM-yyyy/HH:mm",
                Locale.getDefault()).format(System.currentTimeMillis());
        StringTokenizer stringTokenizer = new StringTokenizer(daten, "/");
        String justDate = stringTokenizer.nextToken();
        String justTime = stringTokenizer.nextToken();

        MyDatabaseHelper helper = new MyDatabaseHelper(this);
        SQLiteDatabase database = helper.getWritableDatabase();
        helper.insertPrescription(Sname, filename, database);
        Log.d("ERROR!!!", "calling insertpatient");
        helper.insertPatient(Sname, justDate, justTime, database);


        sms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(send_page.this, send_sms.class);
                intent.putExtra("message", SmsMessage1);
                startActivity(intent);
            }
        });

        email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(send_page.this, send_email.class);
                intent.putExtra("message", filename);
                intent.putExtra("name", Sname);
                startActivity(intent);
            }
        });

        trash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                MyDatabaseHelper myDatabaseHelper = new MyDatabaseHelper(send_page.this);
                myDatabaseHelper.deleteAllData();

                Intent intent = new Intent(send_page.this, MainActivity.class);
                startActivity(intent);
                toastMessage("All Files Deleted");
                finish();
            }
        });

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(send_page.this, MainActivity.class);
                startActivity(intent);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                finish();
            }
        });
    }

    private void toastMessage(String msg) {
        Toast.makeText(send_page.this, msg, Toast.LENGTH_SHORT).show();
    }

    public void checkpdfpermission() {
        Log.d(TAG, "Entered checkpdfpermisssion");
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {


            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) ==
                    PackageManager.PERMISSION_DENIED) {

                String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
                requestPermissions(permissions, STORAGE_CODE);
            } else {
                Log.d(TAG, "First else");
                savePdf();
            }
        } else {
            Log.d(TAG, "Second else");
            savePdf();
        }
    }

    private void savePdf() {

        Log.d(TAG, "Entered savePdf");
        Document mDoc = new Document();


        String mFilePath = getExternalFilesDir(null) + "/Voiceprescriptions/" + filename;
        Log.d(TAG, "Got the filepath");
        Log.d(TAG, mFilePath);

        try {

            PdfWriter pdfWriter;
            pdfWriter = PdfWriter.getInstance(mDoc, new FileOutputStream(mFilePath));
            Log.d(TAG, "Got the pdwriter object");
            mDoc.open();
            Log.d(TAG, "opened the document");


            XMLWorkerHelper worker = XMLWorkerHelper.getInstance();
            Log.d(TAG, "Got the object of XMLWORkER");
            worker.parseXHtml(pdfWriter, mDoc, new StringReader(PdfMessage));
            Log.d(TAG, "successfully wrote in the pdf");

            mDoc.close();
            Log.d(TAG, "Successfully closed the file");
            Toast.makeText(this, filename + "\n is saved to \n" + mFilePath, Toast.LENGTH_LONG).show();


        } catch (Exception e) {
            toastMessage("" + e.getMessage());
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        switch (requestCode) {

            case STORAGE_CODE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    savePdf();

                } else {
                    toastMessage("Permission has been denied");
                }
            }

        }

    }

}