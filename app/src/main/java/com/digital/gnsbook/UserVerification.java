package com.digital.gnsbook;

import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.AuthFailureError;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.digital.gnsbook.Activity.OTPview;
import com.digital.gnsbook.Activity.ProfilePage;
import com.digital.gnsbook.Config.APIs;
import com.digital.gnsbook.Config.AppController;
import com.httpgnsbook.gnsbook.R;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class UserVerification {
    final Context context;
    final ViewDialog dialog;

    /* renamed from: com.digital.gnsbook.UserVerification$7 */
    class C05677 implements OnClickListener {

        /* renamed from: com.digital.gnsbook.UserVerification$7$1 */
        class C05651 implements DialogInterface.OnClickListener {
            C05651() {
            }

            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                UserVerification.this.VerifyMobile();
            }
        }

        /* renamed from: com.digital.gnsbook.UserVerification$7$2 */
        class C05662 implements DialogInterface.OnClickListener {
            C05662() {
            }

            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();

                context.startActivity(new Intent(UserVerification.this.context, ProfilePage.class).putExtra("type",3).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
            }
        }

        C05677() {
        }

        public void onClick(View view) {
            AlertDialog.Builder dialog = new AlertDialog.Builder(context);
            dialog.setTitle("Phone Number Verification");
            String message = "We weill send OTP on ";
            dialog.setMessage(message +Global.mobile);

            dialog.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    VerifyMobile();
                }
            });
            dialog.setNegativeButton("Change Number", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();

                    context.startActivity(new Intent(context,ProfilePage.class).putExtra("type",3).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                }
            });



            dialog.setCancelable(false);
            dialog.show();

        }
    }

    /* renamed from: com.digital.gnsbook.UserVerification$1 */
    class C09971 implements Listener<String> {
        C09971() {
        }

        public void onResponse(String responce) {
            UserVerification.this.dialog.dismiss();
            try {
                JSONObject jSONObject = new JSONObject(responce);
                if (jSONObject.getBoolean("status")) {
                    JSONObject jsonArray = jSONObject.getJSONArray("result").getJSONObject(0);
                    Global.verify_email = jsonArray.getString("verify_email");
                    Global.verify_sms = jsonArray.getString("verify_sms");
                    Global.verify_sms = jsonArray.getString("verify_sms");
                    if (Global.verify_email.equals("0")  || Global.verify_sms.equals("0") ) {
                        showDialoge();
                    }
                } else if (jSONObject.getString("result").equals("No Data Available")) {
                    showDialoge();
                }
            }  catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    /* renamed from: com.digital.gnsbook.UserVerification$2 */
    class C09982 implements ErrorListener {
        C09982() {
        }

        public void onErrorResponse(VolleyError volleyError) {
            UserVerification.this.dialog.dismiss();
        }
    }

    /* renamed from: com.digital.gnsbook.UserVerification$4 */
    class C09994 implements Listener<String> {
        C09994() {
        }

        public void onResponse(String responnce) {
            UserVerification.this.dialog.dismiss();
            try {
                JSONObject jSONObject = new JSONObject(responnce);
                if (jSONObject.getBoolean("status")) {
                    UserVerification.this.context.startActivity(new Intent(UserVerification.this.context, OTPview.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                    return;
                }
                Global.failedDilogue(UserVerification.this.context, jSONObject.getString("result"));
            }  catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    /* renamed from: com.digital.gnsbook.UserVerification$5 */
    class C10005 implements ErrorListener {
        C10005() {
        }

        public void onErrorResponse(VolleyError volleyError) {
            UserVerification.this.dialog.dismiss();
        }
    }

    public UserVerification(Context context) {
        this.context = context;
        this.dialog = new ViewDialog(context);
        UserVerification();
    }

    public void UserVerification() {
        this.dialog.show();
        AppController.getInstance().addToRequestQueue(new StringRequest(1, APIs.Verificationstatus, new C09971(), new C09982()) {
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> hashMap = new HashMap();
                hashMap.put("customer_id", Global.customerid);
                return hashMap;
            }
        });
    }

    public void VerifyMobile() {
        this.dialog.show();
        AppController.getInstance().addToRequestQueue(new StringRequest(1, APIs.PhoneVerification, new C09994(), new C10005()) {
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> hashMap = new HashMap();
                hashMap.put("customer_id", Global.customerid);
                return hashMap;
            }
        });
    }

    public void showDialoge() {
        View inflate = LayoutInflater.from(this.context).inflate(R.layout.vrificationdialoge, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(this.context);
        builder.setCancelable(false);
        Button button = (Button) inflate.findViewById(R.id.vrMobile);
        Button button2 = (Button) inflate.findViewById(R.id.vrEmail);
        RelativeLayout relativeLayout = (RelativeLayout) inflate.findViewById(R.id.relMobile);
        RelativeLayout relativeLayout2 = (RelativeLayout) inflate.findViewById(R.id.relEmail);
        TextView textView = (TextView) inflate.findViewById(R.id.rmdlater);
        if (Global.verify_sms.equals("1")) {
            relativeLayout.setVisibility(View.GONE);
        }
        if (Global.verify_email.equals("1")) {
            relativeLayout2.setVisibility(View.GONE);
        }
        button.setOnClickListener(new C05677());
        builder.setView(inflate);
        final AlertDialog create = builder.create();
        create.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        create.show();
        textView.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                create.dismiss();
            }
        });
        button2.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                create.dismiss();
                UserVerification.this.emailverification();
            }
        });
    }

    private void emailverification() {
        this.dialog.show();
        AppController.getInstance().addToRequestQueue(new StringRequest(1, APIs.EmailVerification, new Listener<String>() {
            public void onResponse(String str) {
                UserVerification.this.dialog.dismiss();
                try {
                    JSONObject jSONObject = new JSONObject(str);
                    if (jSONObject.getBoolean("status") ) {
                        Global.successDilogue(UserVerification.this.context, "We have send verification link on your email id .");
                    } else {
                        Global.failedDilogue(UserVerification.this.context, jSONObject.getString("result"));
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new ErrorListener() {
            public void onErrorResponse(VolleyError volleyError) {
                UserVerification.this.dialog.dismiss();
            }
        }) {
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> hashMap = new HashMap();
                hashMap.put("customer_id", Global.customerid);
                return hashMap;
            }
        });
    }
}
