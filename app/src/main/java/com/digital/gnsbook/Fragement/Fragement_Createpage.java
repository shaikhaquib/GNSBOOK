package com.digital.gnsbook.Fragement;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.digital.gnsbook.Config.APIs;
import com.digital.gnsbook.Config.AppController;
import com.digital.gnsbook.Global;
import com.digital.gnsbook.ViewDialog;
import com.httpgnsbook.gnsbook.R;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.angmarch.views.NiceSpinner;
import org.json.JSONException;
import org.json.JSONObject;

public class Fragement_Createpage extends Fragment {
    EditText address;
    Button btnCreate;
    EditText city;
    EditText company_type;
    EditText description;
    ViewDialog dialog;
    EditText email;
    EditText introduction;
    TextInputLayout laddress;
    TextInputLayout lcity;
    TextInputLayout lcompany_type;
    TextInputLayout ldescription;
    TextInputLayout lemail;
    TextInputLayout lintroduction;
    TextInputLayout lmobile;
    TextInputLayout lname;
    TextInputLayout lstate;
    TextInputLayout lweb;
    TextInputLayout lworking_hours;
    NiceSpinner typeSpinner;
    EditText mobile;
    EditText name;
    String saddress;
    String scity;
    String scompany_type;
    String sdescription;
    String semail;
    String sintroduction;
    String smobile;
    String sname;
    String sstate;
    EditText state;
    String sweb,type;
    String sworking_hours;
    EditText web;
    EditText working_hours;
    List<String> dataset = new LinkedList<>(Arrays.asList("other", "ecommerce"));

    /* renamed from: com.digital.gnsbook.Fragement.Fragement_Createpage$1 */
    class C04401 implements OnClickListener {
        C04401() {
        }

        public void onClick(View view) {
            Fragement_Createpage.this.Authenticat();
        }
    }

    /* renamed from: com.digital.gnsbook.Fragement.Fragement_Createpage$2 */
    class C09162 implements Listener<String> {
        C09162() {
        }

        public void onResponse(String str) {
            Fragement_Createpage.this.dialog.dismiss();
            try {
                JSONObject jSONObject = new JSONObject(str);
                if (jSONObject.getBoolean("status")) {
                    Global.successDilogue(Fragement_Createpage.this.getActivity(), "You have successfully created page for your company");
                } else {
                    Global.failedDilogue(Fragement_Createpage.this.getActivity(), jSONObject.getString("result"));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    /* renamed from: com.digital.gnsbook.Fragement.Fragement_Createpage$3 */
    class C09173 implements ErrorListener {
        C09173() {
        }

        public void onErrorResponse(VolleyError volleyError) {
            Fragement_Createpage.this.dialog.dismiss();
        }
    }

    public View onCreateView(LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, Bundle bundle) {
            View view = layoutInflater.inflate(R.layout.fragement_createpage, viewGroup, false);

            dialog = new ViewDialog(getActivity());
            initilize_element(view);

            btnCreate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Authenticat();
                }
            });

            return view;

    }

    private void Authenticat() {
        View view;
        this.sname = this.name.getText().toString();
        this.scompany_type = this.company_type.getText().toString();
        this.sdescription = this.description.getText().toString();
        this.sintroduction = this.introduction.getText().toString();
        this.saddress = this.address.getText().toString();
        this.sstate = this.state.getText().toString();
        this.scity = this.city.getText().toString();
        this.smobile = this.mobile.getText().toString();
        this.semail = this.email.getText().toString();
        this.sweb = this.web.getText().toString();
        this.sworking_hours = this.working_hours.getText().toString();
        type = dataset.get(typeSpinner.getSelectedIndex());




        Matcher matcher = Pattern.compile("\\w+([-+.']\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*").matcher(this.semail);
        Object obj = 1;
        if (TextUtils.isEmpty(this.sname)) {
            this.lname.setError(getString(R.string.error_field_required));
            view = this.name;
        } else if (TextUtils.isEmpty(this.sintroduction)) {
            this.lintroduction.setError(getString(R.string.error_field_required));
            view = this.introduction;
        } else if (TextUtils.isEmpty(this.sdescription)) {
            this.ldescription.setError(getString(R.string.error_field_required));
            view = this.description;
        } else if (TextUtils.isEmpty(this.scompany_type)) {
            this.lcompany_type.setError(getString(R.string.error_field_required));
            view = this.company_type;
        } else if (TextUtils.isEmpty(this.sweb)) {
            this.lweb.setError(getString(R.string.error_field_required));
            view = this.web;
        } else if (TextUtils.isEmpty(this.semail)) {
            this.lemail.setError(getString(R.string.error_field_required));
            view = this.email;
        } else if (!matcher.matches() && !this.semail.isEmpty()) {
            this.lemail.setError("Enter Valid Email");
            view = this.email;
        } else if (TextUtils.isEmpty(this.smobile)) {
            this.lmobile.setError(getString(R.string.error_field_required));
            view = this.mobile;
        } else if (this.smobile.length() < 10) {
            this.lmobile.setError("Enter valid mobile no");
            view = this.mobile;
        } else if (TextUtils.isEmpty(this.scity)) {
            this.lcity.setError(getString(R.string.error_field_required));
            view = this.city;
        } else if (TextUtils.isEmpty(this.sstate)) {
            this.lstate.setError(getString(R.string.error_field_required));
            view = this.state;
        } else if (TextUtils.isEmpty(this.sworking_hours)) {
            this.lworking_hours.setError(getString(R.string.error_field_required));
            view = this.working_hours;
        } else if (TextUtils.isEmpty(this.saddress)) {
            this.laddress.setError(getString(R.string.error_field_required));
            view = this.address;
        } else {
            obj = null;
            view = null;
        }
        if (obj != null) {
            view.requestFocus();
        } else {
            CreatePage();
        }
    }

