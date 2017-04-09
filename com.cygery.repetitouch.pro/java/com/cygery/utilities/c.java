/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.SharedPreferences
 *  android.content.SharedPreferences$Editor
 *  android.os.SystemClock
 *  java.lang.Math
 *  java.lang.Object
 *  java.lang.String
 */
package com.cygery.utilities;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.SystemClock;

public class c {
    private static final String i = c.class.getName();
    private static c j = null;
    protected Context a;
    protected SharedPreferences b;
    protected SharedPreferences.Editor c;
    protected boolean d;
    protected int e;
    protected int f;
    protected int g;
    protected int h;

    private c(Context context, String string, int n2) {
        this.a = context;
        this.b = context.getSharedPreferences(string, n2);
        this.c = this.b.edit();
        this.d = this.b.getBoolean("appratereminder.dontask", false);
        this.e = this.b.getInt("appratereminder.timeslaunched", 0);
        this.f = this.b.getInt("appratereminder.timeactive", 0);
    }

    public static c a(Context context, String string, int n2) {
        if (j == null) {
            j = new c(context, string, n2);
        }
        return j;
    }

    public void a() {
        this.g = (int)(SystemClock.elapsedRealtime() / 1000);
    }

    public void b() {
        this.h = (int)(SystemClock.elapsedRealtime() / 1000);
        this.f += this.h - this.g;
        this.e = 1 + this.e;
        this.c.putInt("appratereminder.timeslaunched", this.e);
        this.c.putInt("appratereminder.timeactive", this.f);
        this.c.apply();
    }

    /*
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    public boolean c() {
        if (this.d) {
            return false;
        }
        if (this.e < 10) return false;
        if (this.f < 1200) return false;
        double d2 = 0.0 + 0.25 / (1.0 + Math.exp((double)(2.0 * ((double)(- -10 + this.e) / 10.0)))) + 0.75 / (1.0 + Math.exp((double)(8.0 * ((double)(- -1200 + this.f) / 1200.0))));
        if (Math.random() > d2) return false;
        return true;
    }

    public void d() {
        this.d = false;
        this.e = 0;
        this.f = 0;
        this.c.putBoolean("appratereminder.dontask", this.d);
        this.c.putInt("appratereminder.timeslaunched", this.e);
        this.c.putInt("appratereminder.timeactive", this.f);
        this.c.apply();
    }

    public void e() {
        this.d = true;
        this.c.putBoolean("appratereminder.dontask", this.d);
        this.c.apply();
    }
}

