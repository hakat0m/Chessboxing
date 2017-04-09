package com.adjust.sdk;

public class EventResponseData extends ResponseData {
    public String eventToken;

    public EventResponseData(ActivityPackage activityPackage) {
        this.eventToken = (String) activityPackage.getParameters().get("event_token");
    }

    public AdjustEventSuccess getSuccessResponseData() {
        if (!this.success) {
            return null;
        }
        AdjustEventSuccess adjustEventSuccess = new AdjustEventSuccess();
        adjustEventSuccess.message = this.message;
        adjustEventSuccess.timestamp = this.timestamp;
        adjustEventSuccess.adid = this.adid;
        adjustEventSuccess.jsonResponse = this.jsonResponse;
        adjustEventSuccess.eventToken = this.eventToken;
        return adjustEventSuccess;
    }

    public AdjustEventFailure getFailureResponseData() {
        if (this.success) {
            return null;
        }
        AdjustEventFailure adjustEventFailure = new AdjustEventFailure();
        adjustEventFailure.message = this.message;
        adjustEventFailure.timestamp = this.timestamp;
        adjustEventFailure.adid = this.adid;
        adjustEventFailure.willRetry = this.willRetry;
        adjustEventFailure.jsonResponse = this.jsonResponse;
        adjustEventFailure.eventToken = this.eventToken;
        return adjustEventFailure;
    }
}
