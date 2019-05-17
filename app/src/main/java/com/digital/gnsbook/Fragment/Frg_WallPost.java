package com.digital.gnsbook.Fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.digital.gnsbook.Adapter.WallAdapt;
import com.digital.gnsbook.Config.APIs;
import com.digital.gnsbook.Config.AppController;
import com.digital.gnsbook.Global;
import com.digital.gnsbook.Model.TimeLine_Model.LikesItem;
import com.digital.gnsbook.Model.TimeLine_Model.TimeLineItem;
import com.digital.gnsbook.Model.TimeLine_Model.TimeLineResponse;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.httpgnsbook.gnsbook.R;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Frg_WallPost extends Fragment {

    RecyclerView rvWallpost;
    private int offset = 0;
    SwipeRefreshLayout refresh;
    List<LikesItem> likeModel = new ArrayList<>();
    List<TimeLineItem> postModel = new ArrayList<>();
    LinearLayoutManager  layoutManager;
    int count ;
    private @Nullable
    ResizeOptions mResizeOptions;
    private static final int SPAN_COUNT = 3;
    boolean isLoading = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frg_wallpost, container, false);

        layoutManager=new LinearLayoutManager(getActivity());

        refresh = view.findViewById(R.id.refresh);
        rvWallpost = view.findViewById(R.id.rvWallpost);
        rvWallpost.setLayoutManager(layoutManager);
        rvWallpost.addOnLayoutChangeListener(
                new View.OnLayoutChangeListener() {
                    @Override
                    public void onLayoutChange(
                            View view,
                            int left,
                            int top,
                            int right,
                            int bottom,
                            int oldLeft,
                            int oldTop,
                            int oldRight,
                            int oldBottom) {
                        final int imageSize = (right - left) / SPAN_COUNT;
                        mResizeOptions = new ResizeOptions(imageSize, imageSize);
                    }
                });
        rvWallpost.setHasFixedSize(true);
        postModel.clear();
        rvWallpost.setAdapter(new WallAdapt(getActivity(),postModel));


        refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                offset = 0;
                getPost();
            }
        });





        rvWallpost.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();

                if (!isLoading) {
                    if (linearLayoutManager != null && linearLayoutManager.findLastCompletelyVisibleItemPosition() == postModel.size() - 1) {
                        //bottom of list!
                        offset = offset + 10;
                        //  porogress.setVisibility(View.VISIBLE);
                        getPost();
                        isLoading = true;
                        postModel.add(null);
                        rvWallpost.getAdapter().notifyItemInserted(postModel.size() - 1);
                    }
                }
            }
        });

        getPost();
        return view;
    }

    private void getPost() {

        AppController.getInstance().addToRequestQueue(new StringRequest(1, APIs.new_timelineAPI, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                /*porogress.setVisibility(View.GONE);*/


                if (isLoading) {
                    postModel.remove(postModel.size() - 1);
                    int scrollPosition = postModel.size();
                    rvWallpost.getAdapter().notifyItemRemoved(scrollPosition);
                    isLoading = false;
                }

                if (refresh.isRefreshing()){
                    refresh.setRefreshing(false);
                    rvWallpost.setAdapter(null);
                    postModel.clear();
                    rvWallpost.setAdapter(new WallAdapt(getActivity(),postModel));

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



                } catch (JSONException e) {
                    e.printStackTrace();
                }
                rvWallpost.getAdapter().notifyDataSetChanged();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (refresh.isRefreshing()){
                    refresh.setRefreshing(false);
                }

                if (postModel.size()>0){
                postModel.remove(postModel.size() - 1);
                int scrollPosition = postModel.size();
                rvWallpost.getAdapter().notifyItemRemoved(scrollPosition);
                isLoading = false;}
            }
        }) {
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> hashMap = new HashMap();
                hashMap.put("company_id", "1");
                hashMap.put("customer_id", Global.customerid);
                hashMap.put("limit", "10");
                hashMap.put("offset", String.valueOf(offset));
                return hashMap;
            }
        });
    }


}
