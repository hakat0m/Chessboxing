package com.adjust.sdk;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectInputStream.GetField;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamField;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Locale;

public class ActivityState implements Serializable, Cloneable {
    private static final ObjectStreamField[] serialPersistentFields = new ObjectStreamField[]{new ObjectStreamField("uuid", String.class), new ObjectStreamField("enabled", Boolean.TYPE), new ObjectStreamField("askingAttribution", Boolean.TYPE), new ObjectStreamField("eventCount", Integer.TYPE), new ObjectStreamField("sessionCount", Integer.TYPE), new ObjectStreamField("subsessionCount", Integer.TYPE), new ObjectStreamField("sessionLength", Long.TYPE), new ObjectStreamField("timeSpent", Long.TYPE), new ObjectStreamField("lastActivity", Long.TYPE), new ObjectStreamField("lastInterval", Long.TYPE)};
    private static final long serialVersionUID = 9039439291143138148L;
    protected boolean askingAttribution = false;
    protected boolean enabled = true;
    protected int eventCount = 0;
    protected long lastActivity = -1;
    protected long lastInterval = -1;
    private transient ILogger logger = AdjustFactory.getLogger();
    protected int sessionCount = 0;
    protected long sessionLength = -1;
    protected int subsessionCount = -1;
    protected long timeSpent = -1;
    protected String uuid = Util.createUuid();

    protected ActivityState() {
    }

    protected void resetSessionAttributes(long j) {
        this.subsessionCount = 1;
        this.sessionLength = 0;
        this.timeSpent = 0;
        this.lastActivity = j;
        this.lastInterval = -1;
    }

    public String toString() {
        return String.format(Locale.US, "ec:%d sc:%d ssc:%d sl:%.1f ts:%.1f la:%s uuid:%s", new Object[]{Integer.valueOf(this.eventCount), Integer.valueOf(this.sessionCount), Integer.valueOf(this.subsessionCount), Double.valueOf(((double) this.sessionLength) / 1000.0d), Double.valueOf(((double) this.timeSpent) / 1000.0d), stamp(this.lastActivity), this.uuid});
    }

    public ActivityState shallowCopy() {
        try {
            return (ActivityState) super.clone();
        } catch (CloneNotSupportedException e) {
            return null;
        }
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
        ActivityState activityState = (ActivityState) obj;
        if (!Util.equalString(this.uuid, activityState.uuid)) {
            return false;
        }
        if (!Util.equalBoolean(Boolean.valueOf(this.enabled), Boolean.valueOf(activityState.enabled))) {
            return false;
        }
        if (!Util.equalBoolean(Boolean.valueOf(this.askingAttribution), Boolean.valueOf(activityState.askingAttribution))) {
            return false;
        }
        if (!Util.equalInt(Integer.valueOf(this.eventCount), Integer.valueOf(activityState.eventCount))) {
            return false;
        }
        if (!Util.equalInt(Integer.valueOf(this.sessionCount), Integer.valueOf(activityState.sessionCount))) {
            return false;
        }
        if (!Util.equalInt(Integer.valueOf(this.subsessionCount), Integer.valueOf(activityState.subsessionCount))) {
            return false;
        }
        if (!Util.equalLong(Long.valueOf(this.sessionLength), Long.valueOf(activityState.sessionLength))) {
            return false;
        }
        if (!Util.equalLong(Long.valueOf(this.timeSpent), Long.valueOf(activityState.timeSpent))) {
            return false;
        }
        if (Util.equalLong(Long.valueOf(this.lastInterval), Long.valueOf(activityState.lastInterval))) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return ((((((((((((((((Util.hashString(this.uuid) + 629) * 37) + Util.hashBoolean(Boolean.valueOf(this.enabled))) * 37) + Util.hashBoolean(Boolean.valueOf(this.askingAttribution))) * 37) + this.eventCount) * 37) + this.sessionCount) * 37) + this.subsessionCount) * 37) + Util.hashLong(Long.valueOf(this.sessionLength))) * 37) + Util.hashLong(Long.valueOf(this.timeSpent))) * 37) + Util.hashLong(Long.valueOf(this.lastInterval));
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        GetField readFields = objectInputStream.readFields();
        this.eventCount = Util.readIntField(readFields, "eventCount", 0);
        this.sessionCount = Util.readIntField(readFields, "sessionCount", 0);
        this.subsessionCount = Util.readIntField(readFields, "subsessionCount", -1);
        this.sessionLength = Util.readLongField(readFields, "sessionLength", -1);
        this.timeSpent = Util.readLongField(readFields, "timeSpent", -1);
        this.lastActivity = Util.readLongField(readFields, "lastActivity", -1);
        this.lastInterval = Util.readLongField(readFields, "lastInterval", -1);
        this.uuid = Util.readStringField(readFields, "uuid", null);
        this.enabled = Util.readBooleanField(readFields, "enabled", true);
        this.askingAttribution = Util.readBooleanField(readFields, "askingAttribution", false);
        if (this.uuid == null) {
            this.uuid = Util.createUuid();
        }
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();
    }

    private static String stamp(long j) {
        Calendar.getInstance().setTimeInMillis(j);
        return String.format(Locale.US, "%02d:%02d:%02d", new Object[]{Integer.valueOf(11), Integer.valueOf(12), Integer.valueOf(13)});
    }
}
