package com.example.workoutapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "workoutApp.db";
    public static final String TABLE_PROFILE = "profile";
    public static final String KEY_ID = "id";
    public static final String KEY_AGE = "age";
    public static final String KEY_LEVEL = "level";

    public DatabaseHandler(Context context)
    {
        super(context, DATABASE_NAME, null,DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            String CREATE_PROFILE_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_PROFILE + " (" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_AGE + " TEXT, " + KEY_LEVEL + " TEXT)";
            db.execSQL(CREATE_PROFILE_TABLE);
            System.out.print("yest");
        } catch (Exception e) {
            System.out.print("wrong " + e.getMessage());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
    public boolean addContact(String age,String level)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues value = new ContentValues();
       value.put(KEY_AGE,age);
       value.put(KEY_LEVEL,level);
        Long insert = db.insert(TABLE_PROFILE,null,value);
        if(insert >= 1)
        {
            return true;
        }
        else {
            Log.d("failedAdd","Failed to add profile");
            return false;
        }

    }
}
