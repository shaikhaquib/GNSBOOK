package com.digital.gnsbook.Config;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;

public class SessionManager {
    private static final String KEY_IS_LOGGED_IN = "isLoggedIn";
    private static final String PREF_NAME = "AndroidHiveLogin";
    private static String TAG = "SessionManager";
    int PRIVATE_MODE = 0;
    Context _context;
    Editor editor;
    SharedPreferences pref;

    public SessionManager(Context context) {
        this._context = context;
        this.pref = this._context.getSharedPreferences(PREF_NAME, this.PRIVATE_MODE);
        this.editor = this.pref.edit();
    }

    public void setLogin(boolean z) {
        this.editor.putBoolean(KEY_IS_LOGGED_IN, z);
        this.editor.commit();
        Log.d(TAG, "User login session modified!");
    }

    public boolean isLoggedIn() {
        return this.pref.getBoolean(KEY_IS_LOGGED_IN, false);
    }
}
