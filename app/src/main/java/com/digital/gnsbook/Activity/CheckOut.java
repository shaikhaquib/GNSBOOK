package com.digital.gnsbook.Activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.digital.gnsbook.Config.APIs;
import com.digital.gnsbook.Config.AppController;
import com.digital.gnsbook.Extra.DividerDecorator;
import com.digital.gnsbook.Global;
import com.digital.gnsbook.Model.AddressItem;
import com.digital.gnsbook.Model.AddressResponse;
import com.digital.gnsbook.ViewDialog;
import com.google.gson.Gson;
import com.httpgnsbook.gnsbook.R;
import com.razorpay.Checkout;
import com.razorpay.PaymentData;
import com.razorpay.PaymentResultListener;
import com.razorpay.PaymentResultWithDataListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import instamojo.library.InstamojoPay;
import instamojo.library.InstapayListener;

public class CheckOut extends AppCompatActivity implements PaymentResultWithDataListener {

    TextView totelItem, Totalamount, ShipingCharges, PayAbleAmoumt;
    int Amount, count, shipingCharges = 0, grandTotal, address_id = -1;
    int from; //This must be declared as global !
    ViewDialog progressDialog;
    LinearLayout Addlayout;
    List<AddressItem> prodlist = new ArrayList<>();

    TextView Mtype, Madd;

