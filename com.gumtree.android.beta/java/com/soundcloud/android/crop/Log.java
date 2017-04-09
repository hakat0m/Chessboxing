package com.soundcloud.android.crop;

class Log {
    private static final String TAG = "android-crop";

    Log() {
    }

    public static void e(String str) {
        android.util.Log.e(TAG, str);
    }

    public static void e(String str, Throwable th) {
        android.util.Log.e(TAG, str, th);
    }
}
