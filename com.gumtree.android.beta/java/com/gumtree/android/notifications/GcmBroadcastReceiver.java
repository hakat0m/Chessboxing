package com.gumtree.android.notifications;

import android.app.Application;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.WakefulBroadcastReceiver;
import com.gumtree.android.common.analytics.Track;

public class GcmBroadcastReceiver extends WakefulBroadcastReceiver {
    public void onReceive(Context context, Intent intent) {
        Track.prepare((Application) context.getApplicationContext());
        Track.viewNotificationReceived();
        startWakefulService(context, intent.setComponent(new ComponentName(context.getPackageName(), GcmIntentService.class.getName())));
        setResultCode(-1);
    }
}
