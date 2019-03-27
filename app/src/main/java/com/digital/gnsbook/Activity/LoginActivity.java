package com.digital.gnsbook.Activity;

import android.content.Intent;
import android.app.Activity;
import instamojo.library.InstapayListener;
import instamojo.library.InstamojoPay;
import instamojo.library.Config;
import org.json.JSONObject;
import org.json.JSONException;
import android.content.IntentFilter;
import android.widget.Toast;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentManager.OnBackStackChangedListener;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import com.digital.gnsbook.Config.SessionManager;
import com.digital.gnsbook.Fragement.FragementLogin;
import com.digital.gnsbook.Fragement.FragmentSignUp;
import com.httpgnsbook.gnsbook.R;

public class LoginActivity extends AppCompatActivity {
    private static final String ACTIVITY_NAME = MainActivity.class.getSimpleName();
    private static final String COMMON_TAG = "CombinedLifeCycle";
    private static final String TAG = ACTIVITY_NAME;
    private TextView btnLogin;
    private TextView btnSignup;
    private Button buttonAddFragment;
    Fragment fragment;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    SessionManager session;
    private TextView textViewFragmentCount;

    /* renamed from: com.digital.gnsbook.Activity.LoginActivity$1 */
    class C04201 implements OnClickListener {
        C04201() {
        }

        public void onClick(View view) {
            LoginActivity.this.btnSignup.setTextColor(ContextCompat.getColor(LoginActivity.this, R.color.disable));
            LoginActivity.this.btnLogin.setTextColor(ContextCompat.getColor(LoginActivity.this, R.color.colorPrimary));
            LoginActivity.this.btnSignup.setCompoundDrawablesWithIntrinsicBounds(null, LoginActivity.this.getResources().getDrawable(R.drawable.ic_rdisable), null, null);
            LoginActivity.this.btnLogin.setCompoundDrawablesWithIntrinsicBounds(null, LoginActivity.this.getResources().getDrawable(R.drawable.ic_enabel), null, null);
            LoginActivity.this.fragment = LoginActivity.this.fragmentManager.findFragmentById(R.id.fragmentContainer);
            LoginActivity.this.fragment = new FragementLogin();
            LoginActivity.this.fragmentTransaction = LoginActivity.this.fragmentManager.beginTransaction();
            LoginActivity.this.fragmentTransaction.replace(R.id.fragmentContainer, LoginActivity.this.fragment, "demofragment");
            LoginActivity.this.fragmentTransaction.addToBackStack(null);
            LoginActivity.this.fragmentTransaction.commit();
        }
    }

    /* renamed from: com.digital.gnsbook.Activity.LoginActivity$2 */
    class C04212 implements OnClickListener {
        C04212() {
        }

        public void onClick(View view) {
            LoginActivity.this.btnLogin.setTextColor(ContextCompat.getColor(LoginActivity.this, R.color.disable));
            LoginActivity.this.btnSignup.setTextColor(ContextCompat.getColor(LoginActivity.this, R.color.colorPrimary));
            LoginActivity.this.btnLogin.setCompoundDrawablesWithIntrinsicBounds(null, LoginActivity.this.getResources().getDrawable(R.drawable.ic_disable), null, null);
            LoginActivity.this.btnSignup.setCompoundDrawablesWithIntrinsicBounds(null, LoginActivity.this.getResources().getDrawable(R.drawable.ic_renable), null, null);
            LoginActivity.this.fragment = LoginActivity.this.fragmentManager.findFragmentById(R.id.fragmentContainer);
            LoginActivity.this.fragment = new FragmentSignUp();
            LoginActivity.this.fragmentTransaction = LoginActivity.this.fragmentManager.beginTransaction();
            LoginActivity.this.fragmentTransaction.replace(R.id.fragmentContainer, LoginActivity.this.fragment, "demofragment");
            LoginActivity.this.fragmentTransaction.addToBackStack(null);
            LoginActivity.this.fragmentTransaction.commit();
        }
    }

    /* renamed from: com.digital.gnsbook.Activity.LoginActivity$3 */
    class C08893 implements OnBackStackChangedListener {
        public void onBackStackChanged() {
        }

        C08893() {
        }
    }

    public void onBackPressed() {
    }

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_login);
        // Call the function callInstamojo to start payment here
        this.session = new SessionManager(this);
        if (this.session.isLoggedIn()) {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }
        getSupportActionBar().hide();
        this.btnLogin = (TextView) findViewById(R.id.btnLogin);
        this.btnSignup = (TextView) findViewById(R.id.btnSignup);
        this.btnLogin.setOnClickListener(new C04201());
        this.btnSignup.setOnClickListener(new C04212());
        this.fragmentManager = getSupportFragmentManager();
        this.fragmentManager.addOnBackStackChangedListener(new C08893());
        addFragment();
    }

    private void addFragment() {
        switch (this.fragmentManager.getBackStackEntryCount()) {
            case 0:
                this.fragment = new FragementLogin();
                break;
            case 1:
                this.fragment = new FragmentSignUp();
                break;
            default:
                this.fragment = new FragementLogin();
                break;
        }
        this.fragment = this.fragmentManager.findFragmentById(R.id.fragmentContainer);
        if (this.fragment instanceof FragementLogin) {
            this.fragment = new FragmentSignUp();
        } else if (this.fragment instanceof FragmentSignUp) {
            this.fragment = new FragementLogin();
        } else {
            this.fragment = new FragementLogin();
        }
        this.fragmentTransaction = this.fragmentManager.beginTransaction();
        this.fragmentTransaction.replace(R.id.fragmentContainer, this.fragment, "demofragment");
        this.fragmentTransaction.addToBackStack(null);
        this.fragmentTransaction.commit();
    }
}
