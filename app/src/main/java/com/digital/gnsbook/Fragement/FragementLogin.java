package com.digital.gnsbook.Fragement;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.digital.gnsbook.Config.APIs;
import com.digital.gnsbook.Config.AppController;
import com.digital.gnsbook.Config.SQLiteHandler;
import com.digital.gnsbook.Config.SessionManager;
import com.digital.gnsbook.Global;
import com.digital.gnsbook.Activity.MainActivity;
import com.digital.gnsbook.ViewDialog;
import com.httpgnsbook.gnsbook.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class FragementLogin extends Fragment {

    private static final String COMMON_TAG = "CombinedLifeCycle";
    private static final String FRAGMENT_NAME = FragementLogin.class.getSimpleName();
    private static final String TAG = FRAGMENT_NAME;

    EditText edtEmail ,edtPassword;
    String email,password;
    Button Login;
    ViewDialog progressDialog;
    SQLiteHandler db;
    SessionManager session;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frglogin,container,false);


        edtEmail = view.findViewById(R.id.email);
        edtPassword = view.findViewById(R.id.lgPassword);
        Login = view.findViewById(R.id.btnLogin);
        session=new SessionManager(getActivity());
        db=new SQLiteHandler(getActivity());


        progressDialog = new ViewDialog(getActivity());

        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email = edtEmail.getText().toString();
                password = edtPassword.getText().toString();

                boolean cancel= false;
                View focusView = null;

                if (TextUtils.isEmpty(email)){
                    edtEmail.setError(getString(R.string.error_field_required));
                    focusView = edtEmail;
                    cancel = true;
                } else if (TextUtils.isEmpty(password)){
                    edtPassword.setError(getString(R.string.error_field_required));
                    focusView = edtPassword;
                    cancel = true;
                }if (cancel) {
                    focusView.requestFocus();
                }
                else {
                    //    startActivity(new Intent(getApplicationContext(),MainActivity.class));

                    Aunthanticate(email ,password);

                    //    startActivity(new Intent(getActivity(),MainActivity.class));
                }
            }
        });




        return view;
    }

    private void Aunthanticate(final String email,final String password) {


        progressDialog.show();
        StringRequest stringRequest = new StringRequest(StringRequest.Method.POST, APIs.LoginAPI, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                progressDialog.dismiss();

                try {

                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getBoolean("status"))
                    {
                        session.setLogin(true);
                        JSONObject data=jsonObject.getJSONObject("result");
                        db.addUser(
                                data.getString("customer_id"),
                                data.getString("referral_id"),
                                data.getString("email"),
                                data.getString("mobile"),
                                data.getString("name")+" "+data.getString("last_name"),
                                data.getString("added_time"),
                                data.getString("agent_status"),
                                data.getString("agent_id"));
                        startActivity(new Intent(getActivity(),MainActivity.class));
                        getActivity().finish();
                    }
                    else
                    {
                        Global.diloge(getActivity(),"Login Error" , jsonObject.getString("result"));
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
            }
        })

        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map <String,String> param = new HashMap<String,String>();
                param.put("email",email);
                param.put("password",password);
                return param;
            }
        };
        AppController.getInstance().addToRequestQueue(stringRequest);

    }


}