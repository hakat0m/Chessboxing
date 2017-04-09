package com.gumtree.android.common.http;

import android.content.Intent;
import android.os.ResultReceiver;
import com.gumtree.android.GumtreeApplication;

public class HttpIntentBuilder {
    private HttpIntent httpIntent;

    public HttpIntentBuilder createNewHttpIntent() {
        this.httpIntent = new HttpIntent(GumtreeApplication.getContext());
        return this;
    }

    public HttpIntentBuilder createHttpIntentFromIntent(Intent intent) {
        this.httpIntent = new HttpIntent(GumtreeApplication.getContext(), intent);
        return this;
    }

    public HttpIntentBuilder setBaseUrl(String str) {
        this.httpIntent.setBaseUrl(str);
        return this;
    }

    public HttpIntentBuilder setBaseUrl(String str, String str2) {
        this.httpIntent.setBaseUrl(str, str2);
        return this;
    }

    public HttpIntentBuilder setPath(String str) {
        this.httpIntent.setPath(str);
        return this;
    }

    public HttpIntentBuilder setTimestamp(long j) {
        this.httpIntent.setTimestamp(j);
        return this;
    }

    public HttpIntentBuilder setRequestType(int i) {
        this.httpIntent.setRequestType(i);
        return this;
    }

    public HttpIntentBuilder addRequestParameter(String str, String str2) {
        this.httpIntent.addParameter(str, str2);
        return this;
    }

    public HttpIntentBuilder setResultReceiver(ResultReceiver resultReceiver) {
        this.httpIntent.setResultReceiver(resultReceiver);
        return this;
    }

    public HttpIntentBuilder setPayload(String str) {
        this.httpIntent.setPayload(str);
        return this;
    }

    public HttpIntentBuilder setContentType(String str) {
        this.httpIntent.setContentType(str);
        return this;
    }

    public HttpIntentBuilder showCachedDataBeforeLive(boolean z) {
        this.httpIntent.setShowCachedDataBeforeLive(z);
        return this;
    }

    public HttpIntentBuilder setCacheControlProperty(String str) {
        this.httpIntent.setCacheControl(str);
        return this;
    }

    public HttpIntentBuilder setAuthorizationHeader(String str) {
        this.httpIntent.setAuthorization(str);
        return this;
    }

    public HttpIntentBuilder setTrackingAutocompleteHeader(String str) {
        if (str != null) {
            this.httpIntent.setTrackingAutocomplete(str);
        }
        return this;
    }

    public HttpIntentBuilder setAdType(String str) {
        this.httpIntent.setAdType(str);
        return this;
    }

    public HttpIntent build() {
        return this.httpIntent;
    }

    public HttpIntentBuilder setSkipResponseParsing(boolean z) {
        this.httpIntent.setSkipResponseParsing(z);
        return this;
    }
}
