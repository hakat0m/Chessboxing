package com.apptentive.android.sdk.model;

import com.apptentive.android.sdk.ApptentiveLog;

public class DeviceFactory {
    public static Device fromJson(String str) {
        try {
            return new Device(str);
        } catch (Throwable e) {
            ApptentiveLog.v("Error parsing json as Device: %s", e, str);
        } catch (IllegalArgumentException e2) {
        }
        return null;
    }
}
