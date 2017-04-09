package com.gumtree.android.common.search;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import com.apptentive.android.sdk.BuildConfig;
import com.gumtree.Log;
import com.gumtree.android.GumtreeApplication;
import com.gumtree.android.common.activities.StatefulActivity;
import com.gumtree.android.common.location.AppLocation;
import com.gumtree.android.common.location.GeocoderLocation;
import com.gumtree.android.common.location.GumtreeLocation;
import com.gumtree.android.common.location.GumtreePostcodeLocation;
import com.gumtree.android.common.location.PreferenceBasedRadiusDAO;
import com.gumtree.android.common.location.RadiusDAO;
import com.gumtree.android.logging.Timber;
import com.gumtree.android.model.Ads;
import com.gumtree.android.postad.services.converter.PaymentConverter;
import java.io.Serializable;
import java.util.Set;

public final class Search implements Serializable {
    public static final String SEARCH_DATE_DESCENDING = "DATE_DESCENDING";
    public static final String SEARCH_DISTANCE_ASCENDING = "DISTANCE_ASCENDING";
    public static final String SEARCH_PRICE_ASCENDING = "PRICE_ASCENDING";
    public static final String SEARCH_PRICE_DESCENDING = "PRICE_DESCENDING";
    public static final String SORT_TYPE = "sortType";
    private final Intent searchIntent;

    public enum SearchType {
        VIEW,
        SEARCH
    }

    private Search(Context context) {
        this(context, "android.intent.action.VIEW");
    }

    private Search(Context context, String str) {
        this(context, new Intent(str, Ads.URI));
    }

    private Search(Context context, Intent intent) {
        this.searchIntent = intent;
        this.searchIntent.setPackage(context.getPackageName());
    }

    private Search(Context context, Bundle bundle) {
        this(context);
        initIntentFromBundle(bundle);
    }

    public static Search create(Context context) {
        return new Search(context);
    }

    public static Search create(Context context, SearchType searchType) {
        switch (1.$SwitchMap$com$gumtree$android$common$search$Search$SearchType[searchType.ordinal()]) {
            case HighlightView.GROW_NONE /*1*/:
                return new Search(context, "android.intent.action.SEARCH");
            default:
                return new Search(context);
        }
    }

    public static Search create(Context context, Intent intent) {
        if (intent == null) {
            return getDefaultSearch(context);
        }
        return new Search(context, intent);
    }

    public static Search create(Context context, Bundle bundle) {
        return new Search(context, bundle);
    }

    public static void invoke(Activity activity, Search search) {
        activity.startActivity(search.searchIntent);
        activity.overridePendingTransition(0, 0);
    }

    private static Search getDefaultSearch(Context context) {
        AppLocation globalBuyerLocation = ((GumtreeApplication) context.getApplicationContext()).getGlobalBuyerLocation();
        Search create = create(context, SearchType.SEARCH);
        create.addCategory(PaymentConverter.DEFAULT_PAYMENT_METHOD_ID, context.getString(2131165816));
        create.addLocation(globalBuyerLocation);
        create.addRadius(context, PreferenceBasedRadiusDAO.get(context), globalBuyerLocation);
        return create;
    }

    private void initIntentFromBundle(Bundle bundle) {
        Set keySet = bundle.keySet();
        for (String str : (String[]) keySet.toArray(new String[keySet.size()])) {
            try {
                if (StatefulActivity.EXTRA_SESSION_TIMESTAMP.equals(str)) {
                    addParameter(str, bundle.getLong(str));
                } else {
                    addParameter(str, bundle.getString(str));
                }
            } catch (ClassCastException e) {
                e.printStackTrace();
            }
        }
    }

    public Search addParameter(String str, String str2) {
        Timber.d("addParameter key=%s, value=%s", str, str2);
        this.searchIntent.putExtra(str, str2);
        return this;
    }

    private Search addParameter(String str, long j) {
        this.searchIntent.putExtra(str, j);
        return this;
    }

    public long getLongValue(String str) {
        return this.searchIntent.getLongExtra(str, System.currentTimeMillis());
    }

    public String getStringValue(String str) {
        return this.searchIntent.getStringExtra(str);
    }

    public long getTimestamp() {
        return getLongValue(StatefulActivity.EXTRA_SESSION_TIMESTAMP);
    }

    public String getCategoryId() {
        return getStringValue(StatefulActivity.NAME_CATEGORY_ID);
    }

    public String getCategoryName() {
        return getStringValue(StatefulActivity.EXTRA_CATEGORY_NAME);
    }

    public String getLocationName() {
        return getStringValue(StatefulActivity.EXTRA_LOCATION_NAME);
    }

    public String getLocationId() {
        return getStringValue(StatefulActivity.NAME_LOCATION_ID);
    }

    public String getQuery() {
        return getStringValue(StatefulActivity.NAME_QUERY);
    }

    public String getRadius() {
        return getStringValue(StatefulActivity.NAME_DISTANCE);
    }

    public Bundle asBundle() {
        return this.searchIntent.getExtras();
    }

    public void setType(SearchType searchType) {
        switch (1.$SwitchMap$com$gumtree$android$common$search$Search$SearchType[searchType.ordinal()]) {
            case HighlightView.GROW_NONE /*1*/:
                this.searchIntent.setAction("android.intent.action.SEARCH");
                return;
            default:
                this.searchIntent.setAction("android.intent.action.VIEW");
                return;
        }
    }

    public void addLocation(AppLocation appLocation) {
        addParameter(StatefulActivity.EXTRA_LOCATION_NAME, appLocation.getDisplayText(false));
        switch (1.$SwitchMap$com$gumtree$android$common$location$AppLocation$LocationType[appLocation.getType().ordinal()]) {
            case HighlightView.GROW_NONE /*1*/:
                if (appLocation instanceof GumtreePostcodeLocation) {
                    addParameter(StatefulActivity.NAME_ZIPCODE, appLocation.getName());
                    return;
                } else {
                    addParameter(StatefulActivity.NAME_LOCATION_ID, ((GumtreeLocation) appLocation).getLocationId());
                    return;
                }
            case HighlightView.GROW_LEFT_EDGE /*2*/:
                addParameter(StatefulActivity.NAME_LATITUDE, ((GeocoderLocation) appLocation).getLat() + BuildConfig.FLAVOR);
                addParameter(StatefulActivity.NAME_LONGITUDE, ((GeocoderLocation) appLocation).getLng() + BuildConfig.FLAVOR);
                addParameter(StatefulActivity.NAME_ZIPCODE, ((GeocoderLocation) appLocation).getPostCode());
                return;
            default:
                Log.e(getClass().getSimpleName(), "case not supported " + appLocation.getType());
                return;
        }
    }

    public void addRadius(Context context, RadiusDAO radiusDAO, AppLocation appLocation) {
        addParameter(StatefulActivity.NAME_DISTANCE, radiusDAO.getRadius(appLocation) + BuildConfig.FLAVOR);
    }

    public void addCategory(String str, String str2) {
        addParameter(StatefulActivity.NAME_CATEGORY_ID, str);
        addParameter(StatefulActivity.EXTRA_CATEGORY_NAME, str2);
    }

    public Intent getSearchIntent() {
        return this.searchIntent;
    }
}
