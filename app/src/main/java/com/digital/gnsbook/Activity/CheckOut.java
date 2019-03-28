package com.digital.gnsbook.Activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.digital.gnsbook.Config.APIs;
import com.digital.gnsbook.Config.AppController;
import com.digital.gnsbook.DividerDecorator;
import com.digital.gnsbook.Global;
import com.digital.gnsbook.Model.AddressItem;
import com.digital.gnsbook.Model.AddressResponse;
import com.digital.gnsbook.Model.CartItem;
import com.digital.gnsbook.ViewDialog;
import com.google.gson.Gson;
import com.httpgnsbook.gnsbook.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import instamojo.library.InstamojoPay;
import instamojo.library.InstapayListener;

public class CheckOut extends AppCompatActivity {

    TextView totelItem , Totalamount , ShipingCharges ,PayAbleAmoumt;
    int Amount , count ,shipingCharges = 0,grandTotal,address_id;
    int from; //This must be declared as global !
    ViewDialog progressDialog;
    LinearLayout Addlayout;
    List<AddressItem> prodlist = new ArrayList<>();

    TextView Mname , Mmob, Mtype , Madd,Mzip;

    Button Order;
    String[] choice ;


    String orderid , Status;
    String txnid;
    String paymentid;
    String token;
    boolean aBoolean=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);
        progressDialog = new ViewDialog(this);
        Addlayout       = findViewById(R.id.Addlayout);
        totelItem       = findViewById(R.id.titem);
        Totalamount     = findViewById(R.id.total) ;
        ShipingCharges  = findViewById(R.id.shipcost) ;
        PayAbleAmoumt   = findViewById(R.id.stotal);
        Order           = findViewById(R.id.Next);

        Mname =findViewById(R.id.name);
        Mmob = findViewById(R.id.mob);
        Mtype =findViewById(R.id.type);
        Madd = findViewById(R.id.add);
        Mzip = findViewById(R.id.zip);

        Amount = getIntent().getIntExtra("Amount",0);
        count = getIntent().getIntExtra("count",0);
        grandTotal = Amount + shipingCharges;
        totelItem.setText(String.valueOf(count));
        Totalamount.setText(String.valueOf(Amount));
        ShipingCharges.setText(String.valueOf(shipingCharges));

        Order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Mname.getText().toString().isEmpty())
                {
                    Toast.makeText(CheckOut.this, "Please Select Shipping Address...", Toast.LENGTH_SHORT).show();
                }else {
                    callInstamojoPay(Global.Email, Global.mobile, String.valueOf(grandTotal), "Online Shoping", Mname.getText().toString());
                } }
        });

    }

    public void SelectAddress(View view) {

        if (view.getId() == R.id.addAddress ) {
        startActivity(new Intent(getApplicationContext(),Add_Address.class));
        }else {
            getData();
        }

    }

    private void getData() {
        //data variables call
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(StringRequest.Method.POST, APIs.display_address, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //   tprice=0;
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    progressDialog.dismiss();

                    if(jsonObject.getBoolean("status")) {
                        Gson gson = new Gson();
                        AddressResponse res = gson.fromJson(response, AddressResponse.class);
                        prodlist = res.getResult();


                        final Dialog dialog = new Dialog(CheckOut.this);
                        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        dialog.setCancelable(false);
                        dialog.setContentView(R.layout.recyclerview);

                        RecyclerView recyclerView = dialog.findViewById(R.id.recyclerview);
                        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                        recyclerView.addItemDecoration(new DividerDecorator(getApplicationContext()));
                        int lastCheckedPosition = -1;
                        recyclerView.setAdapter(new RecyclerView.Adapter() {
                            @NonNull
                            @Override
                            public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                                View view= LayoutInflater.from(CheckOut.this).inflate(R.layout.addressadpt, viewGroup,false);
                                Holder holder=new Holder(view);
                                return holder;
                            }

                            @Override
                            public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

                                final Holder myHolder= (Holder) viewHolder;
                                final AddressItem model = prodlist.get(i);

                                myHolder.name.setText(model.getCustomerName());
                                myHolder.mob.setText(model.getPhoneNumber());
                                if (model.getType() == 0){
                                    myHolder.type.setText("HOME");}else {
                                    myHolder.type.setText("WORK");
                                }                                myHolder.add.setText(model.getApartmentName()+" "+model.getLandmark()+" "+model.getCity()+" "+model.getState());
                                myHolder.zip.setText(String.valueOf(model.getZipcode()));

                                myHolder.itemView.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Addlayout.setVisibility(View.VISIBLE);
                                        Mname.setText(model.getCustomerName());
                                        Mmob.setText(model.getPhoneNumber());
                                        address_id = model.getId();
                                        if (model.getType() == 0){
                                        Mtype.setText("HOME");}else {
                                            Mtype.setText("WORK");
                                        }
                                        Madd.setText(model.getApartmentName()+" "+model.getLandmark()+" "+model.getCity()+" "+model.getState());
                                        Mzip.setText(String.valueOf(model.getZipcode()));
                                        dialog.dismiss();
                                    }
                                });

                            }

                            @Override
                            public int getItemCount() {
                                return prodlist.size();
                            }
                            class Holder extends RecyclerView.ViewHolder {

                                TextView name , mob, type , add,zip;
                                public Holder(@NonNull View itemView) {
                                    super(itemView);


                                    name = itemView.findViewById(R.id.name);
                                    mob = itemView.findViewById(R.id.mob);
                                    type = itemView.findViewById(R.id.type);
                                    add = itemView.findViewById(R.id.add);
                                    zip = itemView.findViewById(R.id.zip);



                                }
                            }
                        });


                        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                        lp.copyFrom(dialog.getWindow().getAttributes());
                        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
                        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
                        dialog.show();
                        dialog.getWindow().setAttributes(lp);


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
                Map <String,String> param = new HashMap<String,String>();
                param.put("customer_id", Global.customerid);
                return param;
            }
        };
        AppController.getInstance().addToRequestQueue(stringRequest);
    }

    private void callInstamojoPay(String email, String phone, String amount, String purpose, String buyername) {
        final Activity activity = this;
        InstamojoPay instamojoPay = new InstamojoPay();
        IntentFilter filter = new IntentFilter("ai.devsupport.instamojo");
        registerReceiver(instamojoPay, filter);
        JSONObject pay = new JSONObject();
        try {
            pay.put("email", email);
            pay.put("phone", phone);
            pay.put("purpose", purpose);
            pay.put("amount", amount);
            pay.put("name", buyername);
            pay.put("send_sms", true);
            pay.put("send_email", true);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        initListener();
        instamojoPay.start(activity, pay, listener);
    }

    InstapayListener listener;


    private void initListener() {
        listener = new InstapayListener() {
            @Override
            public void onSuccess(String response) {
                {
                    //   Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();
                    Log.d("responce",response);

                    String[] s = response.split(":");
                    Status    =  s[0].substring(s[0].indexOf("=")+1);
                    orderid    =  s[1].substring(s[1].indexOf("=")+1);
                    txnid      = s[2].substring(s[2].indexOf("=")+1);
                    paymentid  = s[3].substring(s[3].indexOf("=")+1);
                    token      = s[4].substring(s[4].indexOf("=")+1);

                    if (aBoolean){
                        aBoolean=false;
                        Order(orderid , txnid , paymentid , token, Status);}
                }
            }

            @Override
            public void onFailure(int code, String reason) {
                Toast.makeText(getApplicationContext(), "Failed: " + reason, Toast.LENGTH_LONG).show();
            }
        };
    }

    private void Order(final String orderid, String txnid, String paymentid, String token, String status) {
        progressDialog.show();
        AppController.getInstance().addToRequestQueue(new StringRequest(StringRequest.Method.POST, APIs.order_placed, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                try {
                    JSONObject jsonObject = new JSONObject(response);

                    if (jsonObject.getBoolean("status")){
                        Global.successDilogue(CheckOut.this,jsonObject.getString("result"));
                    }else {
                        Global.failedDilogue(CheckOut.this,jsonObject.getString("result"));
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                if (error == null || error.networkResponse == null) {
                    return;
                }

                String body;
                //get status code here
                final String statusCode = String.valueOf(error.networkResponse.statusCode);
                //get response body and parse with appropriate encoding
                try {
                    body = new String(error.networkResponse.data,"UTF-8");
                    Log.d("Multi",body);
                } catch (UnsupportedEncodingException e) {
                    // exception
                }

                //do stuff with the body...
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> hashMap = new HashMap<>();
                hashMap.put("customer_id"    , Global.customerid);
                hashMap.put("amount"    , String.valueOf(grandTotal));
                hashMap.put("order_id"    , orderid);
                hashMap.put("address_id"    , String.valueOf(address_id));

                return hashMap;            }
        });

    }


}
