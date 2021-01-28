package com.example.bloodbank2.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.bloodbank2.R;
import com.example.bloodbank2.container.DonarData;
import com.example.bloodbank2.container.dbHelper;

public class DonateActivity extends AppCompatActivity {

     String TAG="DonateActivity";
     ArrayAdapter<CharSequence> Blood_List;
     ArrayAdapter<CharSequence> City_List;
     ArrayAdapter<CharSequence> Area_List;

     private Spinner spinner_blood,spinner_city,spinner_area;
     private Button register;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donate);

        Blood_List=ArrayAdapter.createFromResource(this,R.array.bg_arrays,android.R.layout.select_dialog_item);
        City_List=ArrayAdapter.createFromResource(this,R.array.city_arrays,android.R.layout.select_dialog_item);
        Area_List=ArrayAdapter.createFromResource(this,R.array.city_0,android.R.layout.select_dialog_item);

        spinner_blood=findViewById(R.id.spinner_bloodgrp);
        spinner_city=findViewById(R.id.spinner_city);
        spinner_area=findViewById(R.id.spinner_area);

        register=findViewById(R.id.register);

        spinner_city.setAdapter(City_List);
        City_List.getItem(0).toString();
        spinner_area.setAdapter(Area_List);
        Blood_List.getItem(0).toString();
        spinner_blood.setAdapter(Blood_List);
        Area_List.getItem(0).toString();

        spinner_city.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.d("info", "onItemSelected: "+position);
                setCitySpinner(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerDonar();
            }
        });

    }

    private void registerDonar(){

        String msg="";

        DonarData donerData=new DonarData();

        donerData.full_name = ((EditText) findViewById(R.id.editText_name)).getText().toString();
        donerData.phone = ((EditText) findViewById(R.id.editText_phone)).getText().toString();
        donerData.email = ((EditText) findViewById(R.id.editText_email)).getText().toString();
        donerData.addr = ((EditText) findViewById(R.id.editText_address)).getText().toString();

        donerData.bloodgrp = ((Spinner)findViewById(R.id.spinner_bloodgrp)).getSelectedItem().toString();
        donerData.city = ((Spinner)findViewById(R.id.spinner_city)).getSelectedItem().toString();
        donerData.area = ((Spinner)findViewById(R.id.spinner_area)).getSelectedItem().toString();

        if(donerData.full_name.isEmpty() || donerData.phone.isEmpty() || donerData.email.isEmpty() ||  donerData.addr.isEmpty())
        {
            msg = "One or Multiple fields are Empty.\nCheck all Fields and try Again.";
            Toast.makeText(this,msg, Toast.LENGTH_LONG).show();
        }else if(!Patterns.EMAIL_ADDRESS.matcher(donerData.email).matches()){
            msg = "Invalid E-mail address.";
            Toast.makeText(this,msg,Toast.LENGTH_LONG).show();
        } else if( donerData.phone.length()<10)
        {
            msg = "Invalid Phone number.";
            Toast.makeText(this,msg,Toast.LENGTH_LONG).show();
        }else{

            dbHelper.insert(donerData,this);
            msg = "Successfully, registered as Doner.";
            Toast.makeText(this,msg,Toast.LENGTH_LONG).show();
            super.onBackPressed();
        }

    }

    private void setCitySpinner(int position) {
        String selectedId="city_"+position;

        int selectIdCode=getResources().getIdentifier(selectedId,"array",getPackageName());

        Area_List=ArrayAdapter.createFromResource(this,selectIdCode,android.R.layout.select_dialog_item);
        spinner_area.setAdapter(Area_List);

    }
}
