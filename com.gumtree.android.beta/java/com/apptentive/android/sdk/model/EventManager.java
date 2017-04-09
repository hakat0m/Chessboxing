package com.apptentive.android.sdk.model;

import com.apptentive.android.sdk.ApptentiveInternal;
import com.apptentive.android.sdk.storage.EventStore;

public class EventManager {
    private static EventStore getEventStore() {
        return ApptentiveInternal.getInstance().getApptentiveDatabase();
    }

    public static void sendEvent(Event event) {
        getEventStore().addPayload(new Payload[]{event});
    }
}
