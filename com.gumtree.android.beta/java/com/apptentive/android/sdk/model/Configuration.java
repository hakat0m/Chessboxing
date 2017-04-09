package com.apptentive.android.sdk.model;

import android.content.Context;
import android.content.SharedPreferences;
import com.apptentive.android.sdk.ApptentiveInternal;
import com.apptentive.android.sdk.ApptentiveLog;
import org.json.JSONException;
import org.json.JSONObject;

public class Configuration extends JSONObject {
    private static final String KEY_APP_DISPLAY_NAME = "app_display_name";
    private static final String KEY_CONFIGURATION_CACHE_EXPIRATION_MILLIS = "configuration_cache_expiration_millis";
    private static final String KEY_HIDE_BRANDING = "hide_branding";
    private static final String KEY_MESSAGE_CENTER = "message_center";
    private static final String KEY_MESSAGE_CENTER_BG_POLL = "bg_poll";
    private static final String KEY_MESSAGE_CENTER_ENABLED = "message_center_enabled";
    private static final String KEY_MESSAGE_CENTER_FG_POLL = "fg_poll";
    private static final String KEY_MESSAGE_CENTER_NOTIFICATION_POPUP = "notification_popup";
    private static final String KEY_MESSAGE_CENTER_NOTIFICATION_POPUP_ENABLED = "enabled";
    private static final String KEY_METRICS_ENABLED = "metrics_enabled";

    public Configuration(String str) throws JSONException {
        super(str);
    }

    public void save() {
        ApptentiveInternal.getInstance().getSharedPrefs().edit().putString("appConfiguration.json", toString()).apply();
    }

    public static Configuration load() {
        return load(ApptentiveInternal.getInstance().getSharedPrefs());
    }

    public static Configuration load(SharedPreferences sharedPreferences) {
        String string = sharedPreferences.getString("appConfiguration.json", null);
        if (string != null) {
            try {
                return new Configuration(string);
            } catch (Throwable e) {
                ApptentiveLog.e("Error loading Configuration from SharedPreferences.", e, new Object[0]);
            }
        }
        return new Configuration();
    }

    public boolean isMetricsEnabled() {
        try {
            if (!isNull(KEY_METRICS_ENABLED)) {
                return getBoolean(KEY_METRICS_ENABLED);
            }
        } catch (JSONException e) {
        }
        return true;
    }

    public String getAppDisplayName() {
        try {
            if (!isNull(KEY_APP_DISPLAY_NAME)) {
                return getString(KEY_APP_DISPLAY_NAME);
            }
        } catch (JSONException e) {
        }
        return ApptentiveInternal.getInstance().getDefaultAppDisplayName();
    }

    private JSONObject getMessageCenter() {
        try {
            if (!isNull(KEY_MESSAGE_CENTER)) {
                return getJSONObject(KEY_MESSAGE_CENTER);
            }
        } catch (JSONException e) {
        }
        return null;
    }

    public int getMessageCenterFgPoll() {
        try {
            JSONObject messageCenter = getMessageCenter();
            if (!(messageCenter == null || messageCenter.isNull(KEY_MESSAGE_CENTER_FG_POLL))) {
                return messageCenter.getInt(KEY_MESSAGE_CENTER_FG_POLL);
            }
        } catch (JSONException e) {
        }
        return 15;
    }

    public int getMessageCenterBgPoll() {
        try {
            JSONObject messageCenter = getMessageCenter();
            if (!(messageCenter == null || messageCenter.isNull(KEY_MESSAGE_CENTER_BG_POLL))) {
                return messageCenter.getInt(KEY_MESSAGE_CENTER_BG_POLL);
            }
        } catch (JSONException e) {
        }
        return 60;
    }

    public boolean isMessageCenterEnabled() {
        try {
            if (!isNull(KEY_MESSAGE_CENTER_ENABLED)) {
                return getBoolean(KEY_MESSAGE_CENTER_ENABLED);
            }
        } catch (JSONException e) {
        }
        return false;
    }

    public boolean isMessageCenterNotificationPopupEnabled() {
        JSONObject messageCenter = getMessageCenter();
        if (messageCenter == null || messageCenter.isNull(KEY_MESSAGE_CENTER_NOTIFICATION_POPUP)) {
            return false;
        }
        messageCenter = messageCenter.optJSONObject(KEY_MESSAGE_CENTER_NOTIFICATION_POPUP);
        if (messageCenter != null) {
            return messageCenter.optBoolean(KEY_MESSAGE_CENTER_NOTIFICATION_POPUP_ENABLED, false);
        }
        return false;
    }

    public boolean isHideBranding(Context context) {
        boolean z = false;
        try {
            if (!isNull(KEY_HIDE_BRANDING)) {
                z = getBoolean(KEY_HIDE_BRANDING);
                return z;
            }
        } catch (JSONException e) {
        }
        try {
            z = context.getPackageManager().getApplicationInfo(context.getPackageName(), 128).metaData.getBoolean("apptentive_initially_hide_branding", false);
        } catch (Throwable e2) {
            ApptentiveLog.w("Unexpected error while reading %s manifest setting.", e2, "apptentive_initially_hide_branding");
        }
        return z;
    }

    public long getConfigurationCacheExpirationMillis() {
        try {
            if (!isNull(KEY_CONFIGURATION_CACHE_EXPIRATION_MILLIS)) {
                return getLong(KEY_CONFIGURATION_CACHE_EXPIRATION_MILLIS);
            }
        } catch (JSONException e) {
        }
        return 0;
    }

    public void setConfigurationCacheExpirationMillis(long j) {
        try {
            put(KEY_CONFIGURATION_CACHE_EXPIRATION_MILLIS, j);
        } catch (JSONException e) {
            ApptentiveLog.w("Error adding %s to Configuration.", KEY_CONFIGURATION_CACHE_EXPIRATION_MILLIS);
        }
    }

    public boolean hasConfigurationCacheExpired() {
        return getConfigurationCacheExpirationMillis() < System.currentTimeMillis();
    }
}
