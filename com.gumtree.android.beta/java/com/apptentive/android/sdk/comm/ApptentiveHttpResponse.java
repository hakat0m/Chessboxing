package com.apptentive.android.sdk.comm;

import com.gumtree.android.UrlMatcher;
import java.util.Map;
import uk.co.senab.photoview.IPhotoView;

public class ApptentiveHttpResponse {
    private boolean badPayload = false;
    private int code = -1;
    private String content = null;
    private Map<String, String> headers;
    private String reason = null;

    public boolean isException() {
        return this.code < 0;
    }

    public boolean isSuccessful() {
        return this.code >= IPhotoView.DEFAULT_ZOOM_DURATION && this.code < 300;
    }

    public boolean isRejectedPermanently() {
        return this.code >= 400 && this.code < UrlMatcher.URI_CODE_SAVED_SEARCHES;
    }

    public boolean isRejectedTemporarily() {
        return (isSuccessful() || isRejectedPermanently()) ? false : true;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String str) {
        this.content = str;
    }

    public String getReason() {
        return this.reason;
    }

    public void setReason(String str) {
        this.reason = str;
    }

    public Map<String, String> getHeaders() {
        return this.headers;
    }

    public void setHeaders(Map<String, String> map) {
        this.headers = map;
    }

    public int getCode() {
        return this.code;
    }

    public void setCode(int i) {
        this.code = i;
    }

    public boolean isBadPayload() {
        return this.badPayload;
    }

    public void setBadPayload(boolean z) {
        this.badPayload = z;
    }

    public boolean isZipped() {
        if (this.headers == null) {
            return false;
        }
        String str = (String) this.headers.get("Content-Encoding");
        if (str == null || !str.equalsIgnoreCase("[gzip]")) {
            return false;
        }
        return true;
    }
}
