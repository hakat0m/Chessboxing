package com.apptentive.android.sdk.model;

import com.apptentive.android.sdk.ApptentiveLog;
import com.apptentive.android.sdk.model.Payload.BaseType;
import org.json.JSONException;

public class Sdk extends Payload {
    public static final String KEY = "sdk";
    private static final String KEY_AUTHOR_EMAIL = "author_email";
    private static final String KEY_AUTHOR_NAME = "author_name";
    private static final String KEY_DISTRIBUTION = "distribution";
    private static final String KEY_DISTRIBUTION_VERSION = "distribution_version";
    private static final String KEY_PLATFORM = "platform";
    private static final String KEY_PROGRAMMING_LANGUAGE = "programming_language";
    private static final String KEY_VERSION = "version";

    public Sdk(String str) throws JSONException {
        super(str);
    }

    public void initBaseType() {
        setBaseType(BaseType.sdk);
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
            ApptentiveLog.w("Error adding %s to Sdk.", KEY_VERSION);
        }
    }

    public String getProgrammingLanguage() {
        try {
            if (!isNull(KEY_PROGRAMMING_LANGUAGE)) {
                return getString(KEY_PROGRAMMING_LANGUAGE);
            }
        } catch (JSONException e) {
        }
        return null;
    }

    public void setProgrammingLanguage(String str) {
        try {
            put(KEY_PROGRAMMING_LANGUAGE, str);
        } catch (JSONException e) {
            ApptentiveLog.w("Error adding %s to Sdk.", KEY_PROGRAMMING_LANGUAGE);
        }
    }

    public String getAuthorName() {
        try {
            if (!isNull(KEY_AUTHOR_NAME)) {
                return getString(KEY_AUTHOR_NAME);
            }
        } catch (JSONException e) {
        }
        return null;
    }

    public void setAuthorName(String str) {
        try {
            put(KEY_AUTHOR_NAME, str);
        } catch (JSONException e) {
            ApptentiveLog.w("Error adding %s to Sdk.", KEY_AUTHOR_NAME);
        }
    }

    public String getAuthorEmail() {
        try {
            if (!isNull(KEY_AUTHOR_EMAIL)) {
                return getString(KEY_AUTHOR_EMAIL);
            }
        } catch (JSONException e) {
        }
        return null;
    }

    public void setAuthorEmail(String str) {
        try {
            put(KEY_AUTHOR_EMAIL, str);
        } catch (JSONException e) {
            ApptentiveLog.w("Error adding %s to Sdk.", KEY_AUTHOR_EMAIL);
        }
    }

    public String getPlatform() {
        try {
            if (!isNull(KEY_PLATFORM)) {
                return getString(KEY_PLATFORM);
            }
        } catch (JSONException e) {
        }
        return null;
    }

    public void setPlatform(String str) {
        try {
            put(KEY_PLATFORM, str);
        } catch (JSONException e) {
            ApptentiveLog.w("Error adding %s to Sdk.", KEY_PLATFORM);
        }
    }

    public String getDistribution() {
        try {
            if (!isNull(KEY_DISTRIBUTION)) {
                return getString(KEY_DISTRIBUTION);
            }
        } catch (JSONException e) {
        }
        return null;
    }

    public void setDistribution(String str) {
        try {
            put(KEY_DISTRIBUTION, str);
        } catch (JSONException e) {
            ApptentiveLog.w("Error adding %s to Sdk.", KEY_DISTRIBUTION);
        }
    }

    public String getDistributionVersion() {
        try {
            if (!isNull(KEY_DISTRIBUTION_VERSION)) {
                return getString(KEY_DISTRIBUTION_VERSION);
            }
        } catch (JSONException e) {
        }
        return null;
    }

    public void setDistributionVersion(String str) {
        try {
            put(KEY_DISTRIBUTION_VERSION, str);
        } catch (JSONException e) {
            ApptentiveLog.w("Error adding %s to Sdk.", KEY_DISTRIBUTION_VERSION);
        }
    }
}
