package com.digital.gnsbook.Fragement;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
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
import com.digital.gnsbook.Activity.New_Post;
import com.digital.gnsbook.Config.APIs;
import com.digital.gnsbook.Config.AppController;
import com.digital.gnsbook.Global;
import com.digital.gnsbook.Model.ComponyModel;
import com.digital.gnsbook.Model.WallPostmodel;
import com.digital.gnsbook.ViewDialog;
import com.httpgnsbook.gnsbook.R;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_comapnypost, container, false);

        wallPost = view.findViewById(R.id.wallPost);
        porogress = view.findViewById(R.id.compprogrssview);

        NewPost = view.findViewById(R.id.adminnewpost);

        if (Global.Company_Admin_Id != Integer.parseInt(Global.customerid)){
            NewPost.setVisibility(View.GONE);
        }

        NewPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), New_Post.class));
            }
        });

        Logo = view.findViewById(R.id.componyLogo);
        Picasso.get().load(APIs.Dp + Global.Company_Logo).into(Logo);


        wallPost.setLayoutManager(new LinearLayoutManager(getActivity()));
        dialog = new ViewDialog(getActivity());

        getTimelinePost();
        NestedScrollView scroller = view.findViewById(R.id.comyScroll);

        if (scroller != null) {

            scroller.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
                @Override
                public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {



                    if (scrollY == (v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight())) {

                        if (count > 0){

                            offset = offset + 10 ;
                            getTimelinePost();
                            porogress.setVisibility(View.VISIBLE);}
                    }
                }
            });
        }
        wallPost.setAdapter(new RecyclerView.Adapter() {@NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(getActivity()).inflate(R.layout.wallpostadapter, viewGroup, false);
            return new Holder(view);
        }

            @Override
            public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

                Holder holder = (Holder) viewHolder;
                final WallPostmodel postmodel = postmodels.get(i);

                holder.name.setText(postmodel.name);
                holder.date.setText(postmodel.created_at);
                holder.textPost.setText(postmodel.description);
                holder.title.setText(postmodel.title);
                holder.share.setTag(postmodel);

                holder.share.setOnClickListener(new View.OnClickListener() {@Override
                public void onClick(View v) {
                    String shareBody = postmodel.title;
                    Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                    sharingIntent.setType("text/plain");
                    sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, shareBody);
                    sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, postmodel.description);
                    startActivity(Intent.createChooser(sharingIntent, "https://www.gnsbook.com"));
                }
                });

                if (postmodel.description.equals("") || postmodel.description.isEmpty()) {
                    holder.textPost.setVisibility(View.GONE);
                } else {
                    holder.textPost.setVisibility(View.VISIBLE);
                }
                if (postmodel.description.equals("") || postmodel.description.isEmpty()) {
                    holder.imgPost.setVisibility(View.GONE);
                } else {
                    holder.imgPost.setVisibility(View.VISIBLE);
                }
                Picasso.get().load(APIs.Dp + postmodel.logo).into(holder.dp);
                Picasso.get().load(APIs.postImg + postmodel.images).into(holder.imgPost);
            }

            @Override
            public int getItemCount() {
                return postmodels.size();
            }
            @Override
            public long getItemId(int position) {
                return position;
            }

            @Override
            public int getItemViewType(int position) {
                return position;
            }
            class Holder extends RecyclerView.ViewHolder {
                ImageView dp, imgPost, share;
                TextView name, date, textPost,title;
                public Holder(@NonNull View itemView) {
                    super(itemView);

                    dp = itemView.findViewById(R.id.wpDP);
                    imgPost = itemView.findViewById(R.id.wpImage);
                    share = itemView.findViewById(R.id.wpShare);
                    name = itemView.findViewById(R.id.wpcname);
                    date = itemView.findViewById(R.id.wpDate);
                    textPost = itemView.findViewById(R.id.wpText);
                    title = itemView.findViewById(R.id.wpTexttitile);

                }
            }
        });

        return view;
    }
    private void getTimelinePost() {

        AppController.getInstance().addToRequestQueue(new StringRequest(StringRequest.Method.POST, APIs.timelineAPI_byid, new Response.Listener < String > () {@Override
        public void onResponse(String response) {
            //     dialog.dismiss();
            porogress.setVisibility(View.GONE);
            try {
                JSONObject jsonObject = new JSONObject(response);

                if (jsonObject.getBoolean("status")) {

                    JSONArray jsonArray = jsonObject.getJSONArray("result");
                    for (int i = 0; i < jsonArray.length(); i++) {

                        JSONObject object = jsonArray.getJSONObject(i);
                        WallPostmodel WallPost = new WallPostmodel();

                        // WallPost.working_hours = object.getString("working_hours");
                        WallPost.logo = object.getString("logo");
                        WallPost.company_id = object.getString("customer_id");
                        WallPost.title = object.getString("title");
                        //  WallPost.updated_at = object.getString("updated_at");
                        WallPost.description = object.getString("description");
                        WallPost.name = object.getString("name");
                        WallPost.images = object.getString("images");
                        WallPost.created_at = object.getString("created_at");

                        postmodels.add(WallPost);
                        //  wallPost.getItemAnimator().endAnimations();
                        //  wallPost.getAdapter().notifyDataSetChanged();
                        wallPost.getAdapter().notifyItemRangeInserted(wallPost.getAdapter().getItemCount(), postmodels.size() - 1);

                    }

                }else {
                    count =  jsonObject.getInt("count");
                }

            } catch(JSONException e) {
                e.printStackTrace();
            }

        }
        },
                new Response.ErrorListener() {@Override
                public void onErrorResponse(VolleyError error) {
                    String body = null;
                    porogress.setVisibility(View.GONE);

                }
                }) {@Override
        protected Map< String,
                String > getParams() throws AuthFailureError {
            Map < String,
                    String > param = new HashMap< String,
                    String >();
            param.put("company_id", "1");
            param.put("limit", "10");
            param.put("offset", String.valueOf(offset));
            return param;
        }
        });

    }


}
