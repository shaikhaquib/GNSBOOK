package com.digital.gnsbook;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.support.v7.widget.SnapHelper;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;
import com.digital.gnsbook.Activity.Comment;
import com.digital.gnsbook.Activity.Companypage;
import com.digital.gnsbook.Activity.Compony_list;
import com.digital.gnsbook.Activity.ProductDetail;
import com.digital.gnsbook.Config.APIs;
import com.digital.gnsbook.Config.AppController;
import com.digital.gnsbook.Config.DbHelper;
import com.digital.gnsbook.Model.WallPostmodel;
import com.digital.gnsbook.Payment.OverlapDecoration;
import com.httpgnsbook.gnsbook.R;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class New_WallPostAdapt extends Adapter<ViewHolder> {
    Context context;
    ArrayList<WallPostmodel> postmodels;
    class Holder extends ViewHolder {
        CheckBox BtnLike ;
        RecyclerView Overlapview , slider;
        TextView date, commentCount;
        ImageView dp,wpComment;
        ImageView imgPost ,imgPrd;
        TextView likeCount , prdName ,prdDesc ,prdPrize;
        TextView likename;
        TextView name;
        ImageView share;
        TextView textPost;
        TextView title;
        CardView Buynow;
        LinearLayout productLayout , postLayout;

        public Holder(@NonNull View view) {
            super(view);
            dp = (ImageView) view.findViewById(R.id.wpDP);
            slider = view.findViewById(R.id.wpImageRec);
            imgPost = (ImageView) view.findViewById(R.id.wpImage);
            imgPrd = (ImageView) view.findViewById(R.id.ProductImage);
            share = (ImageView) view.findViewById(R.id.wpShare);
            name = (TextView) view.findViewById(R.id.wpcname);
            date = (TextView) view.findViewById(R.id.wpDate);
            textPost = (TextView) view.findViewById(R.id.wpText);
            title = (TextView) view.findViewById(R.id.wpTexttitile);
            Overlapview = (RecyclerView) view.findViewById(R.id.likeOverlapingImages);
            likeCount = (TextView) view.findViewById(R.id.likeCount);
            likename = (TextView) view.findViewById(R.id.nameLike);
            BtnLike = (CheckBox) view.findViewById(R.id.like);
            commentCount = view.findViewById(R.id.CommentCount);
            wpComment = view.findViewById(R.id.wpComment);
            productLayout = view.findViewById(R.id.PostView);
            postLayout = view.findViewById(R.id.prdPostView);
            prdDesc = view.findViewById(R.id.prdDesc);
            prdName = view.findViewById(R.id.prdName);
            prdPrize = view.findViewById(R.id.prdPrice);
            Buynow = view.findViewById(R.id.prdBuy);
        }
    }



    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public New_WallPostAdapt(ArrayList<WallPostmodel> arrayList, Context context) {
        this.postmodels = arrayList;
        this.context = context;
        setHasStableIds(true);
    }

    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new Holder(LayoutInflater.from(this.context).inflate(R.layout.wallpostadapter, viewGroup, false));
    }

    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

        final Holder holder = (Holder) viewHolder;
        final WallPostmodel postmodel = postmodels.get(i);
        String [] imageArray = null;
        if (postmodel.images!=null){
        imageArray  = postmodel.images.split(",");}

        holder.name.setText(postmodel.name);
        holder.date.setText(postmodel.created_at);
        holder.textPost.setText(postmodel.description);
        holder.share.setTag(postmodel);
        holder.postLayout.setTag(postmodel);
        holder.productLayout.setTag(postmodel);
        holder.wpComment.setTag(postmodel);
        holder.title.setText(postmodel.title);
        holder.prdPrize.setText("₹"+postmodel.product_price);
        holder.Buynow.setTag(postmodel);
        holder.prdName.setText(postmodel.product_name);
        holder.prdDesc.setText(postmodel.product_desc);
        holder.likeCount.setText(String.valueOf(postmodel.likecount));
        holder.commentCount.setText(String.valueOf(postmodel.commentCount));
        holder.likename.setTag(postmodel);
        getText(postmodel, holder.likename);

        holder.name.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, Companypage.class).putExtra(DbHelper.COLUMN_ID, postmodel.company_id));
            }
        });
        holder.dp.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, Companypage.class).putExtra(DbHelper.COLUMN_ID, postmodel.company_id));
            }
        });


        final String[] finalImageArray = imageArray;
        holder.Buynow.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
              //  String url = "http://www.example.com";

                if (postmodel.sell_type==1){
                Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(postmodel.product_link));
                context.startActivity(i);
                }else {
                    Bundle bundle = new Bundle();
                    bundle.putStringArray("images", finalImageArray);
                    Intent intent = new Intent(context, ProductDetail.class);
                    intent.putExtra("product_name", postmodel.product_name);
                    intent.putExtra("product_cat", postmodel.product_cat);
                    intent.putExtra("product_desc", postmodel.product_desc);
                    intent.putExtra("product_link", postmodel.product_link);
                    intent.putExtra("product_price", postmodel.product_price);
                    intent.putExtra("id", postmodel.id);
                    intent.putExtras(bundle);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }


            }
        });

        if (postmodel.type.equals("1")){
            holder. productLayout.setVisibility(View.VISIBLE);
            holder.postLayout.setVisibility(View.GONE);
        }else {
            holder.postLayout.setVisibility(View.VISIBLE);
            holder. productLayout.setVisibility(View.GONE);
        }


        holder.share.setOnClickListener(new View.OnClickListener() {@Override
        public void onClick(View v) {
            String shareBody = postmodel.title;
            Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
            sharingIntent.setType("text/plain");
            sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, shareBody);
            sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, postmodel.description);
            context.startActivity(Intent.createChooser(sharingIntent, "https://www.gnsbook.com"));
        }
        });



        RoundedCornersTransformation roundedCornersTransformation = new RoundedCornersTransformation(15, 15);

        Picasso.get().load(APIs.Dp + postmodel.logo).into(holder.dp);
        Picasso.get().load(APIs.postImg + postmodel.images).into(holder.imgPost);
        Picasso.get().load(APIs.postImg + postmodel.images).into(holder.imgPrd);


        holder.wpComment.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, Comment.class).putExtra("pid",postmodel.id).putExtra("type",postmodel.type).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
            }
        });

        holder.slider.setTag(postmodel);
        holder.slider.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false));
            holder.slider.setAdapter(new Slider(imageArray));

        holder.Overlapview.setTag(postmodel);
        holder.Overlapview.setLayoutManager(new LinearLayoutManager(this.context, 0, false));
        holder.Overlapview.addItemDecoration(new OverlapDecoration());
        holder.Overlapview.setAdapter(new OverLapAdapt(postmodel.Like_imges));

        if (postmodel.selfLike == 1) {
            holder.BtnLike.setChecked(true);
        } else {
            holder.BtnLike.setChecked(false);
        }

        holder.BtnLike.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                if (z) {

                    DoLike(postmodel.id ,postmodel.type);

                    if (postmodel.selfLike == 1) {
                        postmodel.selfLike = 0;
                        postmodel.likecount --;
                        holder.likeCount.setText(String.valueOf(postmodel.likecount));
                        getText(postmodel, holder.likename);
                    }else{
                        postmodel.selfLike = 1;
                        postmodel.likecount ++;
                        holder.likeCount.setText(String.valueOf(postmodel.likecount));
                        getText(postmodel, holder.likename);
                        }
                } else if (postmodel.selfLike == 1) {

                    UnLike(postmodel.id,postmodel.type);

                    postmodel.selfLike = 0;
                    postmodel.likecount --;
                    holder.likeCount.setText(String.valueOf(postmodel.likecount));
                    getText(postmodel, holder.likename);
                } else {
                    postmodel.selfLike = 1;
                    postmodel.likecount ++;
                    holder.likeCount.setText(String.valueOf(postmodel.likecount));
                    getText(postmodel, holder.likename);
                }
            }


            private void DoLike(final String id, final String type) {

                StringRequest stringRequest = new StringRequest(StringRequest.Method.POST, APIs.Dolike, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("DoLike : ",response);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("DoLike : ","ERROR");
                    }
                }){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map <String,String> param = new HashMap<String,String>();
                        param.put("customer_id",Global.customerid);
                        param.put("post_id",id);
                        param.put("type",type);
                        return param;
                    }
                };
                AppController.getInstance().addToRequestQueue(stringRequest);
            }
        });
    }

    private void UnLike(final String id, final String type) {

        StringRequest stringRequest = new StringRequest(StringRequest.Method.POST, APIs.Unlike, new Response.Listener<String>() {
            public void onResponse(String response) {
                Log.d("UnLike : ",response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("UnLike : ","ERROR");
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map <String,String> param = new HashMap<String,String>();
                param.put("customer_id",Global.customerid);
                param.put("post_id",id);
                param.put("type",type);
                return param;
            }
        };
        AppController.getInstance().addToRequestQueue(stringRequest);
    }



    private String getText(WallPostmodel wallPostmodel, TextView textView) {
        if (wallPostmodel.Like_name.length == 1) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(wallPostmodel.Like_name[0]);
            stringBuilder.append("<br> like.");
            Spanned spanned = Html.fromHtml(stringBuilder.toString());
            textView.setText(spanned);
        } else if (wallPostmodel.Like_name.length == 2) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(wallPostmodel.Like_name[0]);
            stringBuilder.append("& <br>one+ like.");
            Spanned spanned = Html.fromHtml(stringBuilder.toString());
            textView.setText(spanned);
        } else if (wallPostmodel.Like_name.length <= 2) {
            textView.setVisibility(View.GONE);
            //  wallPostmodel = null;
        } else if (wallPostmodel.likecount > 2) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(wallPostmodel.Like_name[0]);
            stringBuilder.append(" and <br>");
            stringBuilder.append(wallPostmodel.likecount - 1);
            stringBuilder.append("+ like.");
            Spanned spanned = Html.fromHtml(stringBuilder.toString());
            textView.setText(spanned);        } else {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(wallPostmodel.Like_name[0]);
            stringBuilder.append("& <br>one+ like.");
            Spanned spanned = Html.fromHtml(stringBuilder.toString());
            textView.setText(spanned);
        }
        return String.valueOf(wallPostmodel);
    }

    public int getItemCount() {
        return this.postmodels.size();
    }

    public void Update() {
        notifyItemInserted(this.postmodels.size() - 1);
    }



    private class OverLapAdapt extends Adapter<ViewHolder> {
        String[] Images;

        public class LikeHolder extends ViewHolder {
            ImageView imageView;

            public LikeHolder(@NonNull View view) {
                super(view);
                imageView = (ImageView) view.findViewById(R.id.ovimage);
            }
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public int getItemViewType(int position) {
            return position;
        }

        public OverLapAdapt(String[] strArr) {
            this.Images = strArr;
        }

        @NonNull
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            return new LikeHolder(LayoutInflater.from(New_WallPostAdapt.this.context).inflate(R.layout.circular_image, viewGroup, false));
        }

        public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
            LikeHolder likeHolder = (LikeHolder) viewHolder;
            Picasso picasso = Picasso.get();
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(APIs.Dp);
            stringBuilder.append(this.Images[i]);
            picasso.load(stringBuilder.toString()).into(likeHolder.imageView);
        }

        public int getItemCount() {
            if (this.Images.length > 2) {
                return 2;
            }
            return this.Images.length;
        }
    }
    private class Slider extends Adapter<ViewHolder> {
        String[] imageArray;
        public Slider(String[] imageArray) {
            this.imageArray = imageArray;

        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(context).inflate(R.layout.imageslider,viewGroup,false);
            return new Holder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
            Holder holder = (Holder)viewHolder;

            Glide.with(context).load(APIs.Dp+imageArray[i].replace(" ","")).into(holder.Slide);
            Log.d("Position "+i , APIs.Dp+imageArray[i]);
        }

        @Override
        public int getItemCount() {
            return imageArray.length;
        }

        public long getItemId(int i) {
            return (long) i;
        }


        class Holder extends RecyclerView.ViewHolder {
            ImageView Slide;
            public Holder(@NonNull View itemView) {
                super(itemView);
                Slide = itemView.findViewById(R.id.imageView);
            }
        }
    }

}
