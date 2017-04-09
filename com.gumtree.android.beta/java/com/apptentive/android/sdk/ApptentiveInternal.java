package com.apptentive.android.sdk;

import android.app.Activity;
import android.app.Application;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources.NotFoundException;
import android.content.res.Resources.Theme;
import android.content.res.TypedArray;
import android.os.AsyncTask;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.provider.Settings.Secure;
import android.support.v4.content.ContextCompat;
import android.support.v7.appcompat.R;
import android.text.TextUtils;
import com.apptentive.android.sdk.ApptentiveLog.Level;
import com.apptentive.android.sdk.comm.ApptentiveClient;
import com.apptentive.android.sdk.comm.ApptentiveHttpResponse;
import com.apptentive.android.sdk.lifecycle.ApptentiveActivityLifecycleCallbacks;
import com.apptentive.android.sdk.model.AppRelease;
import com.apptentive.android.sdk.model.CodePointStore;
import com.apptentive.android.sdk.model.Configuration;
import com.apptentive.android.sdk.model.ConversationTokenRequest;
import com.apptentive.android.sdk.model.CustomData;
import com.apptentive.android.sdk.model.Device;
import com.apptentive.android.sdk.model.Event.EventLabel;
import com.apptentive.android.sdk.model.Payload;
import com.apptentive.android.sdk.model.Person;
import com.apptentive.android.sdk.model.Sdk;
import com.apptentive.android.sdk.module.engagement.EngagementModule;
import com.apptentive.android.sdk.module.engagement.interaction.InteractionManager;
import com.apptentive.android.sdk.module.engagement.interaction.InteractionManager.InteractionUpdateListener;
import com.apptentive.android.sdk.module.engagement.interaction.model.MessageCenterInteraction;
import com.apptentive.android.sdk.module.messagecenter.MessageManager;
import com.apptentive.android.sdk.module.metric.MetricModule;
import com.apptentive.android.sdk.module.rating.IRatingProvider;
import com.apptentive.android.sdk.module.rating.impl.GooglePlayRatingProvider;
import com.apptentive.android.sdk.module.survey.OnSurveyFinishedListener;
import com.apptentive.android.sdk.storage.AppReleaseManager;
import com.apptentive.android.sdk.storage.ApptentiveDatabase;
import com.apptentive.android.sdk.storage.DeviceManager;
import com.apptentive.android.sdk.storage.PayloadSendWorker;
import com.apptentive.android.sdk.storage.PersonManager;
import com.apptentive.android.sdk.storage.SdkManager;
import com.apptentive.android.sdk.storage.VersionHistoryStore;
import com.apptentive.android.sdk.storage.VersionHistoryStore.VersionHistoryEntry;
import com.apptentive.android.sdk.util.Util;
import com.gumtree.android.postad.payment.di.PostAdPaymentModule;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import org.json.JSONException;
import org.json.JSONObject;

public class ApptentiveInternal {
    static final String APPTENTIVE_PUSH_EXTRA_KEY = "apptentive";
    static final String PARSE_PUSH_EXTRA_KEY = "com.parse.Data";
    public static final String PUSH_ACTION = "action";
    static AtomicBoolean isApptentiveInitialized = new AtomicBoolean(false);
    private static volatile ApptentiveInternal sApptentiveInternal;
    String androidId;
    String apiKey;
    Context appContext;
    int appDefaultAppCompatThemeId;
    boolean appIsInForeground;
    String appPackageName;
    Theme apptentiveToolbarTheme;
    ExecutorService cachedExecutor;
    CodePointStore codePointStore;
    String conversationId;
    String conversationToken;
    private WeakReference<Activity> currentTaskStackTopActivity;
    private Map<String, Object> customData;
    ApptentiveDatabase database;
    String defaultAppDisplayName = "this app";
    InteractionManager interactionManager;
    final LinkedBlockingQueue interactionUpdateListeners = new LinkedBlockingQueue();
    boolean isAppDebuggable;
    AtomicBoolean isConfigurationFetchPending = new AtomicBoolean(false);
    AtomicBoolean isConversationTokenFetchPending = new AtomicBoolean(false);
    ApptentiveActivityLifecycleCallbacks lifecycleCallbacks;
    MessageManager messageManager;
    WeakReference<OnSurveyFinishedListener> onSurveyFinishedListener;
    PayloadSendWorker payloadWorker;
    String personId;
    SharedPreferences prefs;
    private String pushCallbackActivityName;
    IRatingProvider ratingProvider;
    Map<String, String> ratingProviderArgs;
    int statusBarColorDefault;

    public enum PushAction {
        pmc,
        unknown;

        public static PushAction parse(String str) {
            try {
                return valueOf(str);
            } catch (IllegalArgumentException e) {
                ApptentiveLog.d("Error parsing unknown PushAction: " + str, new Object[0]);
                return unknown;
            }
        }
    }

    public static boolean isApptentiveRegistered() {
        return sApptentiveInternal != null;
    }

