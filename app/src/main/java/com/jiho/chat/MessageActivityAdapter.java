package com.jiho.chat;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jiho.chat.model.MessageModel;

import java.util.ArrayList;

public class MessageActivityAdapter extends RecyclerView.Adapter<MessageActivityAdapter.MessageViewHolder> {

    private ArrayList<MessageModel> messageModels;
    private String myemail;

    public MessageActivityAdapter(ArrayList<MessageModel> messageModels, String myemail) {
        this.messageModels = messageModels;
        this.myemail = myemail;
    }

    @NonNull
    @Override
    public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) { // 아이템 하나를 화면에 띄워주는 역할

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_message, parent, false);

        MessageViewHolder viewHolder = new MessageViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MessageViewHolder holder, int position) { // 왼쪽 메세지인지 오른쪽 메세지인지
        Log.e("checkmessage", messageModels.get(position).getUserName());
        Log.e("checkmessage", myemail.split("@")[0]); // 문자열을 @로 잘라서 아이디와 이메일 구분


        if(messageModels.get(position).getUserName().equals(myemail.split("@")[0])){ // 내가 보내는 메세지
            holder.message.setText(messageModels.get(position).getMessage());
            holder.fromName.setText(messageModels.get(position).getUserName());
            holder.message.setBackgroundResource(R.drawable.rightbubble);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                    );
            params.gravity = Gravity.RIGHT;
            holder.messageItem_linearlayout_destination.setLayoutParams(params);
        } else{
            holder.message.setText(messageModels.get(position).getMessage());
            holder.fromName.setText(messageModels.get(position).getUserName());
            holder.message.setBackgroundResource(R.drawable.leftbubble);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            params.gravity = Gravity.LEFT;
            holder.messageItem_linearlayout_destination.setLayoutParams(params);
        }
    }

    @Override
    public int getItemCount() {
        return (null != messageModels ? messageModels.size() : 0);
    }

    public class MessageViewHolder extends RecyclerView.ViewHolder{

        TextView fromName, message; // 누가 보낸 데이터 set인지
        LinearLayout messageItem_linearlayout_destination;

        public MessageViewHolder(@NonNull View itemView) {
            super(itemView);
            this.fromName = (TextView) itemView.findViewById(R.id.messageItem_textview_name);
            this.message = (TextView) itemView.findViewById(R.id.messageItem_textview_message);
            this.messageItem_linearlayout_destination = (LinearLayout) itemView.findViewById(R.id.messageItem_linearlayout_destination);
        }
    }
}
