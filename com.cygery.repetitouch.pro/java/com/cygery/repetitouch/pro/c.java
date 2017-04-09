/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.content.ComponentName
 *  android.content.Context
 *  android.content.Intent
 *  android.content.SharedPreferences
 *  android.os.Handler
 *  java.lang.Class
 *  java.lang.Integer
 *  java.lang.NumberFormatException
 *  java.lang.Runnable
 *  java.lang.String
 */
package com.cygery.repetitouch.pro;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import com.cygery.repetitouch.b;
import com.cygery.repetitouch.l;
import com.cygery.repetitouch.pro.EventManagerServicePro;
import com.cygery.repetitouch.pro.f;
import com.cygery.repetitouch.s;

public class c
extends l {
    private static final String n = c.class.getName();
    private EventManagerServicePro o;

    public c(EventManagerServicePro eventManagerServicePro) {
        super(eventManagerServicePro);
        this.o = eventManagerServicePro;
        this.e = new f(this.o);
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive exception aggregation
     */
    @Override
    protected void c() {
        var1_1 = 10;
        this.o.w().removeCallbacks(this.o.D());
        if (!this.o.y().getBoolean("dimScreenOnReplay", false)) ** GOTO lbl10
        try {
            var1_1 = var5_2 = this.o.y().getInt("dimScreenPercentageInt", Integer.parseInt((String)this.o.y().getString("dimScreenPercentage", String.valueOf((int)10))));
        }
        catch (NumberFormatException var3_3) {
            ** continue;
        }
lbl8: // 2 sources:
        do {
            this.o.startService(new Intent((Context)this.o, this.o.x()).setAction("panelservice").putExtra("message", "resetbrightness").putExtra("brightness", var1_1));
lbl10: // 2 sources:
            if (this.k) {
                this.o.stopService(new Intent((Context)this.o, this.o.x()));
            }
            return;
            break;
        } while (true);
    }
}

