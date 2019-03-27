package com.digital.gnsbook.Activity;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;
import com.digital.gnsbook.Config.APIs;
import com.digital.gnsbook.Config.AppController;
import com.digital.gnsbook.Global;
import com.digital.gnsbook.ViewDialog;
import com.httpgnsbook.gnsbook.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductDetail extends AppCompatActivity {
    TextView title , desription,description2 , wtut,price ,quantity ;
    ImageView minus ,plus;
    String minimum_quantity,amount ,unit ,s ;
    String[] imagearray ;
    Button addtocart;
    int count = 0,wt ;
    int minteger = 1;
    ViewPager pager;
    ViewDialog progressDialog;

    String product_name ;
    String product_cat  ;
    String product_price;
    String product_desc ;
    String product_link ;
    String[] images ;
    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        progressDialog=new ViewDialog(ProductDetail.this);
        pager =   findViewById(R.id.photos_viewpager);
        quantity = findViewById(R.id.qty);
        minus =findViewById(R.id.minus);
        plus = findViewById(R.id.plus);
        title =findViewById(R.id.pname);
        addtocart =findViewById(R.id.addtocart);
        desription = findViewById(R.id.compname);
        description2=findViewById(R.id.descpn);
        wtut=findViewById(R.id.wtut);
        quantity.setText(String.valueOf(minteger));
        price=findViewById(R.id.pprice);


        getDataIntentData();

        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(minteger<99) {
                    minteger = minteger + 1;
                    quantity.setText(String.valueOf(minteger));
                    int amt = Integer.parseInt(amount);
                    int cal2 = amt * minteger;
                    int cal3 = wt * minteger;
                    s = String.valueOf(cal2);
                    // calculatedprice.setText(s);
                    amount = s;
                    price.setText("Price:  ₹ "+s);
                  //  wtut.setText(cal3 +" "+ unit);
                    //pricesenttobuynow = s.trim();
                }

            }
        });
        minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(minteger>1) {
                    minteger = minteger - 1;
                    quantity.setText(String.valueOf(minteger));
                    int amt = Integer.parseInt(amount);
                    int cal2 = amt * minteger;
                    int cal3 = wt * minteger;
                    s = String.valueOf(cal2);
                    // calculatedprice.setText(s);
                    price.setText("Price:  ₹ "+s);
                    amount=s;
                  //  wtut.setText(cal3 +" "+ unit);
                    //pricesenttobuynow = s.trim();
                }
                else {
                    quantity.setText("1");
                }

            }
        });

        addtocart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddtoCart();
            }
        });
    }

    private void AddtoCart() {
    progressDialog.show();
        StringRequest stringRequest = new StringRequest(StringRequest.Method.POST, APIs.AddtoCart, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getBoolean("status")){
                       // Global.successDilogue(ProductDetail.this,jsonObject.getString("result"));
                        startActivity(new Intent(getApplicationContext(),Cart.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                        finish();
                    }else {
                        Global.failedDilogue(ProductDetail.this,jsonObject.getString("result"));
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
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map <String,String> param = new HashMap<String,String>();
                param.put("customer_id", Global.customerid);
                param.put("quantity",String.valueOf(minteger));
                param.put("amount",amount);
                param.put("product_id",id);
                return param;
            }
        };
        AppController.getInstance().addToRequestQueue(stringRequest);
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() != 16908332) {
            return super.onOptionsItemSelected(menuItem);
        }
        finish();
        return true;
    }
    private void getDataIntentData() {

        Bundle b=this.getIntent().getExtras();
        imagearray=b.getStringArray("images");


       product_name  =getIntent().getStringExtra("product_name");
       product_cat   =getIntent().getStringExtra("product_cat");
       product_price =getIntent().getStringExtra("product_price");
       product_desc  =getIntent().getStringExtra("product_desc");
       product_link  =getIntent().getStringExtra("product_link");
       id            =getIntent().getStringExtra("id");
       wtut.setText(product_link);
        setTitle(product_name);


        title.setText(product_name);
        description2.setText(product_desc);
        desription.setText(product_cat);
        quantity.setText("1");
        price.setText("Price:  ₹ "+product_price);
        amount = product_price;



        VPagerAdapter adapter = new VPagerAdapter(getApplicationContext());
        pager.setAdapter(adapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(pager, true);
        for(int i=0; i < tabLayout.getTabCount(); i++) {
            View tab = ((ViewGroup) tabLayout.getChildAt(0)).getChildAt(i);
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) tab.getLayoutParams();
            p.setMargins(0, 0, 15, 0);
            tab.requestLayout();
        }

    }


    class VPagerAdapter extends PagerAdapter {

        private Context context;
        private LayoutInflater layoutInflater;int type ;

        public VPagerAdapter(Context context) {
            this.context = context;
        }

        @Override
        public int getCount() {
            return imagearray.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, final int position) {
            View view = null;


            layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.custom_layout, null);
            ImageView imageView = (ImageView) view.findViewById(R.id.imageView);
            //imageView.setImageResource(images[position]);


            Glide.with(context).load(APIs.Dp+imagearray[position]).into(imageView);


            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                }
            });

            ViewPager vp = (ViewPager) container;
            vp.addView(view, 0);

            return view;

        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {

            ViewPager vp = (ViewPager) container;
            View view = (View) object;
            vp.removeView(view);

        }
    }

}
