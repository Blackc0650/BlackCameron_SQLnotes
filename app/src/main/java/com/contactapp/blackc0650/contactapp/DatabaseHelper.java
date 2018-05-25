package com.contactapp.blackc0650.contactapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "ContactApp.db";
    public static final String TABLE_NAME = "ContactApp_table";
    public static final String ID = "ID";
    public static final String COLUMN_NAME_CONTACT = "contact";
    public static final String COLUMN_NAME_PHONE = "phone";
    public static final String COLUMN_NAME_ADDRESS = "address";
    public static final String SQL_CREATE_ENTRIES = "CREATE TABLE " + TABLE_NAME + " (" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            COLUMN_NAME_CONTACT + " TEXT," +
            COLUMN_NAME_PHONE + " TEXT," +
            COLUMN_NAME_ADDRESS + " TEXT)";
    public static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + TABLE_NAME;
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        //SQLiteDatabase db = this.getWritableDatabase();
        Log.d("ContactApp","DatabaseHelper: constructed the DatabaseHelper");
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d("ContactApp","DatabaseHelper: creating database");
        db.execSQL(SQL_CREATE_ENTRIES);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d("ContactApp","DatabaseHelper: upgrading database");
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }
    public boolean insertData(String name,String phone,String address) {
        Log.d("ContactApp","DatabaseHelper: inserting data");
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_NAME_CONTACT, name);
        contentValues.put(COLUMN_NAME_PHONE, phone);
        contentValues.put(COLUMN_NAME_ADDRESS, address);
        long result = sqLiteDatabase.insert(TABLE_NAME,null,contentValues);
        if(result == -1)
            Log.d("ContactApp","DatabaseHelper: Contact insert - FAILED");
        else
            Log.d("ContactApp","DatabaseHelper: Contact insert - PASSED RESULT=" + result);
        return result != -1;
    }
    public Cursor getAllData() {
        Log.d("ContactApp","DatabaseHelper: calling getAllData method");
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("select * from " + TABLE_NAME,null);
        return cursor;
    }
}
