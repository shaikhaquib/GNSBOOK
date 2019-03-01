package com.digital.gnsbook;

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

import com.digital.gnsbook.Activity.FriendProfile;
import com.digital.gnsbook.Activity.SearchActivity;
import com.digital.gnsbook.Config.APIs;
import com.digital.gnsbook.Model.SearchModel;
import com.httpgnsbook.gnsbook.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Shaikh Aquib on 02-May-18.
 */

public class HistAdapt extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    Context context;
    private ArrayList<SearchModel> models;
    private ArrayList<SearchModel> contactListFiltered;


    public HistAdapt(Activity activity, ArrayList<SearchModel> models) {
        this.context=activity;
        this.models=models;
        this.contactListFiltered=models;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.searchadapt,parent,false);
        HelpHolder holder = new HelpHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final HelpHolder helpHolder =(HelpHolder) holder;
        final SearchModel model=models.get(position);

        helpHolder.itemView.setTag(model);

        helpHolder.name.setText(model.name +" "+ model.last_name);

        if (!model.city.equals("null")){
            helpHolder.city.setText(model.city);
        }else {
            helpHolder.city.setVisibility(View.GONE);
        }
        Picasso.get().load(APIs.Dp+model.d_pic).into(((HelpHolder) holder).dp);
        helpHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, FriendProfile.class).putExtra("id",model.customer_id).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
            }
        });



    }

    @Override
    public int getItemCount() {
        return models.size();
    }

 /*   @Override
    public Filter getFilter() {

        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {

                String charString = charSequence.toString();

                if (charString.isEmpty()) {

                      models = contactListFiltered;
                } else {

                    ArrayList<SearchModel> filteredList = new ArrayList<>();

                    for (SearchModel row : contactListFiltered) {

                        if (row.getName().contains(charString.toLowerCase())) {
                            filteredList.add(row);
                        }

                    }

                    models = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = contactListFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                contactListFiltered = (ArrayList<SearchModel>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }*/



    class HelpHolder extends RecyclerView.ViewHolder{

        public TextView name , city;
        ImageView dp;

        public HelpHolder(View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.searchNmae);
            city = itemView.findViewById(R.id.searchCity);
            dp = itemView.findViewById(R.id.searchDp);


        }
    }
}
