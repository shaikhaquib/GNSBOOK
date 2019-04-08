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

public class FriendFragment extends Fragment {
    ArrayList<Top_Performer> componyModel = new ArrayList();
    ViewDialog dialog;
    RecyclerView recyclerView ,rvFriend;
    List<FriendItem> friendItems = new ArrayList<>();

    /* renamed from: com.digital.gnsbook.Fragment.FriendFragment$1 */
    class C09291 extends Adapter {

        /* renamed from: com.digital.gnsbook.Fragment.FriendFragment$1$Holder */
        class Holder extends ViewHolder {
            RelativeLayout Follow;
            TextView desc,batchclose;
            ImageView dp;
            TextView name;
            LinearLayout view1;

            public Holder(@NonNull View view) {
                super(view);
                dp = (ImageView) view.findViewById(R.id.cdp);
                name = (TextView) view.findViewById(R.id.cName);
                Follow = (RelativeLayout) view.findViewById(R.id.cFollow);
                desc = (TextView) view.findViewById(R.id.cDesc);
                batchclose = (TextView) view.findViewById(R.id.batchclose);
                view1 =  view.findViewById(R.id.view);
                view1.setVisibility(View.VISIBLE);

                batchclose.setVisibility(View.VISIBLE);

            }
        }


        @NonNull
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            return new Holder(LayoutInflater.from(FriendFragment.this.getActivity()).inflate(R.layout.componylist, viewGroup, false));
        }

        public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
            Holder holder = (Holder) viewHolder;
            final Top_Performer top_Performer = (Top_Performer) FriendFragment.this.componyModel.get(i);
            TextView textView = holder.name;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(top_Performer.name);
            stringBuilder.append(" ");
            stringBuilder.append(top_Performer.last_name);
            textView.setText(stringBuilder.toString());
            holder.desc.setText(top_Performer.city);
            Picasso picasso = Picasso.get();
            stringBuilder = new StringBuilder();
            stringBuilder.append("http://gnsbook.com/dpic/");
            stringBuilder.append(top_Performer.d_pic);
            picasso.load(stringBuilder.toString()).into(holder.dp);
            
            
        }

        public int getItemCount() {
            return FriendFragment.this.componyModel.size();
        }
    }

    /* renamed from: com.digital.gnsbook.Fragment.FriendFragment$2 */
    class C09302 implements Listener<String> {
        C09302() {
        }

        public void onResponse(String responce) {
            FriendFragment.this.dialog.dismiss();
            try {
                JSONArray str = new JSONObject(responce).getJSONArray("result");
                for (int i = 0; i < str.length(); i++) {
                    JSONObject jSONObject = str.getJSONObject(i);
                    Top_Performer top_Performer = new Top_Performer();
                    top_Performer.agent_status = jSONObject.getString("agent_status");
                    top_Performer.customer_id = jSONObject.getString("customer_id");
                    top_Performer.referral_id = jSONObject.getString("referral_id");
                    top_Performer.spill_id = jSONObject.getString("spill_id");
                    top_Performer.agent_id = jSONObject.getString("agent_id");
                    top_Performer.d_pic = jSONObject.getString("d_pic");
                    top_Performer.id = jSONObject.getString(DbHelper.COLUMN_ID);
                    top_Performer.b_pic = jSONObject.getString("b_pic");
                    top_Performer.updated_at = jSONObject.getString("updated_at");
                    top_Performer.email = jSONObject.getString(NotificationCompat.CATEGORY_EMAIL);
                    top_Performer.last_name = jSONObject.getString("last_name");
                    top_Performer.name = jSONObject.getString("name");
                    top_Performer.mobile = jSONObject.getString("mobile");
                    top_Performer.city = jSONObject.getString("city");
                    FriendFragment.this.componyModel.add(top_Performer);
                    FriendFragment.this.recyclerView.getAdapter().notifyDataSetChanged();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    /* renamed from: com.digital.gnsbook.Fragment.FriendFragment$3 */
    class C09313 implements ErrorListener {
        C09313() {
        }

        public void onErrorResponse(VolleyError volleyError) {
            FriendFragment.this.dialog.dismiss();
        }
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View view = layoutInflater.inflate(R.layout.activity_toperformer, viewGroup, false);
        dialog = new ViewDialog(getActivity());
        recyclerView = view.findViewById(R.id.rvtoplist);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        recyclerView = view.findViewById(R.id.rvtoplist);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        recyclerView.setAdapter(new C09291());

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

                Log.d("Channel_id",model.getChannelId());
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (model.getChannelId().equals("0")){
                            createChannel(String.valueOf(model.getId()),model.getName()+" "+model.getLastName(),model);
                        }else {
                            Intent intent = new Intent(getActivity(), ChatRoomActivity.class);
                            intent.putExtra(ChatRoomActivity.CHAT_ROOM_ID, model.getChannelId());
                            intent.putExtra(ChatRoomActivity.CHAT_cdp, model.getDPic());
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
            class Holder extends ViewHolder {
                RelativeLayout Follow;
                TextView desc,batchclose;
                LinearLayout view1;
                ImageView dp;
                TextView name;

                public Holder(@NonNull View view) {
                    super(view);
                    this.dp = (ImageView) view.findViewById(R.id.cdp);
                    this.name = (TextView) view.findViewById(R.id.cName);
                    this.Follow = (RelativeLayout) view.findViewById(R.id.cFollow);
                    this.desc = (TextView) view.findViewById(R.id.cDesc);
                    this.batchclose = (TextView) view.findViewById(R.id.batchclose);
                    this.view1 = (LinearLayout) view.findViewById(R.id.view);

                    batchclose.setText("Friend");

                    view1.setVisibility(View.VISIBLE);
                    batchclose.setVisibility(View.VISIBLE);

                }
            }
        });
        getComponyData();
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
                        recyclerView.getAdapter().notifyDataSetChanged();
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
}
