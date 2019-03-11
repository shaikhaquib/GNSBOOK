package com.digital.gnsbook.Activity;

        import android.os.Bundle;
        import android.support.annotation.NonNull;
        import android.support.v7.app.AppCompatActivity;
        import android.support.v7.widget.LinearLayoutManager;
        import android.support.v7.widget.RecyclerView;
        import android.support.v7.widget.RecyclerView.Adapter;
        import android.support.v7.widget.RecyclerView.ViewHolder;
        import android.text.Editable;
        import android.text.TextWatcher;
        import android.view.LayoutInflater;
        import android.view.MenuItem;
        import android.view.View;
        import android.view.View.OnClickListener;
        import android.view.ViewGroup;
        import android.widget.EditText;
        import android.widget.ImageView;
        import android.widget.LinearLayout;
        import android.widget.TextView;
        import com.android.volley.AuthFailureError;
        import com.android.volley.Request;
        import com.android.volley.Response.ErrorListener;
        import com.android.volley.Response.Listener;
        import com.android.volley.VolleyError;
        import com.android.volley.toolbox.StringRequest;
        import com.digital.gnsbook.Config.APIs;
        import com.digital.gnsbook.Config.AppController;
        import com.digital.gnsbook.Config.PaginationScrollListener;
        import com.digital.gnsbook.DividerDecorator;
        import com.digital.gnsbook.Global;
        import com.digital.gnsbook.Model.CommentItem;
        import com.digital.gnsbook.Model.Comment_Response;
        import com.digital.gnsbook.ViewDialog;
        import com.github.vipulasri.timelineview.TimelineView;
        import com.google.gson.Gson;
        import com.httpgnsbook.gnsbook.R;
        import com.squareup.picasso.Picasso;
        import java.text.ParseException;
        import java.text.SimpleDateFormat;
        import java.util.ArrayList;
        import java.util.Date;
        import java.util.HashMap;
        import java.util.List;
        import java.util.Map;

        import org.json.JSONException;
        import org.json.JSONObject;

public class Comment extends AppCompatActivity {
    EditText EdtComment;
    RecyclerView Rv_Comment;
    ImageView addComment;
    ImageView commentDP;
    List<CommentItem> commentModel = new ArrayList();
    TextView comment_Error;
    int count = 20;
    int currentItems;
    private boolean isLastPage = false;
    private boolean isLoading = false;
    Boolean isScrolling = Boolean.valueOf(false);
    int itemcount;
    LinearLayoutManager layoutManager;
    int limit = 20;
    int offset = 0;
    ViewDialog porogress;
    int scrollOutItems;
    LinearLayout sendcommentprogress;
    TimelineView timelineView;
    int totalItems;

    int pastVisiblesItems, visibleItemCount, totalItemCount;
    boolean loading = true;

    /* renamed from: com.digital.gnsbook.Activity.Comment$2 */
    class C04192 implements TextWatcher {
        public void afterTextChanged(Editable editable) {
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }

