package com.example.bloodbank2.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.bloodbank2.R;

public class RequestActivity extends AppCompatActivity {

    ArrayAdapter<CharSequence> Blood_List,City_List;
    Spinner bloodgrp,city;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request);

        bloodgrp=findViewById(R.id.spinner_bloodgrp);
        city=findViewById(R.id.spinner_city);

       Blood_List=ArrayAdapter.createFromResource(this,R.array.bg_arrays,android.R.layout.select_dialog_item);
       City_List=ArrayAdapter.createFromResource(this,R.array.city_0,android.R.layout.select_dialog_item);

       bloodgrp.setAdapter(Blood_List);
       city.setAdapter(City_List);

    }


    public void register(View view) {

        Intent intent=new Intent(this,DonerListActivity.class);
        intent.putExtra("bloodgrp",bloodgrp.getSelectedItem().toString());
        intent.putExtra("city",city.getSelectedItem().toString());

        startActivity(intent);

    }
}
