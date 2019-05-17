package com.digital.gnsbook.Adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chahinem.pageindicator.PageIndicator;
import com.digital.gnsbook.Activity.Companypage;
import com.digital.gnsbook.Activity.ProductDetail;
import com.digital.gnsbook.Config.APIs;
import com.digital.gnsbook.Config.DbHelper;
import com.digital.gnsbook.Model.Activity_Gstore.Result;
import com.digital.gnsbook.Model.TimeLine_Model.TimeLineItem;
import com.facebook.drawee.view.SimpleDraweeView;
import com.httpgnsbook.gnsbook.R;

import java.util.ArrayList;
import java.util.List;

public class Shop_adapter extends RecyclerView.Adapter<Shop_adapter.Holder> {


    Context context;
    private static final int POST = 0;
    private static final int PRODUCT = 1;
    private static final int SUGGESTION = 2;
    private static final int VIEW_TYPE_LOADING = 3;

    List<Result> Items = new ArrayList<>();

    public Shop_adapter(Context context, List<Result> Items) {
        this.context = context;
        this.Items = Items;
    }


    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if (viewType == PRODUCT) {
            view = LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.walladapt_product,
                    parent,
                    false
            );
        } else if (viewType == POST) {
            view = LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.walladapt_post,
                    parent,
                    false);
        }else if (viewType == VIEW_TYPE_LOADING) {
            view = LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.load_more,
                    parent,
                    false);
        } else {
            view = LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.friend_suggestion,
                    parent,
                    false);

        }


        return new Holder(view);
    }



    @Override
    public int getItemViewType(int position) {
//        int i = timeLineItems.get(position).getType();
        if(Items.size() > 0 && Items.get(position) == null){
            return VIEW_TYPE_LOADING;
        }if (Items.get(position).getType() == 1) {
            return POST;
        } else if (Items.get(position).getType() == 2) {
            return PRODUCT;
        } else {
            return SUGGESTION;
        }
    }
    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        final Result item = Items.get(position);
        if(Items.get(position) != null)
        holder.bindProduct(item, position);

    }

    @Override
    public int getItemCount() {
        return Items.size();
    }

    class Holder extends RecyclerView.ViewHolder {

        RecyclerView Overlapview, slider, frndSuggestion;
        ImageView dp, wpComment, imgPost, imgPrd, share;
        TextView likeCount,reward, prdName, prdDesc, prdPrize, likename, name, date, commentCount, textPost, title, btnText;
        CardView Buynow;
        LinearLayout productLayout, postLayout;
        PageIndicator pageIndicator;
        View prdview;
        SimpleDraweeView draweeView;

        public Holder(View view) {
            super(view);
            prdview = view;
            dp = view.findViewById(R.id.wpDP);
            frndSuggestion = view.findViewById(R.id.frndSuggestion);
            reward = view.findViewById(R.id.prdreward);

        }


        public void bindProduct(final Result postmodel, int position) {
           main(postmodel,position);
            slider = prdview.findViewById(R.id.wpImageRec);
            btnText = prdview.findViewById(R.id.btnText);
            imgPrd = prdview.findViewById(R.id.ProductImage);
            prdDesc = prdview.findViewById(R.id.prdDesc);
            prdName = prdview.findViewById(R.id.prdName);
            prdPrize = prdview.findViewById(R.id.prdPrice);
            Buynow = prdview.findViewById(R.id.prdBuy);
            pageIndicator = prdview.findViewById(R.id.pageIndicator);
            //  productLayout = prdview.findViewById(R.id.PostView);

            // productLayout.setTag(postmodel);

            prdPrize.setText("₹" + postmodel.getProductPrice());
            Buynow.setTag(postmodel);
            pageIndicator.setTag(postmodel);
            prdName.setText(postmodel.getProductName());
            prdDesc.setText(postmodel.getProductDesc());

            if (postmodel.getRewardPoints() > 0) {
                reward.setText("Reward Points : " + postmodel.getRewardPoints());
            }else {
                reward.setVisibility(View.GONE);
            }
            String[] imageArray = null;

            if (postmodel.getImages() != null) {
                imageArray = postmodel.getImages().split(",");
            }

            slider.setTag(postmodel);
            slider.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
            slider.setAdapter(new Slider(imageArray, context));

            final String[] finalImageArray = imageArray;

            if (postmodel.getSellType() == 1) {
                btnText.setText("Shop Now");
            } else {
                btnText.setText("Buy Now");
            }
            Buynow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //  String url = "http://www.example.com";
                    if (postmodel.getSellType() == 1) {

                        Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(postmodel.getProductLink()));
                        context.startActivity(i);
                    } else {
                        Bundle bundle = new Bundle();
                        bundle.putStringArray("images", finalImageArray);
                        Intent intent = new Intent(context, ProductDetail.class);
                        intent.putExtra("product_name", postmodel.getProductName());
                        intent.putExtra("product_cat", postmodel.getProductCat());
                        intent.putExtra("product_desc", postmodel.getProductDesc());
                        intent.putExtra("product_link", postmodel.getProductLink());
                        intent.putExtra("product_price",String.valueOf(postmodel.getProductPrice()));
                        intent.putExtra("id", String.valueOf(postmodel.getId()));
                        intent.putExtras(bundle);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);
                    }


                }
            });


        }


        public void main(final Result item, final int position) {
            name.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    context.startActivity(new Intent(context, Companypage.class).putExtra(DbHelper.COLUMN_ID, item.getCompanyId()));
                }
            });
            dp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    context.startActivity(new Intent(context, Companypage.class).putExtra(DbHelper.COLUMN_ID, item.getCompanyId()));
                }
            });

            name.setText(item.getName());
            date.setText(item.getCreatedAt());

            // Picasso.get().load(APIs.Dp + item.getLogo()).into(holder.dp);

            Glide.with(context).load(APIs.Dp + item.getLogo()).into(dp);

        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }









}
