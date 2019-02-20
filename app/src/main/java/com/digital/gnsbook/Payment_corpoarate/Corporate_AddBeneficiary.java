package com.digital.gnsbook.Payment_corpoarate;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.net.Uri.Builder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.WindowManager.LayoutParams;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.alimuzaffar.lib.pin.PinEntryEditText;
import com.alimuzaffar.lib.pin.PinEntryEditText.OnPinEnteredListener;
import com.android.volley.AuthFailureError;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.load.Key;
import com.digital.gnsbook.Activity.MainActivity;
import com.digital.gnsbook.Config.APIs;
import com.digital.gnsbook.Config.AppController;
import com.digital.gnsbook.Global;
import com.digital.gnsbook.Payment.AddBeneficiary;
import com.httpgnsbook.gnsbook.R;

import in.juspay.godel.core.Constants;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONException;
import org.json.JSONObject;

import swarajsaaj.smscodereader.interfaces.OTPListener;

public class Corporate_AddBeneficiary extends AppCompatActivity {
    static PinEntryEditText pinEntry;
    EditText Accountno;
    Button AddBenificiarybtn;
    EditText Ifsc;
    EditText Mobile;
    EditText Name;
    ProgressBar OtpProgress;
    String benid;
    boolean cancel = false;
    ProgressDialog dialog;
    View focusView = null;
    String otp;
    String strEMAIL;
    String strSMS;
    String uniqueid;

    /* renamed from: com.digital.gnsbook.Payment_corpoarate.Corporate_AddBeneficiary$1 */
    class C05341 implements OnClickListener {
        C05341() {
        }

        public void onClick(View view) {
            if (Corporate_AddBeneficiary.this.Name.getText().toString().equals("")) {
                Corporate_AddBeneficiary.this.Name.setError("Please Enter Name");
                Corporate_AddBeneficiary.this.focusView = Corporate_AddBeneficiary.this.Name;
                Corporate_AddBeneficiary.this.Name.requestFocus();
                Corporate_AddBeneficiary.this.cancel = true;
            } else if (Corporate_AddBeneficiary.this.Mobile.getText().toString().equals("")) {
                Corporate_AddBeneficiary.this.Mobile.setError("Please Enter Mobile No");
                Corporate_AddBeneficiary.this.focusView = Corporate_AddBeneficiary.this.Mobile;
                Corporate_AddBeneficiary.this.Mobile.requestFocus();
                Corporate_AddBeneficiary.this.cancel = true;
            } else if (Corporate_AddBeneficiary.this.Accountno.getText().toString().equals("")) {
                Corporate_AddBeneficiary.this.Accountno.setError("Please Enter AccountNo");
                Corporate_AddBeneficiary.this.focusView = Corporate_AddBeneficiary.this.Accountno;
                Corporate_AddBeneficiary.this.Accountno.requestFocus();
                Corporate_AddBeneficiary.this.cancel = true;
            } else if (Corporate_AddBeneficiary.this.Ifsc.getText().toString().equals("")) {
                Corporate_AddBeneficiary.this.Ifsc.setError("Please Enter Amount Code");
                Corporate_AddBeneficiary.this.focusView = Corporate_AddBeneficiary.this.Ifsc;
                Corporate_AddBeneficiary.this.Ifsc.requestFocus();
                Corporate_AddBeneficiary.this.cancel = true;
            } else {
                new addAsync().execute( Global.customerid, Global.agentid, Corporate_AddBeneficiary.this.Name.getText().toString(), Global.mobile, Corporate_AddBeneficiary.this.Accountno.getText().toString(), Corporate_AddBeneficiary.this.Ifsc.getText().toString());
            }
        }
    }

