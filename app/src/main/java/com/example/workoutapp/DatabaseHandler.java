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
    public static final String KEY_USERID = "id";
    public static final String KEY_PROFID = "profId";
    public static final String KEY_AGE = "age";
    public static final String KEY_LEVEL = "level";
    public static final String TABLE_USER = "user";
    public static final String KEY_FIRSTNAME = "firstName";
    public static final String KEY_LASTNAME = "lastName";


    public DatabaseHandler(Context context)
    {
        super(context, DATABASE_NAME, null,DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            String CREATE_PROFILE_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_PROFILE + " (" + KEY_PROFID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_AGE + " TEXT, " + KEY_LEVEL + " TEXT)";

            String CREATE_USER_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_USER + " (" + KEY_USERID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_FIRSTNAME + " TEXT, " + KEY_LASTNAME + " TEXT)";
            db.execSQL(CREATE_PROFILE_TABLE);
            db.execSQL(CREATE_USER_TABLE);
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
    public boolean addProfile(String age,String level)
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

    public boolean addUser(String fistname,String lastname)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues value = new ContentValues();
        value.put(KEY_FIRSTNAME,fistname);
        value.put(KEY_LASTNAME,lastname);
        Long insert = db.insert(TABLE_USER,null,value);
        if(insert >= 1)
        {
            return true;
        }
        else {
            Log.d("failedAdd","Failed to add user");
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
