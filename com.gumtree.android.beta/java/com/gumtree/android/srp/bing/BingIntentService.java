package com.gumtree.android.srp.bing;

import android.app.IntentService;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.text.TextUtils;
import com.apptentive.android.sdk.BuildConfig;
import com.ebay.classifieds.capi.CapiConfig;
import com.gumtree.DevelopmentFlags;
import com.gumtree.Log;
import com.gumtree.android.GumtreeApplication;
import com.gumtree.android.api.bing.BingAdsApi;
import com.gumtree.android.api.bing.BingAdsImpressionRequest;
import com.gumtree.android.api.bing.BingAdsRequest;
import com.gumtree.android.common.location.AppLocation;
import com.gumtree.android.common.location.AppLocation.LocationType;
import com.gumtree.android.common.location.GumtreeLocation;
import com.gumtree.android.model.Categories;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public abstract class BingIntentService extends IntentService {
    protected static final String ALL_CATEGORIES = "all";
    protected static final String DEFAULT_LOCATION = "uk";
    private static final int FOUR = 4;
    private static final String SEMICOLON = ";";
    private static final String SEPARATOR = "/";
    private static final String SPACE = " ";

    public interface Operation {
        boolean execute();
    }

    public BingIntentService(String str) {
        super(str);
    }

    protected static boolean isBingEnabled() {
        return DevelopmentFlags.getInstance().isBingAdsSupportEnabled();
    }

    protected boolean sendImpression(BingAdsApi bingAdsApi, BingAdsImpressionRequest bingAdsImpressionRequest) {
        if (bingAdsImpressionRequest == null || TextUtils.isEmpty(bingAdsImpressionRequest.getRguid())) {
            return true;
        }
        try {
            bingAdsApi.impressions(bingAdsImpressionRequest.getRguid(), getString(2131165364), bingAdsImpressionRequest);
            return true;
        } catch (Throwable e) {
            Log.w(getClass().getSimpleName(), "Error sending impressions " + e.getMessage(), e);
            return false;
        }
    }

    @NonNull
    protected BingAdsRequest buildBingAdsRequest(@NonNull String str, @NonNull String str2) {
        return new BingAdsRequest(getString(2131165364), getString(2131165366), getString(2131165363), getString(2131165365), str, str2, isBingInTestMode());
    }

    private boolean isBingInTestMode() {
        return GumtreeApplication.isDebug() || getResources().getBoolean(2131427349);
    }

    @Nullable
    private String readCategoryValueFromDatabase(String str, String str2) {
        Cursor query;
        Throwable e;
        try {
            query = getContentResolver().query(Categories.URI, null, "_id=?", new String[]{str}, null);
            try {
                if (query.moveToFirst()) {
                    String string = query.getString(query.getColumnIndex(str2));
                    if (query == null) {
                        return string;
                    }
                    try {
                        query.close();
                        return string;
                    } catch (Throwable e2) {
                        Log.w(getClass().getSimpleName(), "Error closing cursor: " + e2.getMessage(), e2);
                        return string;
                    }
                }
                if (query != null) {
                    try {
                        query.close();
                    } catch (Throwable e3) {
                        Log.w(getClass().getSimpleName(), "Error closing cursor: " + e3.getMessage(), e3);
                    }
                }
                return null;
            } catch (RuntimeException e4) {
                e3 = e4;
                try {
                    Log.w(getClass().getSimpleName(), "Error Reading category information: " + e3.getMessage(), e3);
                    if (query != null) {
                        try {
                            query.close();
                        } catch (Throwable e32) {
                            Log.w(getClass().getSimpleName(), "Error closing cursor: " + e32.getMessage(), e32);
                        }
                    }
                    return null;
                } catch (Throwable th) {
                    e32 = th;
                    if (query != null) {
                        try {
                            query.close();
                        } catch (Throwable e22) {
                            Log.w(getClass().getSimpleName(), "Error closing cursor: " + e22.getMessage(), e22);
                        }
                    }
                    throw e32;
                }
            }
        } catch (RuntimeException e5) {
            e32 = e5;
            query = null;
            Log.w(getClass().getSimpleName(), "Error Reading category information: " + e32.getMessage(), e32);
            if (query != null) {
                query.close();
            }
            return null;
        } catch (Throwable th2) {
            e32 = th2;
            query = null;
            if (query != null) {
                query.close();
            }
            throw e32;
        }
    }

    @Nullable
    protected String getCategories(String str) {
        return readCategoryValueFromDatabase(str, "path_names");
    }

    @NonNull
    private String getCategoryRawValue(String str) {
        String readCategoryValueFromDatabase = readCategoryValueFromDatabase(str, "value_raw");
        if (TextUtils.isEmpty(readCategoryValueFromDatabase)) {
            return ALL_CATEGORIES;
        }
        return readCategoryValueFromDatabase;
    }

    @NonNull
    protected String getReferer(CapiConfig capiConfig, String str, @NonNull String str2) {
        String categoryRawValue = getCategoryRawValue(str);
        String locationId = getLocationId();
        return capiConfig.getBingEndPoint() + SEPARATOR + categoryRawValue.toLowerCase() + SEPARATOR + locationId + SEPARATOR + getEncodedQuery(str2);
    }

    private String getEncodedQuery(String str) {
        try {
            return URLEncoder.encode(str, "utf-8").toLowerCase();
        } catch (UnsupportedEncodingException e) {
            Log.w(getClass().getSimpleName(), "Problem encoding query : " + e.getMessage());
            return BuildConfig.FLAVOR;
        }
    }

    @VisibleForTesting
    public String getLevel1(String str) {
        if (str == null) {
            return null;
        }
        String[] split = str.split(SEMICOLON);
        if (split.length == 0) {
            return null;
        }
        if (split.length == 1) {
            return ALL_CATEGORIES;
        }
        return split[1].replace(SPACE, BuildConfig.FLAVOR);
    }

    @VisibleForTesting
    public String getAllLevels(String str) {
        int i = 1;
        if (str == null) {
            return null;
        }
        String[] split = str.split(SEMICOLON);
        if (split.length == 0) {
            return null;
        }
        if (split.length == 1) {
            return ALL_CATEGORIES;
        }
        StringBuilder stringBuilder = new StringBuilder();
        while (i < Math.min(split.length, FOUR)) {
            stringBuilder.append(split[i]).append(SPACE);
            i++;
        }
        return stringBuilder.toString().trim();
    }

    protected void retry(Operation operation) {
        int integer = getResources().getInteger(2131296293);
        int integer2 = getResources().getInteger(2131296294);
        for (int i = 0; i < integer && !operation.execute(); i++) {
            sleepForNextRetry(integer2);
        }
    }

    private void sleepForNextRetry(int i) {
        try {
            Thread.sleep((long) i);
        } catch (InterruptedException e) {
        }
    }

    public String getLocationId() {
        AppLocation globalBuyerLocation = ((GumtreeApplication) getApplicationContext()).getGlobalBuyerLocation();
        if (globalBuyerLocation == null || globalBuyerLocation.getType() != LocationType.GumtreeLocation) {
            return DEFAULT_LOCATION;
        }
        return ((GumtreeLocation) globalBuyerLocation).getIdName();
    }
}
