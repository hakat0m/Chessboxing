package com.apptentive.android.sdk.model;

import com.apptentive.android.sdk.ApptentiveLog;

public class SdkFactory {
    public static Sdk fromJson(String str) {
        try {
            return new Sdk(str);
        } catch (Throwable e) {
            ApptentiveLog.v("Error parsing json as Sdk: %s", e, str);
        } catch (IllegalArgumentException e2) {
        }
        return null;
    }
}
