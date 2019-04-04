package com.digital.gnsbook.Activity;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.digital.gnsbook.Config.APIs;
import com.digital.gnsbook.Config.AppController;
import com.digital.gnsbook.Model.Top_Performer;
import com.httpgnsbook.gnsbook.R;
import com.digital.gnsbook.Extra.RecyclerViewItemDecorator;
import com.digital.gnsbook.ViewDialog;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TopPerformer extends AppCompatActivity {

    ArrayList<Top_Performer> componyModel = new ArrayList<>();
    RecyclerView recyclerView;
    ViewDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toperformer);

        dialog = new ViewDialog(this);
        recyclerView = findViewById(R.id.rvtoplist);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        int spaceInPixels = 10;
        recyclerView.addItemDecoration(new RecyclerViewItemDecorator(spaceInPixels));
        recyclerView.setAdapter(new RecyclerView.Adapter() {
            @NonNull
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                View view = LayoutInflater.from(TopPerformer.this).inflate(R.layout.componylist,viewGroup,false);
                return new Holder(view);
            }

            @Override
            public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

                Holder holder=(Holder)viewHolder;
                Top_Performer componymodel=componyModel.get(i);


                holder.name.setText(componymodel.name + " " +componymodel.last_name);
                // holder.desc.setText(componymodel.description);
                Picasso.get()
                        .load("http://gnsbook.com/dpic/"+componymodel.d_pic)
                        .into(holder.dp); }

            @Override
            public int getItemCount() {
                return componyModel.size();
            }


            class Holder extends RecyclerView.ViewHolder {
                ImageView dp ;
                RelativeLayout Follow;
                TextView name  , desc;
                public Holder(@NonNull View itemView) {
                    super(itemView);


                    dp=itemView.findViewById(R.id.cdp);
                    name=itemView.findViewById(R.id.cName);
                    Follow=itemView.findViewById(R.id.cFollow);
                    desc=itemView.findViewById(R.id.cDesc);

                }
            }
        });
        getComponyData();
    }

    private void getComponyData() {
        dialog.show();
        AppController.getInstance().addToRequestQueue(new StringRequest(StringRequest.Method.POST, APIs.topperformerAPI, new Response.Listener<String>() {
                                                          @Override
                                                          public void onResponse(String response) {
                                                              dialog.dismiss();
                                                              try {
                                                                  JSONObject jsonObject = new JSONObject(response);


                                                                  if (jsonObject.getBoolean("status")){

                                                                      JSONArray jsonArray = jsonObject.getJSONArray("result");
                                                                      for (int i = 0 ; i < jsonArray.length() ; i++){

                                                                          JSONObject object = jsonArray.getJSONObject(i);
                                                                          Top_Performer topperformermodel = new Top_Performer();

                                                                          topperformermodel.agent_status =object.getString("agent_status");
                                                                          topperformermodel.customer_id =object.getString("customer_id");
                                                                          topperformermodel.referral_id =object.getString("referral_id");
                                                                          topperformermodel.social_networks =object.getString("social_networks");
                                                                          topperformermodel.status =object.getString("status");
                                                                          topperformermodel.spill_id =object.getString("spill_id");
                                                                          topperformermodel.agent_id =object.getString("agent_id");
                                                                          topperformermodel.d_pic =object.getString("d_pic");
                                                                          topperformermodel.id =object.getString("id");
                                                                          topperformermodel.b_pic =object.getString("b_pic");
                                                                          topperformermodel.updated_at =object.getString("updated_at");
                                                                          topperformermodel.email =object.getString("email");
                                                                          topperformermodel.last_name =object.getString("last_name");
                                                                          topperformermodel.name =object.getString("name");
                                                                          topperformermodel.created_at =object.getString("created_at");
                                                                          //   topperformermodel.added_time =object.getString("banner");
                                                                          topperformermodel.mobile=object.getString("mobile");

                                                                          componyModel.add(topperformermodel);
                                                                          recyclerView.getAdapter().notifyDataSetChanged();
                                                                      }


                                                                  }


                                                              } catch (JSONException e) {
                                                                  e.printStackTrace();
                                                              }


                                                          }
                                                      }, new Response.ErrorListener() {
                                                          @Override
                                                          public void onErrorResponse(VolleyError error) {
                                                              dialog.dismiss();
                                                              String body = null;
                                                              //get status code here
                                                              String statusCode = String.valueOf(error.networkResponse.statusCode);
                                                              //get response body and parse with appropriate encoding
                                                              if(error.networkResponse.data!=null) {
                                                                  try {
                                                                      body = new String(error.networkResponse.data,"UTF-8");
                                                                  } catch (UnsupportedEncodingException e) {
                                                                      e.printStackTrace();
                                                                  }
                                                                  System.out.println("Volley_Error_body "+body);
                                                              }
                                                          }
                                                      })
                                                      {
                                                          @Override
                                                          protected Map<String, String> getParams() throws AuthFailureError {
                                                              Map <String,String> param = new HashMap<String,String>();
                                                              param.put("limit","10");
                                                              param.put("offset","0");
                                                              return param;
                                                          }
                                                      }
        );

    }
}
