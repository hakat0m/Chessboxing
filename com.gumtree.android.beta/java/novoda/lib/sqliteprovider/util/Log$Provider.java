package novoda.lib.sqliteprovider.util;

import android.util.Log;

public class Log$Provider {
    public static final String TAG = "SQLiteProvider";

    public static final boolean infoLoggingEnabled() {
        return Log.isLoggable(TAG, 4);
    }

    public static final boolean debugLoggingEnabled() {
        return Log.isLoggable(TAG, 3);
    }

    public static final boolean verboseLoggingEnabled() {
        return Log.isLoggable(TAG, 2);
    }

    public static final boolean warningLoggingEnabled() {
        return Log.isLoggable(TAG, 5);
    }

    public static final boolean errorLoggingEnabled() {
        return Log.isLoggable(TAG, 6);
    }

    public static final void i(String str) {
        Log.i(TAG, str);
    }

    public static final void d(String str) {
        Log.d(TAG, str);
    }

    public static final void v(String str) {
        Log.v(TAG, str);
    }

    public static final void e(String str) {
        Log.e(TAG, str);
    }

    public static final void e(String str, Throwable th) {
        Log.e(TAG, str, th);
    }

    public static final void e(Throwable th) {
        Log.e(TAG, th.getClass().getSimpleName(), th);
    }

    public static final void i(String str, Throwable th) {
        Log.e(TAG, str, th);
    }

    public static final void w(String str) {
        Log.w(TAG, str);
    }
}
