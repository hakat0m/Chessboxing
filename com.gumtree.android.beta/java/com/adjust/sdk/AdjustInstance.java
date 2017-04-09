package com.adjust.sdk;

import android.net.Uri;

public class AdjustInstance {
    private ActivityHandler activityHandler;
    private String referrer;
    private long referrerClickTime;

    private static ILogger getLogger() {
        return AdjustFactory.getLogger();
    }

    public void onCreate(AdjustConfig adjustConfig) {
        if (this.activityHandler != null) {
            getLogger().error("Adjust already initialized", new Object[0]);
            return;
        }
        adjustConfig.referrer = this.referrer;
        adjustConfig.referrerClickTime = this.referrerClickTime;
        this.activityHandler = ActivityHandler.getInstance(adjustConfig);
    }

    public void trackEvent(AdjustEvent adjustEvent) {
        if (checkActivityHandler()) {
            this.activityHandler.trackEvent(adjustEvent);
        }
    }

    public void onResume() {
        if (checkActivityHandler()) {
            this.activityHandler.onResume();
        }
    }

    public void onPause() {
        if (checkActivityHandler()) {
            this.activityHandler.onPause();
        }
    }

    public void setEnabled(boolean z) {
        if (checkActivityHandler()) {
            this.activityHandler.setEnabled(z);
        }
    }

    public boolean isEnabled() {
        if (checkActivityHandler()) {
            return this.activityHandler.isEnabled();
        }
        return false;
    }

    public void appWillOpenUrl(Uri uri) {
        if (checkActivityHandler()) {
            this.activityHandler.readOpenUrl(uri, System.currentTimeMillis());
        }
    }

    public void sendReferrer(String str) {
        long currentTimeMillis = System.currentTimeMillis();
        if (this.activityHandler == null) {
            this.referrer = str;
            this.referrerClickTime = currentTimeMillis;
            return;
        }
        this.activityHandler.sendReferrer(str, currentTimeMillis);
    }

    public void setOfflineMode(boolean z) {
        if (checkActivityHandler()) {
            this.activityHandler.setOfflineMode(z);
        }
    }

    private boolean checkActivityHandler() {
        if (this.activityHandler != null) {
            return true;
        }
        getLogger().error("Adjust not initialized correctly", new Object[0]);
        return false;
    }
}
