package com.example.ourfarming.activity;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.ourfarming.R;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class LoginMessaging extends FirebaseMessagingService {
    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        showloginmessage(remoteMessage.getNotification().getTitle(),remoteMessage.getNotification().getBody());
    }
    public void showloginmessage(String title,String message){
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "Our Farming")
                .setSmallIcon(R.drawable.login_singnup)
                .setContentTitle(title)
                .setContentText(message);
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);

// notificationId is a unique int for each NotificationFragment that you must define
        notificationManager.notify(101, builder.build());
    }
}
