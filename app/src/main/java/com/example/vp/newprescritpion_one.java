package com.example.vp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class newprescritpion_one extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private static final int REQUEST_CODE_SPEAK_NAME = 1;
    private static final int REQUEST_CODE_SPEAK_SYMPTOMS = 2;
    private static final int REQUEST_CODE_SPEAK_DIAGNOSIS = 3;

    Button next,micname,micsymptom,micdiagnosis;
    EditText name, age, symptom, diagnosis, prescriptionnum;
    Spinner sex;
    String patname, patage, patsex, patsymp, patdiag, patcount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newprescritpion_one);

        next =(Button) findViewById(R.id.nextPage);
        micname = (Button) findViewById(R.id.pnamemice);
        micsymptom= (Button) findViewById(R.id.psymptommice);
        micdiagnosis= (Button) findViewById(R.id.pdiagnosismice);
        prescriptionnum= (EditText) findViewById(R.id.patient_count);
        name =(EditText) findViewById(R.id.pname);
        age =(EditText) findViewById(R.id.page);
        symptom =(EditText) findViewById(R.id.psymptom);
        diagnosis =(EditText) findViewById(R.id.pdiagnosis);
        sex =(Spinner) findViewById(R.id.psex);

        String count = "";
        MyDatabaseHelper helper = new MyDatabaseHelper(this);
        SQLiteDatabase database = helper.getReadableDatabase();
        try{
            long Count = DatabaseUtils.queryNumEntries(database,"VOICEPRESCRIPTION");
            database.close();
            Count = Count+1;
            count = Long.toString(Count);
        }catch(Exception e)
        {
           toastMessage(""+ e.getMessage());
        }

        patcount = count;
        prescriptionnum.setText(patcount);

        micname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onClickingmicname();
            }
        });

        micsymptom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickingmicsymptom();
            }
        });

        micdiagnosis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickingmicdiagnosis();
            }
        });


        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                patname = name.getText().toString();
                patage = age.getText().toString();
                patsymp = symptom.getText().toString();
                patdiag = diagnosis.getText().toString();

                if (TextUtils.isEmpty(patname))
                {
                    toastMessage("Please fill all fields");
                    return;
                }

                if (TextUtils.isEmpty(patage))
                {
                    toastMessage("Please fill all fields");
                    return;
                }

                if (TextUtils.isEmpty(patsex))
                {
                    toastMessage("Please fill all fields");
                    return;
                }

                if (TextUtils.isEmpty(patsymp))
                {
                    toastMessage("Please fill all fields");
                    return;
                }

                if (TextUtils.isEmpty(patdiag))
                {
                    toastMessage("Please fill all fields");
                    return;
                }


                Intent intent = new Intent(newprescritpion_one.this,newprescription_two.class);
                intent.putExtra("keyname", patname);
                intent.putExtra("keyage", patage);
                intent.putExtra("keysex", patsex);
                intent.putExtra("keysymptom", patsymp);
                intent.putExtra("keydiagnosis", patdiag);
                intent.putExtra("keypatientcount", patcount);
                startActivity(intent);
                finish();
            }
        });

        ArrayAdapter<CharSequence> adapter= ArrayAdapter.createFromResource(this, R.array.gender, R.layout.support_simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        sex.setAdapter(adapter);

        sex.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {

        patsex = adapterView.getItemAtPosition(position).toString();

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private void toastMessage(String msg)
    {
        Toast.makeText(newprescritpion_one.this,msg,Toast.LENGTH_SHORT).show();
    }

    public void onClickingmicname()
    {
        Intent i = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        i.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        i.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "en_IN");
        i.putExtra(RecognizerIntent.EXTRA_PROMPT , "Please say the name");
        i.putExtra("android.speech.extra.DICTATION_MODE",true);

        try{
            startActivityForResult(i,REQUEST_CODE_SPEAK_NAME);

        }
        catch(Exception e)
        {
            toastMessage("" + e.getMessage());
        }
    }

    public void onClickingmicsymptom()
    {
        Intent i = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        i.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        i.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "en_IN");
        i.putExtra(RecognizerIntent.EXTRA_PROMPT , "Please say the name");
        i.putExtra("android.speech.extra.DICTATION_MODE",true);

        try{
            startActivityForResult(i,REQUEST_CODE_SPEAK_SYMPTOMS);

        }
        catch(Exception e)
        {
            toastMessage("" + e.getMessage());
        }
    }

    public void onClickingmicdiagnosis()
    {
        Intent i = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        i.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        i.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "en_IN");
        i.putExtra(RecognizerIntent.EXTRA_PROMPT , "Please say the name");
        i.putExtra("android.speech.extra.DICTATION_MODE",true);

        try{
            startActivityForResult(i,REQUEST_CODE_SPEAK_DIAGNOSIS);

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

            case REQUEST_CODE_SPEAK_NAME:
                if ( resultCode == RESULT_OK && null!=data){

                    ArrayList<String> matches = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    assert matches != null;
                    name.setText(matches.get(0));
                }
                break;

            case REQUEST_CODE_SPEAK_SYMPTOMS:
                if ( resultCode == RESULT_OK && null!=data){

                    ArrayList<String> matches = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    assert matches != null;
                    symptom.setText(matches.get(0));
                }
                break;

            case REQUEST_CODE_SPEAK_DIAGNOSIS:
                if ( resultCode == RESULT_OK && null!=data)
                {
                    ArrayList<String> matches = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    assert matches != null;
                    diagnosis.setText(matches.get(0));
                }
                break;

        }
    }
}