    public static ApptentiveInternal createInstance(Context context, String str) {
        if (sApptentiveInternal == null) {
            synchronized (ApptentiveInternal.class) {
                if (sApptentiveInternal == null && context != null) {
                    sApptentiveInternal = new ApptentiveInternal();
                    isApptentiveInitialized.set(false);
                    sApptentiveInternal.appContext = context.getApplicationContext();
                    sApptentiveInternal.prefs = sApptentiveInternal.appContext.getSharedPreferences("APPTENTIVE", 0);
                    MessageManager messageManager = new MessageManager();
                    PayloadSendWorker payloadSendWorker = new PayloadSendWorker();
                    InteractionManager interactionManager = new InteractionManager();
                    ApptentiveDatabase apptentiveDatabase = new ApptentiveDatabase(sApptentiveInternal.appContext);
                    CodePointStore codePointStore = new CodePointStore();
                    sApptentiveInternal.messageManager = messageManager;
                    sApptentiveInternal.payloadWorker = payloadSendWorker;
                    sApptentiveInternal.interactionManager = interactionManager;
                    sApptentiveInternal.database = apptentiveDatabase;
                    sApptentiveInternal.codePointStore = codePointStore;
                    sApptentiveInternal.cachedExecutor = Executors.newCachedThreadPool();
                    sApptentiveInternal.apiKey = Util.trim(str);
                }
            }
        }
        return sApptentiveInternal;
    }

    public static ApptentiveInternal getInstance(Context context) {
        if (context == null) {
            context = null;
        }
        return createInstance(context, null);
    }

    public static ApptentiveInternal getInstance() {
        if (!(sApptentiveInternal == null || isApptentiveInitialized.get())) {
            synchronized (ApptentiveInternal.class) {
                if (!(sApptentiveInternal == null || isApptentiveInitialized.get())) {
                    isApptentiveInitialized.set(true);
                    if (!sApptentiveInternal.init()) {
                        ApptentiveLog.e("Apptentive init() failed", new Object[0]);
                    }
                }
            }
        }
        return sApptentiveInternal;
    }

    public static void setInstance(ApptentiveInternal apptentiveInternal) {
        sApptentiveInternal = apptentiveInternal;
        isApptentiveInitialized.set(false);
    }

    public static void setApplicationContext(Context context) {
        synchronized (ApptentiveInternal.class) {
            ApptentiveInternal instance = getInstance();
            if (instance != null) {
                instance.appContext = context;
            }
        }
    }

    static void setLifeCycleCallback() {
        if (sApptentiveInternal != null && sApptentiveInternal.lifecycleCallbacks == null) {
            synchronized (ApptentiveInternal.class) {
                if (sApptentiveInternal != null && sApptentiveInternal.lifecycleCallbacks == null && (sApptentiveInternal.appContext instanceof Application)) {
                    sApptentiveInternal.lifecycleCallbacks = new ApptentiveActivityLifecycleCallbacks();
                    ((Application) sApptentiveInternal.appContext).registerActivityLifecycleCallbacks(sApptentiveInternal.lifecycleCallbacks);
                }
            }
        }
    }

    public boolean setApplicationDefaultTheme(int i) {
        TypedArray obtainStyledAttributes;
        if (i != 0) {
            try {
                this.appContext.getResources().getResourceName(i);
                Theme newTheme = this.appContext.getResources().newTheme();
                newTheme.applyStyle(i, true);
                obtainStyledAttributes = newTheme.obtainStyledAttributes(R.styleable.AppCompatTheme);
                if (obtainStyledAttributes.hasValue(83)) {
                    this.appDefaultAppCompatThemeId = i;
                    obtainStyledAttributes.recycle();
                    return true;
                }
                obtainStyledAttributes.recycle();
            } catch (NotFoundException e) {
                ApptentiveLog.e("Theme Res id not found", new Object[0]);
            } catch (Throwable th) {
                obtainStyledAttributes.recycle();
            }
        }
        return false;
    }

    public Context getApplicationContext() {
        return this.appContext;
    }

    public ApptentiveActivityLifecycleCallbacks getRegisteredLifecycleCallbacks() {
        return this.lifecycleCallbacks;
    }

    public Activity getCurrentTaskStackTopActivity() {
        if (this.currentTaskStackTopActivity != null) {
            return (Activity) this.currentTaskStackTopActivity.get();
        }
        return null;
    }

    public MessageManager getMessageManager() {
        return this.messageManager;
    }

    public InteractionManager getInteractionManager() {
        return this.interactionManager;
    }

    public PayloadSendWorker getPayloadWorker() {
        return this.payloadWorker;
    }

    public ApptentiveDatabase getApptentiveDatabase() {
        return this.database;
    }

    public CodePointStore getCodePointStore() {
        return this.codePointStore;
    }

    public Theme getApptentiveToolbarTheme() {
        return this.apptentiveToolbarTheme;
    }

    public int getDefaultStatusbarColor() {
        return this.statusBarColorDefault;
    }

    public String getApptentiveConversationToken() {
        return this.conversationToken;
    }

    public String getApptentiveApiKey() {
        return this.apiKey;
    }

    public String getDefaultAppDisplayName() {
        return this.defaultAppDisplayName;
    }

    public boolean isApptentiveDebuggable() {
        return this.isAppDebuggable;
    }

    public String getPersonId() {
        return this.personId;
    }

    public String getAndroidId() {
        return this.androidId;
    }

    public SharedPreferences getSharedPrefs() {
        return this.prefs;
    }

    public void addCustomDeviceData(String str, Object obj) {
        if (str != null && str.trim().length() != 0) {
            String trim = str.trim();
            CustomData loadCustomDeviceData = DeviceManager.loadCustomDeviceData();
            if (loadCustomDeviceData != null) {
                try {
                    loadCustomDeviceData.put(trim, obj);
                    DeviceManager.storeCustomDeviceData(loadCustomDeviceData);
                } catch (Throwable e) {
                    ApptentiveLog.w("Unable to add custom device data.", e, new Object[0]);
                }
            }
        }
    }

