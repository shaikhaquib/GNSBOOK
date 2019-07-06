package com.digital.gnsbook.Activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.digital.gnsbook.Config.APIs;
import com.digital.gnsbook.Fragement.Fragement_ChangePass;
import com.digital.gnsbook.Fragement.Fragement_Createpage;
import com.digital.gnsbook.Fragement.Fragement_Setting;
import com.digital.gnsbook.Global;
import com.httpgnsbook.gnsbook.R;
import com.squareup.picasso.Picasso;

public class ProfilePage extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    Fragment fragment;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    ImageView profile , banner;
    DrawerLayout mDrawerLayout;
    CardView openDrawer,closeDrawer;
    TextView Email ,Name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profilesetting);

        getSupportActionBar().hide();

        openDrawer = findViewById(R.id.dropen);
        closeDrawer = findViewById(R.id.drclose);
        profile = findViewById(R.id.prpick);
        banner = findViewById(R.id.prBnr);
        Email = findViewById(R.id.premail);
        Name = findViewById(R.id.prUName);
        mDrawerLayout = findViewById(R.id.pdrawer);

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        mDrawerLayout.addDrawerListener(
                new DrawerLayout.DrawerListener() {
                    @Override
                    public void onDrawerSlide(View drawerView, float slideOffset) {
                        // Respond when the drawer's position changes
                    }

                    @Override
                    public void onDrawerOpened(View drawerView) {
                        // Respond when the drawer is opened
                    }

                    @Override
                    public void onDrawerClosed(View drawerView) {
                        // Respond when the drawer is closed
                    }

                    @Override
                    public void onDrawerStateChanged(int newState) {
                        // Respond when the drawer motion state changes
                    }
                }
        );

        openDrawer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrawerLayout.openDrawer(GravityCompat.START);
            }
        }); closeDrawer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrawerLayout.closeDrawer(GravityCompat.START);
            }
        });

        fragmentManager=getSupportFragmentManager();



        fragmentManager.addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {
            }
        });

        fragment = fragmentManager.findFragmentById(R.id.pfragmentContainer);


        if (getIntent().getIntExtra("type",0)==3) {
            fragment = new Fragement_Createpage();
        }else {
            fragment = new Fragement_Setting();
        }
        fragmentTransaction=fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.pfragmentContainer,fragment,"demofragment");
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

        Picasso.get().load(APIs.Dp + Global.DP).error(R.drawable.ic_man).into(profile);
        Picasso.get().load(APIs.Banner + Global.Banner).error(R.drawable.landing_bg).into(banner);
        Name.setText (Global.name);
        Email.setText(Global.City);

        View headerView = navigationView.getHeaderView(0);
        TextView navUsername = headerView.findViewById(R.id.navUserName1);
        navUsername.setText(Global.name);
        ImageView profileImage = headerView.findViewById(R.id.profileImage1);
        TextView NavEmail = headerView.findViewById(R.id.navEmail1);
        Picasso.get().load(APIs.Dp + Global.DP).error(R.drawable.ic_man).into(profileImage);
        NavEmail.setText(Global.Email);
    }


    @Override
    public void onBackPressed() {
        finish();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.


        return super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        // Handle navigation view item clicks here.
        int id = menuItem.getItemId();

        if (id == R.id.se_personalsetting) {

            fragment = fragmentManager.findFragmentById(R.id.pfragmentContainer);
            fragment = new Fragement_Setting();
            fragmentTransaction=fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.pfragmentContainer,fragment,"Settingfragment");
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();

        } else if (id == R.id.se_changepassword){

            fragment = fragmentManager.findFragmentById(R.id.pfragmentContainer);
            fragment = new Fragement_ChangePass();
            fragmentTransaction=fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.pfragmentContainer,fragment,"Settingfragment");
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();

        } else if (id == R.id.se_createcomponypage){

            fragment = fragmentManager.findFragmentById(R.id.pfragmentContainer);
            fragment = new Fragement_Createpage();
            fragmentTransaction=fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.pfragmentContainer,fragment,"Settingfragment");
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.pdrawer);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}