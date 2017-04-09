package com.apptentive.android.sdk.model;

import com.apptentive.android.sdk.ApptentiveLog;
import org.json.JSONException;
import org.json.JSONObject;

public abstract class ExtendedData extends JSONObject {
    private static final String KEY_VERSION = "version";
    private Type type = Type.unknown;

    public enum Type {
        time,
        location,
        commerce,
        unknown;

        public static Type parse(String str) {
            try {
                return valueOf(str);
            } catch (IllegalArgumentException e) {
                ApptentiveLog.v("Error parsing unknown ExtendedData.BaseType: " + str, new Object[0]);
                return unknown;
            }
        }
    }

    protected abstract void init();

    protected ExtendedData() {
        init();
    }

    protected ExtendedData(String str) throws JSONException {
        super(str);
        init();
    }

    public String getTypeName() {
        return this.type.name();
    }

    protected void setType(Type type) {
        this.type = type;
    }

    protected void setVersion(int i) {
        try {
            put(KEY_VERSION, i);
        } catch (JSONException e) {
            ApptentiveLog.w("Error adding %s to ExtendedData.", KEY_VERSION, e);
        }
    }
}