    public void addCustomPersonData(String str, Object obj) {
        if (str != null && str.trim().length() != 0) {
            CustomData loadCustomPersonData = PersonManager.loadCustomPersonData();
            if (loadCustomPersonData != null) {
                try {
                    loadCustomPersonData.put(str, obj);
                    PersonManager.storeCustomPersonData(loadCustomPersonData);
                } catch (Throwable e) {
                    ApptentiveLog.w("Unable to add custom person data.", e, new Object[0]);
                }
            }
        }
    }

    public void runOnWorkerThread(Runnable runnable) {
        this.cachedExecutor.execute(runnable);
    }

    public void scheduleOnWorkerThread(Runnable runnable) {
        this.cachedExecutor.submit(runnable);
    }

    public void checkAndUpdateApptentiveConfigurations() {
        if (this.conversationToken == null || this.personId == null) {
            asyncFetchConversationToken();
        } else {
            asyncFetchAppConfigurationAndInteractions();
        }
    }

    public void onAppLaunch(Context context) {
        EngagementModule.engageInternal(context, EventLabel.app__launch.getLabelName());
    }

    public void onAppExit(Context context) {
        EngagementModule.engageInternal(context, EventLabel.app__exit.getLabelName());
    }

    public void onActivityStarted(Activity activity) {
        if (activity != null) {
            this.currentTaskStackTopActivity = new WeakReference(activity);
            this.messageManager.setCurrentForgroundActivity(activity);
        }
        checkAndUpdateApptentiveConfigurations();
        syncDevice();
        syncSdk();
        syncPerson();
    }

    public void onActivityResumed(Activity activity) {
        if (activity != null) {
            this.currentTaskStackTopActivity = new WeakReference(activity);
            this.messageManager.setCurrentForgroundActivity(activity);
        }
    }

    public void onAppEnterForeground() {
        this.appIsInForeground = true;
        this.payloadWorker.appWentToForeground();
        this.messageManager.appWentToForeground();
    }

    public void onAppEnterBackground() {
        this.appIsInForeground = false;
        this.currentTaskStackTopActivity = null;
        this.messageManager.setCurrentForgroundActivity(null);
        this.payloadWorker.appWentToBackground();
        this.messageManager.appWentToBackground();
    }

    public void updateApptentiveInteractionTheme(Theme theme, Context context) {
        if (!(context instanceof Activity)) {
            theme.applyStyle(R.style.ApptentiveTheme_Base_Versioned, true);
        }
        if (this.appDefaultAppCompatThemeId != 0) {
            theme.applyStyle(this.appDefaultAppCompatThemeId, true);
        }
        theme.applyStyle(R.style.ApptentiveBaseFrameTheme, true);
        int identifier = context.getResources().getIdentifier("ApptentiveThemeOverride", "style", getApplicationContext().getPackageName());
        if (identifier != 0) {
            theme.applyStyle(identifier, true);
        }
        if (VERSION.SDK_INT >= 21) {
            identifier = ContextCompat.getColor(context, 17170445);
            TypedArray obtainStyledAttributes = theme.obtainStyledAttributes(new int[]{16843857});
            try {
                this.statusBarColorDefault = obtainStyledAttributes.getColor(0, identifier);
            } finally {
                obtainStyledAttributes.recycle();
            }
        }
        identifier = Util.getResourceIdFromAttribute(theme, R.attr.apptentiveToolbarTheme);
        this.apptentiveToolbarTheme.setTo(theme);
        this.apptentiveToolbarTheme.applyStyle(identifier, true);
    }

