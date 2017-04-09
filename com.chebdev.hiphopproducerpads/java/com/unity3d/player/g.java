package com.unity3d.player;

import android.os.Build;
import java.lang.Thread.UncaughtExceptionHandler;

final class g implements UncaughtExceptionHandler {
    private volatile UncaughtExceptionHandler a;

    g() {
    }

    final synchronized boolean a() {
        boolean z;
        g defaultUncaughtExceptionHandler = Thread.getDefaultUncaughtExceptionHandler();
        if (defaultUncaughtExceptionHandler == this) {
            z = false;
        } else {
            this.a = defaultUncaughtExceptionHandler;
            Thread.setDefaultUncaughtExceptionHandler(this);
            z = true;
        }
        return z;
    }

    public final synchronized void uncaughtException(Thread thread, Throwable th) {
        try {
            Throwable error = new Error(String.format("FATAL EXCEPTION [%s]\n", new Object[]{thread.getName()}) + String.format("Unity version     : %s\n", new Object[]{"5.5.0f3"}) + String.format("Device model      : %s %s\n", new Object[]{Build.MANUFACTURER, Build.MODEL}) + String.format("Device fingerprint: %s\n", new Object[]{Build.FINGERPRINT}));
            error.setStackTrace(new StackTraceElement[0]);
            error.initCause(th);
            this.a.uncaughtException(thread, error);
        } catch (Throwable th2) {
            this.a.uncaughtException(thread, th);
        }
    }
}
