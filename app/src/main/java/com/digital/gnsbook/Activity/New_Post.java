package com.digital.gnsbook.Activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.digital.gnsbook.Config.APIs;
import com.digital.gnsbook.Config.AppController;
import com.digital.gnsbook.Global;
import com.digital.gnsbook.ViewDialog;
import com.httpgnsbook.gnsbook.R;
import com.mikelau.croperino.Croperino;
import com.mikelau.croperino.CroperinoConfig;
import com.mikelau.croperino.CroperinoFileUtil;
import com.squareup.picasso.Picasso;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

public class New_Post extends AppCompatActivity {
    EditText Descreption;
    String ImgBase64 = " ";
    ImageView Logo;
    final int MY_PERMISSIONS_REQUEST_CAMERA = 786;
    TextView Name;
    ImageView PImage;
    EditText Title;
    int Type = 0;
    ViewDialog dialog;
    String strDescription;
    String strTitle;
    TextView subheading;


    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_new_post);
        getSupportActionBar().hide();
        dialog = new ViewDialog(this);
        Logo = (ImageView) findViewById(R.id.clogo);
        PImage = (ImageView) findViewById(R.id.postimg);
        Name = (TextView) findViewById(R.id.cmName);
        subheading = (TextView) findViewById(R.id.cmHeading);
        Title = (EditText) findViewById(R.id.postTitle);
        Descreption = (EditText) findViewById(R.id.Postdesc);


        if (getIntent().getStringExtra("type").equals("2")) {
            Name.setText(Global.Company_Name);
            subheading.setText(Global.Company_Type);
            Picasso.get().load(APIs.Dp + Global.Company_Logo).into(this.Logo);
        }else {
            Name.setText(Global.name);
            subheading.setText(Global.Email);
            Picasso.get().load(APIs.Dp + Global.DP).into(this.Logo);
        }
    }

    private void prepareChooser() {
        if (this.Type == 0) {
            if (ContextCompat.checkSelfPermission(this, "android.permission.CAMERA") != 0) {
                ActivityCompat.requestPermissions(this, new String[]{"android.permission.CAMERA"}, 786);
            } else {
                Croperino.prepareCamera(this);
            }
        } else if (this.Type == 1) {
            Croperino.prepareGallery(this);
        } else {
            Croperino.prepareChooser(this, "UploadImage", R.color.primary_color);
        }
    }

    public void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        switch (i) {
            case 1:
                if (i2 == -1) {
                    Croperino.runCropImage(CroperinoFileUtil.getTempFile(), this, false, 0, 0, R.color.gray, R.color.gray_variant);
                    return;
                }
                return;
            case 2:
                if (i2 == -1) {
                    CroperinoFileUtil.newGalleryFile(intent, this);
                    Croperino.runCropImage(CroperinoFileUtil.getTempFile(), this, false, 0, 0, R.color.gray, R.color.gray_variant);
                    return;
                }
                return;
            case 3:
                if (i2 == -1) {
                    Uri fromFile = Uri.fromFile(CroperinoFileUtil.getTempFile());
                    this.PImage.setImageURI(fromFile);
                    this.ImgBase64 = Global.encodeTobase64(Global.uriToBitmap(fromFile, this));
                    return;
                }
                return;
            default:
                return;
        }
    }

    public void onRequestPermissionsResult(int i, @NonNull String[] strArr, @NonNull int[] iArr) {
        super.onRequestPermissionsResult(i, strArr, iArr);
        int i2 = 0;
        if (i != 786) {
            if (i == 1) {
                i = 0;
                Object obj = null;
                while (i2 < strArr.length) {
                    String str = strArr[i2];
                    int i3 = iArr[i2];
                    if (str.equals("android.permission.READ_EXTERNAL_STORAGE") && i3 == 0) {
                        i = 1;
                    }
                    if (str.equals("android.permission.WRITE_EXTERNAL_STORAGE") && i3 == 0) {
                        obj = 1;
                    }
                    i2++;
                }
                if (!(i == 0 || obj == null)) {
                    prepareChooser();
                }
            }
            return;
        }
        if (iArr.length > 0 && iArr[0] == 0) {
            Croperino.prepareCamera(this);
        }
    }

        public void Picker(View view) {

            int id = view.getId();

            if (id == R.id.pgallery){
                Type = 1;
                new CroperinoConfig("IMG_" + System.currentTimeMillis() + ".jpg", "/gnsbook/Pictures", "/sdcard/gnsbook/Pictures");
                CroperinoFileUtil.setupDirectory(New_Post.this);

                if (CroperinoFileUtil.verifyStoragePermissions(New_Post.this)) {
                    prepareChooser();
                }
            }else if (id == R.id.pcamera){
                Type = 0;
                new CroperinoConfig("IMG_" + System.currentTimeMillis() + ".jpg", "/gnsbook/Pictures", "/sdcard/gnsbook/Pictures");
                CroperinoFileUtil.setupDirectory(New_Post.this);

                if (CroperinoFileUtil.verifyStoragePermissions(New_Post.this)) {
                    prepareChooser();
                }
            }else if (id == R.id.UploadPost){

                strTitle = Title.getText().toString();
                strDescription = Descreption.getText().toString();

                boolean cancel= false;
                View focusView = null;

                if (TextUtils.isEmpty(strTitle)){
                    Title.setError(getString(R.string.error_field_required));
                    focusView = Title;
                    cancel = true;
                } else if (TextUtils.isEmpty(strDescription)){
                    Descreption.setError(getString(R.string.error_field_required));
                    focusView = Descreption;
                    cancel = true;
                }if (cancel) {
                    focusView.requestFocus();
                }
                else {
                    if (getIntent().getStringExtra("type").equals("2"))
                        UploadPost(this.strTitle, this.strDescription);
                    else
                        customerPost(this.strTitle, this.strDescription);

                }
        }
    }

    private void customerPost(String str, String str2) {
        this.dialog.show();
        final String str3 = str;
        final String str4 = str2;
        AppController.getInstance().addToRequestQueue(new StringRequest(1, APIs.UploadPostbyCustomer, new Listener<String>() {
            @Override
            public void onResponse(String str) {
                New_Post.this.dialog.dismiss();
                try {
                    JSONObject jSONObject = new JSONObject(str);
                    if (jSONObject.getBoolean("status")) {
                        //   Global.successDilogue(New_Post.this,"You have Successfully post.");
                        Toast.makeText(New_Post.this, "You have Successfully post.", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Global.failedDilogue(New_Post.this, jSONObject.getString("result"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        }, new ErrorListener() {
            public void onErrorResponse(VolleyError error) {
                New_Post.this.dialog.dismiss();

                String body;
                //get status code here
                final String statusCode = String.valueOf(error.networkResponse.statusCode);
                //get response body and parse with appropriate encoding
                try {
                    body = new String(error.networkResponse.data,"UTF-8");
                    Log .d("Multi",body);
                } catch (UnsupportedEncodingException e) {
                    // exception
                }

            }

        }) {
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> hashMap = new HashMap();
                hashMap.put("customer_id", Global.customerid);
                hashMap.put("title", str3);
                hashMap.put("description", str4);
                hashMap.put("image", New_Post.this.ImgBase64);
                return hashMap;
            }
        });
    }
    private void UploadPost(String str, String str2) {
        this.dialog.show();
        final String str3 = str;
        final String str4 = str2;
        AppController.getInstance().addToRequestQueue(new StringRequest(1, APIs.UploadPost, new Listener<String>() {
            @Override
            public void onResponse(String str) {
                New_Post.this.dialog.dismiss();
                try {
                    JSONObject jSONObject = new JSONObject(str);
                    if (jSONObject.getBoolean("status")) {
                        //   Global.successDilogue(New_Post.this,"You have Successfully post.");
                        Toast.makeText(New_Post.this, "You have Successfully post.", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Global.failedDilogue(New_Post.this, jSONObject.getString("result"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        }, new ErrorListener() {
            public void onErrorResponse(VolleyError error) {
                New_Post.this.dialog.dismiss();

                String body;
                //get status code here
                final String statusCode = String.valueOf(error.networkResponse.statusCode);
                //get response body and parse with appropriate encoding
                try {
                    body = new String(error.networkResponse.data,"UTF-8");
                    Log .d("Multi",body);
                } catch (UnsupportedEncodingException e) {
                    // exception
                }

            }

        }) {
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> hashMap = new HashMap();
                hashMap.put("customer_id", Global.customerid);
                hashMap.put("company_id", Global.Company_Id);
                hashMap.put("title", str3);
                hashMap.put("description", str4);
                hashMap.put("image", New_Post.this.ImgBase64);
                return hashMap;
            }
        });
    }
}
