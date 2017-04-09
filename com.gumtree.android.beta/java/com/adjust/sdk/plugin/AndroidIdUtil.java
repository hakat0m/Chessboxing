package com.adjust.sdk.plugin;

import android.content.Context;
import android.provider.Settings.Secure;

public class AndroidIdUtil {
    public static String getAndroidId(Context context) {
        return Secure.getString(context.getContentResolver(), "android_id");
    }
}
