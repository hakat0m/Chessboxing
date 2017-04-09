package com.apptentive.android.sdk.model;

import com.apptentive.android.sdk.ApptentiveLog;
import org.json.JSONException;
import org.json.JSONObject;

public class ConversationTokenRequest extends JSONObject {
    public void setDevice(Device device) {
        try {
            put(Device.KEY, device);
        } catch (JSONException e) {
            ApptentiveLog.e("Error adding %s to ConversationTokenRequest", Device.KEY);
        }
    }

    public void setSdk(Sdk sdk) {
        try {
            put(Sdk.KEY, sdk);
        } catch (JSONException e) {
            ApptentiveLog.e("Error adding %s to ConversationTokenRequest", Sdk.KEY);
        }
    }

    public void setPerson(Person person) {
        try {
            put(Person.KEY, person);
        } catch (JSONException e) {
            ApptentiveLog.e("Error adding %s to ConversationTokenRequest", Person.KEY);
        }
    }
}
