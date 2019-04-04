package com.digital.gnsbook.Activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
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
import com.digital.gnsbook.Config.APIs;
import com.digital.gnsbook.Config.AppController;
import com.digital.gnsbook.Global;
import com.digital.gnsbook.Model.Compony_data;
import com.httpgnsbook.gnsbook.R;
import com.digital.gnsbook.Extra.RecyclerViewItemDecorator;
import com.digital.gnsbook.ViewDialog;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Pool_Rewards extends AppCompatActivity {

    ViewDialog dialog;
    String[] images = {
            "img/goatrip.jpg",
            "img/bangkok.png",
            "img/bike.png",
            "img/car.jpg"
    };
    int claim_pool_id = 0;
    ArrayList<Compony_data> postmodels = new ArrayList<>();
    RecyclerView recyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_componylist);

        dialog = new ViewDialog(this);
        recyclerView = findViewById(R.id.rvclist);
        recyclerView.setLayoutManager(new GridLayoutManager(this,1));
        int spaceInPixels = 10;
        recyclerView.addItemDecoration(new RecyclerViewItemDecorator(spaceInPixels));
        recyclerView.setAdapter(new RecyclerView.Adapter() {
            @NonNull
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                View view = LayoutInflater.from(Pool_Rewards.this).inflate(R.layout.rewards,viewGroup,false);
                return new Holder(view);
            }

            @Override
            public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

                Holder holder=(Holder)viewHolder;
                final Compony_data componymodel=postmodels.get(i);


                Picasso.get()
                        .load("http://gnsbook.com/"+images[i])
                        .into(holder.dp);
                holder.poolname.setText(componymodel.details);
                holder.reAmount.setText(componymodel.reward_amount);
                holder.coamount.setText(componymodel.collected_amount);
                holder.enPool.setTag(componymodel);
                holder.clpool.setTag(componymodel);

                if (i == 0 && claim_pool_id == 0){
                    holder.enPool.setVisibility(View.VISIBLE);
                    holder.clpool.setVisibility(View.GONE);
                }

                holder.enPool.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        EnterPool();
                    }
                });
            }

            @Override
            public int getItemCount() {
                return postmodels.size();
            }


            class Holder extends RecyclerView.ViewHolder {
                ImageView dp ;
                TextView poolname ,reAmount, coamount;
                Button enPool ,clpool, juPool;
                public Holder(@NonNull View itemView) {
                    super(itemView);


                    dp=itemView.findViewById(R.id.poolbanner);
                    poolname =itemView.findViewById(R.id.poolname);
                    reAmount =itemView.findViewById(R.id.reAmount);
                    coamount=itemView.findViewById(R.id.coamount);
                    enPool=itemView.findViewById(R.id.enPool);
                    clpool=itemView.findViewById(R.id.clpool);
                    juPool=itemView.findViewById(R.id.juPool);

                }
            }
        });

        getuserpooldata();
    }

    private void EnterPool() {

        dialog.show();
        AppController.getInstance().addToRequestQueue(new StringRequest(StringRequest.Method.POST, APIs.PooLenter, new Response.Listener < String > () {@Override
        public void onResponse(String response) {
            dialog.dismiss();
            try {
                JSONObject jsonObject = new JSONObject(response);

                if (jsonObject.getBoolean("status")) {

                    Global.successDilogue(Pool_Rewards.this,"You have successfully entered into Pool Reward");
                }

            } catch(JSONException e) {
                e.printStackTrace();
            }

        }},
                new Response.ErrorListener() {@Override
                public void onErrorResponse(VolleyError error) {
                    String body = null;
                    dialog.dismiss();

                }
                }) {@Override
        protected Map< String, String > getParams() throws AuthFailureError {
            Map < String, String > param = new HashMap< String, String >();
            param.put("customer_id", Global.customerid);
            param.put("claim_pool_id", "1");
            return param;
        }
        });

    }

    private void getuserpooldata() {

        dialog.show();
        AppController.getInstance().addToRequestQueue(new StringRequest(StringRequest.Method.POST, APIs.UserPoolDATA, new Response.Listener < String > () {@Override
        public void onResponse(String response) {
            dialog.dismiss();
            try {
                JSONObject jsonObject = new JSONObject(response);

                if (jsonObject.getBoolean("status")) {

                    JSONArray jsonArray = jsonObject.getJSONArray("result");
                    JSONObject object = jsonArray.getJSONObject(0);
                    claim_pool_id = object.getInt("claim_pool_id");
                    getTimelinePost();
                }

            } catch(JSONException e) {
                e.printStackTrace();
            }

        }},
                new Response.ErrorListener() {@Override
                public void onErrorResponse(VolleyError error) {
                    String body = null;
                    dialog.dismiss();

                }
                }) {@Override
        protected Map< String, String > getParams() throws AuthFailureError {
            Map < String, String > param = new HashMap< String, String >();
            param.put("customer_id", Global.customerid);
            param.put("claim_pool_id", "1");
            return param;
        }
        });

    }

    private void getTimelinePost() {

        dialog.show();
        AppController.getInstance().addToRequestQueue(new StringRequest(StringRequest.Method.POST, APIs.PoolDATA, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                dialog.dismiss();
                try {
                    JSONObject jsonObject = new JSONObject(response);

                    if (jsonObject.getBoolean("status")) {

                        JSONArray jsonArray = jsonObject.getJSONArray("result");
                        for (int i = 0; i < jsonArray.length(); i++) {

                            JSONObject object = jsonArray.getJSONObject(i);

                                Compony_data compony_data = new Compony_data();
                                compony_data.id = object.getString("id");
                                compony_data.pool_id = object.getString("pool_id");
                                compony_data.details = object.getString("details");
                                compony_data.reward_amount = object.getString("reward_amount");
                                compony_data.collected_amount = object.getString("collected_amount");
                                Pool_Rewards.this.postmodels.add(compony_data);
                                Pool_Rewards.this.recyclerView.getAdapter().notifyDataSetChanged();


                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }));
    }
}
