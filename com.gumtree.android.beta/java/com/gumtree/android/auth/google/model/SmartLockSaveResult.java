package com.gumtree.android.auth.google.model;

import com.gumtree.android.auth.model.AuthResult;
import java.beans.ConstructorProperties;

public final class SmartLockSaveResult {
    public static final String CANCELED = "canceled";
    public static final String FAILURE = "failure";
    public static final String RESOLVING = "resolving";
    public static final String SUCCESS = "success";
    private final AuthResult authResult;
    private final String status;

    @ConstructorProperties({"authResult", "status"})
    public SmartLockSaveResult(AuthResult authResult, String str) {
        this.authResult = authResult;
        this.status = str;
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof SmartLockSaveResult)) {
            return false;
        }
        SmartLockSaveResult smartLockSaveResult = (SmartLockSaveResult) obj;
        AuthResult authResult = getAuthResult();
        AuthResult authResult2 = smartLockSaveResult.getAuthResult();
        if (authResult != null ? !authResult.equals(authResult2) : authResult2 != null) {
            return false;
        }
        String status = getStatus();
        String status2 = smartLockSaveResult.getStatus();
        if (status == null) {
            if (status2 == null) {
                return true;
            }
        } else if (status.equals(status2)) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        int i = 43;
        AuthResult authResult = getAuthResult();
        int hashCode = (authResult == null ? 43 : authResult.hashCode()) + 59;
        String status = getStatus();
        hashCode *= 59;
        if (status != null) {
            i = status.hashCode();
        }
        return hashCode + i;
    }

    public String toString() {
        return "SmartLockSaveResult(authResult=" + getAuthResult() + ", status=" + getStatus() + ")";
    }

    public AuthResult getAuthResult() {
        return this.authResult;
    }

    public String getStatus() {
        return this.status;
    }
}
