package com.gumtree.android.common.analytics;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.adjust.sdk.AdjustReferrerReceiver;
import com.google.android.gms.analytics.CampaignTrackingReceiver;

public class InstallReceiver extends BroadcastReceiver {
    public void onReceive(Context context, Intent intent) {
        new AdjustReferrerReceiver().onReceive(context, intent);
        new CampaignTrackingReceiver().onReceive(context, intent);
    }
}
