package com.digital.gnsbook.Activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.digital.gnsbook.Config.APIs;
import com.digital.gnsbook.Config.AppController;
import com.digital.gnsbook.Extra.DividerDecorator;
import com.digital.gnsbook.Fragment.FriendFragment;
import com.digital.gnsbook.Global;
import com.digital.gnsbook.GnsChat.Chat;
import com.digital.gnsbook.GnsChat.ChatRoomActivity;
import com.digital.gnsbook.GnsChat.ChatRoomRepository;
import com.digital.gnsbook.Model.FriendItem;
import com.digital.gnsbook.Model.FriendResponse;
import com.digital.gnsbook.ViewDialog;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.gson.Gson;
import com.httpgnsbook.gnsbook.R;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChatAcivity extends AppCompatActivity {

    ViewDialog dialog;
    RecyclerView rvFriend;
    List<FriendItem> friendItems = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toperformer);


        setTitle("ChatBook");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        rvFriend = findViewById(R.id.rvfreind);
        rvFriend.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        rvFriend.addItemDecoration(new DividerDecorator(ChatAcivity.this));
        rvFriend.setAdapter(new RecyclerView.Adapter() {
            @NonNull
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                return new Holder(LayoutInflater.from(ChatAcivity.this).inflate(R.layout.searchadapt, viewGroup, false));
            }

            @Override
            public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
                final Holder holder= (Holder) viewHolder;
                final FriendItem model = friendItems.get(i);

                holder.name.setText(model.getName()+" "+model.getLastName());
                holder.city.setText("India");
                Picasso.get().load(APIs.Dp+model.getDPic()).into(holder.dp);



                if (model.getCustomeridFrom()==0)
                {
                    model.friendID = String.valueOf(model.getCustomeridTo());
                }
                else
                {
                    model.friendID = String.valueOf(model.getCustomeridFrom());
                }
                Log.d("Channel_id",model.getChannelId());
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (model.getChannelId().equals("0")){
                            createChannel(String.valueOf(model.getId()),model.getName()+" "+model.getLastName(),model);
                        }else {
                            Intent intent = new Intent(getApplicationContext(), ChatRoomActivity.class);
                            intent.putExtra(ChatRoomActivity.CHAT_ROOM_ID, model.getChannelId());
                            intent.putExtra(ChatRoomActivity.CHAT_cdp, model.getDPic());


                            intent.putExtra(ChatRoomActivity.CHAT_fid, model.friendID);
                            intent.putExtra(ChatRoomActivity.CHAT_ROOM_NAME,model.getName()+" "+model.getLastName() );
                            startActivity(intent);
                        }
                    }
                });

            }

            @Override
            public int getItemCount() {
                return friendItems.size();
            }
            class Holder extends RecyclerView.ViewHolder {
                RelativeLayout Follow;
                TextView city;
                ImageView dp;
                TextView name;

                public Holder(@NonNull View view) {
                    super(view);
                    name = itemView.findViewById(R.id.searchNmae);
                    city = itemView.findViewById(R.id.searchCity);
                    dp = itemView.findViewById(R.id.searchDp);

                }
            }
        });
        //getComponyData();
        getFriendList();
    }

        private void createChannel(final String s, final String s1, final FriendItem model) {
            new ChatRoomRepository(FirebaseFirestore.getInstance()).createRoom(s
                    ,
                    new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {

                            updateChannelId(s,documentReference.getId(),model);


                        }
                    },
                    new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(
                                    getApplicationContext(),
                                    "Some thing went wrong",
                                    Toast.LENGTH_SHORT
                            ).show();
                        }
                    }
            );
        }

        private void updateChannelId(final String s, final String id, final FriendItem model) {
            StringRequest stringRequest = new StringRequest(StringRequest.Method.POST, APIs.updateChannel, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    //   tprice=0;

                    try {
                        JSONObject jsonObject = new JSONObject(response);

                        if (jsonObject.getBoolean("status")){
                            model.setChannelId(s);
                            //  recyclerView.getAdapter().notifyDataSetChanged();
                            Intent intent = new Intent(getApplicationContext(), ChatRoomActivity.class);
                            intent.putExtra(ChatRoomActivity.CHAT_ROOM_ID, id);
                            intent.putExtra(ChatRoomActivity.CHAT_cdp, model.getDPic());
                            intent.putExtra(ChatRoomActivity.CHAT_ROOM_NAME, model.getName()+" "+model.getLastName()  );
                            startActivity(intent);
                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map <String,String> param = new HashMap<String,String>();
                    param.put("id", s);
                    param.put("channel_id", id);
                    return param;
                }
            };
            AppController.getInstance().addToRequestQueue(stringRequest);

        }

        private void getFriendList() {
            //   dialog.show();
            StringRequest stringRequest = new StringRequest(StringRequest.Method.POST, APIs.ChatFriend, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    //   tprice=0;
                    //    dialog.dismiss();

                    try {
                        JSONObject jsonObject = new JSONObject(response);

                        if (jsonObject.getBoolean("status")){

                            Gson gson = new Gson();
                            FriendResponse res = gson.fromJson(response, FriendResponse.class);
                            friendItems = res.getResult();
                            rvFriend.getAdapter().notifyDataSetChanged();}


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
                    param.put("customerid_to", Global.customerid);
                    return param;
                }
            };
            AppController.getInstance().addToRequestQueue(stringRequest);

        }


        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            int id = item.getItemId();

            if (id == android.R.id.home){
                finish();
            }

            return true;
        }
    }
