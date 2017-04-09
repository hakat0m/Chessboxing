package com.facebook;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import bolts.AppLinks;
import com.facebook.android.Facebook;
import com.facebook.internal.AttributionIdentifiers;
import com.facebook.internal.Logger;
import com.facebook.internal.Utility;
import com.facebook.internal.Utility.FetchedAppSettings;
import com.facebook.internal.Validate;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import org.json.JSONArray;
import org.json.JSONException;

public class AppEventsLogger {
    public static final String ACTION_APP_EVENTS_FLUSHED = "com.facebook.sdk.APP_EVENTS_FLUSHED";
    public static final String APP_EVENTS_EXTRA_FLUSH_RESULT = "com.facebook.sdk.APP_EVENTS_FLUSH_RESULT";
    public static final String APP_EVENTS_EXTRA_NUM_EVENTS_FLUSHED = "com.facebook.sdk.APP_EVENTS_NUM_EVENTS_FLUSHED";
    static final String APP_EVENT_PREFERENCES = "com.facebook.sdk.appEventPreferences";
    private static final int APP_SUPPORTS_ATTRIBUTION_ID_RECHECK_PERIOD_IN_SECONDS = 86400;
    private static final int FLUSH_APP_SESSION_INFO_IN_SECONDS = 30;
    private static final int FLUSH_PERIOD_IN_SECONDS = 15;
    private static final int NUM_LOG_EVENTS_TO_TRY_TO_FLUSH_AFTER = 100;
    private static final String SOURCE_APPLICATION_HAS_BEEN_SET_BY_THIS_INTENT = "_fbSourceApplicationHasBeenSet";
    private static final String TAG = AppEventsLogger.class.getCanonicalName();
    private static String anonymousAppDeviceGUID;
    private static Context applicationContext;
    private static ScheduledThreadPoolExecutor backgroundExecutor;
    private static FlushBehavior flushBehavior = FlushBehavior.AUTO;
    private static boolean isOpenedByApplink;
    private static boolean requestInFlight;
    private static String sourceApplication;
    private static Map<AccessTokenAppIdPair, SessionEventsState> stateMap = new ConcurrentHashMap();
    private static Object staticLock = new Object();
    private final AccessTokenAppIdPair accessTokenAppId;
    private final Context context;

    @Deprecated
    public static boolean getLimitEventUsage(Context context) {
        return Settings.getLimitEventAndDataUsage(context);
    }

    @Deprecated
    public static void setLimitEventUsage(Context context, boolean z) {
        Settings.setLimitEventAndDataUsage(context, z);
    }

    public static void activateApp(Context context) {
        Settings.sdkInitialize(context);
        activateApp(context, Utility.getMetadataApplicationId(context));
    }

    public static void activateApp(Context context, String str) {
        if (context == null || str == null) {
            throw new IllegalArgumentException("Both context and applicationId must be non-null");
        }
        if (context instanceof Activity) {
            setSourceApplication((Activity) context);
        } else {
            resetSourceApplication();
            Log.d(AppEventsLogger.class.getName(), "To set source application the context of activateApp must be an instance of Activity");
        }
        Settings.publishInstallAsync(context, str, null);
        backgroundExecutor.execute(new 1(new AppEventsLogger(context, str, null), System.currentTimeMillis(), getSourceApplication()));
    }

    public static void deactivateApp(Context context) {
        deactivateApp(context, Utility.getMetadataApplicationId(context));
    }

    public static void deactivateApp(Context context, String str) {
        if (context == null || str == null) {
            throw new IllegalArgumentException("Both context and applicationId must be non-null");
        }
        resetSourceApplication();
        backgroundExecutor.execute(new 2(new AppEventsLogger(context, str, null), System.currentTimeMillis()));
    }

    private void logAppSessionResumeEvent(long j, String str) {
        PersistedAppSessionInfo.onResume(applicationContext, this.accessTokenAppId, this, j, str);
    }

    private void logAppSessionSuspendEvent(long j) {
        PersistedAppSessionInfo.onSuspend(applicationContext, this.accessTokenAppId, this, j);
    }

