package com.ebay.classifieds.capi;

import android.support.annotation.NonNull;
import com.squareup.okhttp.OkHttpClient;

public interface CapiConfig {
    String getAuthentication();

    String getBaseUrl();

    String getBingEndPoint();

    @NonNull
    LocalisedTextProvider getLocalisedTextProvider();

    String getMyProfileHost();

    @NonNull
    OkHttpClient getOkHttpClient();

    CapiTracker getTracker();

    String getWebUserAgent();

    boolean isDebug();
}
