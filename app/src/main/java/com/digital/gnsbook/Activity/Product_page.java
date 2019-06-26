package com.digital.gnsbook.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.digital.gnsbook.Adapter.Product_Adapter;
import com.digital.gnsbook.Config.APIs;
import com.digital.gnsbook.Config.AppController;
import com.digital.gnsbook.Config.DbHelper;
import com.digital.gnsbook.Extra.SimpleGestureFilter;
import com.digital.gnsbook.Global;
import com.digital.gnsbook.Model.Activity_Gstore.ProductModel;
import com.digital.gnsbook.Model.Activity_Gstore.Result;
import com.digital.gnsbook.Model.ComponyModel;
import com.digital.gnsbook.ViewDialog;
import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.httpgnsbook.gnsbook.R;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Product_page extends AppCompatActivity {


//    ArrayList<WallPostmodel> postmodels = new ArrayList<>();
    List<Result> postModel = new ArrayList<>();

    ViewDialog dialog;
    RecyclerView wallPost;
    ImageView Logo;
    int count = 10;
    private String TAG = "WallPostFragment";
    private int offset = 0;
    CardView porogress;
    String Cid;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_product);
        wallPost = findViewById(R.id.rv_product);
        porogress = findViewById(R.id.compprogrssview);
       /* NewPost = findViewById(R.id.adminnewpost);
        if (Global.Company_Admin_Id != Integer.parseInt(Global.customerid)) {
            NewPost.setVisibility(View.GONE);
        }*/



//        Logo = findViewById(R.id.componyLogo);
//        Picasso.get().load(APIs.Dp + Global.Company_Logo).into(Logo);

        wallPost.setLayoutManager(new GridLayoutManager(Product_page.this, 2));
        wallPost.setItemAnimator(null);
        dialog = new ViewDialog(Product_page.this);


        if (getIntent().getStringExtra("cat").equals("0"))
            getTimelineProduct();
        else
            getTimelineProductByCat();

        wallPost.setAdapter(new Product_Adapter(postModel, Product_page.this));



        wallPost.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();

                    if (linearLayoutManager != null && linearLayoutManager.findLastCompletelyVisibleItemPosition() == postModel.size() - 1) {
                        //bottom of list!
                        offset = offset + 10;
                        getTimelineProduct();
                        porogress.setVisibility(View.VISIBLE);
                    }

            }
        });



        /*if (nestedScrollView != null) {
            nestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
                @Override
                public void onScrollChange(NestedScrollView nestedScrollView, int i, int i2, int i3, int i4) {
                    if (i2 == nestedScrollView.getChildAt(0).getMeasuredHeight() - nestedScrollView.getMeasuredHeight() && count > 0) {
                        offset = offset + 10;
                        getTimelineProduct();
                        porogress.setVisibility(View.VISIBLE);
                    }
                }

            });
        }
*/
        //  bottomSheet();
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();

        menuInflater.inflate(R.menu.product_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        switch (item.getItemId()) {
            case R.id.prdSearch:
                startActivity(new Intent(getApplicationContext(), ProductSearch.class).putExtra(DbHelper.COLUMN_ID, getIntent().getStringExtra("id")));
                break;

            case R.id.prdCategory:
                startActivity(new Intent(getApplicationContext(), ProductCategory.class).putExtra(DbHelper.COLUMN_ID, getIntent().getStringExtra("id")));
                finish();
                break;

        }
        return true;

    }

    private void showDialoge() {

        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(Product_page.this);
        View inflate = getLayoutInflater().inflate(R.layout.transactionmode, null);
        builder.setView(inflate);
        TextView headTitle = (TextView) inflate.findViewById(R.id.headTitle);
        TextView textView = (TextView) inflate.findViewById(R.id.new_FundTrans);
        TextView textView2 = (TextView) inflate.findViewById(R.id.old_FundTrans);

        headTitle.setText("SELECT POSTING TYPE");
        textView.setText("Normal Posting");
        textView2.setText("Product Posting");
        builder.create().show();
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Product_page.this, New_Post.class));
            }
        });
        textView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Product_page.this, UploadProduct.class));
            }
        });
    }


    private void getTimelineProduct() {
        AppController.getInstance().addToRequestQueue(new StringRequest(StringRequest.Method.POST, APIs.company_product, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                porogress.setVisibility(View.GONE);
                try {
                    JSONObject object = new JSONObject(response);

                    if (object.getBoolean("status")){



                        JsonReader reader = new JsonReader(new StringReader(response));
                        reader.setLenient(true);
                        ProductModel timeLineResponse = new Gson().fromJson(reader, ProductModel.class);
                        if (timeLineResponse.getResult().size() > 0) {
                            postModel.addAll(timeLineResponse.getResult());
                        }
                        wallPost.getAdapter().notifyDataSetChanged();

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                porogress.setVisibility(View.GONE);
            }

        }) {
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> hashMap = new HashMap();
                hashMap.put("company_id", getIntent().getStringExtra("id"));
                hashMap.put("customer_id", Global.customerid);
                hashMap.put("limit", "10");
                hashMap.put("type", "2");
                hashMap.put("offset", String.valueOf(offset));
                return hashMap;
            }
        });
    }


    private void getTimelineProductByCat() {
        AppController.getInstance().addToRequestQueue(new StringRequest(StringRequest.Method.POST, APIs.search_product_by_category, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                porogress.setVisibility(View.GONE);
                try {
                    JSONObject object = new JSONObject(response);

                    if (object.getBoolean("status")){



                        JsonReader reader = new JsonReader(new StringReader(response));
                        reader.setLenient(true);
                        ProductModel timeLineResponse = new Gson().fromJson(reader, ProductModel.class);
                        if (timeLineResponse.getResult().size() > 0) {
                            postModel.addAll(timeLineResponse.getResult());
                        }
                        wallPost.getAdapter().notifyDataSetChanged();

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                porogress.setVisibility(View.GONE);
            }

        }) {
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> hashMap = new HashMap();
                hashMap.put("company_id", getIntent().getStringExtra("id"));
                hashMap.put("product_cat", getIntent().getStringExtra("cat"));
                hashMap.put("customer_id", Global.customerid);
                hashMap.put("limit", "10");
                hashMap.put("type", "2");
                hashMap.put("offset", String.valueOf(offset));
                return hashMap;
            }
        });

    }
}


