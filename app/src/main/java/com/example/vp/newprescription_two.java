package com.example.vp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class newprescription_two extends AppCompatActivity {

    private static final int REQUEST_CODE_SPEAK_PRESCRIPTION = 4;
    private static final int REQUEST_CODE_SPEAK_ADVICE = 5;

    EditText presciription, advice;
    Button  viewPreview,micprescription, micadvice;
    String patname, patage, patsex, patsymp, patdiag,patprescription, patadvice, patcount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newprescription_two);

        presciription =(EditText) findViewById(R.id.pprescription);
        advice =(EditText) findViewById(R.id.padvice);
        viewPreview =(Button) findViewById(R.id.viewpreview);
        micprescription= (Button) findViewById(R.id.pprescriptionmice);
        micadvice = (Button) findViewById(R.id.padvicemice);

        patname = getIntent().getStringExtra("keyname");
        patage = getIntent().getStringExtra("keyage");
        patsex = getIntent().getStringExtra("keysex");
        patsymp = getIntent().getStringExtra("keysymptom");
        patdiag = getIntent().getStringExtra("keydiagnosis");
        patcount =getIntent().getStringExtra("keypatientcount");

        micprescription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickingmicprescription();
            }
        });

        micadvice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCLickinigmicadvice();
            }
        });

        viewPreview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                patprescription = presciription.getText().toString();
                patadvice = advice.getText().toString();

                if (TextUtils.isEmpty(patprescription))
                {
                 toastMessage("Please fill all fields");
                 return;
                }

                if (TextUtils.isEmpty(patadvice))
                {
                    toastMessage("Please fill all fields");
                    return;
                }

                Intent intent = new Intent(newprescription_two.this, preview_page.class);

                intent.putExtra("namekey", patname);
                intent.putExtra("agekey", patage);
                intent.putExtra("sexkey", patsex);
                intent.putExtra("symptomkey", patsymp);
                intent.putExtra("diagnosiskey", patdiag);
                intent.putExtra("keyprescription", patprescription);
                intent.putExtra("keyadvice", patadvice);
                intent.putExtra("patientcountkey", patcount);
                startActivity(intent);
                finish();
            }
        });
    }

    private void toastMessage(String msg)
    {
        Toast.makeText(newprescription_two.this,msg,Toast.LENGTH_SHORT).show();
    }

    public void onClickingmicprescription(){

        Intent i = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        i.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        i.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "en_IN");
        i.putExtra(RecognizerIntent.EXTRA_PROMPT , "Please say the name");
        i.putExtra("android.speech.extra.DICTATION_MODE",true);

        try{
            startActivityForResult(i,REQUEST_CODE_SPEAK_PRESCRIPTION);

        }
        catch(Exception e)
        {
            toastMessage("" + e.getMessage());
        }
    }

    public void onCLickinigmicadvice(){

        Intent i = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        i.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        i.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "en_IN");
        i.putExtra(RecognizerIntent.EXTRA_PROMPT , "Please say the name");
        i.putExtra("android.speech.extra.DICTATION_MODE",true);

        try{
            startActivityForResult(i,REQUEST_CODE_SPEAK_ADVICE);

        }
        catch(Exception e)
        {
            toastMessage("" + e.getMessage());
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode){

            case REQUEST_CODE_SPEAK_PRESCRIPTION:
                if (resultCode == RESULT_OK && null!=data)
                {
                    ArrayList<String> matches = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    assert matches != null;
                    presciription.setText(matches.get(0));
                }
                break;

            case REQUEST_CODE_SPEAK_ADVICE:
                if (resultCode == RESULT_OK && null!=data)
                {
                    ArrayList<String> matches = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    assert matches != null;
                    advice.setText(matches.get(0));
                }
                break;
        }
    }
}
