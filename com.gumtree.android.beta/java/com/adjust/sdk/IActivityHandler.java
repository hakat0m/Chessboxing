package com.adjust.sdk;

import android.net.Uri;

public interface IActivityHandler {
    void finishedTrackingActivity(ResponseData responseData);

    void init(AdjustConfig adjustConfig);

    boolean isEnabled();

    void launchAttributionResponseTasks(AttributionResponseData attributionResponseData);

    void launchEventResponseTasks(EventResponseData eventResponseData);

    void launchSessionResponseTasks(SessionResponseData sessionResponseData);

    void onPause();

    void onResume();

    void readOpenUrl(Uri uri, long j);

    void sendReferrer(String str, long j);

    void setAskingAttribution(boolean z);

    void setEnabled(boolean z);

    void setOfflineMode(boolean z);

    void trackEvent(AdjustEvent adjustEvent);

    boolean updateAttribution(AdjustAttribution adjustAttribution);
}
