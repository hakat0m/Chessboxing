package com.ebay.android.frlib;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager.NameNotFoundException;
import android.util.Log;
import com.ebay.android.frlib.dcs.DCSManager;
import com.ebay.android.frlib.deviceid.DeviceId;
import com.ebay.android.frlib.impl.settings.LibrarySettings;
import com.ebay.android.frlib.impl.settings.LibrarySettings.ComponentSettings;
import com.ebay.android.frlib.impl.settings.LibrarySettings.GeoSettings;
import com.ebay.android.frlib.impl.settings.LibrarySettings.MDNSSettings;
import com.ebay.android.frlib.mdns.MDNS;
import com.ebay.android.frlib.mts.TrackingSessionParams;
import com.ebay.android.frlib.mts.impl.MTSRequest;
import com.ebay.android.frlib.standardtracking.GeoLocationTrackingClient;
import java.util.HashMap;

public class FrontierLib {
    private static final String TAG = FrontierLib.class.getSimpleName();
    private static FrontierLib mSingleton;
    private Context mContext;
    private LibrarySettings mSettings = null;

    public enum NotificationSourceApp {
        ThisApp,
        AnotherAppInTheDomain,
        KnownAppDifferentDomain,
        UnknownApp,
        NotClassifiable
    }

    public static FrontierLib getInstance(Context context) throws NameNotFoundException {
        if (mSingleton == null) {
            synchronized (FrontierLib.class) {
                if (mSingleton == null) {
                    mSingleton = new FrontierLib(context);
                }
            }
        }
        return mSingleton;
    }

    private FrontierLib(Context context) throws NameNotFoundException {
        this.mContext = context;
        DeviceId.initDeviceId(this.mContext);
        init();
    }

    private void init() throws NameNotFoundException {
        try {
            init(new LibrarySettings(this));
        } catch (NameNotFoundException e) {
            Log.e(TAG, "Required parameter not in manifest, " + e.getMessage());
            throw e;
        } catch (Exception e2) {
            Log.e(TAG, "Exception while getting parameters from manifest, " + e2.getMessage());
            throw new NameNotFoundException(e2.getMessage());
        }
    }

    private void init(LibrarySettings librarySettings) {
        this.mSettings = librarySettings;
        MTSRequest.init(this);
        DCSManager.init(this);
        MDNS.init(this);
    }

    @Deprecated
    public StandardTracking getStandardTracking() {
        return StandardTracking.getInstance(this);
    }

    public StandardTracking getStandardTrackingComponent() {
        return StandardTracking.getInstance(this);
    }

    @Deprecated
    public GeoLocationTrackingClient getGeoLocationTracking() {
        return GeoLocationTrackingClient.getInstance(this);
    }

    public GeoLocationTrackingClient getGeoLocationComponent() {
        return GeoLocationTrackingClient.getInstance(this);
    }

    @Deprecated
    public DCSManager getDCSManager() {
        return DCSManager.getInstance(this);
    }

    public DCSManager getDCSComponent() {
        return DCSManager.getInstance(this);
    }

    public MDNS getMDNSComponent() {
        return MDNS.getInstance(this);
    }

    public Context getContext() {
        return this.mContext;
    }

    public LibrarySettings getSettings() {
        return this.mSettings;
    }

    public ComponentSettings getDCSSettings() {
        return getSettings().getDCSSettings();
    }

    public ComponentSettings getMTSSettings() {
        return getSettings().getMTSSettings();
    }

    public GeoSettings getGeoSettings() {
        return getSettings().getGeoSettings();
    }

    public MDNSSettings getMDNSSettings() {
        return new MDNSSettings(this.mSettings.getMDNSSettings());
    }

    public void setMDNSSettings(MDNSSettings mDNSSettings) {
        getSettings().setMDNSSettings(mDNSSettings);
    }

    public TrackingSessionParams getTrackingSessionParams() {
        return this.mSettings.getTrackingSessionParams();
    }

    public void setTrackingParams(TrackingSessionParams trackingSessionParams) {
        Log.e(TAG, "NYI - setTrackingSessionParams called, push values down, components may need to create/destroy stuff");
        this.mSettings.setTrackingParams(trackingSessionParams);
    }

    public String getVersion() {
        return "2.1.0.135";
    }

    public String getApplicationName() {
        return this.mSettings.getApplicationName();
    }

    public String getApplicationVersion() {
        return this.mSettings.getApplicationVersion();
    }

    public void putMDNSAuthHeaders(HashMap<String, String> hashMap, String str) {
        CredentialManager.putMDNSAuthHeaders(this, hashMap, str);
    }

    public boolean useProductionServers() {
        return getSettings().getUseProductionServers();
    }

    public NotificationSourceApp classifyNotificationSource(Intent intent) {
        return CredentialManager.classifyNotificationSource(this, intent, this.mSettings.getApplicationName());
    }

    public boolean setCredentials(String str, boolean z, Credentials credentials) {
        return CredentialManager.set(str, z, credentials);
    }

    public Credentials getCredentials(String str, boolean z) {
        return z ? CredentialManager.get(str).mProduction : CredentialManager.get(str).mQA;
    }

    public boolean setApplication(String str, String str2, boolean z) throws NameNotFoundException {
        try {
            init(new LibrarySettings(this, str, str2, z));
            return true;
        } catch (NameNotFoundException e) {
            Log.e(TAG, "Required parameter not in manifest, " + e.getMessage());
            throw e;
        } catch (Exception e2) {
            Log.e(TAG, "Exception while getting parameters from manifest, " + e2.getMessage());
            throw new NameNotFoundException(e2.getMessage());
        }
    }
}