    public boolean init() {
        String trim;
        boolean z;
        Object obj;
        String str;
        boolean z2;
        Throwable e;
        boolean z3;
        String str2 = null;
        this.codePointStore.init();
        if (this.prefs.getBoolean("messageCenterFeatureUsed", false)) {
            this.messageManager.init();
        }
        this.conversationToken = this.prefs.getString("conversationToken", null);
        this.conversationId = this.prefs.getString("conversationId", null);
        this.personId = this.prefs.getString("personId", null);
        this.apptentiveToolbarTheme = this.appContext.getResources().newTheme();
        try {
            this.appPackageName = this.appContext.getPackageName();
            PackageManager packageManager = this.appContext.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(this.appPackageName, 130);
            ApplicationInfo applicationInfo = packageInfo.applicationInfo;
            Bundle bundle = applicationInfo.metaData;
            if (bundle != null) {
                trim = Util.trim(bundle.getString("apptentive_api_key"));
                try {
                    str2 = Util.trim(bundle.getString("apptentive_log_level"));
                    z = bundle.getBoolean("apptentive_debug");
                    try {
                        this.isAppDebuggable = (applicationInfo.flags & 2) != 0;
                        obj = trim;
                        str = str2;
                        z2 = z;
                    } catch (Exception e2) {
                        e = e2;
                        ApptentiveLog.e("Unexpected error while reading application or package info.", e, new Object[0]);
                        obj = trim;
                        str = str2;
                        z2 = z;
                        z3 = false;
                        if (z2) {
                            ApptentiveLog.i("Apptentive debug logging set to VERBOSE.", new Object[0]);
                            setMinimumLogLevel(Level.VERBOSE);
                        } else if (str != null) {
                            ApptentiveLog.i("Overriding log level: %s", str);
                            setMinimumLogLevel(Level.parse(str));
                        } else if (this.isAppDebuggable) {
                            setMinimumLogLevel(Level.VERBOSE);
                        }
                        ApptentiveLog.i("Debug mode enabled? %b", Boolean.valueOf(this.isAppDebuggable));
                        trim = this.prefs.getString("lastSeenSdkVersion", BuildConfig.FLAVOR);
                        if (!trim.equals(BuildConfig.VERSION_NAME)) {
                            onSdkVersionChanged(this.appContext, trim, BuildConfig.VERSION_NAME);
                        }
                        this.apiKey = obj;
                        if (!TextUtils.isEmpty(this.apiKey)) {
                        }
                        trim = "The Apptentive API Key is not defined. You may provide your Apptentive API Key in Apptentive.register(), or in as meta-data in your AndroidManifest.xml.\n<meta-data android:name=\"apptentive_api_key\"\n           android:value=\"@string/your_apptentive_api_key\"/>";
                        if (this.isAppDebuggable) {
                            ApptentiveLog.e(trim, new Object[0]);
                            ApptentiveLog.d("Apptentive API Key: %s", this.apiKey);
                            this.androidId = Secure.getString(this.appContext.getContentResolver(), "android_id");
                            ApptentiveLog.d("Android ID: ", this.androidId);
                            ApptentiveLog.d("Default Locale: %s", Locale.getDefault().toString());
                            ApptentiveLog.d("Conversation id: %s", this.prefs.getString("conversationId", "null"));
                            return z3;
                        }
                        throw new RuntimeException(trim);
                    }
                } catch (Exception e3) {
                    e = e3;
                    z = false;
                    ApptentiveLog.e("Unexpected error while reading application or package info.", e, new Object[0]);
                    obj = trim;
                    str = str2;
                    z2 = z;
                    z3 = false;
                    if (z2) {
                        ApptentiveLog.i("Apptentive debug logging set to VERBOSE.", new Object[0]);
                        setMinimumLogLevel(Level.VERBOSE);
                    } else if (str != null) {
                        ApptentiveLog.i("Overriding log level: %s", str);
                        setMinimumLogLevel(Level.parse(str));
                    } else if (this.isAppDebuggable) {
                        setMinimumLogLevel(Level.VERBOSE);
                    }
                    ApptentiveLog.i("Debug mode enabled? %b", Boolean.valueOf(this.isAppDebuggable));
                    trim = this.prefs.getString("lastSeenSdkVersion", BuildConfig.FLAVOR);
                    if (trim.equals(BuildConfig.VERSION_NAME)) {
                        onSdkVersionChanged(this.appContext, trim, BuildConfig.VERSION_NAME);
                    }
                    this.apiKey = obj;
                    if (TextUtils.isEmpty(this.apiKey)) {
                    }
                    trim = "The Apptentive API Key is not defined. You may provide your Apptentive API Key in Apptentive.register(), or in as meta-data in your AndroidManifest.xml.\n<meta-data android:name=\"apptentive_api_key\"\n           android:value=\"@string/your_apptentive_api_key\"/>";
                    if (this.isAppDebuggable) {
                        throw new RuntimeException(trim);
                    }
                    ApptentiveLog.e(trim, new Object[0]);
                    ApptentiveLog.d("Apptentive API Key: %s", this.apiKey);
                    this.androidId = Secure.getString(this.appContext.getContentResolver(), "android_id");
                    ApptentiveLog.d("Android ID: ", this.androidId);
                    ApptentiveLog.d("Default Locale: %s", Locale.getDefault().toString());
                    ApptentiveLog.d("Conversation id: %s", this.prefs.getString("conversationId", "null"));
                    return z3;
                }
            }
            obj = null;
            str = null;
            z2 = false;
            try {
                z3 = setApplicationDefaultTheme(applicationInfo.theme);
                int identifier = this.appContext.getResources().getIdentifier("ApptentiveThemeOverride", "style", this.appPackageName);
                Integer valueOf = Integer.valueOf(packageInfo.versionCode);
                String str3 = packageInfo.versionName;
                VersionHistoryEntry lastVersionSeen = VersionHistoryStore.getLastVersionSeen();
                AppRelease appRelease = new AppRelease();
                appRelease.setVersion(str3);
                appRelease.setIdentifier(this.appPackageName);
                appRelease.setBuildNumber(String.valueOf(valueOf));
                appRelease.setTargetSdkVersion(String.valueOf(packageInfo.applicationInfo.targetSdkVersion));
                appRelease.setAppStore(Util.getInstallerPackageName(this.appContext));
                appRelease.setInheritStyle(z3);
                appRelease.setOverrideStyle(identifier != 0);
                if (lastVersionSeen == null) {
                    onVersionChanged(null, valueOf, null, str3, appRelease);
                } else if (!(valueOf.equals(lastVersionSeen.versionCode) && str3.equals(lastVersionSeen.versionName))) {
                    onVersionChanged(lastVersionSeen.versionCode, valueOf, lastVersionSeen.versionName, str3, appRelease);
                }
                this.defaultAppDisplayName = packageManager.getApplicationLabel(packageManager.getApplicationInfo(packageInfo.packageName, 0)).toString();
                ActivityInfo[] activityInfoArr = packageInfo.receivers;
                if (activityInfoArr != null) {
                    for (ActivityInfo activityInfo : activityInfoArr) {
                        if (activityInfo.name.equals("com.apptentive.android.sdk.comm.NetworkStateReceiver")) {
                            throw new AssertionError("NetworkStateReceiver has been removed from Apptentive SDK, please make sure it's also removed from manifest file");
                        }
                    }
                }
                z3 = true;
            } catch (Exception e4) {
                e = e4;
                trim = obj;
                str2 = str;
                z = z2;
                ApptentiveLog.e("Unexpected error while reading application or package info.", e, new Object[0]);
                obj = trim;
                str = str2;
                z2 = z;
                z3 = false;
                if (z2) {
                    ApptentiveLog.i("Apptentive debug logging set to VERBOSE.", new Object[0]);
                    setMinimumLogLevel(Level.VERBOSE);
                } else if (str != null) {
                    ApptentiveLog.i("Overriding log level: %s", str);
                    setMinimumLogLevel(Level.parse(str));
                } else if (this.isAppDebuggable) {
                    setMinimumLogLevel(Level.VERBOSE);
                }
                ApptentiveLog.i("Debug mode enabled? %b", Boolean.valueOf(this.isAppDebuggable));
                trim = this.prefs.getString("lastSeenSdkVersion", BuildConfig.FLAVOR);
                if (trim.equals(BuildConfig.VERSION_NAME)) {
                    onSdkVersionChanged(this.appContext, trim, BuildConfig.VERSION_NAME);
                }
                this.apiKey = obj;
                if (TextUtils.isEmpty(this.apiKey)) {
                }
                trim = "The Apptentive API Key is not defined. You may provide your Apptentive API Key in Apptentive.register(), or in as meta-data in your AndroidManifest.xml.\n<meta-data android:name=\"apptentive_api_key\"\n           android:value=\"@string/your_apptentive_api_key\"/>";
                if (this.isAppDebuggable) {
                    ApptentiveLog.e(trim, new Object[0]);
                    ApptentiveLog.d("Apptentive API Key: %s", this.apiKey);
                    this.androidId = Secure.getString(this.appContext.getContentResolver(), "android_id");
                    ApptentiveLog.d("Android ID: ", this.androidId);
                    ApptentiveLog.d("Default Locale: %s", Locale.getDefault().toString());
                    ApptentiveLog.d("Conversation id: %s", this.prefs.getString("conversationId", "null"));
                    return z3;
                }
                throw new RuntimeException(trim);
            }
        } catch (Exception e5) {
            e = e5;
            trim = null;
            z = false;
            ApptentiveLog.e("Unexpected error while reading application or package info.", e, new Object[0]);
            obj = trim;
            str = str2;
            z2 = z;
            z3 = false;
            if (z2) {
                ApptentiveLog.i("Apptentive debug logging set to VERBOSE.", new Object[0]);
                setMinimumLogLevel(Level.VERBOSE);
            } else if (str != null) {
                ApptentiveLog.i("Overriding log level: %s", str);
                setMinimumLogLevel(Level.parse(str));
            } else if (this.isAppDebuggable) {
                setMinimumLogLevel(Level.VERBOSE);
            }
            ApptentiveLog.i("Debug mode enabled? %b", Boolean.valueOf(this.isAppDebuggable));
            trim = this.prefs.getString("lastSeenSdkVersion", BuildConfig.FLAVOR);
            if (trim.equals(BuildConfig.VERSION_NAME)) {
                onSdkVersionChanged(this.appContext, trim, BuildConfig.VERSION_NAME);
            }
            this.apiKey = obj;
            if (TextUtils.isEmpty(this.apiKey)) {
            }
            trim = "The Apptentive API Key is not defined. You may provide your Apptentive API Key in Apptentive.register(), or in as meta-data in your AndroidManifest.xml.\n<meta-data android:name=\"apptentive_api_key\"\n           android:value=\"@string/your_apptentive_api_key\"/>";
            if (this.isAppDebuggable) {
                throw new RuntimeException(trim);
            }
            ApptentiveLog.e(trim, new Object[0]);
            ApptentiveLog.d("Apptentive API Key: %s", this.apiKey);
            this.androidId = Secure.getString(this.appContext.getContentResolver(), "android_id");
            ApptentiveLog.d("Android ID: ", this.androidId);
            ApptentiveLog.d("Default Locale: %s", Locale.getDefault().toString());
            ApptentiveLog.d("Conversation id: %s", this.prefs.getString("conversationId", "null"));
            return z3;
        }
        if (z2) {
            ApptentiveLog.i("Apptentive debug logging set to VERBOSE.", new Object[0]);
            setMinimumLogLevel(Level.VERBOSE);
        } else if (str != null) {
            ApptentiveLog.i("Overriding log level: %s", str);
            setMinimumLogLevel(Level.parse(str));
        } else if (this.isAppDebuggable) {
            setMinimumLogLevel(Level.VERBOSE);
        }
        ApptentiveLog.i("Debug mode enabled? %b", Boolean.valueOf(this.isAppDebuggable));
        trim = this.prefs.getString("lastSeenSdkVersion", BuildConfig.FLAVOR);
        if (trim.equals(BuildConfig.VERSION_NAME)) {
            onSdkVersionChanged(this.appContext, trim, BuildConfig.VERSION_NAME);
        }
        if (TextUtils.isEmpty(this.apiKey) && !TextUtils.isEmpty(obj)) {
            this.apiKey = obj;
        }
        if (TextUtils.isEmpty(this.apiKey) || this.apiKey.contains("YOUR_APPTENTIVE_API_KEY")) {
            trim = "The Apptentive API Key is not defined. You may provide your Apptentive API Key in Apptentive.register(), or in as meta-data in your AndroidManifest.xml.\n<meta-data android:name=\"apptentive_api_key\"\n           android:value=\"@string/your_apptentive_api_key\"/>";
            if (this.isAppDebuggable) {
                throw new RuntimeException(trim);
            }
            ApptentiveLog.e(trim, new Object[0]);
        } else {
            ApptentiveLog.d("Using cached Apptentive API Key", new Object[0]);
        }
        ApptentiveLog.d("Apptentive API Key: %s", this.apiKey);
        this.androidId = Secure.getString(this.appContext.getContentResolver(), "android_id");
        ApptentiveLog.d("Android ID: ", this.androidId);
        ApptentiveLog.d("Default Locale: %s", Locale.getDefault().toString());
        ApptentiveLog.d("Conversation id: %s", this.prefs.getString("conversationId", "null"));
        return z3;
    }

