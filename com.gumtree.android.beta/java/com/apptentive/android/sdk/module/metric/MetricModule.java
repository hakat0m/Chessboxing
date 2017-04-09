package com.apptentive.android.sdk.module.metric;

import com.apptentive.android.sdk.ApptentiveLog;
import com.apptentive.android.sdk.model.Configuration;
import com.apptentive.android.sdk.model.Event;
import com.apptentive.android.sdk.model.Event.EventLabel;
import com.apptentive.android.sdk.model.EventManager;
import com.apptentive.android.sdk.util.Util;
import java.util.Map;
import org.json.JSONObject;

public class MetricModule {
    private static final String KEY_EXCEPTION = "exception";

    public static void sendMetric(EventLabel eventLabel) {
        sendMetric(eventLabel, null);
    }

    public static void sendMetric(EventLabel eventLabel, String str) {
        sendMetric(eventLabel, str, null);
    }

    public static void sendMetric(EventLabel eventLabel, String str, Map<String, String> map) {
        if (Configuration.load().isMetricsEnabled()) {
            String str2 = "Sending Metric: %s, trigger: %s, data: %s";
            Object[] objArr = new Object[3];
            objArr[0] = eventLabel.getLabelName();
            objArr[1] = str;
            objArr[2] = map != null ? map.toString() : "null";
            ApptentiveLog.v(str2, objArr);
            Event event = new Event(eventLabel.getLabelName(), str);
            event.putData(map);
            EventManager.sendEvent(event);
        }
    }

    public static void sendError(Throwable th, String str, String str2) {
        EventLabel eventLabel = EventLabel.error;
        try {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("thread", Thread.currentThread().getName());
            if (th != null) {
                JSONObject jSONObject2 = new JSONObject();
                jSONObject2.put("message", th.getMessage());
                jSONObject2.put("stackTrace", Util.stackTraceAsString(th));
                jSONObject.put(KEY_EXCEPTION, jSONObject2);
            }
            if (str != null) {
                jSONObject.put("description", str);
            }
            if (str2 != null) {
                jSONObject.put("extraData", str2);
            }
            if (Configuration.load().isMetricsEnabled()) {
                ApptentiveLog.v("Sending Error Metric: %s, data: %s", eventLabel.getLabelName(), jSONObject.toString());
                EventManager.sendEvent(new Event(eventLabel.getLabelName(), jSONObject));
            }
        } catch (Throwable e) {
            ApptentiveLog.w("Error creating Error Metric. Nothing we can do but log this.", e, new Object[0]);
        }
    }
}
