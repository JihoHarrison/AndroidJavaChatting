package com.jiho.chat;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jiho.chat.model.UserModel;

import java.util.ArrayList;

public class FriendFragmentAdapter extends RecyclerView.Adapter<FriendFragmentAdapter.FriendViewHolder> {

    private ArrayList<UserModel> userModels;
    Context context;

    public FriendFragmentAdapter(ArrayList<UserModel> userModels, Context context){ // 어댑터가 생성될 때 유저들의 객체를 받아 줄 매개변수를 가진 어댑터의 생성자
        this.userModels = userModels;
        this.context = context;
    }

    @NonNull
    @Override
    public FriendViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_friend, parent, false);
        FriendViewHolder friendViewHolder = new FriendViewHolder(view);

        return friendViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull FriendViewHolder holder, final int position) {

        holder.txtEmail.setText(userModels.get(position).getUserEmail());
        holder.txtName.setText(userModels.get(position).getUserName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, MessageActivity.class);
                intent.putExtra("toemail", userModels.get(position).getUserEmail());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        Log.d("size", userModels.size()+""); // 아이템들의 갯수를 로그로 출력하여 확인
        return (null != userModels ? userModels.size() : 0); // 생성된 아이템의 갯수 반환
    }

    public class FriendViewHolder extends RecyclerView.ViewHolder{ // 뷰 홀더 클래스 정의. onBindViewHolder 메서드의 매개변수로 전달됨

        TextView txtEmail, txtName;

        public FriendViewHolder(@NonNull View itemView) {
            super(itemView);
            txtEmail = (TextView) itemView.findViewById(R.id.txtEmail);
            txtName = (TextView) itemView.findViewById(R.id.txtName);
        }
    }

}
