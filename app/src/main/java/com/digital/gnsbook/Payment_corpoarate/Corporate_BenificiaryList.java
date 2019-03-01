package com.digital.gnsbook.Payment_corpoarate;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AlertDialog.Builder;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.android.volley.AuthFailureError;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.digital.gnsbook.Activity.MainActivity;
import com.digital.gnsbook.Config.APIs;
import com.digital.gnsbook.Config.AppController;
import com.digital.gnsbook.Global;
import com.digital.gnsbook.Model.Benificiary;
import com.httpgnsbook.gnsbook.R;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Corporate_BenificiaryList extends AppCompatActivity {
    RelativeLayout AddBeneficiary;
    Button Transfer;
    ProgressDialog dialog;
    ColorGenerator generator = ColorGenerator.MATERIAL;
    ArrayList<Benificiary> list = new ArrayList();
    RecyclerView recyclerView;

    /* renamed from: com.digital.gnsbook.Payment_corpoarate.Corporate_BenificiaryList$7 */
    class C05567 implements OnClickListener {
        C05567() {
        }

        public void onClick(View view) {
            Corporate_BenificiaryList.this.startActivity(new Intent(Corporate_BenificiaryList.this.getApplicationContext(), Corporate_AddBeneficiary.class));
        }
    }

    /* renamed from: com.digital.gnsbook.Payment_corpoarate.Corporate_BenificiaryList$1 */
    class C09971 implements Listener<String> {

        /* renamed from: com.digital.gnsbook.Payment_corpoarate.Corporate_BenificiaryList$1$1 */


        public void onResponse(String str) {
            Corporate_BenificiaryList.this.Dissmiss();
            Corporate_BenificiaryList.this.dialog.dismiss();
            Log.d("Comment_Response", str);
            try {
                JSONArray jsonArray = new JSONObject(str).getJSONArray("result");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jSONObject = jsonArray.getJSONObject(i);
                    Benificiary benificiary = new Benificiary();
                    benificiary.account_number = jSONObject.getString("account_number");
                    benificiary.beneficiary_id = jSONObject.getString("beneficiary_id");
                    benificiary.beneficiary_name = jSONObject.getString("beneficiary_name");
                    benificiary.ifsc = jSONObject.getString("ifsc");
                    benificiary.mobile = jSONObject.getString("beneficiary_mobile");
                    benificiary.status = jSONObject.getString("status");
                    benificiary.benstatus = jSONObject.getString("beneficiary_status");
                    if (!benificiary.status.equals("0")) {
                        Corporate_BenificiaryList.this.list.add(benificiary);
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
         recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

            recyclerView.setAdapter(new RecyclerView.Adapter() {
                @Override
                public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

                    View view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.benificary, parent, false);
                    ViewHolder viewHolder = new ViewHolder(view);
                    return viewHolder;
                }

                @Override
                public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

                    final ViewHolder viewHolder = (ViewHolder) holder;
                    final Benificiary current = list.get(position);


                    viewHolder.name.setText(current.beneficiary_name);
                    viewHolder.mobile.setText(current.mobile);
                    viewHolder.ifsc.setText(current.ifsc);
                    viewHolder.account.setText(current.account_number);
                    //   viewHolder.imageView.setTag(position);
                    viewHolder.benCard.setTag(position);

                    if(current.status.equals("-1")){
                        viewHolder.benCard.setBackgroundColor(Color.parseColor("#2affa726"));
                    }

                    viewHolder.layout.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            if(current.status.equals("-1")){
                                Global.diloge(Corporate_BenificiaryList.this,"STATUS PENDING" , "YOUR BENEFICIARY STATUS IS IN PENDING PLEASE CONTACT SUPPORT TO ACTIVATE");
                            }else if(current.status.equals("1")){
                                Intent intent = new Intent(getApplicationContext(), Corporate_FundTransfer.class);
                                //   intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);

                                intent.putExtra("accountno", current.account_number);
                                intent.putExtra("ifsc", current.ifsc);
                                intent.putExtra("Bid",current.beneficiary_id);

                                startActivity(intent);}
                        }
                    });

                    viewHolder.letter.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(getApplicationContext(), Corporate_FundTransfer.class);
                            //   intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);

                            intent.putExtra("accountno", current.account_number);
                            intent.putExtra("ifsc", current.ifsc);
                            intent.putExtra("Bid",current.beneficiary_id);

                            startActivity(intent);
                        }
                    });


                    //        Get the first letter of list item
                    String letter = String.valueOf(current.beneficiary_name.charAt(0));