    private void onVersionChanged(Integer num, Integer num2, String str, String str2, AppRelease appRelease) {
        ApptentiveLog.i("Version changed: Name: %s => %s, Code: %d => %d", str, str2, num, num2);
        VersionHistoryStore.updateVersionHistory(num2, str2);
        if (AppReleaseManager.storeAppReleaseAndReturnDiff(appRelease) != null) {
            ApptentiveLog.d("App release was updated.", new Object[0]);
            this.database.addPayload(new Payload[]{r0});
        }
        invalidateCaches();
    }

    private void onSdkVersionChanged(Context context, String str, String str2) {
        ApptentiveLog.i("SDK version changed: %s => %s", str, str2);
        context.getSharedPreferences("APPTENTIVE", 0).edit().putString("lastSeenSdkVersion", str2).apply();
        invalidateCaches();
    }

    private void invalidateCaches() {
        this.interactionManager.updateCacheExpiration(0);
        Configuration load = Configuration.load();
        load.setConfigurationCacheExpirationMillis(System.currentTimeMillis());
        load.save();
    }

    private synchronized void asyncFetchConversationToken() {
        if (this.isConversationTokenFetchPending.compareAndSet(false, true)) {
            AsyncTask anonymousClass1 = new AsyncTask<Void, Void, Boolean>() {
                private Exception e = null;

                protected Boolean doInBackground(Void... voidArr) {
                    Boolean bool = new Boolean(false);
                    try {
                        bool = Boolean.valueOf(ApptentiveInternal.this.fetchConversationToken());
                    } catch (Exception e) {
                        this.e = e;
                    }
                    return bool;
                }

                protected void onPostExecute(Boolean bool) {
                    if (this.e == null) {
                        ApptentiveLog.i("Fetching conversation token asyncTask finished. Result:" + bool.booleanValue(), new Object[0]);
                        ApptentiveInternal.this.isConversationTokenFetchPending.set(false);
                        if (bool.booleanValue()) {
                            ApptentiveInternal.this.asyncFetchAppConfigurationAndInteractions();
                            return;
                        }
                        return;
                    }
                    ApptentiveLog.w("Unhandled Exception thrown from fetching conversation token asyncTask", this.e, new Object[0]);
                    MetricModule.sendError(this.e, null, null);
                }
            };
            ApptentiveLog.i("Fetching conversation token asyncTask scheduled.", new Object[0]);
            if (VERSION.SDK_INT >= 11) {
                anonymousClass1.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new Void[0]);
            } else {
                anonymousClass1.execute(new Void[0]);
            }
        } else {
            ApptentiveLog.v("Fetching Configuration pending", new Object[0]);
        }
    }

    private boolean fetchConversationToken() {
        ApptentiveLog.i("Fetching Configuration token task started.", new Object[0]);
        ConversationTokenRequest conversationTokenRequest = new ConversationTokenRequest();
        conversationTokenRequest.setDevice(DeviceManager.storeDeviceAndReturnIt());
        conversationTokenRequest.setSdk(SdkManager.storeSdkAndReturnIt());
        conversationTokenRequest.setPerson(PersonManager.storePersonAndReturnIt());
        ApptentiveHttpResponse conversationToken = ApptentiveClient.getConversationToken(conversationTokenRequest);
        if (conversationToken == null) {
            ApptentiveLog.w("Got null response fetching ConversationToken.", new Object[0]);
            return false;
        } else if (!conversationToken.isSuccessful()) {
            return false;
        } else {
            try {
                JSONObject jSONObject = new JSONObject(conversationToken.getContent());
                String string = jSONObject.getString("token");
                ApptentiveLog.d("ConversationToken: " + string, new Object[0]);
                ApptentiveLog.d("New Conversation id: %s", jSONObject.getString("id"));
                if (!(string == null || string.equals(BuildConfig.FLAVOR))) {
                    setConversationToken(string);
                    setConversationId(r4);
                }
                string = jSONObject.getString("person_id");
                ApptentiveLog.d("PersonId: " + string, new Object[0]);
                if (!(string == null || string.equals(BuildConfig.FLAVOR))) {
                    setPersonId(string);
                }
                return true;
            } catch (Throwable e) {
                ApptentiveLog.e("Error parsing ConversationToken response json.", e, new Object[0]);
                return false;
            }
        }
    }

    private void fetchAppConfiguration() {
        ApptentiveLog.i("Fetching new Configuration task started.", new Object[0]);
        ApptentiveHttpResponse appConfiguration = ApptentiveClient.getAppConfiguration();
        try {
            Map headers = appConfiguration.getHeaders();
            if (headers != null) {
                Integer parseCacheControlHeader = Util.parseCacheControlHeader((String) headers.get("Cache-Control"));
                if (parseCacheControlHeader == null) {
                    parseCacheControlHeader = Integer.valueOf(86400);
                }
                ApptentiveLog.d("Caching configuration for %d seconds.", parseCacheControlHeader);
                Configuration configuration = new Configuration(appConfiguration.getContent());
                configuration.setConfigurationCacheExpirationMillis(((long) (parseCacheControlHeader.intValue() * PostAdPaymentModule.REDIRECT_TO_MANAGE_ADS_TIMEOUT)) + System.currentTimeMillis());
                configuration.save();
            }
        } catch (Throwable e) {
            ApptentiveLog.e("Error parsing app configuration from server.", e, new Object[0]);
        }
    }

    private void asyncFetchAppConfigurationAndInteractions() {
        boolean z = this.isAppDebuggable;
        if (this.isConfigurationFetchPending.compareAndSet(false, true) && (z || Configuration.load().hasConfigurationCacheExpired())) {
            AsyncTask anonymousClass2 = new AsyncTask<Void, Void, Void>() {
                private Exception e = null;

                protected Void doInBackground(Void... voidArr) {
                    try {
                        ApptentiveInternal.this.fetchAppConfiguration();
                    } catch (Exception e) {
                        this.e = e;
                    }
                    return null;
                }

                protected void onPostExecute(Void voidR) {
                    ApptentiveLog.i("Fetching new Configuration asyncTask finished.", new Object[0]);
                    ApptentiveInternal.this.isConfigurationFetchPending.set(false);
                    if (this.e != null) {
                        ApptentiveLog.w("Unhandled Exception thrown from fetching configuration asyncTask", this.e, new Object[0]);
                        MetricModule.sendError(this.e, null, null);
                        return;
                    }
                    ApptentiveInternal.this.interactionManager.asyncFetchAndStoreInteractions();
                }
            };
            ApptentiveLog.i("Fetching new Configuration asyncTask scheduled.", new Object[0]);
            if (VERSION.SDK_INT >= 11) {
                anonymousClass2.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new Void[0]);
                return;
            } else {
                anonymousClass2.execute(new Void[0]);
                return;
            }
        }
        ApptentiveLog.v("Using cached Configuration.", new Object[0]);
        this.interactionManager.asyncFetchAndStoreInteractions();
    }

    void syncDevice() {
        Device storeDeviceAndReturnDiff = DeviceManager.storeDeviceAndReturnDiff();
        if (storeDeviceAndReturnDiff != null) {
            ApptentiveLog.d("Device info was updated.", new Object[0]);
            ApptentiveLog.v(storeDeviceAndReturnDiff.toString(), new Object[0]);
            this.database.addPayload(new Payload[]{storeDeviceAndReturnDiff});
            return;
        }
        ApptentiveLog.d("Device info was not updated.", new Object[0]);
    }

    private void syncSdk() {
        Sdk storeSdkAndReturnDiff = SdkManager.storeSdkAndReturnDiff();
        if (storeSdkAndReturnDiff != null) {
            ApptentiveLog.d("Sdk was updated.", new Object[0]);
            ApptentiveLog.v(storeSdkAndReturnDiff.toString(), new Object[0]);
            this.database.addPayload(new Payload[]{storeSdkAndReturnDiff});
            return;
        }
        ApptentiveLog.d("Sdk was not updated.", new Object[0]);
    }

    private void syncPerson() {
        Person storePersonAndReturnDiff = PersonManager.storePersonAndReturnDiff();
        if (storePersonAndReturnDiff != null) {
            ApptentiveLog.d("Person was updated.", new Object[0]);
            ApptentiveLog.v(storePersonAndReturnDiff.toString(), new Object[0]);
            this.database.addPayload(new Payload[]{storePersonAndReturnDiff});
            return;
        }
        ApptentiveLog.d("Person was not updated.", new Object[0]);
    }

    public IRatingProvider getRatingProvider() {
        if (this.ratingProvider == null) {
            this.ratingProvider = new GooglePlayRatingProvider();
        }
        return this.ratingProvider;
    }

    public void setRatingProvider(IRatingProvider iRatingProvider) {
        this.ratingProvider = iRatingProvider;
    }

    public Map<String, String> getRatingProviderArgs() {
        return this.ratingProviderArgs;
    }

    public void putRatingProviderArg(String str, String str2) {
        if (this.ratingProviderArgs == null) {
            this.ratingProviderArgs = new HashMap();
        }
        this.ratingProviderArgs.put(str, str2);
    }

    public void setOnSurveyFinishedListener(OnSurveyFinishedListener onSurveyFinishedListener) {
        if (onSurveyFinishedListener != null) {
            this.onSurveyFinishedListener = new WeakReference(onSurveyFinishedListener);
        } else {
            this.onSurveyFinishedListener = null;
        }
    }

    public OnSurveyFinishedListener getOnSurveyFinishedListener() {
        return this.onSurveyFinishedListener == null ? null : (OnSurveyFinishedListener) this.onSurveyFinishedListener.get();
    }

    public void addInteractionUpdateListener(InteractionUpdateListener interactionUpdateListener) {
        this.interactionUpdateListeners.add(interactionUpdateListener);
    }

    public void removeInteractionUpdateListener(InteractionUpdateListener interactionUpdateListener) {
        this.interactionUpdateListeners.remove(interactionUpdateListener);
    }

    public void setMinimumLogLevel(Level level) {
        ApptentiveLog.overrideLogLevel(level);
    }

    public void setPushCallbackActivity(Class<? extends Activity> cls) {
        this.pushCallbackActivityName = cls.getName();
        ApptentiveLog.d("Setting push callback activity name to %s", this.pushCallbackActivityName);
    }

    public String getPushCallbackActivityName() {
        return this.pushCallbackActivityName;
    }

    static String getApptentivePushNotificationData(Intent intent) {
        String str = null;
        if (intent == null) {
            return str;
        }
        ApptentiveLog.v("Got an Intent.", new Object[0]);
        if (intent.hasExtra(PARSE_PUSH_EXTRA_KEY)) {
            String stringExtra = intent.getStringExtra(PARSE_PUSH_EXTRA_KEY);
            ApptentiveLog.v("Got a Parse Push.", new Object[0]);
            try {
                return new JSONObject(stringExtra).optString(APPTENTIVE_PUSH_EXTRA_KEY, null);
            } catch (JSONException e) {
                ApptentiveLog.e("Corrupt Parse String Extra: %s", stringExtra);
                return str;
            }
        }
        ApptentiveLog.v("Got a non-Parse push.", new Object[0]);
        return intent.getStringExtra(APPTENTIVE_PUSH_EXTRA_KEY);
    }

    static String getApptentivePushNotificationData(Bundle bundle) {
        if (bundle != null) {
            return bundle.getString(APPTENTIVE_PUSH_EXTRA_KEY);
        }
        return null;
    }

    boolean setPendingPushNotification(String str) {
        if (str == null) {
            return false;
        }
        ApptentiveLog.d("Saving Apptentive push notification data.", new Object[0]);
        this.prefs.edit().putString("pendingPushNotification", str).apply();
        this.messageManager.startMessagePreFetchTask();
        return true;
    }

    public void showAboutInternal(Context context, boolean z) {
        Intent intent = new Intent();
        intent.setClass(context, ApptentiveViewActivity.class);
        intent.putExtra("fragmentType", 1);
        intent.putExtra("fragmentExtraData", z);
        if (!(context instanceof Activity)) {
            intent.addFlags(402653184);
        }
        context.startActivity(intent);
    }

    public boolean showMessageCenterInternal(Context context, Map<String, Object> map) {
        if (canShowMessageCenterInternal()) {
            if (map != null) {
                Iterator it = map.keySet().iterator();
                while (it.hasNext()) {
                    Object obj = map.get((String) it.next());
                    if (!(obj == null || (obj instanceof String) || (obj instanceof Boolean) || (obj instanceof Long) || (obj instanceof Double) || (obj instanceof Float) || (obj instanceof Integer) || (obj instanceof Short))) {
                        ApptentiveLog.w("Removing invalid customData type: %s", obj.getClass().getSimpleName());
                        it.remove();
                    }
                }
            }
            this.customData = map;
            boolean engageInternal = EngagementModule.engageInternal(context, "show_message_center");
            if (engageInternal) {
                return engageInternal;
            }
            this.customData = null;
            return engageInternal;
        }
        showMessageCenterFallback(context);
        return false;
    }

    public void showMessageCenterFallback(Context context) {
        EngagementModule.launchMessageCenterErrorActivity(context);
    }

    public boolean canShowMessageCenterInternal() {
        return EngagementModule.canShowInteraction("com.apptentive", "app", "show_message_center");
    }

    public Map<String, Object> getAndClearCustomData() {
        Map<String, Object> map = this.customData;
        this.customData = null;
        return map;
    }

    private void setConversationToken(String str) {
        this.conversationToken = str;
        this.prefs.edit().putString("conversationToken", this.conversationToken).apply();
    }

    private void setConversationId(String str) {
        this.conversationId = str;
        this.prefs.edit().putString("conversationId", this.conversationId).apply();
    }

    private void setPersonId(String str) {
        this.personId = str;
        this.prefs.edit().putString("personId", this.personId).apply();
    }

    public void resetSdkState() {
        this.prefs.edit().clear().apply();
        this.database.reset(this.appContext);
    }

    public void notifyInteractionUpdated(boolean z) {
        Iterator it = this.interactionUpdateListeners.iterator();
        while (it.hasNext()) {
            InteractionUpdateListener interactionUpdateListener = (InteractionUpdateListener) it.next();
            if (interactionUpdateListener != null) {
                interactionUpdateListener.onInteractionUpdated(z);
            }
        }
    }

    public static PendingIntent prepareMessageCenterPendingIntent(Context context) {
        Intent intent;
        if (Apptentive.canShowMessageCenter()) {
            intent = new Intent();
            intent.setClass(context, ApptentiveViewActivity.class);
            intent.putExtra("fragmentType", 4);
            intent.putExtra("fragmentExtraData", "show_message_center");
        } else {
            intent = MessageCenterInteraction.generateMessageCenterErrorIntent(context);
        }
        return intent != null ? PendingIntent.getActivity(context, 0, intent, 1207959552) : null;
    }
}
