package com.digital.gnsbook.Adapter;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.digital.gnsbook.Config.APIs;
import com.digital.gnsbook.Model.TimeLine_Model.LikesItem;
import com.facebook.drawee.view.SimpleDraweeView;
import com.httpgnsbook.gnsbook.R;
import com.squareup.picasso.Picasso;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

class OverLapAdapt extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    List<LikesItem> Images = new ArrayList<>();
    Context context;

    public OverLapAdapt(List<LikesItem> strArr, Context context) {
        this.Images = strArr;
        this.context = context;
    }

    public class LikeHolder extends RecyclerView.ViewHolder {
        SimpleDraweeView imageView;

        public LikeHolder(@NonNull View view) {
            super(view);
            imageView =  view.findViewById(R.id.ovimage);
        }
    }

    public long getItemId(int i) {
        return (long) i;
    }


    @NonNull
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new LikeHolder(LayoutInflater.from(context).inflate(R.layout.circular_image, viewGroup, false));
    }

    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        LikeHolder likeHolder = (LikeHolder) viewHolder;
        /*Picasso picasso = Picasso.get();

        picasso.load(stringBuilder.toString()).into(likeHolder.imageView);*/
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(APIs.Dp);
        stringBuilder.append(this.Images.get(i).getDPic());


        likeHolder.imageView.setImageURI(Uri.parse(String.valueOf(stringBuilder)));
    }

    public int getItemCount() {
        if (this.Images.size() > 2) {
            return 2;
        }
        return this.Images.size();
    }
}
