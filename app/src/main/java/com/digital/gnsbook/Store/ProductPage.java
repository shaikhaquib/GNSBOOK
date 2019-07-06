package com.digital.gnsbook.Store;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.digital.gnsbook.Activity.Cart;
import com.digital.gnsbook.Activity.ProductDetail;
import com.digital.gnsbook.Config.APIs;
import com.digital.gnsbook.Config.AppController;
import com.digital.gnsbook.Global;
import com.digital.gnsbook.ViewDialog;
import com.httpgnsbook.gnsbook.R;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class ProductPage extends AppCompatActivity implements OnItemClick{

    String product_name ;
    String product_cat  ;
    String product_price;
    String product_desc ;
    String product_link ;
    String[] images ;
    String id;
    String[] imagearray ;
    CatalogueAdapter adapter;

    RecyclerView Catalogue;
    ImageView preview;
    TextView prdDesc,prdAmount;

    ViewDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_productpage);
        progressDialog = new ViewDialog(this);

        Catalogue= findViewById(R.id.cateloge);
        prdDesc= findViewById(R.id.prdDesc);
        prdAmount= findViewById(R.id.prdAmount);
        preview= findViewById(R.id.ImageProduct);
        Catalogue.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        getDataIntentData();
       // adapter = new CatalogueAdapter(this, imagearray);
        Catalogue.setAdapter(new CatalogueAdapter(ProductPage.this, imagearray,this));

    }

    private void getDataIntentData() {

        Bundle b=this.getIntent().getExtras();
        imagearray=b.getStringArray("images");


        product_name  =getIntent().getStringExtra("product_name");
        product_cat   =getIntent().getStringExtra("product_cat");
        product_price =getIntent().getStringExtra("product_price");
        product_desc  =getIntent().getStringExtra("product_desc");
        product_link  =getIntent().getStringExtra("product_link");
        id            =getIntent().getStringExtra("id");

        setTitle(product_name);
        prdAmount.setText("₹ "+product_price);
        prdDesc.setText(product_desc);

    }

    @Override
    public void onClick(String value) {
        Picasso.get().load(value).into(preview);

    }




    public void addtocart(View view) {
        AddtoCart();
    }

    private void AddtoCart() {
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(StringRequest.Method.POST, APIs.AddtoCart, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getBoolean("status")){
                        // Global.successDilogue(ProductDetail.this,jsonObject.getString("result"));
                        startActivity(new Intent(getApplicationContext(), Cart.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                        finish();
                    }else {
                        Global.failedDilogue(ProductPage.this,jsonObject.getString("result"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                if (error == null || error.networkResponse == null) {
                    return;
                }

                String body;
                //get status code here
                final String statusCode = String.valueOf(error.networkResponse.statusCode);
                //get response body and parse with appropriate encoding
                try {
                    body = new String(error.networkResponse.data,"UTF-8");
                    Log.d("Multi",body);
                } catch (UnsupportedEncodingException e) {
                    // exception
                }
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map <String,String> param = new HashMap<String,String>();
                param.put("customer_id", Global.customerid);
                param.put("quantity","1");
                param.put("amount",getIntent().getStringExtra("product_price"));
                param.put("product_id",id);
                return param;
            }
        };
        AppController.getInstance().addToRequestQueue(stringRequest);
    }

}
