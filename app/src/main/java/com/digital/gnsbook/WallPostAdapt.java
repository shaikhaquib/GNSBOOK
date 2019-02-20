package com.digital.gnsbook;

import android.content.Context;
import android.content.Intent;
import android.os.Build.VERSION;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.text.Html;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.TextView;
import com.digital.gnsbook.Config.APIs;
import com.digital.gnsbook.Model.WallPostmodel;
import com.digital.gnsbook.Payment.OverlapDecoration;
import com.httpgnsbook.gnsbook.R;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;
import java.util.ArrayList;

public class WallPostAdapt extends Adapter<ViewHolder> {
    Context context;
    ArrayList<WallPostmodel> postmodels;

    class Holder extends ViewHolder {
        CheckBox BtnLike ;
        RecyclerView Overlapview;
        TextView date, commentCount;
        ImageView dp;
        ImageView imgPost;
        TextView likeCount;
        TextView likename;
        TextView name;
        ImageView share;
        TextView textPost;
        TextView title;

        public Holder(@NonNull View view) {
            super(view);
            dp = (ImageView) view.findViewById(R.id.wpDP);
            imgPost = (ImageView) view.findViewById(R.id.wpImage);
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
        }
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

        public long getItemId(int i) {
            return (long) i;
        }

        public OverLapAdapt(String[] strArr) {
            this.Images = strArr;
        }

        @NonNull
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            return new LikeHolder(LayoutInflater.from(WallPostAdapt.this.context).inflate(R.layout.circular_image, viewGroup, false));
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

    public long getItemId(int i) {
        return (long) i;
    }

    public int getItemViewType(int i) {
        return i;
    }

    public WallPostAdapt(ArrayList<WallPostmodel> arrayList, Context context) {
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

        holder.name.setText(postmodel.name);
        holder.date.setText(postmodel.created_at);
        holder.textPost.setText(postmodel.description);
        holder.share.setTag(postmodel);
        holder.title.setText(postmodel.title);
        holder.likeCount.setText(String.valueOf(postmodel.likecount));
        holder.commentCount.setText(String.valueOf(postmodel.commentCount));
        holder.likename.setTag(postmodel);
        getText(postmodel, holder.likename);

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

        if (postmodel.description.equals("") || postmodel.description.isEmpty()) {
            holder.textPost.setVisibility(View.GONE);
        } else {
            holder.textPost.setVisibility(View.VISIBLE);
        }
        if (postmodel.description.equals("") || postmodel.description.isEmpty()) {
            holder.imgPost.setVisibility(View.GONE);
        } else {
            holder.imgPost.setVisibility(View.VISIBLE);
        }

        RoundedCornersTransformation roundedCornersTransformation = new RoundedCornersTransformation(15, 15);

        Picasso.get().load(APIs.Dp + postmodel.logo).into(holder.dp);
        Picasso.get().load(APIs.postImg + postmodel.images).transform(roundedCornersTransformation).into(holder.imgPost);


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
                    if (postmodel.selfLike == 1) {
                        postmodel.selfLike = 0;
                        postmodel.likecount --;
                        holder.likeCount.setText(String.valueOf(postmodel.likecount));
                        getText(postmodel, holder.likename);
                    }else{
                        postmodel.selfLike = 1;
                        postmodel.likecount ++;
                        holder.likeCount.setText(String.valueOf(postmodel.likecount));
                        getText(postmodel, holder.likename);}
                } else if (postmodel.selfLike == 1) {
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
        });
    }

    private String getText(WallPostmodel wallPostmodel, TextView textView) {
        if (wallPostmodel.Like_name.length == 1) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("<b>");
            stringBuilder.append(wallPostmodel.Like_name[0]);
            stringBuilder.append("</b> <br> liked.");
            Spanned spanned = Html.fromHtml(stringBuilder.toString());
            textView.setText(spanned);
        } else if (wallPostmodel.Like_name.length == 2) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("<b>");
            stringBuilder.append(wallPostmodel.Like_name[0]);
            stringBuilder.append("</b> and <br>one+ liked.");
            Spanned spanned = Html.fromHtml(stringBuilder.toString());
            textView.setText(spanned);
        } else if (wallPostmodel.Like_name.length <= 2) {
            textView.setVisibility(View.GONE);
          //  wallPostmodel = null;
        } else if (wallPostmodel.likecount > 2) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("<b>");
            stringBuilder.append(wallPostmodel.Like_name[0]);
            stringBuilder.append("</b> and <br>");
            stringBuilder.append(wallPostmodel.likecount - 1);
            stringBuilder.append("+ liked.");
            Spanned spanned = Html.fromHtml(stringBuilder.toString());
            textView.setText(spanned);        } else {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("<b>");
            stringBuilder.append(wallPostmodel.Like_name[0]);
            stringBuilder.append("</b> and <br>one+ liked.");
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
}
