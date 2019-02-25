package com.digital.gnsbook.Fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
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
import com.digital.gnsbook.Global;
import com.digital.gnsbook.Model.Top_Performer;
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

public class ThreeFragment extends Fragment {
    ArrayList<Top_Performer> componyModel = new ArrayList();
    ViewDialog dialog;
    RecyclerView recyclerView;

    /* renamed from: com.digital.gnsbook.Fragment.ThreeFragment$1 */
    class C09291 extends Adapter {

        /* renamed from: com.digital.gnsbook.Fragment.ThreeFragment$1$Holder */
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

        C09291() {
        }

        @NonNull
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            return new Holder(LayoutInflater.from(ThreeFragment.this.getActivity()).inflate(R.layout.componylist, viewGroup, false));
        }

        public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
            Holder holder = (Holder) viewHolder;
            Top_Performer top_Performer = (Top_Performer) ThreeFragment.this.componyModel.get(i);
            TextView textView = holder.name;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(top_Performer.name);
            stringBuilder.append(" ");
            stringBuilder.append(top_Performer.last_name);
            textView.setText(stringBuilder.toString());
            holder.desc.setText(top_Performer.city);
            Picasso picasso = Picasso.get();
            stringBuilder = new StringBuilder();
            stringBuilder.append("http://gnsbook.com/dpic/");
            stringBuilder.append(top_Performer.d_pic);
            picasso.load(stringBuilder.toString()).into(holder.dp);
        }

        public int getItemCount() {
            return ThreeFragment.this.componyModel.size();
        }
    }

    /* renamed from: com.digital.gnsbook.Fragment.ThreeFragment$2 */
    class C09302 implements Listener<String> {
        C09302() {
        }

        public void onResponse(String responce) {
            ThreeFragment.this.dialog.dismiss();
            try {
                JSONArray str = new JSONObject(responce).getJSONArray("result");
                for (int i = 0; i < str.length(); i++) {
                    JSONObject jSONObject = str.getJSONObject(i);
                    Top_Performer top_Performer = new Top_Performer();
                    top_Performer.agent_status = jSONObject.getString("agent_status");
                    top_Performer.customer_id = jSONObject.getString("customer_id");
                    top_Performer.referral_id = jSONObject.getString("referral_id");
                    top_Performer.spill_id = jSONObject.getString("spill_id");
                    top_Performer.agent_id = jSONObject.getString("agent_id");
                    top_Performer.d_pic = jSONObject.getString("d_pic");
                    top_Performer.id = jSONObject.getString(DbHelper.COLUMN_ID);
                    top_Performer.b_pic = jSONObject.getString("b_pic");
                    top_Performer.updated_at = jSONObject.getString("updated_at");
                    top_Performer.email = jSONObject.getString(NotificationCompat.CATEGORY_EMAIL);
                    top_Performer.last_name = jSONObject.getString("last_name");
                    top_Performer.name = jSONObject.getString("name");
                    top_Performer.mobile = jSONObject.getString("mobile");
                    top_Performer.city = jSONObject.getString("city");
                    ThreeFragment.this.componyModel.add(top_Performer);
                    ThreeFragment.this.recyclerView.getAdapter().notifyDataSetChanged();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    /* renamed from: com.digital.gnsbook.Fragment.ThreeFragment$3 */
    class C09313 implements ErrorListener {
        C09313() {
        }

        public void onErrorResponse(VolleyError volleyError) {
            ThreeFragment.this.dialog.dismiss();
        }
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View view = layoutInflater.inflate(R.layout.activity_toperformer, viewGroup, false);
        this.dialog = new ViewDialog(getActivity());
        this.recyclerView = view.findViewById(R.id.rvtoplist);
        this.recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        this.recyclerView.addItemDecoration(new RecyclerViewItemDecorator(10));
        this.recyclerView.setAdapter(new C09291());
        getComponyData();
        return view;
    }

    private void getComponyData() {
        this.dialog.show();
        AppController.getInstance().addToRequestQueue(new StringRequest(1, APIs.freinds, new C09302(), new C09313()) {
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> hashMap = new HashMap();
                hashMap.put("data", Global.customerid);
                return hashMap;
            }
        });
    }
}
