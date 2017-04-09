package com.gumtree.android.deeplinking;

public final class SearchParameters {
    private String categoryId;
    private String categoryName;
    private String locationId;
    private String locationName;
    private String query;

    private SearchParameters() {
    }

    public static Builder builder() {
        return new Builder(null);
    }

    public String getCategoryId() {
        return this.categoryId;
    }

    public String getCategoryName() {
        return this.categoryName;
    }

    public String getLocationId() {
        return this.locationId;
    }

    public String getLocationName() {
        return this.locationName;
    }

    public String getQuery() {
        return this.query;
    }
}
