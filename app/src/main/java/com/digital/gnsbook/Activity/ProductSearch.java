package com.digital.gnsbook.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.digital.gnsbook.Adapter.Product_Adapter;
import com.digital.gnsbook.Adapter.SearchAdapt;
import com.digital.gnsbook.Config.APIs;
import com.digital.gnsbook.Config.AppController;
import com.digital.gnsbook.Extra.SimpleGestureFilter;
import com.digital.gnsbook.Model.Activity_Gstore.ProductModel;
import com.digital.gnsbook.Model.Activity_Gstore.Result;
import com.digital.gnsbook.Model.SearchItem;
import com.digital.gnsbook.Model.SearchResponse;
import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.httpgnsbook.gnsbook.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class ProductSearch extends AppCompatActivity {

    RecyclerView rvSearch;
    ImageView Back,clear;
    EditText query;
    Context context;
    private Timer timer;
    ProgressBar searchProgress;
    List<SearchItem> commentModel = new ArrayList();
    boolean isCompony= false;
    FrameLayout parent;
    List<Result> postModel = new ArrayList<>();



    public static final String EXTRA_CIRCULAR_REVEAL_X = "EXTRA_CIRCULAR_REVEAL_X";
    public static final String EXTRA_CIRCULAR_REVEAL_Y = "EXTRA_CIRCULAR_REVEAL_Y";

    View rootLayout;

    private int revealX;
    private int revealY;

    private TextWatcher searchTextWatcher = new TextWatcher() {
        @Override
        public void afterTextChanged(final Editable arg0) {
            // user typed: start the timer
            timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    // do your actual work here
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            getSearchresult();

                        }
                    });

                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }


                    // hide keyboard as well?
                    // InputMethodManager in = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    // in.hideSoftInputFromWindow(searchText.getApplicationWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                }
            }, 600); // 600ms delay before the timer executes the "run" method from TimerTask
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            // nothing to do here
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            // user is typing: reset already started timer (if existing)

            if(s.toString().trim().length()==0){
                clear.setVisibility(View.GONE);
            } else {
                clear.setVisibility(View.VISIBLE);
            }

            if (timer != null) {
                timer.cancel();
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        getSupportActionBar().hide();

        parent =findViewById(R.id.parent);
        rvSearch =findViewById(R.id.rvsearch);
        searchProgress =findViewById(R.id.searchProgress);
        rvSearch.setLayoutManager(new GridLayoutManager(this,2));
        //   rvSearch.addItemDecoration(new DividerDecorator(getApplicationContext()));

        Back= (ImageView) findViewById(R.id.SearchBack);
        clear= (ImageView) findViewById(R.id.SearchClear);
        query= (EditText) findViewById(R.id.SearchQuery);
        context=getApplicationContext();

        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                query.setText("");
            }
        });
        query.addTextChangedListener(searchTextWatcher );
        final Intent intent = getIntent();


        rootLayout = findViewById(R.id.root_layout);

     /*   if (savedInstanceState == null && Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP &&
                    intent.hasExtra(EXTRA_CIRCULAR_REVEAL_X) &&
                intent.hasExtra(EXTRA_CIRCULAR_REVEAL_Y)) {
        } else {
            rootLayout.setVisibility(View.VISIBLE);
        }*/

        parent.setOnTouchListener(new SimpleGestureFilter(this)); // yourview is layout or container to swipe

    }
    private void getSearchresult() {
        searchProgress.setVisibility(View.VISIBLE);
        AppController.getInstance().addToRequestQueue(new StringRequest(StringRequest.Method.POST, APIs.SearchProduct, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                searchProgress.setVisibility(View.GONE);

                try {
                    JSONObject object = new JSONObject(response);

                    if (object.getBoolean("status")){



                        JsonReader reader = new JsonReader(new StringReader(response));
                        reader.setLenient(true);
                        ProductModel timeLineResponse = new Gson().fromJson(reader, ProductModel.class);
                        if (timeLineResponse.getResult().size() > 0) {
                            postModel.addAll(timeLineResponse.getResult());
                        }
                        rvSearch.setAdapter(new Product_Adapter(postModel, ProductSearch.this));

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                searchProgress.setVisibility(View.GONE);
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map <String,String> param = new HashMap<String,String>();
                param.put("company_id", getIntent().getStringExtra("id"));
                param.put("keyword",query.getText().toString());
                param.put("limit", "5");
                param.put("offset","0");
                return param;            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

}
