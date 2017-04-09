package com.adjust.sdk;

public class SessionResponseData extends ResponseData {
    public AdjustSessionSuccess getSuccessResponseData() {
        if (!this.success) {
            return null;
        }
        AdjustSessionSuccess adjustSessionSuccess = new AdjustSessionSuccess();
        adjustSessionSuccess.message = this.message;
        adjustSessionSuccess.timestamp = this.timestamp;
        adjustSessionSuccess.adid = this.adid;
        adjustSessionSuccess.jsonResponse = this.jsonResponse;
        return adjustSessionSuccess;
    }

    public AdjustSessionFailure getFailureResponseData() {
        if (this.success) {
            return null;
        }
        AdjustSessionFailure adjustSessionFailure = new AdjustSessionFailure();
        adjustSessionFailure.message = this.message;
        adjustSessionFailure.timestamp = this.timestamp;
        adjustSessionFailure.adid = this.adid;
        adjustSessionFailure.willRetry = this.willRetry;
        adjustSessionFailure.jsonResponse = this.jsonResponse;
        return adjustSessionFailure;
    }
}
