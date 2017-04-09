package com.gumtree.android.api;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import com.ebay.classifieds.capi.CapiConfig;
import com.ebay.classifieds.capi.CapiTracker;
import com.ebay.classifieds.capi.LocalisedTextProvider;
import com.gumtree.android.api.environment.Environment;
import com.gumtree.android.api.tracking.Tracking;
import com.gumtree.android.auth.BaseAccountManager;
import com.gumtree.android.common.utils.UserAgent;
import com.squareup.okhttp.OkHttpClient;
import org.apache.commons.lang3.Validate;

public final class ApiConfig implements CapiConfig {
    public static final String PREFIX_HOST = "https://";
    private static final String PREFIX_HOST_CAPI = "capi";
    private static final String PREFIX_HOST_MY_PROFILE = "my";
    private static final String PROD_ENDPOINT = Environment.LIVE.getBaseCapiEndpoint();
    private static final String PROD_MY_PROFILE_HOST = "my.gumtree.com";
    private final BaseAccountManager accountManager;
    private final Context context;
    private final EnvironmentSettings environmentSettings;
    private final OkHttpClient okHttpClient;

    public class AppLocalisedTextProvider implements LocalisedTextProvider {
        private final Context context;

        public AppLocalisedTextProvider(Context context) {
            this.context = context;
        }

        public String networkErrorMessage() {
            return this.context.getString(2131165438);
        }

        public String unauthorisedErrorMessage() {
            return this.context.getString(2131165429);
        }

        public String unknownError() {
            return this.context.getString(2131165447);
        }

        public String locationServicesErrorMessage() {
            return this.context.getString(2131165436);
        }
    }

    public ApiConfig(@NonNull Context context, @NonNull OkHttpClient okHttpClient, @NonNull BaseAccountManager baseAccountManager, @NonNull EnvironmentSettings environmentSettings) {
        this.context = (Context) Validate.notNull(context.getApplicationContext());
        this.okHttpClient = (OkHttpClient) Validate.notNull(okHttpClient);
        this.accountManager = (BaseAccountManager) Validate.notNull(baseAccountManager);
        this.environmentSettings = (EnvironmentSettings) Validate.notNull(environmentSettings);
    }

    public String getBaseUrl() {
        return PROD_ENDPOINT + "api/";
    }

    public String getBingEndPoint() {
        return PREFIX_HOST + getBingHost(Uri.parse(getBaseUrl()).getHost());
    }

    public String getMyProfileHost() {
        return PROD_MY_PROFILE_HOST;
    }

    private String getBingHost(String str) {
        String[] split = str.split("\\.");
        if (split.length == 0) {
            return "www.gumtree.com";
        }
        String str2 = "www";
        for (int i = 1; i < split.length; i++) {
            str2 = str2 + "." + split[i];
        }
        return str2;
    }

    public CapiTracker getTracker() {
        return Tracking.getInstance(this.context).getTrackingInfo();
    }

    public boolean isDebug() {
        return false;
    }

    @NonNull
    public OkHttpClient getOkHttpClient() {
        return this.okHttpClient;
    }

    public String getAuthentication() {
        return this.accountManager.getAuthentication();
    }

    @NonNull
    public LocalisedTextProvider getLocalisedTextProvider() {
        return new AppLocalisedTextProvider(this.context);
    }

    public String getWebUserAgent() {
        return UserAgent.getUserAgent(this.context);
    }
}
