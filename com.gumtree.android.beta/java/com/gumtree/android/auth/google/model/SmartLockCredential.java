package com.gumtree.android.auth.google.model;

import java.beans.ConstructorProperties;

public final class SmartLockCredential {
    private final String fullName;
    private final String id;
    private final String password;
    private final boolean resolving;

    @ConstructorProperties({"id", "fullName", "password", "resolving"})
    public SmartLockCredential(String str, String str2, String str3, boolean z) {
        this.id = str;
        this.fullName = str2;
        this.password = str3;
        this.resolving = z;
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof SmartLockCredential)) {
            return false;
        }
        SmartLockCredential smartLockCredential = (SmartLockCredential) obj;
        String id = getId();
        String id2 = smartLockCredential.getId();
        if (id != null ? !id.equals(id2) : id2 != null) {
            return false;
        }
        id = getFullName();
        id2 = smartLockCredential.getFullName();
        if (id != null ? !id.equals(id2) : id2 != null) {
            return false;
        }
        id = getPassword();
        id2 = smartLockCredential.getPassword();
        if (id != null ? !id.equals(id2) : id2 != null) {
            return false;
        }
        return isResolving() == smartLockCredential.isResolving();
    }

    public int hashCode() {
        int i = 43;
        String id = getId();
        int hashCode = (id == null ? 43 : id.hashCode()) + 59;
        String fullName = getFullName();
        hashCode = (fullName == null ? 43 : fullName.hashCode()) + (hashCode * 59);
        fullName = getPassword();
        hashCode *= 59;
        if (fullName != null) {
            i = fullName.hashCode();
        }
        return (isResolving() ? 79 : 97) + ((hashCode + i) * 59);
    }

    public String toString() {
        return "SmartLockCredential(id=" + getId() + ", fullName=" + getFullName() + ", password=" + getPassword() + ", resolving=" + isResolving() + ")";
    }

    public String getId() {
        return this.id;
    }

    public String getFullName() {
        return this.fullName;
    }

    public String getPassword() {
        return this.password;
    }

    public boolean isResolving() {
        return this.resolving;
    }
}
