package com.digital.gnsbook.Fragement;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

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

import java.util.HashMap;
import java.util.Map;

public class Fragement_ChangePass extends Fragment {


    EditText ccpss , enpss ,cnpss ;
    Button btnchang;
    ViewDialog dialog ;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragement_changepass, container, false);


        ccpss = view.findViewById(R.id.ccPassword);
        enpss = view.findViewById(R.id.enPassword);
        cnpss = view.findViewById(R.id.cnPassword);
        btnchang = view.findViewById(R.id.btnchangepass);
        dialog = new ViewDialog(getActivity());


        cnpss.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String passwrd = enpss.getText().toString();
                if (editable.length() > 0 && passwrd.length() > 0) {
                    if(!editable.toString() .equals(passwrd )){
                        // give an error that password and confirm password not match
                        cnpss.setError("Password Not Matching.");
                    }

                }
            }
        });

        btnchang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ccpss.getText().toString().isEmpty() || ccpss.getText().toString().equals("")){
                    ccpss.setError("Enter Old Password");
                }else if (!enpss.getText().toString().equals(cnpss.getText().toString())){
                    cnpss.setError("Password not matching");
                }else {
                    Changepassword();
                }
            }
        });


        return view;
    }

    private void Changepassword() {

        dialog.show();
        StringRequest stringRequest = new StringRequest(StringRequest.Method.POST, APIs.UpdatePassword, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                dialog.dismiss();
                try {
                    JSONObject object = new JSONObject(response);

                    if (object.getInt("status") == 1){
                        Global.successDilogue(getActivity(),"You have successively update your password..");
                    }else {
                        Global.failedDilogue(getActivity(),object.getString("result"));

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

                Map<String,String> param = new HashMap<>();
                param.put("customer_id",Global.customerid);
                param.put("password",ccpss.getText().toString());
                param.put("new_password",cnpss.getText().toString());

                return param;

            }
        };

        AppController.getInstance().addToRequestQueue(stringRequest);


    }

}
