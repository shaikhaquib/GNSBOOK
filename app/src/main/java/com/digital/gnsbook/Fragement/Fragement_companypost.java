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
import com.digital.gnsbook.Activity.Companypage;
import com.digital.gnsbook.Activity.New_Post;
import com.digital.gnsbook.Activity.UploadProduct;
import com.digital.gnsbook.Config.APIs;
import com.digital.gnsbook.Config.AppController;
import com.digital.gnsbook.Fragment.WallPostFragment;
import com.digital.gnsbook.Global;
import com.digital.gnsbook.Model.ComponyModel;
import com.digital.gnsbook.Model.WallPostmodel;
import com.digital.gnsbook.New_WallPostAdapt;
import com.digital.gnsbook.ViewDialog;
import com.digital.gnsbook.WallPostAdapt;
import com.httpgnsbook.gnsbook.R;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Fragement_companypost extends Fragment {

    ArrayList< WallPostmodel > postmodels = new ArrayList < >();
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
        porogress = view.findViewById(R.id.compprogrssview);
        NewPost = view.findViewById(R.id.adminnewpost);
        if (Global.Company_Admin_Id != Integer.parseInt(Global.customerid)){
            NewPost.setVisibility(View.GONE);
        }

        NewPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Global.Company_Cate.equals("other")){
                startActivity(new Intent(getActivity(), New_Post.class));}
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
        wallPost.setAdapter(new New_WallPostAdapt(this.postmodels, getActivity()));
        NestedScrollView nestedScrollView = (NestedScrollView) view.findViewById(R.id.comyScroll);
        if (nestedScrollView != null) {
            nestedScrollView.setOnScrollChangeListener(new C09321());
        }
        return view;
    }

    private void showDialoge() {
        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(getActivity());
        View inflate = getLayoutInflater().inflate(R.layout.transactionmode, null);
        builder.setView(inflate);
        TextView headTitle = (TextView) inflate.findViewById(R.id.headTitle);
        TextView textView = (TextView) inflate.findViewById(R.id.new_FundTrans);
        TextView textView2 = (TextView) inflate.findViewById(R.id.old_FundTrans);

        headTitle.setText("SELECT POSTING TYPE");
        textView.setText("Normal Posting");
        textView2.setText("Product Posting");
        builder.create().show();
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), New_Post.class));
            }
        });
        textView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), UploadProduct.class));
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
        AppController.getInstance().addToRequestQueue(new StringRequest(StringRequest.Method.POST, APIs.company_timeline, new C09332(), new C09343()) {
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

    /* renamed from: com.digital.gnsbook.Fragment.WallPostFragment$2 */
    class C09332 implements Response.Listener<String> {
        C09332() {
        }

        public void onResponse(String s) {
            porogress.setVisibility(View.GONE);
            try {
                JSONArray str = new JSONArray(s);
                Object json = new JSONTokener(s).nextValue();
                if (json instanceof JSONObject) {
                    count = 0;
                }else {
                    for (int i = 0; i < str.length(); i++) {
                        JSONObject jSONObject = str.getJSONObject(i);

                        JSONObject jSONObject2 = jSONObject.getJSONArray("result").getJSONObject(0);
                        WallPostmodel wallPostmodel = new WallPostmodel();

                        ArrayList arrayList = new ArrayList();
                        ArrayList arrayList2 = new ArrayList();
                        ArrayList arrayList3 = new ArrayList();

                        wallPostmodel.type = jSONObject.getString("type");

                        if (wallPostmodel.type.equals("1")) {

                            wallPostmodel.logo = jSONObject2.getString("logo");
                            wallPostmodel.id = jSONObject2.getString("id");
                            wallPostmodel.company_id = jSONObject2.getString("company_id");
                            wallPostmodel.title = jSONObject2.getString("title");
                            wallPostmodel.description = jSONObject2.getString("description");
                            wallPostmodel.name = jSONObject2.getString("name");
                            wallPostmodel.images = jSONObject2.getString("image");
                            wallPostmodel.created_at = jSONObject2.getString("created_at");
                            wallPostmodel.likecount = jSONObject2.getInt("like_count");
                            wallPostmodel.commentCount = jSONObject2.getInt("comment_count");
                            wallPostmodel.selfLike = jSONObject2.getInt("Self_Likes");

                            for (int i2 = 0; i2 < jSONObject2.getJSONArray("Likes").length(); i2++) {
                                JSONObject jSONObject3 = jSONObject2.getJSONArray("Likes").getJSONObject(i2);
                                arrayList.add(jSONObject3.getString("d_pic"));
                                arrayList2.add(jSONObject3.getString("name"));
                                arrayList3.add(jSONObject3.getString("customer_id"));
                            }
                            Object[] toArray = arrayList.toArray();
                            Object[] toArray2 = arrayList2.toArray();
                            arrayList3.toArray();
                            wallPostmodel.Like_imges = (String[]) Arrays.copyOf(toArray, toArray.length, String[].class);
                            wallPostmodel.Like_name = (String[]) Arrays.copyOf(toArray2, toArray2.length, String[].class);
                        } else {
                            wallPostmodel.logo = jSONObject2.getString("logo");
                            wallPostmodel.id = jSONObject2.getString("id");
                            wallPostmodel.name = jSONObject2.getString("name");

                            wallPostmodel.company_id = jSONObject2.getString("company_id");
                            wallPostmodel.product_name = jSONObject2.getString("product_name");
                            wallPostmodel.product_cat = jSONObject2.getString("product_cat");
                            wallPostmodel.product_price = jSONObject2.getString("product_price");
                            wallPostmodel.product_desc = jSONObject2.getString("product_desc");
                            wallPostmodel.product_link = jSONObject2.getString("product_link");
                            wallPostmodel.sell_type = jSONObject2.getInt("sell_type");
                            wallPostmodel.images = jSONObject2.getString("images");
                            wallPostmodel.created_at = jSONObject2.getString("created_at");
                            wallPostmodel.likecount = jSONObject2.getInt("like_count");
                            wallPostmodel.commentCount = jSONObject2.getInt("comment_count");

                            wallPostmodel.selfLike = jSONObject2.getInt("Self_Likes");

                            for (int i2 = 0; i2 < jSONObject2.getJSONArray("Likes").length(); i2++) {
                                JSONObject jSONObject3 = jSONObject2.getJSONArray("Likes").getJSONObject(i2);
                                arrayList.add(jSONObject3.getString("d_pic"));
                                arrayList2.add(jSONObject3.getString("name"));
                                arrayList3.add(jSONObject3.getString("customer_id"));
                            }
                            Object[] toArray = arrayList.toArray();
                            Object[] toArray2 = arrayList2.toArray();
                            arrayList3.toArray();
                            wallPostmodel.Like_imges = (String[]) Arrays.copyOf(toArray, toArray.length, String[].class);
                            wallPostmodel.Like_name = (String[]) Arrays.copyOf(toArray2, toArray2.length, String[].class);
                        }

                        postmodels.add(wallPostmodel);
                        wallPost.getAdapter().notifyItemRangeInserted(wallPost.getAdapter().getItemCount(), postmodels.size() - 1);

                    }

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    /* renamed from: com.digital.gnsbook.Fragment.WallPostFragment$3 */
    class C09343 implements Response.ErrorListener {
        C09343() {
        }

        public void onErrorResponse(VolleyError volleyError) {
            porogress.setVisibility(View.GONE);
        }
    }

}
