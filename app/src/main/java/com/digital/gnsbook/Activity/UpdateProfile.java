package com.digital.gnsbook.Activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import com.android.volley.AuthFailureError;
import com.android.volley.Response;
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

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

public class UpdateProfile extends AppCompatActivity {
    Bitmap CircleBitmap;
    final int MY_PERMISSIONS_REQUEST_CAMERA = 786;
    private Button cancel;
    private Button confirm;
    ViewDialog dialog;
    private ImageView ivMain;

    /* renamed from: com.digital.gnsbook.Activity.UpdateProfile$1 */
    class C04351 implements OnClickListener {
        C04351() {
        }

        public void onClick(View view) {
            UpdateProfile.this.finish();
        }
    }

    /* renamed from: com.digital.gnsbook.Activity.UpdateProfile$2 */
    class C04362 implements OnClickListener {
        C04362() {
        }

        public void onClick(View view) {
            if (UpdateProfile.this.CircleBitmap != null) {

                boolean isCompany = getIntent().getBooleanExtra("isCompany", false);

                if (!isCompany)
                    UploadProfile(Global.encodeTobase64(UpdateProfile.this.CircleBitmap));
                else
                    updateComponyDP(Global.encodeTobase64(UpdateProfile.this.CircleBitmap));

            }
        }
    }

    private void updateComponyDP(final String encodeTobase64) {
        dialog.show();
        StringRequest stringRequest = new StringRequest(StringRequest.Method.POST, APIs.uploadDPPage, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                dialog.dismiss();

                try {
                    JSONObject object = new JSONObject(response);

                    if (object.getBoolean("status")){
                        Global.successDilogue(UpdateProfile.this,"You have Successfully updated your profile");
                    }else {
                        Global.failedDilogue(UpdateProfile.this,object.getString("result"));
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                dialog.dismiss();
                if (error == null || error.networkResponse == null) {
                    return;
                }

                String body;
                //get status code here
                final String statusCode = String.valueOf(error.networkResponse.statusCode);
                //get response body and parse with appropriate encoding
                try {
                    body = new String(error.networkResponse.data,"UTF-8");
                    Log.d("Multi",body);
                } catch (UnsupportedEncodingException e) {
                    // exception
                }

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> param = new HashMap<>();

                param.put("company_id",Global.Company_Id);
                param.put("image",encodeTobase64);

                return param;
            }
        };

        AppController.getInstance().addToRequestQueue(stringRequest);
    }

    /* renamed from: com.digital.gnsbook.Activity.UpdateProfile$3 */
    class C09103 implements Listener<String> {
        C09103() {
        }

        public void onResponse(String str) {
            UpdateProfile.this.dialog.dismiss();
            try {
                JSONObject jSONObject = new JSONObject(str);
                if (jSONObject.getBoolean("status")) {
                    Global.successDilogue(UpdateProfile.this,"You have Successfully updated your profile pick.");
                } else {
                    Global.failedDilogue(UpdateProfile.this, jSONObject.getString("result"));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    /* renamed from: com.digital.gnsbook.Activity.UpdateProfile$4 */
    class C09114 implements ErrorListener {
        C09114() {
        }

        public void onErrorResponse(VolleyError volleyError) {
            UpdateProfile.this.dialog.dismiss();
        }
    }

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_update_profile);
        getSupportActionBar().hide();
        this.cancel = (Button) findViewById(R.id.cancelupload);
        this.confirm = (Button) findViewById(R.id.confirmupoload);
        this.dialog = new ViewDialog(this);
        this.cancel.setOnClickListener(new C04351());
        this.confirm.setOnClickListener(new C04362());
        this.ivMain = (ImageView) findViewById(R.id.iv_main);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("IMG_");
        stringBuilder.append(System.currentTimeMillis());
        stringBuilder.append(".jpg");
        new CroperinoConfig(stringBuilder.toString(), "/gnsbook/Pictures", "/sdcard/gnsbook/Pictures");
        CroperinoFileUtil.setupDirectory(this);
        if (CroperinoFileUtil.verifyStoragePermissions(this).booleanValue()) {
            prepareChooser();
        }
    }

    private void UploadProfile(String str) {
        this.dialog.show();
        final String str2 = str;
        AppController.getInstance().addToRequestQueue(new StringRequest(1, APIs.uploadDP, new C09103(), new C09114()) {
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> hashMap = new HashMap();
                hashMap.put("customer_id", Global.customerid);
                hashMap.put("string", str2);
                return hashMap;
            }
        });
    }

    private void prepareChooser() {
        if (getIntent().getIntExtra("type", 0) == 0) {
            if (ContextCompat.checkSelfPermission(this, "android.permission.CAMERA") != 0) {
                ActivityCompat.requestPermissions(this, new String[]{"android.permission.CAMERA"}, 786);
            } else {
                Croperino.prepareCamera(this);
            }
        } else if (getIntent().getIntExtra("type", 0) == 1) {
            Croperino.prepareGallery(this);
        } else {
            Croperino.prepareChooser(this, "UploadImage", R.color.primary_color);
        }
    }

    private void prepareCamera() {
        Croperino.prepareCamera(this);
    }

    public void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        switch (i) {
            case 1:
                if (i2 == -1) {
                    Croperino.runCropImage(CroperinoFileUtil.getTempFile(), this, true, 1, 1, R.color.gray, R.color.gray_variant);
                    return;
                } else {
                    finish();
                    return;
                }
            case 2:
                if (i2 == -1) {
                    CroperinoFileUtil.newGalleryFile(intent, this);
                    Croperino.runCropImage(CroperinoFileUtil.getTempFile(), this, true, 1, 1, R.color.gray, R.color.gray_variant);
                    return;
                }
                finish();
                return;
            case 3:
                if (i2 == -1) {
                    Uri fromFile = Uri.fromFile(CroperinoFileUtil.getTempFile());
                    this.CircleBitmap = Global.getRoundedCroppedBitmap(Global.uriToBitmap(fromFile, this));
                    this.ivMain.setImageBitmap(Global.getRoundedCroppedBitmap(Global.uriToBitmap(fromFile, this)));
                    return;
                }
                finish();
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
}
