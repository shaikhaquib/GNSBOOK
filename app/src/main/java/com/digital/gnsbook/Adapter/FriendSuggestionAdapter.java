package com.digital.gnsbook.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.digital.gnsbook.Config.APIs;
import com.digital.gnsbook.Config.AppController;
import com.digital.gnsbook.Global;
import com.digital.gnsbook.Model.FriendSuggestiontem;
import com.httpgnsbook.gnsbook.R;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FriendSuggestionAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context context;
    List<FriendSuggestiontem> Models;
    public FriendSuggestionAdapter(Context context, List<FriendSuggestiontem> arrayList) {
        this.Models = arrayList;
        this.context = context;
        setHasStableIds(true);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new New_WallPostAdapt.Holder(LayoutInflater.from(this.context).inflate(R.layout.wallpostadapter, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int i) {
        final Holder holder = (Holder) viewHolder;
        final FriendSuggestiontem model = Models.get(i);

        holder.Name.setText(model.getName()+" "+model.getLastName());
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

    }

    @Override
    public int getItemCount() {
        return Models.size();
    }
    class Holder extends RecyclerView.ViewHolder {
        ImageView dp;
        TextView Name;
        CardView frdAddfrind;
        public Holder(@NonNull View itemView) {
            super(itemView);
            dp=itemView.findViewById(R.id.frdp);
            Name=itemView.findViewById(R.id.frdName);
            frdAddfrind=itemView.findViewById(R.id.frdAddfrind);
        }
    }

    private void addFreind(final String id) {
        AppController.getInstance().addToRequestQueue(new StringRequest(1, APIs.addfriend, new Response.Listener<String>() {
            public void onResponse(String str) {

                try {
                    JSONObject jsonObject = new JSONObject(str);

                    if (jsonObject.getBoolean("status")){
                        Toast.makeText(context, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                    }else {
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

}
