package com.example.bloodbank2.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bloodbank2.R;
import com.example.bloodbank2.container.DonarData;
import com.example.bloodbank2.container.dbHelper;

public class Detail extends AppCompatActivity {

    DonarData data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        int id=getIntent().getIntExtra("Id",0);
        data= dbHelper.getDonerDetail(this,id);

        TextView name = (TextView) findViewById(R.id.name);
        TextView bloodgrp = (TextView) findViewById(R.id.bloodgrp);
        TextView phone = (TextView) findViewById(R.id.phone);
        TextView email = (TextView) findViewById(R.id.email);
        TextView city = (TextView) findViewById(R.id.city);
        TextView area = (TextView) findViewById(R.id.area);
        TextView addr = (TextView) findViewById(R.id.addr);

        PhoneListener phoneListener=new PhoneListener();
        TelephonyManager telephonyManager= (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);
        telephonyManager.listen(phoneListener,PhoneStateListener.LISTEN_CALL_STATE);

    }


    public void call(View view) {

        if(Build.VERSION.SDK_INT<23){
            phoneCall();
        }else{

            if(ActivityCompat.checkSelfPermission(this,Manifest.permission.CALL_PHONE)==PackageManager.PERMISSION_GRANTED){
                phoneCall();
            }else{
                final String[] PERMISSIONS_STORAGE={Manifest.permission.CALL_PHONE};
                ActivityCompat.requestPermissions(this,PERMISSIONS_STORAGE,9);
            }

        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
       boolean permissionGranted=false;
       switch (requestCode){
           case 9:
               permissionGranted=grantResults[0]==PackageManager.PERMISSION_GRANTED;
               break;
       }
       if(permissionGranted){
            phoneCall();
       }else{
           Toast.makeText(this, "You don't assign permission.", Toast.LENGTH_SHORT).show();
       }
    }

    private void phoneCall(){
        if(ActivityCompat.checkSelfPermission(this,
                Manifest.permission.CALL_PHONE
        )== PackageManager.PERMISSION_GRANTED){
            try{
                Intent intent=new Intent(Intent.ACTION_CALL,Uri.parse("tel:"+data.phone));
                startActivity(intent);
            }catch(Exception e){
                Toast.makeText(getApplicationContext(),"Your call has failed...",
                        Toast.LENGTH_LONG).show();
            }
        }else{
            Toast.makeText(this, "You don't assign permission.", Toast.LENGTH_SHORT).show();
        }
    }

    private class PhoneListener extends PhoneStateListener{

        private boolean onCall = false;

        @Override
        public void onCallStateChanged(int state, String phoneNumber) {



            switch (state){
                case TelephonyManager.CALL_STATE_RINGING:
                    // phone ringing...
                    Toast.makeText(Detail.this, phoneNumber+ " calls you",
                            Toast.LENGTH_LONG).show();
                    break;

                case TelephonyManager.CALL_STATE_OFFHOOK:
                    // one call exists that is dialing, active, or on hold
                    Toast.makeText(Detail.this, "on call...",
                            Toast.LENGTH_LONG).show();
                    //because user answers the incoming call
                    onCall = true;
                    break;

                case TelephonyManager.CALL_STATE_IDLE:
                    if (onCall == true) {
                        Toast.makeText(Detail.this, "restart app after call",
                                Toast.LENGTH_LONG).show();
                        Intent restart=getBaseContext().getPackageManager().getLaunchIntentForPackage(getBaseContext().getPackageName());
                        restart.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(restart);
                        onCall=false;
                    }
                    break;
                default:
                    break;
            }
        }
    }

}
