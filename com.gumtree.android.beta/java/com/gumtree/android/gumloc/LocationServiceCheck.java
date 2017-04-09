package com.gumtree.android.gumloc;

import android.content.Context;
import android.provider.Settings.Secure;
import android.text.TextUtils;
import com.google.android.gms.common.GooglePlayServicesUtil;

public final class LocationServiceCheck {
    private LocationServiceCheck() {
    }

    public static boolean isLocationServicesEnabled(Context context) {
        return !TextUtils.isEmpty(Secure.getString(context.getContentResolver(), "location_providers_allowed"));
    }

    public static boolean isPlayServicesEnabled(Context context) {
        if (GooglePlayServicesUtil.isGooglePlayServicesAvailable(context) == 0) {
            return true;
        }
        return false;
    }

    public static boolean isServiceAvailable(Context context) {
        return isPlayServicesEnabled(context) && isLocationServicesEnabled(context);
    }
}
