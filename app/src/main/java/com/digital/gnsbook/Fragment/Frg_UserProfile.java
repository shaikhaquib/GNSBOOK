package com.digital.gnsbook.Fragment;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.digital.gnsbook.Activity.ChatAcivity;
import com.digital.gnsbook.Activity.MainActivity;
import com.digital.gnsbook.Activity.ProfilePage;
import com.digital.gnsbook.Activity.UpdateProfile;
import com.digital.gnsbook.Adapter.Profile_wallpostAdapt;
import com.digital.gnsbook.Adapter.WallAdapt;
import com.digital.gnsbook.Config.APIs;
import com.digital.gnsbook.Config.AppController;
import com.digital.gnsbook.Global;
import com.digital.gnsbook.Model.Company_list.Company_list_Model;
import com.digital.gnsbook.Model.Profile_Model.WallPostListModel;
import com.digital.gnsbook.Model.TimeLine_Model.TimeLineItem;
import com.digital.gnsbook.Model.TimeLine_Model.TimeLineResponse;
import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.httpgnsbook.gnsbook.R;
import com.mikelau.croperino.Croperino;
import com.mikelau.croperino.CroperinoFileUtil;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Frg_UserProfile extends Fragment {


    TextView Name;
    ImageView banner,DP,chat,AccountSetting,More,upBanner,upDp;
    TextView city;
    MainActivity activity;
    RecyclerView wallPost;
    List<TimeLineItem> postModel = new ArrayList<>();
    private int offset = 0;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frg_userprofile, container, false);

        activity = (MainActivity) getActivity();



/*
        Initialize
*/

        DP = view.findViewById(R.id.prDP);
        Name = view.findViewById(R.id.prName);
        city = view.findViewById(R.id.prcity);
        banner = view.findViewById(R.id.prBanner);
        wallPost = view.findViewById(R.id.rvprofile_wire);
        chat = view.findViewById(R.id.chat);
        AccountSetting = view.findViewById(R.id.accountsetting);
        More = view.findViewById(R.id.more);
        upBanner = view.findViewById(R.id.upaBanner);
        upDp = view.findViewById(R.id.upDp);



/*
         Setting Value
*/

        DP.setImageDrawable(activity.profileImage.getDrawable());
        Picasso.get().load(APIs.Banner + Global.Banner).error(R.drawable.landing_bg).into(banner);
        Name.setText(Global.name);
        city.setText(Global.City);

/*
        RecyclerView Setting
*/

        wallPost.setHasFixedSize(true);
        wallPost.setNestedScrollingEnabled(false);
        wallPost.setLayoutManager(new LinearLayoutManager(getActivity()));
        wallPost.setAdapter(new Profile_wallpostAdapt(getActivity(),postModel));






