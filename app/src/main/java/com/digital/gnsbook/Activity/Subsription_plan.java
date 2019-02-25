package com.digital.gnsbook.Activity;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.digital.gnsbook.Config.APIs;
import com.digital.gnsbook.Config.AppController;
import com.digital.gnsbook.Global;
import com.digital.gnsbook.Model.CommentItem;
import com.digital.gnsbook.Model.Comment_Response;
import com.digital.gnsbook.Model.Compony_data;
import com.digital.gnsbook.Model.ResSubsItem;
import com.digital.gnsbook.Model.SubsResultItem;
import com.digital.gnsbook.Model.Subscription_Response;
import com.digital.gnsbook.Model.Subscription_Response_ResultItem;
import com.digital.gnsbook.RecyclerViewItemDecorator;
import com.google.gson.Gson;
import com.httpgnsbook.gnsbook.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Subsription_plan extends AppCompatActivity {

    RecyclerView rvPlan;
    List<Subscription_Response_ResultItem> resultItems;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subsription_plan);

        rvPlan=findViewById(R.id.rvPlan);
        rvPlan.setItemAnimator(null);
        rvPlan.setLayoutManager(new GridLayoutManager(Subsription_plan.this, 2));
        rvPlan.addItemDecoration(new RecyclerViewItemDecorator(10));

        rvPlan.setAdapter(new RecyclerView.Adapter() {
            @NonNull
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                return new Holder(LayoutInflater.from(Subsription_plan.this).inflate(R.layout.subsplan, viewGroup, false));
            }

            @Override
            public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
                Holder holder = (Holder) viewHolder;
                Subscription_Response_ResultItem current = resultItems.get(i);

                if (current.getValidity() == 1 ){
                    holder.type.setText(" / Per Month");
                }else  if (current.getPlanType() == 12 ){
                    holder.type.setText(" / For Year");
                } else  if (current.getPlanType() == 100 ){
                    holder.type.setText(" / For Year");
                }

                holder.amount.setText(String.valueOf(current.getAmount()));
                String[] Desc  = current.getDetails().split(",");
                holder.desc.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                holder.desc.setAdapter(new SimpleRVAdapter(Desc));


            }

            @Override
            public int getItemCount() {
                return resultItems.size();
            }

            class Holder extends RecyclerView.ViewHolder {
                TextView name , amount ,type;
                RecyclerView desc;

                public Holder(@NonNull View itemView) {
                    super(itemView);
                    name = itemView.findViewById(R.id.subPlanName);
                    amount = itemView.findViewById(R.id.subPlanamount);
                    type = itemView.findViewById(R.id.subPlantype);
                    desc = itemView.findViewById(R.id.subPlanDescription);
                }
            }
        });

        getPlan();
    }

    private void getPlan() {
        AppController.getInstance().addToRequestQueue(new StringRequest(StringRequest.Method.POST, APIs.SubPlan, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jSONObject = new JSONObject(response);
                    if (jSONObject.getBoolean("status")) {
                        Subscription_Response comment_Response = (Subscription_Response) new Gson().fromJson(response, Subscription_Response.class);
                        resultItems.addAll(comment_Response.getResult());
                        rvPlan.getAdapter().notifyDataSetChanged();
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
                hashMap.put("cid ", Global.Company_Id);
                return hashMap;
            }
        });
    }

    public class SimpleRVAdapter extends RecyclerView.Adapter<SimpleRVAdapter.SimpleViewHolder> {
        private String[] dataSource;
        public SimpleRVAdapter(String[] dataArgs){
            dataSource = dataArgs;
        }

        @Override
        public SimpleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            // create a new view
            View view = new TextView(parent.getContext());
            SimpleViewHolder viewHolder = new SimpleViewHolder(view);
            return viewHolder;
        }

        public  class SimpleViewHolder extends RecyclerView.ViewHolder{
            public TextView textView;
            public SimpleViewHolder(View itemView) {
                super(itemView);
                textView = (TextView) itemView;
            }
        }

        @Override
        public void onBindViewHolder(SimpleViewHolder holder, int position) {
            holder.textView.setText(dataSource[position]);
        }

        @Override
        public int getItemCount() {
            return dataSource.length;
        }
    }
}