    /* renamed from: com.digital.gnsbook.Payment_corpoarate.Corporate_AddBeneficiary$2 */
    class C05352 implements OnTouchListener {
        C05352() {
        }

        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (Corporate_AddBeneficiary.this.Name.getText().toString().equals("")) {
                Corporate_AddBeneficiary.this.Name.setError("Please Enter UserName");
                Corporate_AddBeneficiary.this.focusView = Corporate_AddBeneficiary.this.Name;
                Corporate_AddBeneficiary.this.Name.requestFocus();
                Corporate_AddBeneficiary.this.cancel = true;
            } else {
                Corporate_AddBeneficiary.this.focusView = Corporate_AddBeneficiary.this.Mobile;
                Corporate_AddBeneficiary.this.Mobile.requestFocus();
                Corporate_AddBeneficiary.this.cancel = true;
            }
            return true;
        }
    }

    /* renamed from: com.digital.gnsbook.Payment_corpoarate.Corporate_AddBeneficiary$3 */
    class C05363 implements OnTouchListener {
        C05363() {
        }

        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (Corporate_AddBeneficiary.this.Name.getText().toString().equals("")) {
                Corporate_AddBeneficiary.this.Name.setError("Please Enter UserName");
                Corporate_AddBeneficiary.this.focusView = Corporate_AddBeneficiary.this.Name;
                Corporate_AddBeneficiary.this.Name.requestFocus();
                Corporate_AddBeneficiary.this.cancel = true;
            } else if (Corporate_AddBeneficiary.this.Mobile.getText().toString().equals("")) {
                Corporate_AddBeneficiary.this.Mobile.setError("Please Enter STDcode");
                Corporate_AddBeneficiary.this.focusView = Corporate_AddBeneficiary.this.Mobile;
                Corporate_AddBeneficiary.this.Mobile.requestFocus();
                Corporate_AddBeneficiary.this.cancel = true;
            } else if (Corporate_AddBeneficiary.this.Mobile.getText().toString().length() != 10) {
                Corporate_AddBeneficiary.this.Mobile.setError("Enter valid mobil no");
                Corporate_AddBeneficiary.this.Mobile.requestFocus();
            } else {
                Corporate_AddBeneficiary.this.focusView = Corporate_AddBeneficiary.this.Accountno;
                Corporate_AddBeneficiary.this.Accountno.requestFocus();
                Corporate_AddBeneficiary.this.cancel = true;
            }
            return true;
        }
    }

    /* renamed from: com.digital.gnsbook.Payment_corpoarate.Corporate_AddBeneficiary$4 */
    class C05374 implements OnTouchListener {
        C05374() {
        }

        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (Corporate_AddBeneficiary.this.Name.getText().toString().equals("")) {
                Corporate_AddBeneficiary.this.Name.setError("Please Enter UserName");
                Corporate_AddBeneficiary.this.focusView = Corporate_AddBeneficiary.this.Name;
                Corporate_AddBeneficiary.this.Name.requestFocus();
                Corporate_AddBeneficiary.this.cancel = true;
            } else if (Corporate_AddBeneficiary.this.Mobile.getText().toString().equals("")) {
                Corporate_AddBeneficiary.this.Mobile.setError("Please Enter STDcode");
                Corporate_AddBeneficiary.this.focusView = Corporate_AddBeneficiary.this.Mobile;
                Corporate_AddBeneficiary.this.Mobile.requestFocus();
                Corporate_AddBeneficiary.this.cancel = true;
            } else if (Corporate_AddBeneficiary.this.Mobile.getText().toString().length() != 10) {
                Corporate_AddBeneficiary.this.Mobile.setError("Enter valid mobil no");
                Corporate_AddBeneficiary.this.Mobile.requestFocus();
            } else if (Corporate_AddBeneficiary.this.Accountno.getText().toString().equals("")) {
                Corporate_AddBeneficiary.this.Accountno.setError("Please Enter AccountNo");
                Corporate_AddBeneficiary.this.focusView = Corporate_AddBeneficiary.this.Accountno;
                Corporate_AddBeneficiary.this.Accountno.requestFocus();
                Corporate_AddBeneficiary.this.cancel = true;
            } else {
                Corporate_AddBeneficiary.this.focusView = Corporate_AddBeneficiary.this.Ifsc;
                Corporate_AddBeneficiary.this.Ifsc.requestFocus();
                Corporate_AddBeneficiary.this.cancel = true;
            }
            return true;
        }
    }

    /* renamed from: com.digital.gnsbook.Payment_corpoarate.Corporate_AddBeneficiary$5 */

    /* renamed from: com.digital.gnsbook.Payment_corpoarate.Corporate_AddBeneficiary$6 */

    /* renamed from: com.digital.gnsbook.Payment_corpoarate.Corporate_AddBeneficiary$9 */
    class C05419 implements OnClickListener {
        public void onClick(View view) {
        }

        C05419() {
        }
    }

    class addAsync extends AsyncTask<String, String, String> implements OTPListener {
        HttpURLConnection conn;
        URL url = null;

        addAsync() {
        }

        protected void onPreExecute() {
            Corporate_AddBeneficiary.this.showProgress();
        }

        @Override
        protected String doInBackground(String... params) {


            try {


                url = new URL(APIs.Addebenificiary);

            } catch (MalformedURLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                return "exception";
            }
            try {
                // Setup HttpURLConnection class to send and receive data from php and mysql
                conn = (HttpURLConnection)url.openConnection();
                conn.setReadTimeout(Global.READ_TIMEOUT);
                conn.setConnectTimeout(Global.CONNECTION_TIMEOUT);
                conn.setRequestMethod("POST");

                // setDoInput and setDoOutput method depict handling of both send and receive
                conn.setDoInput(true);
                conn.setDoOutput(true);

                // Append parameters to URL
                Uri.Builder builder = new Uri.Builder()
                        .appendQueryParameter("customer_id", params[0])
                        .appendQueryParameter("agent_id", params[1])
                        .appendQueryParameter("beneficiary_name", params[2])
                        .appendQueryParameter("beneficiary_mobile", params[3])
                        .appendQueryParameter("beneficiary_account_no", params[4])
                        .appendQueryParameter("beneficiary_ifsc", params[5]);
                String query = builder.build().getEncodedQuery();

                // Open connection for sending data
                OutputStream os = conn.getOutputStream();
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(os, "UTF-8"));
                writer.write(query);
                writer.flush();
                writer.close();
                os.close();
                conn.connect();

            } catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
                return "exception";
            }

            try {

                int response_code = conn.getResponseCode();

                // Check if successful connection made
                if (response_code == HttpURLConnection.HTTP_OK) {

                    // Read data sent from server
                    InputStream input = conn.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(input));
                    StringBuilder result = new StringBuilder();
                    String line;

                    while ((line = reader.readLine()) != null) {
                        result.append(line);
                    }

                    // Pass data to onPostExecute method
                    return(result.toString());

                }else{

                    return("unsuccessful");
                }

            } catch (IOException e) {
                e.printStackTrace();
                return "exception";
            } finally {
                conn.disconnect();
            }


        }

        protected void onPostExecute(String s) {
            Corporate_AddBeneficiary.this.dissmissProgrss();
            try {
                JSONObject jSONObject = new JSONObject(s);
                if (jSONObject.getBoolean("status")) {
                    JSONObject str = jSONObject.getJSONObject("result");
                    Corporate_AddBeneficiary.this.otpView(str.getString("beneficiary_id"), str.getString("unique_id"));
                    return;
                }
                Global.failedDilogue(Corporate_AddBeneficiary.this, jSONObject.getString("result"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        public void otpReceived(String str) {
            Corporate_AddBeneficiary.this.OtpProgress.setVisibility(View.GONE);
            Corporate_AddBeneficiary.pinEntry.setText(str.replaceAll("[^0-9]", ""));
        }
    }

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_add_beneficiary);
        getSupportActionBar().hide();
        InitializeObject();
        onTouchField();
        Validation();
        onSubmit();
    }

    private void onSubmit() {
        this.AddBenificiarybtn.setOnClickListener(new C05341());
    }

    @SuppressLint({"ClickableViewAccessibility"})
    private void onTouchField() {
        this.Mobile.setOnTouchListener(new C05352());
        this.Accountno.setOnTouchListener(new C05363());
        this.Ifsc.setOnTouchListener(new C05374());
    }

    private void Validation() {
        Name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                System.out.println(s);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {


                Pattern paname =Pattern.compile("^[a-zA-Z]+$");
                Matcher matchname=paname.matcher(s);

                if (!matchname.matches()) {
                    Name.setError("Enter valid Name");
                    AddBenificiarybtn.setEnabled(false);
                    focusView = Name;
                    cancel = true;
                }else {
                    AddBenificiarybtn.setEnabled(true);
                }

            }

            @Override
            public void afterTextChanged(Editable s) {
                System.out.println(s);
                if (s.toString().contains(" ")) {
                    Name.setError("No Spaces Allowed");
                    AddBenificiarybtn.setEnabled(false);
                    focusView = Name;
                    cancel = true;
                }else {
                    AddBenificiarybtn.setEnabled(true);
                }
            }
        });

        Ifsc.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                System.out.println(s);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {


                Pattern paname =Pattern.compile("^[A-Za-z]{4}[a-zA-Z0-9]{7}$");
                Matcher matchname=paname.matcher(s);

                if (!matchname.matches()) {
                    Ifsc.setError("Enter valid Ifsc");
                    AddBenificiarybtn.setEnabled(false);
                    focusView = Ifsc;
                    cancel = true;
                }else {
                    AddBenificiarybtn.setEnabled(true);
                }

            }

            @Override
            public void afterTextChanged(Editable s) {
                System.out.println(s);
            }
        });
    }

    private void InitializeObject() {
        this.dialog = new ProgressDialog(this);
        this.Name = (EditText) findViewById(R.id.bnfName);
        this.Mobile = (EditText) findViewById(R.id.bnfMobile);
        this.Accountno = (EditText) findViewById(R.id.bnfAccountno);
        this.Ifsc = (EditText) findViewById(R.id.bnfifsc);
        this.AddBenificiarybtn = (Button) findViewById(R.id.Addbnf);
    }

    private void showProgress() {
        this.dialog.setMessage("Wait a second...");
        this.dialog.setCancelable(false);
        this.dialog.show();
    }

    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(new Intent(getApplicationContext(), MainActivity.class)));
    }

    private void dissmissProgrss() {
        this.dialog.dismiss();
    }

    private void otpView(final String str, final String str2) {
        View inflate = LayoutInflater.from(this).inflate(R.layout.confimotp, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(inflate);
        pinEntry = (PinEntryEditText) inflate.findViewById(R.id.otp);
        ProgressBar progressBar = (ProgressBar) inflate.findViewById(R.id.otpProgresss);
        Button button = (Button) inflate.findViewById(R.id.vrotp);
        TextView textView = (TextView) inflate.findViewById(R.id.resendotp);
        if (pinEntry != null) {
            pinEntry.setOnPinEnteredListener(new OnPinEnteredListener() {
                public void onPinEntered(CharSequence charSequence) {
                    InputMethodManager imm = (InputMethodManager) getApplicationContext().getSystemService(Corporate_AddBeneficiary.this.INPUT_METHOD_SERVICE);
                    imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
                    otp =  str.toString();
                    OtpValidation(Global.customerid, str, Global.mobile, Corporate_AddBeneficiary.this.otp, str2);
                }
            });
        }
        AlertDialog create = builder.create();
        button.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                Corporate_AddBeneficiary.this.OtpValidation(Global.customerid, str, Global.mobile, Corporate_AddBeneficiary.this.otp, str2);
            }
        });
        textView.setOnClickListener(new C05419());
        create.show();
    }



    private void OtpValidation(String str, String str2, String str3, String str4, String str5) {
        this.dialog.setMessage("Verifying...");
        this.dialog.show();
        final String str6 = str2;
        final String str7 = str5;
        final String str8 = str4;
        AppController.getInstance().addToRequestQueue(new StringRequest(1, APIs.Corporate_Benificiary_valid, new Listener<String>() {
            public void onResponse(String str) {
                Corporate_AddBeneficiary.this.dialog.dismiss();
                try {
                    JSONObject jSONObject = new JSONObject(str);
                    if (jSONObject.getBoolean("status")) {
                        Global.successDilogue(Corporate_AddBeneficiary.this, jSONObject.getString("result"));
                    } else {
                        Global.failedDilogue(Corporate_AddBeneficiary.this, jSONObject.getString("error"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new ErrorListener() {
            public void onErrorResponse(VolleyError volleyError) {
                Corporate_AddBeneficiary.this.dialog.dismiss();
            }
        }) {
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> hashMap = new HashMap();
                hashMap.put("customer_id", Global.customerid);
                hashMap.put("beneficiary_id", str6);
                hashMap.put("mobile", Global.mobile);
                hashMap.put("unique_id", str7);
                hashMap.put(Constants.OTP, str8);
                return hashMap;
            }
        });
    }
}
