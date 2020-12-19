package com.stark.dpstatus.Util;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

import com.google.firebase.messaging.FirebaseMessaging;

public class AppConfig extends Application {

    public static final String TEXT_STATUS_CHANNEL_ID = "TEXT_STATUS_CHANNEL_ID";
    public static final String TEXT_STATUS_CHANNEL_NAME = "STATUS";

    public static final String IMAGE_STATUS_CHANNEL_ID = "IMAGE_STATUS_CHANNEL_ID";
    public static final String IMAGE_STATUS_CHANNEL_NAME = "IMAGE STATUS";

    @Override
    public void onCreate() {
        super.onCreate();
        createNotificationChannels();
        createFirebaseNotificationTopic();
    }

    private void createNotificationChannels() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel textStatusChannel = new NotificationChannel(TEXT_STATUS_CHANNEL_ID,
                    TEXT_STATUS_CHANNEL_NAME,
                    NotificationManager.IMPORTANCE_HIGH);
            textStatusChannel.setDescription("This Category for Text Status Notification.");
            textStatusChannel.enableLights(true);

            NotificationChannel imageStatusChannel = new NotificationChannel(IMAGE_STATUS_CHANNEL_ID,
                    IMAGE_STATUS_CHANNEL_NAME,
                    NotificationManager.IMPORTANCE_HIGH);
            imageStatusChannel.setDescription("This Category for Image Status Notification.");

            NotificationManager notificationManager = getSystemService(NotificationManager.class);

            notificationManager.createNotificationChannel(textStatusChannel);
            notificationManager.createNotificationChannel(imageStatusChannel);
        }

    }

    private void createFirebaseNotificationTopic() {
        FirebaseMessaging.getInstance().subscribeToTopic("all");
    }
}
