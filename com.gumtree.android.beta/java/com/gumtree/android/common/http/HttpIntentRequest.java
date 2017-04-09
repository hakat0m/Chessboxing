package com.gumtree.android.common.http;

import android.os.ResultReceiver;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import com.gumtree.android.GumtreeApplication;
import com.gumtree.android.common.Constants;
import com.gumtree.android.common.transport.Request;
import com.gumtree.android.common.transport.RequestMethod;
import com.gumtree.android.common.transport.Response;
import com.gumtree.android.common.transport.Transport;
import com.gumtree.android.common.utils.Log;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration$Builder;
import java.io.IOException;

public class HttpIntentRequest {
    public static final String REQUEST_HEADER_FOR_AD_TYPE = "X-ECG-AD-TYPE";
    public static final String REQUEST_HEADER_FOR_AUTH = "X-ECG-Authorization-User";
    public static final String REQUEST_HEADER_FOR_TRACKING_AUTOCOMPLETE = "X-ECG-SUGGESTIONS-TRACKER";
    private final HttpIntent intentRequest;

    public HttpIntentRequest(HttpIntent httpIntent) {
        this.intentRequest = httpIntent;
    }

    public Response makeRequest(Transport transport) throws IOException {
        String url = this.intentRequest.getUrl();
        if (Log.verboseLoggingEnabled()) {
            Log.v("executing " + this.intentRequest.getRequestType() + " for " + url);
        }
        Request prepare;
        switch (this.intentRequest.getRequestType()) {
            case HighlightView.GROW_NONE /*1*/:
                prepare = transport.prepare(RequestMethod.PUT, url);
                if (hasPayload()) {
                    prepare.setPayload(new BinaryStringPayloadPayload(this, null));
                }
                return prepare.execute();
            case HighlightView.GROW_LEFT_EDGE /*2*/:
                prepare = transport.prepare(RequestMethod.POST, url);
                if (hasAdType()) {
                    prepare.addHeader(REQUEST_HEADER_FOR_AD_TYPE, this.intentRequest.getAdType());
                }
                if (hasPayload()) {
                    prepare.setPayload(new BinaryStringPayloadPayload(this, null));
                }
                return prepare.execute();
            case ImageLoaderConfiguration$Builder.DEFAULT_THREAD_POOL_SIZE /*3*/:
                prepare = transport.prepare(RequestMethod.DELETE, url);
                authorize(prepare);
                return prepare.execute();
            default:
                prepare = transport.prepare(RequestMethod.GET, url);
                authorize(prepare);
                tracking(prepare);
                addCacheControl(prepare);
                return prepare.execute();
        }
    }

    private void tracking(Request request) {
        Object tracking = this.intentRequest.getTracking();
        if (!TextUtils.isEmpty(tracking)) {
            PreferenceManager.getDefaultSharedPreferences(GumtreeApplication.getContext()).edit().remove(Constants.PREF_AUTO_COMPLETE_TRACKING).commit();
            request.setHeader(REQUEST_HEADER_FOR_TRACKING_AUTOCOMPLETE, tracking);
        }
    }

    private void addCacheControl(Request request) {
        if (!TextUtils.isEmpty(this.intentRequest.getCacheControlStatus())) {
            request.setCacheControl(this.intentRequest.getCacheControlStatus());
        }
    }

    private void authorize(Request request) {
        Object authorization = this.intentRequest.getAuthorization();
        if (!TextUtils.isEmpty(authorization)) {
            request.setHeader(REQUEST_HEADER_FOR_AUTH, authorization);
        }
    }

    private boolean hasPayload() {
        return this.intentRequest.getPayload() != null;
    }

    private boolean hasAdType() {
        return this.intentRequest.getAdType() != null;
    }

    public String getTimestamp() {
        return this.intentRequest.getTimestamp();
    }

    public ResultReceiver getResultReceiver() {
        return this.intentRequest.getResultReceiver();
    }

    public boolean skipResponseParsing() {
        return this.intentRequest.skipResponseParsing();
    }

    public String getUrl() {
        return this.intentRequest.getUrl();
    }

    public HttpIntent getIntentRequest() {
        return this.intentRequest;
    }
}
