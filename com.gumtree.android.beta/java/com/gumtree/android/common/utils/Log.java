package com.gumtree.android.common.utils;

import com.apptentive.android.sdk.BuildConfig;

public final class Log {
    public static final String DFP_TAG = "GumtreeDFP";
    private static final StackTraceElement NOT_FOUND = new StackTraceElement(BuildConfig.FLAVOR, BuildConfig.FLAVOR, BuildConfig.FLAVOR, 0);
    public static final String TAG = "Gumtree";

    private Log() {
    }

    private static StackTraceElement determineCaller() {
        for (StackTraceElement stackTraceElement : new RuntimeException().getStackTrace()) {
            if (!stackTraceElement.getClassName().equals(Log.class.getName())) {
                return stackTraceElement;
            }
        }
        return NOT_FOUND;
    }

    private static String getClassNameOnly(String str) {
        int lastIndexOf = str.lastIndexOf(46);
        return lastIndexOf == -1 ? str : str.substring(lastIndexOf + 1);
    }

    private static String enhanced(String str) {
        StackTraceElement determineCaller = determineCaller();
        String classNameOnly = getClassNameOnly(determineCaller.getClassName());
        String methodName = determineCaller.getMethodName();
        int lineNumber = determineCaller.getLineNumber();
        Thread currentThread = Thread.currentThread();
        return String.format("%s [%s:%s:%s] %s", new Object[]{str, classNameOnly, methodName, Integer.valueOf(lineNumber), currentThread});
    }

    public static boolean verboseLoggingEnabled() {
        return android.util.Log.isLoggable(TAG, 2);
    }

    public static boolean verboseLoggingEnabled(String str) {
        return android.util.Log.isLoggable(str, 2);
    }

    public static boolean errorLoggingEnabled() {
        return android.util.Log.isLoggable(TAG, 6);
    }

    public static void v(String str) {
        android.util.Log.v(TAG, enhanced(str));
    }

    public static void v(String str, String str2) {
        android.util.Log.v(str, enhanced(str2));
    }

    public static void e(String str) {
        android.util.Log.e(TAG, enhanced(str));
    }

    public static void e(String str, String str2) {
        android.util.Log.e(str, enhanced(str2));
    }

    public static void e(String str, Throwable th) {
        android.util.Log.e(TAG, enhanced(str), th);
    }

    public static void e(String str, String str2, Throwable th) {
        android.util.Log.e(str, enhanced(str2), th);
    }

    public static void w(String str) {
        android.util.Log.w(TAG, enhanced(str));
    }

    public static void w(String str, Throwable th) {
        android.util.Log.w(TAG, enhanced(str), th);
    }
}