    public static AppEventsLogger newLogger(Context context) {
        return new AppEventsLogger(context, null, null);
    }

    public static AppEventsLogger newLogger(Context context, Session session) {
        return new AppEventsLogger(context, null, session);
    }

    public static AppEventsLogger newLogger(Context context, String str, Session session) {
        return new AppEventsLogger(context, str, session);
    }

    public static AppEventsLogger newLogger(Context context, String str) {
        return new AppEventsLogger(context, str, null);
    }

    public static FlushBehavior getFlushBehavior() {
        FlushBehavior flushBehavior;
        synchronized (staticLock) {
            flushBehavior = flushBehavior;
        }
        return flushBehavior;
    }

    public static void setFlushBehavior(FlushBehavior flushBehavior) {
        synchronized (staticLock) {
            flushBehavior = flushBehavior;
        }
    }

    public void logEvent(String str) {
        logEvent(str, null);
    }

    public void logEvent(String str, double d) {
        logEvent(str, d, null);
    }

    public void logEvent(String str, Bundle bundle) {
        logEvent(str, null, bundle, false);
    }

    public void logEvent(String str, double d, Bundle bundle) {
        logEvent(str, Double.valueOf(d), bundle, false);
    }

    public void logPurchase(BigDecimal bigDecimal, Currency currency) {
        logPurchase(bigDecimal, currency, null);
    }

    public void logPurchase(BigDecimal bigDecimal, Currency currency, Bundle bundle) {
        if (bigDecimal == null) {
            notifyDeveloperError("purchaseAmount cannot be null");
        } else if (currency == null) {
            notifyDeveloperError("currency cannot be null");
        } else {
            if (bundle == null) {
                bundle = new Bundle();
            }
            bundle.putString("fb_currency", currency.getCurrencyCode());
            logEvent("fb_mobile_purchase", bigDecimal.doubleValue(), bundle);
            eagerFlush();
        }
    }

    public void flush() {
        flush(FlushReason.EXPLICIT);
    }

    public static void onContextStop() {
        PersistedEvents.persistEvents(applicationContext, stateMap);
    }

    boolean isValidForSession(Session session) {
        return this.accessTokenAppId.equals(new AccessTokenAppIdPair(session));
    }

    public void logSdkEvent(String str, Double d, Bundle bundle) {
        logEvent(str, d, bundle, true);
    }

    public String getApplicationId() {
        return this.accessTokenAppId.getApplicationId();
    }

    private AppEventsLogger(Context context, String str, Session session) {
        Validate.notNull(context, "context");
        this.context = context;
        if (session == null) {
            session = Session.getActiveSession();
        }
        if (session == null || !(str == null || str.equals(session.getApplicationId()))) {
            if (str == null) {
                str = Utility.getMetadataApplicationId(context);
            }
            this.accessTokenAppId = new AccessTokenAppIdPair(null, str);
        } else {
            this.accessTokenAppId = new AccessTokenAppIdPair(session);
        }
        synchronized (staticLock) {
            if (applicationContext == null) {
                applicationContext = context.getApplicationContext();
            }
        }
        initializeTimersIfNeeded();
    }

    private static void initializeTimersIfNeeded() {
        synchronized (staticLock) {
            if (backgroundExecutor != null) {
                return;
            }
            backgroundExecutor = new ScheduledThreadPoolExecutor(1);
            backgroundExecutor.scheduleAtFixedRate(new 3(), 0, 15, TimeUnit.SECONDS);
            backgroundExecutor.scheduleAtFixedRate(new 4(), 0, 86400, TimeUnit.SECONDS);
        }
    }

    private void logEvent(String str, Double d, Bundle bundle, boolean z) {
        logEvent(this.context, new AppEvent(this.context, str, d, bundle, z), this.accessTokenAppId);
    }

    private static void logEvent(Context context, AppEvent appEvent, AccessTokenAppIdPair accessTokenAppIdPair) {
        Settings.getExecutor().execute(new 5(context, accessTokenAppIdPair, appEvent));
    }

