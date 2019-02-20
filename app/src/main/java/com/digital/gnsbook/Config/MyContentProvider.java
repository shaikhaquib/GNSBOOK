package com.digital.gnsbook.Config;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;

public class MyContentProvider extends ContentProvider {
    SQLiteHandler handler;

    public int delete(Uri uri, String str, String[] strArr) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public String getType(Uri uri) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public Uri insert(Uri uri, ContentValues contentValues) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public boolean onCreate() {
        this.handler = new SQLiteHandler(getContext());
        return true;
    }

    public Cursor query(Uri uri, String[] strArr, String str, String[] strArr2, String str2) {
        return this.handler.getReadableDatabase().query("user", strArr, str, strArr2, null, null, str2);
    }

    public int update(Uri uri, ContentValues contentValues, String str, String[] strArr) {
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
