/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.content.BroadcastReceiver
 *  android.content.ComponentName
 *  android.content.Context
 *  android.content.Intent
 *  android.content.SharedPreferences
 *  android.preference.PreferenceManager
 *  android.widget.Toast
 *  java.io.File
 *  java.lang.CharSequence
 *  java.lang.Class
 *  java.lang.Integer
 *  java.lang.Object
 *  java.lang.String
 *  java.util.ArrayList
 */
package com.cygery.repetitouch.pro;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.widget.Toast;
import com.cygery.repetitouch.b;
import com.cygery.repetitouch.d;
import com.cygery.repetitouch.pro.EventManagerServicePro;
import com.cygery.repetitouch.pro.PanelServicePro;
import com.cygery.utilities.a;
import java.io.File;
import java.util.ArrayList;

public class BootReceiver
extends BroadcastReceiver {
    private static final String c = BootReceiver.class.getName();
    protected Context a;
    protected Intent b;

    void a(String string) {
        this.a(string, 0);
    }

    void a(String string, int n2) {
        Toast.makeText((Context)this.a, (CharSequence)string, (int)n2).show();
    }

    /*
     * Enabled aggressive block sorting
     */
    public void onReceive(Context context, Intent intent) {
        this.a = context;
        this.b = intent;
        a.a(this.a);
        SharedPreferences sharedPreferences = this.a.getSharedPreferences("repetitouch_prefs", 0);
        if (!this.b.getAction().equals((Object)"android.intent.action.BOOT_COMPLETED") || !sharedPreferences.getBoolean("startOnBoot", false)) return;
        {
            String string = (Object)this.a.getFilesDir() + File.separator + "eventserver";
            File file = new File(string);
            if (!file.exists() || d.b(this.a) || !file.canExecute()) {
                this.a(this.a.getString(2131165374));
                return;
            } else {
                if (b.C()) {
                    b.b(string, this.a.getString(2131165456));
                }
                PreferenceManager.setDefaultValues((Context)this.a, (String)"repetitouch_prefs", (int)0, (int)2131034112, (boolean)false);
                if (sharedPreferences.getString("storedInputEventIndicesAndNames", null) != null && sharedPreferences.getBoolean("fixInputEventIndices", false)) {
                    d.b(string, sharedPreferences);
                    d.a(string, sharedPreferences);
                }
                String string2 = sharedPreferences.getString("eventIndexString", "");
                String string3 = sharedPreferences.getString("eventMultitouchType", "");
                ArrayList arrayList = new ArrayList();
                int n2 = sharedPreferences.getInt("numberOfAdditionalEventIndizes", 0);
                for (int i2 = 0; i2 < n2; ++i2) {
                    int n3;
                    if (!sharedPreferences.getBoolean("additionalEventEnabled_" + i2, false) || (n3 = Integer.parseInt((String)sharedPreferences.getString("additionalEventIndexString_" + i2, "-1"))) == -1) continue;
                    arrayList.add((Object)n3);
                }
                if (string2.equals((Object)"") || string3.equals((Object)"")) {
                    this.a(this.a.getString(2131165374));
                    return;
                }
                this.a.startService(new Intent(this.a, (Class)EventManagerServicePro.class).putExtra("eventserverPath", string).putExtra("eventIndex", string2).putExtra("eventMultitouchType", string3).putIntegerArrayListExtra("additionalEventIndizes", arrayList));
                int n4 = sharedPreferences.getInt("startGlobalX", 0);
                int n5 = sharedPreferences.getInt("startGlobalY", 0);
                this.a.startService(new Intent(this.a, (Class)PanelServicePro.class).putExtra("x", n4).putExtra("y", n5));
                if (sharedPreferences.getBoolean("hidePanelOnStart", false)) return;
                {
                    this.a.startService(new Intent(this.a, (Class)PanelServicePro.class).setAction("panelservice").putExtra("message", "show"));
                    return;
                }
            }
        }
    }
}

