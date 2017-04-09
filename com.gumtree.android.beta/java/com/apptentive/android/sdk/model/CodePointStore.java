package com.apptentive.android.sdk.model;

import android.content.Context;
import com.apptentive.android.sdk.ApptentiveInternal;
import com.apptentive.android.sdk.ApptentiveLog;
import com.apptentive.android.sdk.util.Util;
import org.json.JSONException;
import org.json.JSONObject;

public class CodePointStore {
    public static final String KEY_BUILD = "build";
    public static final String KEY_CODE_POINT = "code_point";
    public static final String KEY_INTERACTIONS = "interactions";
    public static final String KEY_LAST = "last";
    public static final String KEY_TOTAL = "total";
    public static final String KEY_VERSION = "version";
    private JSONObject store;

    public void init() {
        this.store = loadFromPreference();
    }

    private void saveToPreference() {
        ApptentiveInternal.getInstance().getSharedPrefs().edit().putString("codePointStore", this.store.toString()).apply();
    }

    private JSONObject loadFromPreference() {
        String string = ApptentiveInternal.getInstance().getSharedPrefs().getString("codePointStore", null);
        if (string != null) {
            try {
                return new JSONObject(string);
            } catch (Throwable e) {
                ApptentiveLog.e("Error loading CodePointStore from SharedPreferences.", e, new Object[0]);
            }
        }
        return new JSONObject();
    }

    public synchronized void storeCodePointForCurrentAppVersion(String str) {
        storeRecordForCurrentAppVersion(false, str);
    }

    public synchronized void storeInteractionForCurrentAppVersion(String str) {
        storeRecordForCurrentAppVersion(true, str);
    }

    private void storeRecordForCurrentAppVersion(boolean z, String str) {
        Context applicationContext = ApptentiveInternal.getInstance().getApplicationContext();
        storeRecord(z, str, Util.getAppVersionName(applicationContext), Util.getAppVersionCode(applicationContext));
    }

    public synchronized void storeRecord(boolean z, String str, String str2, int i) {
        storeRecord(z, str, str2, i, Util.currentTimeSeconds());
    }

    public synchronized void storeRecord(boolean z, String str, String str2, int i, double d) {
        int i2 = 0;
        synchronized (this) {
            String valueOf = String.valueOf(i);
            if (!(str == null || str2 == null)) {
                String str3;
                JSONObject jSONObject;
                JSONObject jSONObject2;
                JSONObject jSONObject3;
                int i3;
                if (z) {
                    try {
                        str3 = KEY_INTERACTIONS;
                    } catch (Throwable e) {
                        ApptentiveLog.w("Unable to store code point %s.", e, str);
                    }
                } else {
                    str3 = KEY_CODE_POINT;
                }
                if (this.store.isNull(str3)) {
                    jSONObject = new JSONObject();
                    this.store.put(str3, jSONObject);
                    jSONObject2 = jSONObject;
                } else {
                    jSONObject2 = this.store.getJSONObject(str3);
                }
                if (jSONObject2.isNull(str)) {
                    jSONObject = new JSONObject();
                    jSONObject2.put(str, jSONObject);
                    jSONObject3 = jSONObject;
                } else {
                    jSONObject3 = jSONObject2.getJSONObject(str);
                }
                jSONObject3.put(KEY_LAST, d);
                if (jSONObject3.has(KEY_TOTAL)) {
                    i3 = jSONObject3.getInt(KEY_TOTAL);
                } else {
                    i3 = 0;
                }
                jSONObject3.put(KEY_TOTAL, i3 + 1);
                if (jSONObject3.isNull(KEY_VERSION)) {
                    jSONObject = new JSONObject();
                    jSONObject3.put(KEY_VERSION, jSONObject);
                    jSONObject2 = jSONObject;
                } else {
                    jSONObject2 = jSONObject3.getJSONObject(KEY_VERSION);
                }
                if (jSONObject2.isNull(str2)) {
                    i3 = 0;
                } else {
                    i3 = jSONObject2.getInt(str2);
                }
                jSONObject2.put(str2, i3 + 1);
                if (jSONObject3.isNull(KEY_BUILD)) {
                    jSONObject = new JSONObject();
                    jSONObject3.put(KEY_BUILD, jSONObject);
                } else {
                    jSONObject = jSONObject3.getJSONObject(KEY_BUILD);
                }
                if (!jSONObject.isNull(valueOf)) {
                    i2 = jSONObject.getInt(valueOf);
                }
                jSONObject.put(valueOf, i2 + 1);
                saveToPreference();
            }
        }
    }

    public JSONObject getRecord(boolean z, String str) {
        String str2 = z ? KEY_INTERACTIONS : KEY_CODE_POINT;
        try {
            if (!this.store.isNull(str2) && this.store.has(str2)) {
                JSONObject jSONObject = this.store.getJSONObject(str2);
                if (jSONObject.has(str)) {
                    return jSONObject.getJSONObject(str);
                }
            }
        } catch (JSONException e) {
            ApptentiveLog.w("Error loading code point record for \"%s\"", str);
        }
        return null;
    }

    public Long getTotalInvokes(boolean z, String str) {
        try {
            JSONObject record = getRecord(z, str);
            if (record != null && record.has(KEY_TOTAL)) {
                return Long.valueOf(record.getLong(KEY_TOTAL));
            }
        } catch (JSONException e) {
        }
        return Long.valueOf(0);
    }

    public Double getLastInvoke(boolean z, String str) {
        try {
            JSONObject record = getRecord(z, str);
            if (record != null && record.has(KEY_LAST)) {
                return Double.valueOf(record.getDouble(KEY_LAST));
            }
        } catch (JSONException e) {
        }
        return null;
    }

    public Long getVersionInvokes(boolean z, String str, String str2) {
        try {
            JSONObject record = getRecord(z, str);
            if (record != null && record.has(KEY_VERSION)) {
                record = record.getJSONObject(KEY_VERSION);
                if (record.has(str2)) {
                    return Long.valueOf(record.getLong(str2));
                }
            }
        } catch (JSONException e) {
        }
        return Long.valueOf(0);
    }

    public Long getBuildInvokes(boolean z, String str, String str2) {
        try {
            JSONObject record = getRecord(z, str);
            if (record != null && record.has(KEY_BUILD)) {
                record = record.getJSONObject(KEY_BUILD);
                if (record.has(str2)) {
                    return Long.valueOf(record.getLong(str2));
                }
            }
        } catch (JSONException e) {
        }
        return Long.valueOf(0);
    }

    public String toString() {
        return "CodePointStore:  " + this.store.toString();
    }

    public void clear() {
        ApptentiveInternal.getInstance().getSharedPrefs().edit().remove("codePointStore").apply();
        this.store = new JSONObject();
    }
}
