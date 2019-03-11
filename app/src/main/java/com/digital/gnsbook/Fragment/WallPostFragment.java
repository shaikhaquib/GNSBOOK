package com.digital.gnsbook.Fragment;

import android.os.Bundle;
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
import com.android.volley.AuthFailureError;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.digital.gnsbook.Config.APIs;
import com.digital.gnsbook.Config.AppController;
import com.digital.gnsbook.Global;
import com.digital.gnsbook.Model.WallPostmodel;
import com.digital.gnsbook.New_WallPostAdapt;
import com.digital.gnsbook.ViewDialog;
import com.digital.gnsbook.WallPostAdapt;
import com.httpgnsbook.gnsbook.R;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class WallPostFragment extends Fragment {
    int count = 10;
    ViewDialog dialog;
    private int offset = 0;
    CardView porogress;
    ArrayList<WallPostmodel> postmodels = new ArrayList();
    RecyclerView wallPost;
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

        public void onResponse(String s) {
           porogress.setVisibility(View.GONE);
           if (swipeRefreshLayout.isRefreshing()){
               swipeRefreshLayout.setRefreshing(false);
           }
            try {
                  JSONArray str = new JSONArray(s);
                    for (int i = 0; i < str.length(); i++) {
                        JSONObject jSONObject = str.getJSONObject(i);

                        JSONObject jSONObject2=jSONObject.getJSONArray("result").getJSONObject(0);
                        WallPostmodel wallPostmodel = new WallPostmodel();
                        ArrayList arrayList = new ArrayList();
                        ArrayList arrayList2 = new ArrayList();
                        ArrayList arrayList3 = new ArrayList();

                        wallPostmodel.type = jSONObject.getString("type");

                        if (wallPostmodel.type.equals("1")) {




                            wallPostmodel.logo = jSONObject2.getString("logo");
                            wallPostmodel.id = jSONObject2.getString("id");
                            wallPostmodel.company_id = jSONObject2.getString("customer_id");
                            wallPostmodel.title = jSONObject2.getString("title");
                            wallPostmodel.description = jSONObject2.getString("description");
                            wallPostmodel.name = jSONObject2.getString("name");
                            wallPostmodel.images = jSONObject2.getString("image");
                            wallPostmodel.created_at = jSONObject2.getString("created_at");
                            wallPostmodel.likecount = jSONObject2.getInt("like_count");
                            wallPostmodel.commentCount = jSONObject2.getInt("comment_count");
                          //  wallPostmodel.selfLike = jSONObject2.getInt("Self_Likes");

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
                        else {
                            wallPostmodel.logo = jSONObject2.getString("logo");
                            wallPostmodel.id = jSONObject2.getString("id");
                            wallPostmodel.name = jSONObject2.getString("name");

//                            wallPostmodel.company_id = jSONObject2.getString("customer_id");
                            wallPostmodel.product_name = jSONObject2.getString("product_name");
                            wallPostmodel.product_cat = jSONObject2.getString("product_cat");
                            wallPostmodel.product_price = jSONObject2.getString("product_price");
                            wallPostmodel.product_desc = jSONObject2.getString("product_desc");
                            wallPostmodel.product_link = jSONObject2.getString("product_link");
                            wallPostmodel.images = jSONObject2.getString("images");
                            wallPostmodel.created_at = jSONObject2.getString("created_at");
                            wallPostmodel.likecount = jSONObject2.getInt("like_count");
                            wallPostmodel.commentCount = jSONObject2.getInt("comment_count");
                         //   wallPostmodel.selfLike = jSONObject2.getInt("Self_Likes");

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

                        WallPostFragment.this.postmodels.add(wallPostmodel);
                        wallPost.getAdapter().notifyItemRangeInserted(WallPostFragment.this.wallPost.getAdapter().getItemCount(), WallPostFragment.this.postmodels.size() - 1);

                    }

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
        swipeRefreshLayout = layoutInflater.findViewById(R.id.mnSwipe);
        wallPost.setItemAnimator(null);
        porogress = layoutInflater.findViewById(R.id.progrssview);
        wallPost.setLayoutManager(new LinearLayoutManager(getActivity()));
        dialog = new ViewDialog(getActivity());
        getTimelinePost();
        wallPost.setAdapter(new  New_WallPostAdapt(this.postmodels, getActivity()));
        NestedScrollView nestedScrollView =  layoutInflater.findViewById(R.id.myScroll);
        if (nestedScrollView != null) {
            nestedScrollView.setOnScrollChangeListener(new C09321());
        }

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(true);
                offset = 0;
                postmodels.clear();
                wallPost.getAdapter().notifyDataSetChanged();
                getTimelinePost();
            }
        });
        return layoutInflater;
    }

    private void getTimelinePost() {
        AppController.getInstance().addToRequestQueue(new StringRequest(1, APIs.new_timelineAPI, new C09332(), new C09343()) {
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
