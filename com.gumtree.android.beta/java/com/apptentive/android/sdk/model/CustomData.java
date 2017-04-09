package com.apptentive.android.sdk.model;

import com.apptentive.android.sdk.ApptentiveLog;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

public class CustomData extends JSONObject {
    public CustomData(String str) throws JSONException {
        super(str);
    }

    public CustomData(JSONObject jSONObject) throws JSONException {
        super(jSONObject.toString());
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof CustomData)) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        JSONObject jSONObject = (JSONObject) obj;
        if (length() != jSONObject.length()) {
            return false;
        }
        Map hashMap = new HashMap();
        Map hashMap2 = new HashMap();
        try {
            String str;
            Iterator keys = keys();
            while (keys.hasNext()) {
                str = (String) keys.next();
                hashMap.put(str, getString(str));
            }
            keys = jSONObject.keys();
            while (keys.hasNext()) {
                str = (String) keys.next();
                hashMap2.put(str, jSONObject.getString(str));
            }
        } catch (JSONException e) {
            ApptentiveLog.e("Error comparing two device data entries: \"%s\"  AND  \"%s\"", toString(), jSONObject.toString());
        }
        return hashMap.equals(hashMap2);
    }
}
