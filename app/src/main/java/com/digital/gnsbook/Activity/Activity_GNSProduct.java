package com.digital.gnsbook.Activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;
import com.digital.gnsbook.Config.APIs;
import com.digital.gnsbook.Config.AppController;
import com.digital.gnsbook.Config.MySingleton;
import com.digital.gnsbook.Config.VolleyMultipartRequest;
import com.digital.gnsbook.Global;
import com.digital.gnsbook.Model.Activity_Gstore.GNS_product;
import com.digital.gnsbook.ViewDialog;
import com.google.gson.Gson;
import com.httpgnsbook.gnsbook.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Activity_GNSProduct extends AppCompatActivity {
    RecyclerView recyclerView;
    List<GNS_product.Result> prodlist = new ArrayList<>();
    ViewDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__gnsproduct);
        recyclerView = findViewById(R.id.rv_product);
        progressDialog=new ViewDialog(this);
        getSupportActionBar().hide();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        recyclerView.setAdapter(new RecyclerView.Adapter() {
            @NonNull
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                View view= LayoutInflater.from(Activity_GNSProduct.this).inflate(R.layout.product_list, viewGroup,false);
                Holder holder=new Holder(view);
                return holder;
            }

            @Override
            public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int i) {
                final Holder myHolder= (Holder) viewHolder;
                final GNS_product.Result model = prodlist.get(i);
                String[] images = model.getImages().split(",");

                Glide.with(Activity_GNSProduct.this).load(APIs.Dp+images[0]).into(myHolder.img);
                myHolder.prdname.setText(model.getProductName());
                myHolder.prddesc.setText(model.getProductDesc());
                myHolder.prdprice.setText("₹ "+model.getProductPrice());
                // myHolder.prdwtunit.setText(model.getProductWeight()+" "+model.getUnits());
                myHolder.prodqty.setTag(model);
                // final int calwt=Integer.parseInt(model.getProductWeight());
                myHolder.Select.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showDialoge(model);
                    }
                });



            }

            @Override
            public int getItemCount() {
                return prodlist.size();
            }
            class Holder extends RecyclerView.ViewHolder {
                ImageView img,plus,minus;
                TextView prdname , prddesc, prdwtunit , prdprice,prodqty,campName;
                CardView Select;
                public Holder(@NonNull View itemView) {
                    super(itemView);


                    img = itemView.findViewById(R.id.product_img);
                    prdname = itemView.findViewById(R.id.product_name);
                    prddesc = itemView.findViewById(R.id.product_desc);
                    prdwtunit = itemView.findViewById(R.id.product_qnt);
                    prodqty = itemView.findViewById(R.id.qty);
                    prdprice = itemView.findViewById(R.id.price);
                    plus = itemView.findViewById(R.id.plus);
                    minus = itemView.findViewById(R.id.minus);
                    campName = itemView.findViewById(R.id.campName);
                    campName.setText("SELECT");
                    minus.setVisibility(View.GONE);
                    plus.setVisibility(View.GONE);
                    prodqty.setVisibility(View.GONE);
                    prdwtunit.setVisibility(View.GONE);
                    Select = itemView.findViewById(R.id.removeCart);





                }
            } });
        getData();

    }

    private void getData() {
        //data variables call
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(StringRequest.Method.POST, APIs.CartProduct, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //   tprice=0;
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    progressDialog.dismiss();

                    if(jsonObject.getBoolean("status")) {
                        Gson gson = new Gson();
                        GNS_product res = gson.fromJson(response, GNS_product.class);
                        prodlist = res.getResult();
                        recyclerView.getAdapter().notifyDataSetChanged();

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
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map <String,String> param = new HashMap<String,String>();
                param.put("customer_id", Global.customerid);
                param.put("limit","50");
                param.put("offset","0");
                return param;
            }
        };
        AppController.getInstance().addToRequestQueue(stringRequest);
    }

    private void showDialoge(final GNS_product.Result model) {
        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(Activity_GNSProduct.this);
        View itemView = getLayoutInflater().inflate(R.layout.layout_set_prize, null);

        builder.setView(itemView);

        final ImageView image;
           final TextView prdname , prddesc , prdprice;
           final EditText newPrice;
           final CardView add_product;

            image     = itemView.findViewById(R.id.product_img);
            prdname  = itemView.findViewById(R.id.product_name);
            prddesc  = itemView.findViewById(R.id.product_desc);
            prdprice = itemView.findViewById(R.id.price);
            newPrice = itemView.findViewById(R.id.new_prize);
            add_product = itemView.findViewById(R.id.add_product);


        String[] images = model.getImages().split(",");
        Glide.with(Activity_GNSProduct.this).load(APIs.Dp+images[0]).into(image);
        prdname.setText(model.getProductName());
        prddesc.setText(model.getProductDesc());
        prdprice.setText("₹ "+model.getProductPrice());

        final AlertDialog ad = builder.show();

        add_product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String new_Prize = newPrice.getText().toString();

                if (new_Prize.isEmpty()){
                    newPrice.setError("Field reqired");
                }else if (Integer.parseInt(new_Prize) < model.getProductPrice()){
                    newPrice.setError("Amount must be greater or equal to original amount");

                }else {
                    ad.dismiss();
                    uploadFile(model.getId(),model.getProductName(),model.getProductCat(),new_Prize,model.getProductDesc(),model.getImages());
                }
            }
        });

    }

    private void uploadFile(final Integer id, final String productName, final String productCat, final String productPrice, final String productDesc, final String images) {
        progressDialog.show();
        VolleyMultipartRequest multipartRequest = new VolleyMultipartRequest(Request.Method.POST, APIs.add_stock_product, new Response.Listener<NetworkResponse>() {
            @Override
            public void onResponse(NetworkResponse response) {
                progressDialog.dismiss();
                try {
                    String json = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
                    Log.d("Responce", json);
                    JSONObject jSONObject = new JSONObject(json);
                    if (jSONObject.getBoolean("status")) {
                        Toast.makeText(Activity_GNSProduct.this, "You have Successfully post.", Toast.LENGTH_SHORT).show();
                    } else {
                        Global.failedDilogue(Activity_GNSProduct.this, jSONObject.getString("result"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (UnsupportedEncodingException e) {
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
                    body = new String(error.networkResponse.data, "UTF-8");
                    Log.d("Multi", body);
                } catch (UnsupportedEncodingException e) {
                    // exception
                }

                //do stuff with the body...
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> hashMap = new HashMap<>();
                hashMap.put("stock_id", String.valueOf(id));
                hashMap.put("company_id", Global.Company_Id);
                hashMap.put("product_name", productName);
                hashMap.put("product_price", productPrice);
                hashMap.put("product_desc", productDesc);
                hashMap.put("product_cat", productCat);
                hashMap.put("images", images);
                return hashMap;
            }

        };


        AppController.getInstance().addToRequestQueue(multipartRequest);
    }


}