        C04192() {
        }

        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            if (charSequence.toString().trim().length() == 0) {
                Comment.this.addComment.setVisibility(View.GONE);
            } else {
                Comment.this.addComment.setVisibility(View.VISIBLE);
            }
        }
    }

    /* renamed from: com.digital.gnsbook.Activity.Comment$3 */
    class C04203 implements OnClickListener {
        C04203() {
        }

        public void onClick(View view) {
            Comment.this.Docomment();
        }
    }

    /* renamed from: com.digital.gnsbook.Activity.Comment$1 */
    class C08881 extends Adapter {

        /* renamed from: com.digital.gnsbook.Activity.Comment$1$Holder */
        class Holder extends ViewHolder {
            TextView Comment;
            TextView Date;
            ImageView Dp;
            TextView Name;

            public Holder(@NonNull View view) {
                super(view);
                this.Dp = (ImageView) view.findViewById(R.id.cDp);
                this.Name = (TextView) view.findViewById(R.id.cName);
                this.Comment = (TextView) view.findViewById(R.id.ccomment);
                this.Date = (TextView) view.findViewById(R.id.cdate);
            }
        }

        C08881() {
        }

        @NonNull
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            return new Holder(LayoutInflater.from(Comment.this).inflate(R.layout.comment_view, viewGroup, false));
        }

        public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
            Holder holder = (Holder) viewHolder;
            CommentItem commentItem = (CommentItem)commentModel.get(i);
            String[] split = commentItem.getCreatedAt().split(" ");
            holder.Name.setText(commentItem.getName());
            holder.Comment.setText(commentItem.getComment());
            holder.Date.setText(Comment.this.getDaysAgo(split[0], split[1]));
            Picasso picasso = Picasso.get();
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(APIs.Dp);
            stringBuilder.append(commentItem.getDPic());
            picasso.load(stringBuilder.toString()).into(holder.Dp);
        }

        public int getItemCount() {
            return Comment.this.commentModel.size();
        }
    }

    /* renamed from: com.digital.gnsbook.Activity.Comment$5 */
    class C08895 implements Listener<String> {
        C08895() {
        }

        public void onResponse(String str) {
            Comment.this.porogress.dismiss();
            try {
                JSONObject jSONObject = new JSONObject(str);
                if (jSONObject.getBoolean("status")) {
                    Comment_Response comment_Response = (Comment_Response) new Gson().fromJson(str, Comment_Response.class);
                    commentModel.addAll(comment_Response.getResult());
                    Comment.this.Rv_Comment.getAdapter().notifyDataSetChanged();
                    isLastPage = false;
                }else {
                    Comment.this.count = 0;
                    Comment.this.isLastPage = true;
                    isLoading = false;
                    Comment.this.comment_Error.setVisibility(View.VISIBLE);
                    Comment.this.comment_Error.setText(jSONObject.getString("result"));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    /* renamed from: com.digital.gnsbook.Activity.Comment$6 */
    class C08906 implements ErrorListener {
        C08906() {
        }

        public void onErrorResponse(VolleyError volleyError) {
            Comment.this.porogress.dismiss();
        }
    }

    /* renamed from: com.digital.gnsbook.Activity.Comment$8 */
    class C08918 implements Listener<String> {
        C08918() {
        }

        public void onResponse(String st) {
            Comment.this.sendcommentprogress.setVisibility(View.GONE);
            try {
                JSONObject jSONObject = new JSONObject(st);
                if (jSONObject.getBoolean("status")) {
                    Comment.this.comment_Error.setVisibility(View.GONE);
                    Comment.this.EdtComment.setText("");
                    CommentItem str = new CommentItem();
                    str.setComment(jSONObject.getJSONObject("result").getString("comment"));
                    str.setCustomerId(jSONObject.getJSONObject("result").getInt("customer_id"));
                    str.setDPic(Global.DP);
                    str.setName(Global.name);
                    str.setPostId(jSONObject.getJSONObject("result").getInt("post_id"));
                    str.setCreatedAt(jSONObject.getJSONObject("result").getString("created_at"));
                    Comment.this.commentModel.add(str);
                    Comment.this.Rv_Comment.scrollToPosition(Comment.this.commentModel.size() - 1);
                    Comment.this.Rv_Comment.getAdapter().notifyDataSetChanged();
                }else {
                Global.diloge(Comment.this, "Comment Error", jSONObject.getString("result"));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    /* renamed from: com.digital.gnsbook.Activity.Comment$9 */
    class C08929 implements ErrorListener {
        C08929() {
        }

        public void onErrorResponse(VolleyError volleyError) {
            Comment.this.sendcommentprogress.setVisibility(View.GONE);
        }
    }

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_comment);
        setTitle("COMMENTS");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        this.porogress = new ViewDialog(this);
        this.Rv_Comment = (RecyclerView) findViewById(R.id.rv_Comment);
        this.comment_Error = (TextView) findViewById(R.id.comment_Error);
        this.EdtComment = (EditText) findViewById(R.id.comment_box);
        this.addComment = (ImageView) findViewById(R.id.addComment);
        this.commentDP = (ImageView) findViewById(R.id.commentDP);
        this.sendcommentprogress = (LinearLayout) findViewById(R.id.sendcommentprogress);
        this.layoutManager = new LinearLayoutManager(getApplicationContext());
        this.Rv_Comment.setItemAnimator(null);
        this.Rv_Comment.setLayoutManager(this.layoutManager);
        this.Rv_Comment.addItemDecoration(new DividerDecorator(getApplicationContext()));
        this.Rv_Comment.setAdapter(new C08881());
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append(APIs.Dp);
        stringBuilder.append(Global.DP);
        Picasso.get().load(stringBuilder.toString()).into(this.commentDP);
        this.EdtComment.addTextChangedListener(new C04192());
        this.addComment.setOnClickListener(new C04203());
        this.Rv_Comment.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (dy > 0) {
                //    Log.e("test","reached the last element of recyclerview");
                    visibleItemCount = layoutManager.getChildCount();
                    totalItemCount = layoutManager.getItemCount();
                    pastVisiblesItems = layoutManager.findFirstVisibleItemPosition();

                    if (loading) {
                        if ((visibleItemCount + pastVisiblesItems) >= totalItemCount && count > 0) {
                            loading = false;
                            Comment.this.isLoading = true;
                            Comment.this.offset += 20;
                            Comment.this.getCommentData();
                        }
                    }
                }
            }
        });
                getCommentData();
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() != 16908332) {
            return super.onOptionsItemSelected(menuItem);
        }
        finish();
        return true;
    }

    private String getDaysAgo(String str, String str2) {
        Date parse;
        try {
            parse = new SimpleDateFormat("yyyy-MM-dd").parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
            parse = null;
        }
        long time = (new Date().getTime() - parse.getTime()) / 86400000;
        if (time == 0) {
            StringBuilder  st = new StringBuilder();
            st.append("Today at ");
            st.append(Global.Time(str2));
            return str.toString();
        } else if (time != 1) {
            return Global.Date(str);
        } else {
            StringBuilder st = new StringBuilder();
            st.append("Yesterday at ");
            st.append(Global.Time(str2));
            return str.toString();
        }
    }

    private void getCommentData() {
        this.porogress.show();
        AppController.getInstance().addToRequestQueue(new StringRequest(StringRequest.Method.POST, APIs.Comment_data, new C08895(), new C08906()) {
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> hashMap = new HashMap();
                hashMap.put("post_id",getIntent().getStringExtra("pid"));
                hashMap.put("offset", String.valueOf(Comment.this.offset));
                hashMap.put("limit", String.valueOf(Comment.this.limit));
                hashMap.put("type", getIntent().getStringExtra("type"));
                return hashMap;
            }
        });
    }

    private void Docomment() {
        this.sendcommentprogress.setVisibility(View.VISIBLE);
        AppController.getInstance().addToRequestQueue(new StringRequest(1, APIs.DoComment, new C08918(), new C08929()) {
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> hashMap = new HashMap();
                hashMap.put("post_id", Comment.this.getIntent().getStringExtra("pid"));
                hashMap.put("customer_id", Global.customerid);
                hashMap.put("comment", Comment.this.EdtComment.getText().toString());
                hashMap.put("type", getIntent().getStringExtra("type"));
                return hashMap;
            }
        });
    }
}
