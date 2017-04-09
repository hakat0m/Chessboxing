package com.gumtree.android;

import com.gumtree.Log;

public final class AndroidLog extends Log {
    protected void verbose(String str, String str2) {
        android.util.Log.v(str, str2);
    }

    protected void verbose(String str, String str2, Throwable th) {
        android.util.Log.v(str, str2, th);
    }

    protected void debug(String str, String str2) {
        android.util.Log.d(str, str2);
    }

    protected void debug(String str, String str2, Throwable th) {
        android.util.Log.d(str, str2, th);
    }

    protected void info(String str, String str2) {
        android.util.Log.i(str, str2);
    }

    protected void info(String str, String str2, Throwable th) {
        android.util.Log.i(str, str2, th);
    }

    protected void warn(String str, String str2) {
        android.util.Log.w(str, str2);
    }

    protected void warn(String str, String str2, Throwable th) {
        android.util.Log.w(str, str2, th);
    }

    protected void error(String str, String str2) {
        android.util.Log.e(str, str2);
    }

    protected void error(String str, String str2, Throwable th) {
        android.util.Log.w(str, str2, th);
    }
}
