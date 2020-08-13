package com.jiho.chat.db;

import android.os.AsyncTask;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class DBConnAsync extends AsyncTask<String, Void, String> { // DB 연결 클래스

    String recieveMsg, sendMsg;

    @Override
    protected String doInBackground(String... strings) {
        try {
            URL url = new URL("http://192.168.35.169:8888/test/" + strings[0] + ".jsp");
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);
            //sendMsg = "email=test&name=hello&password=1234";

            OutputStreamWriter osw = new OutputStreamWriter(conn.getOutputStream());
            //sendMsg = "str1=test&str2=qwer";
            //sendMsg = "email=" + strings[0] + "&name="+ strings[1] + "&password=" + strings[2];
            sendMsg = strings[1];
            osw.write(sendMsg);
            osw.flush();

            if(conn.getResponseCode() == conn.HTTP_OK){
                InputStreamReader tmp = new InputStreamReader(conn.getInputStream());
                BufferedReader reader = new BufferedReader(tmp);
                StringBuffer buffer = new StringBuffer();
                String str;
                while((str = reader.readLine()) != null){
                    buffer.append(str);
                }
                recieveMsg = buffer.toString();
            }
        } catch (MalformedURLException e){
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return recieveMsg;
    }
}