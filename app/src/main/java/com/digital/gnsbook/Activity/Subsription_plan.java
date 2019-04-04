package com.digital.gnsbook.Activity;

import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.digital.gnsbook.Config.APIs;
import com.digital.gnsbook.Config.AppController;
import com.digital.gnsbook.Global;
import com.digital.gnsbook.Model.Subscription_Response;
import com.digital.gnsbook.Model.Subscription_Response_ResultItem;
import com.digital.gnsbook.ViewDialog;
import com.google.gson.Gson;
import com.httpgnsbook.gnsbook.R;

import org.angmarch.views.NiceSpinner;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Subsription_plan extends AppCompatActivity {

    RecyclerView rvPlan;
    List<Subscription_Response_ResultItem> resultItems = new ArrayList<>();
    RelativeLayout fabAdd;
     CheckBox chk1;
     CheckBox chk2;
     CheckBox chk3;
    ArrayList<String> list =new ArrayList<>() ;
    String Subscription_type    = "1" , planvalidity = "1";
    ViewDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subsription_plan);
        getSupportActionBar().hide();
        rvPlan = findViewById(R.id.rvPlan);
        fabAdd = findViewById(R.id.fabAddPlan);
        rvPlan.setItemAnimator(null);
        rvPlan.setLayoutManager(new LinearLayoutManager(Subsription_plan.this));
        dialog = new ViewDialog(this);
        //  rvPlan.addItemDecoration(new RecyclerViewItemDecorator(10));
        if (Global.customerid .equals(String.valueOf(Global.Company_Admin_Id))){
            fabAdd.setVisibility(View.VISIBLE);
        }else{
            fabAdd.setVisibility(View.GONE);
        }


        rvPlan.setAdapter(new RecyclerView.Adapter() {
            @NonNull
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                return new Holder(LayoutInflater.from(Subsription_plan.this).inflate(R.layout.subsplan, viewGroup, false));
            }

            @Override
            public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
                Holder holder = (Holder) viewHolder;
                final Subscription_Response_ResultItem current = resultItems.get(i);

                if (current.getValidity() == 1) {
                    holder.type.setText(" / For " + current.getValidity() + " Month");
                } else if (current.getPlanType() == 12) {
                    holder.type.setText(" / For" + current.getValidity() + " Year");
                } else if (current.getPlanType() == 100) {
                    holder.type.setText(" / For Lifetime");
                }

                holder.amount.setText(String.valueOf(current.getAmount()));
                holder.name.setText(current.getPlanName());
                String[] Desc = current.getDetails().split(",");
                holder.desc.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                final List<String> wordList = Arrays.asList(Desc);
                Log.d("Size", String.valueOf(wordList.size()));
                holder.desc.setAdapter(new RecyclerView.Adapter() {
                    @NonNull
                    @Override
                    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                        View view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.textview, viewGroup, false);
                        return new SimpleViewHolder(view);
                    }

                    @Override
                    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
                        SimpleViewHolder holder = (SimpleViewHolder) viewHolder;
                        holder.textView.setText(wordList.get(i));
                        holder.textView.setGravity(Gravity.CENTER);
                    }

                    @Override
                    public int getItemCount() {
                        return wordList.size();
                    }

                    class SimpleViewHolder extends RecyclerView.ViewHolder {
                        public TextView textView;

                        SimpleViewHolder(View itemView) {
                            super(itemView);
                            textView = itemView.findViewById(R.id.textView);
                        }
                    }

                });

                holder.order.setTag(current);
                holder.allreadymember.setTag(current);

                if(current.getSubscribeStatus()==0){
                    holder.order.setVisibility(View.VISIBLE);
                }else {
                    holder.allreadymember.setVisibility(View.VISIBLE);
                }

                holder.order.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        OrderNow(current.getId());
                    }
                });



            }

            @Override
            public int getItemCount() {
                return resultItems.size();
            }

            class Holder extends RecyclerView.ViewHolder {
                TextView name, amount, type;
                RecyclerView desc;
                CardView order , allreadymember;

                public Holder(@NonNull View itemView) {
                    super(itemView);
                    name = itemView.findViewById(R.id.subPlanName);
                    amount = itemView.findViewById(R.id.subPlanamount);
                    type = itemView.findViewById(R.id.subPlantype);
                    desc = itemView.findViewById(R.id.subPlanDescription);
                    order = itemView.findViewById(R.id.subPlanOrder);
                    allreadymember = itemView.findViewById(R.id.alSubscribed);
                }
            }
        });

        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddDialoge();
            }
        });

        getPlan();
    }

    private void OrderNow(final int id) {
        AppController.getInstance().addToRequestQueue(new StringRequest(StringRequest.Method.POST, APIs.SubPlan, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);

                    if (jsonObject.getBoolean("status")){
                        Global.successDilogue(Subsription_plan.this,"You have successfully Subscribed the plan");
                    }else {
                        Global.successDilogue(Subsription_plan.this,response);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
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
                    Log.d("VolleyError",body);
                } catch (UnsupportedEncodingException e) {
                    // exception
                }
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> hashMap = new HashMap();
                hashMap.put("sid", String.valueOf(id));
                hashMap.put("customer_id", Global.customerid);
                return hashMap;
            }
        });
    }


    private void AddDialoge() {
        View inflate = LayoutInflater.from(Subsription_plan.this).inflate(R.layout.subscribtion_form, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(Subsription_plan.this);

        NiceSpinner niceSpinner = inflate.findViewById(R.id.plan);
        final EditText valdity = inflate.findViewById(R.id.edtvalidity);
        final EditText name = inflate.findViewById(R.id.edtPlanName);
        final EditText amt = inflate.findViewById(R.id.edtAmt);
        final CardView btnCreate = inflate.findViewById(R.id.createOrder);
        chk1= inflate.findViewById(R.id.checkBox1);
        chk2= inflate.findViewById(R.id.checkBox2);
        chk3= inflate.findViewById(R.id.checkBox3);
        final List<String> dataset = new LinkedList<>(Arrays.asList("Month", "Year", "LifeTime"));
        niceSpinner.attachDataSource(dataset);
        valdity.setText("1");

        niceSpinner.addOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(getActivity(), , Toast.LENGTH_SHORT).show();

                if (dataset.get(position).equals("LifeTime")) {
                    valdity.setEnabled(false);
                    valdity.setText("");
                    Subscription_type="3";
                    planvalidity = "100";
                } else if (dataset.get(position).equals("Year")) {
                    valdity.setEnabled(true);
                    valdity.setText("1");
                    Subscription_type="2";
                } else if (dataset.get(position).equals("Month")) {
                    valdity.setEnabled(true);
                    valdity.setText("1");
                    Subscription_type="1";
                }

            }
        });
        builder.setView(inflate);
        final AlertDialog create = builder.create();
        create.show();


        btnCreate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                if (!Subscription_type.equals("3")){
                           planvalidity =  valdity.getText().toString();

                }
                StringBuffer stringBuffer = new StringBuffer();
                stringBuffer.append(chk1.isChecked() ? chk1.getText()+",":"");
                stringBuffer.append(chk2.isChecked() ? chk2.getText()+",":"");
                stringBuffer.append(chk3.isChecked() ? chk3.getText():"");
                String strCheck=  String.valueOf(stringBuffer);

                if (strCheck.endsWith(",")) {
                    strCheck = strCheck.substring(0, strCheck.length() - 1);
                }

              //  Toast.makeText(getApplicationContext(), strCheck, Toast.LENGTH_SHORT).show();
                if(name.getText().toString().equals("")){
                    Global.diloge(Subsription_plan.this,"Error Field Required", "Plan Name Field Is Required");
                }else if(amt.getText().toString().equals("")){
                    Global.diloge(Subsription_plan.this,"Error Field Required", "Plan Amount Field Is Required");
                }else {
                    createPlane(name.getText().toString(),amt.getText().toString(),planvalidity,Subscription_type,strCheck);
                }



            }

        });

    }

    private void createPlane(final String name, final String amt, final String planvalidity, String subscription_type, final String strCheck) {
        dialog.show();
        AppController.getInstance().addToRequestQueue(new StringRequest(StringRequest.Method.POST, APIs.Createplane, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
            dialog.dismiss();
                try {
                    JSONObject jsonObject = new JSONObject(response);

                    if (jsonObject.getBoolean("status")){
                        Global.successDilogue(Subsription_plan.this,"You have successfully created Subscription plan");
                    }else {
                        Global.successDilogue(Subsription_plan.this,response);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
             dialog.dismiss();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> hashMap = new HashMap();
                hashMap.put("cid", Global.Company_Id);
                hashMap.put("plan_type", "1");
                hashMap.put("admin_id", String.valueOf(Global.Company_Admin_Id));
                hashMap.put("plan_name", name);
                hashMap.put("amount",  amt);
                hashMap.put("details", strCheck);
                hashMap.put("validity",planvalidity);
                hashMap.put("subscription_type",Subscription_type);
                return hashMap;            }
        });
    }


    private void getPlan() {
        AppController.getInstance().addToRequestQueue(new StringRequest(StringRequest.Method.POST, APIs.Subscribe_plan, new Response.Listener<String>() {
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

                if (error == null || error.networkResponse == null) {
                    return;
                }

                String body;
                //get status code here
                final String statusCode = String.valueOf(error.networkResponse.statusCode);
                //get response body and parse with appropriate encoding
                try {
                    body = new String(error.networkResponse.data,"UTF-8");
                    Log.d("VolleyError",body);
                } catch (UnsupportedEncodingException e) {
                    // exception
                }
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> hashMap = new HashMap();
                hashMap.put("cid", Global.Company_Id);
                hashMap.put("customer_id", Global.customerid);
                return hashMap;
            }
        });
    }

/*
    public class SimpleRVAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
        private List<String> dataSource = new ArrayList<>();
        SimpleRVAdapter(List<String> dataArgs){
            dataSource = dataArgs;
            Log.d("Size",String.valueOf(dataSource.size()));

        }



    }
*/
}
