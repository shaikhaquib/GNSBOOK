package com.digital.gnsbook.Activity;

import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;
import com.digital.gnsbook.Config.APIs;
import com.digital.gnsbook.Config.AppController;
import com.digital.gnsbook.Global;
import com.digital.gnsbook.Model.CartItem;
import com.digital.gnsbook.Model.CartResponse;
import com.digital.gnsbook.ViewDialog;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.httpgnsbook.gnsbook.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class Cart extends AppCompatActivity {
    RecyclerView recyclerView;
    List<CartItem> prodlist = new ArrayList<>();

    int offset = 0;
    ViewDialog progressDialog;
    //private EndlessRecyclerOnScrollListener mScrollListener = null;
    private SwipeRefreshLayout mSwipeRefreshLayout = null;


    private Timer timer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        progressDialog=new ViewDialog(Cart.this);
        getSupportActionBar().hide();
        recyclerView = findViewById(R.id.rv_product);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);


        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // do something
                getData();
                // after refresh is done, remember to call the following code
                if (mSwipeRefreshLayout != null && mSwipeRefreshLayout.isRefreshing()) {
                    mSwipeRefreshLayout.setRefreshing(false);  // This hides the spinner
                }
            }
        });
        recyclerView.setAdapter(new RecyclerView.Adapter() {
            @NonNull
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                View view= LayoutInflater.from(Cart.this).inflate(R.layout.product_list, viewGroup,false);
                Holder holder=new Holder(view);
                return holder;
            }

            @Override
            public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int i) {
                final Holder myHolder= (Holder) viewHolder;
                final CartItem model = prodlist.get(i);
                String[] images = model.getImages().split(",");

                Glide.with(Cart.this).load(APIs.Dp+images[0]).into(myHolder.img);
                myHolder.prdname.setText(model.getProductName());
                myHolder.prddesc.setText(model.getProductDesc());
                myHolder.prdprice.setText("₹ "+model.getAmount());
               // myHolder.prdwtunit.setText(model.getProductWeight()+" "+model.getUnits());
                myHolder.prodqty.setTag(model);
                myHolder.prodqty.setText(String.valueOf(model.getQuantity()));
               // final int calwt=Integer.parseInt(model.getProductWeight());
                model.prdamount = model.getAmount();
                model.basicamt = model.getProductPrice();
                model.prdminteger = model.getQuantity();

                myHolder.minus.setTag(model);
                myHolder.removeCart.setTag(model);
                myHolder.plus.setTag(model);

                myHolder.removeCart.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        prodlist.remove(i);
                        notifyItemRemoved(i);
                        notifyItemRangeChanged(i,prodlist.size());
                        remove(model.getProductId(),model.getId());
                    }
                });

                myHolder.plus.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(model.prdminteger<99) {
                            model.prdminteger = model.prdminteger + 1;
                            myHolder.prodqty.setText(String.valueOf(model.prdminteger));
                            int amt = model.basicamt;
                            int cal2 = amt * model.prdminteger;
                            //  int cal3 = wt * minteger;
                            final String s = String.valueOf(cal2);
                            // calculatedprice.setText(s);
                            model.prdamount = cal2;
                            myHolder.prdprice.setText("₹ " + s);
                            //  wtut.setText(cal3 +" "+ unit);
                            //pricesenttobuynow = s.trim();


                            timer = new Timer();
                            timer.schedule(new TimerTask() {
                                @Override
                                public void run() {
                                    // do your actual work here
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {

                                            UpdateCart(s,String.valueOf(model.prdminteger),String.valueOf(model.getProductId()));

                                        }
                                    });

                                    try {
                                        Thread.sleep(2000);
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }


                                    // hide keyboard as well?
                                    // InputMethodManager in = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                                    // in.hideSoftInputFromWindow(searchText.getApplicationWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                                }
                            }, 600); // 600ms delay before the timer executes the "run" method from TimerTask
                        }
                    }
                });
                myHolder.minus.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(model.prdminteger>1) {
                            model.prdminteger = model.prdminteger - 1;
                            myHolder.prodqty.setText(String.valueOf(model.prdminteger));
                            int amt = model.basicamt;
                            int cal2 = amt * model.prdminteger;
                            //  int cal3 = wt * minteger;
                            String s = String.valueOf(cal2);
                            // calculatedprice.setText(s);
                            model.prdamount = cal2;
                            myHolder.prdprice.setText("₹ "+s);
                            //  wtut.setText(cal3 +" "+ unit);
                            //pricesenttobuynow = s.trim();
                        }
                        else {
                            myHolder.prodqty.setText("1");
                        }
                    }
                });

            }

            @Override
            public int getItemCount() {
                return prodlist.size();
            }
            class Holder extends RecyclerView.ViewHolder {
                ImageView img,plus,minus;
                TextView prdname , prddesc, prdwtunit , prdprice,prodqty;
                CardView removeCart;
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
                    removeCart = itemView.findViewById(R.id.removeCart);


                }
            } }
        );
        getData();
    }

    private void remove(final int productId, final int id) {
        StringRequest stringRequest = new StringRequest(StringRequest.Method.POST, APIs.RemoveCart, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(Cart.this, response, Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error == null || error.networkResponse == null) {
                    return;
                }

                String body;
                //get status code here
                final String statusCode = String.valueOf(error.networkResponse.statusCode);
                //get response body and parse with appropriate encoding
                try {
                    body = new String(error.networkResponse.data,"UTF-8");
                    Log.d("UpdateCart",body);
                } catch (UnsupportedEncodingException e) {
                    // exception
                }
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map <String,String> param = new HashMap<String,String>();
                param.put("customer_id", Global.customerid);
                param.put("product_id", String.valueOf(productId));
                param.put("id", String.valueOf(id));
                return param;
            }
        };
        AppController.getInstance().addToRequestQueue(stringRequest);
    }

    private void UpdateCart(final String amount,final String quantity,final String id) {
        StringRequest stringRequest = new StringRequest(StringRequest.Method.POST, APIs.UpdateCart, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("UpdateCart_responce",response);
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
                    Log.d("UpdateCart",body);
                } catch (UnsupportedEncodingException e) {
                    // exception
                }
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map <String,String> param = new HashMap<String,String>();
                param.put("customer_id", Global.customerid);
                param.put("quantity",quantity);
                param.put("amount",amount);
                param.put("product_id",id);
                return param;
            }
        };
        AppController.getInstance().addToRequestQueue(stringRequest);
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
                        CartResponse res = gson.fromJson(response, CartResponse.class);
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
}
