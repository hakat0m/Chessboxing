package com.apptentive.android.sdk.model;

import com.apptentive.android.sdk.ApptentiveLog;
import com.apptentive.android.sdk.util.Util;
import java.util.UUID;
import org.json.JSONException;

public abstract class ConversationItem extends Payload {
    protected static final String KEY_CLIENT_CREATED_AT = "client_created_at";
    protected static final String KEY_CLIENT_CREATED_AT_UTC_OFFSET = "client_created_at_utc_offset";
    protected static final String KEY_NONCE = "nonce";

    protected ConversationItem() {
        setNonce(UUID.randomUUID().toString());
        double currentTimeSeconds = Util.currentTimeSeconds();
        int utcOffset = Util.getUtcOffset();
        setClientCreatedAt(Double.valueOf(currentTimeSeconds));
        setClientCreatedAtUtcOffset(utcOffset);
    }

    protected ConversationItem(String str) throws JSONException {
        super(str);
    }

    public void setNonce(String str) {
        try {
            put(KEY_NONCE, str);
        } catch (Throwable e) {
            ApptentiveLog.e("Exception setting ConversationItem's %s field.", e, KEY_NONCE);
        }
    }

    public String getNonce() {
        try {
            if (!isNull(KEY_NONCE)) {
                return getString(KEY_NONCE);
            }
        } catch (JSONException e) {
        }
        return null;
    }

    public Double getClientCreatedAt() {
        try {
            return Double.valueOf(getDouble(KEY_CLIENT_CREATED_AT));
        } catch (JSONException e) {
            return null;
        }
    }

    public void setClientCreatedAt(Double d) {
        try {
            put(KEY_CLIENT_CREATED_AT, d);
        } catch (Throwable e) {
            ApptentiveLog.e("Exception setting ConversationItem's %s field.", e, KEY_CLIENT_CREATED_AT);
        }
    }

    public void setClientCreatedAtUtcOffset(int i) {
        try {
            put(KEY_CLIENT_CREATED_AT_UTC_OFFSET, i);
        } catch (Throwable e) {
            ApptentiveLog.e("Exception setting ConversationItem's %s field.", e, KEY_CLIENT_CREATED_AT_UTC_OFFSET);
        }
    }
}
