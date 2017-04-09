package com.gumtree.android.sellersotheritems.models;

import java.io.Serializable;

public final class SellerData implements Serializable {
    private static final long serialVersionUID = -8189208931727618930L;
    private final String id;
    private final int numAds;
    private final String postingSince;
    private final String userName;

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof SellerData)) {
            return false;
        }
        SellerData sellerData = (SellerData) obj;
        String id = getId();
        String id2 = sellerData.getId();
        if (id != null ? !id.equals(id2) : id2 != null) {
            return false;
        }
        id = getUserName();
        id2 = sellerData.getUserName();
        if (id != null ? !id.equals(id2) : id2 != null) {
            return false;
        }
        if (getNumAds() != sellerData.getNumAds()) {
            return false;
        }
        id = getPostingSince();
        id2 = sellerData.getPostingSince();
        if (id == null) {
            if (id2 == null) {
                return true;
            }
        } else if (id.equals(id2)) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        int i = 43;
        String id = getId();
        int hashCode = (id == null ? 43 : id.hashCode()) + 59;
        String userName = getUserName();
        hashCode = (((userName == null ? 43 : userName.hashCode()) + (hashCode * 59)) * 59) + getNumAds();
        userName = getPostingSince();
        hashCode *= 59;
        if (userName != null) {
            i = userName.hashCode();
        }
        return hashCode + i;
    }

    public String toString() {
        return "SellerData(id=" + getId() + ", userName=" + getUserName() + ", numAds=" + getNumAds() + ", postingSince=" + getPostingSince() + ")";
    }

    SellerData(String str, String str2, int i, String str3) {
        this.id = str;
        this.userName = str2;
        this.numAds = i;
        this.postingSince = str3;
    }

    public static SellerDataBuilder builder() {
        return new SellerDataBuilder();
    }

    public String getId() {
        return this.id;
    }

    public String getUserName() {
        return this.userName;
    }

    public int getNumAds() {
        return this.numAds;
    }

    public String getPostingSince() {
        return this.postingSince;
    }
}
