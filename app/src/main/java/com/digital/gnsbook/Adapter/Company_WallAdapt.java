package com.digital.gnsbook.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
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
import com.chahinem.pageindicator.PageIndicator;
import com.digital.gnsbook.Activity.Comment;
import com.digital.gnsbook.Activity.Companypage;
import com.digital.gnsbook.Activity.New_Post;
import com.digital.gnsbook.Config.APIs;
import com.digital.gnsbook.Config.AppController;
import com.digital.gnsbook.Config.DbHelper;
import com.digital.gnsbook.Firebase.Broadcast_FCM;
import com.digital.gnsbook.Global;
import com.digital.gnsbook.Model.TimeLine_Model.CompanyTimeLineItem;
import com.digital.gnsbook.Model.TimeLine_Model.LikesItem;
import com.digital.gnsbook.Model.TimeLine_Model.Suggestion;
import com.digital.gnsbook.Model.TimeLine_Model.TimeLineItem;
import com.digital.gnsbook.Payment.OverlapDecoration;
import com.digital.gnsbook.Store.ProductPage;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.core.ImagePipeline;
import com.httpgnsbook.gnsbook.R;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Company_WallAdapt extends RecyclerView.Adapter<Company_WallAdapt.Holder> {


    private static final int TYPE_HEADER = -1;
    private static final int POST = 0;
    private static final int PRODUCT = 1;
    private static final int SUGGESTION = 2;
    private static final int VIEW_TYPE_LOADING = 3;
    boolean isLoading = false;
    Context context;
    private ResizeOptions mResizeOptions;

    Thread thread;
    Handler handler = new Handler();

    List<CompanyTimeLineItem> timeLineItems = new ArrayList<>();
    List<LikesItem> likesItems = new ArrayList<>();
    List<Suggestion> suggestions = new ArrayList<>();
    private static final String SEPARATOR = ",";

    public Company_WallAdapt(Context context, List<CompanyTimeLineItem> timeLineItems) {
        this.context = context;
        this.timeLineItems = timeLineItems;
    }

    private boolean isPositionHeader(int position) {
        return position == 0;
    }

    @NonNull
    @Override
    public Company_WallAdapt.Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
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
        } else if (viewType == VIEW_TYPE_LOADING) {
            view = LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.load_more,
                    parent,
                    false);
        } else if(viewType == TYPE_HEADER){
            view = LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.new_wallpost,
                    parent,
                    false);

        }else {
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
       if (timeLineItems.size() > 0 && timeLineItems.get(position) == null) {
            return VIEW_TYPE_LOADING;
        }else if (timeLineItems.get(position).getType() == 1 ||    timeLineItems.get(position).getType() == 4) {
            return POST;
        } else if (timeLineItems.get(position).getType() == 2) {
            return PRODUCT;
        } else {
            return SUGGESTION;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull final Holder holder, int position) {



      if (timeLineItems.get(position) != null) {
            final CompanyTimeLineItem item = timeLineItems.get(position);

            if (timeLineItems.get(position).getType() == 1) {
                holder.bindpost(timeLineItems.get(position), position);
            } else if (timeLineItems.get(position).getType() == 2) {
                holder.bindProduct(timeLineItems.get(position), position);
            }
        }
    }

    @Override
    public int getItemCount() {
        return timeLineItems.size();
    }

    class Holder extends RecyclerView.ViewHolder {

        CheckBox BtnLike;
        RecyclerView Overlapview, slider, frndSuggestion;
        ImageView dp, wpComment, imgPost, imgPrd, share,postMenu;
        TextView likeCount, reward, prdcat, prdName, prdDesc, prdPrize, likename, name, date, commentCount, textPost, title, btnText;
        CardView Buynow;
        LinearLayout productLayout, postLayout;
        PageIndicator pageIndicator;
        View prdview;
        SimpleDraweeView draweeView, LikeImage1, LikeImage2;

        public Holder(View view) {
            super(view);
            prdview = view;
            dp = view.findViewById(R.id.wpDP);
            frndSuggestion = view.findViewById(R.id.frndSuggestion);
            reward = view.findViewById(R.id.prdreward);
            draweeView = view.findViewById(R.id.my_image_view);
            postMenu = view.findViewById(R.id.postMenu);
            LikeImage1 = view.findViewById(R.id.ovimage);
            LikeImage2 = view.findViewById(R.id.ovimage1);
            share = view.findViewById(R.id.wpShare);
            name = view.findViewById(R.id.wpcname);
            date = view.findViewById(R.id.wpDate);
            textPost = view.findViewById(R.id.wpText);
            title = view.findViewById(R.id.wpTexttitile);
            Overlapview = view.findViewById(R.id.likeOverlapingImages);
            likeCount = view.findViewById(R.id.likeCount);
            likename = view.findViewById(R.id.nameLike);
            BtnLike = view.findViewById(R.id.like);
            prdcat = view.findViewById(R.id.prdcat);
            commentCount = view.findViewById(R.id.CommentCount);
            wpComment = view.findViewById(R.id.wpComment);
            postLayout = view.findViewById(R.id.prdPostView);
        }

        public void bindpost(final CompanyTimeLineItem postmodel, int position) {

            main(postmodel, position);
            textPost.setText(postmodel.getDescription());
            share.setTag(postmodel);
//            postLayout.setTag(postmodel);
            wpComment.setTag(postmodel);
            title.setText(postmodel.getTitle());

            likeCount.setText(String.valueOf(postmodel.getLikeCount()));
            commentCount.setText(String.valueOf(postmodel.getCommentCount()));
            likename.setTag(postmodel);

/*
            thread = new Thread() {
                @Override
                public void run() {
                    try {
                        while(true) {
                            sleep(1000);
                            handler.post(this);

                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            };
*/
            Uri uri = Uri.parse(APIs.postImg + postmodel.getImage());
            ImagePipeline imagePipeline = Fresco.getImagePipeline();

            draweeView.setImageURI(uri);
//            thread.start();


        }

        public void bindProduct(final CompanyTimeLineItem postmodel, int position) {
            main(postmodel, position);
            likeCount.setText(String.valueOf(postmodel.getLikeCount()));
            commentCount.setText(String.valueOf(postmodel.getCommentCount()));
            likename.setTag(postmodel);
            // slider = prdview.findViewById(R.id.wpImageRec);
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
            prdcat.setText(postmodel.getProductCat());

            if (postmodel.getRewardPoints() > 0) {
                reward.setText("Reward Points : " + postmodel.getRewardPoints());
            } else {
                reward.setVisibility(View.GONE);
            }
            String[] imageArray = null;

            if (postmodel.getImages() != null) {
                imageArray = postmodel.getImages().split(",");
            }


            Uri uri = Uri.parse(APIs.Dp + imageArray[0].replace(" ", ""));
            ImagePipeline imagePipeline = Fresco.getImagePipeline();
            draweeView.setImageURI(uri);

            final String[] finalImageArray = imageArray;

            if (postmodel.getSellType() == 1) {
                btnText.setText("Shop Now");
            } else {
                btnText.setText("Buy Now");
            }
            draweeView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //  String url = "http://www.example.com";
                    if (postmodel.getSellType() == 1) {

                        Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(postmodel.getProductLink()));
                        context.startActivity(i);
                    } else {
                        Bundle bundle = new Bundle();
                        bundle.putStringArray("images", finalImageArray);
                        Intent intent = new Intent(context, ProductPage.class);
                        intent.putExtra("product_name", postmodel.getProductName());
                        intent.putExtra("product_cat", postmodel.getProductCat());
                        intent.putExtra("product_desc", postmodel.getProductDesc());
                        intent.putExtra("product_link", postmodel.getProductLink());
                        intent.putExtra("product_price", String.valueOf(postmodel.getProductPrice()));
                        intent.putExtra("id", String.valueOf(postmodel.getId()));
                        intent.putExtras(bundle);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);
                    }

                }
            });


        }


        public void main(final CompanyTimeLineItem item, final int position) {
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

            if (position == timeLineItems.size() - 1) {
                // When last item is reached.
             //   Toast.makeText(context, "Last", Toast.LENGTH_SHORT).show();
            }

            if (item.getType()==2 && Global.Company_Admin_Id==Integer.parseInt(Global.customerid)){
                postMenu.setVisibility(View.VISIBLE);
            }

            postMenu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MenuDialoge(item);
                }
            });

            likesItems = item.getLikes();

            Overlapview.setTag(item);
            Overlapview.setLayoutManager(new LinearLayoutManager(context, 0, false));
            Overlapview.addItemDecoration(new OverlapDecoration());
            //  Overlapview.setAdapter(new OverLapAdapt(likesItems, context));
            LikeImages(likesItems);

            getText(item, likename);

            share.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new Broadcast_FCM().execute(Global.customerid, item.getTitle(), Global.name + " has Shared a post");

                    // Toast.makeText(context, "Toast", Toast.LENGTH_SHORT).show();

                    Intent whatsappIntent = new Intent(Intent.ACTION_SEND);
                    StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
                    StrictMode.setVmPolicy(builder.build());
                    if (item.getType().equals("1")) {

                        Bitmap image = getBitmapFromURL(APIs.Dp + item.getImage());

                        whatsappIntent.setType("text/plain");
                        whatsappIntent.putExtra(Intent.EXTRA_TEXT, item.getTitle() + "\n" + item.getDescription() + "\n https://www.gnsbook.com/?reffid=" + Global.customerid);
                        whatsappIntent.putExtra(Intent.EXTRA_STREAM, APIs.Dp + item.getImage());
                        whatsappIntent.setType("image/jpeg");
                        whatsappIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    } else {
                        final String[] finalImageArray1 = item.getImages().split(",");
                        Bitmap image = getBitmapFromURL(APIs.Dp + finalImageArray1[0]);
                        whatsappIntent.setType("text/plain");
                        whatsappIntent.putExtra(Intent.EXTRA_TEXT, "*NAME* :" + item.getProductName() + "\n\n" + "*Description* :" + item.getProductDesc() + "\n\n" + "*Price* : ₹" + item.getProductPrice() + "\n\n https://www.gnsbook.com/?reffid=" + Global.customerid);
                        whatsappIntent.putExtra(Intent.EXTRA_STREAM, getImageUri(context, image));
                        whatsappIntent.setType("image/jpeg");
                        whatsappIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

                    }
                    try {
                        context.startActivity(whatsappIntent);
                    } catch (android.content.ActivityNotFoundException ex) {
                        Toast.makeText(context, "Some thing went wrong...", Toast.LENGTH_SHORT).show();
                    }
                }
            });

            wpComment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    context.startActivity(new Intent(context, Comment.class).putExtra("pid", item.getId()).putExtra("type", item.getType()).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                }
            });
            if (item.getSelfLikes() == 1) {
                BtnLike.setChecked(true);
            } else {
                BtnLike.setChecked(false);
            }


            BtnLike.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                public void onCheckedChanged(CompoundButton compoundButton, boolean z) {


                    if (timeLineItems.get(position).getType() != 3 && item.getLikeCount() != null) {

                        if (z) {

                            DoLike(item.getId(), item.getType());

                            if (item.getSelfLikes() == 1) {
                                item.setSelfLikes(0);
                                item.setLikeCount(item.getLikeCount() - 1);
                                likeCount.setText(String.valueOf(item.getLikeCount()));
                                getText(item, likename);
                            } else {

                                item.setSelfLikes(1);
                                item.setLikeCount(item.getLikeCount() + 1);
                                likeCount.setText(String.valueOf(item.getLikeCount()));
                                getText(item, likename);
                            }
                        } else if (item.getSelfLikes() == 1) {

                            UnLike(item.getId(), item.getType());

                            item.setSelfLikes(0);
                            item.setLikeCount(item.getLikeCount() - 1);
                            likeCount.setText(String.valueOf(item.getLikeCount()));
                            getText(item, likename);
                        } else {
                            item.setSelfLikes(1);
                            item.setLikeCount(item.getLikeCount() + 1);
                            likeCount.setText(String.valueOf(item.getLikeCount()));
                            getText(item, likename);
                        }
                    }
                }

            });

        }

        private void LikeImages(List<LikesItem> likesItems) {

            if (likesItems.size() > 0) {
                if (likesItems.size() >= 2) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append(APIs.Dp);
                    stringBuilder.append(likesItems.get(1).getDPic());
                    LikeImage2.setImageURI(Uri.parse(String.valueOf(stringBuilder)));
                }

                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(APIs.Dp);
                stringBuilder.append(likesItems.get(0).getDPic());
                LikeImage1.setImageURI(Uri.parse(String.valueOf(stringBuilder)));

            }


        }

        public void bindHead(int position) {

            LinearLayout NewPost = itemView.findViewById(R.id.newPost);
            ImageView logo = itemView.findViewById(R.id.componyLogo);
            Picasso.get().load(APIs.Dp + Global.DP).into(logo);

            NewPost.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    context.startActivity(new Intent(context, New_Post.class).putExtra("type","1"));
                }
            });

        }

    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    public static Bitmap getBitmapFromURL(String src) {

        int SDK_INT = android.os.Build.VERSION.SDK_INT;
        if (SDK_INT > 8) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);
            //your codes here
            try {
                URL url = new URL(src);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput(true);
                connection.connect();
                InputStream input = connection.getInputStream();
                Bitmap myBitmap = BitmapFactory.decodeStream(input);
                return myBitmap;
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }

        } else {

            try {
                URL url = new URL(src);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput(true);
                connection.connect();
                InputStream input = connection.getInputStream();
                Bitmap myBitmap = BitmapFactory.decodeStream(input);
                return myBitmap;
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }
    } //


    private String getText(CompanyTimeLineItem wallPostmodel, TextView textView) {
        if (wallPostmodel.getLikes().size() == 1) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(wallPostmodel.getLikes().get(0).getName());
            stringBuilder.append("<br> like.");
            Spanned spanned = Html.fromHtml(stringBuilder.toString());
            textView.setText(spanned);
        } else if (wallPostmodel.getLikes().size() == 2) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(wallPostmodel.getLikes().get(0).getName());
            stringBuilder.append("& <br>one+ like.");
            Spanned spanned = Html.fromHtml(stringBuilder.toString());
            textView.setText(spanned);
        } else if (wallPostmodel.getLikes().size() <= 2) {
            textView.setVisibility(View.GONE);
            //  wallPostmodel = null;
        } else if (wallPostmodel.getLikes().size() > 2) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(wallPostmodel.getLikes().get(0).getName());
            stringBuilder.append(" & <br>");
            stringBuilder.append(wallPostmodel.getLikeCount() - 1);
            stringBuilder.append("+ like.");
            Spanned spanned = Html.fromHtml(stringBuilder.toString());
            textView.setText(spanned);
        } else {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(wallPostmodel.getLikes().get(0).getName());
            stringBuilder.append("& <br>one+ like.");
            Spanned spanned = Html.fromHtml(stringBuilder.toString());
            textView.setText(spanned);
        }
        return String.valueOf(wallPostmodel);
    }

    private void DoLike(final Integer id, final Integer type) {

        StringRequest stringRequest = new StringRequest(StringRequest.Method.POST, APIs.Dolike, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("DoLike : ", response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("DoLike : ", "ERROR");
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param = new HashMap<String, String>();
                param.put("customer_id", Global.customerid);
                param.put("post_id", String.valueOf(id));
                param.put("type", String.valueOf(type));
                return param;
            }
        };
        AppController.getInstance().addToRequestQueue(stringRequest);
    }

    private void UnLike(final Integer id, final Integer type) {

        StringRequest stringRequest = new StringRequest(StringRequest.Method.POST, APIs.Unlike, new Response.Listener<String>() {
            public void onResponse(String response) {
                Log.d("UnLike : ", response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("UnLike : ", "ERROR");
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param = new HashMap<String, String>();
                param.put("customer_id", Global.customerid);
                param.put("post_id", String.valueOf(id));
                param.put("type", String.valueOf(type));
                return param;
            }
        };
        AppController.getInstance().addToRequestQueue(stringRequest);
    }

    private void SuggestionAdapter(RecyclerView frndSuggestion, final List<Suggestion> Models) {

        frndSuggestion.setAdapter(new RecyclerView.Adapter() {
            @NonNull
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                return new Holder(LayoutInflater.from(context).inflate(R.layout.friendsuggestionui, viewGroup, false));
            }

            @Override
            public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int i) {
                final Holder holder = (Holder) viewHolder;
                final Suggestion model = Models.get(i);

                holder.Name.setText(model.getName() + " " + model.getLastName());
                Picasso.get().load(APIs.Dp + model.getDPic()).into(holder.dp);
                holder.frdAddfrind.setTag(model);

                holder.frdAddfrind.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Models.remove(model);
                        notifyItemRemoved(i);
                        notifyItemRangeChanged(i, Models.size());
                        addFreind(String.valueOf(model.getCustomerId()));
                    }
                });

                if (model.getCity() == null || model.getCity().isEmpty()) {
                    holder.Location.setText("India");
                } else {
                    holder.Location.setText(model.getCity());
                }
            }

            @Override
            public int getItemCount() {
                return Models.size();
            }

            class Holder extends RecyclerView.ViewHolder {
                ImageView dp;
                TextView Name, Location;
                CardView frdAddfrind;

                public Holder(@NonNull View itemView) {
                    super(itemView);
                    dp = itemView.findViewById(R.id.frdp);
                    Name = itemView.findViewById(R.id.frdName);
                    Location = itemView.findViewById(R.id.frdLocation);
                    frdAddfrind = itemView.findViewById(R.id.frdAddfrind);
                }
            }

        });
    }

    private void addFreind(final String id) {
        AppController.getInstance().addToRequestQueue(new StringRequest(1, APIs.addfriend, new Response.Listener<String>() {
            public void onResponse(String str) {

                try {
                    JSONObject jsonObject = new JSONObject(str);

                    if (jsonObject.getBoolean("status")) {
                        Toast.makeText(context, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(context, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            public void onErrorResponse(VolleyError volleyError) {
            }
        }) {
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> hashMap = new HashMap();
                hashMap.put("customerid_to", id);
                hashMap.put("customerid_from", Global.customerid);
                return hashMap;
            }
        });


    }

    private void MenuDialoge(final CompanyTimeLineItem postmodel) {
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(context);
        View inflate = inflater.inflate(R.layout.transactionmode, null);
        builder.setView(inflate);
        TextView title = (TextView) inflate.findViewById(R.id.headTitle);
        TextView textView = (TextView) inflate.findViewById(R.id.new_FundTrans);
        TextView textView2 = (TextView) inflate.findViewById(R.id.old_FundTrans);

        title.setText("What You Want");
        textView.setText("Edit Post");
        textView2.setText("Remove Post");

        final AlertDialog ad = builder.show();

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ad.dismiss();
                EditProduct(postmodel);
            }
        });
        textView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ad.dismiss();

            }
        });
    }


    private void EditProduct(final CompanyTimeLineItem postmodel) {
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(context);
        View inflate = inflater.inflate(R.layout.product_update, null);
        builder.setView(inflate);
        final EditText edtAmount  = inflate.findViewById(R.id.edtAMOUNT);
        final RecyclerView rvSlide  = inflate.findViewById(R.id.rvProduct);
        final EditText edtTitle  = inflate.findViewById(R.id.edtTitle);
        final EditText edtDescription = inflate.findViewById(R.id.edtDescription);
        Button btnUpdate = inflate.findViewById(R.id.btnUpdate);

        edtTitle.setText(postmodel.getProductName());
        edtAmount.setText(String.valueOf(postmodel.getProductPrice()));
        edtDescription.setText(postmodel.getProductDesc());

        final AlertDialog ad = builder.show();

        rvSlide.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false));
        final String[] imgArray = postmodel.getImages().split(",");
        final List<String> imgList = new ArrayList<>(Arrays.asList(imgArray));

        rvSlide.setAdapter(new RecyclerView.Adapter() {
            @NonNull
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
                View view = LayoutInflater.from(parent.getContext()).inflate(
                        R.layout.images,
                        parent,
                        false);

                return new Holder(view);
            }

            @Override
            public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int i) {
                Holder holder = (Holder)viewHolder;

                Glide.with(context).load(APIs.Dp+imgList.get(i).replace(" ","")).into(holder.image);
                holder.remove.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        imgList.remove(i);
                        StringBuilder csvBuilder = new StringBuilder();
                        for(String city : imgList){
                            csvBuilder.append(city);
                            csvBuilder.append(SEPARATOR);
                        }
                        String csv = csvBuilder.toString();
                        System.out.println(csv);
                        //OUTPUT: Milan,London,New York,San Francisco,
                        //Remove last comma
                        csv = csv.substring(0, csv.length() - SEPARATOR.length());

                        postmodel.setImages(csv);

                        notifyItemRemoved(i);

                    }
                });

            }

            @Override
            public int getItemCount() {
                return imgList.size();
            }

            class Holder extends RecyclerView.ViewHolder {
                ImageView image,remove;
                public Holder(@NonNull View itemView) {
                    super(itemView);
                    image=itemView.findViewById(R.id.image);
                    remove=itemView.findViewById(R.id.remove);
                }
            }
        });


        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edtAmount.getText().toString().isEmpty()){
                    edtAmount.setError("Field Required");
                }else if (edtTitle.getText().toString().isEmpty()){
                    edtTitle.setError("Field Required");
                }else if (edtDescription.getText().toString().isEmpty()){
                    edtDescription.setError("Field Required");
                }else {
                    postmodel.setProductPrice(Integer.valueOf(edtAmount.getText().toString()));
                    postmodel.setProductName(edtTitle.getText().toString());
                    postmodel.setDescription(edtDescription.getText().toString());
                    notifyDataSetChanged();

                    UpdateProduct(edtAmount.getText().toString(),edtTitle.getText().toString(),edtDescription.getText().toString(),postmodel.getId(),postmodel.getImages());
                    ad.dismiss();
                }
            }
        });

        //   builder.create().show();




    }

    private void UpdateProduct(final String amount, final String name, final String desc, final Integer id, final String images) {

        Toast.makeText(context, "updating....", Toast.LENGTH_SHORT).show();

        AppController.getInstance().addToRequestQueue(new StringRequest(StringRequest.Method.POST, APIs.product_update, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.d("responce",response);

                try {
                    JSONObject object = new JSONObject(response);

                    if (object.getBoolean("status"))
                    {
                        Toast.makeText(context, "Updated Successfully", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        Toast.makeText(context, "Failed Please try again after some time", Toast.LENGTH_SHORT).show();

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
                hashMap.put("id", String.valueOf(id));
                hashMap.put("type", "4");
                hashMap.put("product_price", amount);
                hashMap.put("product_name", name);
                hashMap.put("product_desc", desc);
                hashMap.put("images", images);
                hashMap.put("company_id", Global.Company_Id);
                return hashMap;
            }
        });

    }


}
