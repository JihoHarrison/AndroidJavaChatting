package com.jiho.chat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.iid.FirebaseInstanceId;
import com.jiho.chat.db.DBConnAsync;
import com.jiho.chat.db.DBHelper;

public class MainActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    FragmentManager fm;
    FragmentTransaction fragmentTransaction;
    SQLiteDatabase sqlDB;
    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String token = FirebaseInstanceId.getInstance().getToken();

        if (token != null) {
            Log.d("test", "token = " + token);
        }

        bottomNavigationView = findViewById(R.id.bottom_navigation_view);

        dbHelper = new DBHelper(getApplicationContext());

        Log.e("fd",""+checkTable(sqlDB, "friend_list"));
        Log.e("md",""+checkTable(sqlDB, "messages"));
        Log.e("cd",""+checkTable(sqlDB, "chatrooms"));

        if(checkTable(sqlDB, "friend_list")
                || checkTable(sqlDB, "messages")
                || checkTable(sqlDB, "chatrooms")) {
            sqlDB = dbHelper.getWritableDatabase();
            dbHelper.onUpgrade(sqlDB, 1, 2);
            sqlDB.close();
        }

        fm = getSupportFragmentManager();
        fragmentTransaction = fm.beginTransaction();
        Fragment fr = new FriendFragment();
        fragmentTransaction.add(R.id.frame_layout, fr);
        fragmentTransaction.commit();

        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {

                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        fm = getSupportFragmentManager();
                        fragmentTransaction = fm.beginTransaction();
                        Fragment fr = null;
                        switch(item.getItemId()) {
                            case R.id.tabFriend:
                                fr = new FriendFragment();
                                break;
                            case R.id.tabChat:
                                fr = new ChatFragment();
                                break;
                            case R.id.tabPhoto:
                                break;
                            case R.id.tabSetting:
                                fr = new SettingFragment();
                                break;
                        }

                        if(fr!=null) {
                            fragmentTransaction.replace(R.id.frame_layout, fr);
                            fragmentTransaction.commit();
                        }
                        return true;
                    }

                });
    }

    private boolean checkTable(SQLiteDatabase db, String tableName){
        try{
            db.rawQuery("SELECT * FROM "+tableName+" limit 1;" , null);
        }catch(SQLiteException e){
            e.printStackTrace();
            return false;
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

}