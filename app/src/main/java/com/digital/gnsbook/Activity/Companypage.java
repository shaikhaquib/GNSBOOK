package com.digital.gnsbook.Activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.PopupMenu;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.digital.gnsbook.Config.APIs;
import com.digital.gnsbook.Config.AppController;
import com.digital.gnsbook.Fragement.Fragement_companypost;
import com.digital.gnsbook.Fragement.Fragment_Post;
import com.digital.gnsbook.Fragement.Product;
import com.digital.gnsbook.Fragment.ProfileFragment;
import com.digital.gnsbook.Fragment.ThreeFragment;
import com.digital.gnsbook.FragmentViewPagerAdapter;
import com.digital.gnsbook.Global;
import com.digital.gnsbook.Model.ComponyModel;
import com.httpgnsbook.gnsbook.R;
import com.digital.gnsbook.ViewDialog;
import com.google.gson.Gson;
import com.mikelau.croperino.Croperino;
import com.mikelau.croperino.CroperinoConfig;
import com.mikelau.croperino.CroperinoFileUtil;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Companypage extends AppCompatActivity {


    private List< Fragment > fragments = new ArrayList < > ();
    private List < String > titles = new ArrayList < > ();
    private ViewPager viewPager;
    private FragmentViewPagerAdapter adapter;
    private TabLayout tabLayout;
    ViewDialog dialog;
    TextView name,desc;
    ImageView banner , logo;
    CardView fabSubsCribe ,fabSetting;
    private PopupMenu popupMenu;
    Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_companypage);

        dialog = new ViewDialog(this);

        viewPager = (ViewPager) findViewById(R.id.htab_viewpager);
        tabLayout = findViewById(R.id.htab_tabs);
        name = findViewById(R.id.compony_name);
        desc = findViewById(R.id.compony_desc);
        banner = findViewById(R.id.company_banner);
        logo = findViewById(R.id.company_logo);
        fabSubsCribe = findViewById(R.id.fabSubsCribe);
        fabSetting = findViewById(R.id.fabAccountSetting);

        fabSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             //   startActivity(new Intent(getApplicationContext(),Subsription_plan.class));

                popupMenu = new PopupMenu(Companypage.this, v, 17);
                popupMenu.setOnDismissListener(new OnDismissListener());
                popupMenu.setOnMenuItemClickListener(new OnMenuItemClickListener());
                popupMenu.inflate(R.menu.profile_menu);
                popupMenu.show();
            }
        });
        fabSubsCribe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Subsription_plan.class));
            }
        });

        adapter = new FragmentViewPagerAdapter(getSupportFragmentManager(), fragments, titles);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);


        tabLayout.setOnTabSelectedListener(
                new TabLayout.ViewPagerOnTabSelectedListener(viewPager) {

                    @Override
                    public void onTabSelected(TabLayout.Tab tab) {
                        super.onTabSelected(tab);
                        int tabIconColor = ContextCompat.getColor(getApplicationContext(), R.color.white);
//                        tab.getIcon().setColorFilter(tabIconColor, PorterDuff.Mode.SRC_IN);
                    }

                    @Override
                    public void onTabUnselected(TabLayout.Tab tab) {
                        super.onTabUnselected(tab);
                        int tabIconColor = ContextCompat.getColor(getApplicationContext(), R.color.colorPrimaryDark  );
                        //       tab.getIcon().setColorFilter(tabIconColor, PorterDuff.Mode.SRC_IN);
                    }

                    @Override
                    public void onTabReselected(TabLayout.Tab tab) {
                        super.onTabReselected(tab);
                    }
                }
        );



        /* prepareDataResource();*/
        getComanydata();

    }

    private void getComanydata() {

        dialog.show();
        StringRequest request =new StringRequest(Request.Method.POST, APIs.company_data_by_id, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                dialog.dismiss();
                Gson gson = new Gson();
                ComponyModel dashResp = gson.fromJson(response,ComponyModel.class);

                name.setText(dashResp.getResult().get(0).getName());
                desc.setText(dashResp.getResult().get(0).getCompanyType());

                Picasso.get().load(APIs.Dp + dashResp.getResult().get(0).getLogo()).into(logo);
                Picasso.get().load(APIs.Banner + dashResp.getResult().get(0).getBanner()).into(banner);
                Global.Company_Id = dashResp.getResult().get(0).getCompanyId();
                Global.Company_Admin_Id = dashResp.getResult().get(0).getAdminId();
                Global.Company_Name = dashResp.getResult().get(0).getName();
                Global.Company_Logo = dashResp.getResult().get(0).getLogo();
                Global.Company_Type = dashResp.getResult().get(0).getCompanyType();
                Global.Company_Cate = dashResp.getResult().get(0).getCompanyCat();

                if (Global.Company_Admin_Id == Integer.parseInt(Global.customerid)){
                    fabSetting.setVisibility(View.VISIBLE);
                    fabSubsCribe.setVisibility(View.GONE);
                }else {
                    fabSetting.setVisibility(View.GONE);
                    fabSubsCribe.setVisibility(View.VISIBLE);
                }

                prepareDataResource();



            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                dialog.dismiss();
            }
        }){

            @Override
            public String getBodyContentType() {
                return "application/x-www-form-urlencoded; charset=UTF-8";
            }


            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<String, String>();
                params.put("data",getIntent().getStringExtra("id"));

                return params;
            }

        };
        AppController.getInstance().addToRequestQueue(request);
    }

    private void prepareDataResource() {

        fragments = new ArrayList< >();

        fragments.add(new Fragement_companypost());
        fragments.add(new Product());
        fragments.add(new Fragment_Post());





        titles.add("Timeline");
        titles.add("Product");
        titles.add("Post");



        FragmentViewPagerAdapter adapter = new FragmentViewPagerAdapter(getSupportFragmentManager(), fragments, titles);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        viewPager.setCurrentItem(1);
        viewPager.setCurrentItem(0);


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
                    startActivity(new Intent(Companypage.this, Subsription_plan.class));
                    break;
                case R.id.upBp:
                    CroperinoConfig cp = new CroperinoConfig("IMG_"+System.currentTimeMillis()+".jpg", "/gnsbook/Pictures", "/sdcard/gnsbook/Pictures");
                    CroperinoFileUtil.setupDirectory(Companypage.this);
                    if (CroperinoFileUtil.verifyStoragePermissions(Companypage.this) != null) {
                        Croperino.prepareGallery(Companypage.this);
                    }
                    return true;
                case R.id.upDp:
                    showDialoge();
                    return true;
                default:
                    break;
            }
            return true;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case CroperinoConfig.REQUEST_TAKE_PHOTO:
                if (resultCode == Activity.RESULT_OK) {
                    Croperino.runCropImage(CroperinoFileUtil.getTempFile(), Companypage.this, true, 1, 1, R.color.nav_textcolor, R.color.nav_textcolor);
                }
                break;
            case CroperinoConfig.REQUEST_PICK_FILE:
                if (resultCode == Activity.RESULT_OK) {
                    CroperinoFileUtil.newGalleryFile(data, Companypage.this);
                    Croperino.runCropImage(CroperinoFileUtil.getTempFile(), Companypage.this, true, 3, 1, R.color.nav_textcolor, R.color.nav_textcolor);
                }
                break;
            case CroperinoConfig.REQUEST_CROP_PHOTO:
                if (resultCode == Activity.RESULT_OK) {
                    Uri i = Uri.fromFile(CroperinoFileUtil.getTempFile());
                    bitmap = Global.uriToBitmap(i,Companypage.this);
                    Log.d("bit", String.valueOf(bitmap));
                    // ivMain.setImageBitmap(getRoundedCroppedBitmap(uriToBitmap(i)));
                    UploadBanner(Global.encodeTobase64(bitmap));
                }
                break;
            default:
                break;
        }
    }

    private void UploadBanner(final String Base64) {
        dialog.show();
        StringRequest stringRequest = new StringRequest(StringRequest.Method.POST, APIs.uploadbannercomponyPage, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                dialog.dismiss();

                try {
                    JSONObject object = new JSONObject(response);

                    if (object.getBoolean("status")){
                        Global.successDilogue(Companypage.this,"You have Successfully updated your profile");
                    }else {
                        Global.failedDilogue(Companypage.this,object.getString("result"));

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

                param.put("company_id",getIntent().getStringExtra("id"));
                param.put("image1","data:image/jpeg;base64,"+Base64);

                return param;
            }
        };

        AppController.getInstance().addToRequestQueue(stringRequest);
    }
    public void showDialoge() {
        View inflate = LayoutInflater.from(Companypage.this).inflate(R.layout.uploadui, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(Companypage.this);
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
                startActivity(new Intent(Companypage.this, UpdateProfile.class).putExtra("type", 0).putExtra("isCompany",true));
            }
        });
        linearLayout.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                create.dismiss();
                Companypage.this.startActivity(new Intent(Companypage.this, UpdateProfile.class).putExtra("type", 1));
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
                Croperino.prepareGallery(Companypage.this);
            }
        }
    }

}
