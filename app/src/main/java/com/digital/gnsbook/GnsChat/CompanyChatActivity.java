package com.digital.gnsbook.GnsChat;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.digital.gnsbook.Config.APIs;
import com.digital.gnsbook.Config.AppController;
import com.digital.gnsbook.Config.SqlLastMessage;
import com.digital.gnsbook.Extra.Ago;
import com.digital.gnsbook.Extra.WrappedDrawable;
import com.digital.gnsbook.Firebase.Chat_FCM;
import com.digital.gnsbook.Global;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.httpgnsbook.gnsbook.R;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CompanyChatActivity extends AppCompatActivity {

    public static final String CHAT_ROOM_ID = "CHAT_ROOM_ID";
    public static final String CHAT_ROOM_NAME = "CHAT_ROOM_NAME";
    public static final String CHAT_cdp = "CHAT_cdp";
    public static final String CHAT_fid = "CHAT_fid";
    public static final String TYPE = "TYPE";
    private static final String CURRENT_USER_KEY = "CURRENT_USER_KEY";
    View rootView;
    private String roomId = "";
    private String roomName = "";
    private String userId = "";

    Ago ago;

    private ChatRoomRepository chatRoomRepository;

    private EditText message;
    private ImageButton send;
    private RecyclerView chats;
    private ChatsAdapter adapter;
    Bitmap bitmap;
    ImageView cdp;
    Bundle extras;
    SqlLastMessage sqlLastMessage;
    List<Chat> messagesList = new ArrayList<>();
    LinearLayout startchat,layChat;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room);
        ago = new Ago().locale(getApplicationContext());

        sqlLastMessage = new SqlLastMessage(this);
        chatRoomRepository = new ChatRoomRepository(FirebaseFirestore.getInstance());
        setTitle("");
        extras = getIntent().getExtras();
        if (extras != null) {
            roomId = extras.getString(CHAT_ROOM_ID, "");
            roomName = extras.getString(CHAT_ROOM_NAME, "");
        }

        userId = getCurrentUserKey();
        initUI();
        showChatMessages();

        startchat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startchat();
            }
        });

    }

    private void startchat() {
        {


            final ProgressDialog progressDialog = new ProgressDialog(CompanyChatActivity.this);
            progressDialog.setMessage("Sending Request..");
            progressDialog.setCancelable(false);

            AppController.getInstance().addToRequestQueue(new StringRequest(StringRequest.Method.POST, APIs.add_chat_request, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    progressDialog.dismiss();

                    Log.d("responce",response);

                    try {
                        JSONObject object = new JSONObject(response);

                        if (object.getBoolean("status"))
                        {
                            Toast.makeText(getApplicationContext(), "Added Successfully ! you can now send your first message. ", Toast.LENGTH_SHORT).show();
                            layChat.setVisibility(View.VISIBLE);
                            startchat.setVisibility(View.GONE);
                        }
                        else
                        {
                            Toast.makeText(getApplicationContext(), "Failed Please try again after some time", Toast.LENGTH_SHORT).show();

                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    progressDialog.dismiss();
                }
            }){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> hashMap = new HashMap();
                    hashMap.put("customer_id", Global.customerid);
                    hashMap.put("channel_id", Global.Company_Id+"_"+Global.customerid);
                    hashMap.put("company_id", Global.Company_Id);
                    return hashMap;
                }
            });

        }
    }

    private String getCurrentUserKey() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        return preferences.getString(CURRENT_USER_KEY, "");
    }
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() != 16908332) {
            return super.onOptionsItemSelected(menuItem);
        }
        onStop();
        return true;
    }

    @Override
    protected void onStop() {
        super.onStop();

        if (messagesList.size()>0) {
            sqlLastMessage.addMessage(messagesList.get(0).chatRoomId,//Channel_id
                    messagesList.get(0).message,//Message
                    String.valueOf(messagesList.get(0).sent));//Time Stamp
        }else {
            startchat.setVisibility(View.VISIBLE);
            layChat.setVisibility(View.GONE);
        }
     //   finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Global.sender_id ="0";
    }



    private void initUI() {

        Global.sender_id = extras.getString(CHAT_fid, "");
        rootView = getWindow().getDecorView().getRootView();
        message = findViewById(R.id.message_text);
        cdp = findViewById(R.id.cdp);
        send = findViewById(R.id.send_message);
        layChat = findViewById(R.id.layChat);
        startchat = findViewById(R.id.startchat);
        chats = findViewById(R.id.chats);
        chats.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,true));

        Picasso.get().load(APIs.Dp + extras.getString(CHAT_cdp, "")).into(cdp);
        Drawable drawable = cdp.getDrawable();
        WrappedDrawable wrappedDrawable = new WrappedDrawable(drawable);
