/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  java.lang.InterruptedException
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.Throwable
 *  java.util.concurrent.ArrayBlockingQueue
 */
package com.cygery.repetitouch.pro;

import com.cygery.repetitouch.Event;
import com.cygery.repetitouch.j;
import com.cygery.repetitouch.pro.EventManagerServicePro;
import com.cygery.utilities.a;
import java.util.concurrent.ArrayBlockingQueue;

public class b
extends j {
    private static final String f = b.class.getName();
    private EventManagerServicePro g;

    public b(EventManagerServicePro eventManagerServicePro) {
        super(eventManagerServicePro);
        this.g = eventManagerServicePro;
    }

    @Override
    protected void b() {
        try {
            this.g.s().put((Object)Event.STOP_EVENT);
            return;
        }
        catch (InterruptedException var1_1) {
            a.c(f, (Throwable)var1_1);
            return;
        }
    }
}

