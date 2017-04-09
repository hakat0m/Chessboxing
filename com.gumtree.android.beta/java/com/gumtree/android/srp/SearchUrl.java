package com.gumtree.android.srp;

import android.content.Intent;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.preference.PreferenceManager;
import com.apptentive.android.sdk.BuildConfig;
import com.gumtree.android.GumtreeApplication;
import com.gumtree.android.common.Constants;
import com.gumtree.android.common.activities.StatefulActivity;
import com.gumtree.android.common.http.CAPIIntentFactory;
import com.gumtree.android.common.search.Search;

public class SearchUrl {
    private final Search search;

    private SearchUrl(Search search) {
        this.search = search;
    }

    public static SearchUrl from(Search search) {
        return new SearchUrl(search);
    }

    public static boolean isPrimarySearchParam(String str) {
        if (str.equals(StatefulActivity.NAME_CATEGORY_ID) || str.equals(StatefulActivity.NAME_LOCATION_ID) || str.equals(StatefulActivity.NAME_MAX_PRICE) || str.equals(StatefulActivity.NAME_MIN_PRICE) || str.equals(StatefulActivity.NAME_QUERY) || str.equals(StatefulActivity.NAME_PICTURE_REQUIRED) || str.equals(StatefulActivity.NAME_LATITUDE) || str.equals(StatefulActivity.NAME_LONGITUDE) || str.equals(StatefulActivity.NAME_DISTANCE) || str.equals(Search.SORT_TYPE) || str.equals(StatefulActivity.NAME_ZIPCODE)) {
            return true;
        }
        return false;
    }

    public Intent getCall(int i, int i2, ResultReceiver resultReceiver) {
        return getAPI().getHttpIntentForAds(this.search.getLongValue(StatefulActivity.EXTRA_SESSION_TIMESTAMP), i + BuildConfig.FLAVOR, i2, getBundleWithServiceCallParams(), resultReceiver, getTrackingId());
    }

    private String getTrackingId() {
        return PreferenceManager.getDefaultSharedPreferences(GumtreeApplication.getContext()).getString(Constants.PREF_AUTO_COMPLETE_TRACKING, null);
    }

    private Bundle getBundleWithServiceCallParams() {
        Bundle bundle = new Bundle();
        Bundle asBundle = this.search.asBundle();
        if (asBundle == null) {
            return asBundle;
        }
        for (String str : asBundle.keySet()) {
            if (isPrimarySearchParam(str)) {
                bundle.putString(str, BuildConfig.FLAVOR + asBundle.get(str));
            } else if (isValidRemoteParam(str)) {
                bundle.putString("attr[" + str + "]", BuildConfig.FLAVOR + asBundle.get(str));
            }
        }
        return bundle;
    }

    private boolean isValidRemoteParam(String str) {
        if (str.equals(StatefulActivity.EXTRA_CATEGORY_NAME) || str.equals(StatefulActivity.EXTRA_LOCATION_NAME) || str.equals(StatefulActivity.EXTRA_SESSION_TIMESTAMP)) {
            return false;
        }
        return true;
    }

    public CAPIIntentFactory getAPI() {
        return GumtreeApplication.getAPI();
    }
}
