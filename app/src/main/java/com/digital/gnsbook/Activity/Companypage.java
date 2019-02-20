package com.digital.gnsbook.Activity;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.digital.gnsbook.Config.APIs;
import com.digital.gnsbook.Config.AppController;
import com.digital.gnsbook.Fragement.Fragement_companypost;
import com.digital.gnsbook.Fragment.ProfileFragment;
import com.digital.gnsbook.Fragment.ThreeFragment;
import com.digital.gnsbook.FragmentViewPagerAdapter;
import com.digital.gnsbook.Global;
import com.digital.gnsbook.Model.ComponyModel;
import com.httpgnsbook.gnsbook.R;
import com.digital.gnsbook.ViewDialog;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

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
       /* fragments.add(new ProfileFragment());
        fragments.add(new ThreeFragment());*/





        titles.add("Timeline");
      /*  titles.add("About");
        titles.add("Photos");*/



        FragmentViewPagerAdapter adapter = new FragmentViewPagerAdapter(getSupportFragmentManager(), fragments, titles);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        viewPager.setCurrentItem(1);
        viewPager.setCurrentItem(0);


    }

}
