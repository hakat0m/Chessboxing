package com.gumtree.android;

import com.apptentive.android.sdk.BuildConfig;
import java.util.HashMap;

public class UrlMatcher {
    private static final String ADS = "ads";
    private static final String ADS_JSON = "/ads.json";
    private static final String API_USERS = "/api/users/";
    public static final String AUTHORITY = "com.gumtree.android.beta";
    private static final String FULL_STOP = ".";
    private static final String JSON = "json";
    public static final String NO_MATCH = "-1";
    private static final String QUERY = "?";
    private static final String SEPARATOR = "/";
    public static final int URI_CODE_AD = 5;
    public static final int URI_CODE_ADS = 4;
    public static final int URI_CODE_ADS_VIEW = 6;
    public static final int URI_CODE_ATTR_VIP_DISPLAY = 109;
    public static final int URI_CODE_CATEGORIES = 0;
    public static final int URI_CODE_CATEGORY = 1;
    public static final int URI_CODE_CONFIGURATIONS = 210;
    public static final int URI_CODE_CONVERSATIONS = 503;
    public static final int URI_CODE_CONVERSATIONS_VIEW = 504;
    public static final int URI_CODE_FEATURES = 511;
    public static final int URI_CODE_LOCATION = 3;
    public static final int URI_CODE_LOCATIONS = 2;
    public static final int URI_CODE_MANAGED_AD = 203;
    public static final int URI_CODE_MANAGED_ADS = 201;
    public static final int URI_CODE_MANAGED_ADS_VIEW = 202;
    public static final int URI_CODE_MESSAGES = 505;
    public static final int URI_CODE_NEW_SAVED_SEARCHES = 502;
    public static final int URI_CODE_POST_AD = 199;
    public static final int URI_CODE_POST_ADS_IMAGES = 200;
    public static final int URI_CODE_POST_METADATA = 28;
    public static final int URI_CODE_SAVED_ADS_VIEW = 7;
    public static final int URI_CODE_SAVED_SEARCHES = 500;
    public static final int URI_CODE_SAVED_SEARCHES_VIEW = 501;
    public static final int URI_CODE_SEARCH_METADATA = 26;
    public static final int URI_CODE_SERVICE_CALL_REGISTRY = 99;
    public String baseUrl;
    private final HashMap<String, Integer> map = new HashMap();

    public UrlMatcher(String str) {
        this.baseUrl = str;
        this.map.put(NO_MATCH, Integer.valueOf(-1));
    }

    public void addUrl(String str, Integer num) {
        this.map.put(str, num);
    }

    public int getUriCode(String str) {
        String replace = str.replace(this.baseUrl, BuildConfig.FLAVOR);
        int indexOf = replace.indexOf(QUERY);
        if (indexOf > 0) {
            replace = replace.substring(URI_CODE_CATEGORIES, indexOf);
        }
        replace = replace.replace(".json", BuildConfig.FLAVOR);
        StringBuilder stringBuilder = new StringBuilder(this.baseUrl);
        String[] split = replace.split(SEPARATOR);
        int length = split.length;
        for (int i = URI_CODE_CATEGORIES; i < length; i += URI_CODE_CATEGORY) {
            String str2 = split[i];
            if (str2.trim().length() > 0) {
                if (isInteger(str2)) {
                    stringBuilder.append("/%s");
                } else {
                    stringBuilder.append(SEPARATOR).append(str2);
                }
            }
        }
        stringBuilder.append(".json");
        Integer num = (Integer) this.map.get(stringBuilder.toString());
        if (num == null) {
            num = (Integer) this.map.get(NO_MATCH);
        }
        return num.intValue();
    }

    public String getIdFromUrl(String str) {
        int lastIndexOf = str.lastIndexOf(SEPARATOR);
        if (lastIndexOf == -1) {
            throw new IllegalStateException("Url format is not valid for requested url" + str);
        }
        int i = lastIndexOf + URI_CODE_CATEGORY;
        lastIndexOf = str.lastIndexOf(".json");
        if (lastIndexOf == -1) {
            lastIndexOf = str.lastIndexOf(QUERY);
        }
        if (lastIndexOf == -1) {
            lastIndexOf = str.length();
        }
        String substring = str.substring(i, lastIndexOf);
        try {
            Long.parseLong(substring);
            return substring;
        } catch (NumberFormatException e) {
            throw new IllegalStateException("This is not a valid id " + substring + " for requested url " + str);
        }
    }

    private boolean isInteger(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public boolean matchManageAdsList(String str) {
        if (str == null || str.length() == 0 || !str.contains(API_USERS) || !str.contains(ADS_JSON)) {
            return false;
        }
        return true;
    }

    public boolean matchManageAd(String str) {
        if (str == null || str.length() == 0 || !str.contains(API_USERS) || str.contains(ADS_JSON)) {
            return false;
        }
        if (str.contains("/ads/") && str.contains("/.json")) {
            return true;
        }
        return true;
    }
}