// set bounds on wrapper
        wrappedDrawable.setBounds(0,0,70,70);
        // getSupportActionBar().setIcon(wrappedDrawable);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle(roomName);
        getSupportActionBar().setLogo(wrappedDrawable);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        final Drawable upArrow = getResources().getDrawable(R.drawable.ic_nav_back);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (message.getText().toString().isEmpty()) {
                    Toast.makeText(CompanyChatActivity.this, "Empty", Toast.LENGTH_SHORT).show();
                } else {
                    addMessageToChatRoom();
                }
            }
        });

      /*  final EmojiPopup emojiPopup = EmojiPopup.Builder.fromRootView().build(message);
        emojiPopup.toggle(); // Toggles visibility of the Popup.
        emojiPopup.dismiss(); // Dismisses the Popup.
        emojiPopup.isShowing(); */// Returns true when Popup is showing.
    }

    private void addMessageToChatRoom() {
        String chatMessage = message.getText().toString();
        new Chat_FCM().execute(extras.getString(CHAT_fid, ""), chatMessage,Global.name+" has sent you Message" ,Global.customerid,roomId);

        message.setText("");
        send.setEnabled(false);
        chatRoomRepository.addMessageToChatRoom(
                roomId,
                userId,
                chatMessage,
                new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        send.setEnabled(true);
                    }
                },
                new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        send.setEnabled(true);
                        Toast.makeText(
                                CompanyChatActivity.this,
                                "Error",
                                Toast.LENGTH_SHORT
                        ).show();
                    }
                }
        );
    }

    private void showChatMessages() {
        chatRoomRepository.getChats(roomId, new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(QuerySnapshot snapshots, FirebaseFirestoreException e) {
                if (e != null) {
                    Log.e("CompanyChatActivity", "Listen failed.", e);
                    return;
                }

                List<Chat> messages = new ArrayList<>();
                messagesList = messages;
                for (QueryDocumentSnapshot doc : snapshots) {
                    Chat chat = new Chat();
                   /* messages.add(
                            new Chat(
                                    doc.getId(),
                                    doc.getString("chat_room_id"),
                                    doc.getString("sender_id"),
                                    doc.getString("message"),
                                    doc.getLong("sent")
                            )
                    );*/
                    chat.id=doc.getId();
                    chat.chatRoomId=doc.getString("chat_room_id");
                    chat.senderId=doc.getString("sender_id");
                    chat.message=doc.getString("message");
                    chat.sent=doc.getLong("sent");

                    messages.add(chat);
                    Log.d ("chat_room_id",doc.getString("chat_room_id"));
                    Log.d ("sender_id", doc.getString("sender_id"));
                }

                adapter = new ChatsAdapter(messages, userId,CompanyChatActivity.this);
                chats.setAdapter(adapter);
            }
        });

        if (messagesList.size()>0 && getIntent().getStringExtra(TYPE).equals("0")) {
            startchat.setVisibility(View.GONE);
            layChat.setVisibility(View.VISIBLE);
        }else {
            startchat.setVisibility(View.VISIBLE);
            layChat.setVisibility(View.GONE);
        }
    }
}

