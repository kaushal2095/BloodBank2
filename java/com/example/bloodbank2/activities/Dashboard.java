package com.example.bloodbank2.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.bloodbank2.R;

public class Dashboard extends AppCompatActivity {

    private ImageView donar,blood;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        donar=findViewById(R.id.dashboard_requestDonar);
        blood=findViewById(R.id.dashboard_requestBlood);

        donar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Dashboard.this,DonateActivity.class);
                startActivity(intent);
            }
        });

        blood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Dashboard.this,RequestActivity.class);
                startActivity(intent);
            }
        });

    }
}
