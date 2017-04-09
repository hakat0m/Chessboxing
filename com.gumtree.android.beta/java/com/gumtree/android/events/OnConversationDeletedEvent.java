package com.gumtree.android.events;

import com.ebay.classifieds.capi.executor.ResponseException;

public class OnConversationDeletedEvent {
    private ResponseException mResponseException;
    private boolean mSuccess;

    public OnConversationDeletedEvent(boolean z, ResponseException responseException) {
        this.mSuccess = z;
        this.mResponseException = responseException;
    }

    public boolean isSuccess() {
        return this.mSuccess;
    }

    public ResponseException getResponseException() {
        return this.mResponseException;
    }
}
