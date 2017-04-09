package com.ebay.kleinanzeigen.imagepicker.platform;

import android.util.Log;
import com.apptentive.android.sdk.BuildConfig;

public class LOG {
    private static final String LOG_TAG = "EBK-Image-Picker";
    private static final StackTraceElement NOT_FOUND = new StackTraceElement(BuildConfig.FLAVOR, BuildConfig.FLAVOR, BuildConfig.FLAVOR, 0);
    private static boolean release = false;

    private LOG() {
    }

    public static void setRelease(boolean z) {
        release = z;
    }

    public static void error(Throwable th) {
        if (!release) {
            Log.e(LOG_TAG, enhanced(BuildConfig.FLAVOR), th);
            logRootCause(th);
        }
    }

    public static void error(String str) {
        if (!release) {
            Log.e(LOG_TAG, str);
        }
    }

    public static void info(String str) {
        if (!release) {
            Log.i(LOG_TAG, str);
        }
    }

    private static void logRootCause(Throwable th) {
        if (getRootCause(th) != th) {
            Log.e(LOG_TAG, "root cause", getRootCause(th));
        }
    }

    private static Throwable getRootCause(Throwable th) {
        Throwable cause = th.getCause();
        return cause == null ? th : getRootCause(cause);
    }

    private static final String enhanced(String str) {
        StackTraceElement determineCaller = determineCaller();
        String classNameOnly = getClassNameOnly(determineCaller.getClassName());
        String methodName = determineCaller.getMethodName();
        int lineNumber = determineCaller.getLineNumber();
        Thread currentThread = Thread.currentThread();
        return String.format("%s [%s:%s:%s] %s", new Object[]{str, classNameOnly, methodName, Integer.valueOf(lineNumber), currentThread});
    }

    private static StackTraceElement determineCaller() {
        for (StackTraceElement stackTraceElement : new RuntimeException().getStackTrace()) {
            if (!stackTraceElement.getClassName().equals(LOG.class.getName())) {
                return stackTraceElement;
            }
        }
        return NOT_FOUND;
    }

    private static String getClassNameOnly(String str) {
        int lastIndexOf = str.lastIndexOf(46);
        return lastIndexOf == -1 ? str : str.substring(lastIndexOf + 1);
    }
}
