package com.jiho.chat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.jiho.chat.db.DBConnAsync;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutionException;

public class LoginActivity extends AppCompatActivity {

    EditText editEmail, editPass;
    Button btnLogin, btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editEmail = (EditText) findViewById(R.id.editEmail);
        editPass = (EditText) findViewById(R.id.editPass);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnRegister = (Button) findViewById(R.id.btnRegister);

        btnRegister.setOnClickListener(new View.OnClickListener() { // 회원가입 버튼 클릭 시 회원가입 액티비티로 이동
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), SignupActivity.class));
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DBConnAsync customTask = new DBConnAsync();
                try{
                    String myemail = editEmail.getText().toString();
                    String str = customTask.execute("login",
                            "email=" + editEmail.getText().toString() + "&password=" + editPass.getText().toString()
                    ).get();

                    if(str.equals("로그인 성공")){
                        MyemailSingleton.getInstance().setMyEmail(myemail); // 로그인에 성공했을 시 싱글턴 클래스의 setEmail메서드에 로그인 시 입력받은 이메일을 저장
                        Toast.makeText(getApplicationContext(), "로그인 성공", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                    } else{
                        Toast.makeText(getApplicationContext(), "로그인 실패", Toast.LENGTH_SHORT).show();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }
        });
    }

//    public class CustomTask extends AsyncTask<String, Void, String> {
//
//        String recieveMsg, sendMsg;
//
//        @Override
//        protected String doInBackground(String... strings) {
//            try {
//                URL url = new URL("http://192.168.0.33:8888/test/login.jsp");
//                HttpURLConnection conn = (HttpURLConnection)url.openConnection();
//                conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
//                conn.setRequestMethod("POST");
//                conn.setDoInput(true);
//                conn.setDoOutput(true);
//                //sendMsg = "email=test&name=hello&password=1234";
//
//                OutputStreamWriter osw = new OutputStreamWriter(conn.getOutputStream());
//                //sendMsg = "str1=test&str2=qwer";
//                sendMsg = "email=" + strings[0] + "&password=" + strings[1];
//                osw.write(sendMsg);
//                osw.flush();
//
//                if(conn.getResponseCode() == conn.HTTP_OK){
//                    InputStreamReader tmp = new InputStreamReader(conn.getInputStream());
//                    BufferedReader reader = new BufferedReader(tmp);
//                    StringBuffer buffer = new StringBuffer();
//                    String str;
//                    while((str = reader.readLine()) != null){
//                        buffer.append(str);
//                    }
//                    recieveMsg = buffer.toString();
//                }
//            } catch (MalformedURLException e){
//                e.printStackTrace();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            return recieveMsg;
//        }
//    }
}