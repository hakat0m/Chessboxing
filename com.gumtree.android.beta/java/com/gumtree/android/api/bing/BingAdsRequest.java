package com.gumtree.android.api.bing;

import com.apptentive.android.sdk.BuildConfig;
import java.util.HashMap;
import java.util.Map;

public class BingAdsRequest {
    private static final String AD_UNIT_ID_KEY = "adUnitId";
    private static final String APP_ID_KEY = "appid";
    private static final String FORM_KEY = "form";
    private static final String FORM_VALUE = "monitr";
    private static final String MAIN_LINE_COUNT_KEY = "mainlineCount";
    private static final String MARKET_KEY = "mkt";
    private static final String MARKET_VALUE = "en-GB";
    private static final String PAGE_NUMBER_KEY = "pageNumber";
    private static final String PROPERTY_ID_KEY = "propertyId";
    private static final String QUERY_KEY = "q";
    private static final String SAFE_SEARCH = "strict";
    private static final String SAFE_SEARCH_KEY = "safeSearch";
    private static final String SIDE_BAR_COUNT_KEY = "sidebarCount";
    private static final String SIDE_BAR_VALUE = "0";
    private static final String TRACING_TAG_PARAMETER_KEY = "tracingTag";
    private static final String TRAFFIC_TYPE_KEY = "trafficType";
    private static final String TRAFFIC_TYPE_VALUE = "test";
    private Map<String, String> map = new HashMap();
    private int pageNumber = 0;

    public BingAdsRequest(String str, String str2, String str3, String str4, String str5, String str6, boolean z) {
        this.map.put(APP_ID_KEY, str);
        this.map.put(PROPERTY_ID_KEY, str2);
        this.map.put(AD_UNIT_ID_KEY, str3);
        if (str6 != null && str6.trim().length() > 0) {
            this.map.put(QUERY_KEY, str6.trim());
        }
        this.map.put(MAIN_LINE_COUNT_KEY, str4);
        this.map.put(SIDE_BAR_COUNT_KEY, SIDE_BAR_VALUE);
        this.map.put(SAFE_SEARCH_KEY, SAFE_SEARCH);
        this.map.put(MARKET_KEY, MARKET_VALUE);
        this.map.put(PAGE_NUMBER_KEY, BuildConfig.FLAVOR + this.pageNumber);
        if (z) {
            this.map.put(TRAFFIC_TYPE_KEY, TRAFFIC_TYPE_VALUE);
            this.map.put(FORM_KEY, FORM_VALUE);
        }
        this.map.put(TRACING_TAG_PARAMETER_KEY, str5.replaceAll(" ", "_"));
    }

    public Map<String, String> getMap() {
        return this.map;
    }

    public void nextPage() {
        this.pageNumber++;
        this.map.put(PAGE_NUMBER_KEY, BuildConfig.FLAVOR + this.pageNumber);
    }
}
