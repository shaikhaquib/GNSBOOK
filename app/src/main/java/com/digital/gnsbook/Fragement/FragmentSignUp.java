package com.digital.gnsbook.Fragement;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.digital.gnsbook.Config.APIs;
import com.digital.gnsbook.Config.AppController;
import com.digital.gnsbook.Global;
import com.digital.gnsbook.Activity.LoginActivity;
import com.digital.gnsbook.ViewDialog;
import com.httpgnsbook.gnsbook.R;

import org.angmarch.views.NiceSpinner;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FragmentSignUp extends Fragment {


    Button Submit;

    int intGen=0;

    EditText edtfname ,edtlname ,edtemail,edtphone,edtreferral,edtaddress,edtpassword,edtcity,edtpin;
    String fname ,lname ,email,phone,referral,address,password,city,pin,gender;
    ViewDialog progressDialog;


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frgsignup,container,false);

        progressDialog = new ViewDialog(getActivity());


        edtfname = view.findViewById(R.id.fname);
        edtlname = view.findViewById(R.id.lname);
        edtemail = view.findViewById(R.id.email);
        edtphone = view.findViewById(R.id.mobile);
        edtreferral = view.findViewById(R.id.referralid);
        edtpassword = view.findViewById(R.id.password);
        Submit = view.findViewById(R.id.spsignup);



        NiceSpinner niceSpinner =view.findViewById(R.id.gender_spinner);
        final List<String> dataset = new LinkedList<>(Arrays.asList("MALE", "FEMALE"));
        niceSpinner.attachDataSource(dataset);

        niceSpinner.addOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getActivity(), dataset.get(position), Toast.LENGTH_SHORT).show();
            }
        });


        Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                fname = edtfname.getText().toString();
                lname = edtlname.getText().toString();
                email = edtemail.getText().toString();
                phone = edtphone.getText().toString();
                password = edtpassword.getText().toString();
                referral = edtreferral.getText().toString();

                profileValidation();


            }
        });


        return view;
    }


    private void profileValidation() {

        boolean cancel= false;
        View focusView = null;

        Pattern pattern1=Pattern.compile("\\w+([-+.']\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");
        Matcher matcher1=pattern1.matcher(email);
        String regx = "^[\\p{L} .'-]+$";
        Pattern paname =Pattern.compile(regx);
        Matcher matchfname=paname.matcher(fname);
        Matcher matchlname=paname.matcher(lname);


        if (intGen == 0){
            gender = "M" ;
        }else {
            gender = "F" ;
        }


        if (TextUtils.isEmpty(fname)) {
            edtfname.setError(getString(R.string.error_field_required));
            focusView = edtfname;
            cancel = true;
        }else if (!matchfname.matches()) {
            edtfname.setError("Enter First Name");
            focusView = edtfname;
            cancel = true;
        }else if (TextUtils.isEmpty(lname)) {
            edtlname.setError(getString(R.string.error_field_required));
            focusView = edtlname;
            cancel = true;
        }else if (!matchlname.matches()) {
            edtlname.setError("Enter Last Name");
            focusView = edtlname;
            cancel = true;
        }else if (TextUtils.isEmpty(email)) {
            edtemail.setError(getString(R.string.error_field_required));
            focusView = edtemail;
            cancel = true;
        }else if (!matcher1.matches() && !email.isEmpty()) {
            edtemail.setError("Enter Valid Email");
            focusView = edtemail;
            cancel = true;
        }else if (TextUtils.isEmpty(phone)) {
            edtphone.setError(getString(R.string.error_field_required));
            focusView = edtphone;
            cancel = true;
        }else if (phone.length() < 10) {
            edtphone.setError("Enter valid mobile no");
            focusView = edtphone;
            cancel = true;
        }else if (TextUtils.isEmpty(password)) {
            edtpassword.setError(getString(R.string.error_field_required));
            focusView = edtpassword;
            cancel = true;
        }else if (password.length() <= 5) {
            edtpassword.setError("Password length must be greater than or equal to 6");
            focusView = edtpassword;
            cancel = true;
        }if (cancel) {
            focusView.requestFocus();
        }
        else {
            Authenticate();
        }
    }

    private void Authenticate() {
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(StringRequest.Method.POST, APIs.RegisterAPI, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                progressDialog.dismiss();

                try {

                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getBoolean("status"))
                    {

                        // new Fcm().execute(edtreferral.getText().toString(),"Mr/Miss "+edtfname.getText().toString()+ " has joined with your reference. Whenever he/she play any game you will be rewarded." ,"Congratulation");
                        AlertDialog.Builder builder;
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            builder = new AlertDialog.Builder(getActivity(), android.R.style.Theme_Material_Light_Dialog_Alert);
                        } else {
                            builder = new AlertDialog.Builder(getActivity());
                        }
                        builder.setCancelable(false);
                        builder.setTitle("Success")
                                .setMessage("You have successfully registered.")
                                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                        startActivity(new Intent(getActivity(),LoginActivity.class));
                                    }
                                })
                                .show();
                    }
                    else {
                        JSONObject json = new JSONObject(response);

                        if (json.has("result")) {

                            JSONObject dataObject = json.optJSONObject("result");

                            if (dataObject != null) {

                                JSONObject error =jsonObject.getJSONObject("result");

                                if (error.has("email") && error.has("mobile")){
                                    Global.diloge(getActivity(),"Signup Error" , error.getJSONArray("email").getString(0) + "\n" + error.getJSONArray("mobile").getString(0) );
                                }
                                else  if (error.has("email"))
                                {
                                    Global.diloge(getActivity(),"Signup Error" , error.getJSONArray("email").getString(0));
                                }
                                else  if (error.has("mobile"))
                                {
                                    Global.diloge(getActivity(),"Signup Error" , error.getJSONArray("mobile").getString(0));
                                }else {
                                    Global.diloge(getActivity(),"Signup Error" , jsonObject.getString("result"));
                                }
                            } else {

                                JSONArray array = json.optJSONArray("data");
                                Global.diloge(getActivity(),"Signup Error" , jsonObject.getString("result"));



                                //Do things with array
                            }
                        } else {
                            // Do nothing or throw exception if "data" is a mandatory field
                        }


                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();

                String body = null;
                //get status code here
                String statusCode = String.valueOf(error.networkResponse.statusCode);
                //get response body and parse with appropriate encoding
                if(error.networkResponse.data!=null) {
                    try {
                        body = new String(error.networkResponse.data,"UTF-8");
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    System.out.println("Error "+body);
                }


            }
        })

        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map <String,String> param = new HashMap<String,String>();
                param.put("first_name",fname);
                param.put("last_name",lname);
                param.put("email",email);
                param.put("password",password);
                param.put("mobile",phone);
                param.put("referral_id",referral);
                return param;
            }
        };
        AppController.getInstance().addToRequestQueue(stringRequest);

    }


}