package com.gumtree.android.common.http;

import com.ebay.classifieds.capi.CapiTracker;

public class TrackingInfo implements CapiTracker {
    private String uID;
    private String userAgent;
    private String userUID;
    private String version;

    public String getUID() {
        return this.uID;
    }

    public String getUserUID() {
        return this.userUID;
    }

    public void setUserUID(String str) {
        this.userUID = str;
    }

    public String getVersion() {
        return this.version;
    }

    public void setVersion(String str) {
        this.version = str;
    }

    public String getUserAgent() {
        return this.userAgent;
    }

    public void setUserAgent(String str) {
        this.userAgent = str;
    }

    public boolean isUserUIDValid() {
        return this.userUID != null && this.userUID.length() > 0;
    }

    public void setuID(String str) {
        this.uID = str;
    }

    public String toString() {
        return "TrackingInfo{uID='" + this.uID + '\'' + ", userUID='" + this.userUID + '\'' + ", userAgent='" + this.userAgent + '\'' + ", version='" + this.version + '\'' + '}';
    }
}
