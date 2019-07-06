package com.digital.gnsbook.Fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;
import com.digital.gnsbook.Activity.Orderd_Detail;
import com.digital.gnsbook.Config.APIs;
import com.digital.gnsbook.Config.AppController;
import com.digital.gnsbook.Global;
import com.digital.gnsbook.Model.Product_model.Company_order;
import com.digital.gnsbook.ViewDialog;
import com.google.gson.Gson;
import com.httpgnsbook.gnsbook.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Frg_CompanyOrder extends Fragment implements View.OnClickListener {

    RecyclerView orderHistory;
    List<Company_order.Result> prodlist = new ArrayList<>();
    ViewDialog progressDialog;
    Button all, ordered, shipped, incart, cancelled, delivered;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup viewGroup, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.comapony_order, viewGroup, false);

        orderHistory = view.findViewById(R.id.orderHistory);
        orderHistory.setLayoutManager(new LinearLayoutManager(getActivity()));

        all = view.findViewById(R.id.all);
        all.setOnClickListener(this);
        ordered = view.findViewById(R.id.ordered);
        ordered.setOnClickListener(this);
        delivered = view.findViewById(R.id.delivered);
        delivered.setOnClickListener(this);
        shipped = view.findViewById(R.id.shipped);
        shipped.setOnClickListener(this);
        incart = view.findViewById(R.id.incart);
        incart.setOnClickListener(this);
        cancelled = view.findViewById(R.id.cancelled);
        cancelled.setOnClickListener(this);

        orderHistory.setAdapter(new RecyclerView.Adapter() {
                                    @NonNull
                                    @Override
                                    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                                        View view = LayoutInflater.from(getActivity()).inflate(R.layout.order_detail, viewGroup, false);
                                        Holder holder = new Holder(view);
                                        return holder;
                                    }

                                    @Override
                                    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int i) {

                                        final Holder myHolder = (Holder) viewHolder;
                                        final Company_order.Result model = prodlist.get(i);
                                        String[] images = model.getImages().split(",");

                                        Glide.with(getActivity()).load(APIs.Dp + images[0]).into(myHolder.img);
                                        myHolder.prdname.setText(model.getProductName());
                                        myHolder.prddesc.setText(model.getProductDesc());
                                        myHolder.prdprice.setText("₹ " + model.getAmount());

                                        if (model.getOrderStatus() == 1) {
                                            myHolder.orderstatus.setText("In cart");
                                            myHolder.orderstatus.setTextColor(Color.parseColor("#60a9a6"));

                                        } else if (model.getOrderStatus() == 3) {
                                            myHolder.orderstatus.setText("Ordered");
                                            myHolder.orderstatus.setTextColor(Color.parseColor("#60a9a6"));

                                        } else if (model.getOrderStatus() == 4) {
                                            myHolder.orderstatus.setText("Shipped");
                                            myHolder.orderstatus.setTextColor(Color.parseColor("#ff8300"));

                                        } else if (model.getOrderStatus() == 5) {
                                            myHolder.orderstatus.setText("Delivered");
                                            myHolder.orderstatus.setTextColor(Color.parseColor("#58b368"));

                                        } else if (model.getOrderStatus() == -10) {
                                            myHolder.orderstatus.setText("Cancelled");
                                            myHolder.orderstatus.setTextColor(Color.parseColor("#58b368"));

                                        }


                                        myHolder.itemView.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                Intent intent = new Intent(getActivity(), Orderd_Detail.class);
                                                Bundle b = new Bundle();
                                                b.putSerializable("user", model);
                                                intent.putExtras(b); //pass bundle to your intent
                                                startActivity(intent);                                            }
                                        });

                                    }

                                    @Override
                                    public int getItemCount() {
                                        return prodlist.size();
                                    }

                                    class Holder extends RecyclerView.ViewHolder {

                                        ImageView img, plus, minus;
                                        TextView prdname, prddesc, prdwtunit, prdprice, prodqty, orderstatus;

                                        public Holder(@NonNull View itemView) {
                                            super(itemView);

                                            img = itemView.findViewById(R.id.product_img);
                                            prdname = itemView.findViewById(R.id.product_name);
                                            prddesc = itemView.findViewById(R.id.product_desc);
                                            prdwtunit = itemView.findViewById(R.id.product_qnt);
                                            prodqty = itemView.findViewById(R.id.qty);
                                            prdprice = itemView.findViewById(R.id.price);
                                            orderstatus = itemView.findViewById(R.id.orderstatus);


                                        }
                                    }
                                }
        );

        getData("0");

        return view;
    }

    private void getData(final String s) {
        //data variables call


        prodlist.clear();
        orderHistory.getRecycledViewPool().clear();
        orderHistory.getAdapter().notifyDataSetChanged();

        StringRequest stringRequest = new StringRequest(StringRequest.Method.POST, APIs.company_order, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //   tprice=0;
                try {
                    JSONObject jsonObject = new JSONObject(response);

                    if (jsonObject.getBoolean("status")) {
                        Gson gson = new Gson();
                        Company_order res = gson.fromJson(response, Company_order.class);
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
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param = new HashMap<String, String>();
                param.put("company_id", Global.Company_Id);
                param.put("order_status", s);
                param.put("limit", "50");
                param.put("offset", "0");
                return param;
            }
        };
        AppController.getInstance().addToRequestQueue(stringRequest);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.all:
                getData("0");
                break;
            case R.id.incart:
                getData("1");
                break;
            case R.id.shipped:
                getData("4");
                break;
            case R.id.ordered:
                getData("3");
                break;
            case R.id.delivered:
                getData("5");
                break;
            case R.id.cancelled:
                getData("-10");
                break;
        }

    }
}
