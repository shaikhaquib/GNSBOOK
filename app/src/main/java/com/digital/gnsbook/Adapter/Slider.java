package com.digital.gnsbook.Adapter;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.digital.gnsbook.Config.APIs;
import com.digital.gnsbook.Extra.WrapContentDraweeView;
import com.facebook.drawee.view.SimpleDraweeView;
import com.httpgnsbook.gnsbook.R;

import io.grpc.CallOptions;

class Slider extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    String[] imageArray;
    Context context;
    public Slider(String[] imageArray, Context context) {
        this.imageArray = imageArray;
        this.context=context;
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
       // Log.d("Position "+i , APIs.Dp+imageArray[i]);
      //  holder.Slide.setImageURI(Uri.parse(APIs.Dp+imageArray[i]));
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
