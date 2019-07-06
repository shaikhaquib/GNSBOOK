package com.digital.gnsbook.Activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;
import com.digital.gnsbook.Config.APIs;
import com.digital.gnsbook.Config.AppController;
import com.digital.gnsbook.Global;
import com.digital.gnsbook.Model.Product_model.Company_order;
import com.httpgnsbook.gnsbook.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Orderd_Detail extends AppCompatActivity {


    ImageView img, plus, minus;
    TextView prdname, prddesc, prdwtunit, prdprice, prodqty, type, add, orderstatus,name,conatct;
    CardView cardstatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orderd__detail);



        img = findViewById(R.id.product_img);
        name = findViewById(R.id.name);
        conatct = findViewById(R.id.conatct);
        prdname = findViewById(R.id.product_name);
        prddesc = findViewById(R.id.product_desc);
        prdwtunit = findViewById(R.id.product_qnt);
        cardstatus = findViewById(R.id.status);
        prdprice = findViewById(R.id.price);
        type = findViewById(R.id.type);
        add = findViewById(R.id.add);
        orderstatus = findViewById(R.id.orderstatus);

        Intent i = getIntent();
        Bundle bundle = i.getExtras();
        Company_order.Result model = (Company_order.Result) bundle.getSerializable("user");


        setTitle(model.getProductName());

        String[] images = model.getImages().split(",");
        prdwtunit.setText("Quantity: "+model.getQuantity());
        name.setText(model.getCustomerName());
        Glide.with(Orderd_Detail.this).load(APIs.Dp + images[0]).into(img);
        prdname.setText(model.getProductName());
        prddesc.setText(model.getProductDesc());
        prdprice.setText("₹ " + model.getAmount());
        if (model.getType() == 0) {
            type.setText("HOME");
        } else {
            type.setText("WORK");
        }
        add.setText(model.getApartmentName() + " " + model.getLandmark() + " " + model.getCity() + " "+model.getZipcode() + " " + model.getState());
        conatct.setText(model.getPhoneNumber());

        if (model.getOrderStatus() == 1) {
            orderstatus.setText("In cart");
            cardstatus.setCardBackgroundColor(Color.parseColor("#60a9a6"));

        } else if (model.getOrderStatus() == 3) {
            orderstatus.setText("Ordered");
            cardstatus.setCardBackgroundColor(Color.parseColor("#60a9a6"));

        } else if (model.getOrderStatus() == 4) {
            orderstatus.setText("Shipped");
            cardstatus.setCardBackgroundColor(Color.parseColor("#ff8300"));

        } else if (model.getOrderStatus() == 5) {
            orderstatus.setText("Delivered");
            cardstatus.setCardBackgroundColor(Color.parseColor("#58b368"));

        } else if (model.getOrderStatus() == -10) {
            orderstatus.setText("Cancelled");
            cardstatus.setCardBackgroundColor(Color.parseColor("#58b368"));

        }

    }

    public void order_detail(View view) {
        int id = view.getId();

        if (id == R.id.add_link){
            addlink();
        }else if (id == R.id.add_status){
            addStatus();
        }
    }

    private void addStatus() {
        AlertDialog.Builder builder = new AlertDialog.Builder(Orderd_Detail.this);
        builder.setTitle("Select Order status");

// add a radio button list
        String[] animals = {"Ordered", "Shipped", "Delivered"};
        int checkedItem = 1; // cow
        builder.setSingleChoiceItems(animals, checkedItem, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // user checked an item
            }
        });

// add OK and Cancel buttons
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // user clicked OK
            }
        });
        builder.setNegativeButton("Cancel", null);

// create and show the alert dialog
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void addlink() {
        LayoutInflater inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(Orderd_Detail.this);
        View inflate = inflater.inflate(R.layout.updatepost, null);
        builder.setView(inflate);

        final EditText edtTitle  = inflate.findViewById(R.id.edtTitle);
        final EditText edtDescription = inflate.findViewById(R.id.edtDescription);
        Button btnUpdate = inflate.findViewById(R.id.btnUpdate);

        edtTitle.setHint("Tracking Id");
        edtDescription.setHint("Tracking Link");
        TextView textView = inflate.findViewById(R.id.title);
        textView.setText("Add your product tracking link and tracking id");

        final AlertDialog ad = builder.show();


        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edtTitle.getText().toString().isEmpty()){
                    edtTitle.setError("Field Required");
                }else if (edtDescription.getText().toString().isEmpty()){
                    edtDescription.setError("Field Required");
                }else {
                    Addlink(edtTitle.getText().toString(),edtDescription.getText().toString());
                    ad.dismiss();
                }
            }
        });

        //   builder.create().show();




    }

    private void Addlink(final String id, final String link) {

        Toast.makeText(getApplicationContext(), "updating....", Toast.LENGTH_SHORT).show();

        AppController.getInstance().addToRequestQueue(new StringRequest(StringRequest.Method.POST, APIs.company_order_edit, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.d("responce",response);

                try {
                    JSONObject object = new JSONObject(response);

                    if (object.getBoolean("status"))
                    {
                        Toast.makeText(getApplicationContext(), "Updated Successfully", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(), "Failed Please try again after some time", Toast.LENGTH_SHORT).show();

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
                hashMap.put("tracking_id", id);
                hashMap.put("tracking_link", link);
                hashMap.put("company_id", Global.Company_Id);
                return hashMap;
            }
        });

    }



}

