package com.digital.gnsbook.GnsChat;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.httpgnsbook.gnsbook.R;

import java.util.ArrayList;
import java.util.List;

public class ChatRoomActivity extends AppCompatActivity {

    public static final String CHAT_ROOM_ID = "CHAT_ROOM_ID";
    public static final String CHAT_ROOM_NAME = "CHAT_ROOM_NAME";
    private static final String CURRENT_USER_KEY = "CURRENT_USER_KEY";

    private String roomId = "";
    private String roomName = "";

    private String userId = "";

    private ChatRoomRepository chatRoomRepository;

    private EditText message;
    private ImageButton send;
    private RecyclerView chats;
    private ChatsAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room);
        chatRoomRepository = new ChatRoomRepository(FirebaseFirestore.getInstance());
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            roomId = extras.getString(CHAT_ROOM_ID, "");
            roomName = extras.getString(CHAT_ROOM_NAME, "");
        }

        if (getSupportActionBar() != null) {
            setTitle(roomName);
        }

        userId = getCurrentUserKey();
        initUI();
        showChatMessages();

    }

    private String getCurrentUserKey() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        return preferences.getString(CURRENT_USER_KEY, "");
    }

    private void initUI() {
        message = findViewById(R.id.message_text);
        send = findViewById(R.id.send_message);
        chats = findViewById(R.id.chats);
        chats.setLayoutManager(new LinearLayoutManager(this));


        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (message.getText().toString().isEmpty()) {
                    Toast.makeText(
                            ChatRoomActivity.this,
                            "Empty",
                            Toast.LENGTH_SHORT
                    ).show();
                } else {
                    addMessageToChatRoom();
                }
            }
        });
    }

    private void addMessageToChatRoom() {
        String chatMessage = message.getText().toString();
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
                                ChatRoomActivity.this,
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
                    Log.e("ChatRoomActivity", "Listen failed.", e);
                    return;
                }

                List<Chat> messages = new ArrayList<>();
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

                adapter = new ChatsAdapter(messages, userId);
                chats.setAdapter(adapter);
            }
        });
    }
}

