package com.gumtree.android.events;

import com.ebay.classifieds.capi.executor.ResponseException;

public class OnActivatedAccountEvent {
    private ResponseException responseException;
    private boolean success;

    public OnActivatedAccountEvent(boolean z, ResponseException responseException) {
        this.success = z;
        this.responseException = responseException;
    }

    public boolean isSuccess() {
        return this.success;
    }

    public ResponseException getResponseException() {
        return this.responseException;
    }
}