    static void eagerFlush() {
        if (getFlushBehavior() != FlushBehavior.EXPLICIT_ONLY) {
            flush(FlushReason.EAGER_FLUSHING_EVENT);
        }
    }

    private static void flushIfNecessary() {
        synchronized (staticLock) {
            if (getFlushBehavior() != FlushBehavior.EXPLICIT_ONLY && getAccumulatedEventCount() > NUM_LOG_EVENTS_TO_TRY_TO_FLUSH_AFTER) {
                flush(FlushReason.EVENT_THRESHOLD);
            }
        }
    }

    private static int getAccumulatedEventCount() {
        int i;
        synchronized (staticLock) {
            i = 0;
            for (SessionEventsState accumulatedEventCount : stateMap.values()) {
                i = accumulatedEventCount.getAccumulatedEventCount() + i;
            }
        }
        return i;
    }

    private static SessionEventsState getSessionEventsState(Context context, AccessTokenAppIdPair accessTokenAppIdPair) {
        SessionEventsState sessionEventsState;
        AttributionIdentifiers attributionIdentifiers = null;
        if (((SessionEventsState) stateMap.get(accessTokenAppIdPair)) == null) {
            attributionIdentifiers = AttributionIdentifiers.getAttributionIdentifiers(context);
        }
        synchronized (staticLock) {
            sessionEventsState = (SessionEventsState) stateMap.get(accessTokenAppIdPair);
            if (sessionEventsState == null) {
                sessionEventsState = new SessionEventsState(attributionIdentifiers, context.getPackageName(), getAnonymousAppDeviceGUID(context));
                stateMap.put(accessTokenAppIdPair, sessionEventsState);
            }
        }
        return sessionEventsState;
    }

    private static SessionEventsState getSessionEventsState(AccessTokenAppIdPair accessTokenAppIdPair) {
        SessionEventsState sessionEventsState;
        synchronized (staticLock) {
            sessionEventsState = (SessionEventsState) stateMap.get(accessTokenAppIdPair);
        }
        return sessionEventsState;
    }

