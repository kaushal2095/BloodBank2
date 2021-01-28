package com.example.bloodbank2.container;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class dbHelper extends SQLiteOpenHelper {

    public static final int version=1;
    public static final String name="Doner.db";

   private static final String SQL_CREATE_ENTRIES="CREATE TABLE Doner ("+
                    "Id INTEGER PRIMARY KEY,"+
                    "Name TEXT ,"+
                    "Email TEXT ,"+
                    "Phone TEXT ,"+
                   "Addr TEXT ,"+
                   "BloodGroup TEXT ,"+
                   "City TEXT ,"+
                   "Area TEXT"+ ")";

   private static final String SQL_DELETE_ENTRIES="DROP TABLE IF EXISTS Doner";

    public dbHelper(Context context) {
        super(context,name,null,version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
          db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    public static ArrayList<DonarData> getDonerList(Context context,String bloodgrp,String city){
        ArrayList<DonarData> data=new ArrayList<>();

        dbHelper dbHelper=new dbHelper(context);
        SQLiteDatabase database=dbHelper.getReadableDatabase();

        String[] projection={
                "ID",
                "Name",
                "City",
                "Area"
        };

        String where="BloodGroup LIKE ? AND City LIKE ?";

        String[] values={
                bloodgrp,
                city
        };

        String sortOrder="Id DESC";

        Cursor c=database.query(
                "Doner",
                    projection,
                    null,null,
                null,
                null,
                  sortOrder
        );

         if(c !=null && c.moveToFirst() ){
              do{
                  DonarData donarData=new DonarData();
                  donarData.id=c.getInt(c.getColumnIndexOrThrow("Id"));
                  donarData.full_name=c.getString(c.getColumnIndexOrThrow("Name"));
                  donarData.city=c.getString(c.getColumnIndexOrThrow("City"));
                  donarData.area=c.getString(c.getColumnIndexOrThrow("Area"));

                  data.add(donarData);

              }while(c.moveToNext());
         }

        return data;

    }

    public static DonarData getDonerDetail(Context context,int id){

        dbHelper dbhelper=new dbHelper(context);
        SQLiteDatabase db=dbhelper.getReadableDatabase();

        DonarData d=new DonarData();

        String[] projection={
                "Id",
                "Name",
                "Email",
                "Phone",
                "Addr",
                "BloodGroup",
                "City",
                "Area"
        };

        String where="Id LIKE ?";

        String[] values={id+""};

        Cursor c=db.query(
                "Doner",
                projection,
                where,
                values,
                null,
                null,
                null
        );

        if(c !=null && c.moveToFirst()){
            d.full_name = c.getString(c.getColumnIndexOrThrow("Name"));
            d.bloodgrp = c.getString(c.getColumnIndexOrThrow("BloodGroup"));
            d.phone = c.getString(c.getColumnIndexOrThrow("Phone"));
            d.email = c.getString(c.getColumnIndexOrThrow("Email"));
            d.addr = c.getString(c.getColumnIndexOrThrow("Addr"));
            d.area = c.getString(c.getColumnIndexOrThrow("Area"));
            d.city = c.getString(c.getColumnIndexOrThrow("City"));
        }

        return d;

    }


    public static void insert(DonarData doner,Context context){

         dbHelper dbHelper=new dbHelper(context);
         SQLiteDatabase db=dbHelper.getWritableDatabase();

         ContentValues values=new ContentValues();
         values.put("Name",doner.full_name);
         values.put("Email",doner.email);
         values.put("Phone",doner.phone);
         values.put("Addr",doner.addr);
         values.put("BloodGroup",doner.bloodgrp);
         values.put("City",doner.city);
         values.put("Area",doner.area);
         db.insert("Doner","id",values);
         db.close();
    }

}
