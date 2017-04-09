package com.gumtree.android.postad;

import java.beans.ConstructorProperties;

public final class DraftLocation {
    private final String breadCrumb;
    private final long id;
    private final boolean leaf;
    private final String localisedName;
    private final String name;
    private final long parentId;

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof DraftLocation)) {
            return false;
        }
        DraftLocation draftLocation = (DraftLocation) obj;
        if (getId() != draftLocation.getId()) {
            return false;
        }
        String name = getName();
        String name2 = draftLocation.getName();
        if (name != null ? !name.equals(name2) : name2 != null) {
            return false;
        }
        name = getLocalisedName();
        name2 = draftLocation.getLocalisedName();
        if (name != null ? !name.equals(name2) : name2 != null) {
            return false;
        }
        if (isLeaf() != draftLocation.isLeaf()) {
            return false;
        }
        if (getParentId() != draftLocation.getParentId()) {
            return false;
        }
        name = getBreadCrumb();
        name2 = draftLocation.getBreadCrumb();
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
        name = getLocalisedName();
        i2 = (isLeaf() ? 79 : 97) + (((name == null ? 43 : name.hashCode()) + (i2 * 59)) * 59);
        id = getParentId();
        i2 = (i2 * 59) + ((int) (id ^ (id >>> 32)));
        name = getBreadCrumb();
        i2 *= 59;
        if (name != null) {
            i = name.hashCode();
        }
        return i2 + i;
    }

    public String toString() {
        return "DraftLocation(id=" + getId() + ", name=" + getName() + ", localisedName=" + getLocalisedName() + ", leaf=" + isLeaf() + ", parentId=" + getParentId() + ", breadCrumb=" + getBreadCrumb() + ")";
    }

    public static DraftLocationBuilder builder() {
        return new DraftLocationBuilder();
    }

    @ConstructorProperties({"id", "name", "localisedName", "leaf", "parentId", "breadCrumb"})
    public DraftLocation(long j, String str, String str2, boolean z, long j2, String str3) {
        this.id = j;
        this.name = str;
        this.localisedName = str2;
        this.leaf = z;
        this.parentId = j2;
        this.breadCrumb = str3;
    }

    public long getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getLocalisedName() {
        return this.localisedName;
    }

    public boolean isLeaf() {
        return this.leaf;
    }

    public long getParentId() {
        return this.parentId;
    }

    public String getBreadCrumb() {
        return this.breadCrumb;
    }
}