    private void CreatePage() {
        this.dialog.show();
        AppController.getInstance().addToRequestQueue(new StringRequest(1, APIs.createpage, new C09162(), new C09173()) {
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> hashMap = new HashMap();
                hashMap.put("name", Fragement_Createpage.this.sname);
                hashMap.put("company_type", Fragement_Createpage.this.scompany_type);
                hashMap.put("description", Fragement_Createpage.this.sdescription);
                hashMap.put("introduction", Fragement_Createpage.this.sintroduction);
                hashMap.put("address", Fragement_Createpage.this.saddress);
                hashMap.put("state", Fragement_Createpage.this.sstate);
                hashMap.put("city", Fragement_Createpage.this.scity);
                hashMap.put("mobile", Fragement_Createpage.this.smobile);
                hashMap.put("email", Fragement_Createpage.this.semail);
                hashMap.put("web", Fragement_Createpage.this.sweb);
                hashMap.put("working_hours", Fragement_Createpage.this.sworking_hours);
                hashMap.put("admin_id", Global.customerid);
                hashMap.put("company_cat",type);
                return hashMap;
            }
        });
    }

    private void initilize_element(View view) {
        this.btnCreate = (Button) view.findViewById(R.id.ccreatePage);
        this.name = (EditText) view.findViewById(R.id.cname);
        this.company_type = (EditText) view.findViewById(R.id.ctype);
        this.description = (EditText) view.findViewById(R.id.cabt);
        this.introduction = (EditText) view.findViewById(R.id.cintro);
        this.address = (EditText) view.findViewById(R.id.caddress);
        this.state = (EditText) view.findViewById(R.id.cstate);
        this.city = (EditText) view.findViewById(R.id.ccity);
        this.mobile = (EditText) view.findViewById(R.id.ccontact);
        this.email = (EditText) view.findViewById(R.id.cemail);
        this.web = (EditText) view.findViewById(R.id.cwebsite);
        this.working_hours = (EditText) view.findViewById(R.id.cworkinghr);
        this.lname = (TextInputLayout) view.findViewById(R.id.lcname);
        this.lcompany_type = (TextInputLayout) view.findViewById(R.id.lctype);
        this.ldescription = (TextInputLayout) view.findViewById(R.id.lcabt);
        this.lintroduction = (TextInputLayout) view.findViewById(R.id.lcintro);
        this.laddress = (TextInputLayout) view.findViewById(R.id.lcaddress);
        this.lstate = (TextInputLayout) view.findViewById(R.id.lcstate);
        this.lcity = (TextInputLayout) view.findViewById(R.id.lccity);
        this.lmobile = (TextInputLayout) view.findViewById(R.id.lccontact);
        this.lemail = (TextInputLayout) view.findViewById(R.id.lcemail);
        this.lweb = (TextInputLayout) view.findViewById(R.id.lcwebsite);
        this.lworking_hours = (TextInputLayout) view.findViewById(R.id.lcworkinghr);

        typeSpinner =view.findViewById(R.id.ctype_spinner);
        typeSpinner.attachDataSource(dataset);

        typeSpinner.addOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getActivity(), dataset.get(position), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
