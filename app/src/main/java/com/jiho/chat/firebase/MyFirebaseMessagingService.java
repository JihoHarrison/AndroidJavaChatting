package com.jiho.chat.firebase;

/* 구글 파이어베이스 원격 메세지 전달 클래스 */

import android.app.ActivityManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.jiho.chat.MainActivity;
import com.jiho.chat.MyemailSingleton;
import com.jiho.chat.R;
import com.jiho.chat.db.DBHelper;

import java.util.List;


public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "메시지";
    SQLiteDatabase sqlDB;
    DBHelper dbHelper;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        Log.d(TAG, "From: " + remoteMessage.getFrom());

        if(remoteMessage.getNotification() != null){
            Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
            //sendNotification(remoteMessage.getNotification().getTitle(), remoteMessage.getNotification().getBody()); // 원격으로 받은 메세지의 제목과 타이틀 받아옴

            String title = remoteMessage.getNotification().getTitle();
            String message = remoteMessage.getNotification().getBody();
            String toemail = MyemailSingleton.getInstance().getMyEmail();


            dbHelper = new DBHelper(getApplicationContext());
            sqlDB = dbHelper.getWritableDatabase();
            /* 클라우드로 부터 상대방이 전송한 메세지 받아오는 과정 */
            sqlDB.execSQL("INSERT INTO messages VALUES('"+title+"','"+toemail+"','"+message+"');"); // send.jsp 파일의 String json 변수에 입력된 사항들과 관련있는 부분
            dbHelper.close();
            sqlDB.close();

            ActivityManager activityManager = (ActivityManager)getSystemService(Context.ACTIVITY_SERVICE); // 현재 실행중인 여러가지 액티비티들을 다룬다

            List<ActivityManager.RunningTaskInfo> taskInfoList = activityManager.getRunningTasks(9999); // 보통 액티비티는 1만개가 넘지 않기 때문에 9999로 지정
            if(taskInfoList.get(0).topActivity.getClassName().endsWith("MessageActivity")){ // 현재 최상단 액티비티가 MessageActivity 일 때
                Intent sendIntent = new Intent("receivedmessage"); // 인텐트를 이용하여  receivedmessage 액션 적용
                sendIntent.putExtra("title", title);
                sendIntent.putExtra("message", message);
                sendBroadcast(sendIntent); // Broadcast 신호를 이용
            }else{
                sendNotification(remoteMessage.getNotification().getTitle(), remoteMessage.getNotification().getBody());
            }
        }
    }

    private void sendNotification(String title, String message){ // 푸시알림 띄워주는 메서드
        Intent intent = new Intent(this, MainActivity.class);// 인텐트는 메인 액티비티로 넘어가게 해 줌
        intent.putExtra("name", "test"); // name이라는 태그의 test 문자열을 인텐트로 넘겨준다
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,0, intent, PendingIntent.FLAG_CANCEL_CURRENT); // 팬딩 인텐트

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION); // 소리 받아옴

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        String name = "chname"; // 채널의 이름
        String channel_id = "chid"; // 채널의 ID

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){ // 안드로이드 버전 체크 후 oreo 버전 이상일 때 알림 채널 생성해주는 부분
            NotificationChannel mchannel = new NotificationChannel(channel_id, name, NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(mchannel);
        }

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, channel_id).setLargeIcon(BitmapFactory.decodeResource(getResources(),android.R.drawable.ic_dialog_info)
        ).setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle(title)
                .setContentText(message)
                .setAutoCancel(true) // 터치 시 알림 사라짐
                .setSound(defaultSoundUri) // 소리 가져옴
                .setContentIntent(pendingIntent); // pendingIntent 가져옴

        notificationManager.notify(0, notificationBuilder.build());
    }
}