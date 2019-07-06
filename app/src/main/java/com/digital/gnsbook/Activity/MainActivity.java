package com.digital.gnsbook.Activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.NavigationView.OnNavigationItemSelectedListener;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.crashlytics.android.Crashlytics;
import com.digital.gnsbook.Adapter.Shop_adapter;
import com.digital.gnsbook.Config.APIs;
import com.digital.gnsbook.Config.AppController;
import com.digital.gnsbook.Config.SQLiteHandler;
import com.digital.gnsbook.Config.SessionManager;
import com.digital.gnsbook.Fragment.ChatFragment;
import com.digital.gnsbook.Fragment.FreindRequests;
import com.digital.gnsbook.Fragment.Frg_Home;
import com.digital.gnsbook.Fragment.Frg_UserProfile;
import com.digital.gnsbook.Fragment.Frg_componylist;
import com.digital.gnsbook.Fragment.ProfileFragment;
import com.digital.gnsbook.Fragment.FriendFragment;
import com.digital.gnsbook.Fragment.WallPostFragment;
import com.digital.gnsbook.Adapter.FragmentViewPagerAdapter;
import com.digital.gnsbook.Global;
import com.digital.gnsbook.GnsChat.AuthenticationRepository;
import com.digital.gnsbook.GnsChat.ChatRoomRepository;
import com.digital.gnsbook.Payment.Manual_Payment;
import com.digital.gnsbook.Payment_corpoarate.Corporate_Agent_Signup;
import com.digital.gnsbook.Payment_corpoarate.Corporate_BenificiaryList;
import com.digital.gnsbook.UserVerification;
import com.digital.gnsbook.ViewDialog;
import com.digital.gnsbook.sample.Main2Activity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.iid.FirebaseInstanceId;
import com.httpgnsbook.gnsbook.R;
import com.mikelau.croperino.Croperino;
import com.mikelau.croperino.CroperinoConfig;
import com.mikelau.croperino.CroperinoFileUtil;
import com.squareup.picasso.Picasso;
import com.vanniktech.emoji.EmojiManager;
import com.vanniktech.emoji.ios.IosEmojiProvider;

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
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements OnNavigationItemSelectedListener {
    private static final int REQUEST_WRITE_PERMISSION = 1155;
    private FragmentViewPagerAdapter adapter;
    Bitmap bitmap;
    SQLiteHandler db;
    ViewDialog dialog;
    private List<Fragment> fragments = new ArrayList();
    public ImageView profileImage;
    SessionManager session;
    private int[] tabIcons = new int[]{R.drawable.ic_newsfeed_icon ,
            R.drawable.ic_user_avatar,
            R.drawable.ic_happy_faces_icon
            ,R.drawable.ic_friends ,
            R.drawable.ic_co};
    private TabLayout tabLayout;
    private List<String> titles = new ArrayList();
    HashMap<String, String> user;
    private ViewPager viewPager;
    RelativeLayout rvSearch;
    String notiCount= null ;
    int tabIconColor ;
    AuthenticationRepository authentication;
    private static final String CURRENT_USER_KEY = "CURRENT_USER_KEY";
    ChatRoomRepository chatRoomRepository;
    private FirebaseAnalytics mFirebaseAnalytics;
    NavigationView navigationView;

    /* renamed from: com.digital.gnsbook.Activity.MainActivity$5 */
    class C04225 implements OnClickListener {
        C04225() {
        }

        public void onClick(View view) {
            if (Global.A_status.equals("0")) {
                MainActivity.this.startActivity(new Intent(MainActivity.this.getApplicationContext(), Corporate_Agent_Signup.class));
            } else if (Global.A_status.equals("1")) {
                MainActivity.this.startActivity(new Intent(MainActivity.this.getApplicationContext(), Corporate_BenificiaryList.class));
            } else if (Global.A_status.equals("-1")) {
                Global.diloge(MainActivity.this, "Pending", "Your GNSBOOK wallet is in under verification");
            }
        }
    }

    /* renamed from: com.digital.gnsbook.Activity.MainActivity$6 */
    class C04236 implements OnClickListener {
        C04236() {
        }

        public void onClick(View view) {
            if (!Global.verify_sms.equals("1")) {
                Global.diloge(MainActivity.this, "User not verified", "For Fund Transfer from GnsBook you have to verify your mobile no.");
            } else if (Global.A_status.equals("0") ) {
                MainActivity.this.startActivity(new Intent(MainActivity.this.getApplicationContext(), Corporate_Agent_Signup.class));
            } else if (Global.A_status.equals("1")) {
                MainActivity.this.startActivity(new Intent(MainActivity.this.getApplicationContext(), Manual_Payment.class));
            } else if (Global.A_status.equals("-1")) {
                Global.diloge(MainActivity.this, "Pending", "Your GNSBOOK wallet is in under verification");
            }
        }
    }

    /* renamed from: com.digital.gnsbook.Activity.MainActivity$7 */
    class C04247 implements DialogInterface.OnClickListener {
        C04247() {
        }

        public void onClick(DialogInterface dialogInterface, int i) {
            dialogInterface.dismiss();
        }
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
            dialog.show();

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


            dialog.dismiss();
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
                    Picasso.get().load(APIs.Dp + Global.DP).into(profileImage);

                    if (Global.premium_status == 0){
                        Menu nav_Menu = navigationView.getMenu();
                        nav_Menu.findItem(R.id.premium).setVisible(true);
                    }else {
                        Menu nav_Menu = navigationView.getMenu();
                        nav_Menu.findItem(R.id.premium).setVisible(false);
                    }



                }

            } catch (JSONException e) {
                e.printStackTrace();
            }


        }
        @Override
        protected void onCancelled() {

        }
    }

    /* renamed from: com.digital.gnsbook.Activity.MainActivity$2 */
    class C08902 implements Listener<String> {
        C08902() {
        }

        public void onResponse(String str) {
            try {
                JSONObject jSONObject = new JSONObject(str);
                if (jSONObject.getBoolean("status")) {
                    Global.A_status = jSONObject.getJSONArray("result").getJSONObject(0).getString("agent_status");
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    /* renamed from: com.digital.gnsbook.Activity.MainActivity$3 */
    class C08913 implements ErrorListener {
        public void onErrorResponse(VolleyError volleyError) {
        }

        C08913() {
        }
    }

    /* renamed from: com.digital.gnsbook.Activity.MainActivity$9 */
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_main);

        EmojiManager.install(new IosEmojiProvider());
        Toolbar toolbar = (Toolbar) findViewById(R.id.maintoolbar);
        setSupportActionBar(toolbar);
        this.dialog = new ViewDialog(this);

        FirebaseApp.initializeApp(this);
        authentication = new AuthenticationRepository(FirebaseFirestore.getInstance());
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);


        tabIconColor = ContextCompat.getColor(getApplicationContext(), R.color.tabindiactor);
        setTitle("");
        this.session = new SessionManager(this);
        this.db = new SQLiteHandler(this);
        this.user = this.db.getUserDetails();
        String str = (String) this.user.get("name");
        Global.Email = (String) this.user.get(NotificationCompat.CATEGORY_EMAIL);
        Global.mobile = (String) this.user.get("mobile");
        Global.customerid = (String) this.user.get("customer_id");
        Global.userid = (String) this.user.get("customer_id");
        Global.refferalid = (String) this.user.get("referral_id");
        Global.agentid = (String) this.user.get("aid");
        Global.name = str;

        DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        new UserDetailTask().execute(new String[]{Global.customerid});
        navigationView.setItemIconTintList(null);

        View headerView = navigationView.getHeaderView(0);
        TextView navUsername = headerView.findViewById(R.id.navUserName);
        navUsername.setText(Global.name);
        profileImage = headerView.findViewById(R.id.profileImage);
        rvSearch = findViewById(R.id.rvSearch);
        TextView NavEmail = headerView.findViewById(R.id.navEmail);
        NavEmail.setText(Global.Email);

        viewPager = (ViewPager) findViewById(R.id.container);
        viewPager.setOffscreenPageLimit(4);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        prepareDataResource();
        requestPermission();

        this.adapter = new FragmentViewPagerAdapter(getSupportFragmentManager(), this.fragments, this.titles);

        viewPager.setAdapter(this.adapter);
        tabLayout.setupWithViewPager(this.viewPager);
        tabLayout.setOnTabSelectedListener(
                new TabLayout.ViewPagerOnTabSelectedListener(viewPager) {

                    @Override
                    public void onTabSelected(TabLayout.Tab tab) {
                        super.onTabSelected(tab);

                        tab.getIcon().setColorFilter(tabIconColor, PorterDuff.Mode.SRC_IN);


                        if (notiCount!=null) {
                            View view = tabLayout.getTabAt(2).getCustomView();
                            ImageView img_title = (ImageView) view.findViewById(R.id.img_title);
                            if (tab.getPosition() == 2) {
                                img_title.setColorFilter(Color.parseColor("#f4511e"));
                            } else {
                                img_title.setColorFilter(ContextCompat.getColor(MainActivity.this, R.color.colorPrimaryDark), android.graphics.PorterDuff.Mode.MULTIPLY);
                            }
                        }

                    }

                    @Override
                    public void onTabUnselected(TabLayout.Tab tab) {
                        super.onTabUnselected(tab);
                        int tabIconColor = ContextCompat.getColor(getApplicationContext(), R.color.colorPrimaryDark  );
                        tab.getIcon().setColorFilter(tabIconColor, PorterDuff.Mode.SRC_IN);
                    }

                    @Override
                    public void onTabReselected(TabLayout.Tab tab) {
                        super.onTabReselected(tab);
                    }
                }



        );
        setTabIcons();
        new UserVerification(MainActivity.this);
        getAgentStatus();
        getFreindRequest();
        authenticate();
        UpdateDeviceToken();
    }

    private void getAgentStatus() {
        AppController.getInstance().addToRequestQueue(new StringRequest(1, APIs.AgenStatus, new C08902(), new C08913()) {
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> hashMap = new HashMap();
                hashMap.put("customer_id", Global.customerid);
                return hashMap;
            }
        });

        tabLayout.getTabAt(0).getIcon().setColorFilter(tabIconColor, PorterDuff.Mode.SRC_IN);
    }

    private void setTabIcons() {
        this.tabLayout.getTabAt(0).setIcon(this.tabIcons[0]);
        this.tabLayout.getTabAt(1).setIcon(this.tabIcons[1]);
        this.tabLayout.getTabAt(2).setIcon(this.tabIcons[2]);
        this.tabLayout.getTabAt(3).setIcon(this.tabIcons[3]);
        this.tabLayout.getTabAt(4).setIcon(this.tabIcons[4]);
    }

    private void requestPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[] {
                    android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_WRITE_PERMISSION);
        }
    }
    private void prepareDataResource() {
        fragments = new ArrayList();
        fragments.add(new Frg_Home());
        fragments.add(new Frg_UserProfile());
        fragments.add(new FreindRequests());
        fragments.add(new FriendFragment());
        fragments.add(new Frg_componylist());
        titles.add("");
        titles.add("");
        titles.add("");
        titles.add("");
        titles.add("");

    }

    public void onBackPressed() {
        DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawerLayout.isDrawerOpen((int) GravityCompat.START)) {
            drawerLayout.closeDrawer((int) GravityCompat.START);
        } else {
            finish();
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);

        return true;
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == R.id.action_search) {
            startActivity(new Intent(getApplicationContext(),SearchActivity.class));
            return true;
      }else  if (menuItem.getItemId() == R.id.cart) {
            startActivity(new Intent(getApplicationContext(), Cart.class));
            return true;
        } if (menuItem.getItemId() == R.id.action_chat) {
            startActivity(new Intent(getApplicationContext(), ChatAcivity.class));
            return true;
        }
        return true;
    }

    public boolean onNavigationItemSelected(MenuItem Item) {
        int menuItem = Item.getItemId();
        if (menuItem == R.id.nav_dash) {
            startActivity(new Intent(getApplicationContext(), Account.class));
        } else if (menuItem == R.id.nav_chat) {
            startActivity(new Intent(getApplicationContext(), ChatAcivity.class));
        } else if (menuItem == R.id.nav_Componylist) {
            startActivity(new Intent(getApplicationContext(), Compony_list.class));
        } else if (menuItem == R.id.nav_topperform) {
            startActivity(new Intent(getApplicationContext(), ProfilePage.class).putExtra("type",1));
        } else if (menuItem == R.id.nav_rewards) {
            startActivity(new Intent(getApplicationContext(), Pool_Rewards.class));
        } else if (menuItem == R.id.support) {
            callPhoneNumber();
        } else if (menuItem == R.id.history) {
            startActivity(new Intent(getApplicationContext(), Order_history.class));
        }else if (menuItem == R.id.premium) {
            startActivity(new Intent(getApplicationContext(),Become_Premium.class));
        } else if (menuItem == R.id.Gstore) {
            startActivity(new Intent(getApplicationContext(),G_Store.class));
        }else if (menuItem == R.id.mycompany) {
            startActivity(new Intent(getApplicationContext(), MyCompany.class));
        } else if (menuItem == R.id.nav_wallet) {
            if (!Global.verify_sms.equals("1")) {
                Global.diloge(MainActivity.this, "User not verified", "For Fund Transfer from GnsBook you have to verify your mobile no.");
            }else {
            fundTransfer();}
        } else if (menuItem == R.id.nav_logout) {
            Logout();
        }
        ((DrawerLayout) findViewById(R.id.drawer_layout)).closeDrawer((int) GravityCompat.START);
        return true;
    }

    private void fundTransfer() {
        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(this);
        View inflate = getLayoutInflater().inflate(R.layout.transactionmode, null);
        builder.setView(inflate);
        TextView textView = (TextView) inflate.findViewById(R.id.new_FundTrans);
        TextView textView2 = (TextView) inflate.findViewById(R.id.old_FundTrans);
        builder.create().show();
        textView.setOnClickListener(new C04225());
        textView2.setOnClickListener(new C04236());
    }

    private void Logout() {
        this.session.setLogin(false);
        this.db.deleteUsers();
        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
    }

    public void onRequestPermissionsResult(int i, String[] strArr, int[] iArr) {
        if (i == 101 && iArr[0] == 0) {
            callPhoneNumber();
        }
    }

    public void callPhoneNumber() {
        try {
            if (VERSION.SDK_INT <= 22) {
                startActivity(new Intent("android.intent.action.CALL", Uri.parse("tel:9004901264")));
            } else if (ContextCompat.checkSelfPermission(this, "android.permission.CALL_PHONE") != 0) {
                ActivityCompat.requestPermissions(this, new String[]{"android.permission.CALL_PHONE"}, 101);
            } else {
                startActivity(new Intent("android.intent.action.CALL", Uri.parse("tel:9004901264")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case CroperinoConfig.REQUEST_TAKE_PHOTO:
                if (resultCode == Activity.RESULT_OK) {
                    Croperino.runCropImage(CroperinoFileUtil.getTempFile(), MainActivity.this, true, 1, 1, R.color.nav_textcolor, R.color.nav_textcolor);
                }
                break;
            case CroperinoConfig.REQUEST_PICK_FILE:
                if (resultCode == Activity.RESULT_OK) {
                    CroperinoFileUtil.newGalleryFile(data, MainActivity.this);
                    Croperino.runCropImage(CroperinoFileUtil.getTempFile(), MainActivity.this, true, 3, 1, R.color.nav_textcolor, R.color.nav_textcolor);
                }
                break;
            case CroperinoConfig.REQUEST_CROP_PHOTO:
                if (resultCode == Activity.RESULT_OK) {
                    Uri i = Uri.fromFile(CroperinoFileUtil.getTempFile());
                    bitmap = Global.uriToBitmap(i,MainActivity.this);
                    Log.d("bit", String.valueOf(bitmap));
                    // ivMain.setImageBitmap(getRoundedCroppedBitmap(uriToBitmap(i)));
                    UploadProfile(Global.encodeTobase64(bitmap));
                }
                break;
            default:
                break;
        }
    }

    private void UploadProfile(final String Base64) {
        dialog.show();
        StringRequest stringRequest = new StringRequest(StringRequest.Method.POST, APIs.uploadbanner, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                dialog.dismiss();

                try {
                    JSONObject object = new JSONObject(response);

                    if (object.getBoolean("status")){
                        Global.successDilogue(MainActivity.this,"You have Successfully updated your profile");
                    }else {
                        Global.failedDilogue(MainActivity.this,object.getString("result"));

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
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> param = new HashMap<>();

                param.put("customer_id",Global.customerid);
                param.put("string",Base64);

                return param;
            }
        };

        AppController.getInstance().addToRequestQueue(stringRequest);
    }

    public View getTabView(int position, String request_count) {
        View view = LayoutInflater.from(this).inflate(R.layout.customeview, null);
        TextView txt_title = (TextView) view.findViewById(R.id.txt_title);
        txt_title.setText(request_count);
        ImageView img_title = (ImageView) view.findViewById(R.id.img_title);
        img_title.setImageResource(tabIcons[position]);

        return view;
    }

    private void getFreindRequest() {
      //  dialog.show();
        AppController.getInstance().addToRequestQueue(new StringRequest(1, APIs.RequestList, new Response.Listener<String>() {
            public void onResponse(String str) {
       //         dialog.dismiss();

                try {
                    JSONObject jsonObject = new JSONObject(str);

                    if (jsonObject.getBoolean("status")){

                        notiCount =jsonObject.getString("request_count");

                        tabLayout.getTabAt(2).setCustomView(getTabView(2,notiCount));



                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            public void onErrorResponse(VolleyError volleyError) {
          //      dialog.dismiss();
            }
        }) {
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> hashMap = new HashMap();
                hashMap.put("customerid_to", Global.customerid);
                return hashMap;
            }
        });

    }

    private void authenticate() {
        String currentUserKey = getCurrentUserKey();
        if (currentUserKey.isEmpty()) {
            authentication.createNewUser(
                    new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            saveCurrentUserKey(documentReference.getId());
                        //    Toast.makeText(MainActivity.this, "New user created", Toast.LENGTH_SHORT).show();
                        }
                    },
                    new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(
                                    MainActivity.this,
                                    "Error creating user. Check your internet connection",
                                    Toast.LENGTH_SHORT)
                                    .show();
                        }
                    }
            );
        } else {
            authentication.login(
                    currentUserKey,
                    new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                   //         Toast.makeText(MainActivity.this, "Logged in", Toast.LENGTH_SHORT).show();
                            getCurrentUserKey();
                          //  getChatRooms();
                        }
                    },
                    new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(
                                    MainActivity.this,
                                    "Error signing in. Check your internet connection",
                                    Toast.LENGTH_SHORT)
                                    .show();
                        }
                    }
            );
        }
    }

    private String getCurrentUserKey() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        return preferences.getString(CURRENT_USER_KEY, "");
    }

    private void saveCurrentUserKey(String key) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(CURRENT_USER_KEY, key);
        editor.apply();
     //   getChatRooms();

    }

    private void UpdateDeviceToken() {
        StringRequest request =new StringRequest(StringRequest.Method.POST, APIs.devicetoken, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject =new JSONObject(response);
                    System.out.println(response);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {}
        }){

            @Override
            public String getBodyContentType() {
                return "application/x-www-form-urlencoded; charset=UTF-8";
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String>  params = new HashMap<String, String>();

                params.put("customer_id", Global.customerid);
                params.put("token",FirebaseInstanceId.getInstance().getToken());

                return params;
            }

        };
        AppController.getInstance().addToRequestQueue(request);
    }


}
