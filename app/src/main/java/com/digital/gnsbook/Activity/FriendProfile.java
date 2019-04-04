package com.digital.gnsbook.Activity;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.digital.gnsbook.Config.APIs;
import com.digital.gnsbook.Config.AppController;
import com.digital.gnsbook.Global;
import com.digital.gnsbook.ViewDialog;
import com.httpgnsbook.gnsbook.R;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class FriendProfile extends AppCompatActivity {



    ImageView prBanner , prDP ;
    TextView prName , prcity;
    LinearLayout layout;
    CardView addFreind , removefriend , acceptRequest ,friend ,fabReject;
    ViewDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_profile);
        prBanner = findViewById(R.id.prBanner);
        prDP = findViewById(R.id.prDP);
        prName = findViewById(R.id.prName);
        friend = findViewById(R.id.fabfriend);
        fabReject = findViewById(R.id.fabReject);
        layout = findViewById(R.id.accesptrequestUi);
        prcity = findViewById(R.id.prcity);
        addFreind = findViewById(R.id.fabaddfreind);
        acceptRequest = findViewById(R.id.fabAccept);
        removefriend = findViewById(R.id.fabremoove);
        dialog=new ViewDialog(FriendProfile.this);

        addFreind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addFreind();

                addFreind.setVisibility(View.GONE);
                removefriend.setVisibility(View.VISIBLE);
            }
        });
        removefriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removefriend();
                removefriend.setVisibility(View.GONE);
                addFreind.setVisibility(View.VISIBLE);
            }
        });
        fabReject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removefriend();
                removefriend.setVisibility(View.GONE);
                addFreind.setVisibility(View.VISIBLE);
                layout.setVisibility(View.GONE);
            }
        });
        acceptRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                acceptRequest();
                acceptRequest.setVisibility(View.GONE);
                layout.setVisibility(View.GONE);
                removefriend.setVisibility(View.VISIBLE);
            }
        });

        getDetail();
    }

    private void removefriend() {
        dialog.show();
        AppController.getInstance().addToRequestQueue(new StringRequest(1, APIs.declinefriend, new Response.Listener<String>() {
            public void onResponse(String str) {
                dialog.dismiss();

                try {
                    JSONObject jsonObject = new JSONObject(str);

                    if (jsonObject.getBoolean("status")){
                        Toast.makeText(FriendProfile.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(FriendProfile.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            public void onErrorResponse(VolleyError volleyError) {
                dialog.dismiss();
            }
        }) {
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> hashMap = new HashMap();
                hashMap.put("customerid_to", getIntent().getStringExtra("id"));
                hashMap.put("customerid_from", Global.customerid);
                return hashMap;
            }
        });

    }

    private void addFreind() {
        dialog.show();
        AppController.getInstance().addToRequestQueue(new StringRequest(1, APIs.addfriend, new Response.Listener<String>() {
            public void onResponse(String str) {
                dialog.dismiss();

                try {
                    JSONObject jsonObject = new JSONObject(str);

                    if (jsonObject.getBoolean("status")){
                        Toast.makeText(FriendProfile.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(FriendProfile.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            public void onErrorResponse(VolleyError volleyError) {
                dialog.dismiss();
            }
        }) {
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> hashMap = new HashMap();
                hashMap.put("customerid_to", getIntent().getStringExtra("id"));
                hashMap.put("customerid_from", Global.customerid);
                return hashMap;
            }
        });

    }


    private void getDetail() {

        dialog.show();

        AppController.getInstance().addToRequestQueue(new StringRequest(1, APIs.display_specific_profile, new Response.Listener<String>() {
            public void onResponse(String str) {
                dialog.dismiss();

                try {
                    JSONObject jsonObject = new JSONObject(str);

                    if (jsonObject.getBoolean("status")){

                        JSONObject object = jsonObject.getJSONArray("result").getJSONObject(0);
                        Picasso.get().load(APIs.Banner+object.getString("b_pic")).error((int) R.drawable.landing_bg).into(prBanner);
                        Picasso.get().load(APIs.Dp+object.getString("d_pic")).error((int) R.drawable.landing_bg).into(prDP);
                        prName.setText(object.getString("name")+" "+object.getString("last_name"));
                        prcity.setText(object.getString("email"));

                        int reqstatus = jsonObject.getInt("reqstatus"),
                            frndstatus =jsonObject.getInt("frndstatus");

                        if (reqstatus == 1){
                            if (frndstatus == 0){
                                removefriend.setVisibility(View.VISIBLE);
                            }else if (frndstatus == 1){
                                removefriend.setVisibility(View.VISIBLE);
                            }else {
                                removefriend.setVisibility(View.GONE);
                            }
                        }else if (reqstatus == 2){
                            if (frndstatus == 0){
                                layout.setVisibility(View.VISIBLE);
                                acceptRequest.setVisibility(View.VISIBLE);
                            }else if (frndstatus == 1){
                                removefriend.setVisibility(View.VISIBLE);
                            }else if (frndstatus == 5){
                                addFreind.setVisibility(View.VISIBLE);
                            }else {
                                layout.setVisibility(View.GONE);
                                acceptRequest.setVisibility(View.GONE);
                            }
                        }else if (reqstatus == 3){
                            addFreind.setVisibility(View.VISIBLE);
                        }else {
                            friend.setVisibility(View.VISIBLE);
                        }


                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            public void onErrorResponse(VolleyError volleyError) {
                dialog.dismiss();
            }
        }) {
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> hashMap = new HashMap();
                hashMap.put("customer_id", getIntent().getStringExtra("id"));
                hashMap.put("customerid_from", Global.customerid);
                return hashMap;
            }
        });
    }

    private void acceptRequest() {
        dialog.show();
        AppController.getInstance().addToRequestQueue(new StringRequest(1, APIs.acceptRequest, new Response.Listener<String>() {
            public void onResponse(String str) {
                dialog.dismiss();

                try {
                    JSONObject jsonObject = new JSONObject(str);

                    if (jsonObject.getBoolean("status")){
                        Toast.makeText(getApplicationContext(), jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(getApplicationContext(), jsonObject.getString("result"), Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            public void onErrorResponse(VolleyError volleyError) {
                dialog.dismiss();
            }
        }) {
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> hashMap = new HashMap();
                hashMap.put("customerid_to", Global.customerid);
                hashMap.put("customerid_from",getIntent().getStringExtra("id"));
                return hashMap;
            }
        });

    }


}
