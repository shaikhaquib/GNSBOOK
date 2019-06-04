package com.digital.gnsbook.Store;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.digital.gnsbook.Config.APIs;
import com.httpgnsbook.gnsbook.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

class CatalogueAdapter extends RecyclerView.Adapter<CatalogueAdapter.SingleViewHolder> {

    private Context context;
    private String[] employees;
    private int checkedPosition = -1;
    private OnItemClick mCallback;


    public CatalogueAdapter(Context context, String[] imagearray, OnItemClick listener) {
        this.context = context;
        this.employees = imagearray;
        this.mCallback=listener;
    }



    @NonNull
    @Override
    public SingleViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_cateloge, viewGroup, false);
        return new SingleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SingleViewHolder singleViewHolder, int position) {
        singleViewHolder.bind(employees[position],position);
    }

    @Override
    public int getItemCount() {
        return employees.length;
    }

    class SingleViewHolder extends RecyclerView.ViewHolder {

        private ImageView imageView,catlogeImage;
        RelativeLayout relativeLayout;

        SingleViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image_tik);
            catlogeImage = itemView.findViewById(R.id.itemCateloge);
            relativeLayout = itemView.findViewById(R.id.catloglauoyt);
        }

        void bind(final String Image, int position) {
            if (checkedPosition == -1) {
                if (position==0){
                    checkedPosition = getAdapterPosition();
                    imageView.setVisibility(View.VISIBLE);
                    mCallback.onClick(APIs.Dp+Image.replace(" ",""));
                    relativeLayout.setBackground(ContextCompat.getDrawable(context, R.drawable.border_orange));
                }else {
                imageView.setVisibility(View.GONE);
                    relativeLayout.setBackground(ContextCompat.getDrawable(context, R.drawable.rzp_border));
                }
            } else {
                if (checkedPosition == getAdapterPosition()) {
                    imageView.setVisibility(View.VISIBLE);
                    relativeLayout.setBackground(ContextCompat.getDrawable(context, R.drawable.border_orange));

                } else {
                    imageView.setVisibility(View.GONE);
                    relativeLayout.setBackground(ContextCompat.getDrawable(context, R.drawable.rzp_border));
                }
            }
         //   textView.setText(employee.getName());
            Picasso.get().load(APIs.Dp+Image.replace(" ","")).into(catlogeImage);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    imageView.setVisibility(View.VISIBLE);
                    if (checkedPosition != getAdapterPosition()) {
                        notifyItemChanged(checkedPosition);
                        checkedPosition = getAdapterPosition();
                        mCallback.onClick(APIs.Dp+Image.replace(" ",""));
                    }
                }
            });
        }
    }

    public String getSelected() {
        if (checkedPosition != -1) {
            return employees[checkedPosition];
        }
        return null;
    }
}
