package com.digital.gnsbook.Payment;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AlertDialog.Builder;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.digital.gnsbook.Activity.MainActivity;
import com.digital.gnsbook.Config.APIs;
import com.digital.gnsbook.Config.AppController;
import com.digital.gnsbook.Global;
import com.digital.gnsbook.Model.Benificiary;
import com.digital.gnsbook.ViewDialog;
import com.httpgnsbook.gnsbook.R;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Manual_Payment extends AppCompatActivity {

    RelativeLayout AddBeneficiary;
    RecyclerView recyclerView;
    ViewDialog dialog;
    ArrayList<Benificiary> list = new ArrayList< >();
    ColorGenerator generator = ColorGenerator.MATERIAL;
    Button Transfer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_benificiary_list);
        // Initialize Variable


        initialize();
        // getBenificary();
        getBenificarydata();

    }

    private void getBenificarydata() {
        ShowProgress();
        StringRequest postRequest = new StringRequest(Request.Method.POST, APIs.ACTIVEBenificiarylist,
                new Response.Listener <String> () {
                    @Override
                    public void onResponse(String response) {
                        Manual_Payment.this.Dissmiss();
                        Manual_Payment.this.dialog.dismiss();
                  //      Log.d("Comment_Response", str);
                        try {
                            JSONArray jsonArray = new JSONObject(response).getJSONArray("result");
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
                                Manual_Payment.this.list.add(benificiary);

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
                                            Global.diloge(Manual_Payment.this,"STATUS PENDING" , "YOUR BENEFICIARY STATUS IS IN PENDING PLEASE CONTACT SUPPORT TO ACTIVATE");
                                        }else if(current.status.equals("1")){
                                            Intent intent = new Intent(getApplicationContext(), ManualTransaction.class);
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
                                        Intent intent = new Intent(getApplicationContext(), Manual_Payment.class);
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
                                            builder = new AlertDialog.Builder(Manual_Payment.this);
                                        } else {
                                            builder = new AlertDialog.Builder(Manual_Payment.this);
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
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        Dissmiss();
                        dialog.dismiss();
                        final Dialog dialog = new Dialog(Manual_Payment.this);
                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        dialog.setCancelable(false);
                        dialog.setContentView(R.layout.connectionerror);
                        Button button = dialog.findViewById(R.id.btnOK);
                        button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                startActivity(new Intent(getApplicationContext(),MainActivity.class));
                            }
                        });

                        dialog.show();
                    }
                }
        ) {

            @Override
            public String getBodyContentType() {
                return "application/x-www-form-urlencoded; charset=UTF-8";
            }


            @Override
            protected Map< String, String > getParams() {
                Map< String, String > params = new HashMap< String, String >();
                params.put("mobile", Global.mobile);
                params.put("customer_id", Global.customerid);
                return params;
            }
        };
        AppController.getInstance().addToRequestQueue(postRequest);
    }


    private void deleteBenificiary(String id, String agentid, final String unique_id, final String beneficiary_id)
    {
        StringRequest request =new StringRequest(StringRequest.Method.POST, APIs.BeniDelete, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject =new JSONObject(response);
                    Toast.makeText(Manual_Payment.this, jsonObject.getString("result"), Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                //Toast.makeText(BenificiaryList.this, response, Toast.LENGTH_SHORT).show();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                final Dialog dialog = new Dialog(Manual_Payment.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setCancelable(false);
                dialog.setContentView(R.layout.connectionerror);
                Button button = dialog.findViewById(R.id.btnOK);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(getApplicationContext(),MainActivity.class));
                    }
                });

                dialog.show();              }
        }){

            @Override
            public String getBodyContentType() {
                return "application/x-www-form-urlencoded; charset=UTF-8";
            }


            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<String, String>();

                params.put("unique_id", unique_id);

                return params;
            }

        };
        AppController.getInstance().addToRequestQueue(request);
    }

    private void initialize() {
        AddBeneficiary = findViewById(R.id.fabAddbeneficiary);
        recyclerView = findViewById(R.id.benilist);
        dialog = new ViewDialog(Manual_Payment.this);
        AddBeneficiary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), AddBeneficiary.class));
            }
        });


    }



    @Override
    protected void onResume() {
        super.onResume();


    }

    private void ShowProgress() {
        dialog.show();
    }
    private void Dissmiss() {
        dialog.dismiss();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(new Intent(getApplicationContext(), MainActivity.class)));

    }

}
