package com.digital.gnsbook.Activity;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;
import com.digital.gnsbook.Config.APIs;
import com.digital.gnsbook.Config.AppController;
import com.digital.gnsbook.Global;
import com.digital.gnsbook.Model.AddressResponse;
import com.digital.gnsbook.Model.CartItem;
import com.digital.gnsbook.Model.OrderHistory.OrderHistryModel;
import com.digital.gnsbook.Model.OrderHistory.Result_Order;
import com.digital.gnsbook.ViewDialog;
import com.google.gson.Gson;
import com.httpgnsbook.gnsbook.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class Order_history extends AppCompatActivity {

    RecyclerView orderHistory;
    List<Result_Order> prodlist = new ArrayList<>();
    ViewDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_history);
        setTitle("Order history");
        progressDialog=new ViewDialog(this);
        orderHistory= findViewById(R.id.orderHistory);
        orderHistory.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        orderHistory.setAdapter(new RecyclerView.Adapter() {
            @NonNull
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                View view= LayoutInflater.from(Order_history.this).inflate(R.layout.order_detail, viewGroup,false);
                Holder holder=new Holder(view);
                return holder;
            }

            @Override
            public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int i) {

                final Holder myHolder= (Holder) viewHolder;
                final Result_Order model = prodlist.get(i);
                String[] images = model.getImages().split(",");

                Glide.with(Order_history.this).load(APIs.Dp+images[0]).into(myHolder.img);
                myHolder.prdname.setText(model.getProductName());
                myHolder.prddesc.setText(model.getProductDesc());
                myHolder.prdprice.setText("₹ "+model.getAmount());
                if (model.getType() == 0) {
                    myHolder.type.setText("HOME");
                } else {
                    myHolder.type.setText("WORK");
                }
                myHolder.add.setText(model.getApartmentName() + " " + model.getLandmark() + " " + model.getCity() + " " + model.getState());

            }

            @Override
            public int getItemCount() {
                return prodlist.size();
            }
            class Holder extends RecyclerView.ViewHolder {

                ImageView img,plus,minus;
                TextView prdname , prddesc, prdwtunit , prdprice,prodqty,type, add;
                public Holder(@NonNull View itemView) {
                    super(itemView);

                    img = itemView.findViewById(R.id.product_img);
                    prdname = itemView.findViewById(R.id.product_name);
                    prddesc = itemView.findViewById(R.id.product_desc);
                    prdwtunit = itemView.findViewById(R.id.product_qnt);
                    prodqty = itemView.findViewById(R.id.qty);
                    prdprice = itemView.findViewById(R.id.price);
                    type = itemView.findViewById(R.id.type);
                    add = itemView.findViewById(R.id.add);


                }
            } }
        );

        getData();

    }

    private void getData() {
        //data variables call
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(StringRequest.Method.POST, APIs.order_history, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //   tprice=0;
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    progressDialog.dismiss();

                    if (jsonObject.getBoolean("status")) {
                        Gson gson = new Gson();
                        OrderHistryModel res = gson.fromJson(response, OrderHistryModel.class);
                        prodlist = res.getResult();
                        orderHistory.getAdapter().notifyDataSetChanged();


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
                Map<String, String> param = new HashMap<String, String>();
                param.put("customer_id", Global.customerid);
                param.put("limit", "50");
                param.put("offset", "0");
                return param;
            }
        };
        AppController.getInstance().addToRequestQueue(stringRequest);
    }
}
