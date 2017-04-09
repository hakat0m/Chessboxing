/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.app.AlarmManager
 *  android.app.Application
 *  android.app.PendingIntent
 *  android.content.Context
 *  android.content.Intent
 *  android.os.Process
 *  java.io.PrintWriter
 *  java.io.StringWriter
 *  java.io.Writer
 *  java.lang.Class
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.System
 *  java.lang.Thread
 *  java.lang.Thread$UncaughtExceptionHandler
 *  java.lang.Throwable
 */
package com.hendricks;

import android.app.AlarmManager;
import android.app.Application;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Process;
import com.hendricks.DebugActivity;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;

public class SketchApplication
extends Application {
    private Thread.UncaughtExceptionHandler uncaughtExceptionHandler;

    private String getStackTrace(Throwable throwable) {
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter((Writer)stringWriter);
        do {
            if (throwable == null) {
                String string = stringWriter.toString();
                printWriter.close();
                return string;
            }
            throwable.printStackTrace(printWriter);
            throwable = throwable.getCause();
        } while (true);
    }

    public void onCreate() {
        this.uncaughtExceptionHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler((Thread.UncaughtExceptionHandler)new Thread.UncaughtExceptionHandler(){

            public void uncaughtException(Thread thread, Throwable throwable) {
                Intent intent = new Intent(SketchApplication.this.getApplicationContext(), (Class)DebugActivity.class);
                intent.setFlags(32768);
                intent.putExtra("error", SketchApplication.this.getStackTrace(throwable));
                PendingIntent pendingIntent = PendingIntent.getActivity((Context)SketchApplication.this.getApplicationContext(), (int)11111, (Intent)intent, (int)1073741824);
                ((AlarmManager)SketchApplication.this.getSystemService("alarm")).set(2, 1000, pendingIntent);
                Process.killProcess((int)Process.myPid());
                System.exit((int)2);
                SketchApplication.this.uncaughtExceptionHandler.uncaughtException(thread, throwable);
            }
        });
        super.onCreate();
    }

}

