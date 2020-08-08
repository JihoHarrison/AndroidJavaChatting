package com.jiho.chat.firebase;

/* 구글 파이어베이스 원격 메세지 전달 클래스 */

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.jiho.chat.MainActivity;
import com.jiho.chat.R;


public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "메시지";
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        Log.d(TAG, "From: " + remoteMessage.getFrom());

        if(remoteMessage.getNotification() != null){
            Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
            sendNotification(remoteMessage.getNotification().getTitle(), remoteMessage.getNotification().getBody()); // 원격으로 받은 메세지의 제목과 타이틀 받아옴
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