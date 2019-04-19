package com.digital.gnsbook.Activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.digital.gnsbook.Config.APIs;
import com.digital.gnsbook.Config.AppController;
import com.digital.gnsbook.Extra.DividerDecorator;
import com.digital.gnsbook.Adapter.SearchAdapt;
import com.digital.gnsbook.Model.CommentItem;
import com.digital.gnsbook.Model.SearchItem;
import com.digital.gnsbook.Model.SearchResponse;
import com.google.gson.Gson;
import com.httpgnsbook.gnsbook.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class SearchActivity extends AppCompatActivity {

    RecyclerView rvSearch;
    ImageView Back,clear;
    EditText query;
    Context context;
    private Timer timer;
    ProgressBar searchProgress;
    List<SearchItem> commentModel = new ArrayList();



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
                    SearchActivity.this.runOnUiThread(new Runnable() {
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

        rvSearch =findViewById(R.id.rvsearch);
        searchProgress =findViewById(R.id.searchProgress);
        rvSearch.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        rvSearch.addItemDecoration(new DividerDecorator(getApplicationContext()));

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

    }
    private void getSearchresult() {
        searchProgress.setVisibility(View.VISIBLE);
        AppController.getInstance().addToRequestQueue(new StringRequest(StringRequest.Method.POST, APIs.Search, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                    searchProgress.setVisibility(View.GONE);
                    commentModel.clear();

                    JSONObject jsonObject = new JSONObject(response);

                    if (jsonObject.getBoolean("status")){
                        JSONArray jsonArray = jsonObject.getJSONArray("result");
                        SearchResponse searchResponse = (SearchResponse) new Gson().fromJson(response, SearchResponse.class);
                        commentModel=searchResponse.getResult();
                        rvSearch.setAdapter(new SearchAdapt(SearchActivity.this,commentModel));

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
                param.put("limit", "10");
                param.put("offset","0");
                param.put("keyword",query.getText().toString());
                return param;            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

}
