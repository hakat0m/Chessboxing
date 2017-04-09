package com.apptentive.android.sdk.model;

import com.apptentive.android.sdk.ApptentiveLog;
import com.apptentive.android.sdk.model.ExtendedData.Type;
import java.util.Date;
import org.json.JSONException;

public class TimeExtendedData extends ExtendedData {
    private static final String KEY_TIMESTAMP = "timestamp";
    private static final int VERSION = 1;

    protected void init() {
        setType(Type.time);
        setVersion(VERSION);
    }

    public TimeExtendedData() {
        setTimestamp(System.currentTimeMillis());
    }

    public TimeExtendedData(String str) throws JSONException {
        super(str);
    }

    public TimeExtendedData(Date date) {
        setTimestamp(date);
    }

    public TimeExtendedData(long j) {
        setTimestamp(j);
    }

    public TimeExtendedData(double d) {
        setTimestamp(d);
    }

    protected void setTimestamp(Date date) {
        if (date != null) {
            setTimestamp(date.getTime());
        } else {
            setTimestamp(System.currentTimeMillis());
        }
    }

    protected void setTimestamp(long j) {
        setTimestamp(((double) j) / 1000.0d);
    }

    protected void setTimestamp(double d) {
        try {
            put(KEY_TIMESTAMP, d);
        } catch (JSONException e) {
            ApptentiveLog.w("Error adding %s to TimeExtendedData.", KEY_TIMESTAMP, e);
        }
    }
}
