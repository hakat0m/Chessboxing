package com.gumtree.android.location.model;

import java.beans.ConstructorProperties;
import java.io.Serializable;

public class LocationData implements Serializable {
    private static final long serialVersionUID = 7166621096296948484L;
    private String breadCrumb;
    private String displayName;
    private long id;
    private boolean leaf;
    private String localizedName;
    private String name;
    private String neighborhood;
    private long parentId;
    private String type;
    private String zipCode;

    @ConstructorProperties({"id", "name", "localizedName", "leaf", "type", "parentId", "zipCode", "breadCrumb", "neighborhood", "displayName"})
    public LocationData(long j, String str, String str2, boolean z, String str3, long j2, String str4, String str5, String str6, String str7) {
        this.id = j;
        this.name = str;
        this.localizedName = str2;
        this.leaf = z;
        this.type = str3;
        this.parentId = j2;
        this.zipCode = str4;
        this.breadCrumb = str5;
        this.neighborhood = str6;
        this.displayName = str7;
    }

    protected boolean canEqual(Object obj) {
        return obj instanceof LocationData;
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof LocationData)) {
            return false;
        }
        LocationData locationData = (LocationData) obj;
        if (!locationData.canEqual(this)) {
            return false;
        }
        if (getId() != locationData.getId()) {
            return false;
        }
        String name = getName();
        String name2 = locationData.getName();
        if (name != null ? !name.equals(name2) : name2 != null) {
            return false;
        }
        name = getLocalizedName();
        name2 = locationData.getLocalizedName();
        if (name != null ? !name.equals(name2) : name2 != null) {
            return false;
        }
        if (isLeaf() != locationData.isLeaf()) {
            return false;
        }
        name = getType();
        name2 = locationData.getType();
        if (name != null ? !name.equals(name2) : name2 != null) {
            return false;
        }
        if (getParentId() != locationData.getParentId()) {
            return false;
        }
        name = getZipCode();
        name2 = locationData.getZipCode();
        if (name != null ? !name.equals(name2) : name2 != null) {
            return false;
        }
        name = getBreadCrumb();
        name2 = locationData.getBreadCrumb();
        if (name != null ? !name.equals(name2) : name2 != null) {
            return false;
        }
        name = getNeighborhood();
        name2 = locationData.getNeighborhood();
        if (name != null ? !name.equals(name2) : name2 != null) {
            return false;
        }
        name = getDisplayName();
        name2 = locationData.getDisplayName();
        if (name == null) {
            if (name2 == null) {
                return true;
            }
        } else if (name.equals(name2)) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        int i = 43;
        long id = getId();
        int i2 = ((int) (id ^ (id >>> 32))) + 59;
        String name = getName();
        i2 = (name == null ? 43 : name.hashCode()) + (i2 * 59);
        name = getLocalizedName();
        i2 = (isLeaf() ? 79 : 97) + (((name == null ? 43 : name.hashCode()) + (i2 * 59)) * 59);
        name = getType();
        i2 = (name == null ? 43 : name.hashCode()) + (i2 * 59);
        id = getParentId();
        i2 = (i2 * 59) + ((int) (id ^ (id >>> 32)));
        name = getZipCode();
        i2 = (name == null ? 43 : name.hashCode()) + (i2 * 59);
        name = getBreadCrumb();
        i2 = (name == null ? 43 : name.hashCode()) + (i2 * 59);
        name = getNeighborhood();
        i2 = (name == null ? 43 : name.hashCode()) + (i2 * 59);
        name = getDisplayName();
        i2 *= 59;
        if (name != null) {
            i = name.hashCode();
        }
        return i2 + i;
    }

    public void setBreadCrumb(String str) {
        this.breadCrumb = str;
    }

    public void setDisplayName(String str) {
        this.displayName = str;
    }

    public void setId(long j) {
        this.id = j;
    }

    public void setLeaf(boolean z) {
        this.leaf = z;
    }

    public void setLocalizedName(String str) {
        this.localizedName = str;
    }

    public void setName(String str) {
        this.name = str;
    }

    public void setNeighborhood(String str) {
        this.neighborhood = str;
    }

    public void setParentId(long j) {
        this.parentId = j;
    }

    public void setType(String str) {
        this.type = str;
    }

    public void setZipCode(String str) {
        this.zipCode = str;
    }

    public String toString() {
        return "LocationData(id=" + getId() + ", name=" + getName() + ", localizedName=" + getLocalizedName() + ", leaf=" + isLeaf() + ", type=" + getType() + ", parentId=" + getParentId() + ", zipCode=" + getZipCode() + ", breadCrumb=" + getBreadCrumb() + ", neighborhood=" + getNeighborhood() + ", displayName=" + getDisplayName() + ")";
    }

    public static LocationDataBuilder builder() {
        return new LocationDataBuilder();
    }

    public long getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getLocalizedName() {
        return this.localizedName;
    }

    public boolean isLeaf() {
        return this.leaf;
    }

    public String getType() {
        return this.type;
    }

    public long getParentId() {
        return this.parentId;
    }

    public String getZipCode() {
        return this.zipCode;
    }

    public String getBreadCrumb() {
        return this.breadCrumb;
    }

    public String getNeighborhood() {
        return this.neighborhood;
    }

    public String getDisplayName() {
        return this.displayName;
    }
}
