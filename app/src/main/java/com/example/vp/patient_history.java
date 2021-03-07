package com.example.vp;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class patient_history extends AppCompatActivity {

    TextView patientHistoryDisplay;
    ArrayList<String> NameList, DateList, TimeList;

    private static final String TAG = "patient_history";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_history);

        patientHistoryDisplay= (TextView) findViewById(R.id.pathisshow);
        NameList = new ArrayList<>();
        DateList = new ArrayList<>();
        TimeList = new ArrayList<>();

        MyDatabaseHelper helper = new MyDatabaseHelper(this);
        SQLiteDatabase database = helper.getReadableDatabase();

        try {
            Log.d("ERROR!!!","calling cursor");
            Cursor cursor = database.rawQuery("SELECT PATIENTNAME,PDATE,PTIME FROM PATIENTINFO", new String[]{});
            Log.d("ERROR!!!","got cursor");
            if (cursor != null) {
                cursor.moveToFirst();
                do {
                    String name = cursor.getString(0);
                    String date = cursor.getString(1);
                    String time = cursor.getString(2);
                    NameList.add(name);
                    DateList.add(date);
                    TimeList.add(time);
                } while (cursor.moveToNext());

            }
        }catch (Exception e)
        {
           toastMessage(""+ e.getMessage());
        }

        if(NameList.isEmpty())
        {
            patientHistoryDisplay.setText("Still waiting for the first patient");
        }
        else
        {
            String finalString = "";
            for(int i=0;i<NameList.size();i++){
                String sno = String.valueOf((i+1));
                finalString = finalString + sno +".  "+ NameList.get(i) + " visited on "+DateList.get(i)+" at time "+TimeList.get(i)+"\n\n";
            }
            patientHistoryDisplay.setText(finalString);
        }


    }

    private void toastMessage(String msg)
    {
        Toast.makeText(patient_history.this,msg,Toast.LENGTH_SHORT).show();
    }
}