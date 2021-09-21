package com.example.workoutapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "workoutApp.db";
    public static final String TABLE_PROFILE = "profile";
    private static final String ID = "id";
    private static final String FIRST_NAME = "firstName";
    private static final String LASTNAME = "lastname";
    private static final String DEPARTMENT = "department";
    private static final String USERNAME = "username";
    private static final String PASSWORD = "password";
    private static final String AGE = "age";
    private static final String LEVEL = "level";


    public DatabaseHandler(Context context)
    {
        super(context, DATABASE_NAME, null,DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            String CREATE_PROFILE_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_PROFILE + " (" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + FIRST_NAME + " TEXT, " + LASTNAME + " TEXT, " + DEPARTMENT + " TEXT, " + USERNAME + " TEXT, " + PASSWORD + " TEXT, " + AGE + " TEXT, " + LEVEL + " TEXT)";
            db.execSQL(CREATE_PROFILE_TABLE);
        } catch (Exception e) {
            System.out.print("wrong " + e.getMessage());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PROFILE);
        onCreate(db);
    }

    //Write to database
    public boolean addProfile(String firstName,String lastName,String department,String username,String password,String age,String level)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues value = new ContentValues();
        value.put(FIRST_NAME,firstName);
        value.put(LASTNAME,lastName);
        value.put(DEPARTMENT,department);
        value.put(USERNAME,username);
        value.put(PASSWORD,password);
        value.put(AGE,age);
        value.put(LEVEL,level);
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

    //Read from database
    //Cursor provides read-write access to the result set.
    public Cursor getProfiles(){
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM profile", null);
        return cursor;

    }
}
