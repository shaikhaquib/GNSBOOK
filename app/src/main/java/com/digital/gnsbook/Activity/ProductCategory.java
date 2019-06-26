package com.digital.gnsbook.Activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.digital.gnsbook.Config.APIs;
import com.digital.gnsbook.Config.AppController;
import com.digital.gnsbook.Config.DbHelper;
import com.digital.gnsbook.Extra.DividerDecorator;
import com.digital.gnsbook.Global;
import com.digital.gnsbook.Model.Product_model.Category_model;
import com.digital.gnsbook.Model.ResponseRequest;
import com.google.gson.Gson;
import com.httpgnsbook.gnsbook.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductCategory extends AppCompatActivity {

    RecyclerView rvCategory;
    List<Category_model.Result> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_category);
        setTitle("Category");

        rvCategory=findViewById(R.id.rvCategory);
        rvCategory.setLayoutManager(new LinearLayoutManager(this));
        rvCategory.setHasFixedSize(true);
        rvCategory.addItemDecoration(new DividerDecorator(this));
        rvCategory.setAdapter(new RecyclerView.Adapter() {
            @NonNull
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                View view = LayoutInflater.from(ProductCategory.this).inflate(R.layout.simple_list,viewGroup,false);
                return new Holder(view);
            }

            @Override
            public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
                    Category_model.Result item= list.get(i);
                    Holder holder= (Holder)viewHolder;

                    holder.bindItem(item);
            }

            @Override
            public int getItemCount() {
                return list.size();
            }

            class Holder extends RecyclerView.ViewHolder {
                LinearLayout layout;
                TextView cat;
                public Holder(@NonNull View itemView) {
                    super(itemView);
                    layout=itemView.findViewById(R.id.item);
                    cat=itemView.findViewById(R.id.ItemText);
                }


                public void bindItem(final Category_model.Result item) {
                    cat.setText(item.getProductCat());

                    layout.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startActivity(new Intent(getApplicationContext(), Product_page.class).putExtra(DbHelper.COLUMN_ID,getIntent().getStringExtra("id")).putExtra("cat",item.getProductCat()));
                            finish();
                        }
                    });
                }
            }
        }
        );


        getCategory();

    }

    private void getCategory() {
        AppController.getInstance().addToRequestQueue(new StringRequest(StringRequest.Method.POST, APIs.ProductCategory, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);

                    if (jsonObject.getBoolean("status")){

                        Category_model request = new Gson().fromJson(response,Category_model.class);
                        list.addAll(request.getResult());
                        //   Notificationitems.addAll(request.getResult2());
                        rvCategory.getAdapter().notifyDataSetChanged();


                    }else {
                     /*   noRequest.setVisibility(View.VISIBLE);
                        noRequest.setText(jsonObject.getString("message"));*/
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> hashMap = new HashMap();
                hashMap.put("company_id", getIntent().getStringExtra("id"));
                return hashMap;
            }
        });
    }
}
