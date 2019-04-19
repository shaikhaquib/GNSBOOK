package com.digital.gnsbook.Config;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.HashMap;

public class SqlLastMessage extends SQLiteOpenHelper {
    public static final String TAG = DbHelper.class.getSimpleName();
    public static final String DB_NAME = "Messages.db";
    public static final int DB_VERSION = 1;

    public static final String TABLE_MESSAGE= "Message";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_CHANNEL_ID = "channel_id";
    public static final String COLUMN_MESSAGE = "message";
    public static final String COLUMN_DATE = "date";


    public  static  final String CREATE_TABLE_MESSAGE= "CREATE TABLE " + TABLE_MESSAGE + "("
            + COLUMN_ID + "  INTEGER PRIMARY KEY , "
            + COLUMN_CHANNEL_ID + " TEXT , "+ COLUMN_MESSAGE + " TEXT , " + COLUMN_DATE + " TEXT);";




    public SqlLastMessage(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_MESSAGE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF Exist" + TABLE_MESSAGE);
        onCreate(db);
    }
    public void addMessage(String channel_id, String message , String date)
    {
        SQLiteDatabase db = this.getReadableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_CHANNEL_ID, channel_id);
        values.put(COLUMN_MESSAGE, message);
        values.put(COLUMN_DATE,date);

        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_MESSAGE + " WHERE " + COLUMN_CHANNEL_ID + " =?",new String[] {channel_id});
        System.out.println(cursor);
        if(cursor.getCount() <= 0)
        {

            long id = db.insert(TABLE_MESSAGE, null, values);
            db.close();

            Log.d(TAG, "Message inserted " +id);
        }
        else
        {
            System.out.println("Allready Exists");
            long id = db.update(TABLE_MESSAGE, values,  COLUMN_CHANNEL_ID + " =?",new String[] {channel_id});
            Log.d(TAG, "Message Updated " +id);

            db.close();
        }

    }

    public Cursor getMessagesList(){
        //String selectQuery = "select * from " + USER_TABLE_ADDICON;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select * from "+ TABLE_MESSAGE, null);
        return cursor;
    }
    public HashMap<String, String> getMessage(String channel_id){
        HashMap<String, String> Message = new HashMap<String, String>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_MESSAGE + " WHERE " + COLUMN_CHANNEL_ID + " =?",new String[] {channel_id});
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            Message.put(COLUMN_CHANNEL_ID, cursor.getString(1));
            Message.put(COLUMN_MESSAGE, cursor.getString(2));
            Message.put(COLUMN_DATE, cursor.getString(3));
        }
        cursor.close();
        db.close();
        // return user
        Log.d(TAG, "Fetching user from Sqlite: " + Message.toString());

        return Message;    }
    public void deleteData(String id)
    {
        try {
            SQLiteDatabase db = this.getWritableDatabase();

            int i = db.delete(TABLE_MESSAGE,"id=?",new String[]{id});
            db.close();

            Log.d(TAG, "Delete medicine name" +i); } catch (SQLException s) {
            new Exception("Error with DB Open");
        }
    }

    public void SMSDelete() {
        SQLiteDatabase db = this.getWritableDatabase();
        // Delete All Rows
        db.delete(TABLE_MESSAGE, null, null);
        db.close();

        Log.d(TAG, "Deleted all user info from sqlite");
    }


    // Add Icon Method
    public boolean updateData(String id, String date, String count) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_CHANNEL_ID,date);
        contentValues.put(COLUMN_MESSAGE,count);
        db.update(TABLE_MESSAGE, contentValues, "id = ?",new String[] { id });
        return true;
    }

    public boolean doesTableExist() {
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery("select DISTINCT tbl_name from sqlite_master where tbl_name = '" + TABLE_MESSAGE + "'", null);

        if (cursor != null) {
            if (cursor.getCount() > 0) {
                cursor.close();
                return true;
            }
            cursor.close();
        }
        return false;
    }

}

