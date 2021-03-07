package com.example.vp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

public class old_prescriptions extends AppCompatActivity {

    SearchView patientrecord_search;
    ListView patientrecord_list;
    Button fetch_btn,delete_btn;

    private static final String TAG = "old_prescriptions";

    ArrayList<String> myList;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_old_prescriptions);

        fetch_btn = (Button) findViewById(R.id.btn_fetch);
        patientrecord_search = (SearchView) findViewById(R.id.search_pres);
        patientrecord_list = (ListView) findViewById(R.id.patient_record);
        myList = new ArrayList<String>();

        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

        MyDatabaseHelper helper = new MyDatabaseHelper(this);
        SQLiteDatabase database = helper.getReadableDatabase();

        try {
            Cursor cursor = database.rawQuery("SELECT FILENAME FROM VOICEPRESCRIPTION", new String[]{});
            if (cursor != null) {
                cursor.moveToFirst();
                do {
                    String temp = cursor.getString(0);
                    myList.add(temp);
                } while (cursor.moveToNext());

                adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, myList);
                patientrecord_list.setAdapter(adapter);

                patientrecord_search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String s) {
                        return false;
                    }

                    @Override
                    public boolean onQueryTextChange(String s) {
                        adapter.getFilter().filter(s);
                        return false;
                    }
                });


            } else {
                Toast.makeText(this, "Nothing to display", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, "" + e.getMessage(), Toast.LENGTH_LONG).show();
        }

        fetch_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    String filename = "/" + patientrecord_search.getQuery().toString();
                    if (filename.isEmpty()) {
                        Toast.makeText(old_prescriptions.this, "Please select a proper file", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(old_prescriptions.this, "" + filename, Toast.LENGTH_LONG).show();
                        //String path = Environment.getExternalStorageDirectory()+"/Prescriptions/";
                        File file = new File(getExternalFilesDir("/Voiceprescriptions").getPath() + filename);
                        Log.d(TAG, "onClick:file:" + filename);
                        Log.d(TAG, "onClick:file path:" + file);
                        Uri path = Uri.fromFile(file);
                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        Log.d("Prescript History", "Created intent");
                        intent.setDataAndType(path, "application/pdf");
                        Log.d("Prescript History", "Created intent 2");
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        Log.d("Prescript History", "Created intent 3");
                        startActivity(intent);
                        Log.d("Prescript History", "Returned from pdf");
                    }

                } catch (Exception e) {
                    Toast.makeText(old_prescriptions.this, "" + e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });

        patientrecord_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String selecteditem = patientrecord_list.getItemAtPosition(position).toString();
                patientrecord_search.setQuery(selecteditem, false);

            }
        });
    }
 
}