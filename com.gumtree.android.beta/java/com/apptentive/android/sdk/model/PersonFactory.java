package com.apptentive.android.sdk.model;

import com.apptentive.android.sdk.ApptentiveLog;

public class PersonFactory {
    public static Person fromJson(String str) {
        try {
            return new Person(str);
        } catch (Throwable e) {
            ApptentiveLog.v("Error parsing json as Person: %s", e, str);
        } catch (IllegalArgumentException e2) {
        }
        return null;
    }
}
