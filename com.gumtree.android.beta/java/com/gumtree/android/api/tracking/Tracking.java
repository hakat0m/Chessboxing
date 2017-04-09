package com.gumtree.android.api.tracking;

import android.content.Context;
import android.preference.PreferenceManager;
import com.ebay.classifieds.capi.ICapiClient;
import com.gumtree.android.auth.CustomerAccountManager;
import com.gumtree.android.common.http.TrackingInfo;

public class Tracking {
    private static final String EMPTY = "";
    private static Tracking instance;
    private Context context;
    private String uID = createUID();
    private UIDBuilder uidBuilder = new UIDBuilder();
    private String userAgent = createUserAgent();
    private String userUID = createUserUID();
    private String version = createVersion();

    protected Tracking(Context context) {
        this.context = context;
    }

    public static Tracking getInstance(Context context) {
        if (instance == null) {
            instance = new Tracking(context);
        }
        return instance;
    }

    private String createVersion() {
        return "1.28";
    }

    private String createUID() {
        String string = PreferenceManager.getDefaultSharedPreferences(this.context).getString(ICapiClient.ECG_UID, EMPTY);
        if (string.length() == 0) {
            return this.uidBuilder.generateAnonymousUID(this.context);
        }
        return string;
    }

    private String createUserUID() {
        CustomerAccountManager customerAccountManager = new CustomerAccountManager(this.context);
        if (customerAccountManager.getUsername() != null) {
            return customerAccountManager.getUsername();
        }
        return EMPTY;
    }

    private String createUserAgent() {
        String str = "android-app-3.2.7 Beta";
        String string = PreferenceManager.getDefaultSharedPreferences(this.context).getString(ICapiClient.ECG_USER_AGENT, EMPTY);
        if (string.equals(str)) {
            return string;
        }
        PreferenceManager.getDefaultSharedPreferences(this.context).edit().putString(ICapiClient.ECG_USER_AGENT, str).commit();
        return str;
    }

    public TrackingInfo getTrackingInfo() {
        TrackingInfo trackingInfo = new TrackingInfo();
        trackingInfo.setuID(this.uID);
        trackingInfo.setUserUID(this.userUID);
        trackingInfo.setUserAgent(this.userAgent);
        trackingInfo.setVersion(this.version);
        return trackingInfo;
    }
}
