package com.jiho.chat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.jiho.chat.db.DBConnAsync;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutionException;

public class SignupActivity extends AppCompatActivity {

    Button btnRegister;
    EditText editEmail, editName, editPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        btnRegister = (Button) findViewById(R.id.btnRegister);
        editEmail = (EditText) findViewById(R.id.editEmail);
        editName = (EditText) findViewById(R.id.editName);
        editPass = (EditText) findViewById(R.id.editPass);

        btnRegister.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                int permissionCheck = ContextCompat.checkSelfPermission(getApplicationContext(),
                        Manifest.permission.INTERNET);
                if(permissionCheck == PackageManager.PERMISSION_GRANTED) {

/*------------------------------------------------------------------------------------------------------------------------*/
                }else{
                    AlertDialog.Builder dialog =
                            new AlertDialog.Builder(getApplicationContext());
                    dialog.setTitle("권한 요청")
                            .setMessage("권한이 필요합니다 계속하시겠습니까?")
                            .setPositiveButton("네",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            requestPermissions(new String[]{Manifest.permission.INTERNET},
                                                    1000);
                                        }
                                    }).create().show();
                }
/*------------------------------------------------------------------------------------------------------------------------*/
                FirebaseInstanceId.getInstance().getInstanceId()
                        .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                            @Override
                            public void onComplete(@NonNull Task<InstanceIdResult> task) {
                                if (!task.isSuccessful()) {
                                    Log.w("실패", "getInstanceId failed", task.getException());
                                    return;
                                }
                                String token = task.getResult().getToken();
                                Log.i("토큰",token);

                                DBConnAsync customTask = new DBConnAsync();
                                try{
                                    String str = customTask.execute("signup",
                                            "email=" + editEmail.getText().toString() + "&name="+ editName.getText().toString() + "&password=" + editPass.getText().toString() + "&token=" + token
                                    ).get();
                                    Toast.makeText(getApplicationContext(), str, Toast.LENGTH_SHORT).show();
                                    finish();
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                } catch (ExecutionException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
            }
        });
    }
}