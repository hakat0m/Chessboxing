package com.gumtree.android.ad.treebay;

import com.gumtree.android.common.location.AppLocation;

public class TreebayCustomDimensionData {
    private String categoryId;
    private String categoryName;
    private AppLocation location;
    private String locationId;
    private String locationName;
    private int page;
    private String searchQuery;

    public class TreebayCustomDimensionDataBuilder {
        private String categoryId;
        private String categoryName;
        private AppLocation location;
        private String locationId;
        private String locationName;
        private int page;
        private String searchQuery;

        TreebayCustomDimensionDataBuilder() {
        }

        public TreebayCustomDimensionData build() {
            return new TreebayCustomDimensionData(this.locationId, this.locationName, this.categoryId, this.categoryName, this.page, this.searchQuery, this.location);
        }

        public TreebayCustomDimensionDataBuilder categoryId(String str) {
            this.categoryId = str;
            return this;
        }

        public TreebayCustomDimensionDataBuilder categoryName(String str) {
            this.categoryName = str;
            return this;
        }

        public TreebayCustomDimensionDataBuilder location(AppLocation appLocation) {
            this.location = appLocation;
            return this;
        }

        public TreebayCustomDimensionDataBuilder locationId(String str) {
            this.locationId = str;
            return this;
        }

        public TreebayCustomDimensionDataBuilder locationName(String str) {
            this.locationName = str;
            return this;
        }

        public TreebayCustomDimensionDataBuilder page(int i) {
            this.page = i;
            return this;
        }

        public TreebayCustomDimensionDataBuilder searchQuery(String str) {
            this.searchQuery = str;
            return this;
        }

        public String toString() {
            return "TreebayCustomDimensionData.TreebayCustomDimensionDataBuilder(locationId=" + this.locationId + ", locationName=" + this.locationName + ", categoryId=" + this.categoryId + ", categoryName=" + this.categoryName + ", page=" + this.page + ", searchQuery=" + this.searchQuery + ", location=" + this.location + ")";
        }
    }

    protected boolean canEqual(Object obj) {
        return obj instanceof TreebayCustomDimensionData;
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof TreebayCustomDimensionData)) {
            return false;
        }
        TreebayCustomDimensionData treebayCustomDimensionData = (TreebayCustomDimensionData) obj;
        if (!treebayCustomDimensionData.canEqual(this)) {
            return false;
        }
        String locationId = getLocationId();
        String locationId2 = treebayCustomDimensionData.getLocationId();
        if (locationId != null ? !locationId.equals(locationId2) : locationId2 != null) {
            return false;
        }
        locationId = getLocationName();
        locationId2 = treebayCustomDimensionData.getLocationName();
        if (locationId != null ? !locationId.equals(locationId2) : locationId2 != null) {
            return false;
        }
        locationId = getCategoryId();
        locationId2 = treebayCustomDimensionData.getCategoryId();
        if (locationId != null ? !locationId.equals(locationId2) : locationId2 != null) {
            return false;
        }
        locationId = getCategoryName();
        locationId2 = treebayCustomDimensionData.getCategoryName();
        if (locationId != null ? !locationId.equals(locationId2) : locationId2 != null) {
            return false;
        }
        if (getPage() != treebayCustomDimensionData.getPage()) {
            return false;
        }
        locationId = getSearchQuery();
        locationId2 = treebayCustomDimensionData.getSearchQuery();
        if (locationId != null ? !locationId.equals(locationId2) : locationId2 != null) {
            return false;
        }
        AppLocation location = getLocation();
        AppLocation location2 = treebayCustomDimensionData.getLocation();
        if (location == null) {
            if (location2 == null) {
                return true;
            }
        } else if (location.equals(location2)) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        int i = 43;
        String locationId = getLocationId();
        int hashCode = (locationId == null ? 43 : locationId.hashCode()) + 59;
        String locationName = getLocationName();
        hashCode = (locationName == null ? 43 : locationName.hashCode()) + (hashCode * 59);
        locationName = getCategoryId();
        hashCode = (locationName == null ? 43 : locationName.hashCode()) + (hashCode * 59);
        locationName = getCategoryName();
        hashCode = (((locationName == null ? 43 : locationName.hashCode()) + (hashCode * 59)) * 59) + getPage();
        locationName = getSearchQuery();
        hashCode = (locationName == null ? 43 : locationName.hashCode()) + (hashCode * 59);
        AppLocation location = getLocation();
        hashCode *= 59;
        if (location != null) {
            i = location.hashCode();
        }
        return hashCode + i;
    }

    public void setCategoryId(String str) {
        this.categoryId = str;
    }

    public void setCategoryName(String str) {
        this.categoryName = str;
    }

    public void setLocation(AppLocation appLocation) {
        this.location = appLocation;
    }

    public void setLocationId(String str) {
        this.locationId = str;
    }

    public void setLocationName(String str) {
        this.locationName = str;
    }

    public void setPage(int i) {
        this.page = i;
    }

    public void setSearchQuery(String str) {
        this.searchQuery = str;
    }

    public String toString() {
        return "TreebayCustomDimensionData(locationId=" + getLocationId() + ", locationName=" + getLocationName() + ", categoryId=" + getCategoryId() + ", categoryName=" + getCategoryName() + ", page=" + getPage() + ", searchQuery=" + getSearchQuery() + ", location=" + getLocation() + ")";
    }

    TreebayCustomDimensionData(String str, String str2, String str3, String str4, int i, String str5, AppLocation appLocation) {
        this.locationId = str;
        this.locationName = str2;
        this.categoryId = str3;
        this.categoryName = str4;
        this.page = i;
        this.searchQuery = str5;
        this.location = appLocation;
    }

    public static TreebayCustomDimensionDataBuilder builder() {
        return new TreebayCustomDimensionDataBuilder();
    }

    public String getLocationId() {
        return this.locationId;
    }

    public String getLocationName() {
        return this.locationName;
    }

    public String getCategoryId() {
        return this.categoryId;
    }

    public String getCategoryName() {
        return this.categoryName;
    }

    public int getPage() {
        return this.page;
    }

    public String getSearchQuery() {
        return this.searchQuery;
    }

    public AppLocation getLocation() {
        return this.location;
    }
}
