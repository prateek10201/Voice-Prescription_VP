package com.example.vp;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class MyDatabaseHelper extends SQLiteOpenHelper {


    private Context context;
    private static final String DATABASE_NAME = "Records.db";
    private static final int DATABASE_VERSION= 1;
    private static final String TABLE_NAME = "VOICEPRESCRIPTION";
    private static final String TABLE_PATIENT = "PATIENTINFO";

    public MyDatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context= context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String SQL = "CREATE TABLE VOICEPRESCRIPTION (_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "PATIENTNAME TEXT, FILENAME TEXT)";
        db.execSQL(SQL);

        String SQL2 = "CREATE TABLE PATIENTINFO (_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "PATIENTNAME TEXT, PDATE TEXT, PTIME TEXT)";
        db.execSQL(SQL2);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void insertPrescription(String Pname,String Fname,SQLiteDatabase db)
    {
        ContentValues contentValues = new ContentValues();
        contentValues.put("PATIENTNAME",Pname);
        contentValues.put("FILENAME",Fname);
        db.insert("VOICEPRESCRIPTION",null,contentValues);
    }

    public void insertPatient(String Pname, String date,String time, SQLiteDatabase db)
    {
        ContentValues contentValues = new ContentValues();
        contentValues.put("PATIENTNAME",Pname);
        contentValues.put("PDATE",date);
        contentValues.put("PTIME",time);
        db.insert("PATIENTINFO",null,contentValues);

    }


    void deleteAllData(){

        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_NAME);
        db.execSQL("DELETE FROM " + TABLE_PATIENT);
    }

}
