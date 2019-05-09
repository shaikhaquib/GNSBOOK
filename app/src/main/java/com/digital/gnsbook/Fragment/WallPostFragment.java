package com.digital.gnsbook.Fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.support.v4.widget.NestedScrollView.OnScrollChangeListener;
import android.support.v4.widget.SwipeRefreshLayout;
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
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.digital.gnsbook.Adapter.FriendSuggestionAdapter;
import com.digital.gnsbook.Adapter.WallAdapt;
import com.digital.gnsbook.Config.APIs;
import com.digital.gnsbook.Config.AppController;
import com.digital.gnsbook.Global;
import com.digital.gnsbook.Model.FriendSuggestionResponse;
import com.digital.gnsbook.Model.FriendSuggestiontem;
import com.digital.gnsbook.Model.TimeLine_Model.TimeLineItem;
import com.digital.gnsbook.Model.TimeLine_Model.TimeLineResponse;
import com.digital.gnsbook.Model.WallPostmodel;
import com.digital.gnsbook.ViewDialog;
import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.httpgnsbook.gnsbook.R;
import com.squareup.picasso.Picasso;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class WallPostFragment extends Fragment {
    int count = 10;
    ViewDialog dialog;
    private int offset = 0;
    CardView porogress;
    List<TimeLineItem> postModel = new ArrayList<>();
    List<FriendSuggestiontem> Models = new ArrayList();

    RecyclerView wallPost,frndSuggestion;
    SwipeRefreshLayout swipeRefreshLayout ;

    /* renamed from: com.digital.gnsbook.Fragment.WallPostFragment$1 */
    class C09321 implements OnScrollChangeListener {
        C09321() {
        }

        public void onScrollChange(NestedScrollView nestedScrollView, int i, int i2, int i3, int i4) {
            if (i2 == nestedScrollView.getChildAt(0).getMeasuredHeight() - nestedScrollView.getMeasuredHeight() && WallPostFragment.this.count > 0) {
                WallPostFragment.this.offset = WallPostFragment.this.offset + 10;
                WallPostFragment.this.getTimelinePost();
                porogress.setVisibility(View.VISIBLE);
            }
        }
    }

    /* renamed from: com.digital.gnsbook.Fragment.WallPostFragment$2 */
    class C09332 implements Listener<String> {
        C09332() {
        }

        public void onResponse(String response) {
            porogress.setVisibility(View.GONE);
            if (swipeRefreshLayout.isRefreshing()){
                swipeRefreshLayout.setRefreshing(false);
            }
            try {
                JSONArray jsonArray = new JSONArray(response);

                for (int i=0 ; i < jsonArray.length() ; i++){

                    String MyResponce = jsonArray.getString(i);



                    JsonReader reader = new JsonReader(new StringReader(MyResponce));
                    reader.setLenient(true);

                    TimeLineResponse timeLineResponse = new Gson().fromJson(reader, TimeLineResponse.class);

                    if (timeLineResponse.getResult().size() > 0) {
                        postModel.addAll(timeLineResponse.getResult());
                    }
                }
                wallPost.getAdapter().notifyDataSetChanged();



            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    /* renamed from: com.digital.gnsbook.Fragment.WallPostFragment$3 */
    class C09343 implements ErrorListener {
        C09343() {
            if (swipeRefreshLayout.isRefreshing()){
                swipeRefreshLayout.setRefreshing(false);
            }
        }

        public void onErrorResponse(VolleyError volleyError) {
            porogress.setVisibility(View.GONE);
        }
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
    }

    public View onCreateView(LayoutInflater lnflater, ViewGroup viewGroup, Bundle bundle) {
        View layoutInflater = lnflater.inflate(R.layout.fragment_wallpost, viewGroup, false);
        wallPost =layoutInflater.findViewById(R.id.wallPostmain);
        frndSuggestion =layoutInflater.findViewById(R.id.frndSuggestion);
        swipeRefreshLayout = layoutInflater.findViewById(R.id.mnSwipe);
        porogress = layoutInflater.findViewById(R.id.progrssview);
        wallPost.setLayoutManager(new LinearLayoutManager(getActivity()));

        frndSuggestion.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false));
        dialog = new ViewDialog(getActivity());
        getTimelinePost();
        wallPost.setAdapter(new WallAdapt(getActivity(),postModel));

        /*NestedScrollView nestedScrollView =  layoutInflater.findViewById(R.id.myScroll);
        if (nestedScrollView != null) {
            nestedScrollView.setOnScrollChangeListener(new C09321());
        }*/

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(true);
                offset = 0;
                postModel.clear();
                wallPost .getRecycledViewPool().clear();
                wallPost.getAdapter().notifyDataSetChanged();
                getTimelinePost();
            }
        });
        getFriendList();
        return layoutInflater;
    }
    private void getTimelinePost() {
        AppController.getInstance().addToRequestQueue(new StringRequest(1, APIs.new_timelineAPI, new C09332(), new C09343()) {
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> hashMap = new HashMap();
                hashMap.put("company_id", "1");
                hashMap.put("customer_id", Global.customerid);
                hashMap.put("limit", "25");
                hashMap.put("offset", String.valueOf(offset));
                return hashMap;
            }
        });

        SuggestionAdapter();
    }

    private void SuggestionAdapter() {

        frndSuggestion.setAdapter(new RecyclerView.Adapter() {
            @NonNull
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                return new Holder(LayoutInflater.from(getActivity()).inflate(R.layout.friendsuggestionui, viewGroup, false));
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

                if (model.getCity()==null || model.getCity().isEmpty()){
                    holder.Location.setText("India");
                }else {
                    holder.Location.setText(model.getCity());
                }
            }

            @Override
            public int getItemCount() {
                return Models.size();
            }

            class Holder extends RecyclerView.ViewHolder {
                ImageView dp;
                TextView Name,Location;
                CardView frdAddfrind;
                public Holder(@NonNull View itemView) {
                    super(itemView);
                    dp=itemView.findViewById(R.id.frdp);
                    Name=itemView.findViewById(R.id.frdName);
                    Location=itemView.findViewById(R.id.frdLocation);
                    frdAddfrind=itemView.findViewById(R.id.frdAddfrind);
                }
            }

        });
    }

    private void getFriendList() {
        //   dialog.show();
        StringRequest stringRequest = new StringRequest(StringRequest.Method.POST, APIs.friend_suggestion, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //   tprice=0;
                //    dialog.dismiss();

                try {
                    JSONObject jsonObject = new JSONObject(response);

                    if (jsonObject.getBoolean("status")){

                        Gson gson = new Gson();
                        FriendSuggestionResponse res = gson.fromJson(response, FriendSuggestionResponse.class);
                        Models = res.getResult();
                        frndSuggestion.getAdapter().notifyDataSetChanged();}


                } catch (JSONException e) {
                    e.printStackTrace();
                }




            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // dialog.dismiss();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map <String,String> param = new HashMap<String,String>();
                param.put("customer_id", Global.customerid);
                return param;
            }
        };
        AppController.getInstance().addToRequestQueue(stringRequest);

    }


    private void addFreind(final String id) {
        AppController.getInstance().addToRequestQueue(new StringRequest(1, APIs.addfriend, new Response.Listener<String>() {
            public void onResponse(String str) {

                try {
                    JSONObject jsonObject = new JSONObject(str);

                    if (jsonObject.getBoolean("status")){
                        Toast.makeText(getContext(), jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(getContext(), jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
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
