package com.digital.gnsbook.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.android.volley.AuthFailureError;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.digital.gnsbook.Config.APIs;
import com.digital.gnsbook.Config.AppController;
import com.digital.gnsbook.Config.DbHelper;
import com.digital.gnsbook.Model.Compony_data;
import com.digital.gnsbook.RecyclerViewItemDecorator;
import com.digital.gnsbook.ViewDialog;
import com.httpgnsbook.gnsbook.R;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Compony_list extends AppCompatActivity {
    ArrayList<Compony_data> componyModel = new ArrayList();
    ViewDialog dialog;
    RecyclerView recyclerView;

    /* renamed from: com.digital.gnsbook.Activity.Compony_list$1 */
    class C08861 extends Adapter {

        /* renamed from: com.digital.gnsbook.Activity.Compony_list$1$Holder */
        class Holder extends ViewHolder {
            RelativeLayout Follow;
            TextView desc;
            ImageView dp;
            TextView name;

            public Holder(@NonNull View view) {
                super(view);
                this.dp = (ImageView) view.findViewById(R.id.cdp);
                this.name = (TextView) view.findViewById(R.id.cName);
                this.Follow = (RelativeLayout) view.findViewById(R.id.cFollow);
                this.desc = (TextView) view.findViewById(R.id.cDesc);

            }
        }

        C08861() {
        }

        @NonNull
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            return new Holder(LayoutInflater.from(Compony_list.this).inflate(R.layout.componylist, viewGroup, false));
        }

        public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
            Holder holder = (Holder) viewHolder;
            final Compony_data compony_data = (Compony_data) Compony_list.this.componyModel.get(i);
            holder.name.setText(compony_data.name);
            holder.desc.setText(compony_data.description);
            Picasso picasso = Picasso.get();
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("http://gnsbook.com/dpic/");
            stringBuilder.append(compony_data.logo);
            picasso.load(stringBuilder.toString()).into(holder.dp);
            holder.itemView.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    Compony_list.this.startActivity(new Intent(Compony_list.this.getApplicationContext(), Companypage.class).putExtra(DbHelper.COLUMN_ID, compony_data.company_id));
                }
            });
        }

        public int getItemCount() {
            return Compony_list.this.componyModel.size();
        }
    }

    /* renamed from: com.digital.gnsbook.Activity.Compony_list$2 */
    class C08872 implements Listener<String> {
        C08872() {
        }

        public void onResponse(String responce) {
            Compony_list.this.dialog.dismiss();
            try {
                JSONObject jSONObject = new JSONObject(responce);
                if (jSONObject.getBoolean("status")) {
                    JSONArray jsonArray = jSONObject.getJSONArray("result");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jSONObject2 = jsonArray.getJSONObject(i);
                        Compony_data compony_data = new Compony_data();

                        compony_data.working_hours = jSONObject2.getString("working_hours");
                        compony_data.logo = jSONObject2.getString("logo");
                        compony_data.company_id = jSONObject2.getString("company_id");
                        compony_data.social_networks = jSONObject2.getString("social_networks");
                        compony_data.status = jSONObject2.getString("status");
                        compony_data.state = jSONObject2.getString("state");
                        compony_data.web = jSONObject2.getString("web");
                        compony_data.city = jSONObject2.getString("city");
                        compony_data.id = jSONObject2.getString(DbHelper.COLUMN_ID);
                        compony_data.location_link = jSONObject2.getString("location_link");
                        compony_data.updated_at = jSONObject2.getString("updated_at");
                        compony_data.email = jSONObject2.getString(NotificationCompat.CATEGORY_EMAIL);
                        compony_data.description = jSONObject2.getString("description");
                        compony_data.name = jSONObject2.getString("name");
                        compony_data.created_at = jSONObject2.getString("created_at");
                        compony_data.banner = jSONObject2.getString("banner");
                        compony_data.mobile = jSONObject2.getString("mobile");
                        Compony_list.this.componyModel.add(compony_data);
                        Compony_list.this.recyclerView.getAdapter().notifyDataSetChanged();
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    /* renamed from: com.digital.gnsbook.Activity.Compony_list$3 */
    class C08883 implements ErrorListener {
        C08883() {
        }

        public void onErrorResponse(VolleyError volleyError) {
            Compony_list.this.dialog.dismiss();
        }
    }

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_componylist);
        this.dialog = new ViewDialog(this);
        this.recyclerView = (RecyclerView) findViewById(R.id.rvclist);
        this.recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        this.recyclerView.addItemDecoration(new RecyclerViewItemDecorator(10));
        this.recyclerView.setAdapter(new C08861());
        getComponyData();
    }

    private void getComponyData() {
        this.dialog.show();
        AppController.getInstance().addToRequestQueue(new StringRequest(1, APIs.companydataAPI, new C08872(), new C08883()) {
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> hashMap = new HashMap();
                hashMap.put("limit", "10");
                hashMap.put("offset", "0");
                return hashMap;
            }
        });
    }
}
