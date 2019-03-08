package com.digital.gnsbook.Activity;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class UploadProduct extends AppCompatActivity {
    EditText prdName,prdCat,prdLink,Descreption,prdPrize;
    String ImgBase64 = "",strDescription, strPrdName,strCat,strLink,strPrize;
    ImageView Logo,PImage;
    final int MY_PERMISSIONS_REQUEST_CAMERA = 786;
    int Type = 0;
    ViewDialog dialog;
    TextView subheading,Name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_product);


        getSupportActionBar().hide();
        dialog      =  new ViewDialog(this);
        Logo        =  findViewById(R.id.clogo);
        prdCat      =  findViewById(R.id.prdctCat);
        prdLink     =  findViewById(R.id.prdctLink);
        prdPrize    =  findViewById(R.id.prdcPrize);
        PImage      =  findViewById(R.id.postimg);
        Name        =  findViewById(R.id.cmName);
        subheading  =  findViewById(R.id.cmHeading);
        prdName     =  findViewById(R.id.postTitle);
        Descreption = findViewById(R.id.Postdesc);

        Name.setText(Global.Company_Name);
        subheading.setText(Global.Company_Type);
        Picasso.get().load(APIs.Dp+Global.Company_Logo).into(this.Logo);

    }

    public void PickerProduct(View view) {

        int id = view.getId();

        if (id == R.id.pgallery){
            Type = 1;
            new CroperinoConfig("IMG_" + System.currentTimeMillis() + ".jpg", "/gnsbook/Pictures", "/sdcard/gnsbook/Pictures");
            CroperinoFileUtil.setupDirectory(UploadProduct.this);

            if (CroperinoFileUtil.verifyStoragePermissions(UploadProduct.this)) {
                prepareChooser();
            }
        }else if (id == R.id.UploadPost){

            strPrdName = prdName.getText().toString();
            strDescription = Descreption.getText().toString();
            strCat   = prdCat.getText().toString();
            strLink  = prdLink.getText().toString();
            strPrize = prdPrize.getText().toString();

            boolean cancel= false;
            View focusView = null;

            if (TextUtils.isEmpty(strPrdName)){
                prdName.setError(getString(R.string.error_field_required));
                focusView = prdName;
                cancel = true;
            } if (TextUtils.isEmpty(strCat)){
                prdCat.setError(getString(R.string.error_field_required));
                focusView = prdCat;
                cancel = true;
            } if (TextUtils.isEmpty(strLink)){
                prdLink.setError(getString(R.string.error_field_required));
                focusView = prdLink;
                cancel = true;
            } if (TextUtils.isEmpty(strDescription)){
                Descreption.setError(getString(R.string.error_field_required));
                focusView = Descreption;
                cancel = true;
            } else if (TextUtils.isEmpty(strPrize)){
                prdPrize.setError(getString(R.string.error_field_required));
                focusView = prdPrize;
                cancel = true;
            }if (cancel) {
                focusView.requestFocus();
            }
            else {
                UploadPost(strPrdName,strDescription,strCat,strLink,strPrize);
            }
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

    private void UploadPost(final String strName,final String strdesc,final String strCat,final String strLink, final String strPrize) {
        this.dialog.show();
        AppController.getInstance().addToRequestQueue(new StringRequest(1, APIs.UploadProduct, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                dialog.dismiss();
                try {
                    JSONObject jSONObject = new JSONObject(response);
                    if (jSONObject.getBoolean("status")) {
                        Global.successDilogue(UploadProduct.this,"You have Successfully post.");
                    } else {
                        Global.failedDilogue(UploadProduct.this, jSONObject.getString("result"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                dialog.dismiss();

            }
        }) {
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> hashMap = new HashMap();
                hashMap.put("customer_id", Global.customerid);
                hashMap.put("company_id", Global.Company_Id);
                hashMap.put("product_name", strName);
                hashMap.put("product_price", strPrize);
                hashMap.put("product_desc", strDescription);
                hashMap.put("product_cat", strCat);
                hashMap.put("product_link", strLink);
                hashMap.put("images",ImgBase64);
                return hashMap;
            }
        });
    }

}
