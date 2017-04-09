package com.apptentive.android.sdk.model;

import com.apptentive.android.sdk.ApptentiveLog;
import com.apptentive.android.sdk.model.ExtendedData.Type;
import org.json.JSONArray;
import org.json.JSONException;

public class LocationExtendedData extends ExtendedData {
    private static final String KEY_COORDINATES = "coordinates";
    private static final int VERSION = 1;

    protected void init() {
        setType(Type.location);
        setVersion(VERSION);
    }

    public LocationExtendedData(String str) throws JSONException {
        super(str);
    }

    public LocationExtendedData(double d, double d2) {
        setCoordinates(d, d2);
    }

    public void setCoordinates(double d, double d2) {
        try {
            JSONArray jSONArray = new JSONArray();
            put(KEY_COORDINATES, jSONArray);
            jSONArray.put(0, d);
            jSONArray.put(VERSION, d2);
        } catch (JSONException e) {
            ApptentiveLog.w("Error adding %s to LocationExtendedData.", KEY_COORDINATES, e);
        }
    }
}
