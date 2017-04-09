package com.adjust.sdk;

public interface ISdkClickHandler {
    void init(boolean z);

    void pauseSending();

    void resumeSending();

    void sendSdkClick(ActivityPackage activityPackage);
}
