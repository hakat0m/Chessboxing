package com.adjust.sdk;

import android.content.Context;
import java.io.IOException;
import java.net.URL;
import javax.net.ssl.HttpsURLConnection;

public class AdjustFactory {
    private static IActivityHandler activityHandler = null;
    private static IAttributionHandler attributionHandler = null;
    private static HttpsURLConnection httpsURLConnection = null;
    private static ILogger logger = null;
    private static IPackageHandler packageHandler = null;
    private static BackoffStrategy packageHandlerBackoffStrategy = null;
    private static IRequestHandler requestHandler = null;
    private static BackoffStrategy sdkClickBackoffStrategy = null;
    private static ISdkClickHandler sdkClickHandler = null;
    private static long sessionInterval = -1;
    private static long subsessionInterval = -1;
    private static long timerInterval = -1;
    private static long timerStart = -1;

    public class URLGetConnection {
        HttpsURLConnection httpsURLConnection;
        URL url;

        URLGetConnection(HttpsURLConnection httpsURLConnection, URL url) {
            this.httpsURLConnection = httpsURLConnection;
            this.url = url;
        }
    }

    public static IPackageHandler getPackageHandler(ActivityHandler activityHandler, Context context, boolean z) {
        if (packageHandler == null) {
            return new PackageHandler(activityHandler, context, z);
        }
        packageHandler.init(activityHandler, context, z);
        return packageHandler;
    }

    public static IRequestHandler getRequestHandler(IPackageHandler iPackageHandler) {
        if (requestHandler == null) {
            return new RequestHandler(iPackageHandler);
        }
        requestHandler.init(iPackageHandler);
        return requestHandler;
    }

    public static ILogger getLogger() {
        if (logger == null) {
            logger = new Logger();
        }
        return logger;
    }

    public static long getTimerInterval() {
        if (timerInterval == -1) {
            return 60000;
        }
        return timerInterval;
    }

    public static long getTimerStart() {
        if (timerStart == -1) {
            return 60000;
        }
        return timerStart;
    }

    public static long getSessionInterval() {
        if (sessionInterval == -1) {
            return 1800000;
        }
        return sessionInterval;
    }

    public static long getSubsessionInterval() {
        if (subsessionInterval == -1) {
            return 1000;
        }
        return subsessionInterval;
    }

    public static BackoffStrategy getSdkClickBackoffStrategy() {
        if (sdkClickBackoffStrategy == null) {
            return BackoffStrategy.SHORT_WAIT;
        }
        return sdkClickBackoffStrategy;
    }

    public static BackoffStrategy getPackageHandlerBackoffStrategy() {
        if (packageHandlerBackoffStrategy == null) {
            return BackoffStrategy.LONG_WAIT;
        }
        return packageHandlerBackoffStrategy;
    }

    public static IActivityHandler getActivityHandler(AdjustConfig adjustConfig) {
        if (activityHandler == null) {
            return ActivityHandler.getInstance(adjustConfig);
        }
        activityHandler.init(adjustConfig);
        return activityHandler;
    }

    public static IAttributionHandler getAttributionHandler(IActivityHandler iActivityHandler, ActivityPackage activityPackage, boolean z, boolean z2) {
        if (attributionHandler == null) {
            return new AttributionHandler(iActivityHandler, activityPackage, z, z2);
        }
        attributionHandler.init(iActivityHandler, activityPackage, z, z2);
        return attributionHandler;
    }

    public static HttpsURLConnection getHttpsURLConnection(URL url) throws IOException {
        if (httpsURLConnection == null) {
            return (HttpsURLConnection) url.openConnection();
        }
        return httpsURLConnection;
    }

    public static URLGetConnection getHttpsURLGetConnection(URL url) throws IOException {
        if (httpsURLConnection == null) {
            return new URLGetConnection((HttpsURLConnection) url.openConnection(), url);
        }
        return new URLGetConnection(httpsURLConnection, url);
    }

    public static ISdkClickHandler getSdkClickHandler(boolean z) {
        if (sdkClickHandler == null) {
            return new SdkClickHandler(z);
        }
        sdkClickHandler.init(z);
        return sdkClickHandler;
    }

    public static void setPackageHandler(IPackageHandler iPackageHandler) {
        packageHandler = iPackageHandler;
    }

    public static void setRequestHandler(IRequestHandler iRequestHandler) {
        requestHandler = iRequestHandler;
    }

    public static void setLogger(ILogger iLogger) {
        logger = iLogger;
    }

    public static void setTimerInterval(long j) {
        timerInterval = j;
    }

    public static void setTimerStart(long j) {
        timerStart = j;
    }

    public static void setSessionInterval(long j) {
        sessionInterval = j;
    }

    public static void setSubsessionInterval(long j) {
        subsessionInterval = j;
    }

    public static void setSdkClickBackoffStrategy(BackoffStrategy backoffStrategy) {
        sdkClickBackoffStrategy = backoffStrategy;
    }

    public static void setPackageHandlerBackoffStrategy(BackoffStrategy backoffStrategy) {
        packageHandlerBackoffStrategy = backoffStrategy;
    }

    public static void setActivityHandler(IActivityHandler iActivityHandler) {
        activityHandler = iActivityHandler;
    }

    public static void setAttributionHandler(IAttributionHandler iAttributionHandler) {
        attributionHandler = iAttributionHandler;
    }

    public static void setHttpsURLConnection(HttpsURLConnection httpsURLConnection) {
        httpsURLConnection = httpsURLConnection;
    }

    public static void setSdkClickHandler(ISdkClickHandler iSdkClickHandler) {
        sdkClickHandler = iSdkClickHandler;
    }
}
