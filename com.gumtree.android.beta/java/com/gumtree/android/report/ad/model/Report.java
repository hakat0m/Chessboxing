package com.gumtree.android.report.ad.model;

import java.beans.ConstructorProperties;

public class Report {
    private String comments;
    private String reason;
    private String username;

    @ConstructorProperties({"reason", "comments", "username"})
    public Report(String str, String str2, String str3) {
        this.reason = str;
        this.comments = str2;
        this.username = str3;
    }

    protected boolean canEqual(Object obj) {
        return obj instanceof Report;
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Report)) {
            return false;
        }
        Report report = (Report) obj;
        if (!report.canEqual(this)) {
            return false;
        }
        String reason = getReason();
        String reason2 = report.getReason();
        if (reason != null ? !reason.equals(reason2) : reason2 != null) {
            return false;
        }
        reason = getComments();
        reason2 = report.getComments();
        if (reason != null ? !reason.equals(reason2) : reason2 != null) {
            return false;
        }
        reason = getUsername();
        reason2 = report.getUsername();
        if (reason == null) {
            if (reason2 == null) {
                return true;
            }
        } else if (reason.equals(reason2)) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        int i = 43;
        String reason = getReason();
        int hashCode = (reason == null ? 43 : reason.hashCode()) + 59;
        String comments = getComments();
        hashCode = (comments == null ? 43 : comments.hashCode()) + (hashCode * 59);
        comments = getUsername();
        hashCode *= 59;
        if (comments != null) {
            i = comments.hashCode();
        }
        return hashCode + i;
    }

    public void setComments(String str) {
        this.comments = str;
    }

    public void setReason(String str) {
        this.reason = str;
    }

    public void setUsername(String str) {
        this.username = str;
    }

    public String toString() {
        return "Report(reason=" + getReason() + ", comments=" + getComments() + ", username=" + getUsername() + ")";
    }

    public String getReason() {
        return this.reason;
    }

    public String getComments() {
        return this.comments;
    }

    public String getUsername() {
        return this.username;
    }
}
