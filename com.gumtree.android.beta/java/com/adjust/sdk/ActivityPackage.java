package com.adjust.sdk;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectInputStream.GetField;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamField;
import java.io.Serializable;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

public class ActivityPackage implements Serializable {
    private static final ObjectStreamField[] serialPersistentFields = new ObjectStreamField[]{new ObjectStreamField("path", String.class), new ObjectStreamField("clientSdk", String.class), new ObjectStreamField("parameters", Map.class), new ObjectStreamField("activityKind", ActivityKind.class), new ObjectStreamField("suffix", String.class)};
    private static final long serialVersionUID = -35935556512024097L;
    private ActivityKind activityKind = ActivityKind.UNKNOWN;
    private String clientSdk;
    private transient int hashCode;
    private Map<String, String> parameters;
    private String path;
    private int retries;
    private String suffix;

    public String getPath() {
        return this.path;
    }

    public void setPath(String str) {
        this.path = str;
    }

    public String getClientSdk() {
        return this.clientSdk;
    }

    public void setClientSdk(String str) {
        this.clientSdk = str;
    }

    public Map<String, String> getParameters() {
        return this.parameters;
    }

    public void setParameters(Map<String, String> map) {
        this.parameters = map;
    }

    public ActivityKind getActivityKind() {
        return this.activityKind;
    }

    public String getSuffix() {
        return this.suffix;
    }

    public void setSuffix(String str) {
        this.suffix = str;
    }

    public int getRetries() {
        return this.retries;
    }

    public int increaseRetries() {
        this.retries++;
        return this.retries;
    }

    public ActivityPackage(ActivityKind activityKind) {
        this.activityKind = activityKind;
    }

    public String toString() {
        return String.format(Locale.US, "%s%s", new Object[]{this.activityKind.toString(), this.suffix});
    }

    public String getExtendedString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(String.format(Locale.US, "Path:      %s\n", new Object[]{this.path}));
        stringBuilder.append(String.format(Locale.US, "ClientSdk: %s\n", new Object[]{this.clientSdk}));
        if (this.parameters != null) {
            stringBuilder.append("Parameters:");
            for (Entry entry : new TreeMap(this.parameters).entrySet()) {
                stringBuilder.append(String.format(Locale.US, "\n\t%-16s %s", new Object[]{entry.getKey(), entry.getValue()}));
            }
        }
        return stringBuilder.toString();
    }

    protected String getFailureMessage() {
        return String.format(Locale.US, "Failed to track %s%s", new Object[]{this.activityKind.toString(), this.suffix});
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();
    }

    private void readObject(ObjectInputStream objectInputStream) throws ClassNotFoundException, IOException {
        GetField readFields = objectInputStream.readFields();
        this.path = Util.readStringField(readFields, "path", null);
        this.clientSdk = Util.readStringField(readFields, "clientSdk", null);
        this.parameters = (Map) Util.readObjectField(readFields, "parameters", null);
        this.activityKind = (ActivityKind) Util.readObjectField(readFields, "activityKind", ActivityKind.UNKNOWN);
        this.suffix = Util.readStringField(readFields, "suffix", null);
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
        ActivityPackage activityPackage = (ActivityPackage) obj;
        if (!Util.equalString(this.path, activityPackage.path)) {
            return false;
        }
        if (!Util.equalString(this.clientSdk, activityPackage.clientSdk)) {
            return false;
        }
        if (!Util.equalsMap(this.parameters, activityPackage.parameters)) {
            return false;
        }
        if (!Util.equalEnum(this.activityKind, activityPackage.activityKind)) {
            return false;
        }
        if (Util.equalString(this.suffix, activityPackage.suffix)) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        if (this.hashCode == 0) {
            this.hashCode = 17;
            this.hashCode = (this.hashCode * 37) + Util.hashString(this.path);
            this.hashCode = (this.hashCode * 37) + Util.hashString(this.clientSdk);
            this.hashCode = (this.hashCode * 37) + Util.hashMap(this.parameters);
            this.hashCode = (this.hashCode * 37) + Util.hashEnum(this.activityKind);
            this.hashCode = (this.hashCode * 37) + Util.hashString(this.suffix);
        }
        return this.hashCode;
    }
}
