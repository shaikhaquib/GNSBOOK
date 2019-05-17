package com.digital.gnsbook.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.digital.gnsbook.Config.APIs;
import com.digital.gnsbook.Config.AppController;
import com.digital.gnsbook.Global;
import com.digital.gnsbook.ViewDialog;
import com.httpgnsbook.gnsbook.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class Add_Address extends AppCompatActivity {
    public EditText name,country,apartment,landmark,phno,city,state,pin,email;
    String strName, strCountry,strApartment,strLandmark,strPhn,strCity,strState,strZip;
    public Button next;

    String Addresstype = "0";
    ViewDialog progressDialog;
    RadioButton R1 ,R2 ;
    RadioGroup radioGroup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shiping_address);


        progressDialog=new ViewDialog(Add_Address.this);
        R1 = findViewById(R.id.typeHome);
        R2 = findViewById(R.id.typeWork);
        radioGroup = findViewById(R.id.Rtype);
        name = findViewById(R.id.fullname);
        phno = findViewById(R.id.phoneno);
        apartment = findViewById(R.id.apartment);
        landmark = findViewById(R.id.landmark);
        city = findViewById(R.id.city);
        pin = findViewById(R.id.pincode);
        state = findViewById(R.id.state);
        country = findViewById(R.id.country);
        next = findViewById(R.id.nextbtn);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId==R.id.typeHome){
                    Addresstype = "0";
                }else {
                    Addresstype = "1";
                }
            }
        });


        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Validation();
            }
        });
    }

    private void Validation() {
        strName         = name.getText().toString();
        strPhn          = phno.getText().toString();
        strApartment    = apartment.getText().toString();
        strLandmark     = landmark.getText().toString();
        strCity         = city.getText().toString();
        strZip          = pin.getText().toString();
        strState        = state.getText().toString();
        strCountry      = country.getText().toString();



        boolean cancel= false;
        View focusView = null;

        if (TextUtils.isEmpty(strName)){
            name.setError(getString(R.string.error_field_required));
            focusView = name;
            cancel = true;
        }else if (TextUtils.isEmpty(strPhn)){
            phno.setError(getString(R.string.error_field_required));
            focusView = phno;
            cancel = true;
        }else if (TextUtils.isEmpty(strApartment)){
            apartment.setError(getString(R.string.error_field_required));
            focusView = apartment;
            cancel = true;
        }else if (TextUtils.isEmpty(strLandmark)){
            landmark.setError(getString(R.string.error_field_required));
            focusView = landmark;
            cancel = true;
        } else if (TextUtils.isEmpty(strCity)){
            city.setError(getString(R.string.error_field_required));
            focusView = city;
            cancel = true;
        }else if (TextUtils.isEmpty(strZip)){
            pin.setError(getString(R.string.error_field_required));
            focusView = pin;
            cancel = true;
        } else if (TextUtils.isEmpty(strState)){
            state.setError(getString(R.string.error_field_required));
            focusView = state;
            cancel = true;
        }else if (TextUtils.isEmpty(strCountry)){
            country.setError(getString(R.string.error_field_required));
            focusView = country;
            cancel = true;
        }if (cancel) {
            focusView.requestFocus();
        }
        else {
            Add(strName, strCountry,strApartment,strLandmark,strPhn,strCity,strState,strZip);
        }
    }

    private void Add(final String strName, final String strCountry, final String strApartment, final String strLandmark, final String strPhn, final String strCity, final String strState, final String strZip) {
        progressDialog.show();
        AppController.getInstance().addToRequestQueue(new StringRequest(StringRequest.Method.POST, APIs.Add_Address, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                try {
                    JSONObject jsonObject = new JSONObject(response);

                    if (jsonObject.getBoolean("status")){

                        Toast.makeText(Add_Address.this, jsonObject.getString("result"), Toast.LENGTH_LONG).show();
                        finish();
                     //   Global.successDilogue(Add_Address.this,jsonObject.getString("result"));
                    }else {
                        Global.failedDilogue(Add_Address.this,jsonObject.getString("result"));
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
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
                hashMap.put("customer_id"    , Global.customerid);
                hashMap.put("customer_name"  ,strName);
                hashMap.put("phone_number"   , strPhn);
                hashMap.put("landmark"       , strLandmark);
                hashMap.put("apartment_name" , strApartment);
                hashMap.put("city"           , strCity);
                hashMap.put("state"          , strState);
                hashMap.put("country"        , strCountry);
                hashMap.put("type"           , Addresstype);
                hashMap.put("zipcode"        , strZip);
                return hashMap;            }
        });

    }



}
