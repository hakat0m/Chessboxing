/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.app.Service
 *  android.content.BroadcastReceiver
 *  android.content.ComponentName
 *  android.content.Context
 *  android.content.Intent
 *  android.content.SharedPreferences
 *  android.os.Bundle
 *  android.os.IBinder
 *  java.lang.Class
 *  java.lang.Object
 *  java.lang.String
 */
package com.cygery.repetitouch.pro;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.IBinder;
import com.cygery.repetitouch.pro.EditConditionActivity;
import com.cygery.utilities.a;

public class QueryBackgroundService
extends Service {
    private static final String a = QueryBackgroundService.class.getName();
    private static boolean b;
    private static boolean c;
    private static boolean d;
    private static boolean e;

    private void e() {
        b = false;
        c = false;
        d = false;
        e = false;
    }

    public IBinder onBind(Intent intent) {
        return null;
    }

    public void onCreate() {
        super.onCreate();
        a.a((Context)this);
        this.e();
    }

    public void onDestroy() {
        super.onDestroy();
    }

    /*
     * Enabled aggressive block sorting
     */
    public int onStartCommand(Intent intent, int n2, int n3) {
        if (intent != null && "querybackgroundservice".equals((Object)intent.getAction())) {
            String string = intent.getStringExtra("message");
            if ("recordstarted".equals((Object)string)) {
                b = true;
            } else if ("recordstopped".equals((Object)string)) {
                b = false;
            } else if ("replaystarted".equals((Object)string)) {
                c = true;
            } else if ("replaystopped".equals((Object)string)) {
                c = false;
            } else if ("start".equals((Object)string)) {
                d = true;
            } else if ("stop".equals((Object)string)) {
                super.e();
            } else if ("show".equals((Object)string)) {
                e = true;
            } else if ("hide".equals((Object)string)) {
                e = false;
            }
            this.sendBroadcast(new Intent("com.twofortyfouram.locale.intent.action.REQUEST_QUERY").putExtra("com.twofortyfouram.locale.intent.extra.ACTIVITY", EditConditionActivity.class.getName()));
        }
        return 1;
    }

    public static class QueryReceiver
    extends BroadcastReceiver {
        private Context a;

        boolean a() {
            return this.a.getSharedPreferences("repetitouch_prefs", 0).getBoolean("enableLocaleSupport", false);
        }

        /*
         * Enabled aggressive block sorting
         */
        public void onReceive(Context context, Intent intent) {
            if ("com.twofortyfouram.locale.intent.action.QUERY_CONDITION".equals((Object)intent.getAction())) {
                this.a = context;
                int n2 = 18;
                if (this.a()) {
                    Bundle bundle = intent.getBundleExtra("com.twofortyfouram.locale.intent.extra.BUNDLE");
                    if (bundle != null) {
                        String string = bundle.getString("condition");
                        if (string == null) {
                            string = "";
                        }
                        n2 = string.equals((Object)"is recording") ? (b ? 16 : 17) : (string.equals((Object)"is replaying") ? (c ? 16 : 17) : (string.equals((Object)"is running") ? (d ? 16 : 17) : (string.equals((Object)"is panel visible") ? (e ? 16 : 17) : 17)));
                    }
                    context.startService(new Intent(context, (Class)QueryBackgroundService.class));
                }
                this.setResultCode(n2);
            }
        }
    }

}

