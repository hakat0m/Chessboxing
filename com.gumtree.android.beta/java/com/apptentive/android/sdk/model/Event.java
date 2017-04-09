package com.apptentive.android.sdk.model;

import com.apptentive.android.sdk.ApptentiveLog;
import com.apptentive.android.sdk.model.Payload.BaseType;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

public class Event extends ConversationItem {
    private static final String KEY_CUSTOM_DATA = "custom_data";
    private static final String KEY_DATA = "data";
    private static final String KEY_INTERACTION_ID = "interaction_id";
    private static final String KEY_LABEL = "label";
    private static final String KEY_TRIGGER = "trigger";

    public enum EventLabel {
        app__launch("launch"),
        app__exit("exit"),
        error("error");
        
        private final String labelName;

        private EventLabel(String str) {
            this.labelName = str;
        }

        public String getLabelName() {
            return this.labelName;
        }
    }

    public Event(String str) throws JSONException {
        super(str);
    }

    public Event(String str, JSONObject jSONObject) {
        try {
            put(KEY_LABEL, str);
            if (jSONObject != null) {
                put(KEY_DATA, jSONObject);
            }
        } catch (Throwable e) {
            ApptentiveLog.e("Unable to construct Event.", e, new Object[0]);
        }
    }

    public Event(String str, Map<String, String> map) {
        try {
            put(KEY_LABEL, str);
            if (map != null && !map.isEmpty()) {
                JSONObject jSONObject = new JSONObject();
                for (String str2 : map.keySet()) {
                    jSONObject.put(str2, map.get(str2));
                }
                put(KEY_DATA, jSONObject);
            }
        } catch (Throwable e) {
            ApptentiveLog.e("Unable to construct Event.", e, new Object[0]);
        }
    }

    public Event(String str, String str2, String str3, Map<String, Object> map, ExtendedData... extendedDataArr) {
        try {
            put(KEY_LABEL, str);
            if (str2 != null) {
                put(KEY_INTERACTION_ID, str2);
            }
            if (str3 != null) {
                put(KEY_DATA, new JSONObject(str3));
            }
            if (!(map == null || map.isEmpty())) {
                put(KEY_CUSTOM_DATA, generateCustomDataJson(map));
            }
            if (extendedDataArr != null && extendedDataArr.length != 0) {
                for (ExtendedData extendedData : extendedDataArr) {
                    if (extendedData != null) {
                        put(extendedData.getTypeName(), extendedData);
                    }
                }
            }
        } catch (Throwable e) {
            ApptentiveLog.e("Unable to construct Event.", e, new Object[0]);
        }
    }

    private JSONObject generateCustomDataJson(Map<String, Object> map) {
        JSONObject jSONObject = new JSONObject();
        for (String str : map.keySet()) {
            Object obj = map.get(str);
            if (obj != null) {
                try {
                    jSONObject.put(str, obj);
                } catch (Exception e) {
                    ApptentiveLog.w("Error adding custom data to Event: \"%s\" = \"%s\"", str, obj.toString(), e);
                }
            }
        }
        return jSONObject;
    }

    public Event(String str, String str2) {
        this(str, (Map) null);
        Map hashMap = new HashMap();
        hashMap.put(KEY_TRIGGER, str2);
        putData(hashMap);
    }

    protected void initBaseType() {
        setBaseType(BaseType.event);
    }

    public void putData(Map<String, String> map) {
        if (map != null && !map.isEmpty()) {
            try {
                JSONObject jSONObject;
                if (isNull(KEY_DATA)) {
                    JSONObject jSONObject2 = new JSONObject();
                    put(KEY_DATA, jSONObject2);
                    jSONObject = jSONObject2;
                } else {
                    jSONObject = getJSONObject(KEY_DATA);
                }
                for (String str : map.keySet()) {
                    jSONObject.put(str, map.get(str));
                }
            } catch (Throwable e) {
                ApptentiveLog.e("Unable to add data to Event.", e, new Object[0]);
            }
        }
    }
}
