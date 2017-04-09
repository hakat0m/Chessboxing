package com.adjust.sdk;

import android.content.Context;

public class AdjustConfig {
    public static final String ENVIRONMENT_PRODUCTION = "production";
    public static final String ENVIRONMENT_SANDBOX = "sandbox";
    String appToken;
    Context context;
    Class deepLinkComponent;
    String defaultTracker;
    Boolean deviceKnown;
    String environment;
    boolean eventBufferingEnabled;
    LogLevel logLevel;
    OnAttributionChangedListener onAttributionChangedListener;
    OnDeeplinkResponseListener onDeeplinkResponseListener;
    OnEventTrackingFailedListener onEventTrackingFailedListener;
    OnEventTrackingSucceededListener onEventTrackingSucceededListener;
    OnSessionTrackingFailedListener onSessionTrackingFailedListener;
    OnSessionTrackingSucceededListener onSessionTrackingSucceededListener;
    String processName;
    String referrer;
    long referrerClickTime;
    String sdkPrefix;
    boolean sendInBackground;

    public AdjustConfig(Context context, String str, String str2) {
        if (isValid(context, str, str2)) {
            this.context = context.getApplicationContext();
            this.appToken = str;
            this.environment = str2;
            this.logLevel = LogLevel.INFO;
            this.eventBufferingEnabled = false;
            this.sendInBackground = false;
        }
    }

    public void setEventBufferingEnabled(Boolean bool) {
        if (bool == null) {
            this.eventBufferingEnabled = false;
        } else {
            this.eventBufferingEnabled = bool.booleanValue();
        }
    }

    public void setSendInBackground(boolean z) {
        this.sendInBackground = z;
    }

    public void setLogLevel(LogLevel logLevel) {
        this.logLevel = logLevel;
    }

    public void setSdkPrefix(String str) {
        this.sdkPrefix = str;
    }

    public void setProcessName(String str) {
        this.processName = str;
    }

    public void setDefaultTracker(String str) {
        this.defaultTracker = str;
    }

    public void setOnAttributionChangedListener(OnAttributionChangedListener onAttributionChangedListener) {
        this.onAttributionChangedListener = onAttributionChangedListener;
    }

    public void setDeviceKnown(boolean z) {
        this.deviceKnown = Boolean.valueOf(z);
    }

    public void setDeepLinkComponent(Class cls) {
        this.deepLinkComponent = cls;
    }

    public void setOnEventTrackingSucceededListener(OnEventTrackingSucceededListener onEventTrackingSucceededListener) {
        this.onEventTrackingSucceededListener = onEventTrackingSucceededListener;
    }

    public void setOnEventTrackingFailedListener(OnEventTrackingFailedListener onEventTrackingFailedListener) {
        this.onEventTrackingFailedListener = onEventTrackingFailedListener;
    }

    public void setOnSessionTrackingSucceededListener(OnSessionTrackingSucceededListener onSessionTrackingSucceededListener) {
        this.onSessionTrackingSucceededListener = onSessionTrackingSucceededListener;
    }

    public void setOnSessionTrackingFailedListener(OnSessionTrackingFailedListener onSessionTrackingFailedListener) {
        this.onSessionTrackingFailedListener = onSessionTrackingFailedListener;
    }

    public void setOnDeeplinkResponseListener(OnDeeplinkResponseListener onDeeplinkResponseListener) {
        this.onDeeplinkResponseListener = onDeeplinkResponseListener;
    }

    public boolean hasAttributionChangedListener() {
        return this.onAttributionChangedListener != null;
    }

    public boolean hasListener() {
        return (this.onAttributionChangedListener == null && this.onEventTrackingSucceededListener == null && this.onEventTrackingFailedListener == null && this.onSessionTrackingSucceededListener == null && this.onSessionTrackingFailedListener == null) ? false : true;
    }

    public boolean isValid() {
        return this.appToken != null;
    }

    private boolean isValid(Context context, String str, String str2) {
        if (checkAppToken(str) && checkEnvironment(str2) && checkContext(context)) {
            return true;
        }
        return false;
    }

    private static boolean checkContext(Context context) {
        ILogger logger = AdjustFactory.getLogger();
        if (context == null) {
            logger.error("Missing context", new Object[0]);
            return false;
        } else if (Util.checkPermission(context, "android.permission.INTERNET")) {
            return true;
        } else {
            logger.error("Missing permission: INTERNET", new Object[0]);
            return false;
        }
    }

    private static boolean checkAppToken(String str) {
        ILogger logger = AdjustFactory.getLogger();
        if (str == null) {
            logger.error("Missing App Token", new Object[0]);
            return false;
        } else if (str.length() == 12) {
            return true;
        } else {
            logger.error("Malformed App Token '%s'", str);
            return false;
        }
    }

    private static boolean checkEnvironment(String str) {
        ILogger logger = AdjustFactory.getLogger();
        if (str == null) {
            logger.error("Missing environment", new Object[0]);
            return false;
        } else if (str.equals(ENVIRONMENT_SANDBOX)) {
            logger.Assert("SANDBOX: Adjust is running in Sandbox mode. Use this setting for testing. Don't forget to set the environment to `production` before publishing!", new Object[0]);
            return true;
        } else if (str.equals(ENVIRONMENT_PRODUCTION)) {
            logger.Assert("PRODUCTION: Adjust is running in Production mode. Use this setting only for the build that you want to publish. Set the environment to `sandbox` if you want to test your app!", new Object[0]);
            return true;
        } else {
            logger.error("Unknown environment '%s'", str);
            return false;
        }
    }
}
