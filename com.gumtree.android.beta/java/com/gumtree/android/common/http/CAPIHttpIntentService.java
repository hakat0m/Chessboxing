package com.gumtree.android.common.http;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.text.TextUtils;
import com.gumtree.android.UrlMatcher;
import com.gumtree.android.common.transport.HttpUrlConnectionTransport;
import com.gumtree.android.common.utils.Log;
import com.gumtree.android.common.utils.crashlytics.CrashlyticsHelper;

public class CAPIHttpIntentService extends IntentService implements HttpService {
    public static final String URL = "URL";
    private final GumtreeRestExecutor restExecutor = new GumtreeRestExecutor();

    public CAPIHttpIntentService() {
        super("HTTP");
    }

    public void executeRequest(HttpIntentRequest httpIntentRequest) {
        Bundle bundleWithCacheData = getBundleWithCacheData(httpIntentRequest);
        try {
            sendResultToReceiver(this.restExecutor.execute(), bundleWithCacheData, httpIntentRequest);
        } catch (Exception e) {
            onException(httpIntentRequest, bundleWithCacheData, e);
        }
    }

    private Bundle getBundleWithCacheData(HttpIntentRequest httpIntentRequest) {
        Bundle bundle = new Bundle();
        if (!(httpIntentRequest.getIntentRequest() == null || TextUtils.isEmpty(httpIntentRequest.getIntentRequest().getCacheControlStatus()))) {
            bundle.putString(HttpUrlConnectionTransport.HTTP_URL_MESSAGE_KEY, httpIntentRequest.getIntentRequest().getCacheControlStatus());
        }
        bundle.putString(URL, httpIntentRequest.getUrl());
        return bundle;
    }

    private void onException(HttpIntentRequest httpIntentRequest, Bundle bundle, Exception exception) {
        sendResultToReceiver(UrlMatcher.URI_CODE_SAVED_SEARCHES, bundle, httpIntentRequest);
        logErrorMessageWithCause(exception);
        CrashlyticsHelper.getInstance().catchGumtreeException(exception);
    }

    private void sendResultToReceiver(int i, Bundle bundle, HttpIntentRequest httpIntentRequest) {
        ResultReceiver resultReceiver = httpIntentRequest.getResultReceiver();
        if (resultReceiver != null) {
            resultReceiver.send(i, bundle);
        }
    }

    private void logErrorMessageWithCause(Exception exception) {
        Log.e(exception.getLocalizedMessage());
        if (exception.getCause() != null) {
            Log.e(exception.getCause().getLocalizedMessage());
        }
    }

    private RestExecutor getRestExecutor() {
        return this.restExecutor;
    }

    protected void onHandleIntent(Intent intent) {
        executeRequest(getRestExecutor().prepareRequest(intent));
    }
}
