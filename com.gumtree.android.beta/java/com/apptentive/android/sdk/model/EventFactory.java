package com.apptentive.android.sdk.model;

import com.apptentive.android.sdk.ApptentiveLog;

public class EventFactory {
    public static Event fromJson(String str) {
        try {
            return new Event(str);
        } catch (Throwable e) {
            ApptentiveLog.v("Error parsing json as Event: %s", e, str);
        } catch (IllegalArgumentException e2) {
        }
        return null;
    }
}
