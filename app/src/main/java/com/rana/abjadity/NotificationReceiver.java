package com.rana.abjadity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class NotificationReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.


        String msg = intent.getStringExtra("msg");
        NotificationHelper notificationHelper = new NotificationHelper(context,msg);

        notificationHelper.createNotification();

    }
}