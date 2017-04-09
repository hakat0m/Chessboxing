package com.gumtree.android;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.preference.PreferenceManager;
import android.support.multidex.MultiDexApplication;
import com.apptentive.android.sdk.Apptentive;
import com.comscore.analytics.comScore;
import com.ebay.classifieds.capi.ICapiClient;
import com.gumtree.AndroidDevelopmentFlags;
import com.gumtree.DevelopmentFlags;
import com.gumtree.Log;
import com.gumtree.RxSchedulersHookHost;
import com.gumtree.android.api.tracking.Tracking;
import com.gumtree.android.auth.BaseAccountManager;
import com.gumtree.android.common.analytics.Track;
import com.gumtree.android.common.cleanup.CleanupIntentService;
import com.gumtree.android.common.http.CAPIIntentFactory;
import com.gumtree.android.common.http.GumtreeCAPIIntentFactory;
import com.gumtree.android.common.http.TrackingInfo;
import com.gumtree.android.common.location.AppLocation;
import com.gumtree.android.common.location.LocationDAO;
import com.gumtree.android.common.location.LocationProvider;
import com.gumtree.android.common.transport.AutoInflatingTransport;
import com.gumtree.android.common.transport.CapiAwareTransport;
import com.gumtree.android.common.transport.HttpUrlConnectionTransport;
import com.gumtree.android.common.transport.Transport;
import com.gumtree.android.common.utils.crashlytics.CrashlyticsHelper;
import com.gumtree.android.common.utils.debug.StrictMode;
import com.gumtree.android.dagger.ComponentsManager;
import com.gumtree.android.dagger.components.ApplicationComponent;
import com.gumtree.android.dagger.components.BaseComponent;
import com.gumtree.android.dagger.components.DaggerApplicationComponent;
import com.gumtree.android.dagger.modules.ApplicationModule;
import com.gumtree.android.dfp.DFPProcessor;
import com.gumtree.android.gumloc.LocationServiceCheck;
import com.gumtree.android.handler.ContentProviderDataStorage;
import com.gumtree.android.handler.DataStorage;
import com.gumtree.android.notifications.GumtreeBadgeCounterManager;
import com.gumtree.android.notifications.providers.PushNotificationsProvider;
import com.gumtree.android.startup.SecurityProviderManager;
import com.gumtree.android.startup.StartupService;
import com.gumtree.android.userprofile.services.UserService;
import javax.inject.Inject;
import javax.inject.Named;
import rx.plugins.RxJavaPlugins;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig.Builder;

public class GumtreeApplication extends MultiDexApplication implements LocationProvider {
    private static final Boolean IS_BETA = Boolean.valueOf(false);
    @Inject
    static CAPIIntentFactory capiIntentFactory;
    private static GumtreeApplication instance;
    private static CapiAwareTransport transport;
    @Inject
    BaseAccountManager accountManager;
    @Inject
    Context appContext;
    @Inject
    PushNotificationsProvider appNotificationsProvider;
    @Named("xmlClient")
    @Inject
    ICapiClient capiClient;
    private AppLocation currentDeviceLocation;
    private AppLocation globalBuyerLocation;
    @Inject
    UserService userService;

    public static CAPIIntentFactory getAPI() {
        return capiIntentFactory;
    }

    public static boolean isFirstInstall(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(getApplicationContext(context)).getBoolean("pref_first_install", true);
    }

    public static void setFirstInstallStatus(boolean z, Context context) {
        PreferenceManager.getDefaultSharedPreferences(getApplicationContext(context)).edit().putBoolean("pref_first_install", z).apply();
    }

    public static Context getContext() {
        return instance;
    }

    public static GumtreeApplication getApplication() {
        return instance;
    }

    public static Transport getHttpTransport() {
        if (transport == null) {
            transport = new CapiAwareTransport(new AutoInflatingTransport(new HttpUrlConnectionTransport(instance)));
        }
        return transport;
    }

    public static boolean hasNFC() {
        return instance.getPackageManager().hasSystemFeature("android.hardware.nfc");
    }

    public static DataStorage getDataStorage() {
        return new ContentProviderDataStorage(instance);
    }

    private static Context getApplicationContext(Context context) {
        if (context == null) {
            return instance;
        }
        return context.getApplicationContext();
    }

    public static String getPackageNameForIntent() {
        return getContext().getPackageName();
    }

    public static boolean isDebug() {
        return false;
    }

