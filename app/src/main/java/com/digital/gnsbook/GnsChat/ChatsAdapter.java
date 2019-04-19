package com.digital.gnsbook.GnsChat;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.digital.gnsbook.Extra.Ago;
import com.digital.gnsbook.Global;
import com.httpgnsbook.gnsbook.R;

import java.util.List;

public class ChatsAdapter extends RecyclerView.Adapter<ChatsAdapter.ChatViewHolder> {
    private static final int SENT = 0;
    private static final int RECEIVED = 1;

    private String userId;
    private List<Chat> chats;
    Ago ago;
    Context context;

    public ChatsAdapter(List<Chat> chats, String userId, Context context) {
        this.chats = chats;
        this.userId = userId;
        this.context = context;
        ago= new Ago().locale(this.context);
    }

    @Override
    public ChatViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if (viewType == SENT) {
            view = LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.item_chat_sent,
                    parent,
                    false
            );
        } else {
            view = LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.item_chat_received,
                    parent,
                    false);
        }
        return new ChatViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ChatViewHolder holder, int position) {
        holder.bind(chats.get(position));
    }

    @Override
    public int getItemViewType(int position) {
        if (chats.get(position).senderId.contentEquals(Global.customerid)) {
            return SENT;
        } else {
            return RECEIVED;
        }
    }

    @Override
    public int getItemCount() {
        return chats.size();
    }

    class ChatViewHolder extends RecyclerView.ViewHolder {
        TextView message,date;

        public ChatViewHolder(View itemView) {
            super(itemView);
            message = itemView.findViewById(R.id.chat_message);
            date = itemView.findViewById(R.id.date);

        }

        public void bind(Chat chat) {
            message.setText(chat.message);
            date.setText(ago.getTimeAgo(Global.getNewDate(chat.sent)));
        }
    }
}
