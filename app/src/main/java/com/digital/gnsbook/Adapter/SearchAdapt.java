package com.digital.gnsbook.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.digital.gnsbook.Activity.Companypage;
import com.digital.gnsbook.Activity.FriendProfile;
import com.digital.gnsbook.Activity.SearchActivity;
import com.digital.gnsbook.Config.APIs;
import com.digital.gnsbook.Config.DbHelper;
import com.digital.gnsbook.Global;
import com.digital.gnsbook.Model.SearchItem;
import com.digital.gnsbook.Model.SearchModel;
import com.httpgnsbook.gnsbook.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Shaikh Aquib on 02-May-18.
 */

public class SearchAdapt extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    Context context;
    private List<SearchItem> models;
    boolean isCompony =false;
    private static final int CMP = 0;
    private static final int PPL = 1;

    public SearchAdapt(Activity activity, List<SearchItem> models, boolean isCompony) {
        this.context=activity;
        this.models=models;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;

        if (viewType==PPL){
        view= LayoutInflater.from(context).inflate(R.layout.searchadapt,parent,false);
        }else {
            view= LayoutInflater.from(context).inflate(R.layout.searchadaptcmp,parent,false);
        }
        return new HelpHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final HelpHolder helpHolder =(HelpHolder) holder;
        final SearchItem model=models.get(position);

        holder.setIsRecyclable(false);

        helpHolder.itemView.setTag(model);
        if (position==0 && model.getSearchType()==1){
            helpHolder.searchTag.setVisibility(View.VISIBLE);
            helpHolder.searchTag.setText("People");
        }

        if (model.getSearchType() == 1) {
            helpHolder.name.setText(model.getName() + " " + model.getLastName());
            Picasso.get().load(APIs.Dp + model.getDPic()).into( helpHolder.dp);
        }
        else{
            helpHolder.name.setText(model.getName());
            Picasso.get().load(APIs.Dp+model.getLogo()).into(helpHolder.dp);

            if (!isCompony){
                isCompony = true;
                helpHolder.searchTagcmp.setVisibility(View.VISIBLE);
            }
        }


            helpHolder.city.setText("India");
        helpHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (model.getSearchType() == 1)
                    context.startActivity(new Intent(context, FriendProfile.class).putExtra("id",model.getCustomerId()).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                else
                    context.startActivity(new Intent(context, Companypage.class).putExtra(DbHelper.COLUMN_ID, model.getCompanyId()));

            }
        });



    }

    @Override
    public int getItemCount() {
        return models.size();
    }


    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        if (models.get(position).getSearchType() != 1) {
            return CMP;
        } else {
            return PPL;
        }
    }

    class HelpHolder extends RecyclerView.ViewHolder{

        public TextView name , city,searchTag,searchTagcmp;
        ImageView dp;

        public HelpHolder(View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.searchNmae);
            city = itemView.findViewById(R.id.searchCity);
            dp = itemView.findViewById(R.id.searchDp);
            searchTag = itemView.findViewById(R.id.searchTag);
            searchTagcmp = itemView.findViewById(R.id.searchTagcmp);


        }
    }
}
