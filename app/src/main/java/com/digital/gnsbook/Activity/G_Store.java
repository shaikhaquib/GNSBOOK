package com.digital.gnsbook.Activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.digital.gnsbook.Adapter.Shop_adapter;
import com.digital.gnsbook.Adapter.WallAdapt;
import com.digital.gnsbook.Config.APIs;
import com.digital.gnsbook.Config.AppController;
import com.digital.gnsbook.Fragement.Product;
import com.digital.gnsbook.Global;
import com.digital.gnsbook.Model.Activity_Gstore.ProductModel;
import com.digital.gnsbook.Model.Activity_Gstore.Result;
import com.digital.gnsbook.Model.TimeLine_Model.TimeLineItem;
import com.digital.gnsbook.Model.TimeLine_Model.TimeLineResponse;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;
import com.httpgnsbook.gnsbook.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class G_Store extends AppCompatActivity {

    RecyclerView rvShop;
    List<Result> postModel = new ArrayList<>();
    private int offset = 0;
    SwipeRefreshLayout Refresh;
    boolean isLoading = false;
    String string = "1000";
    RadioGroup Rgroup;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.frg_store);

        Rgroup = findViewById(R.id.Rgroup);
        rvShop = findViewById(R.id.rvShop);
        Refresh = findViewById(R.id.Refresh);
        rvShop.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        rvShop.setHasFixedSize(true);
       rvShop.setAdapter(new Shop_adapter(G_Store.this,postModel));


        Refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                offset = 0;
                getPost();
            }
        });

        rvShop.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();

                if (!isLoading) {
                    if (linearLayoutManager != null && linearLayoutManager.findLastCompletelyVisibleItemPosition() == postModel.size() - 1) {
                        //bottom of list!
                        offset = offset + 10;
                        //  porogress.setVisibility(View.VISIBLE);
                        getPost();
                        isLoading = true;
                        postModel.add(null);
                        rvShop.getAdapter().notifyItemInserted(postModel.size() - 1);
                    }
                }
            }
        });


        getPost();
        Rgroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                Refresh.setRefreshing(true);
                if (checkedId == R.id.thousand){
                    string = "1000";
                    getPost();
                }else if (checkedId == R.id.twothousand){
                    string = "2000";
                    getPost();
                } else if (checkedId == R.id.threethousand){
                    string = "3000";
                    getPost();
                } else if (checkedId == R.id.fourthousand){
                    string = "4000";
                    getPost();
                }
            }
        });

    }
    private void getPost() {

        AppController.getInstance().addToRequestQueue(new StringRequest(1, APIs.display_product_by_category, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                /*porogress.setVisibility(View.GONE);*/


                if (isLoading) {
                    postModel.remove(postModel.size() - 1);
                    int scrollPosition = postModel.size();
                    rvShop.getAdapter().notifyItemRemoved(scrollPosition);
                    isLoading = false;
                }

                if (Refresh.isRefreshing()){
                    Refresh.setRefreshing(false);

                    try {
                        JSONObject object = new JSONObject(response);
                        if (object.getBoolean("status")){
                            rvShop.setAdapter(null);
                            postModel.clear();
                            rvShop.setAdapter(new Shop_adapter(G_Store.this,postModel));

                        }else {
                            Toast.makeText(G_Store.this, object.getString("result"), Toast.LENGTH_SHORT).show();

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }



                }

                try {
                    JSONObject object = new JSONObject(response);

                    if (object.getBoolean("status")){



                        JsonReader reader = new JsonReader(new StringReader(response));
                        reader.setLenient(true);
                        ProductModel timeLineResponse = new Gson().fromJson(reader, ProductModel.class);
                        if (timeLineResponse.getResult().size() > 0) {
                            postModel.addAll(timeLineResponse.getResult());
                        }
                        rvShop.getAdapter().notifyDataSetChanged();

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

             /*   postModel.remove(postModel.size() - 1);
                int scrollPosition = postModel.size();*/
              /*  rvWallpost.getAdapter().notifyItemRemoved(scrollPosition);
                isLoading = false;*/
            }
        }) {
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> hashMap = new HashMap();
                hashMap.put("company_id", "1");
                hashMap.put("product_price", string);
                hashMap.put("customer_id", Global.customerid);
                hashMap.put("limit", "10");
                hashMap.put("offset", String.valueOf(offset));
                return hashMap;
            }
        });
    }

}
