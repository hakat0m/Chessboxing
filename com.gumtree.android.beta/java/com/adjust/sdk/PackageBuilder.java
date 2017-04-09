package com.adjust.sdk;

import android.text.TextUtils;
import com.apptentive.android.sdk.BuildConfig;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import org.json.JSONObject;

class PackageBuilder {
    private static ILogger logger = AdjustFactory.getLogger();
    private ActivityState activityState;
    private AdjustConfig adjustConfig;
    AdjustAttribution attribution;
    private long createdAt;
    String deeplink;
    private DeviceInfo deviceInfo;
    Map<String, String> extraParameters;
    String referrer;
    String reftag;

    public PackageBuilder(AdjustConfig adjustConfig, DeviceInfo deviceInfo, ActivityState activityState, long j) {
        this.adjustConfig = adjustConfig;
        this.deviceInfo = deviceInfo;
        this.activityState = activityState == null ? null : activityState.shallowCopy();
        this.createdAt = j;
    }

    public ActivityPackage buildSessionPackage() {
        Map defaultParameters = getDefaultParameters();
        addDuration(defaultParameters, "last_interval", this.activityState.lastInterval);
        addString(defaultParameters, "default_tracker", this.adjustConfig.defaultTracker);
        ActivityPackage defaultActivityPackage = getDefaultActivityPackage(ActivityKind.SESSION);
        defaultActivityPackage.setPath("/session");
        defaultActivityPackage.setSuffix(BuildConfig.FLAVOR);
        defaultActivityPackage.setParameters(defaultParameters);
        return defaultActivityPackage;
    }

    public ActivityPackage buildEventPackage(AdjustEvent adjustEvent) {
        Map defaultParameters = getDefaultParameters();
        addInt(defaultParameters, "event_count", (long) this.activityState.eventCount);
        addString(defaultParameters, "event_token", adjustEvent.eventToken);
        addDouble(defaultParameters, "revenue", adjustEvent.revenue);
        addString(defaultParameters, "currency", adjustEvent.currency);
        addMapJson(defaultParameters, "callback_params", adjustEvent.callbackParameters);
        addMapJson(defaultParameters, "partner_params", adjustEvent.partnerParameters);
        ActivityPackage defaultActivityPackage = getDefaultActivityPackage(ActivityKind.EVENT);
        defaultActivityPackage.setPath("/event");
        defaultActivityPackage.setSuffix(getEventSuffix(adjustEvent));
        defaultActivityPackage.setParameters(defaultParameters);
        return defaultActivityPackage;
    }

    public ActivityPackage buildClickPackage(String str, long j) {
        Map idsParameters = getIdsParameters();
        addString(idsParameters, "source", str);
        addDate(idsParameters, "click_time", j);
        addString(idsParameters, Constants.REFTAG, this.reftag);
        addMapJson(idsParameters, "params", this.extraParameters);
        addString(idsParameters, Constants.REFERRER, this.referrer);
        addString(idsParameters, Constants.DEEPLINK, this.deeplink);
        injectAttribution(idsParameters);
        ActivityPackage defaultActivityPackage = getDefaultActivityPackage(ActivityKind.CLICK);
        defaultActivityPackage.setPath("/sdk_click");
        defaultActivityPackage.setSuffix(BuildConfig.FLAVOR);
        defaultActivityPackage.setParameters(idsParameters);
        return defaultActivityPackage;
    }

    public ActivityPackage buildAttributionPackage() {
        Map idsParameters = getIdsParameters();
        ActivityPackage defaultActivityPackage = getDefaultActivityPackage(ActivityKind.ATTRIBUTION);
        defaultActivityPackage.setPath("attribution");
        defaultActivityPackage.setSuffix(BuildConfig.FLAVOR);
        defaultActivityPackage.setParameters(idsParameters);
        return defaultActivityPackage;
    }

    private ActivityPackage getDefaultActivityPackage(ActivityKind activityKind) {
        ActivityPackage activityPackage = new ActivityPackage(activityKind);
        activityPackage.setClientSdk(this.deviceInfo.clientSdk);
        return activityPackage;
    }

    private Map<String, String> getDefaultParameters() {
        Map<String, String> hashMap = new HashMap();
        injectDeviceInfo(hashMap);
        injectConfig(hashMap);
        injectActivityState(hashMap);
        injectCreatedAt(hashMap);
        checkDeviceIds(hashMap);
        return hashMap;
    }

    private Map<String, String> getIdsParameters() {
        Map<String, String> hashMap = new HashMap();
        injectDeviceInfoIds(hashMap);
        injectConfig(hashMap);
        injectCreatedAt(hashMap);
        checkDeviceIds(hashMap);
        return hashMap;
    }

