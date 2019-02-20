package com.digital.gnsbook.Fragment;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.net.Uri.Builder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.support.v4.widget.NestedScrollView.OnScrollChangeListener;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.android.volley.AuthFailureError;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.load.Key;
import com.digital.gnsbook.Activity.ProfilePage;
import com.digital.gnsbook.Activity.SpillTree;
import com.digital.gnsbook.Activity.UpdateProfile;
import com.digital.gnsbook.Config.APIs;
import com.digital.gnsbook.Config.AppController;
import com.digital.gnsbook.Global;
import com.digital.gnsbook.Model.WallPostmodel;
import com.digital.gnsbook.ViewDialog;
import com.httpgnsbook.gnsbook.R;
import com.mikelau.croperino.Croperino;
import com.mikelau.croperino.CroperinoConfig;
import com.mikelau.croperino.CroperinoFileUtil;
import com.squareup.picasso.Picasso;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.angmarch.views.NiceSpinner;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ProfileFragment extends Fragment {
    ImageView DP;
    TextView Name;
    ImageView banner;
    String cappingAmount = null;
    TextView city;
    int count = 10;
    ViewDialog dialog;
    FloatingActionButton fab;
    FloatingActionButton fabcommunity;
    private int offset = 0;
    private PopupMenu popupMenu;
    CardView porogress;
    ArrayList<WallPostmodel> postmodels = new ArrayList();
    RecyclerView wallPost;

    /* renamed from: com.digital.gnsbook.Fragment.ProfileFragment$1 */
    class C04541 implements OnClickListener {
        C04541() {
        }

        public void onClick(View view) {
           Statistics(view);
        }
    }

    /* renamed from: com.digital.gnsbook.Fragment.ProfileFragment$2 */
    class C04552 implements OnClickListener {
        C04552() {
        }

        public void onClick(View view) {
            ProfileFragment.this.startActivity(new Intent(ProfileFragment.this.getActivity(), SpillTree.class).addFlags(268435456));
        }
    }

    /* renamed from: com.digital.gnsbook.Fragment.ProfileFragment$9 */
    class C04589 implements OnClickListener {
        C04589() {
        }

        public void onClick(View view) {
            ProfileFragment.this.Updatecappinglimit();
        }
    }

    public class UserDetailTask extends AsyncTask<String, String, String> {
        HttpURLConnection conn;
        ViewDialog pdLoading = new ViewDialog(ProfileFragment.this.getActivity());
        URL url = null;

        protected void onCancelled() {
        }

        protected void onPreExecute() {
            super.onPreExecute();
            this.pdLoading.show();
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

        protected void onPostExecute(String Responce) {
            this.pdLoading.dismiss();
            try {
                JSONObject jSONObject = new JSONObject(Responce);
                if (jSONObject.getBoolean("status")) {
                   JSONObject str = jSONObject.getJSONArray("result").getJSONObject(0);
                    Global.DP = str.getString("d_pic");
                    Global.Banner = str.getString("b_pic");
                    Global.City = str.getString("city");

                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append(APIs.Dp);
                    stringBuilder.append(Global.DP);
                    Picasso.get().load(stringBuilder.toString()).into(ProfileFragment.this.DP);
                    stringBuilder = new StringBuilder();
                    stringBuilder.append(APIs.Banner);
                    stringBuilder.append(Global.Banner);
                    Picasso.get().load(stringBuilder.toString()).error((int) R.drawable.landing_bg).into(ProfileFragment.this.banner);
                    ProfileFragment.this.Name.setText(Global.name);
                    ProfileFragment.this.city.setText(Global.City);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    /* renamed from: com.digital.gnsbook.Fragment.ProfileFragment$3 */
    class C09253 extends Adapter {

        /* renamed from: com.digital.gnsbook.Fragment.ProfileFragment$3$Holder */
        class Holder extends ViewHolder {
            TextView date;
            ImageView dp;
            ImageView imgPost;
            TextView name;
            ImageView share;
            TextView textPost;
            TextView wpTexttitile;

            public Holder(@NonNull View view) {
                super(view);
                this.dp = (ImageView) view.findViewById(R.id.wpDP);
                this.imgPost = (ImageView) view.findViewById(R.id.wpImage);
                this.share = (ImageView) view.findViewById(R.id.wpShare);
                this.name = (TextView) view.findViewById(R.id.wpcname);
                this.date = (TextView) view.findViewById(R.id.wpDate);
                this.textPost = (TextView) view.findViewById(R.id.wpText);
                this.wpTexttitile = (TextView) view.findViewById(R.id.wpTexttitile);
            }
        }

        C09253() {
        }

        @NonNull
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            return new Holder(LayoutInflater.from(ProfileFragment.this.getActivity()).inflate(R.layout.wallpostadapter, viewGroup, false));
        }

        public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
            Picasso picasso;
            StringBuilder stringBuilder;
            Holder holder = (Holder) viewHolder;
            final WallPostmodel wallPostmodel = (WallPostmodel) ProfileFragment.this.postmodels.get(i);
            holder.name.setText(wallPostmodel.name);
            holder.date.setText(wallPostmodel.created_at);
            holder.textPost.setText(wallPostmodel.description);
            holder.wpTexttitile.setText(wallPostmodel.title);
            holder.share.setTag(wallPostmodel);
            holder.share.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    String shareBody = wallPostmodel.title;
                    Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                    sharingIntent.setType("text/plain");
                    sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, shareBody);
                    sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, wallPostmodel.description);
                    startActivity(Intent.createChooser(sharingIntent, "https://www.gnsbook.com").setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));     }
            });
            if (!wallPostmodel.description.equals("")) {
                if (!wallPostmodel.description.isEmpty()) {
                    holder.textPost.setVisibility(0);
                    if (!wallPostmodel.description.equals("")) {
                        if (wallPostmodel.description.isEmpty()) {
                            holder.imgPost.setVisibility(0);
                            picasso = Picasso.get();
                            stringBuilder = new StringBuilder();
                            stringBuilder.append(APIs.Dp);
                            stringBuilder.append(wallPostmodel.logo);
                            picasso.load(stringBuilder.toString()).into(holder.dp);
                            picasso = Picasso.get();
                            stringBuilder = new StringBuilder();
                            stringBuilder.append(APIs.postImg);
                            stringBuilder.append(wallPostmodel.images);
                            picasso.load(stringBuilder.toString()).into(holder.imgPost);
                        }
                    }
                    holder.imgPost.setVisibility(8);
                    picasso = Picasso.get();
                    stringBuilder = new StringBuilder();
                    stringBuilder.append(APIs.Dp);
                    stringBuilder.append(wallPostmodel.logo);
                    picasso.load(stringBuilder.toString()).into(holder.dp);
                    picasso = Picasso.get();
                    stringBuilder = new StringBuilder();
                    stringBuilder.append(APIs.postImg);
                    stringBuilder.append(wallPostmodel.images);
                    picasso.load(stringBuilder.toString()).into(holder.imgPost);
                }
            }
            holder.textPost.setVisibility(8);
            if (wallPostmodel.description.equals("")) {
                if (wallPostmodel.description.isEmpty()) {
                    holder.imgPost.setVisibility(0);
                    picasso = Picasso.get();
                    stringBuilder = new StringBuilder();
                    stringBuilder.append(APIs.Dp);
                    stringBuilder.append(wallPostmodel.logo);
                    picasso.load(stringBuilder.toString()).into(holder.dp);
                    picasso = Picasso.get();
                    stringBuilder = new StringBuilder();
                    stringBuilder.append(APIs.postImg);
                    stringBuilder.append(wallPostmodel.images);
                    picasso.load(stringBuilder.toString()).into(holder.imgPost);
                }
            }
            holder.imgPost.setVisibility(8);
            picasso = Picasso.get();
            stringBuilder = new StringBuilder();
            stringBuilder.append(APIs.Dp);
            stringBuilder.append(wallPostmodel.logo);
            picasso.load(stringBuilder.toString()).into(holder.dp);
            picasso = Picasso.get();
            stringBuilder = new StringBuilder();
            stringBuilder.append(APIs.postImg);
            stringBuilder.append(wallPostmodel.images);
            picasso.load(stringBuilder.toString()).into(holder.imgPost);
        }

        public int getItemCount() {
            return ProfileFragment.this.postmodels.size();
        }
    }

    /* renamed from: com.digital.gnsbook.Fragment.ProfileFragment$4 */
    class C09264 implements OnScrollChangeListener {
        C09264() {
        }

        public void onScrollChange(NestedScrollView nestedScrollView, int i, int i2, int i3, int i4) {
            if (i2 == nestedScrollView.getChildAt(0).getMeasuredHeight() - nestedScrollView.getMeasuredHeight() && count > 0) {
                ProfileFragment.this.offset = ProfileFragment.this.offset + 10;
                ProfileFragment.this.getTimelinePost();
                ProfileFragment.this.porogress.setVisibility(0);
            }
        }
    }

    /* renamed from: com.digital.gnsbook.Fragment.ProfileFragment$5 */
    class C09275 implements Listener<String> {
        C09275() {
        }

        public void onResponse(String s) {
           porogress.setVisibility(View.GONE);
            try {
                JSONObject jSONObject = new JSONObject(s);
                int i = 0;
                if (jSONObject.getBoolean("status")) {
                  JSONArray str = jSONObject.getJSONArray("result");
                    while (i < str.length()) {
                        jSONObject = str.getJSONObject(i);
                        WallPostmodel wallPostmodel = new WallPostmodel();
                        wallPostmodel.logo = jSONObject.getString("d_pic");
                        wallPostmodel.company_id = jSONObject.getString("customer_id");
                        wallPostmodel.title = jSONObject.getString("title");
                        wallPostmodel.updated_at = jSONObject.getString("updated_at");
                        wallPostmodel.description = jSONObject.getString("description");
                        wallPostmodel.name = jSONObject.getString("name");
                        wallPostmodel.images = jSONObject.getString("images");
                        wallPostmodel.created_at = jSONObject.getString("created_at");
                        ProfileFragment.this.postmodels.add(wallPostmodel);
                        ProfileFragment.this.wallPost.getAdapter().notifyItemRangeInserted(ProfileFragment.this.wallPost.getAdapter().getItemCount(), ProfileFragment.this.postmodels.size() - 1);
                        i++;
                    }
                }else {
                ProfileFragment.this.count = 0;}

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    /* renamed from: com.digital.gnsbook.Fragment.ProfileFragment$6 */
    class C09286 implements ErrorListener {
        C09286() {
        }

        public void onErrorResponse(VolleyError volleyError) {
            ProfileFragment.this.porogress.setVisibility(8);
        }
    }

    private class OnDismissListener implements PopupMenu.OnDismissListener {
        public void onDismiss(PopupMenu popupMenu) {
        }

        private OnDismissListener() {
        }
    }

    private class OnMenuItemClickListener implements PopupMenu.OnMenuItemClickListener {
        private OnMenuItemClickListener() {
        }

        public boolean onMenuItemClick(MenuItem menuItem) {
            switch (menuItem.getItemId()) {
                case R.id.upAccountsetting:
                    ProfileFragment.this.startActivity(new Intent(ProfileFragment.this.getActivity(), ProfilePage.class));
                    break;
                case R.id.upBp:
                   /* StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("IMG_");
                    stringBuilder.append(System.currentTimeMillis());
                    stringBuilder.append(".jpg");
                    menuItem = new CroperinoConfig(stringBuilder.toString(), "/gnsbook/Pictures", "/sdcard/gnsbook/Pictures");
                    CroperinoFileUtil.setupDirectory(ProfileFragment.this.getActivity());
                    if (CroperinoFileUtil.verifyStoragePermissions(ProfileFragment.this.getActivity()).booleanValue() != null) {
                        Croperino.prepareGallery(ProfileFragment.this.getActivity());
                    }*/
                    return true;
                case R.id.upCApingLimit:
                    break;
                case R.id.upDp:
                    ProfileFragment.this.showDialoge();
                    return true;
                default:
                    break;
            }
            CapingLimit();
            return true;
        }
    }



    public View onCreateView(LayoutInflater layoutinflater, ViewGroup viewGroup, Bundle bundle) {
       View layoutInflater = layoutinflater.inflate(R.layout.activity_profile_page, viewGroup, false);
        this.wallPost = (RecyclerView) layoutInflater.findViewById(R.id.rvprofile_wire);
        this.banner = (ImageView) layoutInflater.findViewById(R.id.prBanner);
        this.porogress = (CardView) layoutInflater.findViewById(R.id.frgprogrssview);
        this.DP = (ImageView) layoutInflater.findViewById(R.id.prDP);
        this.Name = (TextView) layoutInflater.findViewById(R.id.prName);
        this.city = (TextView) layoutInflater.findViewById(R.id.prcity);
        this.wallPost.setLayoutManager(new LinearLayoutManager(getActivity()));
        this.dialog = new ViewDialog(getActivity());
        this.fab = (FloatingActionButton) layoutInflater.findViewById(R.id.fabSetting);
        this.fabcommunity = (FloatingActionButton) layoutInflater.findViewById(R.id.fabcommunity);
        this.fab.setOnClickListener(new C04541());
        this.fabcommunity.setOnClickListener(new C04552());
        getTimelinePost();
        this.wallPost.setAdapter(new C09253());
        new UserDetailTask().execute(new String[]{Global.customerid});
        NestedScrollView nestedScrollView = (NestedScrollView) layoutInflater.findViewById(R.id.profilefrgScroll);
        if (nestedScrollView != null) {
            nestedScrollView.setOnScrollChangeListener(new C09264());
        }
        return layoutInflater;
    }

    private void getTimelinePost() {
        AppController.getInstance().addToRequestQueue(new StringRequest(1, APIs.timelineAPI, new C09275(), new C09286()) {
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> hashMap = new HashMap();
                hashMap.put("company_id", "1");
                hashMap.put("limit", "10");
                hashMap.put("offset", String.valueOf(ProfileFragment.this.offset));
                return hashMap;
            }
        });
    }

    private void CapingLimit() {
        View inflate = LayoutInflater.from(getActivity()).inflate(R.layout.capping_limit, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        Button button = (Button) inflate.findViewById(R.id.updatecaping);
        ((TextView) inflate.findViewById(R.id.cappingampunt)).setText(String.valueOf(Global.Cappinglimt));
        NiceSpinner niceSpinner = (NiceSpinner) inflate.findViewById(R.id.capping_spinner);
        final List linkedList = new LinkedList(Arrays.asList(new String[]{"5000", "7500", "10000", "20000", "30000", "50000"}));
        niceSpinner.attachDataSource(linkedList);
        niceSpinner.addOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
                ProfileFragment.this.cappingAmount = (String) linkedList.get(i);
            }
        });
        button.setOnClickListener(new C04589());
        builder.setView(inflate);
        AlertDialog create = builder.create();
        create.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        create.show();
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
        LayoutParams attributes = window.getAttributes();
        attributes.gravity = 48;
        attributes.y = 80;
        attributes.flags &= -3;
        window.setAttributes(attributes);
        create.show();
        linearLayout2.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                create.dismiss();
                ProfileFragment.this.startActivity(new Intent(ProfileFragment.this.getActivity(), UpdateProfile.class).putExtra("type", 0));
            }
        });
        linearLayout.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                create.dismiss();
                ProfileFragment.this.startActivity(new Intent(ProfileFragment.this.getActivity(), UpdateProfile.class).putExtra("type", 1));
            }
        });
    }

    public void onRequestPermissionsResult(int i, @NonNull String[] strArr, @NonNull int[] iArr) {
        super.onRequestPermissionsResult(i, strArr, iArr);
        if (i == 1) {
            Object obj = null;
            Object obj2 = null;
            for (i = 0; i < strArr.length; i++) {
                String str = strArr[i];
                int i2 = iArr[i];
                if (str.equals("android.permission.READ_EXTERNAL_STORAGE") && i2 == 0) {
                    obj = 1;
                }
                if (str.equals("android.permission.WRITE_EXTERNAL_STORAGE") && i2 == 0) {
                    obj2 = 1;
                }
            }
            if (obj != null && obj2 != null) {
                Croperino.prepareGallery(getActivity());
            }
        }
    }

    private void Statistics(final View view) {
        this.dialog.show();
        AppController.getInstance().addToRequestQueue(new StringRequest(1, APIs.Statistics, new Listener<String>() {
            public void onResponse(String s) {
                ProfileFragment.this.dialog.dismiss();
                try {
                    JSONObject jSONObject = new JSONObject(s);
                    if (jSONObject.getBoolean("status") ) {
                        Global.Cappinglimt = jSONObject.getJSONArray("result").getJSONObject(0).getInt("capping");
                        popupMenu = new PopupMenu(ProfileFragment.this.getContext(), view, 17);
                        popupMenu.setOnDismissListener(new OnDismissListener());
                        popupMenu.setOnMenuItemClickListener(new OnMenuItemClickListener());
                        popupMenu.inflate(R.menu.profile_menu);
                       Menu str = popupMenu.getMenu();
                        if (Global.premium_status >= 1) {
                            str.findItem(R.id.upCApingLimit).setVisible(true);
                        }
                        ProfileFragment.this.popupMenu.show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new ErrorListener() {
            public void onErrorResponse(VolleyError volleyError) {
                ProfileFragment.this.dialog.dismiss();
            }
        }) {
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> hashMap = new HashMap();
                hashMap.put("customer_id", Global.customerid);
                return hashMap;
            }
        });
    }

    private void Updatecappinglimit() {
        this.dialog.show();
        AppController.getInstance().addToRequestQueue(new StringRequest(1, APIs.UpadteCapping, new Listener<String>() {
            public void onResponse(String str) {
                ProfileFragment.this.dialog.dismiss();
                try {
                    JSONObject jSONObject = new JSONObject(str);
                    if (jSONObject.getBoolean("status")) {
                        Global.successDilogue(ProfileFragment.this.getActivity(), jSONObject.getString("result"));
                    } else {
                        Global.failedDilogue(ProfileFragment.this.getActivity(), jSONObject.getString("result"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new ErrorListener() {
            public void onErrorResponse(VolleyError volleyError) {
                ProfileFragment.this.dialog.dismiss();
            }
        }) {
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> hashMap = new HashMap();
                hashMap.put("customer_id", Global.customerid);
                hashMap.put("claimed_capping", ProfileFragment.this.cappingAmount);
                return hashMap;
            }
        });
    }
}
