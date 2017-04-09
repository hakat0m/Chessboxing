package com.adjust.sdk;

import android.content.Context;
import android.net.Uri;

public class Adjust {
    private static AdjustInstance defaultInstance;

    private Adjust() {
    }

    public static synchronized AdjustInstance getDefaultInstance() {
        AdjustInstance adjustInstance;
        synchronized (Adjust.class) {
            if (defaultInstance == null) {
                defaultInstance = new AdjustInstance();
            }
            adjustInstance = defaultInstance;
        }
        return adjustInstance;
    }

    public static void onCreate(AdjustConfig adjustConfig) {
        getDefaultInstance().onCreate(adjustConfig);
    }

    public static void trackEvent(AdjustEvent adjustEvent) {
        getDefaultInstance().trackEvent(adjustEvent);
    }

    public static void onResume() {
        getDefaultInstance().onResume();
    }

    public static void onPause() {
        getDefaultInstance().onPause();
    }

    public static void setEnabled(boolean z) {
        getDefaultInstance().setEnabled(z);
    }

    public static boolean isEnabled() {
        return getDefaultInstance().isEnabled();
    }

    public static void appWillOpenUrl(Uri uri) {
        getDefaultInstance().appWillOpenUrl(uri);
    }

    public static void setReferrer(String str) {
        getDefaultInstance().sendReferrer(str);
    }

    public static void setOfflineMode(boolean z) {
        getDefaultInstance().setOfflineMode(z);
    }

    public static void getGoogleAdId(Context context, OnDeviceIdsRead onDeviceIdsRead) {
        Util.getGoogleAdId(context, onDeviceIdsRead);
    }
}
