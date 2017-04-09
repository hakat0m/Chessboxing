/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.content.BroadcastReceiver
 *  android.content.ComponentName
 *  android.content.Context
 *  android.content.Intent
 *  android.content.SharedPreferences
 *  android.os.Bundle
 *  android.os.PowerManager
 *  android.preference.PreferenceManager
 *  android.telephony.TelephonyManager
 *  android.widget.Toast
 *  java.io.File
 *  java.lang.CharSequence
 *  java.lang.Class
 *  java.lang.Double
 *  java.lang.Integer
 *  java.lang.NumberFormatException
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
import android.os.Bundle;
import android.os.PowerManager;
import android.preference.PreferenceManager;
import android.telephony.TelephonyManager;
import android.widget.Toast;
import com.cygery.repetitouch.b;
import com.cygery.repetitouch.d;
import com.cygery.repetitouch.pro.EventManagerServicePro;
import com.cygery.repetitouch.pro.PanelServicePro;
import com.cygery.utilities.a;
import com.cygery.utilities.d;
import java.io.File;
import java.util.ArrayList;

public final class FireReceiver
extends BroadcastReceiver
implements d.a {
    private static final String a = FireReceiver.class.getName();
    private Context b;
    private Intent c;
    private SharedPreferences d;

    @Override
    public void a() {
        this.a(this.b.getString(2131165358), 1);
    }

    void a(String string) {
        this.a(string, 0);
    }

    void a(String string, int n2) {
        Toast.makeText((Context)this.b, (CharSequence)string, (int)n2).show();
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    @Override
    public void b() {
        this.d = this.b.getSharedPreferences("repetitouch_prefs", 0);
        if (!this.c()) {
            this.a(this.b.getString(2131165280), 1);
            return;
        }
        a.a(this.b);
        var1_1 = this.c.getBundleExtra("com.twofortyfouram.locale.intent.extra.BUNDLE");
        if (var1_1 == null) {
            this.a(this.b.getString(2131165357));
            return;
        }
        var2_2 = var1_1.getString("action");
        var3_3 = var2_2 == null ? "" : var2_2;
        var4_4 = var1_1.getString("filename");
        var5_5 = var4_4 == null ? "" : var4_4;
        var6_6 = var1_1.getBoolean("appendingrecord", false);
        var7_7 = var1_1.getInt("looptimes", 1);
        try {
            var44_8 = var1_1.getString("replayspeed");
            var9_10 = var44_8 != null ? (var45_9 = Double.parseDouble((String)var44_8)) : 0.0;
        }
        catch (NumberFormatException var8_11) {
            var9_10 = -1.0;
        }
        var11_12 = var1_1.getBoolean("hidepanel", false);
        var12_13 = var1_1.getBoolean("closeafteraction", false);
        var13_14 = var1_1.getBoolean("silent", false);
        var14_15 = (Object)this.b.getFilesDir() + File.separator + "eventserver";
        var15_16 = new File(var14_15);
        if (!var15_16.exists() || d.b(this.b) || !var15_16.canExecute()) {
            this.a(this.b.getString(2131165374));
            return;
        }
        if (b.C()) {
            b.b(var14_15, this.b.getString(2131165456));
        }
        PreferenceManager.setDefaultValues((Context)this.b, (String)"repetitouch_prefs", (int)0, (int)2131034112, (boolean)false);
        if (this.d.getString("storedInputEventIndicesAndNames", null) != null && this.d.getBoolean("fixInputEventIndices", false)) {
            d.b(var14_15, this.d);
            d.a(var14_15, this.d);
        }
        var16_17 = this.d.getString("eventIndexString", "");
        var17_18 = this.d.getString("eventMultitouchType", "");
        var18_19 = new ArrayList();
        var19_20 = this.d.getInt("numberOfAdditionalEventIndizes", 0);
        for (var20_21 = 0; var20_21 < var19_20; ++var20_21) {
            if (!this.d.getBoolean("additionalEventEnabled_" + var20_21, false) || (var42_22 = Integer.parseInt((String)this.d.getString("additionalEventIndexString_" + var20_21, "-1"))) == -1) continue;
            var18_19.add((Object)var42_22);
        }
        var21_23 = this.d.getBoolean("ignoreCallState", false);
        if (var16_17.equals((Object)"") || var17_18.equals((Object)"")) {
            this.a(this.b.getString(2131165374));
            return;
        }
        if (var21_23) ** GOTO lbl-1000
        switch (((TelephonyManager)this.b.getSystemService("phone")).getCallState()) {
            default: lbl-1000: // 2 sources:
            {
                if (((PowerManager)this.b.getSystemService("power")).isScreenOn()) break;
                a.b(FireReceiver.a, "action aborted because screen is off");
                return;
            }
            case 1: 
            case 2: {
                this.a(this.b.getString(2131165245));
                return;
            }
        }
        this.b.startService(new Intent(this.b, (Class)EventManagerServicePro.class).putExtra("eventserverPath", var14_15).putExtra("eventIndex", var16_17).putExtra("eventMultitouchType", var17_18).putIntegerArrayListExtra("additionalEventIndizes", var18_19).putExtra("silent", var13_14).putExtra("block", true));
        var23_24 = this.d.getInt("startGlobalX", 0);
        var24_25 = this.d.getInt("startGlobalY", 0);
        this.b.startService(new Intent(this.b, (Class)PanelServicePro.class).putExtra("x", var23_24).putExtra("y", var24_25).putExtra("silent", var13_14));
        a.a(FireReceiver.a, "Locale plugin: action=" + var3_3 + " appendingRecord=" + var6_6 + " loopTimes=" + var7_7 + " replaySpeed=" + var9_10 + " hidePanel=" + var11_12 + " closeAfterAction=" + var12_13 + " silent=" + var13_14 + " path=" + var5_5);
        if (var3_3.equals((Object)"Start Record")) {
            if (var6_6) {
                this.b.startService(new Intent(this.b, (Class)EventManagerServicePro.class).setAction("eventmanagerservice").putExtra("message", "startrecordappending").putExtra("silent", var13_14));
                return;
            }
            this.b.startService(new Intent(this.b, (Class)EventManagerServicePro.class).setAction("eventmanagerservice").putExtra("message", "startrecord").putExtra("silent", var13_14));
            return;
        }
        if (var3_3.equals((Object)"Stop Record")) {
            this.b.startService(new Intent(this.b, (Class)EventManagerServicePro.class).setAction("eventmanagerservice").putExtra("message", "stoprecordbyservice").putExtra("silent", var13_14));
            if (!var5_5.equals((Object)"")) {
                this.b.startService(new Intent(this.b, (Class)EventManagerServicePro.class).setAction("eventmanagerservice").putExtra("message", "saveevents").putExtra("path", var5_5).putExtra("silent", var13_14).putExtra("block", true));
            }
            if (var12_13 == false) return;
            this.b.startService(new Intent(this.b, (Class)EventManagerServicePro.class).setAction("eventmanagerservice").putExtra("message", "close").putExtra("silent", var13_14));
            return;
        }
        if (var3_3.equals((Object)"Start Replay")) {
            if (var7_7 == 0) return;
            if (var9_10 < 0.0) return;
            this.b.startService(new Intent(this.b, (Class)PanelServicePro.class).setAction("panelservice").putExtra("message", "overridehide").putExtra("hidepanel", var11_12).putExtra("silent", var13_14));
            if (!var5_5.equals((Object)"")) {
                this.b.startService(new Intent(this.b, (Class)EventManagerServicePro.class).setAction("eventmanagerservice").putExtra("message", "loadevents").putExtra("path", var5_5).putExtra("silent", var13_14).putExtra("block", true));
            }
            if (var7_7 == 1) {
                var33_26 = new Intent(this.b, (Class)EventManagerServicePro.class).setAction("eventmanagerservice").putExtra("message", "startreplay").putExtra("closeafteraction", var12_13).putExtra("silent", var13_14);
                if (var9_10 != 0.0) {
                    var33_26.putExtra("replaySpeed", var9_10);
                }
                this.b.startService(var33_26);
                return;
            }
            var30_27 = new Intent(this.b, (Class)EventManagerServicePro.class).setAction("eventmanagerservice").putExtra("message", "startreplayloop").putExtra("loopCount", var7_7).putExtra("closeafteraction", var12_13).putExtra("silent", var13_14);
            if (var9_10 != 0.0) {
                var30_27.putExtra("replaySpeed", var9_10);
            }
            this.b.startService(var30_27);
            return;
        }
        if (var3_3.equals((Object)"Stop Replay")) {
            this.b.startService(new Intent(this.b, (Class)EventManagerServicePro.class).setAction("eventmanagerservice").putExtra("message", "stopreplay").putExtra("closeafteraction", var12_13).putExtra("silent", var13_14));
            return;
        }
        if (var3_3.equals((Object)"Load File")) {
            if (var5_5.equals((Object)"") != false) return;
            this.b.startService(new Intent(this.b, (Class)EventManagerServicePro.class).setAction("eventmanagerservice").putExtra("message", "loadevents").putExtra("path", var5_5).putExtra("silent", var13_14).putExtra("block", true));
            return;
        }
        if (var3_3.equals((Object)"Save File")) {
            if (var5_5.equals((Object)"") != false) return;
            this.b.startService(new Intent(this.b, (Class)EventManagerServicePro.class).setAction("eventmanagerservice").putExtra("message", "saveevents").putExtra("path", var5_5).putExtra("silent", var13_14).putExtra("block", true));
            return;
        }
        this.a(this.b.getString(2131165363));
    }

    boolean c() {
        return this.d.getBoolean("enableLocaleSupport", false);
    }

    public void onReceive(Context context, Intent intent) {
        if ("com.twofortyfouram.locale.intent.action.FIRE_SETTING".equals((Object)intent.getAction())) {
            this.b = context;
            this.c = intent;
            new com.cygery.utilities.d(this.b, (d.a)this, "repetitouch_prefs").a(context.getString(2131165453), false);
        }
    }
}

