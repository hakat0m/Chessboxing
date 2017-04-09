package com.adjust.sdk;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Process;
import com.apptentive.android.sdk.BuildConfig;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class ActivityHandler extends HandlerThread implements IActivityHandler {
    private static final String ACTIVITY_STATE_NAME = "Activity state";
    private static final String ADJUST_PREFIX = "adjust_";
    private static final String ATTRIBUTION_NAME = "Attribution";
    private static long BACKGROUND_TIMER_INTERVAL = 0;
    private static final String BACKGROUND_TIMER_NAME = "Background timer";
    private static long FOREGROUND_TIMER_INTERVAL = 0;
    private static final String FOREGROUND_TIMER_NAME = "Foreground timer";
    private static long FOREGROUND_TIMER_START = 0;
    private static long SESSION_INTERVAL = 0;
    private static long SUBSESSION_INTERVAL = 0;
    private static final String TIME_TRAVEL = "Time travel!";
    private ActivityState activityState;
    private AdjustConfig adjustConfig;
    private AdjustAttribution attribution;
    private IAttributionHandler attributionHandler;
    private TimerOnce backgroundTimer;
    private DeviceInfo deviceInfo;
    private TimerCycle foregroundTimer;
    private Handler internalHandler;
    private InternalState internalState;
    private ILogger logger = AdjustFactory.getLogger();
    private IPackageHandler packageHandler;
    private ScheduledExecutorService scheduler;
    private ISdkClickHandler sdkClickHandler;

    public class InternalState {
        boolean background;
        boolean enabled;
        boolean offline;

        public boolean isEnabled() {
            return this.enabled;
        }

        public boolean isDisabled() {
            return !this.enabled;
        }

        public boolean isOffline() {
            return this.offline;
        }

        public boolean isOnline() {
            return !this.offline;
        }

        public boolean isBackground() {
            return this.background;
        }

        public boolean isForeground() {
            return !this.background;
        }
    }

    private ActivityHandler(AdjustConfig adjustConfig) {
        super(Constants.LOGTAG, 1);
        setDaemon(true);
        start();
        init(adjustConfig);
        if (AdjustConfig.ENVIRONMENT_PRODUCTION.equals(adjustConfig.environment)) {
            this.logger.setLogLevel(LogLevel.ASSERT);
        } else {
            this.logger.setLogLevel(adjustConfig.logLevel);
        }
        this.internalHandler = new Handler(getLooper());
        this.internalState = new InternalState();
        readAttribution(adjustConfig.context);
        readActivityState(adjustConfig.context);
        if (this.activityState == null) {
            this.internalState.enabled = true;
        } else {
            this.internalState.enabled = this.activityState.enabled;
        }
        this.internalState.offline = false;
        this.internalState.background = true;
        this.internalHandler.post(new Runnable() {
            public void run() {
                ActivityHandler.this.initInternal();
            }
        });
        FOREGROUND_TIMER_INTERVAL = AdjustFactory.getTimerInterval();
        FOREGROUND_TIMER_START = AdjustFactory.getTimerStart();
        BACKGROUND_TIMER_INTERVAL = AdjustFactory.getTimerInterval();
        this.foregroundTimer = new TimerCycle(new Runnable() {
            public void run() {
                ActivityHandler.this.foregroundTimerFired();
            }
        }, FOREGROUND_TIMER_START, FOREGROUND_TIMER_INTERVAL, FOREGROUND_TIMER_NAME);
        this.scheduler = Executors.newSingleThreadScheduledExecutor();
        this.backgroundTimer = new TimerOnce(this.scheduler, new Runnable() {
            public void run() {
                ActivityHandler.this.backgroundTimerFired();
            }
        }, BACKGROUND_TIMER_NAME);
    }

    public void init(AdjustConfig adjustConfig) {
        this.adjustConfig = adjustConfig;
    }

    public static ActivityHandler getInstance(AdjustConfig adjustConfig) {
        if (adjustConfig == null) {
            AdjustFactory.getLogger().error("AdjustConfig missing", new Object[0]);
            return null;
        } else if (adjustConfig.isValid()) {
            if (adjustConfig.processName != null) {
                int myPid = Process.myPid();
                ActivityManager activityManager = (ActivityManager) adjustConfig.context.getSystemService("activity");
                if (activityManager == null) {
                    return null;
                }
                for (RunningAppProcessInfo runningAppProcessInfo : activityManager.getRunningAppProcesses()) {
                    if (runningAppProcessInfo.pid == myPid) {
                        if (!runningAppProcessInfo.processName.equalsIgnoreCase(adjustConfig.processName)) {
                            AdjustFactory.getLogger().info("Skipping initialization in background process (%s)", runningAppProcessInfo.processName);
                            return null;
                        }
                    }
                }
            }
            return new ActivityHandler(adjustConfig);
        } else {
            AdjustFactory.getLogger().error("AdjustConfig not initialized correctly", new Object[0]);
            return null;
        }
    }

    public void onResume() {
        this.internalState.background = false;
        this.internalHandler.post(new Runnable() {
            public void run() {
                ActivityHandler.this.stopBackgroundTimer();
                ActivityHandler.this.startForegroundTimer();
                ActivityHandler.this.logger.verbose("Subsession start", new Object[0]);
                ActivityHandler.this.startInternal();
            }
        });
    }

    public void onPause() {
        this.internalState.background = true;
        this.internalHandler.post(new Runnable() {
            public void run() {
                ActivityHandler.this.stopForegroundTimer();
                ActivityHandler.this.startBackgroundTimer();
                ActivityHandler.this.logger.verbose("Subsession end", new Object[0]);
                ActivityHandler.this.endInternal();
            }
        });
    }

    public void trackEvent(final AdjustEvent adjustEvent) {
        this.internalHandler.post(new Runnable() {
            public void run() {
                if (ActivityHandler.this.activityState == null) {
                    ActivityHandler.this.logger.warn("Event triggered before first application launch.\nThis will trigger the SDK start and an install without user interaction.\nPlease check https://github.com/adjust/android_sdk#can-i-trigger-an-event-at-application-launch for more information.", new Object[0]);
                    ActivityHandler.this.startInternal();
                }
                ActivityHandler.this.trackEventInternal(adjustEvent);
            }
        });
    }

    public void finishedTrackingActivity(ResponseData responseData) {
        if (responseData instanceof SessionResponseData) {
            this.attributionHandler.checkSessionResponse((SessionResponseData) responseData);
        } else if (responseData instanceof EventResponseData) {
            launchEventResponseTasks((EventResponseData) responseData);
        }
    }

    public void setEnabled(boolean z) {
        boolean z2 = true;
        if (hasChangedState(isEnabled(), z, "Adjust already enabled", "Adjust already disabled")) {
            this.internalState.enabled = z;
            if (this.activityState == null) {
                if (z) {
                    z2 = false;
                }
                updateStatus(z2, "Package handler and attribution handler will start as paused due to the SDK being disabled", "Package and attribution handler will still start as paused due to the SDK being offline", "Package handler and attribution handler will start as active due to the SDK being enabled");
                return;
            }
            this.activityState.enabled = z;
            writeActivityState();
            if (z) {
                z2 = false;
            }
            updateStatus(z2, "Pausing package handler and attribution handler due to SDK being disabled", "Package and attribution handler remain paused due to SDK being offline", "Resuming package handler and attribution handler due to SDK being enabled");
        }
    }

    private void updateStatus(boolean z, String str, String str2, String str3) {
        if (z) {
            this.logger.info(str, new Object[0]);
            updateHandlersStatusAndSend();
        } else if (paused()) {
            this.logger.info(str2, new Object[0]);
        } else {
            this.logger.info(str3, new Object[0]);
            updateHandlersStatusAndSend();
        }
    }

    private boolean hasChangedState(boolean z, boolean z2, String str, String str2) {
        if (z != z2) {
            return true;
        }
        if (z) {
            this.logger.debug(str, new Object[0]);
            return false;
        }
        this.logger.debug(str2, new Object[0]);
        return false;
    }

    public void setOfflineMode(boolean z) {
        if (hasChangedState(this.internalState.isOffline(), z, "Adjust already in offline mode", "Adjust already in online mode")) {
            this.internalState.offline = z;
            if (this.activityState == null) {
                updateStatus(z, "Package handler and attribution handler will start paused due to SDK being offline", "Package and attribution handler will still start as paused due to SDK being disabled", "Package handler and attribution handler will start as active due to SDK being online");
            } else {
                updateStatus(z, "Pausing package and attribution handler to put SDK offline mode", "Package and attribution handler remain paused due to SDK being disabled", "Resuming package handler and attribution handler to put SDK in online mode");
            }
        }
    }

    public boolean isEnabled() {
        if (this.activityState != null) {
            return this.activityState.enabled;
        }
        return this.internalState.isEnabled();
    }

    public void readOpenUrl(final Uri uri, final long j) {
        this.internalHandler.post(new Runnable() {
            public void run() {
                ActivityHandler.this.readOpenUrlInternal(uri, j);
            }
        });
    }

    public boolean updateAttribution(AdjustAttribution adjustAttribution) {
        if (adjustAttribution == null || adjustAttribution.equals(this.attribution)) {
            return false;
        }
        saveAttribution(adjustAttribution);
        return true;
    }

    private void saveAttribution(AdjustAttribution adjustAttribution) {
        this.attribution = adjustAttribution;
        writeAttribution();
    }

    public void setAskingAttribution(boolean z) {
        this.activityState.askingAttribution = z;
        writeActivityState();
    }

    public void sendReferrer(final String str, final long j) {
        this.internalHandler.post(new Runnable() {
            public void run() {
                ActivityHandler.this.sendReferrerInternal(str, j);
            }
        });
    }

    public void launchEventResponseTasks(final EventResponseData eventResponseData) {
        this.internalHandler.post(new Runnable() {
            public void run() {
                ActivityHandler.this.launchEventResponseTasksInternal(eventResponseData);
            }
        });
    }

    public void launchSessionResponseTasks(final SessionResponseData sessionResponseData) {
        this.internalHandler.post(new Runnable() {
            public void run() {
                ActivityHandler.this.launchSessionResponseTasksInternal(sessionResponseData);
            }
        });
    }

    public void launchAttributionResponseTasks(final AttributionResponseData attributionResponseData) {
        this.internalHandler.post(new Runnable() {
            public void run() {
                ActivityHandler.this.launchAttributionResponseTasksInternal(attributionResponseData);
            }
        });
    }

    public ActivityPackage getAttributionPackage() {
        return new PackageBuilder(this.adjustConfig, this.deviceInfo, this.activityState, System.currentTimeMillis()).buildAttributionPackage();
    }

    public InternalState getInternalState() {
        return this.internalState;
    }

    private void updateHandlersStatusAndSend() {
        this.internalHandler.post(new Runnable() {
            public void run() {
                ActivityHandler.this.updateHandlersStatusAndSendInternal();
            }
        });
    }

    private void foregroundTimerFired() {
        this.internalHandler.post(new Runnable() {
            public void run() {
                ActivityHandler.this.foregroundTimerFiredInternal();
            }
        });
    }

    private void backgroundTimerFired() {
        this.internalHandler.post(new Runnable() {
            public void run() {
                ActivityHandler.this.backgroundTimerFiredInternal();
            }
        });
    }

    private void initInternal() {
        SESSION_INTERVAL = AdjustFactory.getSessionInterval();
        SUBSESSION_INTERVAL = AdjustFactory.getSubsessionInterval();
        this.deviceInfo = new DeviceInfo(this.adjustConfig.context, this.adjustConfig.sdkPrefix);
        if (this.adjustConfig.eventBufferingEnabled) {
            this.logger.info("Event buffering is enabled", new Object[0]);
        }
        if (Util.getPlayAdId(this.adjustConfig.context) == null) {
            this.logger.warn("Unable to get Google Play Services Advertising ID at start time", new Object[0]);
            if (this.deviceInfo.macSha1 == null && this.deviceInfo.macShortMd5 == null && this.deviceInfo.androidId == null) {
                this.logger.error("Unable to get any device id's. Please check if Proguard is correctly set with Adjust SDK", new Object[0]);
            }
        } else {
            this.logger.info("Google Play Services Advertising ID read correctly at start time", new Object[0]);
        }
        if (this.adjustConfig.defaultTracker != null) {
            this.logger.info("Default tracker: '%s'", this.adjustConfig.defaultTracker);
        }
        if (this.adjustConfig.referrer != null) {
            sendReferrer(this.adjustConfig.referrer, this.adjustConfig.referrerClickTime);
        }
        this.packageHandler = AdjustFactory.getPackageHandler(this, this.adjustConfig.context, toSend());
        this.attributionHandler = AdjustFactory.getAttributionHandler(this, getAttributionPackage(), toSend(), this.adjustConfig.hasAttributionChangedListener());
        this.sdkClickHandler = AdjustFactory.getSdkClickHandler(toSend());
    }

    private void startInternal() {
        if (this.activityState == null || this.activityState.enabled) {
            updateHandlersStatusAndSendInternal();
            processSession();
            checkAttributionState();
        }
    }

    private void processSession() {
        long currentTimeMillis = System.currentTimeMillis();
        if (this.activityState == null) {
            this.activityState = new ActivityState();
            this.activityState.sessionCount = 1;
            transferSessionPackage(currentTimeMillis);
            this.activityState.resetSessionAttributes(currentTimeMillis);
            this.activityState.enabled = this.internalState.isEnabled();
            writeActivityState();
            return;
        }
        long j = currentTimeMillis - this.activityState.lastActivity;
        if (j < 0) {
            this.logger.error(TIME_TRAVEL, new Object[0]);
            this.activityState.lastActivity = currentTimeMillis;
            writeActivityState();
        } else if (j > SESSION_INTERVAL) {
            r4 = this.activityState;
            r4.sessionCount++;
            this.activityState.lastInterval = j;
            transferSessionPackage(currentTimeMillis);
            this.activityState.resetSessionAttributes(currentTimeMillis);
            writeActivityState();
        } else if (j > SUBSESSION_INTERVAL) {
            r4 = this.activityState;
            r4.subsessionCount++;
            r4 = this.activityState;
            r4.sessionLength = j + r4.sessionLength;
            this.activityState.lastActivity = currentTimeMillis;
            this.logger.verbose("Started subsession %d of session %d", Integer.valueOf(this.activityState.subsessionCount), Integer.valueOf(this.activityState.sessionCount));
            writeActivityState();
        } else {
            this.logger.verbose("Time span since last activity too short for a new subsession", new Object[0]);
        }
    }

    private void checkAttributionState() {
        if (!checkActivityState(this.activityState) || this.activityState.subsessionCount <= 1) {
            return;
        }
        if (this.attribution == null || this.activityState.askingAttribution) {
            this.attributionHandler.getAttribution();
        }
    }

    private void endInternal() {
        if (!toSend()) {
            pauseSending();
        }
        if (updateActivityState(System.currentTimeMillis())) {
            writeActivityState();
        }
    }

    private void trackEventInternal(AdjustEvent adjustEvent) {
        if (checkActivityState(this.activityState) && isEnabled() && checkEvent(adjustEvent)) {
            long currentTimeMillis = System.currentTimeMillis();
            ActivityState activityState = this.activityState;
            activityState.eventCount++;
            updateActivityState(currentTimeMillis);
            this.packageHandler.addPackage(new PackageBuilder(this.adjustConfig, this.deviceInfo, this.activityState, currentTimeMillis).buildEventPackage(adjustEvent));
            if (this.adjustConfig.eventBufferingEnabled) {
                this.logger.info("Buffered event %s", r0.getSuffix());
            } else {
                this.packageHandler.sendFirstPackage();
            }
            if (this.adjustConfig.sendInBackground && this.internalState.isBackground()) {
                startBackgroundTimer();
            }
            writeActivityState();
        }
    }

    private void launchEventResponseTasksInternal(final EventResponseData eventResponseData) {
        Handler handler = new Handler(this.adjustConfig.context.getMainLooper());
        if (eventResponseData.success && this.adjustConfig.onEventTrackingSucceededListener != null) {
            this.logger.debug("Launching success event tracking listener", new Object[0]);
            handler.post(new Runnable() {
                public void run() {
                    ActivityHandler.this.adjustConfig.onEventTrackingSucceededListener.onFinishedEventTrackingSucceeded(eventResponseData.getSuccessResponseData());
                }
            });
        } else if (!eventResponseData.success && this.adjustConfig.onEventTrackingFailedListener != null) {
            this.logger.debug("Launching failed event tracking listener", new Object[0]);
            handler.post(new Runnable() {
                public void run() {
                    ActivityHandler.this.adjustConfig.onEventTrackingFailedListener.onFinishedEventTrackingFailed(eventResponseData.getFailureResponseData());
                }
            });
        }
    }

    private void launchSessionResponseTasksInternal(SessionResponseData sessionResponseData) {
        Handler handler = new Handler(this.adjustConfig.context.getMainLooper());
        if (updateAttribution(sessionResponseData.attribution)) {
            launchAttributionListener(handler);
        }
        launchSessionResponseListener(sessionResponseData, handler);
        prepareDeeplink(sessionResponseData, handler);
    }

    private void launchSessionResponseListener(final SessionResponseData sessionResponseData, Handler handler) {
        if (sessionResponseData.success && this.adjustConfig.onSessionTrackingSucceededListener != null) {
            this.logger.debug("Launching success session tracking listener", new Object[0]);
            handler.post(new Runnable() {
                public void run() {
                    ActivityHandler.this.adjustConfig.onSessionTrackingSucceededListener.onFinishedSessionTrackingSucceeded(sessionResponseData.getSuccessResponseData());
                }
            });
        } else if (!sessionResponseData.success && this.adjustConfig.onSessionTrackingFailedListener != null) {
            this.logger.debug("Launching failed session tracking listener", new Object[0]);
            handler.post(new Runnable() {
                public void run() {
                    ActivityHandler.this.adjustConfig.onSessionTrackingFailedListener.onFinishedSessionTrackingFailed(sessionResponseData.getFailureResponseData());
                }
            });
        }
    }

    private void launchAttributionResponseTasksInternal(AttributionResponseData attributionResponseData) {
        Handler handler = new Handler(this.adjustConfig.context.getMainLooper());
        if (updateAttribution(attributionResponseData.attribution)) {
            launchAttributionListener(handler);
        }
    }

    private void launchAttributionListener(Handler handler) {
        if (this.adjustConfig.onAttributionChangedListener != null) {
            handler.post(new Runnable() {
                public void run() {
                    ActivityHandler.this.adjustConfig.onAttributionChangedListener.onAttributionChanged(ActivityHandler.this.attribution);
                }
            });
        }
    }

    private void prepareDeeplink(ResponseData responseData, Handler handler) {
        if (responseData.jsonResponse != null) {
            final String optString = responseData.jsonResponse.optString(Constants.DEEPLINK, null);
            if (optString != null) {
                final Uri parse = Uri.parse(optString);
                final Intent createDeeplinkIntent = createDeeplinkIntent(parse);
                handler.post(new Runnable() {
                    public void run() {
                        boolean z = true;
                        if (ActivityHandler.this.adjustConfig.onDeeplinkResponseListener != null) {
                            z = ActivityHandler.this.adjustConfig.onDeeplinkResponseListener.launchReceivedDeeplink(parse);
                        }
                        if (z) {
                            ActivityHandler.this.launchDeeplinkMain(createDeeplinkIntent, optString);
                        }
                    }
                });
            }
        }
    }

    private Intent createDeeplinkIntent(Uri uri) {
        Intent intent;
        if (this.adjustConfig.deepLinkComponent == null) {
            intent = new Intent("android.intent.action.VIEW", uri);
        } else {
            intent = new Intent("android.intent.action.VIEW", uri, this.adjustConfig.context, this.adjustConfig.deepLinkComponent);
        }
        intent.setFlags(268435456);
        intent.setPackage(this.adjustConfig.context.getPackageName());
        return intent;
    }

    private void launchDeeplinkMain(Intent intent, String str) {
        if ((this.adjustConfig.context.getPackageManager().queryIntentActivities(intent, 0).size() > 0 ? 1 : 0) == 0) {
            this.logger.error("Unable to open deep link (%s)", str);
            return;
        }
        this.logger.info("Open deep link (%s)", str);
        this.adjustConfig.context.startActivity(intent);
    }

    private void sendReferrerInternal(String str, long j) {
        if (str != null && str.length() != 0) {
            PackageBuilder queryStringClickPackageBuilder = queryStringClickPackageBuilder(str);
            if (queryStringClickPackageBuilder != null) {
                queryStringClickPackageBuilder.referrer = str;
                this.sdkClickHandler.sendSdkClick(queryStringClickPackageBuilder.buildClickPackage(Constants.REFTAG, j));
            }
        }
    }

    private void readOpenUrlInternal(Uri uri, long j) {
        if (uri != null) {
            String query = uri.getQuery();
            if (query == null && uri.toString().length() > 0) {
                query = BuildConfig.FLAVOR;
            }
            PackageBuilder queryStringClickPackageBuilder = queryStringClickPackageBuilder(query);
            if (queryStringClickPackageBuilder != null) {
                queryStringClickPackageBuilder.deeplink = uri.toString();
                this.sdkClickHandler.sendSdkClick(queryStringClickPackageBuilder.buildClickPackage(Constants.DEEPLINK, j));
            }
        }
    }

    private PackageBuilder queryStringClickPackageBuilder(String str) {
        int i = 0;
        if (str == null) {
            return null;
        }
        Map linkedHashMap = new LinkedHashMap();
        AdjustAttribution adjustAttribution = new AdjustAttribution();
        this.logger.verbose("Reading query string (%s)", str);
        String[] split = str.split("&");
        int length = split.length;
        while (i < length) {
            readQueryString(split[i], linkedHashMap, adjustAttribution);
            i++;
        }
        String str2 = (String) linkedHashMap.remove(Constants.REFTAG);
        PackageBuilder packageBuilder = new PackageBuilder(this.adjustConfig, this.deviceInfo, this.activityState, System.currentTimeMillis());
        packageBuilder.extraParameters = linkedHashMap;
        packageBuilder.attribution = adjustAttribution;
        packageBuilder.reftag = str2;
        return packageBuilder;
    }

    private boolean readQueryString(String str, Map<String, String> map, AdjustAttribution adjustAttribution) {
        String[] split = str.split("=");
        if (split.length != 2) {
            return false;
        }
        String str2 = split[0];
        if (!str2.startsWith(ADJUST_PREFIX)) {
            return false;
        }
        String str3 = split[1];
        if (str3.length() == 0) {
            return false;
        }
        str2 = str2.substring(ADJUST_PREFIX.length());
        if (str2.length() == 0) {
            return false;
        }
        if (!trySetAttribution(adjustAttribution, str2, str3)) {
            map.put(str2, str3);
        }
        return true;
    }

    private boolean trySetAttribution(AdjustAttribution adjustAttribution, String str, String str2) {
        if (str.equals("tracker")) {
            adjustAttribution.trackerName = str2;
            return true;
        } else if (str.equals("campaign")) {
            adjustAttribution.campaign = str2;
            return true;
        } else if (str.equals("adgroup")) {
            adjustAttribution.adgroup = str2;
            return true;
        } else if (!str.equals("creative")) {
            return false;
        } else {
            adjustAttribution.creative = str2;
            return true;
        }
    }

    private void updateHandlersStatusAndSendInternal() {
        if (toSend()) {
            resumeSending();
            if (!this.adjustConfig.eventBufferingEnabled) {
                this.packageHandler.sendFirstPackage();
                return;
            }
            return;
        }
        pauseSending();
    }

    private void pauseSending() {
        this.attributionHandler.pauseSending();
        this.packageHandler.pauseSending();
        this.sdkClickHandler.pauseSending();
    }

    private void resumeSending() {
        this.attributionHandler.resumeSending();
        this.packageHandler.resumeSending();
        this.sdkClickHandler.resumeSending();
    }

    private boolean updateActivityState(long j) {
        if (!checkActivityState(this.activityState)) {
            return false;
        }
        long j2 = j - this.activityState.lastActivity;
        if (j2 > SESSION_INTERVAL) {
            return false;
        }
        this.activityState.lastActivity = j;
        if (j2 < 0) {
            this.logger.error(TIME_TRAVEL, new Object[0]);
        } else {
            ActivityState activityState = this.activityState;
            activityState.sessionLength += j2;
            activityState = this.activityState;
            activityState.timeSpent = j2 + activityState.timeSpent;
        }
        return true;
    }

    public static boolean deleteActivityState(Context context) {
        return context.deleteFile(Constants.ACTIVITY_STATE_FILENAME);
    }

    public static boolean deleteAttribution(Context context) {
        return context.deleteFile(Constants.ATTRIBUTION_FILENAME);
    }

    private void transferSessionPackage(long j) {
        this.packageHandler.addPackage(new PackageBuilder(this.adjustConfig, this.deviceInfo, this.activityState, j).buildSessionPackage());
        this.packageHandler.sendFirstPackage();
    }

    private void startForegroundTimer() {
        if (!paused()) {
            this.foregroundTimer.start();
        }
    }

    private void stopForegroundTimer() {
        this.foregroundTimer.suspend();
    }

    private void foregroundTimerFiredInternal() {
        if (paused()) {
            stopForegroundTimer();
            return;
        }
        this.packageHandler.sendFirstPackage();
        if (updateActivityState(System.currentTimeMillis())) {
            writeActivityState();
        }
    }

    private void startBackgroundTimer() {
        if (toSend() && this.backgroundTimer.getFireIn() <= 0) {
            this.backgroundTimer.startIn(BACKGROUND_TIMER_INTERVAL);
        }
    }

    private void stopBackgroundTimer() {
        this.backgroundTimer.cancel();
    }

    private void backgroundTimerFiredInternal() {
        this.packageHandler.sendFirstPackage();
    }

    private void readActivityState(Context context) {
        try {
            this.activityState = (ActivityState) Util.readObject(context, Constants.ACTIVITY_STATE_FILENAME, ACTIVITY_STATE_NAME, ActivityState.class);
        } catch (Exception e) {
            this.logger.error("Failed to read %s file (%s)", ACTIVITY_STATE_NAME, e.getMessage());
            this.activityState = null;
        }
    }

    private void readAttribution(Context context) {
        try {
            this.attribution = (AdjustAttribution) Util.readObject(context, Constants.ATTRIBUTION_FILENAME, ATTRIBUTION_NAME, AdjustAttribution.class);
        } catch (Exception e) {
            this.logger.error("Failed to read %s file (%s)", ATTRIBUTION_NAME, e.getMessage());
            this.attribution = null;
        }
    }

    private synchronized void writeActivityState() {
        Util.writeObject(this.activityState, this.adjustConfig.context, Constants.ACTIVITY_STATE_FILENAME, ACTIVITY_STATE_NAME);
    }

    private void writeAttribution() {
        Util.writeObject(this.attribution, this.adjustConfig.context, Constants.ATTRIBUTION_FILENAME, ATTRIBUTION_NAME);
    }

    private boolean checkEvent(AdjustEvent adjustEvent) {
        if (adjustEvent == null) {
            this.logger.error("Event missing", new Object[0]);
            return false;
        } else if (adjustEvent.isValid()) {
            return true;
        } else {
            this.logger.error("Event not initialized correctly", new Object[0]);
            return false;
        }
    }

    private boolean checkActivityState(ActivityState activityState) {
        if (activityState != null) {
            return true;
        }
        this.logger.error("Missing activity state", new Object[0]);
        return false;
    }

    private boolean paused() {
        return this.internalState.isOffline() || !isEnabled();
    }

    private boolean toSend() {
        if (paused()) {
            return false;
        }
        if (this.adjustConfig.sendInBackground) {
            return true;
        }
        return this.internalState.isForeground();
    }
}
