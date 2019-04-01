package com.digital.gnsbook.Activity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.digital.gnsbook.Config.APIs;
import com.digital.gnsbook.Config.AppController;
import com.digital.gnsbook.Config.FileUtils;
import com.digital.gnsbook.Config.MySingleton;
import com.digital.gnsbook.Config.VolleyMultipartRequest;
import com.digital.gnsbook.Global;
import com.digital.gnsbook.ViewDialog;
import com.digital.gnsbook.sample.MyAdapter;
import com.httpgnsbook.gnsbook.R;
import com.mikelau.croperino.Croperino;
import com.mikelau.croperino.CroperinoConfig;
import com.mikelau.croperino.CroperinoFileUtil;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class UploadProduct extends AppCompatActivity {
    EditText prdName,prdCat,prdLink,Descreption,prdPrize;
    TextInputLayout textLink;
    String ImgBase64 = "",strDescription, strPrdName,strCat,strLink,strPrize,productType = "0";
    RadioGroup type ;
    RadioButton R1 , R2;
    ImageView Logo,PImage;
    final int MY_PERMISSIONS_REQUEST_CAMERA = 786;
    int Type = 0;
    ViewDialog dialog;
    TextView subheading,Name;
    ListView listView;
    private static final String TAG = MainActivity.class.getSimpleName();
    private static final int REQUEST_CODE = 6384;
    private static final int REQUEST_CODE_ASK_PERMISSIONS = 124;
    private ArrayList<Uri> arrayList;
    private ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_product);
        pDialog=new ProgressDialog(this);

        arrayList = new ArrayList<>();

        getSupportActionBar().hide();
        dialog      =  new ViewDialog(this);
        Logo        =  findViewById(R.id.clogo);
        textLink        =  findViewById(R.id.textLink);
        type        =  findViewById(R.id.saleType);
        R1          =  findViewById(R.id.rb1);
        R2          =  findViewById(R.id.rb2);
        prdCat      =  findViewById(R.id.prdctCat);
        prdLink     =  findViewById(R.id.prdctLink);
        prdPrize    =  findViewById(R.id.prdcPrize);
        PImage      =  findViewById(R.id.postimg);
        Name        =  findViewById(R.id.cmName);
        subheading  =  findViewById(R.id.cmHeading);
        prdName     =  findViewById(R.id.postTitle);
        Descreption = findViewById(R.id.Postdesc);
        listView = findViewById(R.id.listView);

        Name.setText(Global.Company_Name);
        subheading.setText(Global.Company_Type);
        Picasso.get().load(APIs.Dp+Global.Company_Logo).into(this.Logo);

        R2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    textLink.setVisibility(View.VISIBLE);
                    productType = "1";
                }else {
                    textLink.setVisibility(View.GONE);
                    productType = "0";
                }
            }
        });

    }

    public void PickerProduct(View view) {

        int id = view.getId();

        if (id == R.id.pgallery){
            Type = 1;
            if (askForPermission())
                showChooser();
        }else if (id == R.id.UploadPost){

            strPrdName = prdName.getText().toString();
            strDescription = Descreption.getText().toString();
            strCat   = prdCat.getText().toString();

            if (productType.equals("1"))
            {  strLink  = prdLink.getText().toString();}
            else
            {  strLink = "-"; }

            strPrize = prdPrize.getText().toString();

            boolean cancel= false;
            View focusView = null;

            if (TextUtils.isEmpty(strPrdName)){
                prdName.setError(getString(R.string.error_field_required));
                focusView = prdName;
                cancel = true;
            }else if (TextUtils.isEmpty(strCat)){
                prdCat.setError(getString(R.string.error_field_required));
                focusView = prdCat;
                cancel = true;
            }else if (TextUtils.isEmpty(strLink)){
                prdLink.setError(getString(R.string.error_field_required));
                focusView = prdLink;
                cancel = true;
            }else if (TextUtils.isEmpty(strDescription)){
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
                 uploadFile(strPrdName,strDescription,strCat,strLink,strPrize);
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
/*
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
*/
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


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_CODE:
                // If the file selection was successful
                if (resultCode == RESULT_OK) {
                    if(data.getClipData() != null) {
                        int count = data.getClipData().getItemCount();
                        int currentItem = 0;
                        while(currentItem < count) {
                            Uri imageUri = data.getClipData().getItemAt(currentItem).getUri();
                            //do something with the image (save it to some directory or whatever you need to do with it here)
                            currentItem = currentItem + 1;
                            Log.d("Uri Selected", imageUri.toString());
                            try {
                                // Get the file path from the URI
                                String path = FileUtils.getPath(this, imageUri);
                                Log.d("Multiple File Selected", path);

                                arrayList.add(imageUri);
                                MyAdapter mAdapter = new MyAdapter(UploadProduct.this, arrayList);
                                listView.setAdapter(mAdapter);

                            } catch (Exception e) {
                                Log.e(TAG, "File select error", e);
                            }
                        }
                    } else if(data.getData() != null) {
                        //do something with the image (save it to some directory or whatever you need to do with it here)
                        final Uri uri = data.getData();
                        Log.i(TAG, "Uri = " + uri.toString());
                        try {
                            // Get the file path from the URI
                            final String path = FileUtils.getPath(this, uri);
                            Log.d("Single File Selected", path);

                            arrayList.add(uri);
                            MyAdapter mAdapter = new MyAdapter(UploadProduct.this, arrayList);
                            listView.setAdapter(mAdapter);

                        } catch (Exception e) {
                            Log.e(TAG, "File select error", e);
                        }
                    }
                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    private boolean askForPermission() {
        int currentAPIVersion = Build.VERSION.SDK_INT;
        if (currentAPIVersion >= Build.VERSION_CODES.M) {
            int hasCallPermission = ContextCompat.checkSelfPermission(UploadProduct.this,
                    Manifest.permission.READ_EXTERNAL_STORAGE);
            if (hasCallPermission != PackageManager.PERMISSION_GRANTED) {
                // Ask for permission
                // need to request permission
                if (ActivityCompat.shouldShowRequestPermissionRationale(UploadProduct.this,
                        Manifest.permission.READ_EXTERNAL_STORAGE)) {
                    // explain
                    showMessageOKCancel(
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    ActivityCompat.requestPermissions(UploadProduct.this,
                                            new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE},
                                            REQUEST_CODE_ASK_PERMISSIONS);
                                }
                            });
                    // if denied then working here
                } else {
                    // Request for permission
                    ActivityCompat.requestPermissions(UploadProduct.this,
                            new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE},
                            REQUEST_CODE_ASK_PERMISSIONS);
                }

                return false;
            } else {
                // permission granted and calling function working
                return true;
            }
        } else {
            return true;
        }
    }
    private void showChooser() {
        // Use the GET_CONTENT intent from the utility class
        Intent target = FileUtils.createGetContentIntent();
        // Create the chooser Intent
        Intent intent = Intent.createChooser(
                target, getString(R.string.chooser_title));
        try {
            startActivityForResult(intent, REQUEST_CODE);
        } catch (ActivityNotFoundException e) {
            // The reason for the existence of aFileChooser
        }
    }
    private void showMessageOKCancel(DialogInterface.OnClickListener okListener) {

        AlertDialog.Builder builder = new AlertDialog.Builder(UploadProduct.this);
        final AlertDialog dialog = builder.setMessage("You need to grant access to Read External Storage")
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create();

        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface arg0) {
                dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(
                        ContextCompat.getColor(UploadProduct.this, android.R.color.holo_blue_light));
                dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(
                        ContextCompat.getColor(UploadProduct.this, android.R.color.holo_red_light));
            }
        });

        dialog.show();

    }

    private void uploadFile(final String strName,final String strdesc,final String strCat,final String strLink, final String strPrize){
        dialog.show();
        VolleyMultipartRequest multipartRequest = new VolleyMultipartRequest(Request.Method.POST, APIs.UploadProduct, new Response.Listener<NetworkResponse>() {
            @Override
            public void onResponse(NetworkResponse response) {
                dialog.dismiss();
                try {
                    String json = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
                    Log.d("Responce",json);
                    JSONObject jSONObject = new JSONObject(json);
                    if (jSONObject.getBoolean("status")) {
                        Global.successDilogue(UploadProduct.this,"You have Successfully post.");
                    } else {
                        Global.failedDilogue(UploadProduct.this, jSONObject.getString("result"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (UnsupportedEncodingException e) {
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

                //do stuff with the body...
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> hashMap = new HashMap<>();
                hashMap.put("customer_id", Global.customerid);
                hashMap.put("company_id", Global.Company_Id);
                hashMap.put("product_name", strName);
                hashMap.put("product_price", strPrize);
                hashMap.put("product_desc", strDescription);
                hashMap.put("product_cat", strCat);
                hashMap.put("product_link", strLink);
                hashMap.put("sell_type", productType);
                return hashMap;
            }

            @Override
            protected Map<String, DataPart> getByteData() throws AuthFailureError {
                Map<String, DataPart> params = new HashMap<>();
                // file name could found file base or direct access from real path
                //for now just get bitmap data from ImageView

                File uploads[] = new File[arrayList.size()];
                for (int i = 0; i < arrayList.size(); i++) {

                    try {
                        InputStream iStream =   getContentResolver().openInputStream(arrayList.get(i));
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), arrayList.get(i));

                        params.put("images[" + i + "]", new DataPart(getFileName(arrayList.get(i)) , getFileDataFromDrawable(bitmap)));

                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                }

                return params;
            }
        };



        MySingleton.getInstance(getBaseContext()).addToRequestQueue(multipartRequest);
    }

    public byte[] getFileDataFromDrawable(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 80, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    public byte[] getBytes(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
        int bufferSize = 1024;
        byte[] buffer = new byte[bufferSize];

        int len = 0;
        while ((len = inputStream.read(buffer)) != -1) {
            byteBuffer.write(buffer, 0, len);
        }
        return byteBuffer.toByteArray();
    }
    public String getFileName(Uri uri) {
        String result = null;
        if (uri.getScheme().equals("content")) {
            Cursor cursor = getContentResolver().query(uri, null, null, null, null);
            try {
                if (cursor != null && cursor.moveToFirst()) {
                    result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                }
            } finally {
                cursor.close();
            }
        }
        if (result == null) {
            result = uri.getPath();
            int cut = result.lastIndexOf('/');
            if (cut != -1) {
                result = result.substring(cut + 1);
            }
        }
        return result;
    }
}
