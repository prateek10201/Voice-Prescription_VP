package com.example.vp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class register extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    Spinner genderlist;
    String sgender;
    Button regbt;
    EditText regname, regage,regsex, regemail, reghospital, regspcl, regmobile;
    FirebaseDatabase rootnode;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Button back = (Button) findViewById(R.id.back);
        genderlist= (Spinner) findViewById(R.id.gender);
        regbt= (Button) findViewById(R.id.regbtn);
        regname= (EditText) findViewById(R.id.fullname);
        regage= (EditText) findViewById(R.id.age);
        regemail= (EditText) findViewById(R.id.email);
        reghospital= (EditText) findViewById(R.id.hospital);
        regspcl= (EditText) findViewById(R.id.speciality);
        regmobile= (EditText) findViewById(R.id.phone);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(register.this, login_doc.class);
                startActivity(intent);
                finish();
            }
        });

        regbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rootnode = FirebaseDatabase.getInstance();
                reference = rootnode.getReference("user");

                String name= regname.getText().toString();
                String age= regage.getText().toString();
                String email= regemail.getText().toString();
                String hospital= reghospital.getText().toString();
                String spcl= regspcl.getText().toString();
                String mobile= regmobile.getText().toString();
                String gender= sgender;

                if(TextUtils.isEmpty(name)){
                    toastMessage("Please fill all fields");
                    return;
                }
                if(TextUtils.isEmpty(age)){
                    toastMessage("Please fill all fields");
                    return;
                }
                if(TextUtils.isEmpty(email)){
                    toastMessage("Please fill all fields");
                    return;
                }
                if(TextUtils.isEmpty(hospital)){
                    toastMessage("Please fill all fields");
                    return;
                }
                if(TextUtils.isEmpty(spcl)){
                    toastMessage("Please fill all fields");
                    return;
                }
                if(TextUtils.isEmpty(mobile)){
                    toastMessage("Please fill all fields");
                    return;
                }

                UserHelperClass userHelperClass= new UserHelperClass(name,age,gender,email,hospital,spcl,mobile,"");

                reference.child(name).setValue(userHelperClass);
                toastMessage("clicked");
                Intent intent = new Intent(register.this, splash_register.class);
                startActivity(intent);
                finish();
            }
        });


        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.sex, R.layout.support_simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        genderlist.setAdapter(adapter);

        genderlist.setOnItemSelectedListener(this);


    }
    private void toastMessage(String msg)
    {
        Toast.makeText(register.this,msg,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
        sgender = adapterView.getItemAtPosition(position).toString();
        toastMessage(sgender + " choosed");
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}