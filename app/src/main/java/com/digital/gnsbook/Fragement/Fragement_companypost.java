package com.digital.gnsbook.Fragement;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.digital.gnsbook.Activity.Activity_GNSProduct;
import com.digital.gnsbook.Activity.Companypage;
import com.digital.gnsbook.Activity.New_Post;
import com.digital.gnsbook.Activity.UploadProduct;
import com.digital.gnsbook.Adapter.Company_WallAdapt;
import com.digital.gnsbook.Config.APIs;
import com.digital.gnsbook.Config.AppController;
import com.digital.gnsbook.Global;
import com.digital.gnsbook.Model.ComponyModel;
import com.digital.gnsbook.Model.TimeLine_Model.CompanyTimeLineItem;
import com.digital.gnsbook.Model.TimeLine_Model.CompanyTimelineResponse;
import com.digital.gnsbook.Model.TimeLine_Model.TimeLineResponse;
import com.digital.gnsbook.Model.WallPostmodel;
import com.digital.gnsbook.Adapter.New_WallPostAdapt;
import com.digital.gnsbook.ViewDialog;
import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.httpgnsbook.gnsbook.R;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Fragement_companypost extends Fragment {

    List<CompanyTimeLineItem> postmodels = new ArrayList < >();
    ViewDialog dialog;
    RecyclerView wallPost;
    ImageView Logo , logoc;
    ComponyModel componyModel;
    CardView NewPost;
    int count = 10 ;
    private String TAG = "WallPostFragment";
    private int offset = 0;
    CardView porogress;
    String Cid;
    Companypage activity;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_comapnypost, container, false);


        activity = (Companypage) getActivity();
        wallPost = view.findViewById(R.id.wallPost);
        wallPost.setNestedScrollingEnabled(false);
        porogress = view.findViewById(R.id.compprogrssview);
        NewPost = view.findViewById(R.id.adminnewpost);
        if (Global.Company_Admin_Id != Integer.parseInt(Global.customerid)){
            NewPost.setVisibility(View.GONE);
        }

        NewPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Global.Company_Cate.equals("other")){
                startActivity(new Intent(getActivity(), New_Post.class).putExtra("type","2"));}
                else{
                    showDialoge();
                }
            }
        });

        Logo = view.findViewById(R.id.componyLogo);
        Picasso.get().load(APIs.Dp + Global.Company_Logo).into(Logo);

        wallPost.setLayoutManager(new LinearLayoutManager(getActivity()));
        wallPost.setItemAnimator(null);
        dialog = new ViewDialog(getActivity());

        getTimelinePost();
        wallPost.setAdapter(new Company_WallAdapt(getActivity(), postmodels));
        NestedScrollView nestedScrollView = (NestedScrollView) view.findViewById(R.id.comyScroll);
        if (nestedScrollView != null) {
            nestedScrollView.setOnScrollChangeListener(new C09321());
        }
        return view;
    }

    private void showDialoge() {
        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(getActivity());
        View inflate = getLayoutInflater().inflate(R.layout.compony_upload_dialoge, null);
        builder.setView(inflate);
        TextView headTitle = (TextView) inflate.findViewById(R.id.headTitle);
        TextView textView = (TextView) inflate.findViewById(R.id.new_FundTrans);
        TextView textView2 = (TextView) inflate.findViewById(R.id.old_FundTrans);
        TextView textView3 = (TextView) inflate.findViewById(R.id.thirdOption);

        headTitle.setText("SELECT POSTING TYPE");
        textView.setText("Normal Posting");
        textView2.setText("Product Posting");
        textView3.setText("Sell GNS product");
        builder.create().show();
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), New_Post.class).putExtra("type","2"));
            }
        });
        textView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), UploadProduct.class));
            }
        });
        textView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), Activity_GNSProduct.class));
            }
        });
    }

    class C09321 implements NestedScrollView.OnScrollChangeListener {
        C09321() {
        }

        public void onScrollChange(NestedScrollView nestedScrollView, int i, int i2, int i3, int i4) {
            if (i2 == nestedScrollView.getChildAt(0).getMeasuredHeight() - nestedScrollView.getMeasuredHeight() && count > 0) {
                offset = offset + 10;
                getTimelinePost();
                porogress.setVisibility(View.VISIBLE);
            }
        }
    }

    private void getTimelinePost() {
        AppController.getInstance().addToRequestQueue(new StringRequest(StringRequest.Method.POST, APIs.company_timeline, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                porogress.setVisibility(View.GONE);


                try {
                    JSONObject object = new JSONObject(s);

                    if (object.getBoolean("status")) {

                        JsonReader reader = new JsonReader(new StringReader(s));
                        reader.setLenient(true);
                        CompanyTimelineResponse timeLineResponse = new Gson().fromJson(reader, CompanyTimelineResponse.class);

                        if (timeLineResponse.getResult().size() > 0) {
                            postmodels.addAll(timeLineResponse.getResult());
                        }

                    }
                        wallPost.getAdapter().notifyDataSetChanged();

            } catch (JSONException e) {
                e.printStackTrace();
            }



            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> hashMap = new HashMap();
                hashMap.put("company_id",activity.getIntent().getStringExtra("id"));
                hashMap.put("customer_id", Global.customerid);
                hashMap.put("limit", "10");
                hashMap.put("offset", String.valueOf(offset));
                return hashMap;
            }
        });
    }


}
