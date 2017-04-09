package com.gumtree.android.common.transport;

import android.content.Context;
import android.net.http.HttpResponseCache;
import android.os.Build.VERSION;
import com.ebay.classifieds.capi.ICapiClient;
import com.gumtree.android.GumtreeApplication;
import com.gumtree.android.common.http.TrackingInfo;
import com.gumtree.android.common.utils.crashlytics.CrashlyticsHelper;
import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;

public class HttpUrlConnectionTransport implements Transport {
    private static final long HTTP_CACHE_SIZE = 10485760;
    public static final String HTTP_URL_MESSAGE_KEY = "http_response";
    public static final String MAX_AGE_0 = "max-age=0";
    public static final String MAX_STALE = "max-stale=";
    public static final String MIN_FRESH_10_SEC = "min-fresh=10";
    public static final String NO_CACHE = "no_cache";
    public static final String ONLY_CACHED = "only-if-cached";
    private static final int TIMEOUT = 60000;

    public HttpUrlConnectionTransport(Context context) {
        disableConnectionReuseIfNecessary();
        enableHttpResponseCache(context);
    }

    private static void enableHttpResponseCache(Context context) {
        try {
            HttpResponseCache.install(new File(context.getCacheDir(), "http"), HTTP_CACHE_SIZE);
        } catch (Throwable e) {
            CrashlyticsHelper.getInstance().catchGumtreeException(e);
        }
    }

    private void disableConnectionReuseIfNecessary() {
        if (Integer.parseInt(VERSION.SDK) < 8) {
            System.setProperty("http.keepAlive", "false");
        }
    }

    public Request prepare(RequestMethod requestMethod, String str) throws IOException {
        HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(str).openConnection();
        try {
            prepare(httpURLConnection, requestMethod);
            Request loggingHttpUrlConnectionRequest = new LoggingHttpUrlConnectionRequest(httpURLConnection);
            return loggingHttpUrlConnectionRequest;
        } finally {
            httpURLConnection.disconnect();
        }
    }

    private void prepare(HttpURLConnection httpURLConnection, RequestMethod requestMethod) throws ProtocolException {
        httpURLConnection.setConnectTimeout(TIMEOUT);
        httpURLConnection.setReadTimeout(TIMEOUT);
        httpURLConnection.setRequestMethod(requestMethod.name());
        httpURLConnection.setDoInput(true);
        httpURLConnection.setUseCaches(true);
        TrackingInfo trackingInfo = GumtreeApplication.getTrackingInfo();
        httpURLConnection.addRequestProperty(ICapiClient.ECG_USER_AGENT, trackingInfo.getUserAgent());
        httpURLConnection.addRequestProperty(ICapiClient.ECG_UID, trackingInfo.getUID());
        httpURLConnection.addRequestProperty(ICapiClient.ECG_VERSION, trackingInfo.getVersion());
        if (trackingInfo.isUserUIDValid()) {
            httpURLConnection.addRequestProperty(ICapiClient.ECG_USER_UID, trackingInfo.getUserUID());
        }
    }
}
