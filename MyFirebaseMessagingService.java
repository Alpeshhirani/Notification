package com.oozeetech.padkranti.fcm;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.oozeetech.padkranti.R;
import com.oozeetech.padkranti.activity.Common.SplashScreenActivity;
import com.oozeetech.padkranti.utils.Preferences;

import static android.content.ContentValues.TAG;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    Preferences pref;
    String Message = "", Type = "", Title = "";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        pref = new Preferences(getApplicationContext());

        Log.e(TAG, "Data Payload: " + remoteMessage.getData().toString());

        Message = remoteMessage.getData().get("Message");
        Type = remoteMessage.getData().get("Type");
        Title = remoteMessage.getData().get("Title");

        sendNotification();

    }

    private void sendNotification() {

        Bitmap icon = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
        Intent intent;
        intent = new Intent(this, SplashScreenActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);

        String CHANNEL_ID = getPackageName();
        CharSequence name = getString(R.string.app_name);

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this, CHANNEL_ID)
                        .setAutoCancel(true)
                        .setContentTitle(Title)
                        .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000})
                        .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                        //.setSound(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.win))
                        .setContentInfo("")
                        .setLargeIcon(icon)
                        .setChannelId(CHANNEL_ID)
                        .setColor(Color.TRANSPARENT)
                        .setContentIntent(pendingIntent)
                        .setContentText(Message)
                        .setLights(Color.TRANSPARENT, 1000, 300)
                        .setDefaults(Notification.DEFAULT_VIBRATE)
                        .setSmallIcon(R.drawable.ic_mail);

        NotificationCompat.BigTextStyle bigText = new NotificationCompat.BigTextStyle();
        bigText.bigText(Title);
        bigText.setBigContentTitle(Message);
        bigText.setSummaryText("By : " + getString(R.string.app_name));
        mBuilder.setStyle(bigText);


        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify((int) System.currentTimeMillis(), mBuilder.build());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_HIGH;

            NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID, name, importance);
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.enableVibration(true);
            notificationChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
            notificationManager.createNotificationChannel(notificationChannel);
        }
    }


}