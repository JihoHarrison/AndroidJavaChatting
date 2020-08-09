package com.jiho.chat;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jiho.chat.db.DBHelper;
import com.jiho.chat.model.UserModel;

import java.util.ArrayList;

public class FriendFragment extends Fragment {

    private FriendFragmentAdapter friendFragmentAdapter;
    private ArrayList<UserModel> userModelList;
    SQLiteDatabase sqlDB;
    DBHelper dbHelper;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_friend, container, false);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.friendFragment_recyclerview);

        recyclerView.setLayoutManager(new LinearLayoutManager(inflater.getContext()));

        userModelList = new ArrayList<>();
        dbHelper = new DBHelper(inflater.getContext());
        getFriend(); // DB로부터 이메일과 이름 값을 받아와서 친구목록 적용

        /* 생성자를 직접 생성하여 친구목록 입력하는 방법 */
//        UserModel user1 = new UserModel();
//        user1.setUserName("a");
//        user1.setUserEmail("a");
//        userModelList.add(user1);
//
//        UserModel user2 = new UserModel();
//        user2.setUserName("b");
//        user2.setUserEmail("b");
//        userModelList.add(user2);

        friendFragmentAdapter = new FriendFragmentAdapter(userModelList, getContext());
        recyclerView.setAdapter(friendFragmentAdapter);

        return view;
    }

    public void getFriend(){ // 데이터 베이스로 부터 친구 목록을 불러온다
        sqlDB = dbHelper.getReadableDatabase();
        Cursor cursor;
        cursor = sqlDB.rawQuery("SELECT * FROM friend_list;", null);

        while(cursor.moveToNext()){
            UserModel userModel = new UserModel(cursor.getString(1), cursor.getString(0)); // 0 번째 값이 이메일 1 번째 값이 이름
            userModelList.add(userModel);
        }
        cursor.close();
        sqlDB.close();
    }
}