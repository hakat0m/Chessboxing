package com.apptentive.android.sdk.model;

import com.apptentive.android.sdk.ApptentiveLog;

public class AppReleaseFactory {
    public static AppRelease fromJson(String str) {
        try {
            return new AppRelease(str);
        } catch (Throwable e) {
            ApptentiveLog.v("Error parsing json as AppRelease: %s", e, str);
        } catch (IllegalArgumentException e2) {
        }
        return null;
    }
}
