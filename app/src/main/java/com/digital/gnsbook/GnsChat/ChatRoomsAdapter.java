package com.digital.gnsbook.GnsChat;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.httpgnsbook.gnsbook.R;

import java.util.List;

public class ChatRoomsAdapter extends RecyclerView.Adapter<ChatRoomsAdapter.ChatRoomViewHolder> {

    private List<ChatRoom> chatRooms;
    private Context context;

    public ChatRoomsAdapter(List<ChatRoom> chatRooms, CMainActivity mainActivity) {
        this.chatRooms = chatRooms;
        this.context   = mainActivity;
    }

    @Override
    public ChatRoomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.item_chat_room,
                parent,
                false
        );
        return new ChatRoomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ChatRoomViewHolder holder, final int position) {
        holder.bind(chatRooms.get(position));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ChatRoomActivity.class);
                intent.putExtra(ChatRoomActivity.CHAT_ROOM_ID, chatRooms.get(position).getId());
                intent.putExtra(ChatRoomActivity.CHAT_ROOM_NAME,chatRooms.get(position).getName());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return chatRooms.size();
    }

    class ChatRoomViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        ChatRoom chatRoom;

        public ChatRoomViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.item_chat_room_name);
        }

        public void bind(ChatRoom chatRoom) {
            this.chatRoom = chatRoom;
            name.setText(chatRoom.getName());
        }
    }
}
