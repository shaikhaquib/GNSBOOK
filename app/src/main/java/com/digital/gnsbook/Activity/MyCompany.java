package com.digital.gnsbook.Activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.digital.gnsbook.Config.APIs;
import com.digital.gnsbook.Config.AppController;
import com.digital.gnsbook.Config.DbHelper;
import com.digital.gnsbook.Global;
import com.digital.gnsbook.Model.Company_list.Company_list_Model;
import com.digital.gnsbook.Model.Company_list.Result_Company_list;
import com.digital.gnsbook.ViewDialog;
import com.google.gson.Gson;
import com.httpgnsbook.gnsbook.R;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyCompany extends AppCompatActivity {

    List<Result_Company_list> componyModel = new ArrayList();
    ViewDialog dialog;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_componylist);

        dialog = new ViewDialog(this);
        recyclerView = findViewById(R.id.rvclist);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerView.setAdapter(new ReacyclerAdapter());
        getComponyData();
    }

    class ReacyclerAdapter extends RecyclerView.Adapter {

        /* renamed from: com.digital.gnsbook.Activity.Compony_list$1$Holder */
        class Holder extends RecyclerView.ViewHolder {
            RelativeLayout Follow;
            TextView desc;
            ImageView dp,bg;
            TextView name;
            CardView suggestion;

            public Holder(@NonNull View view) {
                super(view);
                dp = (ImageView) view.findViewById(R.id.cdp);
                bg = (ImageView) view.findViewById(R.id.bg);
                name = (TextView) view.findViewById(R.id.cName);
                Follow = (RelativeLayout) view.findViewById(R.id.cFollow);
                desc = (TextView) view.findViewById(R.id.cDesc);
                suggestion=view.findViewById(R.id.suggestion);

            }
        }



        @NonNull
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            return new ReacyclerAdapter.Holder(LayoutInflater.from(MyCompany.this).inflate(R.layout.componylist, viewGroup, false));
        }

        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
            ReacyclerAdapter.Holder holder = (ReacyclerAdapter.Holder) viewHolder;
            final Result_Company_list compony_data = componyModel.get(i);
            holder.name.setText(compony_data.getName());
            holder.desc.setText(compony_data.getDescription());
            Picasso picasso = Picasso.get();
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(APIs.Dp);
            stringBuilder.append(compony_data.getLogo());
            picasso.load(stringBuilder.toString()).into(holder.dp);
            Picasso.get().load(APIs.Banner+compony_data.getBanner()).into(holder.bg);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    startActivity(new Intent(getApplicationContext(), Companypage.class).putExtra(DbHelper.COLUMN_ID, compony_data.getCompanyId()));
                }
            });

            if (compony_data.getAdminId() != Integer.parseInt(Global.customerid)){
                holder.suggestion.setVisibility(View.VISIBLE);
            }
        }

        public int getItemCount() {
            return componyModel.size();
        }
    }

    private void getComponyData() {
        this.dialog.show();
        AppController.getInstance().addToRequestQueue(new StringRequest(1, APIs.MycompanydataAPI, new Response.Listener<String>() {
            @Override
            public void onResponse(String responce) {
                dialog.dismiss();
                try {
                    JSONObject jSONObject = new JSONObject(responce);
                    if (jSONObject.getBoolean("status")) {
                        Company_list_Model comment_Response =  new Gson().fromJson(responce, Company_list_Model.class);
                        componyModel.addAll(comment_Response.getResult());
                        recyclerView.getAdapter().notifyDataSetChanged();
                        getSuggestion();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                dialog.dismiss();
            }
        }) {
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> hashMap = new HashMap();
                hashMap.put("limit", "10");
                hashMap.put("offset", "0");
                hashMap.put("customer_id", Global.customerid);
                return hashMap;
            }
        });
    }
    private void getSuggestion() {
        AppController.getInstance().addToRequestQueue(new StringRequest(1, APIs.Suggestion_companydataAPI, new Response.Listener<String>() {
            @Override
            public void onResponse(String responce) {
                dialog.dismiss();
                try {
                    JSONObject jSONObject = new JSONObject(responce);
                    if (jSONObject.getBoolean("status")) {
                        Company_list_Model comment_Response =  new Gson().fromJson(responce, Company_list_Model.class);
                        componyModel.addAll(comment_Response.getResult());
                        recyclerView.getAdapter().notifyDataSetChanged();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                dialog.dismiss();
            }
        }) {
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> hashMap = new HashMap();
                hashMap.put("limit", "10");
                hashMap.put("offset", "0");
                hashMap.put("admin_id", Global.customerid);
                return hashMap;
            }
        });

    }

}
