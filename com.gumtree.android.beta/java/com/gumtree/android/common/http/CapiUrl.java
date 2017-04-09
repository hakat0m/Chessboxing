package com.gumtree.android.common.http;

public final class CapiUrl {
    public static final String EDIT_AD = "users/%s/ads/%s";
    public static final String LOCATION_URL_DEPTH = "locations/%s.json";
    public static final String MANAGE_AD = "users/%s/ads/%s.json?_in=title,description,email,phone,poster-contact-email,price-frequency,neighborhood,category,locations,price,pictures,attributes,visible-on-map,poster-contact-name,features-active";
    public static final String METADATA_POST = "ads/metadata/%s.json";
    public static final String METADATA_POST_LIMIT = "ads/metadata/%s.json?_in=description";
    public static final String POST_AD = "users/%s/ads";
    public static final String SUGGESTION_ADS = "suggestions/ads.json";

    private CapiUrl() {
    }

    public static String getUrl(String str, Object... objArr) {
        return String.format(str, objArr);
    }
}
