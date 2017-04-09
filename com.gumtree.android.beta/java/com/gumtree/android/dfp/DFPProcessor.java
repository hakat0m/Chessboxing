package com.gumtree.android.dfp;

import android.content.Context;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import com.apptentive.android.sdk.BuildConfig;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.doubleclick.PublisherAdRequest;
import com.google.android.gms.ads.doubleclick.PublisherAdRequest.Builder;
import com.google.android.gms.ads.mediation.admob.AdMobExtras;
import com.gumtree.android.common.utils.AppUtils;
import com.gumtree.android.dagger.ComponentsManager;
import javax.inject.Inject;

public abstract class DFPProcessor {
    private static final String ALL = "all";
    private static final int CATEGORY_DEPTH_LIMIT = 2;
    private static final String PASSING_CONTENT_URL = "Passing content url ";
    public static final String SEPARATOR = "/";
    private static final String TAG = "GumDFP";
    @Inject
    Context context;
    private final String ppid = PreferenceManager.getDefaultSharedPreferences(this.context).getString("pref_hashed_email", BuildConfig.FLAVOR);

    public abstract String getMpuBanner(String str);

    public abstract String getPageType();

    public DFPProcessor() {
        ComponentsManager.get().getAppComponent().inject(this);
    }

    public PublisherAdRequest createRequest(String str, Bundle bundle) {
        if (this.ppid == null || this.ppid.length() == 0) {
            return new Builder().addNetworkExtras(getExtras(str, bundle)).build();
        }
        log("Passing ppid " + this.ppid);
        return new Builder().setPublisherProvidedId(this.ppid).addNetworkExtras(getExtras(str, bundle)).build();
    }

    public PublisherAdRequest createRequest(String str, String str2, Bundle bundle) {
        if (TextUtils.isEmpty(str2)) {
            return createRequest(str, bundle);
        }
        if (this.ppid == null || this.ppid.length() == 0) {
            log(PASSING_CONTENT_URL + str2);
            return new Builder().setContentUrl(str2).addNetworkExtras(getExtras(str, bundle)).build();
        }
        log(PASSING_CONTENT_URL + str2 + " and ppid " + this.ppid);
        return new Builder().setPublisherProvidedId(this.ppid).setContentUrl(str2).addNetworkExtras(getExtras(str, bundle)).build();
    }

    protected Bundle getBasicExtras(Bundle bundle, String str) {
        if (!TextUtils.isEmpty(str)) {
            bundle.putString("kw", str);
        }
        bundle.putString("page_type", getPageType());
        bundle.putString("ot", AppUtils.getOrientation());
        return bundle;
    }

    private AdMobExtras getExtras(String str, Bundle bundle) {
        int i = CATEGORY_DEPTH_LIMIT;
        String replace = str.replace("all/", BuildConfig.FLAVOR).replace("flats-houses/", BuildConfig.FLAVOR);
        if (replace.contains(SEPARATOR)) {
            String[] split = replace.split(SEPARATOR);
            if (split.length > CATEGORY_DEPTH_LIMIT) {
                while (i < split.length) {
                    bundle.putString("l" + (i + 1), split[i]);
                    i++;
                }
            }
        }
        log(bundle);
        return new AdMobExtras(bundle);
    }

    public String getAdUnitId(String str, String str2) {
        String str3;
        if (TextUtils.isEmpty(str)) {
            str3 = ALL;
        } else {
            str3 = str;
        }
        String str4 = BuildConfig.FLAVOR;
        str3 = str3.replace("all;", BuildConfig.FLAVOR).replace("flats-houses;", BuildConfig.FLAVOR).replace(";", SEPARATOR);
        if (str.contains(SEPARATOR)) {
            String[] split = str.split(SEPARATOR);
            if (split.length > CATEGORY_DEPTH_LIMIT) {
                str3 = str4;
                for (int i = 0; i < CATEGORY_DEPTH_LIMIT; i++) {
                    if (i == 1) {
                        str3 = str3 + split[i];
                    } else {
                        str3 = str3 + split[i] + SEPARATOR;
                    }
                }
            }
        }
        str3 = str2 + str3;
        log("START VIP BANNER ----------------------------------------------");
        log(str3);
        log("END VIP BANNER ------------------------------------------------");
        return str3;
    }

    public AdSize[] getMpuAdSizes() {
        return new AdSize[]{AdSize.MEDIUM_RECTANGLE};
    }

    protected void log(Bundle bundle) {
        for (String str : bundle.keySet()) {
            log("key = " + str + " value = " + bundle.get(str));
        }
    }

    private void log(String str) {
    }
}
