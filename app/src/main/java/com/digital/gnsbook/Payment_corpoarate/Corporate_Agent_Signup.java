package com.digital.gnsbook.Payment_corpoarate;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.net.Uri.Builder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
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
import com.digital.gnsbook.Config.SQLiteHandler;
import com.digital.gnsbook.Global;
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
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;
import swarajsaaj.smscodereader.interfaces.OTPListener;
import swarajsaaj.smscodereader.receivers.OtpReader;

public class Corporate_Agent_Signup extends AppCompatActivity implements OTPListener {
    ProgressBar OtpProgress;
    EditText Pincode;
    SimpleDateFormat SDF = new SimpleDateFormat("ddMMyyyyhhmmss");
    Button btnAgentsignup;
    SQLiteHandler db;
    ProgressDialog dialog;
    String otp;
    PinEntryEditText pinEntry;
    ProgressDialog progressDialog;

    /* renamed from: com.digital.gnsbook.Payment_corpoarate.Corporate_Agent_Signup$1 */
    class C05421 implements OnClickListener {
        C05421() {
        }

        public void onClick(View view) {
            if (Corporate_Agent_Signup.this.Pincode.getText().toString().equals("") ) {
                Global.diloge(Corporate_Agent_Signup.this, "Invalid Pin", "Enter Valid Address");
            } else {
                Corporate_Agent_Signup.this.agent_signup();
            }
        }
    }

    /* renamed from: com.digital.gnsbook.Payment_corpoarate.Corporate_Agent_Signup$5 */
    class C05455 implements OnClickListener {
        C05455() {
        }

        public void onClick(View view) {
            Corporate_Agent_Signup.this.startActivity(new Intent(Corporate_Agent_Signup.this.getApplicationContext(), Corporate_BenificiaryList.class));
        }
    }
      class ConfirmOTP extends AsyncTask<String, String, String> {
        HttpURLConnection conn;
        URL url = null;

        ConfirmOTP() {
        }

        protected void onPreExecute() {
            Corporate_Agent_Signup.this.showProgress();
        }