/*
        Listeners
*/
        chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), ChatAcivity.class));
            }
        });
        AccountSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), ProfilePage.class).putExtra("type",1));
            }
        });
        More.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        upDp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialoge();
            }
        });
        upBanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CroperinoFileUtil.setupDirectory(getActivity());
                if (CroperinoFileUtil.verifyStoragePermissions(getActivity()) != null) {
                    Croperino.prepareGallery(getActivity());
                }
            }
        });

        new UserDetailTask().execute(new String[]{Global.customerid});


        return view;

    }

    private void getPost() {

        AppController.getInstance().addToRequestQueue(new StringRequest(1, APIs.new_timelineAPI_Profile, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                /*porogress.setVisibility(View.GONE);*/

/*
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

                }*/

                try {
                    JSONObject object = new JSONObject(response);

                    if (object.getBoolean("status")){
                        WallPostListModel Response =  new Gson().fromJson(response, WallPostListModel.class);
                        postModel.addAll(Response.getResult());
                        wallPost.getAdapter().notifyDataSetChanged();
                    }




                } catch (JSONException e) {
                    e.printStackTrace();
                }
                wallPost.getAdapter().notifyDataSetChanged();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
               /* if (refresh.isRefreshing()){
                    refresh.setRefreshing(false);
                }

                if (postModel.size()>0){
                    postModel.remove(postModel.size() - 1);
                    int scrollPosition = postModel.size();
                    rvWallpost.getAdapter().notifyItemRemoved(scrollPosition);
                    isLoading = false;}*/
            }
        }) {
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> hashMap = new HashMap();
                hashMap.put("company_id", "1");
                hashMap.put("customer_id", Global.customerid);
                hashMap.put("limit", "50");
                hashMap.put("offset", String.valueOf(offset));
                return hashMap;
            }
        });
    }

    public void showDialoge() {
        View inflate = LayoutInflater.from(getActivity()).inflate(R.layout.uploadui, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LinearLayout linearLayout = (LinearLayout) inflate.findViewById(R.id.opengallery);
        LinearLayout linearLayout2 = (LinearLayout) inflate.findViewById(R.id.openCamera);
        builder.setView(inflate);
        final AlertDialog create = builder.create();
        Window window = create.getWindow();
        window.setBackgroundDrawable(new ColorDrawable(0));
        WindowManager.LayoutParams attributes = window.getAttributes();
        attributes.gravity = 48;
        attributes.y = 80;
        attributes.flags &= -3;
        window.setAttributes(attributes);
        create.show();
        linearLayout2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                create.dismiss();
                startActivity(new Intent(getActivity(), UpdateProfile.class).putExtra("type", 0).putExtra("isCompany",false));
            }
        });
        linearLayout.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                create.dismiss();
                startActivity(new Intent(getActivity(), UpdateProfile.class).putExtra("type", 1).putExtra("isCompany",false));
            }
        });
    }

    public class UserDetailTask extends AsyncTask<String, String, String> {
        ///   ProgressDialog pdLoading = new ProgressDialog(MainActivity.this);
        HttpURLConnection conn;
        URL url = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            //this method will be running on UI thread
            // pdLoading.setMessage("\tLoading...");
            //dialog.show();

        }


        @Override
        protected String doInBackground(String... params) {
            try {

                // Enter URL address where your php file resides
                //url = new URL("http://192.168.2.2:80/pro/login.inc.php");
                url = new URL(APIs.ProfileDetail);
                //url = new URL("http://www.sd-constructions.com/bhushan/login.inc.php");

            } catch (MalformedURLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                return "exception";
            }
            try {
                // Setup HttpURLConnection class to send and receive data from php and mysql
                conn = (HttpURLConnection)url.openConnection();
                conn.setReadTimeout(Global.READ_TIMEOUT);
                conn.setConnectTimeout(Global.CONNECTION_TIMEOUT);
                conn.setRequestMethod("POST");

                // setDoInput and setDoOutput method depict handling of both send and receive
                conn.setDoInput(true);
                conn.setDoOutput(true);

                // Append parameters to URL
                Uri.Builder builder = new Uri.Builder()
                        .appendQueryParameter("customer_id", params[0]);
                String query = builder.build().getEncodedQuery();

                // Open connection for sending data
                OutputStream os = conn.getOutputStream();
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(os, "UTF-8"));
                writer.write(query);
                writer.flush();
                writer.close();
                os.close();
                conn.connect();

            } catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
                return "exception";
            }

            try {

                int response_code = conn.getResponseCode();

                // Check if successful connection made
                if (response_code == HttpURLConnection.HTTP_OK) {

                    // Read data sent from server
                    InputStream input = conn.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(input));
                    StringBuilder result = new StringBuilder();
                    String line;

                    while ((line = reader.readLine()) != null) {
                        result.append(line);
                    }

                    // Pass data to onPostExecute method
                    return(result.toString());

                }else{

                    return("unsuccessful");
                }

            } catch (IOException e) {
                e.printStackTrace();
                return "exception";
            } finally {
                conn.disconnect();
            }


        }

        @Override
        protected void onPostExecute(String result) {


           // dialog.dismiss();
            try {

                JSONObject jsonObject = new JSONObject(result);

                if (jsonObject.getBoolean("status")){



                    JSONArray jsonArray = jsonObject.getJSONArray("result");
                    JSONObject object = jsonArray.getJSONObject(0);
                    Global.name = object.getString("name") + object.getString("last_name");

                    Global.DP = object.getString("d_pic");
                    Global.Banner = object.getString("b_pic");
                    Global.City = object.getString("city");
                    Global.premium_status = object.getInt("premium_status");


                    DP.setImageDrawable(activity.profileImage.getDrawable());
                    Picasso.get().load(APIs.Banner + Global.Banner).error(R.drawable.landing_bg).into(banner);
                    Name.setText(Global.name);
                    city.setText(Global.City);
                    getPost();



                }

            } catch (JSONException e) {
                e.printStackTrace();
            }


        }
        @Override
        protected void onCancelled() {

        }
    }

}
