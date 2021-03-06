package com.example.repairit;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Color;
import android.os.Build;

import androidx.annotation.RequiresApi;

public class NotificationHelper extends ContextWrapper {

    private static final String MY_CHANNEL_ID = "repait.it,notification";
    private static final String MY_CHANNEL_NAME = "repair.it.channel";
    private NotificationManager manager;

    @RequiresApi(api = Build.VERSION_CODES.O)
    public NotificationHelper(Context base) {
        super(base);
        createChannels();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void createChannels() {

        NotificationChannel myChannel = new NotificationChannel(MY_CHANNEL_ID, MY_CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
        myChannel.enableLights(true);
        myChannel.enableVibration(true);
        myChannel.setLightColor(Color.RED);
        myChannel.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);

        getManager().createNotificationChannel(myChannel);

    }

    public NotificationManager getManager() {
        if(manager == null)
            manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        return manager;
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    public Notification.Builder getNotifcation(String title, String body) {

        return new Notification.Builder(getApplicationContext(), MY_CHANNEL_ID)
                .setContentText(body)
                .setContentTitle(title)
                .setSmallIcon(R.drawable.ic_sms)
                .setAutoCancel(true);

    }
}
