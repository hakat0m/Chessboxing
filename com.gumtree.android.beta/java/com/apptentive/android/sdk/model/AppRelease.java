package com.apptentive.android.sdk.model;

import com.apptentive.android.sdk.ApptentiveLog;
import com.apptentive.android.sdk.model.Payload.BaseType;
import org.json.JSONException;

public class AppRelease extends Payload {
    private static final String KEY_APP_STORE = "app_store";
    private static final String KEY_BUILD_NUMBER = "build_number";
    private static final String KEY_IDENTIFIER = "identifier";
    private static final String KEY_STYLE_INHERIT = "inheriting_styles";
    private static final String KEY_STYLE_OVERRIDE = "overriding_styles";
    private static final String KEY_TARGET_SDK_VERSION = "target_sdk_version";
    private static final String KEY_VERSION = "version";

    public AppRelease(String str) throws JSONException {
        super(str);
    }

    public void initBaseType() {
        setBaseType(BaseType.app_release);
    }

    public String getVersion() {
        try {
            if (!isNull(KEY_VERSION)) {
                return getString(KEY_VERSION);
            }
        } catch (JSONException e) {
        }
        return null;
    }

    public void setVersion(String str) {
        try {
            put(KEY_VERSION, str);
        } catch (JSONException e) {
            ApptentiveLog.w("Error adding %s to AppRelease.", KEY_VERSION);
        }
    }

    public String getBuildNumber() {
        try {
            if (!isNull(KEY_BUILD_NUMBER)) {
                return getString(KEY_BUILD_NUMBER);
            }
        } catch (JSONException e) {
        }
        return null;
    }

    public void setBuildNumber(String str) {
        try {
            put(KEY_BUILD_NUMBER, str);
        } catch (JSONException e) {
            ApptentiveLog.w("Error adding %s to AppRelease.", KEY_BUILD_NUMBER);
        }
    }

    public String getIdentifier() {
        try {
            if (!isNull(KEY_IDENTIFIER)) {
                return getString(KEY_IDENTIFIER);
            }
        } catch (JSONException e) {
        }
        return null;
    }

    public void setIdentifier(String str) {
        try {
            put(KEY_IDENTIFIER, str);
        } catch (JSONException e) {
            ApptentiveLog.w("Error adding %s to AppRelease.", KEY_IDENTIFIER);
        }
    }

    public String getTargetSdkVersion() {
        try {
            if (!isNull(KEY_TARGET_SDK_VERSION)) {
                return getString(KEY_TARGET_SDK_VERSION);
            }
        } catch (JSONException e) {
        }
        return null;
    }

    public void setTargetSdkVersion(String str) {
        try {
            put(KEY_TARGET_SDK_VERSION, str);
        } catch (JSONException e) {
            ApptentiveLog.w("Error adding %s to AppRelease.", KEY_TARGET_SDK_VERSION);
        }
    }

    public String getAppStore() {
        try {
            if (!isNull(KEY_APP_STORE)) {
                return getString(KEY_APP_STORE);
            }
        } catch (JSONException e) {
        }
        return null;
    }

    public void setAppStore(String str) {
        try {
            put(KEY_APP_STORE, str);
        } catch (JSONException e) {
            ApptentiveLog.w("Error adding %s to AppRelease.", KEY_APP_STORE);
        }
    }

    public boolean getInheritStyle() {
        return optBoolean(KEY_STYLE_INHERIT);
    }

    public void setInheritStyle(boolean z) {
        try {
            put(KEY_STYLE_INHERIT, z);
        } catch (JSONException e) {
            ApptentiveLog.w("Error adding %s to AppRelease.", KEY_STYLE_INHERIT);
        }
    }

    public boolean getOverrideStyle() {
        return optBoolean(KEY_STYLE_OVERRIDE);
    }

    public void setOverrideStyle(boolean z) {
        try {
            put(KEY_STYLE_OVERRIDE, z);
        } catch (JSONException e) {
            ApptentiveLog.w("Error adding %s to AppRelease.", KEY_STYLE_OVERRIDE);
        }
    }
}
