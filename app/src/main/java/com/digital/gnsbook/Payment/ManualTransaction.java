package com.digital.gnsbook.Payment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.digital.gnsbook.Activity.MainActivity;
import com.digital.gnsbook.Config.APIs;
import com.digital.gnsbook.Global;
import com.digital.gnsbook.ViewDialog;
import com.httpgnsbook.gnsbook.R;
import com.mikhaellopez.circularprogressbar.CircularProgressBar;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class ManualTransaction extends AppCompatActivity {
    Button Transfer;
    EditText accountno;
    EditText admincharge;
    EditText amount;
    CircularProgressBar balancebar;
    CircularProgressBar consumbar;
    TextView consume;
    ProgressDialog dialog;
    EditText ifsc;
    LinearLayout layout;
    ViewDialog progressDialog;
    RequestQueue queue;
    TextView remain;
    CircularProgressBar remainbar;
    String stracc;
    String strifsc;
    EditText surcharge;
    EditText tdscharge;
    EditText total;
    TextView txtError;
    TextView withdrawBalance;

    /* renamed from: com.digital.gnsbook.Payment.ManualTransaction$1 */
    class C04971 implements TextWatcher {
        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }

        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }

        C04971() {
        }

        public void afterTextChanged(Editable editable) {
            Log.e("edited text", editable.toString());



            if (!editable.toString().equals("")) {
                int i = Integer.parseInt(editable.toString());
                double entamount = Double.parseDouble(editable.toString());
                if (i > 1000)
                {
                    amount.setError("Amount must be less than or equal to 1000");
                    surcharge.setText("0.0");
                    tdscharge.setText("0.0");
                    admincharge.setText("0.0");
                    total.setText("0.0");
                }else {
                    double amount = Double.parseDouble(editable.toString());
                    double res = (amount / 100.0f) * 1.5;
                    double tds = (entamount / 100.0f) * 5;
                    double Admincharge = (entamount / 100.0f) * 5;


                    surcharge.setText(res + "");
                    tdscharge.setText(tds + "");
                    admincharge.setText(Admincharge + "");
                    double totalamt = res+tds+Admincharge-amount;
                    total.setText(String.valueOf(Math.abs(totalamt)));
                }
            } else{
                surcharge.setText("0");
                total.setText("0");
            }}
    }

    /* renamed from: com.digital.gnsbook.Payment.ManualTransaction$8 */
    class C04998 implements OnClickListener {
        C04998() {
        }

        public void onClick(View view) {
         //   ManualTransaction.this.addMoneytoVS();
        }
    }

    /* renamed from: com.digital.gnsbook.Payment.ManualTransaction$9 */
    class C05009 implements OnTouchListener {
        C05009() {
        }

        public boolean onTouch(View view, MotionEvent motionEvent) {
            ManualTransaction.this.amount.setFocusableInTouchMode(true);
            return true;
        }
    }

    private class transferAPI extends AsyncTask<String, String, String> {
        HttpURLConnection conn;
        URL url;

        private transferAPI() {
            this.url = null;
        }

        protected void onPreExecute() {
            super.onPreExecute();
            ManualTransaction.this.ShowProgress("Processing...");
        }

        @Override
        protected String doInBackground(String... params) {


            try {
                url = new URL(APIs.Manultransfer);
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
                        .appendQueryParameter("amount", params[0])
                        .appendQueryParameter("mobile", params[1])
                        .appendQueryParameter("beneficiary_id", params[2])
                        .appendQueryParameter("customer_id", params[3]);
                // / .appendQueryParameter("ifsc", params[5]);
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

        protected void onPostExecute(String str) {
            ManualTransaction.this.DissProgress();
            try {
                JSONObject jSONObject = new JSONObject(str);
                if (jSONObject.getBoolean("status")) {
                   Global.successDilogue(ManualTransaction.this, jSONObject.getString("result"));
                } else {
                    Global.failedDilogue(ManualTransaction.this, jSONObject.getString("result"));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    /* renamed from: com.digital.gnsbook.Payment.ManualTransaction$2 */
    class C09572 implements Listener<String> {
        C09572() {
        }

        public void onResponse(String str) {
            try {
                JSONObject jSONObject = new JSONObject(str);
                ManualTransaction.this.withdrawBalance.setText(jSONObject.getString("balance"));
                double parseDouble = Double.parseDouble(jSONObject.getString("balance"));
                double parseDouble2 = Double.parseDouble(jSONObject.getString("jolo_api_balance"));
               // ManualTransaction.this.layout.setVisibility(View.VISIBLE);
                //ManualTransaction.this.txtError.setVisibility(View.GONE);
               /* if (parseDouble < 115.0d || parseDouble2 < 1500.0d) {
                    ManualTransaction.this.layout.setVisibility(View.VISIBLE);
                    ManualTransaction.this.txtError.setVisibility(View.VISIBLE);
                } else {
                    ManualTransaction.this.layout.setVisibility(View.VISIBLE);
                    ManualTransaction.this.txtError.setVisibility(View.GONE);
                }*/
               int time = Calendar.getInstance().get(11);
                PrintStream printStream = System.out;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Hour : ");
                stringBuilder.append(str);
                printStream.println(stringBuilder.toString());
                if (time < 9 || time > 23) {
                  //  ManualTransaction.this.layout.setVisibility(View.GONE);
                 //   ManualTransaction.this.txtError.setVisibility(View.VISIBLE);
                //    ManualTransaction.this.txtError.setText("Fundtransfer is only available between 9 am to 6 pm.");
                    return;
                }
               // ManualTransaction.this.layout.setVisibility(View.GONE);
                //ManualTransaction.this.txtError.setVisibility(View.VISIBLE);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    /* renamed from: com.digital.gnsbook.Payment.ManualTransaction$3 */
    class C09583 implements ErrorListener {
        C09583() {
        }

        public void onErrorResponse(VolleyError volleyError) {
            ManualTransaction.this.DissProgress();
        }
    }

    /* renamed from: com.digital.gnsbook.Payment.ManualTransaction$5 */
    class C09595 implements Listener<String> {
        C09595() {
        }

        public void onResponse(String s) {
            ManualTransaction.this.DissProgress();
/*
            try {
                JSONObject str = new JSONObject(s).getJSONObject("result").getJSONObject("fulldata").getJSONArray("remitter_limit").getJSONObject(0).getJSONObject("limit");
                ManualTransaction.this.remain.setText(str.getString("remaining"));
                ManualTransaction.this.consume.setText(str.getString("consumed"));
                float parseFloat = Float.parseFloat(str.getString("remaining"));
                float parseFloat2 = Float.parseFloat(str.getString("consumed"));
                Float.parseFloat(str.getString("total"));
                parseFloat2 = (parseFloat2 / 75000.0f) * 100.0f;
                ManualTransaction.this.remainbar.setProgress((parseFloat / 75000.0f) * 100.0f);
                ManualTransaction.this.consumbar.setProgress(parseFloat2);
                ManualTransaction.this.consumbar.setProgress((Float.parseFloat(str.getString("customer_balance")) / 100000.0f) * 1120403456);
            } catch (JSONException e) {
                e.printStackTrace();
            }
*/
        }
    }

    /* renamed from: com.digital.gnsbook.Payment.ManualTransaction$6 */
    class C09606 implements ErrorListener {

        /* renamed from: com.digital.gnsbook.Payment.ManualTransaction$6$1 */
        class C04981 implements OnClickListener {
            C04981() {
            }

            public void onClick(View view) {
                ManualTransaction.this.startActivity(new Intent(ManualTransaction.this.getApplicationContext(), MainActivity.class));
            }
        }

        C09606() {
        }

        public void onErrorResponse(VolleyError volleyError) {
            ManualTransaction.this.DissProgress();
        }
    }

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_fund_transfer);
        getSupportActionBar().hide();
        initialize();
        getExtra();
        webService();
        PrintStream printStream = System.out;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Hour : ");
        stringBuilder.append(bundle);
        printStream.println(stringBuilder.toString());
        this.amount.addTextChangedListener(new C04971());
        balance();
    }

    private void balance() {
        ShowProgress("Processing...");
        Request c11604 = new StringRequest(1, APIs.Jolo_soft_balance, new C09572(), new C09583()) {
            public String getBodyContentType() {
                return "application/x-www-form-urlencoded; charset=UTF-8";
            }

            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> hashMap = new HashMap();
                hashMap.put("customer_id", Global.customerid);
                return hashMap;
            }
        };
        c11604.setRetryPolicy(new DefaultRetryPolicy(Global.READ_TIMEOUT, 1, 1.0f));
        this.queue.add(c11604);
    }

    private void webService() {
        ShowProgress("Loading data...");
        Request c11617 = new StringRequest(1, APIs.Jolo_soft_dmr_details, new C09595(), new C09606()) {
            public String getBodyContentType() {
                return "application/x-www-form-urlencoded; charset=UTF-8";
            }

            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> hashMap = new HashMap();
                hashMap.put("mobile", Global.mobile);
                return hashMap;
            }
        };
        c11617.setRetryPolicy(new DefaultRetryPolicy(Global.READ_TIMEOUT, 1, 1.0f));
        this.queue.add(c11617);
    }

    private void getExtra() {
        this.stracc = getIntent().getStringExtra("accountno");
        this.strifsc = getIntent().getStringExtra("ifsc");
        this.accountno.setText(this.stracc);
        this.ifsc.setText(this.strifsc);
    }

    private void initialize() {
        this.queue = Volley.newRequestQueue(this);
        this.dialog = new ProgressDialog(this);
        this.progressDialog = new ViewDialog(this);
        this.accountno = (EditText) findViewById(R.id.funAccount);
        this.ifsc = (EditText) findViewById(R.id.funIfsc);
        this.amount = (EditText) findViewById(R.id.fundamount);
        this.surcharge = (EditText) findViewById(R.id.fundsurcharge);
        this.tdscharge = (EditText) findViewById(R.id.tds);
        this.admincharge = (EditText) findViewById(R.id.admincharge);
        this.total = (EditText) findViewById(R.id.fundToatal);
        this.consume = (TextView) findViewById(R.id.consumlimit);
        this.balancebar = (CircularProgressBar) findViewById(R.id.balanecustomer);
        this.consumbar = (CircularProgressBar) findViewById(R.id.consumprogrss);
        this.withdrawBalance = (TextView) findViewById(R.id.withBalance);
        this.remain = (TextView) findViewById(R.id.remainlimit);
        this.remainbar = (CircularProgressBar) findViewById(R.id.remainProgress);
        this.Transfer = (Button) findViewById(R.id.btnTrans);
        this.layout = (LinearLayout) findViewById(R.id.fundlayout);
     //   this.txtError = (TextView) findViewById(R.id.txtError);
        ((ImageView) findViewById(R.id.icaddmoneytr)).setOnClickListener(new C04998());
        this.Transfer.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                if (ManualTransaction.this.amount.getText().toString().isEmpty()) {
                    ManualTransaction.this.amount.setError("Enter valid amount");
                    ManualTransaction.this.amount.requestFocus();
                } else if (Integer.parseInt(ManualTransaction.this.amount.getText().toString()) > 1000) {
                    ManualTransaction.this.amount.setError("Amount must be less than or equal to 1000");
                    ManualTransaction.this.amount.requestFocus();
                } else {
                    new transferAPI().execute(ManualTransaction.this.amount.getText().toString(), Global.mobile, ManualTransaction.this.getIntent().getStringExtra("Bid"), Global.customerid);
                }
            }
        });
    }

    public void ShowProgress(String str) {
        this.dialog.setMessage(str);
        this.dialog.setCancelable(false);
        this.dialog.show();
    }

    public void DissProgress() {
        this.dialog.dismiss();
    }



    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(new Intent(getApplicationContext(), MainActivity.class)));
    }
}
