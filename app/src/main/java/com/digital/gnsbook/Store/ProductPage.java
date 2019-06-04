package com.digital.gnsbook.Store;

import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.digital.gnsbook.Activity.ProductDetail;
import com.digital.gnsbook.Config.APIs;
import com.httpgnsbook.gnsbook.R;
import com.squareup.picasso.Picasso;

public class ProductPage extends AppCompatActivity implements OnItemClick{

    String product_name ;
    String product_cat  ;
    String product_price;
    String product_desc ;
    String product_link ;
    String[] images ;
    String id;
    String[] imagearray ;
    CatalogueAdapter adapter;

    RecyclerView Catalogue;
    ImageView preview;
    TextView prdDesc,prdAmount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_productpage);


        Catalogue= findViewById(R.id.cateloge);
        prdDesc= findViewById(R.id.prdDesc);
        prdAmount= findViewById(R.id.prdAmount);
        preview= findViewById(R.id.ImageProduct);
        Catalogue.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        getDataIntentData();
       // adapter = new CatalogueAdapter(this, imagearray);
        Catalogue.setAdapter(new CatalogueAdapter(ProductPage.this, imagearray,this));

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

        setTitle(product_name);
        prdAmount.setText("₹ "+product_price);
        prdDesc.setText(product_desc);

    }

    @Override
    public void onClick(String value) {
        Picasso.get().load(value).into(preview);

    }
}
