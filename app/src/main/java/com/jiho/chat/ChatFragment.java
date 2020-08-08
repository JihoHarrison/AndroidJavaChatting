package com.jiho.chat;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jiho.chat.model.ChatRoomModel;

import java.util.ArrayList;

public class ChatFragment extends Fragment {

    private ChatFragmentAdapter chatFragmentAdapter;
    private ArrayList<ChatRoomModel> chatRoomModels;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_chat, container, false);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.chatFragment_recyclerview);

        recyclerView.setLayoutManager(new LinearLayoutManager(inflater.getContext()));

        chatRoomModels = new ArrayList<>();
        ChatRoomModel user1 = new ChatRoomModel("a");
        chatRoomModels.add(user1);
        ChatRoomModel user2 = new ChatRoomModel("b");
        chatRoomModels.add(user2);


        chatFragmentAdapter = new ChatFragmentAdapter(chatRoomModels);
        recyclerView.setAdapter(chatFragmentAdapter);
        return view;
    }
}