package com.digital.gnsbook.Fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
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
import com.digital.gnsbook.Extra.DividerDecorator;
import com.digital.gnsbook.Global;
import com.digital.gnsbook.Model.RequestAcceptItem;
import com.digital.gnsbook.Model.ResponseRequest;
import com.digital.gnsbook.Model.RquestItem;
import com.digital.gnsbook.ViewDialog;
import com.google.gson.Gson;
import com.httpgnsbook.gnsbook.R;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



public class FreindRequests extends Fragment {

    RecyclerView recyclerView,rvNotifiaction;
    ViewDialog dialog;
    TextView noRequest;
    List<RequestAcceptItem> items = new ArrayList<RequestAcceptItem>();
    List<RquestItem> Notificationitems = new ArrayList<RquestItem>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup viewGroup, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frgfreindrequest, viewGroup, false);
        recyclerView = view.findViewById(R.id.rvRequest);
        rvNotifiaction = view.findViewById(R.id.rvNotifiaction);
        noRequest = view.findViewById(R.id.noRequest);
        dialog = new ViewDialog(getActivity());
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.addItemDecoration(new DividerDecorator(getActivity()));
        rvNotifiaction.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvNotifiaction.addItemDecoration(new DividerDecorator(getActivity()));
        recyclerView.setAdapter(new RecyclerView.Adapter() {
            @NonNull
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                return new Holder(LayoutInflater.from(getActivity()).inflate(R.layout.requestitem, viewGroup, false));
            }

            @Override
            public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int i) {
                Holder holder = (Holder) viewHolder;
                final RequestAcceptItem item = items.get(i);
                holder.accept.setTag(item);
                holder.reject.setTag(item);
                holder.reject.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        removefriend(String.valueOf(item.getCustomeridFrom()));
                        items.remove(i);
                        notifyItemRemoved(i);
                        notifyItemRangeChanged(i, items.size());
                    }
                });
                holder.accept.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        addFreind(String.valueOf(item.getCustomeridFrom()));
                        items.remove(i);
                        notifyItemRemoved(i);
                        notifyItemRangeChanged(i, items.size());
                    }
                });

                holder.name.setText(item.getName() +" "+ item.getLastName());
                Picasso.get().load(APIs.Dp+item.getDPic()).into(holder.dp);

            }

            @Override
            public int getItemCount() {
                return items.size();
            }
            class Holder extends RecyclerView.ViewHolder {
                public TextView name , city;
                ImageView dp;
                CardView accept,reject;

                public Holder(@NonNull View itemView) {
                    super(itemView);
                    name = itemView.findViewById(R.id.searchNmae);
                    city = itemView.findViewById(R.id.searchCity);
                    dp = itemView.findViewById(R.id.searchDp);
                    accept = itemView.findViewById(R.id.accRequest);
                    reject = itemView.findViewById(R.id.rejRequest);
                }
            }
        });
        rvNotifiaction.setAdapter(new RecyclerView.Adapter() {
            @NonNull
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                return new Holder(LayoutInflater.from(getActivity()).inflate(R.layout.requestitem, viewGroup, false));
            }

            @Override
            public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int i) {
                Holder holder = (Holder) viewHolder;
                final RquestItem item = Notificationitems.get(i);
                holder.accept.setVisibility(View.GONE);
                holder.reject.setVisibility(View.GONE);

                holder.name.setText(item.getName() +" "+ item.getLastName()+" is now your friend.");
                Picasso.get().load(APIs.Dp+item.getDPic()).into(holder.dp);

            }

            @Override
            public int getItemCount() {
                return Notificationitems.size();
            }
            class Holder extends RecyclerView.ViewHolder {
                public TextView name , city;
                ImageView dp;
                CardView accept,reject;

                public Holder(@NonNull View itemView) {
                    super(itemView);
                    name = itemView.findViewById(R.id.searchNmae);
                    city = itemView.findViewById(R.id.searchCity);
                    dp = itemView.findViewById(R.id.searchDp);
                    accept = itemView.findViewById(R.id.accRequest);
                    reject = itemView.findViewById(R.id.rejRequest);
                }
            }
        });

        getFreindRequest();
        return view ;
    }

    private void getFreindRequest() {
        dialog.show();
        AppController.getInstance().addToRequestQueue(new StringRequest(1, APIs.RequestList, new Response.Listener<String>() {
            public void onResponse(String str) {
                dialog.dismiss();

                try {
                    JSONObject jsonObject = new JSONObject(str);

                    if (jsonObject.getBoolean("status")){

                        ResponseRequest request = new Gson().fromJson(str,ResponseRequest.class);
                        items.addAll(request.getResult());
                        Notificationitems.addAll(request.getResult2());
                        recyclerView.getAdapter().notifyDataSetChanged();


                    }else {
                        noRequest.setVisibility(View.VISIBLE);
                        noRequest.setText(jsonObject.getString("message"));
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            public void onErrorResponse(VolleyError volleyError) {
                dialog.dismiss();
            }
        }) {
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> hashMap = new HashMap();
                hashMap.put("customerid_to", Global.customerid);
                return hashMap;
            }
        });

    }

    private void removefriend(final String id) {
        dialog.show();
        AppController.getInstance().addToRequestQueue(new StringRequest(1, APIs.declinefriend, new Response.Listener<String>() {
            public void onResponse(String str) {
                dialog.dismiss();

                try {
                    JSONObject jsonObject = new JSONObject(str);

                    if (jsonObject.getBoolean("status")){
                        Toast.makeText(getActivity(), jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(getActivity(), jsonObject.getString("result"), Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            public void onErrorResponse(VolleyError volleyError) {
                dialog.dismiss();
            }
        }) {
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> hashMap = new HashMap();
                hashMap.put("customerid_to", Global.customerid);
                hashMap.put("customerid_from",id);
                return hashMap;
            }
        });

    }

    private void addFreind(final String id) {
        dialog.show();
        AppController.getInstance().addToRequestQueue(new StringRequest(1, APIs.acceptRequest, new Response.Listener<String>() {
            public void onResponse(String str) {
                dialog.dismiss();

                try {
                    JSONObject jsonObject = new JSONObject(str);

                    if (jsonObject.getBoolean("status")){
                        Toast.makeText(getActivity(), jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(getActivity(), jsonObject.getString("result"), Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            public void onErrorResponse(VolleyError volleyError) {
                dialog.dismiss();
            }
        }) {
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> hashMap = new HashMap();
                hashMap.put("customerid_to", Global.customerid);
                hashMap.put("customerid_from",id);
                return hashMap;
            }
        });

    }
}
