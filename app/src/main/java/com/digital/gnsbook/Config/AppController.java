package com.digital.gnsbook.Config;

import android.app.Application;
import android.text.TextUtils;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class  AppController extends Application {
    public static final String TAG = "AppController";
    private static AppController mInstance;
    private RequestQueue mRequestQueue;

    public void onCreate() {
        super.onCreate();
        mInstance = this;
    }

    public static synchronized AppController getInstance() {
        AppController appController;
        synchronized (AppController.class) {
            appController = mInstance;
        }
        return appController;
    }

    public RequestQueue getRequestQueue() {
        if (this.mRequestQueue == null) {
            this.mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }
        return this.mRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> request, String str) {
        if (TextUtils.isEmpty(str)) {
            str = TAG;
        }
        request.setTag(str);
        getRequestQueue().add(request);
    }

    public <T> void addToRequestQueue(Request<T> request) {
        request.setTag(TAG);
        request.setRetryPolicy(new DefaultRetryPolicy(20000, 0, 1.0f));
        getRequestQueue().add(request);
    }

    public void cancelPendingRequests(Object obj) {
        if (this.mRequestQueue != null) {
            this.mRequestQueue.cancelAll(obj);
        }
    }
}