//        Create a new TextDrawable for our image's background
                    TextDrawable drawable = TextDrawable.builder().buildRound(letter, generator.getRandomColor());

                    viewHolder.letter.setImageDrawable(drawable);


                    viewHolder.menu.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            AlertDialog.Builder builder;
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                builder = new AlertDialog.Builder(Corporate_BenificiaryList.this);
                            } else {
                                builder = new AlertDialog.Builder(Corporate_BenificiaryList.this);
                            }
                            builder.setTitle("Delete Beneficiary")
                                    .setMessage("Are you sure you want to delete this Beneficiary?")
                                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            // continue with delete
                                            deleteBenificiary(Global.customerid,Global.agentid,current.unique_id,current.beneficiary_id);
                                            list.remove(position);
                                            notifyItemRemoved(position);
                                            notifyItemRangeChanged(position, list.size());
                                        }
                                    })
                                    .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            // do nothing
                                        }
                                    })
                                    .setIcon(R.drawable.ic_report_problem)
                                    .show();
                        }
                    });

                }

                @Override
                public int getItemCount() {
                    return list.size();
                }

                class ViewHolder extends RecyclerView.ViewHolder {

                    ImageView  menu ;
                    ImageView letter;
                    LinearLayout layout;
                    TextView name, ifsc, mobile, account;
                    LinearLayout benCard;
                    public ViewHolder(View itemView) {
                        super(itemView);

                        //  imageView = itemView.findViewById(R.id.FundTrans);
                        layout=itemView.findViewById(R.id.benlayout);
                        menu = itemView.findViewById(R.id.bendelit);
                        name = itemView.findViewById(R.id.beName);
                        mobile = itemView.findViewById(R.id.beMobile);
                        ifsc = itemView.findViewById(R.id.beIfsc);
                        account = itemView.findViewById(R.id.beAccount);
                        benCard = itemView.findViewById(R.id.benCard);
                        letter = (ImageView) itemView.findViewById(R.id.gmailitem_letter);
                    }
                }
            });


        }
    }

    /* renamed from: com.digital.gnsbook.Payment_corpoarate.Corporate_BenificiaryList$2 */
    class C09982 implements ErrorListener {

        /* renamed from: com.digital.gnsbook.Payment_corpoarate.Corporate_BenificiaryList$2$1 */

        C09982() {
        }

        public void onErrorResponse(VolleyError volleyError) {

        }
    }

    /* renamed from: com.digital.gnsbook.Payment_corpoarate.Corporate_BenificiaryList$4 */
    class C09994 implements Listener<String> {
        C09994() {
        }

        public void onResponse(String str) {
            try {
                Toast.makeText(Corporate_BenificiaryList.this, new JSONObject(str).getString("result"), Toast.LENGTH_LONG).show();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    /* renamed from: com.digital.gnsbook.Payment_corpoarate.Corporate_BenificiaryList$5 */

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_benificiary_list);
        initialize();
        getBenificarydata();
        limitTransaction();
    }

    private void getBenificarydata() {
        ShowProgress();
        AppController.getInstance().addToRequestQueue(new StringRequest(1, APIs.ACTIVEBenificiarylist, new C09971(), new C09982()) {
            public String getBodyContentType() {
                return "application/x-www-form-urlencoded; charset=UTF-8";
            }

            protected Map<String, String> getParams() {
                Map<String, String> hashMap = new HashMap();
                hashMap.put("mobile", Global.mobile);
                hashMap.put("customer_id", Global.customerid);
                return hashMap;
            }
        });
    }

    private void deleteBenificiary(String str, String str2, String str3, String str4) {}

    private void initialize() {
        this.AddBeneficiary = (RelativeLayout) findViewById(R.id.fabAddbeneficiary);
        this.recyclerView = (RecyclerView) findViewById(R.id.benilist);
        this.dialog = new ProgressDialog(this);
        this.AddBeneficiary.setOnClickListener(new C05567());
    }

    protected void onResume() {
        super.onResume();
    }

    private void ShowProgress() {
        this.dialog.setMessage("Getting beneficiary data...");
        this.dialog.setCancelable(false);
        this.dialog.show();
    }

    private void Dissmiss() {
        this.dialog.dismiss();
    }

    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(new Intent(getApplicationContext(), MainActivity.class)));
    }


    private void limitTransaction() {
        this.dialog.setMessage("Verifying...");
        this.dialog.show();

        AppController.getInstance().addToRequestQueue(new StringRequest(1, APIs.limitTransaction, new Listener<String>() {
            public void onResponse(String str) {
                dialog.dismiss();
            }
        }, new ErrorListener() {
            public void onErrorResponse(VolleyError volleyError) {
                dialog.dismiss();
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
