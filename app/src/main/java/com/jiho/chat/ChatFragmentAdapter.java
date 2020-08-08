package com.jiho.chat;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jiho.chat.model.ChatRoomModel;

import java.util.ArrayList;

public class ChatFragmentAdapter extends RecyclerView.Adapter<ChatFragmentAdapter.ChatViewHolder> {

    private ArrayList<ChatRoomModel> chatRoomModels;

    public ChatFragmentAdapter(ArrayList<ChatRoomModel> chatRoomModels){
        this.chatRoomModels = chatRoomModels;
    }

    @NonNull
    @Override
    public ChatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chat, parent, false);
        ChatViewHolder chatViewHolder = new ChatViewHolder(view);
        return chatViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ChatViewHolder holder, int position) {
        holder.txtChat.setText(chatRoomModels.get(position).getToUserEmail());
    }

    @Override
    public int getItemCount() {
        return (null != chatRoomModels ? chatRoomModels.size() : 0); // 생성된 아이템의 갯수 반환
    }

    public class ChatViewHolder extends RecyclerView.ViewHolder{

        TextView txtChat;


        public ChatViewHolder(@NonNull View itemView) {
            super(itemView);

            txtChat = (TextView) itemView.findViewById(R.id.txtChat);
        }
    }
}
