/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.os.SystemClock
 *  java.io.DataOutputStream
 *  java.lang.InterruptedException
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.Thread
 *  java.lang.Thread$State
 *  java.util.TreeSet
 */
package com.cygery.repetitouch;

import android.os.SystemClock;
import com.cygery.repetitouch.b;
import com.cygery.repetitouch.e;
import com.cygery.utilities.a;
import java.io.DataOutputStream;
import java.util.TreeSet;

public abstract class s
extends Thread {
    private static final String j = s.class.getName();
    protected DataOutputStream a;
    protected final Object b = new Object();
    protected volatile boolean c;
    protected int d;
    protected double e;
    protected volatile boolean f;
    protected volatile int g = -1;
    protected long h;
    protected long i;
    private b k;

    public s(b b2) {
        this.k = b2;
        this.c = true;
        this.d = 1;
        this.e = 1.0;
        this.f = true;
    }

    public void a() {
        s s2 = this;
        synchronized (s2) {
            this.c = false;
            if (this.getState() == Thread.State.TIMED_WAITING) {
                this.interrupt();
            }
            return;
        }
    }

    public void a(double d2) {
        this.e = d2;
    }

    public void a(int n2) {
        this.d = n2;
    }

    protected void a(long l2) {
        this.a(l2, 0);
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    protected void a(long var1, int var3_2) {
        var4_3 = 0;
        while (this.c != false) {
            if (var4_3 >= var1) return;
            var13_5 = var6_8 = this.b;
            synchronized (var13_5) {
                while (this.f) {
                    this.b.wait();
                }
                if (!this.c) return;
                {
                    var8_4 = SystemClock.elapsedRealtime();
                    var10_7 = var1 - var4_3;
                    s.sleep((long)var10_7, (int)var3_2);
                    ** GOTO lbl16
                }
                {
                    catch (InterruptedException var12_6) {}
                }
lbl16: // 2 sources:
                var4_3 += SystemClock.elapsedRealtime() - var8_4;
                continue;
            }
        }
    }

    public void a(DataOutputStream dataOutputStream) {
        this.a = dataOutputStream;
    }

    /*
     * Enabled aggressive block sorting
     */
    public void a(String string) {
        if (this.k.o != null) {
            e e2 = this.f ? new e(this.g, this.h - this.k.t() - this.i, string) : new e(this.g, SystemClock.elapsedRealtime() - this.k.t() - this.i, string);
            this.k.o.add((Object)e2);
            a.a(j, "added marker: " + e2.a() + " " + e2.b());
        }
    }

    public void b() {
        a.a(j, "pauseReplay()");
        this.h = SystemClock.elapsedRealtime();
        this.f = true;
        if (this.getState() == Thread.State.TIMED_WAITING) {
            this.interrupt();
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void c() {
        Object object;
        a.a(j, "resumeReplay()");
        Object object2 = object = this.b;
        synchronized (object2) {
            this.i += SystemClock.elapsedRealtime() - this.h;
            this.f = false;
            this.b.notify();
            return;
        }
    }
}

