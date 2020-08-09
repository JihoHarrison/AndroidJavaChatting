package com.jiho.chat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.jiho.chat.db.DBConnAsync;
import com.jiho.chat.db.DBHelper;
import com.jiho.chat.model.MessageModel;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class MessageActivity extends AppCompatActivity {

    ArrayList<MessageModel> messageModels;
    MessageActivityAdapter messageActivityAdapter;
    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    EditText messageET;
    Button send;
    String myEmail;
    String toEmail;
    SQLiteDatabase sqlDB;
    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        myEmail = MyemailSingleton.getInstance().getMyEmail();
        Intent intent = getIntent();
        toEmail = intent.getExtras().getString("toemail");

        dbHelper = new DBHelper(getApplicationContext());
        messageModels = new ArrayList<>();
//        MessageModel data1 = new MessageModel("test1", "messagetest1");
//        messageModels.add(data1);
//
//        MessageModel data2 = new MessageModel("jhshin925", "massagetest2");
//        messageModels.add(data2);

        getMessageList();

        messageET = (EditText) findViewById(R.id.messageActivity_editText);
        recyclerView = (RecyclerView) findViewById(R.id.messageActivity_recyclerView);
        linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        messageActivityAdapter = new MessageActivityAdapter(messageModels, myEmail);
        recyclerView.setAdapter(messageActivityAdapter);

        send = (Button) findViewById(R.id.messageActivity_button);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendMessage(myEmail, toEmail, messageET.getText().toString());
            }
        });
    }

    public void sendMessage(String myEmail, String toEmail, String message){
        sqlDB = dbHelper.getWritableDatabase();
        sqlDB.execSQL("INSERT INTO messages VALUES('"+myEmail+"','"+toEmail+"','"+message+"');");
        sqlDB.close();

        messageModels.add(new MessageModel(myEmail.split("@")[0], message)); // 내가 보내는 메세지
        messageActivityAdapter.notifyDataSetChanged();
        new Handler().postDelayed(new Runnable() { // 채팅이 많아지면 자동으로 화면 스크롤 해주는 메서드
            @Override
            public void run() {
                recyclerView.scrollToPosition(messageActivityAdapter.getItemCount()-1);
            }
        }, 200);
        DBConnAsync dbConnAsync = new DBConnAsync(); // 이클립스 통해서 firebase로 전달해주는 객체
        String result = null;
        try{
            result = dbConnAsync.execute("send","fromemail="+myEmail+"&toemail="+toEmail+"&message"+message+"").get();
        }catch(ExecutionException e){
            e.printStackTrace();
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }

    public void getMessageList(){
        sqlDB = dbHelper.getReadableDatabase();
        Cursor cursor;
        /* 나 -> 친구 || 친구 -> 나의 발신처로 된 메세지 정보들을 외부 DB에서 가져오는 과정 */
        cursor = sqlDB.rawQuery("SELECT * FROM messages WHERE fromUser='"+myEmail+"' AND toUser='"+toEmail+"' OR fromUser='"+toEmail+"' AND toUser='"+myEmail+"';" ,null);

        while(cursor.moveToNext()){
            MessageModel messageModel = new MessageModel(cursor.getString(0).split("@")[0], cursor.getString(2));
            messageModels.add(messageModel);
        }
        cursor.close();
        sqlDB.close();
    }
}