    private void injectDeviceInfo(Map<String, String> map) {
        injectDeviceInfoIds(map);
        addString(map, "fb_id", this.deviceInfo.fbAttributionId);
        addString(map, "package_name", this.deviceInfo.packageName);
        addString(map, "app_version", this.deviceInfo.appVersion);
        addString(map, "device_type", this.deviceInfo.deviceType);
        addString(map, "device_name", this.deviceInfo.deviceName);
        addString(map, "device_manufacturer", this.deviceInfo.deviceManufacturer);
        addString(map, "os_name", this.deviceInfo.osName);
        addString(map, "os_version", this.deviceInfo.osVersion);
        addString(map, "api_level", this.deviceInfo.apiLevel);
        addString(map, "language", this.deviceInfo.language);
        addString(map, "country", this.deviceInfo.country);
        addString(map, "screen_size", this.deviceInfo.screenSize);
        addString(map, "screen_format", this.deviceInfo.screenFormat);
        addString(map, "screen_density", this.deviceInfo.screenDensity);
        addString(map, "display_width", this.deviceInfo.displayWidth);
        addString(map, "display_height", this.deviceInfo.displayHeight);
        addString(map, "hardware_name", this.deviceInfo.hardwareName);
        addString(map, "cpu_type", this.deviceInfo.abi);
        fillPluginKeys(map);
    }

    private void injectDeviceInfoIds(Map<String, String> map) {
        addString(map, "mac_sha1", this.deviceInfo.macSha1);
        addString(map, "mac_md5", this.deviceInfo.macShortMd5);
        addString(map, "android_id", this.deviceInfo.androidId);
    }

    private void injectConfig(Map<String, String> map) {
        addString(map, "app_token", this.adjustConfig.appToken);
        addString(map, "environment", this.adjustConfig.environment);
        addBoolean(map, "device_known", this.adjustConfig.deviceKnown);
        addBoolean(map, "needs_response_details", Boolean.valueOf(this.adjustConfig.hasListener()));
        addString(map, "gps_adid", Util.getPlayAdId(this.adjustConfig.context));
        addBoolean(map, "tracking_enabled", Util.isPlayTrackingEnabled(this.adjustConfig.context));
        addBoolean(map, "event_buffering_enabled", Boolean.valueOf(this.adjustConfig.eventBufferingEnabled));
    }

    private void injectActivityState(Map<String, String> map) {
        addString(map, "android_uuid", this.activityState.uuid);
        addInt(map, "session_count", (long) this.activityState.sessionCount);
        addInt(map, "subsession_count", (long) this.activityState.subsessionCount);
        addDuration(map, "session_length", this.activityState.sessionLength);
        addDuration(map, "time_spent", this.activityState.timeSpent);
    }

    private void injectCreatedAt(Map<String, String> map) {
        addDate(map, "created_at", this.createdAt);
    }

    private void injectAttribution(Map<String, String> map) {
        if (this.attribution != null) {
            addString(map, "tracker", this.attribution.trackerName);
            addString(map, "campaign", this.attribution.campaign);
            addString(map, "adgroup", this.attribution.adgroup);
            addString(map, "creative", this.attribution.creative);
        }
    }

    private void checkDeviceIds(Map<String, String> map) {
        if (!map.containsKey("mac_sha1") && !map.containsKey("mac_md5") && !map.containsKey("android_id") && !map.containsKey("gps_adid")) {
            logger.error("Missing device id's. Please check if Proguard is correctly set with Adjust SDK", new Object[0]);
        }
    }

    private void fillPluginKeys(Map<String, String> map) {
        if (this.deviceInfo.pluginKeys != null) {
            for (Entry entry : this.deviceInfo.pluginKeys.entrySet()) {
                addString(map, (String) entry.getKey(), (String) entry.getValue());
            }
        }
    }

    private String getEventSuffix(AdjustEvent adjustEvent) {
        if (adjustEvent.revenue == null) {
            return String.format(Locale.US, "'%s'", new Object[]{adjustEvent.eventToken});
        }
        return String.format(Locale.US, "(%.5f %s, '%s')", new Object[]{adjustEvent.revenue, adjustEvent.currency, adjustEvent.eventToken});
    }

    private void addString(Map<String, String> map, String str, String str2) {
        if (!TextUtils.isEmpty(str2)) {
            map.put(str, str2);
        }
    }

    private void addInt(Map<String, String> map, String str, long j) {
        if (j >= 0) {
            addString(map, str, Long.toString(j));
        }
    }

    private void addDate(Map<String, String> map, String str, long j) {
        if (j >= 0) {
            addString(map, str, Util.dateFormat(j));
        }
    }

    private void addDuration(Map<String, String> map, String str, long j) {
        if (j >= 0) {
            addInt(map, str, (500 + j) / 1000);
        }
    }

    private void addMapJson(Map<String, String> map, String str, Map<String, String> map2) {
        if (map2 != null && map2.size() != 0) {
            addString(map, str, new JSONObject(map2).toString());
        }
    }

    private void addBoolean(Map<String, String> map, String str, Boolean bool) {
        if (bool != null) {
            addInt(map, str, (long) (bool.booleanValue() ? 1 : 0));
        }
    }

    private void addDouble(Map<String, String> map, String str, Double d) {
        if (d != null) {
            addString(map, str, String.format(Locale.US, "%.5f", new Object[]{d}));
        }
    }
}
