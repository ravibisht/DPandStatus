package com.stark.dpstatus.Util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class DpStatusNotificationService extends FirebaseMessagingService {

    private static final String TAG = "DpStatusMessagingService";

    @Override
    public void onNewToken(@NonNull String s) {
        super.onNewToken(s);
        Log.e(TAG, "onNewToken: " + s);

    }

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {

        NotificationHelper notificationHelper = new NotificationHelper(getApplicationContext());

        String title = remoteMessage.getNotification() == null ? remoteMessage.getData().get("title") : remoteMessage.getNotification().getTitle();
        String message = remoteMessage.getNotification() == null ? remoteMessage.getData().get("body") : remoteMessage.getNotification().getBody();


        if (remoteMessage.getData().get("image") != null || remoteMessage.getData().get("image_url") != null) {
            notificationHelper.showImageNotification(title, message,
                    getBitmapFromImageUrl(remoteMessage.getData().get("image")), remoteMessage.getData().get("image"));
        } else {
            notificationHelper.showTextStatusNotification(title, message);
        }

    }

    public Bitmap getBitmapFromImageUrl(String imageUrl) {

        try {
            URL url = new URL(imageUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream inputStream = connection.getInputStream();
            return BitmapFactory.decodeStream(inputStream);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
