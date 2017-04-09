/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.os.SystemClock
 *  java.io.BufferedInputStream
 *  java.io.IOException
 *  java.io.InputStream
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.Thread
 *  java.lang.Throwable
 *  java.util.concurrent.ArrayBlockingQueue
 */
package com.cygery.repetitouch;

import android.os.SystemClock;
import com.cygery.repetitouch.Event;
import com.cygery.repetitouch.b;
import com.cygery.utilities.a;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.ArrayBlockingQueue;

public class i
extends Thread {
    private static final String a = i.class.getName();
    private b b;
    private final BufferedInputStream c;

    public i(b b2, InputStream inputStream) {
        this.b = b2;
        this.c = new BufferedInputStream(inputStream);
    }

    public void run() {
        try {
            byte[] arrby = new byte[Event.EVENT_SIZE];
            if (this.c.read(arrby, 0, Event.EVENT_SIZE) == Event.EVENT_SIZE) {
                Event event = new Event(arrby);
                this.b.u = 1000 * (SystemClock.elapsedRealtime() - this.b.r) - event.b();
                a.a(a, "got event: " + event.toString());
                if (!this.b.v.offer((Object)event)) {
                    a.b(a, "event skipped");
                }
            }
            while (this.c.read(arrby, 0, Event.EVENT_SIZE) == Event.EVENT_SIZE) {
                Event event = new Event(arrby);
                a.a(a, "got event: " + event.toString());
                if (this.b.v.offer((Object)event)) continue;
                a.b(a, "event skipped");
            }
        }
        catch (IOException var1_4) {
            a.c(a, "IOException:");
            a.c(a, (Throwable)var1_4);
        }
    }
}