    Button Order;
    String[] choice;
    int unique_id = (int) ((new Date().getTime() / 1000L) % Integer.MAX_VALUE);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);


        progressDialog = new ViewDialog(this);
        Addlayout = findViewById(R.id.Addlayout);
        totelItem = findViewById(R.id.titem);
        Totalamount = findViewById(R.id.total);
        ShipingCharges = findViewById(R.id.shipcost);
        PayAbleAmoumt = findViewById(R.id.stotal);
        Order = findViewById(R.id.Next);

        Mtype = findViewById(R.id.type);
        Madd = findViewById(R.id.add);


      /*  Mname = findViewById(R.id.name);
        Mmob = findViewById(R.id.mob);
        Mzip = findViewById(R.id.zip);*/

        Amount = getIntent().getIntExtra("Amount", 0);
        count = getIntent().getIntExtra("count", 0);
        grandTotal = Amount + shipingCharges;
        totelItem.setText(String.valueOf(count));
        Totalamount.setText(String.valueOf(Amount));
        ShipingCharges.setText(String.valueOf(shipingCharges));
        PayAbleAmoumt.setText(String.valueOf(grandTotal));
        Order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (address_id !=-1)
                        genrrateOrder();
                else
                    Toast.makeText(CheckOut.this, "You have not added Shipping address Please first Add Shipping Address...", Toast.LENGTH_SHORT).show();

               /* if (Mname.getText().toString().isEmpty())
                {
                    Toast.makeText(CheckOut.this, "Please Select Shipping Address...", Toast.LENGTH_SHORT).show();
                }else {

                }*/
            }
        });


        getData();
    }

    private void genrrateOrder() {
        progressDialog.show();

        AppController.getInstance().addToRequestQueue(new StringRequest(StringRequest.Method.POST, APIs.genrate_order, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                try {
                    JSONObject jsonObject = new JSONObject(response);

                    if (jsonObject.getBoolean("status")) {
                        startPayment(Global.Email, Global.mobile, String.valueOf(grandTotal), "Online Shoping", Global.name, jsonObject.getString("result"));
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
                    body = new String(error.networkResponse.data, "UTF-8");
                    Log.d("Multi", body);
                } catch (UnsupportedEncodingException e) {
                    // exception
                }
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> hashMap = new HashMap<>();
                hashMap.put("customer_id", Global.customerid);
                hashMap.put("amount", String.valueOf(grandTotal));
                hashMap.put("cart_id", getIntent().getStringExtra("cart_id"));
                hashMap.put("address_id", String.valueOf(address_id));

                return hashMap;
            }
        });

    }

    public void SelectAddress(View view) {

        if (view.getId() == R.id.addAddress) {
            startActivity(new Intent(getApplicationContext(), Add_Address.class));
        } else {

            if (address_id !=-1)
                address_list();
            else
                Toast.makeText(CheckOut.this, "You have not added Shipping address Please first Add Shipping Address...", Toast.LENGTH_SHORT).show();
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

                    if (jsonObject.getBoolean("status")) {
                        Gson gson = new Gson();
                        AddressResponse res = gson.fromJson(response, AddressResponse.class);
                        prodlist = res.getResult();

                        if (prodlist.size() > 0){

                            Addlayout.setVisibility(View.VISIBLE);
                            //   Mname.setText(model.getCustomerName());
                            //   Mmob.setText(model.getPhoneNumber());
                            address_id = prodlist.get(0).getId();
                            if (prodlist.get(0).getType() == 0) {
                                Mtype.setText("HOME");
                            } else {
                                Mtype.setText("WORK");
                            }
                            Madd.setText(prodlist.get(0).getApartmentName() + " " + prodlist.get(0).getLandmark() + " " + prodlist.get(0).getCity() + " " + String.valueOf(prodlist.get(0).getZipcode()) + " " + prodlist.get(0).getState());
                            //    Mzip.setText(String.valueOf(model.getZipcode()));
                        }


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
                Map<String, String> param = new HashMap<String, String>();
                param.put("customer_id", Global.customerid);
                return param;
            }
        };
        AppController.getInstance().addToRequestQueue(stringRequest);
    }


    InstapayListener listener;


    public void address_list() {

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
                View view = LayoutInflater.from(CheckOut.this).inflate(R.layout.addressadpt, viewGroup, false);
                Holder holder = new Holder(view);
                return holder;
            }

            @Override
            public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

                final Holder myHolder = (Holder) viewHolder;
                final AddressItem model = prodlist.get(i);

                myHolder.name.setText(model.getCustomerName());
                myHolder.mob.setText(model.getPhoneNumber());
                if (model.getType() == 0) {
                    myHolder.type.setText("HOME");
                } else {
                    myHolder.type.setText("WORK");
                }
                myHolder.add.setText(model.getApartmentName() + " " + model.getLandmark() + " " + model.getCity() + " " + model.getState());
                myHolder.zip.setText(String.valueOf(model.getZipcode()));

                myHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Addlayout.setVisibility(View.VISIBLE);
                        //   Mname.setText(model.getCustomerName());
                        //   Mmob.setText(model.getPhoneNumber());
                        address_id = model.getId();
                        if (model.getType() == 0) {
                            Mtype.setText("HOME");
                        } else {
                            Mtype.setText("WORK");
                        }
                        Madd.setText(model.getApartmentName() + " " + model.getLandmark() + " " + model.getCity() + " " + String.valueOf(model.getZipcode()) + " " + model.getState());
                        //    Mzip.setText(String.valueOf(model.getZipcode()));
                        dialog.dismiss();
                    }
                });

            }

            @Override
            public int getItemCount() {
                return prodlist.size();
            }

            class Holder extends RecyclerView.ViewHolder {

                TextView name, mob, type, add, zip;

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


    public void startPayment(String email, String phone, String amount, String purpose, String buyername, String order_id) {
        Checkout checkout = new Checkout();

        checkout.setImage(R.drawable.gnsbooklogo);

        final Activity activity = this;

        try {
            JSONObject options = new JSONObject();

            /**
             * Merchant Name
             * eg: ACME Corp || HasGeek etc.
             */
            options.put("name", "Merchant Name");
            /**
             * Description can be anything
             * eg: Order #123123
             *     Invoice Payment
             *     etc.
             */
            options.put("order_id", order_id);
            options.put("merchant_order_id", Global.customerid);
            options.put("currency", "INR");

            JSONObject preFill = new JSONObject();
            preFill.put("email", Global.Email);
            preFill.put("contact", Global.mobile);
            options.put("prefill", preFill);


            JSONObject notes = new JSONObject();
            notes.put("merchant_order_id", Global.customerid);
            options.put("notes", notes);


            /**
             * Amount is always passed in PAISE
             * Eg: "500" = Rs 5.00
             */

            int amount_in_paise = Integer.parseInt(amount) * 100;
            options.put("amount", amount_in_paise);

            checkout.open(activity, options);
        } catch (Exception e) {
            Log.e("Razorpay Error", "Error in starting Razorpay Checkout", e);
        }
    }

   /* @Override
    public void onPaymentError(int i,final String s) {
        Log.d("Razorpay Error",  s);

    }*/

    private void Order(final String paymentId, final String signature, final String orderId) {
        progressDialog.show();
        AppController.getInstance().addToRequestQueue(new StringRequest(StringRequest.Method.POST, APIs.razorPaySuccess, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                try {
                    JSONObject jsonObject = new JSONObject(response);

                    if (jsonObject.getBoolean("status")) {
                        Global.successDilogue(CheckOut.this, jsonObject.getString("result"));
                    } else {
                        Global.failedDilogue(CheckOut.this, jsonObject.getString("result"));
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
                    body = new String(error.networkResponse.data, "UTF-8");
                    Log.d("Multi", body);
                } catch (UnsupportedEncodingException e) {
                    // exception
                }

                //do stuff with the body...
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> hashMap = new HashMap<>();
                hashMap.put("customer_id", Global.customerid);
                hashMap.put("signature", signature);
                hashMap.put("order_id", orderId);
                hashMap.put("payment_id", paymentId);

                return hashMap;
            }
        });
    }

    @Override
    public void onPaymentSuccess(String s, PaymentData data) {
        String paymentId = data.getPaymentId();
        String signature = data.getSignature();
        String orderId = data.getOrderId();

        Order(paymentId, signature, orderId);




       /* Signature generated_signature = hmac_sha256(orderId + "|" + paymentId, secret);

        if (generated_signature == razorpay_signature) {
            payment is successful
        }*/
    }

    @Override
    public void onPaymentError(int i, String s, PaymentData paymentData) {

    }
}
