package com.digital.gnsbook.GnsChat;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.httpgnsbook.gnsbook.R;

public class CreateRoomActivity extends AppCompatActivity {

    private EditText roomName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_room);

        roomName = findViewById(R.id.room_name);

        setTitle("Create Room");

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_clear);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.create_room_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.create_room:
                if (isRoomEmpty()) {
                    Toast.makeText(this, "Empty", Toast.LENGTH_SHORT).show();
                } else {
                    createRoom();
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private boolean isRoomEmpty() {
        return roomName.getText().toString().isEmpty();
    }

    private void createRoom() {
        new ChatRoomRepository(FirebaseFirestore.getInstance()).createRoom(
                roomName.getText().toString(),
                new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Intent intent = new Intent(CreateRoomActivity.this, ChatRoomActivity.class);
                        intent.putExtra(ChatRoomActivity.CHAT_ROOM_ID, documentReference.getId());
                        intent.putExtra(ChatRoomActivity.CHAT_ROOM_NAME, roomName.getText().toString());
                        startActivity(intent);
                        finish();                    }
                },
                new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(
                                CreateRoomActivity.this,
                                "Some thing went wrong",
                                Toast.LENGTH_SHORT
                        ).show();
                    }
                }
        );
    }
}