    public static void loadLocalCategoryContent(Context context) {
        if (isFirstInstall(context)) {
            context.startService(StartupService.getService(true, context));
        } else {
            context.startService(StartupService.getService(false, context));
        }
    }

    public static boolean isLocationServicesAvailable() {
        return LocationServiceCheck.isLocationServicesEnabled(instance);
    }

    public static String getBaseUrl() {
        return getAPI().getBaseUrl() + getAPI().getFixedPathIfAny() + DFPProcessor.SEPARATOR;
    }

    public static Resources getResource() {
        return instance.getResources();
    }

    public static boolean isBeta() {
        return IS_BETA.booleanValue();
    }

    public static ApplicationComponent component() {
        return ComponentsManager.get().getAppComponent();
    }

    public static boolean isConnected(Context context) {
        NetworkInfo activeNetworkInfo = ((ConnectivityManager) context.getSystemService("connectivity")).getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public static boolean isLiveEnvironment() {
        return !isDebug() || getAPI().getBaseUrl().equals(GumtreeCAPIIntentFactory.PROD_ENDPOINT);
    }

    public static TrackingInfo getTrackingInfo() {
        return Tracking.getInstance(instance).getTrackingInfo();
    }

    public void onCreate() {
        super.onCreate();
        Log.setInstance(new AndroidLog());
        instance = this;
        initStrictMode();
        initRx();
        initTracker();
        initComponents();
        DevelopmentFlags.setInstance(new AndroidDevelopmentFlags(this));
        initFonts();
        initCleanup();
        initComScore();
        this.appNotificationsProvider.setup();
        initCrashlytics();
        initApptentive();
        CrashHandler.init();
        initSecurityProvider();
        if (this.accountManager.isUserLoggedIn()) {
            initUserProfile();
            initBadgeCounter();
        }
    }

    private void initRx() {
        RxJavaPlugins.getInstance().registerSchedulersHook(RxSchedulersHookHost.getInstance());
    }

    private void initSecurityProvider() {
        SecurityProviderManager.get().updateSecurityProviderAsync(getContext());
    }

    protected void initComponents() {
        Object build = DaggerApplicationComponent.builder().applicationModule(new ApplicationModule(getContext())).build();
        ComponentsManager.get().setAppComponent(build);
        build.inject(this);
    }

    public void setComponent(BaseComponent baseComponent) {
        ComponentsManager.get().setAppComponent(baseComponent);
    }

    private void initBadgeCounter() {
        GumtreeBadgeCounterManager.requestNumUnreadConversations();
    }

    public void initUserProfile() {
        this.userService.update();
    }

    private void initFonts() {
        CalligraphyConfig.initDefault(new Builder().setDefaultFontPath("fonts/Roboto-Regular.ttf").setFontAttrId(2130772492).build());
    }

    private void initTracker() {
        Track.prepare(this);
    }

    private void initCrashlytics() {
        CrashlyticsHelper.getInstance().initialize(getApplicationContext());
    }

    private void initApptentive() {
        Apptentive.register(this, getString(2131165357));
    }

    private void initComScore() {
        try {
            comScore.setAppContext(getApplicationContext());
            comScore.setPublisherSecret("da70ecc74afa03681ecfece4e0e164e8");
            comScore.setCustomerC2("7849854");
        } catch (NullPointerException e) {
        }
    }

    private void initCleanup() {
        startService(new Intent(this, CleanupIntentService.class));
    }

    protected void initStrictMode() {
        if (isDebug()) {
            StrictMode.enable();
        }
    }

    public AppLocation getGlobalBuyerLocation() {
        if (this.globalBuyerLocation == null) {
            this.globalBuyerLocation = LocationDAO.create().from(instance, getDeviceLocation());
        }
        return this.globalBuyerLocation;
    }

    public void setGlobalBuyerLocation(AppLocation appLocation) {
        this.globalBuyerLocation = appLocation;
    }

    public AppLocation getDeviceLocation() {
        if (this.currentDeviceLocation == null) {
            this.currentDeviceLocation = LocationDAO.create().from(instance);
        }
        return this.currentDeviceLocation;
    }

    public void setDeviceLocation(AppLocation appLocation) {
        this.currentDeviceLocation = appLocation;
        if (getString(2131165844).equals(getGlobalBuyerLocation().getName())) {
            setGlobalBuyerLocation(appLocation);
        }
    }
}
