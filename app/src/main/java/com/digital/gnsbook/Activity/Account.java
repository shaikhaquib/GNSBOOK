package com.digital.gnsbook.Activity;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.digital.gnsbook.Config.APIs;
import com.digital.gnsbook.Config.AppController;
import com.digital.gnsbook.Global;
import com.digital.gnsbook.Model.TransactionModel;
import com.httpgnsbook.gnsbook.R;
import com.digital.gnsbook.ViewDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.ithebk.barchart.BarChart;
import me.ithebk.barchart.BarChartModel;

public class Account extends AppCompatActivity {

    private BarChart barChartVertical;
    TextView mainbalance , repurchace , paivalue ,lastpayout ;
    RecyclerView rvTransaction;
    ViewDialog dialog;
    List<TransactionModel> transactionModel = new ArrayList<>();
    CardView porogress;
    LinearLayoutManager layoutManager;
    int count = 10 ;
    private int offset = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        getSupportActionBar().hide();
        dialog = new ViewDialog(this);
        barChartVertical = findViewById(R.id.bar_chart_vertical);

        getBinaryData();
        mainbalance      = findViewById(R.id.mainbalance);
        repurchace       = findViewById(R.id.Repurchaseamt);
        paivalue         = findViewById(R.id.pairvalue);
        lastpayout       = findViewById(R.id.lastpayout);
        rvTransaction    = findViewById(R.id.transactiondetail);
        rvTransaction.setNestedScrollingEnabled(false);
        rvTransaction.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        porogress = findViewById(R.id.accountprogrssview);
        NestedScrollView scroller = findViewById(R.id.acountScroll);

        balance();
        rvTransaction.setAdapter(new RecyclerView.Adapter() {@NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(Account.this).inflate(R.layout.rvtransaction, viewGroup, false);
            return new Holder(view);
        }

            @Override
            public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

                Holder holder = (Holder) viewHolder;
                final TransactionModel postmodel = transactionModel.get(i);

                //holder.amount.setText(postmodel.credit);
                holder.date.setText(postmodel.date);
                holder.desc.setText("Info : "+postmodel.details);
                holder.id.setText("ID : "+postmodel.trID);
                holder.status.setTag(postmodel);
                holder.more.setTag(postmodel);

                if (postmodel.credit > 0){
                    holder.amount.setText("Credited : "+String.valueOf(postmodel.credit));
                    holder.amount.setTextColor(Color.parseColor("#4caf50"));
                    holder.status.setImageResource(R.drawable.ic_plus);

                }else {
                    holder.amount.setText("Debited : "+String.valueOf(postmodel.debit));
                    holder.amount.setTextColor(Color.parseColor("#f6272f"));
                    holder.status.setImageResource(R.drawable.ic_minusl);
                }

                holder.more.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (postmodel.credit > 0){
                            CapingLimit(postmodel.credit,postmodel.beTrans,postmodel.afTrans,postmodel.surcharge,postmodel.date,postmodel.details,postmodel.trID);
                        }else {
                            CapingLimit(postmodel.debit,postmodel.beTrans,postmodel.afTrans,postmodel.surcharge,postmodel.date,postmodel.details,postmodel.trID);
                        }
                    }
                });

            }

            @Override
            public int getItemCount() {
                return transactionModel.size();
            }

            class Holder extends RecyclerView.ViewHolder {
                ImageView status , more;
                TextView amount, date, id , desc;
                public Holder(@NonNull View itemView) {
                    super(itemView);

                    status = itemView.findViewById(R.id.trstatus);
                    more = itemView.findViewById(R.id.trmore);
                    amount = itemView.findViewById(R.id.tramt);
                    date = itemView.findViewById(R.id.trdate);
                    id = itemView.findViewById(R.id.trid);
                    desc = itemView.findViewById(R.id.trinfo);

                }
            }
        });

        getTransactionData();

        if (scroller != null) {

            scroller.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
                @Override
                public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {



                    if (scrollY == (v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight())) {

                        if (count > 0){

                            offset = offset + 10 ;
                            getTransactionData();
                            porogress.setVisibility(View.VISIBLE);}
                    }
                }
            });
        }