    private static void flush(FlushReason flushReason) {
        Settings.getExecutor().execute(new 6(flushReason));
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static void flushAndWait(com.facebook.AppEventsLogger.FlushReason r4) {
        /*
        r1 = staticLock;
        monitor-enter(r1);
        r0 = requestInFlight;	 Catch:{ all -> 0x0048 }
        if (r0 == 0) goto L_0x0009;
    L_0x0007:
        monitor-exit(r1);	 Catch:{ all -> 0x0048 }
    L_0x0008:
        return;
    L_0x0009:
        r0 = 1;
        requestInFlight = r0;	 Catch:{ all -> 0x0048 }
        r2 = new java.util.HashSet;	 Catch:{ all -> 0x0048 }
        r0 = stateMap;	 Catch:{ all -> 0x0048 }
        r0 = r0.keySet();	 Catch:{ all -> 0x0048 }
        r2.<init>(r0);	 Catch:{ all -> 0x0048 }
        monitor-exit(r1);	 Catch:{ all -> 0x0048 }
        accumulatePersistedEvents();
        r0 = 0;
        r0 = buildAndExecuteRequests(r4, r2);	 Catch:{ Exception -> 0x004b }
    L_0x0020:
        r1 = staticLock;
        monitor-enter(r1);
        r2 = 0;
        requestInFlight = r2;	 Catch:{ all -> 0x0054 }
        monitor-exit(r1);	 Catch:{ all -> 0x0054 }
        if (r0 == 0) goto L_0x0008;
    L_0x0029:
        r1 = new android.content.Intent;
        r2 = "com.facebook.sdk.APP_EVENTS_FLUSHED";
        r1.<init>(r2);
        r2 = "com.facebook.sdk.APP_EVENTS_NUM_EVENTS_FLUSHED";
        r3 = r0.numEvents;
        r1.putExtra(r2, r3);
        r2 = "com.facebook.sdk.APP_EVENTS_FLUSH_RESULT";
        r0 = r0.result;
        r1.putExtra(r2, r0);
        r0 = applicationContext;
        r0 = android.support.v4.content.LocalBroadcastManager.getInstance(r0);
        r0.sendBroadcast(r1);
        goto L_0x0008;
    L_0x0048:
        r0 = move-exception;
        monitor-exit(r1);	 Catch:{ all -> 0x0048 }
        throw r0;
    L_0x004b:
        r1 = move-exception;
        r2 = TAG;
        r3 = "Caught unexpected exception while flushing: ";
        com.facebook.internal.Utility.logd(r2, r3, r1);
        goto L_0x0020;
    L_0x0054:
        r0 = move-exception;
        monitor-exit(r1);	 Catch:{ all -> 0x0054 }
        throw r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.facebook.AppEventsLogger.flushAndWait(com.facebook.AppEventsLogger$FlushReason):void");
    }

    private static FlushStatistics buildAndExecuteRequests(FlushReason flushReason, Set<AccessTokenAppIdPair> set) {
        FlushStatistics flushStatistics = new FlushStatistics(null);
        boolean limitEventAndDataUsage = Settings.getLimitEventAndDataUsage(applicationContext);
        List<Request> arrayList = new ArrayList();
        for (AccessTokenAppIdPair accessTokenAppIdPair : set) {
            Request buildRequestForSession;
            SessionEventsState sessionEventsState = getSessionEventsState(accessTokenAppIdPair);
            if (sessionEventsState != null) {
                buildRequestForSession = buildRequestForSession(accessTokenAppIdPair, sessionEventsState, limitEventAndDataUsage, flushStatistics);
                if (buildRequestForSession != null) {
                    arrayList.add(buildRequestForSession);
                }
            }
        }
        if (arrayList.size() <= 0) {
            return null;
        }
        Logger.log(LoggingBehavior.APP_EVENTS, TAG, "Flushing %d events due to %s.", new Object[]{Integer.valueOf(flushStatistics.numEvents), flushReason.toString()});
        for (Request buildRequestForSession2 : arrayList) {
            buildRequestForSession2.executeAndWait();
        }
        return flushStatistics;
    }

    private static Request buildRequestForSession(AccessTokenAppIdPair accessTokenAppIdPair, SessionEventsState sessionEventsState, boolean z, FlushStatistics flushStatistics) {
        FetchedAppSettings queryAppSettings = Utility.queryAppSettings(accessTokenAppIdPair.getApplicationId(), false);
        Request newPostRequest = Request.newPostRequest(null, String.format("%s/activities", new Object[]{r0}), null, null);
        Bundle parameters = newPostRequest.getParameters();
        if (parameters == null) {
            parameters = new Bundle();
        }
        parameters.putString(Facebook.TOKEN, accessTokenAppIdPair.getAccessToken());
        newPostRequest.setParameters(parameters);
        if (queryAppSettings == null) {
            return null;
        }
        int populateRequest = sessionEventsState.populateRequest(newPostRequest, queryAppSettings.supportsImplicitLogging(), z);
        if (populateRequest == 0) {
            return null;
        }
        flushStatistics.numEvents = populateRequest + flushStatistics.numEvents;
        newPostRequest.setCallback(new 7(accessTokenAppIdPair, newPostRequest, sessionEventsState, flushStatistics));
        return newPostRequest;
    }

    private static void handleResponse(AccessTokenAppIdPair accessTokenAppIdPair, Request request, Response response, SessionEventsState sessionEventsState, FlushStatistics flushStatistics) {
        FlushResult flushResult;
        FacebookRequestError error = response.getError();
        String str = "Success";
        FlushResult flushResult2 = FlushResult.SUCCESS;
        String str2;
        if (error == null) {
            str2 = str;
            flushResult = flushResult2;
        } else if (error.getErrorCode() == -1) {
            Object obj = "Failed: No Connectivity";
            flushResult = FlushResult.NO_CONNECTIVITY;
        } else {
            str2 = String.format("Failed:\n  Response: %s\n  Error %s", new Object[]{response.toString(), error.toString()});
            flushResult = FlushResult.SERVER_ERROR;
        }
        if (Settings.isLoggingBehaviorEnabled(LoggingBehavior.APP_EVENTS)) {
            String jSONArray;
            try {
                jSONArray = new JSONArray((String) request.getTag()).toString(2);
            } catch (JSONException e) {
                jSONArray = "<Can't encode events for debug logging>";
            }
            Logger.log(LoggingBehavior.APP_EVENTS, TAG, "Flush completed\nParams: %s\n  Result: %s\n  Events JSON: %s", new Object[]{request.getGraphObject().toString(), obj, jSONArray});
        }
        sessionEventsState.clearInFlightAndStats(error != null);
        if (flushResult == FlushResult.NO_CONNECTIVITY) {
            PersistedEvents.persistEvents(applicationContext, accessTokenAppIdPair, sessionEventsState);
        }
        if (flushResult != FlushResult.SUCCESS && flushStatistics.result != FlushResult.NO_CONNECTIVITY) {
            flushStatistics.result = flushResult;
        }
    }

    private static int accumulatePersistedEvents() {
        PersistedEvents readAndClearStore = PersistedEvents.readAndClearStore(applicationContext);
        int i = 0;
        for (AccessTokenAppIdPair accessTokenAppIdPair : readAndClearStore.keySet()) {
            SessionEventsState sessionEventsState = getSessionEventsState(applicationContext, accessTokenAppIdPair);
            List events = readAndClearStore.getEvents(accessTokenAppIdPair);
            sessionEventsState.accumulatePersistedEvents(events);
            i = events.size() + i;
        }
        return i;
    }

    private static void notifyDeveloperError(String str) {
        Logger.log(LoggingBehavior.DEVELOPER_ERRORS, "AppEvents", str);
    }

    private static void setSourceApplication(Activity activity) {
        ComponentName callingActivity = activity.getCallingActivity();
        if (callingActivity != null) {
            String packageName = callingActivity.getPackageName();
            if (packageName.equals(activity.getPackageName())) {
                resetSourceApplication();
                return;
            }
            sourceApplication = packageName;
        }
        Intent intent = activity.getIntent();
        if (intent == null || intent.getBooleanExtra(SOURCE_APPLICATION_HAS_BEEN_SET_BY_THIS_INTENT, false)) {
            resetSourceApplication();
            return;
        }
        Bundle appLinkData = AppLinks.getAppLinkData(intent);
        if (appLinkData == null) {
            resetSourceApplication();
            return;
        }
        isOpenedByApplink = true;
        appLinkData = appLinkData.getBundle("referer_app_link");
        if (appLinkData == null) {
            sourceApplication = null;
            return;
        }
        sourceApplication = appLinkData.getString("package");
        intent.putExtra(SOURCE_APPLICATION_HAS_BEEN_SET_BY_THIS_INTENT, true);
    }

    static void setSourceApplication(String str, boolean z) {
        sourceApplication = str;
        isOpenedByApplink = z;
    }

    static String getSourceApplication() {
        String str = "Unclassified";
        if (isOpenedByApplink) {
            str = "Applink";
        }
        if (sourceApplication != null) {
            return str + "(" + sourceApplication + ")";
        }
        return str;
    }

    static void resetSourceApplication() {
        sourceApplication = null;
        isOpenedByApplink = false;
    }

    static String getAnonymousAppDeviceGUID(Context context) {
        if (anonymousAppDeviceGUID == null) {
            synchronized (staticLock) {
                if (anonymousAppDeviceGUID == null) {
                    anonymousAppDeviceGUID = context.getSharedPreferences(APP_EVENT_PREFERENCES, 0).getString("anonymousAppDeviceGUID", null);
                    if (anonymousAppDeviceGUID == null) {
                        anonymousAppDeviceGUID = "XZ" + UUID.randomUUID().toString();
                        context.getSharedPreferences(APP_EVENT_PREFERENCES, 0).edit().putString("anonymousAppDeviceGUID", anonymousAppDeviceGUID).apply();
                    }
                }
            }
        }
        return anonymousAppDeviceGUID;
    }
}
