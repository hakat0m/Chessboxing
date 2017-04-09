package com.gumtree.android.notifications;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NotificationAlertText {
    @SerializedName("action-loc-key")
    @Expose
    private String actionLocKey;
    @Expose
    private String body;

    public String getBody() {
        return this.body;
    }

    public void setBody(String str) {
        this.body = str;
    }

    public String getActionLocKey() {
        return this.actionLocKey;
    }

    public void setActionLocKey(String str) {
        this.actionLocKey = str;
    }
}
