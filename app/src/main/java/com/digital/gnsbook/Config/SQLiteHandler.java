package com.digital.gnsbook.Config;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.HashMap;

public class SQLiteHandler extends SQLiteOpenHelper {

    private static final String TAG = SQLiteHandler.class.getSimpleName();

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "android_api";

    // Login table name
    private static final String TABLE_USER = "user";

    // Login Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_UID = "customer_id";
    private static final String KEY_REFERRALID = "referral_id";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_MOBILE="mobile";
    private static final String KEY_NAME = "name";
    private static final String KEY_CREATED_AT = "created_at";
    private static final String KEY_AGENT_STATUS = "status";
    private static final String KEY_AGENT_ID = "aid";



    public SQLiteHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_LOGIN_TABLE = "CREATE TABLE " + TABLE_USER + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_UID + " TEXT,"+ KEY_REFERRALID + " TEXT,"
                + KEY_EMAIL + " TEXT UNIQUE," + KEY_MOBILE + " TEXT,"
                + KEY_NAME + " TEXT," + KEY_CREATED_AT + " TEXT," + KEY_AGENT_STATUS+ " TEXT," + KEY_AGENT_ID + " TEXT" + ")";


        db.execSQL(CREATE_LOGIN_TABLE);

        Log.d(TAG, "Database tables created");
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);

        // Create tables again
        onCreate(db);
    }

    /**
     * Storing user details in database
     * */
    public void addUser(String uid, String referralid, String email, String mobile, String name , String created_at, String status,String aid) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_UID, uid); // USER ID
        values.put(KEY_REFERRALID, referralid); // referral_id
        values.put(KEY_EMAIL, email); // Email
        values.put(KEY_MOBILE, mobile); // MOBILE
        values.put(KEY_NAME, name); // name
        values.put(KEY_CREATED_AT, created_at); // Created At
        values.put(KEY_AGENT_STATUS, status); // Status
        values.put(KEY_AGENT_ID, aid); // Status

        // Inserting Row
        long id = db.insert(TABLE_USER, null, values);
        db.close(); // Closing database connection

        Log.d(TAG, "New user inserted into sqlite: " + id);
    }


    /**
     * Getting user data from database
     * */
    public HashMap<String, String> getUserDetails() {
        HashMap<String, String> user = new HashMap<String, String>();
        String selectQuery = "SELECT  * FROM " + TABLE_USER;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // Move to first row
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            user.put("customer_id", cursor.getString(1));
            user.put("referral_id", cursor.getString(2));
            user.put("email", cursor.getString(3));
            user.put("mobile", cursor.getString(4));
            user.put("name", cursor.getString(5));
            user.put("created_at", cursor.getString(6));
            user.put("status", cursor.getString(7));
            user.put("aid", cursor.getString(8));
        }
        cursor.close();
        db.close();
        // return user
        Log.d(TAG, "Fetching user from Sqlite: " + user.toString());

        return user;
    }



    /**
     * Re crate database Delete all tables and create them again
     * */
    public void deleteUsers() {
        SQLiteDatabase db = this.getWritableDatabase();
        // Delete All Rows
        db.delete(TABLE_USER, null, null);
        db.close();

        Log.d(TAG, "Deleted all user info from sqlite");
    }



}