        @Override
        protected String doInBackground(String... params) {


            try {


                url = new URL(APIs.Corporate_AgentVr);

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
                        .appendQueryParameter("mobile", params[1])
                        .appendQueryParameter("Constants.OTP", params[2])
                        ;
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
            Corporate_Agent_Signup.this.dissmissProgrss();
            try {
                JSONObject jSONObject = new JSONObject(s);
                if (jSONObject.getBoolean("status")) {
                    db.deleteUsers();
                    db.addUser(Global.customerid, Global.refferalid, Global.Email, Global.mobile, Global.name, Corporate_Agent_Signup.this.SDF.format(new Date()), "1", " ");
                    Global.successDilogue(Corporate_Agent_Signup.this, jSONObject.getString("result"));
                    return;
                }
                Global.failedDilogue(Corporate_Agent_Signup.this, jSONObject.getString("result"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    /* renamed from: com.digital.gnsbook.Payment_corpoarate.Corporate_Agent_Signup$2 */
    class C09842 implements Listener<String> {

        /* renamed from: com.digital.gnsbook.Payment_corpoarate.Corporate_Agent_Signup$2$2 */
        class C05432 implements OnClickListener {
            C05432() {
            }

            public void onClick(View view) {
                new ConfirmOTP().execute(Global.customerid, Global.mobile, Corporate_Agent_Signup.this.otp);
            }
        }

        /* renamed from: com.digital.gnsbook.Payment_corpoarate.Corporate_Agent_Signup$2$3 */
        class C05443 implements OnClickListener {
            public void onClick(View view) {
            }

            C05443() {
            }
        }

        /* renamed from: com.digital.gnsbook.Payment_corpoarate.Corporate_Agent_Signup$2$1 */
        class C09831 implements OnPinEnteredListener {
            String string;
            C09831(String str) {
                string = str ;
            }

            public void onPinEntered(CharSequence charSequence) {
                InputMethodManager imm = (InputMethodManager) getApplicationContext().getSystemService(Corporate_Agent_Signup.this.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
                otp =  string.toString();
                new ConfirmOTP().execute(Global.customerid, Global.mobile, Corporate_Agent_Signup.this.otp);
            }
        }

        C09842() {
        }

        public void onResponse(String str) {
            progressDialog.dismiss();
            try {
                JSONObject jSONObject = new JSONObject(str);
                if (jSONObject.getBoolean("status")) {
                    Global.successDilogue(Corporate_Agent_Signup.this, jSONObject.getString("desc"));
                } else if (jSONObject.has("error")){
                    Global.failedDilogue(Corporate_Agent_Signup.this, jSONObject.getString("error"));

                }else if (jSONObject.getString("desc").equals("Virtual account already exist & verified")) {
                    db.deleteUsers();
                    db.addUser(Global.customerid, Global.refferalid, Global.Email, Global.mobile, Global.name, Corporate_Agent_Signup.this.SDF.format(new Date()), "1", " ");
                    Global.successDilogue(Corporate_Agent_Signup.this, jSONObject.getString("desc"));
                } else {
                    View mView = LayoutInflater.from(Corporate_Agent_Signup.this).inflate(R.layout.confimotp, null);
                    AlertDialog.Builder builder = new AlertDialog.Builder(Corporate_Agent_Signup.this);
                    builder.setView(mView);
                    pinEntry = (PinEntryEditText) mView.findViewById(R.id.otp);
                    OtpProgress = (ProgressBar) mView.findViewById(R.id.otpProgresss);
                    Button button = (Button) mView.findViewById(R.id.vrotp);
                    TextView textView = (TextView) mView.findViewById(R.id.resendotp);
                    if (pinEntry != null) {
                        pinEntry.setOnPinEnteredListener(new C09831(str));
                    }
                    AlertDialog create = builder.create();
                    button.setOnClickListener(new C05432());
                    textView.setOnClickListener(new C05443());
                    create.show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    /* renamed from: com.digital.gnsbook.Payment_corpoarate.Corporate_Agent_Signup$3 */
    class C09853 implements ErrorListener {
        C09853() {
        }

        public void onErrorResponse(VolleyError volleyError) {
            Corporate_Agent_Signup.this.progressDialog.dismiss();

        }
    }

    /* renamed from: com.digital.gnsbook.Payment_corpoarate.Corporate_Agent_Signup$7 */
    class C09867 implements Listener<String> {
        C09867() {
        }

        public void onResponse(String str) {
            Corporate_Agent_Signup.this.dissmissProgrss();
        }
    }

    /* renamed from: com.digital.gnsbook.Payment_corpoarate.Corporate_Agent_Signup$8 */
    class C09878 implements ErrorListener {

        /* renamed from: com.digital.gnsbook.Payment_corpoarate.Corporate_Agent_Signup$8$1 */
        class C05471 implements OnClickListener {
            C05471() {
            }

            public void onClick(View view) {
                Corporate_Agent_Signup.this.startActivity(new Intent(Corporate_Agent_Signup.this.getApplicationContext(), MainActivity.class));
            }
        }

        C09878() {
        }

        public void onErrorResponse(VolleyError volleyError) {
           dissmissProgrss();

        }
    }

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_agent_signup);
        this.db = new SQLiteHandler(this);
        OtpReader.bind(this, "VM-iMONEY");
        OtpReader.bind(this, "IM-iMONEY");
        OtpReader.bind(this, "AD-MONEYT");
        this.progressDialog = new ProgressDialog(this);
        this.dialog = new ProgressDialog(this);
        this.Pincode = (EditText) findViewById(R.id.agPincode);
        this.btnAgentsignup = (Button) findViewById(R.id.btnAgentsignup);
        this.btnAgentsignup.setOnClickListener(new C05421());
    }

    private void agent_signup() {
        this.progressDialog.setTitle("Submitting...");
        this.progressDialog.setCancelable(true);
        this.progressDialog.show();
        AppController.getInstance().addToRequestQueue(new StringRequest(1, APIs.Corporate_AgentSignup, new C09842(), new C09853()) {
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> hashMap = new HashMap();
                hashMap.put("customer_id", Global.customerid);
                hashMap.put("name", Global.name);
                hashMap.put("mobile", Global.mobile);
                hashMap.put(NotificationCompat.CATEGORY_EMAIL, Global.Email);
                hashMap.put("address", Corporate_Agent_Signup.this.Pincode.getText().toString());
                return hashMap;
            }
        });
    }

    public void otpReceived(String str) {
        this.OtpProgress.setVisibility(View.GONE);
        this.pinEntry.setText(str.replaceAll("[^0-9]", ""));
    }

    private void showProgress() {
        this.dialog.setMessage("Wait a second...");
        this.dialog.setCancelable(false);
        this.dialog.show();
    }

    private void dissmissProgrss() {
        this.dialog.dismiss();
    }

    private void ResendOTP() {
        showProgress();
        AppController.getInstance().addToRequestQueue(new StringRequest(1, APIs.reSendOtp, new C09867(), new C09878()) {
            public String getBodyContentType() {
                return "application/x-www-form-urlencoded; charset=UTF-8";
            }

            protected Map<String, String> getParams() throws AuthFailureError {
                return new HashMap();
            }
        });
    }
}
