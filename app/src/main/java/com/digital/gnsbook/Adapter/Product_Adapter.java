package com.digital.gnsbook.Adapter;


import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.chahinem.pageindicator.PageIndicator;
import com.digital.gnsbook.Activity.Comment;
import com.digital.gnsbook.Activity.Companypage;
import com.digital.gnsbook.Activity.ProductDetail;
import com.digital.gnsbook.Config.APIs;
import com.digital.gnsbook.Config.AppController;
import com.digital.gnsbook.Config.DbHelper;
import com.digital.gnsbook.Extra.ImageLoader;
import com.digital.gnsbook.Firebase.Broadcast_FCM;
import com.digital.gnsbook.Global;
import com.digital.gnsbook.Model.Activity_Gstore.Result;
import com.digital.gnsbook.Model.WallPostmodel;
import com.digital.gnsbook.Payment.OverlapDecoration;
import com.digital.gnsbook.Extra.RoundedCornersTransformation;
import com.digital.gnsbook.Store.ProductPage;
import com.httpgnsbook.gnsbook.R;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Product_Adapter extends Adapter<ViewHolder> {
    Context context;
    List<Result> postmodels =new ArrayList<>();
    ImageLoader imageLoader;
    static class Holder extends ViewHolder {
        ImageView imgPrd;
        TextView  prdName  ,prdPrize;

        public Holder(@NonNull View view) {
            super(view);

            imgPrd          = view.findViewById(R.id.ProductImage);
            prdName         = view.findViewById(R.id.prdName);
            prdPrize        = view.findViewById(R.id.prdAmount);
        }
    }



    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return postmodels.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public Product_Adapter(List<Result> arrayList, Context context) {
        this.postmodels = arrayList;
        this.context = context;
        setHasStableIds(true);
        imageLoader = new ImageLoader(context);
    }

    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new Holder(LayoutInflater.from(this.context).inflate(R.layout.product_card, viewGroup, false));
    }

    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {

        final Holder holder = (Holder) viewHolder;
        final Result postmodel = postmodels.get(i);
        String [] imageArray = null;
        if (postmodel.getImages()!=null){
            imageArray  = postmodel.getImages().split(",");
            Glide.with(context).load(APIs.Dp+imageArray[0].replace(" ","")).into(holder.imgPrd);
        }


        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        try {
            // JSON here
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }



        holder.prdPrize.setText("₹"+postmodel.getProductPrice());
        holder.prdName.setText(postmodel.getProductName());



        final String[] finalImageArray = imageArray;
        holder.itemView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                //  String url = "http://www.example.com";

                if (postmodel.getSellType()==1){
                    Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(postmodel.getProductLink()));
                    context.startActivity(i);
                }else {
                    Bundle bundle = new Bundle();
                    bundle.putStringArray("images", finalImageArray);
                    Intent intent = new Intent(context, ProductPage.class);
                    intent.putExtra("product_name", postmodel.getProductName());
                    intent.putExtra("product_cat", postmodel.getProductCat());
                    intent.putExtra("product_desc", postmodel.getProductDesc());
                    intent.putExtra("product_link", postmodel.getProductLink());
                    intent.putExtra("product_price", postmodel.getProductPrice());
                    intent.putExtra("id", postmodel.getId());
                    intent.putExtras(bundle);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }


            }
        });


      /*  if (postmodel.reward>0){
            holder.prdreward.setVisibility(View.VISIBLE);
            holder.prdreward.setText("Reward Points : "+postmodel.reward);
        }*/


        final String[] finalImageArray1 = imageArray;




        RoundedCornersTransformation roundedCornersTransformation = new RoundedCornersTransformation(15, 15);


       // Picasso.get().load(APIs.postImg + postmodel.images).fit().into(holder.imgPost);
    }

}
