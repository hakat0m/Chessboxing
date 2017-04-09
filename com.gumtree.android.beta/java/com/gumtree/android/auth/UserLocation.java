package com.gumtree.android.auth;

import java.beans.ConstructorProperties;

public final class UserLocation {
    private final String locId;
    private final String locMap;
    private final String locName;
    private final String locPostcode;
    private final String locText;

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof UserLocation)) {
            return false;
        }
        UserLocation userLocation = (UserLocation) obj;
        String locId = getLocId();
        String locId2 = userLocation.getLocId();
        if (locId != null ? !locId.equals(locId2) : locId2 != null) {
            return false;
        }
        locId = getLocName();
        locId2 = userLocation.getLocName();
        if (locId != null ? !locId.equals(locId2) : locId2 != null) {
            return false;
        }
        locId = getLocText();
        locId2 = userLocation.getLocText();
        if (locId != null ? !locId.equals(locId2) : locId2 != null) {
            return false;
        }
        locId = getLocMap();
        locId2 = userLocation.getLocMap();
        if (locId != null ? !locId.equals(locId2) : locId2 != null) {
            return false;
        }
        locId = getLocPostcode();
        locId2 = userLocation.getLocPostcode();
        if (locId == null) {
            if (locId2 == null) {
                return true;
            }
        } else if (locId.equals(locId2)) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        int i = 43;
        String locId = getLocId();
        int hashCode = (locId == null ? 43 : locId.hashCode()) + 59;
        String locName = getLocName();
        hashCode = (locName == null ? 43 : locName.hashCode()) + (hashCode * 59);
        locName = getLocText();
        hashCode = (locName == null ? 43 : locName.hashCode()) + (hashCode * 59);
        locName = getLocMap();
        hashCode = (locName == null ? 43 : locName.hashCode()) + (hashCode * 59);
        locName = getLocPostcode();
        hashCode *= 59;
        if (locName != null) {
            i = locName.hashCode();
        }
        return hashCode + i;
    }

    public String toString() {
        return "UserLocation(locId=" + getLocId() + ", locName=" + getLocName() + ", locText=" + getLocText() + ", locMap=" + getLocMap() + ", locPostcode=" + getLocPostcode() + ")";
    }

    public static UserLocationBuilder builder() {
        return new UserLocationBuilder();
    }

    @ConstructorProperties({"locId", "locName", "locText", "locMap", "locPostcode"})
    public UserLocation(String str, String str2, String str3, String str4, String str5) {
        this.locId = str;
        this.locName = str2;
        this.locText = str3;
        this.locMap = str4;
        this.locPostcode = str5;
    }

    public String getLocId() {
        return this.locId;
    }

    public String getLocName() {
        return this.locName;
    }

    public String getLocText() {
        return this.locText;
    }

    public String getLocMap() {
        return this.locMap;
    }

    public String getLocPostcode() {
        return this.locPostcode;
    }
}
