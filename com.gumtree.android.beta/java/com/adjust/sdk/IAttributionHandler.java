package com.adjust.sdk;

public interface IAttributionHandler {
    void checkSessionResponse(SessionResponseData sessionResponseData);

    void getAttribution();

    void init(IActivityHandler iActivityHandler, ActivityPackage activityPackage, boolean z, boolean z2);

    void pauseSending();

    void resumeSending();
}
