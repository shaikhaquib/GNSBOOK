package com.digital.gnsbook.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.alimuzaffar.lib.pin.PinEntryEditText;
import com.android.volley.AuthFailureError;
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

import java.util.HashMap;
import java.util.Map;

public class OTPview extends AppCompatActivity {

    ViewDialog dialog;
    PinEntryEditText pinEntry;
    Button validateButton ;
    TextView textMessage ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otpview);
        dialog = new ViewDialog(OTPview.this);

        getSupportActionBar().hide();

        pinEntry = findViewById(R.id.otp);
        validateButton = findViewById(R.id.validate_button);
        textMessage = findViewById(R.id.otpmessage);
        textMessage.setText("Please type the verification code sent to\n" + Global.mobile);

        validateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (pinEntry.getText().toString().length() == 4) {
                    VerifyMobile(pinEntry.getText().toString());
                }else{
                    Toast.makeText(getApplicationContext(), "Please Enter Proper OTP", Toast.LENGTH_SHORT).show();
                }
            }
        });

        if (pinEntry != null) {
            pinEntry.setOnPinEnteredListener(new PinEntryEditText.OnPinEnteredListener() {
                @Override
                public void onPinEntered(CharSequence str){
                    InputMethodManager imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
                    imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
                    VerifyMobile(pinEntry.getText().toString());
                }
            });
        }


    }





    public void VerifyMobile(final String otp) {

        dialog.show();

        StringRequest stringRequest = new StringRequest(StringRequest.Method.POST, APIs.Phoneotpverifie, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                dialog.dismiss();

                try {

                    JSONObject object = new JSONObject(response);
                    if (object.getBoolean("status")){
                        Global.successDilogue(OTPview.this,object.getString("result"));
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
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map <String,String> param = new HashMap<String,String>();
                param.put("customer_id",Global.customerid);
                param.put("otp",otp);
                return param;
            }

        };
        AppController.getInstance().addToRequestQueue(stringRequest);

    }

}
