package com.digital.gnsbook.Fragment;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
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
import com.digital.gnsbook.Activity.ChatAcivity;
import com.digital.gnsbook.Activity.ProfilePage;
import com.digital.gnsbook.Activity.SpillTree;
import com.digital.gnsbook.Activity.UpdateProfile;
import com.digital.gnsbook.Config.APIs;
import com.digital.gnsbook.Config.AppController;
import com.digital.gnsbook.Global;
import com.digital.gnsbook.Model.WallPostmodel;
import com.digital.gnsbook.ViewDialog;
import com.digital.gnsbook.Adapter.WallPostAdapt;
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
                    Name.setText(Global.name);
                    city.setText(Global.City);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    /* renamed from: com.digital.gnsbook.Fragment.ProfileFragment$3 */

    /* renamed from: com.digital.gnsbook.Fragment.ProfileFragment$4 */
    class C09264 implements OnScrollChangeListener {
        C09264() {
        }

        public void onScrollChange(NestedScrollView nestedScrollView, int i, int i2, int i3, int i4) {
            if (i2 == nestedScrollView.getChildAt(0).getMeasuredHeight() - nestedScrollView.getMeasuredHeight() && count > 0) {
                offset = ProfileFragment.this.offset + 10;
                getTimelinePost();
                porogress.setVisibility(View.VISIBLE);
            }
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
                    startActivity(new Intent(getActivity(), ProfilePage.class).putExtra("type",1));
                    break;
                case R.id.upBp:
                    CroperinoFileUtil.setupDirectory(ProfileFragment.this.getActivity());
                    if (CroperinoFileUtil.verifyStoragePermissions(getActivity()) != null) {
                        Croperino.prepareGallery(getActivity());
                    }
                    return true;
                case R.id.upCApingLimit:
                    CapingLimit();
                    break;
                case R.id.upDp:
                    showDialoge();
                    return true;
                default:
                    break;
            }
            return true;
        }
    }



    public View onCreateView(LayoutInflater layoutinflater, ViewGroup viewGroup, Bundle bundle) {
       View layoutInflater = layoutinflater.inflate(R.layout.activity_profile_page, viewGroup, false);
        wallPost = (RecyclerView) layoutInflater.findViewById(R.id.rvprofile_wire);
        banner = (ImageView) layoutInflater.findViewById(R.id.prBanner);
        porogress = (CardView) layoutInflater.findViewById(R.id.frgprogrssview);
        DP = (ImageView) layoutInflater.findViewById(R.id.prDP);
        Name = (TextView) layoutInflater.findViewById(R.id.prName);
        city = (TextView) layoutInflater.findViewById(R.id.prcity);
        wallPost.setLayoutManager(new LinearLayoutManager(getActivity()));
        dialog = new ViewDialog(getActivity());
        fab = (FloatingActionButton) layoutInflater.findViewById(R.id.fabSetting);
        fabcommunity = (FloatingActionButton) layoutInflater.findViewById(R.id.fabcommunity);
        fab.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Statistics(v);
            }
        });
        fabcommunity.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProfileFragment.this.getActivity(), ChatAcivity.class));
            }
        });
        wallPost.setAdapter(new WallPostAdapt(this.postmodels, getActivity()));

        getTimelinePost();
        new UserDetailTask().execute(new String[]{Global.customerid});
        NestedScrollView nestedScrollView = (NestedScrollView) layoutInflater.findViewById(R.id.profilefrgScroll);
        if (nestedScrollView != null) {
            nestedScrollView.setOnScrollChangeListener(new C09264());
        }
        return layoutInflater;
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
                ProfileFragment.this.startActivity(new Intent(ProfileFragment.this.getActivity(), UpdateProfile.class).putExtra("type", 0).putExtra("isCompany",false));
            }
        });
        linearLayout.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                create.dismiss();
                ProfileFragment.this.startActivity(new Intent(ProfileFragment.this.getActivity(), UpdateProfile.class).putExtra("type", 1).putExtra("isCompany",false));
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
                    if (!jSONObject.getBoolean("status") ) {
                        Global.Cappinglimt = jSONObject.getJSONArray("result").getJSONObject(0).getInt("capping");
                        popupMenu = new PopupMenu(ProfileFragment.this.getContext(), view, 17);
                        popupMenu.setOnDismissListener(new OnDismissListener());
                        popupMenu.setOnMenuItemClickListener(new OnMenuItemClickListener());
                        popupMenu.inflate(R.menu.profile_menu);
                        Menu str = popupMenu.getMenu();
                        if (Global.premium_status >= 1) {
                            str.findItem(R.id.upCApingLimit).setVisible(true);
                        }
                        popupMenu.show();
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


    private void getTimelinePost() {
        AppController.getInstance().addToRequestQueue(new StringRequest(1, APIs.timelineAPI, new C09332(), new C09343()) {
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
    class C09343 implements ErrorListener {
        C09343() {
        }

        public void onErrorResponse(VolleyError volleyError) {
            porogress.setVisibility(View.GONE);
        }
    }
    class C09332 implements Listener<String> {
        C09332() {
        }

        public void onResponse(String s) {
            porogress.setVisibility(View.GONE);
            try {
                JSONObject jSONObject = new JSONObject(s);
                if (jSONObject.getBoolean("status")) {
                    JSONArray str = jSONObject.getJSONArray("result");
                    for (int i = 0; i < str.length(); i++) {
                        JSONObject jSONObject2 = str.getJSONObject(i);
                        ArrayList arrayList = new ArrayList();
                        ArrayList arrayList2 = new ArrayList();
                        ArrayList arrayList3 = new ArrayList();
                        WallPostmodel wallPostmodel = new WallPostmodel();
                        wallPostmodel.logo = jSONObject2.getString("logo");
                        wallPostmodel.company_id = jSONObject2.getString("customer_id");
                        wallPostmodel.title = jSONObject2.getString("title");
                        wallPostmodel.description = jSONObject2.getString("description");
                        wallPostmodel.name = jSONObject2.getString("name");
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
                        postmodels.add(wallPostmodel);
                        wallPost.getAdapter().notifyItemRangeInserted(wallPost.getAdapter().getItemCount(), postmodels.size() - 1);
                    }
                }else {
                    count = 0 ;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

}
