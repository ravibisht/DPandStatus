package com.stark.dpstatus.Util;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.RingtoneManager;
import android.net.Uri;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.stark.dpstatus.R;
import com.stark.dpstatus.activity.StatusActivity;

public class NotificationHelper {

    public static final String TEXT_NOTIFICATION_ORIGIN = "TEXT_NOTIFICATION_ORIGIN";
    public static final String IMAGE_NOTIFICATION_ORIGIN = "IMAGE_NOTIFICATION_ORIGIN";
    public static final String NOTIFICATION_TITLE = "NOTIFICATION_TITLE";
    public static final String NOTIFICATION_MESSAGE = "NOTIFICATION_MESSAGE";
    public static final String NOTIFICATION_IMAGE_URL = "NOTIFICATION_IMAGE_URL";
    private static final String TAG = "NotificationHelper";

    private Context mContext;
    private NotificationManagerCompat notificationManagerCompat;
    private Uri defaultSoundUri;


    public NotificationHelper(Context mContext) {
        this.mContext = mContext;
        this.notificationManagerCompat = NotificationManagerCompat.from(mContext);
    }

    public void showTextStatusNotification(String title, String message) {
        Log.e(TAG, "showTextStatusNotification: Me Calling form ");
        defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        Intent textNotificationIntent = new Intent(mContext, StatusActivity.class);
        textNotificationIntent.putExtra(StatusActivity.INTENT_ORIGIN, TEXT_NOTIFICATION_ORIGIN);
        textNotificationIntent.putExtra(NOTIFICATION_TITLE, title);
        textNotificationIntent.putExtra(NOTIFICATION_MESSAGE, message);
        textNotificationIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent textNotificationPendingIntent = PendingIntent.getActivity(mContext,
                0, textNotificationIntent, PendingIntent.FLAG_ONE_SHOT);

        NotificationCompat.BigTextStyle bigTextStyle = new NotificationCompat.BigTextStyle()
                .bigText(message)
                .setBigContentTitle(title);

        Notification textStatusNotification = new NotificationCompat.Builder(
                mContext, AppConfig.TEXT_STATUS_CHANNEL_ID)
                .setContentTitle(title)
                .setContentText(message)
                .setStyle(bigTextStyle)
                .setSmallIcon(R.drawable.notification_icon)
                .setColor(mContext.getResources().getColor(R.color.main_app_color))
                .setAutoCancel(true)
                .setPriority(NotificationManagerCompat.IMPORTANCE_HIGH)
                .setSound(defaultSoundUri)
                .setContentIntent(textNotificationPendingIntent)
                .build();
        notificationManagerCompat.notify(3, textStatusNotification);
    }


    public void showImageNotification(String title, String message, Bitmap notificationImage, String imageUrl) {

        defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);

        Intent imageNotificationIntent = new Intent(mContext, StatusActivity.class);
        imageNotificationIntent.putExtra(StatusActivity.INTENT_ORIGIN, IMAGE_NOTIFICATION_ORIGIN);
        imageNotificationIntent.putExtra(NOTIFICATION_IMAGE_URL, imageUrl);
        imageNotificationIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent imageNotificationPendingIntent = PendingIntent.getActivity(mContext,
                0, imageNotificationIntent, PendingIntent.FLAG_ONE_SHOT);

        NotificationCompat.BigPictureStyle bigPictureStyle = new NotificationCompat.BigPictureStyle()
                .bigPicture(notificationImage)
                .bigLargeIcon(null);

        Notification imageNotification = new NotificationCompat.Builder(
                mContext, AppConfig.IMAGE_STATUS_CHANNEL_ID)
                .setContentTitle(title)
                .setContentText(message)
                .setSmallIcon(R.drawable.notification_icon)
                .setColor(mContext.getResources().getColor(R.color.main_app_color))
                .setStyle(bigPictureStyle)
                .setLargeIcon(notificationImage)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(imageNotificationPendingIntent)
                .build();

        notificationManagerCompat.notify(33, imageNotification);

    }


}
