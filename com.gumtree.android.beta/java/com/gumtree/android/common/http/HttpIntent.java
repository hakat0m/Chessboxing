package com.gumtree.android.common.http;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.ResultReceiver;
import android.text.TextUtils;
import com.apptentive.android.sdk.BuildConfig;

public class HttpIntent {
    private static final String KEY_AD_TYPE = "key_ad_type";
    private static final String KEY_AUTHORIZATION = "key_authorization";
    private static final String KEY_CACHE_CONTROL = "key_cache_control";
    private static final String KEY_CALL_TIMESTAMP = "key_call_timestamp";
    private static final String KEY_CONTENT_TYPE = "key_content_type";
    private static final String KEY_PAYLOAD = "key_payload";
    private static final String KEY_SHOW_CACHED_BEFORE_LIVE = "key_show_cached_before_live";
    private static final String KEY_TRACKING_AUTOCOMPLETE = "key_tracking_autocomplete";
    public static final int REQUEST_TYPE_DELETE = 3;
    public static final int REQUEST_TYPE_GET = 0;
    public static final int REQUEST_TYPE_POST = 2;
    public static final int REQUEST_TYPE_PUT = 1;
    private final String KEY_RECEIVER = "key_receiver";
    private final String KEY_REQUEST_TYPE = "key_request_type";
    private final String KEY_SKIP_RESPONSE_PARSING = "key_skip_response_parsing";
    private final String KEY_VERIFIER = "key_verify_http_intent";
    private final Intent intent;

    protected HttpIntent(Context context) {
        this.intent = new Intent(context, CAPIHttpIntentService.class);
        this.intent.putExtra("key_verify_http_intent", true);
    }

    protected HttpIntent(Context context, Intent intent) {
        if (intent == null || !intent.getBooleanExtra("key_verify_http_intent", false)) {
            this.intent = new Intent(context, CAPIHttpIntentService.class);
            this.intent.putExtra("key_verify_http_intent", true);
            return;
        }
        this.intent = intent;
    }

    public void setBaseUrl(String str, String str2) {
        this.intent.setData(Uri.parse(str).buildUpon().appendPath(str2).build());
    }

    protected void addParameter(String str, String str2) {
        Uri data = this.intent.getData();
        if (TextUtils.isEmpty(data + BuildConfig.FLAVOR)) {
            throw new IllegalStateException("Set a base url before setting params");
        }
        this.intent.setData(data.buildUpon().appendQueryParameter(str, str2).build());
    }

    protected void setSkipResponseParsing(boolean z) {
        this.intent.putExtra("key_skip_response_parsing", z);
    }

    public ResultReceiver getResultReceiver() {
        return (ResultReceiver) this.intent.getParcelableExtra("key_receiver");
    }

    protected void setResultReceiver(ResultReceiver resultReceiver) {
        this.intent.putExtra("key_receiver", resultReceiver);
    }

    public int getRequestType() {
        return this.intent.getIntExtra("key_request_type", REQUEST_TYPE_GET);
    }

    protected void setRequestType(int i) throws IllegalStateException {
        if (i > REQUEST_TYPE_DELETE || i < 0) {
            throw new IllegalStateException("Request type does not exists");
        }
        this.intent.putExtra("key_request_type", i);
    }

    public String getUrl() {
        return this.intent.getDataString();
    }

    public String getBaseUrl() {
        return this.intent.getData().getScheme() + "://" + this.intent.getData().getAuthority();
    }

    protected void setBaseUrl(String str) {
        this.intent.setData(Uri.parse(str));
    }

    public String getTimestamp() {
        return String.valueOf(this.intent.getLongExtra(KEY_CALL_TIMESTAMP, System.currentTimeMillis()));
    }

    protected void setTimestamp(long j) {
        this.intent.putExtra(KEY_CALL_TIMESTAMP, j);
    }

    public String getPath() {
        return this.intent.getData().getPath();
    }

    protected void setPath(String str) {
        Uri data = this.intent.getData();
        if (TextUtils.isEmpty(data + BuildConfig.FLAVOR)) {
            throw new IllegalStateException("Set a base url before setting path");
        }
        this.intent.setData(data.buildUpon().appendEncodedPath(str).build());
    }

    public Intent getIntent() {
        return this.intent;
    }

    public String getContentType() {
        return this.intent.getStringExtra(KEY_CONTENT_TYPE);
    }

    protected void setContentType(String str) {
        this.intent.putExtra(KEY_CONTENT_TYPE, str);
    }

    public String getPayload() {
        return this.intent.getStringExtra(KEY_PAYLOAD);
    }

    protected void setPayload(String str) {
        this.intent.putExtra(KEY_PAYLOAD, str);
    }

    public String getCacheControlStatus() {
        return this.intent.getStringExtra(KEY_CACHE_CONTROL);
    }

    public boolean hasPayload() {
        return !TextUtils.isEmpty(this.intent.getStringExtra(KEY_PAYLOAD));
    }

    public boolean skipResponseParsing() {
        return this.intent.getBooleanExtra("key_skip_response_parsing", false);
    }

    protected void setAuthorization(String str) {
        this.intent.putExtra(KEY_AUTHORIZATION, str);
    }

    protected void setTrackingAutocomplete(String str) {
        this.intent.putExtra(KEY_TRACKING_AUTOCOMPLETE, str);
    }

    protected void setCacheControl(String str) {
        this.intent.putExtra(KEY_CACHE_CONTROL, str);
    }

    public String getAuthorization() {
        return this.intent.getStringExtra(KEY_AUTHORIZATION);
    }

    public String getAdType() {
        return this.intent.getStringExtra(KEY_AD_TYPE);
    }

    protected void setAdType(String str) {
        this.intent.putExtra(KEY_AD_TYPE, str);
    }

    protected void setShowCachedDataBeforeLive(boolean z) {
        this.intent.putExtra(KEY_SHOW_CACHED_BEFORE_LIVE, z);
    }

    public boolean isSetShowCachedDataBeforeLive() {
        return this.intent.getBooleanExtra(KEY_SHOW_CACHED_BEFORE_LIVE, false);
    }

    public String getTracking() {
        return this.intent.getStringExtra(KEY_TRACKING_AUTOCOMPLETE);
    }
}
