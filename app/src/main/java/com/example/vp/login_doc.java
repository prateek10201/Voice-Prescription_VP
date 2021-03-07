package com.example.vp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class login_doc extends AppCompatActivity {

    EditText username, passcode;
    Button loginbtn;
    TextView reg;
    CheckBox remember;
    ProgressBar progressBar;
    TextView forgotPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_doc);

        reg= (TextView) findViewById(R.id.register);
        username= (EditText) findViewById(R.id.username);
        passcode= (EditText) findViewById(R.id.password);
        loginbtn= (Button) findViewById(R.id.login);
        remember= (CheckBox) findViewById(R.id.checklogged);
        progressBar= (ProgressBar) findViewById(R.id.progress_bar);
        forgotPassword= (TextView) findViewById(R.id.needhelp);

        progressBar.setVisibility(View.INVISIBLE);

        SharedPreferences preferences= getSharedPreferences("checkbox", MODE_PRIVATE);
        String checkbox = preferences.getString("remember","");

        if (checkbox.equals("true"))
        {
                Intent intent= new Intent(login_doc.this, MainActivity.class);
                startActivity(intent);
                finish();
        }

        else if(checkbox.equals("false"))
        {
            toastMessage("Please Sign in!");
        }

        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name,pass,gname,gpass;
                /*name="prateek";
                pass="10201";*/

                gname= username.getText().toString().trim();
                gpass= passcode.getText().toString().trim();

                if(TextUtils.isEmpty(gname))
                {
                    toastMessage("Please fill all fields");
                    return;
                }

                if (TextUtils.isEmpty(gpass))
                {
                    toastMessage("Please fill all fields");
                    return;
                }

                if (!TextUtils.isEmpty(gname) && !TextUtils.isEmpty(gname))
                {
                    isvalidUser();
                }

                /*if(gname.equals(name) && gpass.equals(pass))
                {
                    Intent intent= new Intent(login_doc.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }*/
            }
        });



        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(login_doc.this,register.class);
                startActivity(intent);
            }
        });

        remember.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {

                if (compoundButton.isChecked()){
                    SharedPreferences preferences= getSharedPreferences("checkbox", MODE_PRIVATE);
                    SharedPreferences.Editor editor= preferences.edit();
                    editor.putString("remember", "true");
                    editor.apply();
                    toastMessage("Checked");
                }

                else if (!compoundButton.isChecked()){
                    SharedPreferences preferences= getSharedPreferences("checkbox", MODE_PRIVATE);
                    SharedPreferences.Editor editor= preferences.edit();
                    editor.putString("remember", "false");
                    editor.apply();
                    toastMessage("Unchecked");

                }
            }
        });

        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(login_doc.this, splash_updatepassword.class);
                startActivity(intent);
                finish();
            }
        });

    }

    private void isvalidUser(){

        final String rname = username.getText().toString().trim();
        final String rpasscode = passcode.getText().toString().trim();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("user");
        Query checkUser = reference.orderByChild("dname").equalTo(rname);

        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.exists())
                {

                    String passwordDB = snapshot.child(rname).child("dpass").getValue(String.class);
                    if (passwordDB.equals(rpasscode))
                    {
                        String nameDB = snapshot.child(rname).child("dname").getValue(String.class);
                        String ageDB = snapshot.child(rname).child("dage").getValue(String.class);
                        String emailDB = snapshot.child(rname).child("demail").getValue(String.class);
                        String genderDB = snapshot.child(rname).child("dgender").getValue(String.class);
                        String hospitalDB = snapshot.child(rname).child("dhospital").getValue(String.class);
                        String mobDB = snapshot.child(rname).child("dmob").getValue(String.class);
                        String spclDB = snapshot.child(rname).child("dspcl").getValue(String.class);

                        Intent intent= new Intent(getApplicationContext(),MainActivity.class);
                        intent.putExtra("dbname", nameDB);
                        intent.putExtra("dbage", ageDB);
                        intent.putExtra("dbgender", genderDB);
                        intent.putExtra("dbmob", mobDB);
                        intent.putExtra("dbhospital", hospitalDB);
                        intent.putExtra("dbspcl", spclDB);
                        intent.putExtra("dbemail", emailDB);

                        startActivity(intent);
                        progressBar.setVisibility(View.VISIBLE);
                        finish();
                    }
                    else{
                        toastMessage("Wrong Password");
                        passcode.requestFocus();
                    }
                }
                else {
                    toastMessage("No such User Exists");
                    username.requestFocus();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    private void toastMessage(String msg)
    {
        Toast.makeText(login_doc.this,msg,Toast.LENGTH_SHORT).show();
    }


}