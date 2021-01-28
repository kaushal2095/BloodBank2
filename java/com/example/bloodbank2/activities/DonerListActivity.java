package com.example.bloodbank2.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.example.bloodbank2.ListItem;
import com.example.bloodbank2.R;
import com.example.bloodbank2.container.DonarData;
import com.example.bloodbank2.container.dbHelper;

import java.util.ArrayList;

public class DonerListActivity extends AppCompatActivity {

    String bloodgrp,city;
    ArrayList<DonarData> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doner_list);

        bloodgrp=getIntent().getStringExtra("bloodgrp");
        city=getIntent().getStringExtra("city");

        data= dbHelper.getDonerList(this,bloodgrp,city);

        FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();

        for(DonarData d:data){
            ListItem listItem=new ListItem();
            Bundle b=new Bundle();
            b.putInt("Id",d.id);
            b.putString("Name",d.full_name);
            b.putString("City",d.city);
            b.putString("Area",d.area);
            listItem.setArguments(b);

            fragmentTransaction.add(R.id.ListHolder,listItem);
        }

         fragmentTransaction.commit();

    }
}
