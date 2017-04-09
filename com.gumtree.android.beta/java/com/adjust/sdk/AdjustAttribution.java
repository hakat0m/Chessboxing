package com.adjust.sdk;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamField;
import java.io.Serializable;
import java.util.Locale;
import org.json.JSONObject;

public class AdjustAttribution implements Serializable {
    private static final ObjectStreamField[] serialPersistentFields = new ObjectStreamField[]{new ObjectStreamField("trackerToken", String.class), new ObjectStreamField("trackerName", String.class), new ObjectStreamField("network", String.class), new ObjectStreamField("campaign", String.class), new ObjectStreamField("adgroup", String.class), new ObjectStreamField("creative", String.class), new ObjectStreamField("clickLabel", String.class)};
    private static final long serialVersionUID = 1;
    public String adgroup;
    public String campaign;
    public String clickLabel;
    public String creative;
    public String network;
    public String trackerName;
    public String trackerToken;

    public static AdjustAttribution fromJson(JSONObject jSONObject) {
        if (jSONObject == null) {
            return null;
        }
        AdjustAttribution adjustAttribution = new AdjustAttribution();
        adjustAttribution.trackerToken = jSONObject.optString("tracker_token", null);
        adjustAttribution.trackerName = jSONObject.optString("tracker_name", null);
        adjustAttribution.network = jSONObject.optString("network", null);
        adjustAttribution.campaign = jSONObject.optString("campaign", null);
        adjustAttribution.adgroup = jSONObject.optString("adgroup", null);
        adjustAttribution.creative = jSONObject.optString("creative", null);
        adjustAttribution.clickLabel = jSONObject.optString("click_label", null);
        return adjustAttribution;
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        AdjustAttribution adjustAttribution = (AdjustAttribution) obj;
        if (!Util.equalString(this.trackerToken, adjustAttribution.trackerToken)) {
            return false;
        }
        if (!Util.equalString(this.trackerName, adjustAttribution.trackerName)) {
            return false;
        }
        if (!Util.equalString(this.network, adjustAttribution.network)) {
            return false;
        }
        if (!Util.equalString(this.campaign, adjustAttribution.campaign)) {
            return false;
        }
        if (!Util.equalString(this.adgroup, adjustAttribution.adgroup)) {
            return false;
        }
        if (!Util.equalString(this.creative, adjustAttribution.creative)) {
            return false;
        }
        if (Util.equalString(this.clickLabel, adjustAttribution.clickLabel)) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return ((((((((((((Util.hashString(this.trackerToken) + 629) * 37) + Util.hashString(this.trackerName)) * 37) + Util.hashString(this.network)) * 37) + Util.hashString(this.campaign)) * 37) + Util.hashString(this.adgroup)) * 37) + Util.hashString(this.creative)) * 37) + Util.hashString(this.clickLabel);
    }

    public String toString() {
        return String.format(Locale.US, "tt:%s tn:%s net:%s cam:%s adg:%s cre:%s cl:%s", new Object[]{this.trackerToken, this.trackerName, this.network, this.campaign, this.adgroup, this.creative, this.clickLabel});
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();
    }

    private void readObject(ObjectInputStream objectInputStream) throws ClassNotFoundException, IOException {
        objectInputStream.defaultReadObject();
    }
}
