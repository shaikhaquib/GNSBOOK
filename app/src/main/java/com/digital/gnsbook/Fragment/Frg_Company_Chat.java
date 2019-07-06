package com.digital.gnsbook.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
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
import com.digital.gnsbook.Global;
import com.digital.gnsbook.GnsChat.ChatRoomActivity;
import com.digital.gnsbook.GnsChat.CompanyChatActivity;
import com.digital.gnsbook.Model.Subscription.Compony_chat_model;
import com.digital.gnsbook.ViewDialog;
import com.google.gson.Gson;
import com.httpgnsbook.gnsbook.R;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Frg_Company_Chat extends Fragment {
    List<Compony_chat_model.Result> componyModel = new ArrayList();
    ViewDialog dialog;
    RecyclerView recyclerView;
    CardView Create;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_componylist, container, false);

        dialog = new ViewDialog(getActivity());
        Create = view.findViewById(R.id.CreateCompony);
        Create.setVisibility(View.GONE);
        recyclerView = view.findViewById(R.id.rvclist);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        recyclerView.setAdapter(new ReacyclerAdapter());
        getChatData();

        return view;
    }

    class ReacyclerAdapter extends RecyclerView.Adapter {

        /* renamed from: com.digital.gnsbook.Activity.Compony_list$1$Holder */
        class Holder extends RecyclerView.ViewHolder {
            RelativeLayout Follow;
            TextView desc;
            ImageView dp,bg;
            TextView name;
            CardView suggestion;

            public Holder(@NonNull View view) {
                super(view);
                dp = (ImageView) view.findViewById(R.id.cdp);
                bg = (ImageView) view.findViewById(R.id.bg);
                name = (TextView) view.findViewById(R.id.cName);
                Follow = (RelativeLayout) view.findViewById(R.id.cFollow);
                desc = (TextView) view.findViewById(R.id.cDesc);
                suggestion=view.findViewById(R.id.suggestion);

            }
        }



        @NonNull
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            return new ReacyclerAdapter.Holder(LayoutInflater.from(getActivity()).inflate(R.layout.componylist, viewGroup, false));
        }

        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
            ReacyclerAdapter.Holder holder = (ReacyclerAdapter.Holder) viewHolder;
            final Compony_chat_model.Result compony_data = componyModel.get(i);
            holder.name.setText(compony_data.getName());
            holder.desc.setText(compony_data.getCity());
            Picasso picasso = Picasso.get();
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(APIs.Dp);
            stringBuilder.append(compony_data.getDPic());
            picasso.load(stringBuilder.toString()).into(holder.dp);
            Picasso.get().load(APIs.Banner+compony_data.getBPic()).into(holder.bg);

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), ChatRoomActivity.class);
                    intent.putExtra(CompanyChatActivity.CHAT_ROOM_ID, compony_data.getChannelId());
                    intent.putExtra(CompanyChatActivity.CHAT_cdp, compony_data.getDPic());
                    intent.putExtra(CompanyChatActivity.CHAT_fid, compony_data.getCustomerId());
                    intent.putExtra(CompanyChatActivity.CHAT_ROOM_NAME,compony_data.getName()+" "+compony_data.getLastName() );
                    startActivity(intent);
                }
            });

        }

        public int getItemCount() {
            return componyModel.size();
        }
    }


    private void getChatData() {
        this.dialog.show();
        AppController.getInstance().addToRequestQueue(new StringRequest(1, APIs.display_chat, new Response.Listener<String>() {
            @Override
            public void onResponse(String responce) {
                dialog.dismiss();
                try {
                    JSONObject jSONObject = new JSONObject(responce);
                    if (jSONObject.getBoolean("status")) {
                        Compony_chat_model comment_Response =  new Gson().fromJson(responce, Compony_chat_model.class);
                        componyModel.addAll(comment_Response.getResult());
                        recyclerView.getAdapter().notifyDataSetChanged();
                        //    getSuggestion();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                dialog.dismiss();
            }
        }) {
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> hashMap = new HashMap();

                hashMap.put("company_id", Global.Company_Id);
                return hashMap;
            }
        });
    }



}
