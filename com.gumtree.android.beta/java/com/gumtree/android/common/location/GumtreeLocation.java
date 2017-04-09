package com.gumtree.android.common.location;

import com.gumtree.android.common.location.AppLocation.LocationType;
import java.util.HashMap;
import java.util.Map;

public class GumtreeLocation implements AppLocation {
    protected final String idName;
    private boolean isRecent;
    protected final String locationId;
    protected final String locationName;
    protected GumtreeTypeLocation typeLocation = GumtreeTypeLocation.LOCATION;

    protected GumtreeLocation(String str, String str2, String str3) {
        this.locationId = str;
        this.locationName = str2;
        this.idName = str3;
    }

    public static GumtreeLocation get(String str, String str2, String str3, String str4, String str5) {
        if ("POSTCODE".equals(str5) || "OUTCODE".equals(str5)) {
            return GumtreePostcodeLocation.get(str + ", " + str3, str2, str, str4);
        }
        if ("OUTCODE_POSTCODE".equals(str5)) {
            return GumtreePostcodeLocation.get(str, str2, str3, str4);
        }
        return new GumtreeLocation(str2, str3, str4);
    }

    public String getLocationId() {
        return this.locationId;
    }

    public String getName() {
        return this.locationName;
    }

    public LocationType getType() {
        return LocationType.GumtreeLocation;
    }

    public String getDisplayText(boolean z) {
        return getName();
    }

    public Map<String, String> getAdMobData() {
        Map<String, String> hashMap = new HashMap();
        hashMap.put(AppLocation.ADMOB_DATA_LOCATION, this.idName);
        return hashMap;
    }

    public String getTrackingData() {
        return this.locationId + "_" + this.locationName;
    }

    public boolean getIsRecent() {
        return this.isRecent;
    }

    public void setIsRecent(boolean z) {
        this.isRecent = z;
    }

    public String getIdName() {
        return this.idName;
    }

    public String getTypeLocation() {
        return this.typeLocation.name();
    }
}
