package com.gumtree.android.tracking;

import com.gumtree.android.GumtreeApplication;

public class DefaultTrackingDataProvider implements TrackingDataProvider {
    public String getUserAgent() {
        return GumtreeApplication.getTrackingInfo().getUserAgent();
    }
}
