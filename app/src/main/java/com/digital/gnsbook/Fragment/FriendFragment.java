package com.digital.gnsbook.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.digital.gnsbook.Activity.FriendProfile;
import com.digital.gnsbook.Config.APIs;
import com.digital.gnsbook.Config.AppController;
import com.digital.gnsbook.Config.DbHelper;
import com.digital.gnsbook.Global;
import com.digital.gnsbook.GnsChat.ChatRoomActivity;
import com.digital.gnsbook.GnsChat.ChatRoomRepository;
import com.digital.gnsbook.GnsChat.CreateRoomActivity;
import com.digital.gnsbook.Model.FriendItem;
import com.digital.gnsbook.Model.FriendResponse;
import com.digital.gnsbook.Model.Top_Performer;
import com.digital.gnsbook.ViewDialog;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.gson.Gson;
import com.httpgnsbook.gnsbook.R;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import in.juspay.godel.ui.uber.FloatingActionButton;

public class FriendFragment extends Fragment {
    ArrayList<Top_Performer> componyModel = new ArrayList();
    ViewDialog dialog;
    RecyclerView rvFriend;
    List<FriendItem> friendItems = new ArrayList<>();


    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View view = layoutInflater.inflate(R.layout.activity_toperformer, viewGroup, false);
        dialog = new ViewDialog(getActivity());
       /* recyclerView = view.findViewById(R.id.rvtoplist);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        recyclerView = view.findViewById(R.id.rvtoplist);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        recyclerView.setAdapter(new C09291());*/



        rvFriend = view.findViewById(R.id.rvfreind);
        rvFriend.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        rvFriend.setAdapter(new RecyclerView.Adapter() {
            @NonNull
            @Override
            public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                return new Holder(LayoutInflater.from(FriendFragment.this.getActivity()).inflate(R.layout.componylist, viewGroup, false));
            }

            @Override
            public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
                final Holder holder= (Holder) viewHolder;
                final FriendItem model = friendItems.get(i);

                holder.name.setText(model.getName()+" "+model.getLastName());
                holder.desc.setText("India");
                Picasso.get().load(APIs.Dp+model.getDPic()).into(holder.dp);
          //      Log.d("BG", model.getBPic());
                Picasso.get().load(APIs.Banner+model.getBPic()).into(holder.bg);

                if (model.getType() == 1){
                    holder.batchclose.setText("Friend");
                }
                else
                {
                    holder.batchclose.setText("Business Friend");
                }

                if (model.getCustomeridFrom()==0)
                {
                    model.friendID = String.valueOf(model.getCustomeridTo());
                }
                else
                {
                    model.friendID = String.valueOf(model.getCustomeridFrom());
                }
                holder.fabChat.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.d("Channel_id",model.friendID);

                        if (model.getChannelId().equals("0")){
                            createChannel(String.valueOf(model.getId()),model.getName()+" "+model.getLastName(),model);
                        }else {
                            Intent intent = new Intent(getActivity(), ChatRoomActivity.class);
                            intent.putExtra(ChatRoomActivity.CHAT_ROOM_ID, model.getChannelId());
                            intent.putExtra(ChatRoomActivity.CHAT_cdp, model.getDPic());
                            intent.putExtra(ChatRoomActivity.CHAT_fid, model.friendID);
                            intent.putExtra(ChatRoomActivity.CHAT_ROOM_NAME,model.getName()+" "+model.getLastName() );
                            startActivity(intent);
                        }
                    }
                });

                holder.fabcommunity.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(getActivity(), FriendProfile.class).putExtra("id",Integer.parseInt(model.friendID)).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                    }
                });

            }

            @Override
            public int getItemCount() {
                return friendItems.size();
            }
            class Holder extends ViewHolder {
                RelativeLayout Follow;
                TextView desc,batchclose;
                LinearLayout view1;
                ImageView dp,bg;
                TextView name;
                ImageView fabcommunity,fabChat;

                public Holder(@NonNull View view) {
                    super(view);
                    this.dp = (ImageView) view.findViewById(R.id.cdp);
                    this.name = (TextView) view.findViewById(R.id.cName);
                    this.Follow = (RelativeLayout) view.findViewById(R.id.cFollow);
                    this.desc = (TextView) view.findViewById(R.id.cDesc);
                    this.batchclose = (TextView) view.findViewById(R.id.batchclose);
                    this.bg = (ImageView) view.findViewById(R.id.bg);
                    this.view1 = (LinearLayout) view.findViewById(R.id.view);

                    fabChat = view.findViewById(R.id.fabChat);
                    fabcommunity = view.findViewById(R.id.fabcommunity);


                    view1.setVisibility(View.VISIBLE);
                    batchclose.setVisibility(View.VISIBLE);

                }
            }
        });
        //getComponyData();
        getFriendList();
        return view;
    }

    private void createChannel(final String s, final String s1, final FriendItem model) {
        dialog.show();
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
                        dialog.dismiss();
                        Toast.makeText(
                                getActivity(),
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
                dialog.dismiss();

                try {
                    JSONObject jsonObject = new JSONObject(response);

                    if (jsonObject.getBoolean("status")){
                        model.setChannelId(s);
                        //  recyclerView.getAdapter().notifyDataSetChanged();
                        Intent intent = new Intent(getActivity(), ChatRoomActivity.class);
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
                dialog.dismiss();
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

/*
    private void getComponyData() {
        this.dialog.show();
        AppController.getInstance().addToRequestQueue(new StringRequest(1, APIs.freinds, new C09302(), new C09313()) {
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> hashMap = new HashMap();
                hashMap.put("data", Global.customerid);
                return hashMap;
            }
        });
    }
*/
}
