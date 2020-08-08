package com.jiho.chat;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jiho.chat.model.UserModel;

import java.util.ArrayList;

public class FriendFragment extends Fragment {

    private FriendFragmentAdapter friendFragmentAdapter;
    private ArrayList<UserModel> userModelList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_friend, container, false);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.friendFragment_recyclerview);

        recyclerView.setLayoutManager(new LinearLayoutManager(inflater.getContext()));

        userModelList = new ArrayList<>();
        UserModel user1 = new UserModel();
        user1.setUserName("a");
        user1.setUserEmail("a");
        userModelList.add(user1);

        UserModel user2 = new UserModel();
        user2.setUserName("b");
        user2.setUserEmail("b");
        userModelList.add(user2);

        friendFragmentAdapter = new FriendFragmentAdapter(userModelList);
        recyclerView.setAdapter(friendFragmentAdapter);

        return view;
    }
}