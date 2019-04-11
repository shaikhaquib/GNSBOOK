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

public class
FriendSuggestionAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context context;
    List<FriendSuggestiontem> Models;
    public FriendSuggestionAdapter(Context context, List<FriendSuggestiontem> arrayList) {
        this.Models = arrayList;
        this.context = context;
        setHasStableIds(true);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)
    {
    return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int i) {


    }

    @Override
    public int getItemCount() {
        return Models.size();
    }



}
