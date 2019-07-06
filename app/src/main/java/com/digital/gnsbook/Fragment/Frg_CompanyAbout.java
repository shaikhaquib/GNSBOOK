package com.digital.gnsbook.Fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.digital.gnsbook.Config.APIs;
import com.digital.gnsbook.Config.AppController;
import com.digital.gnsbook.Global;
import com.digital.gnsbook.Model.ComponyModel;
import com.digital.gnsbook.ViewDialog;
import com.google.gson.Gson;
import com.httpgnsbook.gnsbook.R;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

public class Frg_CompanyAbout extends Fragment {


    TextView intro,location,website,phone,email;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frg_about, container, false);

        intro    = view.findViewById(R.id.intro);
        location = view.findViewById(R.id.location);
        website  = view.findViewById(R.id.website);
        phone    = view.findViewById(R.id.conatctPhone);
        email    = view.findViewById(R.id.conatctEmail);
        getComanydata();


        return view;
    }

    private void getComanydata() {

        StringRequest request = new StringRequest(Request.Method.POST, APIs.company_data_by_id, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson = new Gson();
                ComponyModel dashResp = gson.fromJson(response, ComponyModel.class);

                intro.setText(dashResp.getResult().get(0).getDescription());
                location.setText(dashResp.getResult().get(0).getCity());
                website.setText(dashResp.getResult().get(0).getWeb());
                phone.setText(dashResp.getResult().get(0).getMobile());
                email.setText(dashResp.getResult().get(0).getEmail());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        }) {

            @Override
            public String getBodyContentType() {
                return "application/x-www-form-urlencoded; charset=UTF-8";
            }


            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<String, String>();
                params.put("data",Global.Company_Id);

                return params;
            }

        };
        AppController.getInstance().addToRequestQueue(request);
    }

}

