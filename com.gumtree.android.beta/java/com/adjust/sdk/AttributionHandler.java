package com.adjust.sdk;

import android.net.Uri;
import android.net.Uri.Builder;
import com.adjust.sdk.AdjustFactory.URLGetConnection;
import java.net.URL;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class AttributionHandler implements IAttributionHandler {
    private static final String ATTRIBUTION_TIMER_NAME = "Attribution timer";
    private IActivityHandler activityHandler;
    private ActivityPackage attributionPackage;
    private boolean hasListener;
    public URL lastUrlUsed;
    private ILogger logger = AdjustFactory.getLogger();
    private boolean paused;
    private ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
    private TimerOnce timer;

    public AttributionHandler(IActivityHandler iActivityHandler, ActivityPackage activityPackage, boolean z, boolean z2) {
        if (this.scheduler != null) {
            this.timer = new TimerOnce(this.scheduler, new Runnable() {
                public void run() {
                    AttributionHandler.this.getAttributionInternal();
                }
            }, ATTRIBUTION_TIMER_NAME);
        } else {
            this.logger.error("Timer not initialized, attribution handler is disabled", new Object[0]);
        }
        init(iActivityHandler, activityPackage, z, z2);
    }

    public void init(IActivityHandler iActivityHandler, ActivityPackage activityPackage, boolean z, boolean z2) {
        this.activityHandler = iActivityHandler;
        this.attributionPackage = activityPackage;
        this.paused = !z;
        this.hasListener = z2;
    }

    public void getAttribution() {
        getAttribution(0);
    }

    public void checkSessionResponse(final SessionResponseData sessionResponseData) {
        this.scheduler.submit(new Runnable() {
            public void run() {
                AttributionHandler.this.checkSessionResponseInternal(sessionResponseData);
            }
        });
    }

    private void checkAttributionResponse(final AttributionResponseData attributionResponseData) {
        this.scheduler.submit(new Runnable() {
            public void run() {
                AttributionHandler.this.checkAttributionResponseInternal(attributionResponseData);
            }
        });
    }

    public void pauseSending() {
        this.paused = true;
    }

    public void resumeSending() {
        this.paused = false;
    }

    private void getAttribution(long j) {
        if (this.timer.getFireIn() <= j) {
            if (j != 0) {
                String format = Util.SecondsDisplayFormat.format(((double) j) / 1000.0d);
                this.logger.debug("Waiting to query attribution in %s seconds", format);
            }
            this.timer.startIn(j);
        }
    }

    private void checkAttributionInternal(ResponseData responseData) {
        if (responseData.jsonResponse != null) {
            long optLong = responseData.jsonResponse.optLong("ask_in", -1);
            if (optLong >= 0) {
                this.activityHandler.setAskingAttribution(true);
                getAttribution(optLong);
                return;
            }
            this.activityHandler.setAskingAttribution(false);
            responseData.attribution = AdjustAttribution.fromJson(responseData.jsonResponse.optJSONObject("attribution"));
        }
    }

    private void checkSessionResponseInternal(SessionResponseData sessionResponseData) {
        checkAttributionInternal(sessionResponseData);
        this.activityHandler.launchSessionResponseTasks(sessionResponseData);
    }

    private void checkAttributionResponseInternal(AttributionResponseData attributionResponseData) {
        checkAttributionInternal(attributionResponseData);
        this.activityHandler.launchAttributionResponseTasks(attributionResponseData);
    }

    private void getAttributionInternal() {
        if (!this.hasListener) {
            return;
        }
        if (this.paused) {
            this.logger.debug("Attribution handler is paused", new Object[0]);
            return;
        }
        this.logger.verbose("%s", this.attributionPackage.getExtendedString());
        try {
            URLGetConnection createGETHttpsURLConnection = Util.createGETHttpsURLConnection(buildUri(this.attributionPackage.getPath(), this.attributionPackage.getParameters()).toString(), this.attributionPackage.getClientSdk());
            ResponseData readHttpResponse = Util.readHttpResponse(createGETHttpsURLConnection.httpsURLConnection, this.attributionPackage);
            this.lastUrlUsed = createGETHttpsURLConnection.url;
            if (readHttpResponse instanceof AttributionResponseData) {
                checkAttributionResponse((AttributionResponseData) readHttpResponse);
            }
        } catch (Exception e) {
            this.logger.error("Failed to get attribution (%s)", e.getMessage());
        }
    }

    private Uri buildUri(String str, Map<String, String> map) {
        Builder builder = new Builder();
        builder.scheme(Constants.SCHEME);
        builder.authority(Constants.AUTHORITY);
        builder.appendPath(str);
        for (Entry entry : map.entrySet()) {
            builder.appendQueryParameter((String) entry.getKey(), (String) entry.getValue());
        }
        builder.appendQueryParameter("sent_at", Util.dateFormat(System.currentTimeMillis()));
        return builder.build();
    }
}