/*
        barChartVertical.setOnBarClickListener(new BarChart.OnBarClickListener() {
            @Override
            public void onBarClick(BarChartModel barChartModel) {
                barChartVertical.removeBar(barChartModel);
            }
        });
*/
//        int a[] =new int[]{-20,-5,2,4,5,1,-1};
//        //System.out.println("Minimum of "+ Arrays.toString(a)+":Is:"+solution(a));

    }

    private void getBinaryData() {
        dialog.show();
        StringRequest request = new StringRequest(StringRequest.Method.POST, APIs.Binary, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                dialog.dismiss();
                try {
                    JSONObject object = new JSONObject(response);
                    if (!object.getBoolean("status")){

                        JSONArray jsonArray = object.getJSONArray("result");
                        JSONObject jObject =jsonArray.getJSONObject(jsonArray.length()-1);
                        paivalue.setText("₹" + String.valueOf(jObject.getInt("pair_value")));


                        for (int i = 0 ; i < jsonArray.length() ; i++)
                        {
                            JSONObject jsonObject =jsonArray.getJSONObject(i);
                            BarChartModel barChartModel = new BarChartModel();
                            barChartModel.setBarValue(jsonObject.getInt("pair_value"));

                            String[] Date = jsonObject.getString("date").split("-");

                            barChartModel.setBarText(Date[2]);
                            barChartModel.setBarTag(Date[2]);
                            barChartModel.setBarValue(jsonObject.getInt("pair_value"));
                            //Random rnd = new Random();
                            //  barChartModel.setBarColor(Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256)));
                            //  barChartModel.setBarTag(null);
                            barChartVertical.addBar(barChartModel);

                        }

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
        });
        AppController.getInstance().addToRequestQueue(request);

    }

    private void balance() {
        //  dialog.show();
        StringRequest request =new StringRequest(Request.Method.POST, APIs.Jolo_soft_balance, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //      dialog.dismiss();
                try {

                    JSONObject jsonObject =new JSONObject(response);
                    mainbalance.setText("₹" +jsonObject.getString("balance"));
                    repurchace.setText("₹" +jsonObject.getString("secondary_balance"));


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //   dialog.dismiss();
                // Toast.makeText(getApplicationContext(), "Connection problem please try agin later!", Toast.LENGTH_LONG).show();
            }
        }){

            @Override
            public String getBodyContentType() {
                return "application/x-www-form-urlencoded; charset=UTF-8";
            }


            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<String, String>();
                params.put("customer_id",Global.customerid);

                return params;
            }

        };
        AppController.getInstance().addToRequestQueue(request);

    }
    private void getTransactionData() {



        StringRequest stringRequest = new StringRequest(StringRequest.Method.POST, APIs.transaction_detail, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                //   dialog.dismiss();

                porogress.setVisibility(View.GONE);
                try {

                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getBoolean("status")){

                        JSONArray jsonArray = jsonObject.getJSONArray("result");

                        JSONObject Jobject = jsonArray.getJSONObject(0);
                        //  TransactionModel model = new TransactionModel();

                        if (Jobject.getInt("credit") > 0 ) {
                            lastpayout.setText("₹"+Jobject.getString("credit"));
                        }else
                        if (Jobject.getInt("debit")>0) {
                            lastpayout.setText("₹"+Jobject.getString("debit"));

                        }

                        for (int i = 0; i < jsonArray.length(); i++) {

                            JSONObject object = jsonArray.getJSONObject(i);
                            TransactionModel model = new TransactionModel();


                            Object credit = object.get("credit");
                            Object debit = object.get("debit");
                            if (credit instanceof Integer) {
                                if (object.getInt("credit") > 0) {
                                    model.credit = object.getInt("credit");
                                }
                            } else if (object.getString("credit") != null) {
                                //  model.credit = object.getInt("credit");

                            }

                            if (debit instanceof Integer) {
                                if (object.getInt("debit") > 0) {
                                    model.debit = object.getInt("debit");
                                }
                            } else if (object.getString("debit") != null) {
                                // model.debit = object.get("debit");
                            }


                            model.customer_id = object.getString("customer_id");
                            model.trID = object.getString("transaction_id");
                            model.details = object.getString("description");
                            model.date = Global.Date(object.getString("date"));

                            Object beTrans = object.get("before_transaction");
                            Object afTrans = object.get("after_transaction");
                            Object surcharge = object.get("surcharge");

                            if (beTrans instanceof Integer) {
                                model.beTrans = object.getInt("before_transaction");
                            }
                            if (afTrans instanceof Integer) {
                                model.afTrans = object.getInt("after_transaction");
                            }
                            if (surcharge instanceof Integer) {
                                model.surcharge = object.getInt("surcharge");
                            }


                            transactionModel.add(model);
                            rvTransaction.getAdapter().notifyItemRangeInserted(rvTransaction.getAdapter().getItemCount(), transactionModel.size() - 1);
                        }
                    }else {
                        count = 0 ;
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                porogress.setVisibility(View.GONE);
            }
        })

        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param = new HashMap<String, String>();
                param.put("customer_id", Global.customerid);
                param.put("offset", String.valueOf(offset));
                param.put("limit", "20");
                return param;
            }
        };
        AppController.getInstance().addToRequestQueue(stringRequest);

    }


    private void CapingLimit(int credit, int beTrans, int afTrans, int surcharge, String date, String details, final String trID) {


        LayoutInflater layoutInflaterAndroid = LayoutInflater.from(Account.this);
        View mView = layoutInflaterAndroid.inflate(R.layout.transactiondetail, null);
        final android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(Account.this);
        // builder.setCancelable(false);

        final TextView amount =mView.findViewById(R.id.dgamount);
        final TextView bt     =mView.findViewById(R.id.dgbt);
        final TextView at     =mView.findViewById(R.id.dgat);
        final TextView txtsurcharge =mView.findViewById(R.id.dgsur);
        final TextView  description =mView.findViewById(R.id.dgdesc);
        final TextView txtdate =mView.findViewById(R.id.dgdate);
        Button close =mView.findViewById(R.id.dgclose);
        final Button dispute =mView.findViewById(R.id.dispute);

        dispute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispute(trID);
            }
        });

        amount.setText("₹ "+String.valueOf(credit));
        bt.setText("₹ "+String.valueOf(beTrans));
        at.setText("₹ "+String.valueOf(afTrans));
        txtsurcharge.setText(String.valueOf(surcharge));
        description.setText(details);
        txtdate.setText(date);

        builder.setView(mView);
        final android.support.v7.app.AlertDialog dialog = builder.create();
        Window window = dialog.getWindow();
        window.setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.show();

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });


    }

    private void dispute(final String trID) {
        dialog.show();
        StringRequest stringRequest = new StringRequest(StringRequest.Method.POST, APIs.change_dispute_status, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                dialog.dismiss();
                try {
                    JSONObject jsonObject = new JSONObject(response);

                    if (jsonObject.getBoolean("status")){
                        Global.successDilogue(Account.this,"You have successfully dispute this transaction.We will investigate this matter and correct the error as soon as possible.");
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
                Map<String, String> params = new HashMap<String, String>();
                params.put("customer_id",Global.customerid);
                params.put("transaction_id",trID);
                params.put("status","10");
                return params;            }

        };
        AppController.getInstance().addToRequestQueue(stringRequest);

    }

}