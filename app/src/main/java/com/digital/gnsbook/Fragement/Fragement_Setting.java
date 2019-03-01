package com.digital.gnsbook.Fragement;

import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.net.Uri;
import android.net.Uri.Builder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.AuthFailureError;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.load.Key;
import com.digital.gnsbook.Config.APIs;
import com.digital.gnsbook.Config.AppController;
import com.digital.gnsbook.Config.SQLiteHandler;
import com.digital.gnsbook.Global;
import com.digital.gnsbook.UserVerification;
import com.digital.gnsbook.ViewDialog;
import com.httpgnsbook.gnsbook.R;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Parameter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.angmarch.views.NiceSpinner;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Fragement_Setting extends Fragment {
    EditText DOB;
    Button Update;
    EditText about;
    EditText city;
    final List<String> dataset = new LinkedList(Arrays.asList(new String[]{"MALE", "FEMALE"}));
    SQLiteHandler db;
    ViewDialog dialog;
    EditText email;
    EditText fname;
    EditText lname;
    EditText mobile;
    NiceSpinner niceSpinner;
    EditText occupation;
    ViewDialog pdLoading;
    ImageView picdate;
    EditText pincode;
    EditText state;
    String strEmail;
    String strMobile;
    String strPhone;
    String stremail;
    Button updEmail;
    Button updMobile;
    ImageView updatepic;
    TextView verify;
    Button vrem;
    Button vrmb;
    EditText website;

    /* renamed from: com.digital.gnsbook.Fragement.Fragement_Setting$1 */
    class C04411 implements OnItemClickListener {
        C04411() {
        }

        public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
            Toast.makeText(Fragement_Setting.this.getActivity(), (CharSequence) Fragement_Setting.this.dataset.get(i), 0).show();
        }
    }

    /* renamed from: com.digital.gnsbook.Fragement.Fragement_Setting$2 */
    class C04422 implements OnClickListener {
        C04422() {
        }

        public void onClick(View view) {
            Fragement_Setting.this.profileValidation();
        }
    }

    /* renamed from: com.digital.gnsbook.Fragement.Fragement_Setting$3 */
    class C04433 implements OnClickListener {
        C04433() {
        }

        public void onClick(View view) {
            Fragement_Setting.this.datePicker();
        }
    }

    /* renamed from: com.digital.gnsbook.Fragement.Fragement_Setting$4 */
    class C04444 implements OnClickListener {
        C04444() {
        }

        public void onClick(View view) {
           new UserVerification(getActivity());
        }
    }

    /* renamed from: com.digital.gnsbook.Fragement.Fragement_Setting$5 */
    class C04455 implements OnClickListener {
        C04455() {
        }

        public void onClick(View view) {
            new UserVerification(getActivity());
        }
    }

    /* renamed from: com.digital.gnsbook.Fragement.Fragement_Setting$6 */
    class C04466 implements OnClickListener {
        C04466() {
        }

        public void onClick(View view) {
            if (Fragement_Setting.this.mobile.getText().toString().equals("") ) {
                Global.diloge(Fragement_Setting.this.getActivity(), "Update Error", "Enter Mobile No");
                return;
            }
            if (Fragement_Setting.this.mobile.getText().length() >= 10) {
                if (Fragement_Setting.this.mobile.getText().length() <= 10) {
                    Fragement_Setting.this.updateMobile();
                    return;
                }
            }
            Global.diloge(Fragement_Setting.this.getActivity(), "Update Error", "Enter Valid Mobile No");
        }
    }

    /* renamed from: com.digital.gnsbook.Fragement.Fragement_Setting$7 */
    class C04477 implements OnClickListener {
        C04477() {
        }

        public void onClick(View view) {
            Fragement_Setting.this.updateEmail();
        }
    }

    /* renamed from: com.digital.gnsbook.Fragement.Fragement_Setting$8 */
    class C04488 implements OnClickListener {
        C04488() {
        }

        public void onClick(View view) {
            new UserVerification(getActivity());
        }
    }

    public class UserDetailTask extends AsyncTask<String, String, String> {
        HttpURLConnection conn;
        URL url = null;

        protected void onCancelled() {
        }

        protected void onPreExecute() {
            super.onPreExecute();
            Fragement_Setting.this.pdLoading.show();
        }

        @Override
        protected String doInBackground(String... params) {
            try {

                // Enter URL address where your php file resides
                //url = new URL("http://192.168.2.2:80/pro/login.inc.php");
                url = new URL(APIs.ProfileDetail);
                //url = new URL("http://www.sd-constructions.com/bhushan/login.inc.php");

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
                        .appendQueryParameter("customer_id", params[0]);
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

        protected void onPostExecute(String responce) {
            Fragement_Setting.this.pdLoading.dismiss();
            try {
                JSONObject jSONObject = new JSONObject(responce);
                if (jSONObject.getBoolean("status")) {
                    JSONObject str = jSONObject.getJSONArray("result").getJSONObject(0);
                    Global.DP = str.getString("d_pic");
                    Global.Banner = str.getString("b_pic");
                    Global.City = str.getString("city");
                    email.setText(str.getString(NotificationCompat.CATEGORY_EMAIL));
                    strEmail = str.getString(NotificationCompat.CATEGORY_EMAIL);
                    strMobile = str.getString("mobile");
                    mobile.setText(str.getString("mobile"));
                    fname.setText(str.getString("name"));
                    lname.setText(str.getString("last_name"));
                    pincode.setText(str.getString("pincode"));
                    about.setText(str.getString("about"));
                    website.setText(str.getString("website"));
                    state.setText(str.getString("state"));
                    DOB.setText(str.getString("birthdate"));
                    occupation.setText(str.getString("employement"));
                    city.setText(str.getString("city"));
                    if (str.getString("sex").equals("Male")) {
                        Fragement_Setting.this.niceSpinner.setSelectedIndex(0);
                    } else {
                        Fragement_Setting.this.niceSpinner.setSelectedIndex(1);
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    /* renamed from: com.digital.gnsbook.Fragement.Fragement_Setting$9 */
    class C09189 implements Listener<String> {
        C09189() {
        }

        public void onResponse(String str) {
            Fragement_Setting.this.pdLoading.dismiss();
            try {
                JSONObject jSONObject = new JSONObject(str);
                if (jSONObject.getBoolean("status")) {
                    Fragement_Setting.this.db.addUser(Global.customerid, Global.refferalid, Global.Email, Fragement_Setting.this.mobile.getText().toString(), Global.name, "", Global.A_status, Global.agentid);
                    Global.successDilogue(Fragement_Setting.this.getActivity(), jSONObject.getString("result"));
                    return;
                }
                Global.failedDilogue(Fragement_Setting.this.getActivity(), jSONObject.getString("result"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public View onCreateView(LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, Bundle bundle) {
        View view = layoutInflater.inflate(R.layout.fragement_setting, viewGroup, false);
        dialog = new ViewDialog(getActivity());
        pdLoading = new ViewDialog(getActivity());
        db = new SQLiteHandler(getActivity());
        DOB     = view.findViewById(R.id.DOB);
        Update  = view.findViewById(R.id.updatedata);
        fname   = view.findViewById(R.id.prname);
        lname   = view.findViewById(R.id.prlname);
        city    = view.findViewById(R.id.prCity);
        state   = view.findViewById(R.id.prstate);
        pincode = view.findViewById(R.id.prpincode);
        website     = view.findViewById(R.id.prwebsite);
        occupation  = view.findViewById(R.id.proccupation);
        about       = view.findViewById(R.id.prDesc);
        email       = view.findViewById(R.id.prvemail);
        mobile      = view.findViewById(R.id.prvmobile);
        updMobile   = view.findViewById(R.id.vrmbstst);
        updEmail    = view.findViewById(R.id.vremstst);
        vrem        = view.findViewById(R.id.vrem);
        vrmb        = view.findViewById(R.id.vrmb);
        picdate     = view.findViewById(R.id.picdate);
        updatepic   = view.findViewById(R.id.updatePic);
        niceSpinner = view.findViewById(R.id.prgender_spinner);
        verify      = view.findViewById(R.id.prverify);
        new UserDetailTask().execute(Global.customerid);
        this.niceSpinner.attachDataSource(this.dataset);
        this.niceSpinner.addOnItemClickListener(new C04411());
        this.Update.setOnClickListener(new C04422());
        this.picdate.setOnClickListener(new C04433());
        if (Global.verify_sms.equals("1")) {
            this.vrmb.setVisibility(View.GONE);
            this.updMobile.setVisibility(View.GONE);
            this.mobile.setEnabled(false);
            this.strPhone = "";
        }

        if (Global.A_status.equals("1")) {
          //  this.vrmb.setVisibility(View.GONE);
            this.updMobile.setVisibility(View.GONE);
            this.mobile.setEnabled(false);
            this.strPhone = "";
        }



        if (Global.verify_email.equals("1") ) {
            this.updEmail.setVisibility(View.GONE);
            this.vrem.setVisibility(View.GONE);
            this.email.setEnabled(false);
            this.stremail = "";
        }
        this.vrem.setOnClickListener(new C04444());
        this.vrmb.setOnClickListener(new C04455());
        if (!(Global.verify_sms.equals("1") || Global.verify_email.equals("1") )) {
            this.verify.setVisibility(View.GONE);
        }
        this.updMobile.setOnClickListener(new C04466());
        this.updEmail.setOnClickListener(new C04477());
        this.verify.setOnClickListener(new C04488());
        return view;
    }

    private void updateMobile() {
        this.pdLoading.show();
        AppController.getInstance().addToRequestQueue(new StringRequest(1, APIs.update_mobile, new C09189(), new ErrorListener() {
            public void onErrorResponse(VolleyError volleyError) {
                Fragement_Setting.this.pdLoading.dismiss();
            }
        }) {
            public Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> hashMap = new HashMap();
                hashMap.put("customer_id", Global.customerid);
                hashMap.put("mobile", Fragement_Setting.this.mobile.getText().toString());
                hashMap.put(NotificationCompat.CATEGORY_EMAIL, Fragement_Setting.this.email.getText().toString());
                return hashMap;
            }
        });
    }

    private void updateEmail() {
        this.pdLoading.show();
        AppController.getInstance().addToRequestQueue(new StringRequest(1, APIs.update_email, new Listener<String>() {
            public void onResponse(String str) {
                Fragement_Setting.this.pdLoading.dismiss();
                try {
                    JSONObject jSONObject = new JSONObject(str);
                    if (jSONObject.getBoolean("status")) {
                        Fragement_Setting.this.db.addUser(Global.customerid, Global.refferalid, Fragement_Setting.this.email.getText().toString(), Fragement_Setting.this.mobile.getText().toString(), Global.name, "", Global.A_status, Global.agentid);
                        Global.successDilogue(Fragement_Setting.this.getActivity(), jSONObject.getString("result"));
                        return;
                    }
                    Global.failedDilogue(Fragement_Setting.this.getActivity(), jSONObject.getString("result"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new ErrorListener() {
            public void onErrorResponse(VolleyError volleyError) {
                Fragement_Setting.this.pdLoading.dismiss();
            }
        }) {
            public Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> hashMap = new HashMap();
                hashMap.put("customer_id", Global.customerid);
                hashMap.put("mobile", Fragement_Setting.this.mobile.getText().toString());
                hashMap.put(NotificationCompat.CATEGORY_EMAIL, Fragement_Setting.this.email.getText().toString());
                return hashMap;
            }
        });
    }

    private void UpdateContact() {
        this.pdLoading.show();
        AppController.getInstance().addToRequestQueue(new StringRequest(1, APIs.contactUpadte, new Listener<String>() {
            public void onResponse(String str) {
                Fragement_Setting.this.pdLoading.dismiss();
                try {
                    JSONObject jSONObject = new JSONObject(str);
                    if (jSONObject.getBoolean("status") ) {
                        Fragement_Setting.this.db.addUser(Global.customerid, Global.refferalid, Fragement_Setting.this.email.getText().toString(), Fragement_Setting.this.mobile.getText().toString(), Global.name, "", Global.A_status, Global.agentid);
                        Global.successDilogue(Fragement_Setting.this.getActivity(), jSONObject.getString("result"));
                        return;
                    }
                    Global.failedDilogue(Fragement_Setting.this.getActivity(), jSONObject.getString("result"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new ErrorListener() {
            public void onErrorResponse(VolleyError volleyError) {
                Fragement_Setting.this.pdLoading.dismiss();
            }
        }) {
            public Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> hashMap = new HashMap();
                hashMap.put("customer_id", Global.customerid);
                hashMap.put("mobile", Fragement_Setting.this.mobile.getText().toString());
                hashMap.put(NotificationCompat.CATEGORY_EMAIL, Fragement_Setting.this.email.getText().toString());
                return hashMap;
            }
        });
    }

    public void datePicker() {
        Calendar instance = Calendar.getInstance();
        new DatePickerDialog(getActivity(), new OnDateSetListener() {
            public void onDateSet(DatePicker datePicker, int i, int i2, int i3) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(i);
                stringBuilder.append("/");
                stringBuilder.append(i2 + 1);
                stringBuilder.append("/");
                stringBuilder.append(i3);
                DOB.setText(stringBuilder.toString());
            }
        }, instance.get(1), instance.get(2), instance.get(5)).show();
    }

    private void profileValidation() {
        View view;
        Pattern compile = Pattern.compile("^[\\p{L} .'-]+$");
        Matcher matcher = compile.matcher(this.fname.getText().toString());
        Matcher matcher2 = compile.matcher(this.lname.getText().toString());
        Object obj = 1;
        if (TextUtils.isEmpty(this.fname.getText().toString())) {
            this.fname.setError(getString(R.string.error_field_required));
            view = this.fname;
        } else if (!matcher.matches()) {
            this.fname.setError("Enter First Name");
            view = this.fname;
        } else if (TextUtils.isEmpty(this.lname.getText().toString())) {
            this.lname.setError(getString(R.string.error_field_required));
            view = this.lname;
        } else if (!matcher2.matches()) {
            this.lname.setError("Enter Last Name");
            view = this.lname;
        } else if (TextUtils.isEmpty(this.DOB.getText().toString())) {
            this.DOB.setError(getString(R.string.error_field_required));
            view = this.DOB;
        } else if (TextUtils.isEmpty(this.occupation.getText().toString())) {
            this.occupation.setError(getString(R.string.error_field_required));
            view = this.occupation;
        } else if (TextUtils.isEmpty(this.about.getText().toString())) {
            this.about.setError(getString(R.string.error_field_required));
            view = this.about;
        } else if (TextUtils.isEmpty(this.state.getText().toString())) {
            this.state.setError(getString(R.string.error_field_required));
            view = this.state;
        } else if (TextUtils.isEmpty(this.city.getText().toString())) {
            this.city.setError(getString(R.string.error_field_required));
            view = this.city;
        } else if (TextUtils.isEmpty(this.pincode.getText().toString())) {
            this.pincode.setError(getString(R.string.error_field_required));
            view = this.pincode;
        } else if (this.pincode.getText().toString().length() <= 5) {
            this.pincode.setError("Password length must be greater than or equal to 6");
            view = this.pincode;
        } else if (TextUtils.isEmpty(this.website.getText().toString())) {
            this.website.setError(getString(R.string.error_field_required));
            view = this.website;
        } else {
            obj = null;
            view = null;
        }
        if (obj != null) {
            view.requestFocus();
        } else {
            Updateprofile();
        }
    }

    private void Updateprofile() {
        this.dialog.show();
        AppController.getInstance().addToRequestQueue(new StringRequest(1, APIs.UpdateProfile, new Listener<String>() {
            public void onResponse(String str) {
                Fragement_Setting.this.dialog.dismiss();
                try {
                    if (new JSONObject(str).getBoolean("status")) {
                        Global.successDilogue(Fragement_Setting.this.getActivity(), "Your profile has been updated");
                    } else {
                        Global.diloge(Fragement_Setting.this.getActivity(), "Failed", "Some thing went wrong");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new ErrorListener() {
            public void onErrorResponse(VolleyError volleyError) {
                Fragement_Setting.this.dialog.dismiss();
            }
        }) {
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> hashMap = new HashMap();
                hashMap.put("customer_id", Global.customerid);
                hashMap.put("name", Fragement_Setting.this.fname.getText().toString());
                hashMap.put("last_name", Fragement_Setting.this.lname.getText().toString());
                hashMap.put("about", Fragement_Setting.this.about.getText().toString());
                hashMap.put("birthdate", Fragement_Setting.this.DOB.getText().toString());
                hashMap.put("employement", Fragement_Setting.this.occupation.getText().toString());
                hashMap.put("pincode", Fragement_Setting.this.pincode.getText().toString());
                hashMap.put("country", "india");
                hashMap.put("state", Fragement_Setting.this.state.getText().toString());
                hashMap.put("city", Fragement_Setting.this.city.getText().toString());
                hashMap.put("website", Fragement_Setting.this.website.getText().toString());
                hashMap.put("sex", Fragement_Setting.this.dataset.get(Fragement_Setting.this.niceSpinner.getSelectedIndex()));
                return hashMap;
            }
        });
    }
}
