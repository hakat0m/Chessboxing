package com.apptentive.android.sdk.model;

import com.apptentive.android.sdk.ApptentiveLog;
import org.json.JSONException;
import org.json.JSONObject;

public abstract class Payload extends JSONObject {
    private BaseType baseType;
    private Long databaseId;

    public enum BaseType {
        message,
        event,
        device,
        sdk,
        app_release,
        person,
        unknown,
        survey;

        public static BaseType parse(String str) {
            try {
                return valueOf(str);
            } catch (IllegalArgumentException e) {
                ApptentiveLog.v("Error parsing unknown Payload.BaseType: " + str, new Object[0]);
                return unknown;
            }
        }
    }

    protected abstract void initBaseType();

    public Payload() {
        initBaseType();
    }

    public Payload(String str) throws JSONException {
        super(str);
        initBaseType();
    }

    public String marshallForSending() {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put(getBaseType().name(), this);
            return jSONObject.toString();
        } catch (Throwable e) {
            ApptentiveLog.w("Error wrapping Payload in JSONObject.", e, new Object[0]);
            return null;
        }
    }

    public long getDatabaseId() {
        return this.databaseId.longValue();
    }

    public void setDatabaseId(long j) {
        this.databaseId = Long.valueOf(j);
    }

    public BaseType getBaseType() {
        return this.baseType;
    }

    protected void setBaseType(BaseType baseType) {
        this.baseType = baseType;
    }

    public boolean has(String str) {
        return super.has(str);
    }
}
