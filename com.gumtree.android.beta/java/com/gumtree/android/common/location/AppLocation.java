package com.gumtree.android.common.location;

import java.io.Serializable;
import java.util.Map;

public interface AppLocation extends Serializable {
    public static final String ADMOB_DATA_LOCATION = "loc";
    public static final String ADMOB_DATA_POSTCODE = "postcode";

    public enum LocationType {
        GumtreeLocation,
        GeocoderLocation
    }

    Map<String, String> getAdMobData();

    String getDisplayText(boolean z);

    boolean getIsRecent();

    String getName();

    String getTrackingData();

    LocationType getType();

    void setIsRecent(boolean z);
}
