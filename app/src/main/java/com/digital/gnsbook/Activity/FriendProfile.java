package com.digital.gnsbook.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.digital.gnsbook.Config.APIs;
import com.digital.gnsbook.Config.AppController;
import com.digital.gnsbook.Fragment.ProfileFragment;
import com.digital.gnsbook.Global;
import com.digital.gnsbook.ViewDialog;
import com.google.gson.JsonObject;
import com.httpgnsbook.gnsbook.R;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class FriendProfile extends AppCompatActivity {



    ImageView prBanner , prDP ;
    TextView prName , prcity;
    ViewDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_profile);
        prBanner = findViewById(R.id.prBanner);
        prDP = findViewById(R.id.prDP);
        prName = findViewById(R.id.prName);
        prcity = findViewById(R.id.prcity);
        dialog=new ViewDialog(FriendProfile.this);

        limitTransaction();
    }


    private void limitTransaction() {

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

}
